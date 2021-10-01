package com.swmansion.rnscreens;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.swmansion.rnscreens.Screen;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "RNSScreen")
public class ScreenViewManager extends ViewGroupManager<Screen> {
    protected static final String REACT_CLASS = "RNSScreen";

    public String getName() {
        return REACT_CLASS;
    }

    /* access modifiers changed from: protected */
    public Screen createViewInstance(ThemedReactContext themedReactContext) {
        return new Screen(themedReactContext);
    }

    @ReactProp(name = "activityState")
    public void setActivityState(Screen screen, Integer num) {
        if (num != null) {
            if (num.intValue() == 0) {
                screen.setActivityState(Screen.ActivityState.INACTIVE);
            } else if (num.intValue() == 1) {
                screen.setActivityState(Screen.ActivityState.TRANSITIONING_OR_BELOW_TOP);
            } else if (num.intValue() == 2) {
                screen.setActivityState(Screen.ActivityState.ON_TOP);
            }
        }
    }

    @ReactProp(name = "stackPresentation")
    public void setStackPresentation(Screen screen, String str) {
        if ("push".equals(str)) {
            screen.setStackPresentation(Screen.StackPresentation.PUSH);
        } else if ("modal".equals(str) || "containedModal".equals(str) || "fullScreenModal".equals(str) || "formSheet".equals(str)) {
            screen.setStackPresentation(Screen.StackPresentation.MODAL);
        } else if ("transparentModal".equals(str) || "containedTransparentModal".equals(str)) {
            screen.setStackPresentation(Screen.StackPresentation.TRANSPARENT_MODAL);
        } else {
            throw new JSApplicationIllegalArgumentException("Unknown presentation type " + str);
        }
    }

    @ReactProp(name = "stackAnimation")
    public void setStackAnimation(Screen screen, String str) {
        if (str == null || "default".equals(str)) {
            screen.setStackAnimation(Screen.StackAnimation.DEFAULT);
        } else if ("none".equals(str)) {
            screen.setStackAnimation(Screen.StackAnimation.NONE);
        } else if ("fade".equals(str)) {
            screen.setStackAnimation(Screen.StackAnimation.FADE);
        } else if ("slide_from_right".equals(str)) {
            screen.setStackAnimation(Screen.StackAnimation.SLIDE_FROM_RIGHT);
        } else if ("slide_from_left".equals(str)) {
            screen.setStackAnimation(Screen.StackAnimation.SLIDE_FROM_LEFT);
        }
    }

    @ReactProp(defaultBoolean = true, name = "gestureEnabled")
    public void setGestureEnabled(Screen screen, boolean z) {
        screen.setGestureEnabled(z);
    }

    @ReactProp(name = "replaceAnimation")
    public void setReplaceAnimation(Screen screen, String str) {
        if (str == null || "pop".equals(str)) {
            screen.setReplaceAnimation(Screen.ReplaceAnimation.POP);
        } else if ("push".equals(str)) {
            screen.setReplaceAnimation(Screen.ReplaceAnimation.PUSH);
        }
    }

    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.m145of(ScreenDismissedEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onDismissed"), ScreenWillAppearEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onWillAppear"), ScreenAppearEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onAppear"), ScreenWillDisappearEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onWillDisappear"), ScreenDisappearEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onDisappear"), StackFinishTransitioningEvent.EVENT_NAME, MapBuilder.m140of("registrationName", "onFinishTransitioning"));
    }
}
