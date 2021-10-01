package com.ocetnik.timer;

import android.os.Handler;
import android.os.PowerManager;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class BackgroundTimerModule extends ReactContextBaseJavaModule {
    private Handler handler;
    private final LifecycleEventListener listener = new LifecycleEventListener() {
        public void onHostPause() {
        }

        public void onHostResume() {
        }

        public void onHostDestroy() {
            if (BackgroundTimerModule.this.wakeLock.isHeld()) {
                BackgroundTimerModule.this.wakeLock.release();
            }
        }
    };
    private PowerManager powerManager;
    /* access modifiers changed from: private */
    public ReactContext reactContext;
    private Runnable runnable;
    /* access modifiers changed from: private */
    public PowerManager.WakeLock wakeLock;

    public String getName() {
        return "RNBackgroundTimer";
    }

    public BackgroundTimerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
        PowerManager powerManager2 = (PowerManager) getReactApplicationContext().getSystemService("power");
        this.powerManager = powerManager2;
        this.wakeLock = powerManager2.newWakeLock(1, "rohit_bg_wakelock");
        reactApplicationContext.addLifecycleEventListener(this.listener);
    }

    @ReactMethod
    public void start(int i) {
        if (!this.wakeLock.isHeld()) {
            this.wakeLock.acquire();
        }
        this.handler = new Handler();
        C22942 r2 = new Runnable() {
            public void run() {
                BackgroundTimerModule backgroundTimerModule = BackgroundTimerModule.this;
                backgroundTimerModule.sendEvent(backgroundTimerModule.reactContext, "backgroundTimer");
            }
        };
        this.runnable = r2;
        this.handler.post(r2);
    }

    @ReactMethod
    public void stop() {
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacks(this.runnable);
        }
    }

    /* access modifiers changed from: private */
    public void sendEvent(ReactContext reactContext2, String str) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext2.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, (Object) null);
    }

    @ReactMethod
    public void setTimeout(final int i, double d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (BackgroundTimerModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                    ((DeviceEventManagerModule.RCTDeviceEventEmitter) BackgroundTimerModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("backgroundTimer.timeout", Integer.valueOf(i));
                }
            }
        }, (long) d);
    }
}
