package com.clipsub.RNShake;

import android.hardware.SensorManager;
import com.clipsub.RNShake.CustomShakeDetector;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = "RNShakeEvent")
public class RNShakeEventModule extends ReactContextBaseJavaModule {
    private final CustomShakeDetector mShakeDetector;

    public String getName() {
        return "RNShakeEvent";
    }

    public RNShakeEventModule(final ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        CustomShakeDetector customShakeDetector = new CustomShakeDetector(new CustomShakeDetector.ShakeListener() {
            public void onShake() {
                RNShakeEventModule.this.sendEvent(reactApplicationContext, "ShakeEvent", (WritableMap) null);
            }
        });
        this.mShakeDetector = customShakeDetector;
        customShakeDetector.start((SensorManager) reactApplicationContext.getSystemService("sensor"));
    }

    /* access modifiers changed from: private */
    public void sendEvent(ReactContext reactContext, String str, WritableMap writableMap) {
        if (reactContext.hasActiveCatalystInstance()) {
            ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
        }
    }
}
