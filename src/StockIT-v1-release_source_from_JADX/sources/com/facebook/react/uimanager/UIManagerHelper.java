package com.facebook.react.uimanager;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.JSIModuleType;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactNoCrashSoftException;
import com.facebook.react.bridge.ReactSoftException;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.uimanager.common.ViewUtil;
import com.facebook.react.uimanager.events.EventDispatcher;

public class UIManagerHelper {
    public static UIManager getUIManagerForReactTag(ReactContext reactContext, int i) {
        return getUIManager(reactContext, ViewUtil.getUIManagerType(i));
    }

    public static UIManager getUIManager(ReactContext reactContext, int i) {
        return getUIManager(reactContext, i, true);
    }

    private static UIManager getUIManager(ReactContext reactContext, int i, boolean z) {
        if (reactContext.isBridgeless()) {
            return (UIManager) reactContext.getJSIModule(JSIModuleType.UIManager);
        }
        if (!reactContext.hasCatalystInstance()) {
            ReactSoftException.logSoftException("UIManagerHelper", new ReactNoCrashSoftException("Cannot get UIManager because the context doesn't contain a CatalystInstance."));
            return null;
        }
        if (!reactContext.hasActiveCatalystInstance()) {
            ReactSoftException.logSoftException("UIManagerHelper", new ReactNoCrashSoftException("Cannot get UIManager because the context doesn't contain an active CatalystInstance."));
            if (z) {
                return null;
            }
        }
        CatalystInstance catalystInstance = reactContext.getCatalystInstance();
        if (i == 2) {
            return (UIManager) catalystInstance.getJSIModule(JSIModuleType.UIManager);
        }
        return (UIManager) catalystInstance.getNativeModule(UIManagerModule.class);
    }

    public static EventDispatcher getEventDispatcherForReactTag(ReactContext reactContext, int i) {
        return getEventDispatcher(reactContext, ViewUtil.getUIManagerType(i));
    }

    public static EventDispatcher getEventDispatcher(ReactContext reactContext, int i) {
        UIManager uIManager = getUIManager(reactContext, i, false);
        if (uIManager == null) {
            return null;
        }
        return (EventDispatcher) uIManager.getEventDispatcher();
    }

    public static ReactContext getReactContext(View view) {
        Context context = view.getContext();
        if (!(context instanceof ReactContext) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (ReactContext) context;
    }
}
