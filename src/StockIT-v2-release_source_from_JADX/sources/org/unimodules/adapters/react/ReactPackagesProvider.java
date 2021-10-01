package org.unimodules.adapters.react;

import com.facebook.react.ReactPackage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;

public class ReactPackagesProvider implements InternalModule {
    private Collection<ReactPackage> mReactPackages = new ArrayList();

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public List<? extends Class> getExportedInterfaces() {
        return Collections.singletonList(ReactPackagesProvider.class);
    }

    public void addPackage(ReactPackage reactPackage) {
        this.mReactPackages.add(reactPackage);
    }

    public Collection<ReactPackage> getReactPackages() {
        return this.mReactPackages;
    }
}
