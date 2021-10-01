package org.apache.logging.log4j.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.Provider;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;

public final class ProviderUtil {
    private static final String API_VERSION = "Log4jAPIVersion";
    private static final String[] COMPATIBLE_API_VERSIONS = {"2.0.0", "2.1.0"};
    private static volatile ProviderUtil INSTANCE = null;
    private static final Logger LOGGER = StatusLogger.getLogger();
    protected static final Collection<Provider> PROVIDERS = new HashSet();
    protected static final String PROVIDER_RESOURCE = "META-INF/log4j-provider.properties";
    protected static final Lock STARTUP_LOCK = new ReentrantLock();

    private ProviderUtil() {
        for (LoaderUtil.UrlResource next : LoaderUtil.findUrlResources(PROVIDER_RESOURCE)) {
            loadProvider(next.getUrl(), next.getClassLoader());
        }
    }

    protected static void loadProvider(URL url, ClassLoader classLoader) {
        try {
            Properties loadClose = PropertiesUtil.loadClose(url.openStream(), url);
            if (validVersion(loadClose.getProperty(API_VERSION))) {
                PROVIDERS.add(new Provider(loadClose, url, classLoader));
            }
        } catch (IOException e) {
            LOGGER.error("Unable to open {}", url, e);
        }
    }

    @Deprecated
    protected static void loadProviders(Enumeration<URL> enumeration, ClassLoader classLoader) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                loadProvider(enumeration.nextElement(), classLoader);
            }
        }
    }

    public static Iterable<Provider> getProviders() {
        lazyInit();
        return PROVIDERS;
    }

    public static boolean hasProviders() {
        lazyInit();
        return !PROVIDERS.isEmpty();
    }

    protected static void lazyInit() {
        if (INSTANCE == null) {
            try {
                STARTUP_LOCK.lockInterruptibly();
                if (INSTANCE == null) {
                    INSTANCE = new ProviderUtil();
                }
            } catch (InterruptedException e) {
                LOGGER.fatal("Interrupted before Log4j Providers could be loaded.", (Throwable) e);
                Thread.currentThread().interrupt();
            } catch (Throwable th) {
                STARTUP_LOCK.unlock();
                throw th;
            }
            STARTUP_LOCK.unlock();
        }
    }

    public static ClassLoader findClassLoader() {
        return LoaderUtil.getThreadContextClassLoader();
    }

    private static boolean validVersion(String str) {
        for (String startsWith : COMPATIBLE_API_VERSIONS) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }
}
