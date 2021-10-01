package com.thunkable.live;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.thunkable.live.generated.BasePackageList;
import expo.modules.updates.UpdatesController;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.unimodules.adapters.react.ModuleRegistryAdapter;
import org.unimodules.adapters.react.ReactModuleRegistryProvider;
import org.unimodules.core.interfaces.SingletonModule;

public class MainApplication extends Application implements ReactApplication {
    /* access modifiers changed from: private */
    public final ReactModuleRegistryProvider mModuleRegistryProvider = new ReactModuleRegistryProvider(new BasePackageList().getPackageList(), (List<SingletonModule>) null);
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        /* access modifiers changed from: protected */
        public String getJSMainModuleName() {
            return "index";
        }

        public boolean getUseDeveloperSupport() {
            return false;
        }

        /* access modifiers changed from: protected */
        public List<ReactPackage> getPackages() {
            ArrayList<ReactPackage> packages = new PackageList((ReactNativeHost) this).getPackages();
            packages.add(new ModuleRegistryAdapter(MainApplication.this.mModuleRegistryProvider));
            return packages;
        }

        /* access modifiers changed from: protected */
        @Nullable
        public String getJSBundleFile() {
            return UpdatesController.getInstance().getLaunchAssetFile();
        }

        /* access modifiers changed from: protected */
        @Nullable
        public String getBundleAssetName() {
            return UpdatesController.getInstance().getBundleAssetName();
        }
    };

    private static void initializeFlipper(Context context, ReactInstanceManager reactInstanceManager) {
    }

    public ReactNativeHost getReactNativeHost() {
        return this.mReactNativeHost;
    }

    public void onCreate() {
        super.onCreate();
        SoLoader.init((Context) this, false);
        UpdatesController.initialize(this);
        initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
    }
}
