package org.unimodules.adapters.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.adapters.react.views.SimpleViewManagerAdapter;
import org.unimodules.adapters.react.views.ViewGroupManagerAdapter;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;

public class ModuleRegistryAdapter implements ReactPackage {
    protected ReactModuleRegistryProvider mModuleRegistryProvider;
    protected ReactAdapterPackage mReactAdapterPackage = new ReactAdapterPackage();

    public ModuleRegistryAdapter(ReactModuleRegistryProvider reactModuleRegistryProvider) {
        this.mModuleRegistryProvider = reactModuleRegistryProvider;
    }

    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        ModuleRegistry moduleRegistry = this.mModuleRegistryProvider.get(reactApplicationContext);
        for (InternalModule registerInternalModule : this.mReactAdapterPackage.createInternalModules(reactApplicationContext)) {
            moduleRegistry.registerInternalModule(registerInternalModule);
        }
        return getNativeModulesFromModuleRegistry(reactApplicationContext, moduleRegistry);
    }

    /* access modifiers changed from: protected */
    public List<NativeModule> getNativeModulesFromModuleRegistry(ReactApplicationContext reactApplicationContext, ModuleRegistry moduleRegistry) {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new NativeModulesProxy(reactApplicationContext, moduleRegistry));
        arrayList.add(new ModuleRegistryReadyNotifier(moduleRegistry));
        for (ReactPackage createNativeModules : ((ReactPackagesProvider) moduleRegistry.getModule(ReactPackagesProvider.class)).getReactPackages()) {
            arrayList.addAll(createNativeModules.createNativeModules(reactApplicationContext));
        }
        return arrayList;
    }

    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        ArrayList arrayList = new ArrayList(this.mModuleRegistryProvider.getReactViewManagers(reactApplicationContext));
        for (org.unimodules.core.ViewManager next : this.mModuleRegistryProvider.getViewManagers(reactApplicationContext)) {
            int i = C35111.$SwitchMap$org$unimodules$core$ViewManager$ViewManagerType[next.getViewManagerType().ordinal()];
            if (i == 1) {
                arrayList.add(new ViewGroupManagerAdapter(next));
            } else if (i == 2) {
                arrayList.add(new SimpleViewManagerAdapter(next));
            }
        }
        return arrayList;
    }

    /* renamed from: org.unimodules.adapters.react.ModuleRegistryAdapter$1 */
    static /* synthetic */ class C35111 {
        static final /* synthetic */ int[] $SwitchMap$org$unimodules$core$ViewManager$ViewManagerType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                org.unimodules.core.ViewManager$ViewManagerType[] r0 = org.unimodules.core.ViewManager.ViewManagerType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$unimodules$core$ViewManager$ViewManagerType = r0
                org.unimodules.core.ViewManager$ViewManagerType r1 = org.unimodules.core.ViewManager.ViewManagerType.GROUP     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$unimodules$core$ViewManager$ViewManagerType     // Catch:{ NoSuchFieldError -> 0x001d }
                org.unimodules.core.ViewManager$ViewManagerType r1 = org.unimodules.core.ViewManager.ViewManagerType.SIMPLE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.unimodules.adapters.react.ModuleRegistryAdapter.C35111.<clinit>():void");
        }
    }
}
