package com.facebook.fbreact.specs;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class NativeJSDevSupportSpec extends ReactContextBaseJavaModule implements ReactModuleWithSpec, TurboModule {
    /* access modifiers changed from: protected */
    public abstract Map<String, Object> getTypedExportedConstants();

    @ReactMethod
    public abstract void onFailure(double d, String str);

    @ReactMethod
    public abstract void onSuccess(String str);

    public NativeJSDevSupportSpec(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Nullable
    public final Map<String, Object> getConstants() {
        return getTypedExportedConstants();
    }
}
