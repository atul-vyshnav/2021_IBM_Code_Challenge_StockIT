package com.facebook.react.uimanager;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.JSIModule;
import com.facebook.react.bridge.JSIModuleType;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

public class ThemedReactContext extends ReactContext {
    private final ReactApplicationContext mReactApplicationContext;
    private final String mSurfaceID;

    public ThemedReactContext(ReactApplicationContext reactApplicationContext, Context context) {
        this(reactApplicationContext, context, (String) null);
    }

    public ThemedReactContext(ReactApplicationContext reactApplicationContext, Context context, String str) {
        super(context);
        if (reactApplicationContext.hasCatalystInstance()) {
            initializeWithInstance(reactApplicationContext.getCatalystInstance());
        }
        this.mReactApplicationContext = reactApplicationContext;
        this.mSurfaceID = str;
    }

    public void addLifecycleEventListener(LifecycleEventListener lifecycleEventListener) {
        this.mReactApplicationContext.addLifecycleEventListener(lifecycleEventListener);
    }

    public void removeLifecycleEventListener(LifecycleEventListener lifecycleEventListener) {
        this.mReactApplicationContext.removeLifecycleEventListener(lifecycleEventListener);
    }

    public boolean hasCurrentActivity() {
        return this.mReactApplicationContext.hasCurrentActivity();
    }

    public Activity getCurrentActivity() {
        return this.mReactApplicationContext.getCurrentActivity();
    }

    public String getSurfaceID() {
        return this.mSurfaceID;
    }

    public boolean isBridgeless() {
        return this.mReactApplicationContext.isBridgeless();
    }

    public JSIModule getJSIModule(JSIModuleType jSIModuleType) {
        if (isBridgeless()) {
            return this.mReactApplicationContext.getJSIModule(jSIModuleType);
        }
        return super.getJSIModule(jSIModuleType);
    }
}
