package org.apache.logging.log4j.util;

import java.net.URL;
import java.security.Permission;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

public class Activator implements BundleActivator, SynchronousBundleListener {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();
    private boolean lockingProviderUtil;

    private static void checkPermission(Permission permission) {
        SecurityManager securityManager = SECURITY_MANAGER;
        if (securityManager != null) {
            securityManager.checkPermission(permission);
        }
    }

    private void loadProvider(Bundle bundle) {
        if (bundle.getState() != 1) {
            try {
                checkPermission(new AdminPermission(bundle, "resource"));
                checkPermission(new AdaptPermission(BundleWiring.class.getName(), bundle, "adapt"));
                loadProvider((BundleWiring) bundle.adapt(BundleWiring.class));
            } catch (SecurityException e) {
                LOGGER.debug("Cannot access bundle [{}] contents. Ignoring.", bundle.getSymbolicName(), e);
            } catch (Exception e2) {
                LOGGER.warn("Problem checking bundle {} for Log4j 2 provider.", bundle.getSymbolicName(), e2);
            }
        }
    }

    private void loadProvider(BundleWiring bundleWiring) {
        for (URL loadProvider : bundleWiring.findEntries("META-INF", "log4j-provider.properties", 0)) {
            ProviderUtil.loadProvider(loadProvider, bundleWiring.getClassLoader());
        }
    }

    public void start(BundleContext bundleContext) throws Exception {
        ProviderUtil.STARTUP_LOCK.lock();
        this.lockingProviderUtil = true;
        for (BundleWire providerWiring : ((BundleWiring) bundleContext.getBundle().adapt(BundleWiring.class)).getRequiredWires(LoggerContextFactory.class.getName())) {
            loadProvider(providerWiring.getProviderWiring());
        }
        bundleContext.addBundleListener(this);
        for (Bundle loadProvider : bundleContext.getBundles()) {
            loadProvider(loadProvider);
        }
        unlockIfReady();
    }

    private void unlockIfReady() {
        if (this.lockingProviderUtil && !ProviderUtil.PROVIDERS.isEmpty()) {
            ProviderUtil.STARTUP_LOCK.unlock();
            this.lockingProviderUtil = false;
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        bundleContext.removeBundleListener(this);
        unlockIfReady();
    }

    public void bundleChanged(BundleEvent bundleEvent) {
        if (bundleEvent.getType() == 2) {
            loadProvider(bundleEvent.getBundle());
            unlockIfReady();
        }
    }
}
