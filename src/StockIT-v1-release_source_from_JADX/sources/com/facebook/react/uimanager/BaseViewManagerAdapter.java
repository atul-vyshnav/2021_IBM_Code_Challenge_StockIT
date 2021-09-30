package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

public abstract class BaseViewManagerAdapter<T extends View> implements BaseViewManagerInterface<T> {
    public void setAccessibilityActions(T t, ReadableArray readableArray) {
    }

    public void setAccessibilityHint(T t, String str) {
    }

    public void setAccessibilityLabel(T t, String str) {
    }

    public void setAccessibilityLiveRegion(T t, String str) {
    }

    public void setAccessibilityRole(T t, String str) {
    }

    public void setBackgroundColor(T t, int i) {
    }

    public void setBorderBottomLeftRadius(T t, float f) {
    }

    public void setBorderBottomRightRadius(T t, float f) {
    }

    public void setBorderRadius(T t, float f) {
    }

    public void setBorderTopLeftRadius(T t, float f) {
    }

    public void setBorderTopRightRadius(T t, float f) {
    }

    public void setElevation(T t, float f) {
    }

    public void setImportantForAccessibility(T t, String str) {
    }

    public void setNativeId(T t, String str) {
    }

    public void setOpacity(T t, float f) {
    }

    public void setRenderToHardwareTexture(T t, boolean z) {
    }

    public void setRotation(T t, float f) {
    }

    public void setScaleX(T t, float f) {
    }

    public void setScaleY(T t, float f) {
    }

    public void setTestId(T t, String str) {
    }

    public void setTransform(T t, ReadableArray readableArray) {
    }

    public void setTranslateX(T t, float f) {
    }

    public void setTranslateY(T t, float f) {
    }

    public void setViewState(T t, ReadableMap readableMap) {
    }

    public void setZIndex(T t, float f) {
    }
}
