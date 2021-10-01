package com.learnium.RNDeviceInfo;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

/* renamed from: com.learnium.RNDeviceInfo.RNDeviceInfo */
public class C2273RNDeviceInfo implements ReactPackage {
    @Nonnull
    public List<NativeModule> createNativeModules(@Nonnull ReactApplicationContext reactApplicationContext) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new RNDeviceModule(reactApplicationContext));
        return arrayList;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Nonnull
    public List<ViewManager> createViewManagers(@Nonnull ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }
}
