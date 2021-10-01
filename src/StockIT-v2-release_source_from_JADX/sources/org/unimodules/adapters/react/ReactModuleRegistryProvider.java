package org.unimodules.adapters.react;

import android.content.Context;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.ModuleRegistryProvider;
import org.unimodules.core.interfaces.Package;
import org.unimodules.core.interfaces.SingletonModule;

public class ReactModuleRegistryProvider extends ModuleRegistryProvider {
    private Collection<ViewManager> mReactViewManagers;
    private Collection<SingletonModule> mSingletonModules;
    private Collection<org.unimodules.core.ViewManager> mViewManagers;

    public ReactModuleRegistryProvider(List<Package> list) {
        this(list, (List<SingletonModule>) null);
    }

    public ReactModuleRegistryProvider(List<Package> list, List<SingletonModule> list2) {
        super(list);
        this.mSingletonModules = list2;
    }

    public ModuleRegistry get(Context context) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ReactPackagesProvider reactPackagesProvider = new ReactPackagesProvider();
        for (Package next : getPackages()) {
            arrayList.addAll(next.createInternalModules(context));
            arrayList2.addAll(next.createExportedModules(context));
            if (next instanceof ReactPackage) {
                reactPackagesProvider.addPackage((ReactPackage) next);
            }
        }
        arrayList.add(reactPackagesProvider);
        return new ModuleRegistry(arrayList, arrayList2, getViewManagers(context), getSingletonModules(context));
    }

    private Collection<SingletonModule> getSingletonModules(Context context) {
        Collection<SingletonModule> collection = this.mSingletonModules;
        if (collection != null) {
            return collection;
        }
        ArrayList arrayList = new ArrayList();
        for (Package createSingletonModules : getPackages()) {
            arrayList.addAll(createSingletonModules.createSingletonModules(context));
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public Collection<org.unimodules.core.ViewManager> getViewManagers(Context context) {
        Collection<org.unimodules.core.ViewManager> collection = this.mViewManagers;
        if (collection != null) {
            return collection;
        }
        HashSet hashSet = new HashSet();
        this.mViewManagers = hashSet;
        hashSet.addAll(createViewManagers(context));
        return this.mViewManagers;
    }

    /* access modifiers changed from: package-private */
    public Collection<ViewManager> getReactViewManagers(ReactApplicationContext reactApplicationContext) {
        Collection<ViewManager> collection = this.mReactViewManagers;
        if (collection != null) {
            return collection;
        }
        this.mReactViewManagers = new HashSet();
        for (Package next : getPackages()) {
            if (next instanceof ReactPackage) {
                this.mReactViewManagers.addAll(((ReactPackage) next).createViewManagers(reactApplicationContext));
            }
        }
        return this.mReactViewManagers;
    }
}
