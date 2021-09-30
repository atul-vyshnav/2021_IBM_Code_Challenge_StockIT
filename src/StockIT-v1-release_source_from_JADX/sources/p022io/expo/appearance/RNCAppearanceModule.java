package p022io.expo.appearance;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.util.HashMap;
import java.util.Map;

/* renamed from: io.expo.appearance.RNCAppearanceModule */
public class RNCAppearanceModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    public static final String REACT_CLASS = "RNCAppearance";
    private BroadcastReceiver mBroadcastReceiver = null;

    public String getName() {
        return REACT_CLASS;
    }

    public void onHostDestroy() {
    }

    public RNCAppearanceModule(final ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        if (Build.VERSION.SDK_INT > 28) {
            this.mBroadcastReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    Configuration configuration = (Configuration) intent.getParcelableExtra("newConfig");
                    RNCAppearanceModule rNCAppearanceModule = RNCAppearanceModule.this;
                    rNCAppearanceModule.sendEvent(reactApplicationContext, "appearanceChanged", rNCAppearanceModule.getPreferences());
                }
            };
            reactApplicationContext.addLifecycleEventListener(this);
        }
    }

    /* access modifiers changed from: protected */
    public String getColorScheme(Configuration configuration) {
        if (Build.VERSION.SDK_INT > 28) {
            int i = configuration.uiMode & 48;
            if (i == 0 || i == 16) {
                return "light";
            }
            return i != 32 ? "no-preference" : "dark";
        }
    }

    /* access modifiers changed from: private */
    public WritableMap getPreferences() {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("colorScheme", getColorScheme(getReactApplicationContext().getResources().getConfiguration()));
        return createMap;
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("initialPreferences", getPreferences());
        return hashMap;
    }

    /* access modifiers changed from: private */
    public void sendEvent(ReactContext reactContext, String str, WritableMap writableMap) {
        if (reactContext.hasActiveCatalystInstance()) {
            FLog.m76i("sendEvent", str + ": " + writableMap.toString());
            ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
        }
    }

    private void sendEvent(String str, WritableMap writableMap) {
        if (getReactApplicationContext().hasActiveCatalystInstance()) {
            FLog.m76i("sendEvent", str + ": " + writableMap.toString());
            ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
        }
    }

    private void updateAndSendAppearancePreferences() {
        sendEvent("appearanceChanged", getPreferences());
    }

    public void onHostResume() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            FLog.m64e(ReactConstants.TAG, "no activity to register receiver");
            return;
        }
        currentActivity.registerReceiver(this.mBroadcastReceiver, new IntentFilter("onConfigurationChanged"));
        updateAndSendAppearancePreferences();
    }

    public void onHostPause() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            try {
                currentActivity.unregisterReceiver(this.mBroadcastReceiver);
            } catch (IllegalArgumentException e) {
                FLog.m65e(ReactConstants.TAG, "mBroadcastReceiver already unregistered", (Throwable) e);
            }
        }
    }
}
