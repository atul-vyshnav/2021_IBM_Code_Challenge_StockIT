package com.swmansion.rnscreens;

import android.view.View;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nonnull;

@ReactModule(name = "RNSScreenStackHeaderConfig")
public class ScreenStackHeaderConfigViewManager extends ViewGroupManager<ScreenStackHeaderConfig> {
    protected static final String REACT_CLASS = "RNSScreenStackHeaderConfig";

    public String getName() {
        return REACT_CLASS;
    }

    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    /* access modifiers changed from: protected */
    public ScreenStackHeaderConfig createViewInstance(ThemedReactContext themedReactContext) {
        return new ScreenStackHeaderConfig(themedReactContext);
    }

    public void addView(ScreenStackHeaderConfig screenStackHeaderConfig, View view, int i) {
        if (view instanceof ScreenStackHeaderSubview) {
            screenStackHeaderConfig.addConfigSubview((ScreenStackHeaderSubview) view, i);
            return;
        }
        throw new JSApplicationCausedNativeException("Config children should be of type RNSScreenStackHeaderSubview");
    }

    public void onDropViewInstance(@Nonnull ScreenStackHeaderConfig screenStackHeaderConfig) {
        screenStackHeaderConfig.destroy();
    }

    public void removeAllViews(ScreenStackHeaderConfig screenStackHeaderConfig) {
        screenStackHeaderConfig.removeAllConfigSubviews();
    }

    public void removeViewAt(ScreenStackHeaderConfig screenStackHeaderConfig, int i) {
        screenStackHeaderConfig.removeConfigSubview(i);
    }

    public int getChildCount(ScreenStackHeaderConfig screenStackHeaderConfig) {
        return screenStackHeaderConfig.getConfigSubviewsCount();
    }

    public View getChildAt(ScreenStackHeaderConfig screenStackHeaderConfig, int i) {
        return screenStackHeaderConfig.getConfigSubview(i);
    }

    /* access modifiers changed from: protected */
    public void onAfterUpdateTransaction(ScreenStackHeaderConfig screenStackHeaderConfig) {
        super.onAfterUpdateTransaction(screenStackHeaderConfig);
        screenStackHeaderConfig.onUpdate();
    }

    @ReactProp(name = "title")
    public void setTitle(ScreenStackHeaderConfig screenStackHeaderConfig, String str) {
        screenStackHeaderConfig.setTitle(str);
    }

    @ReactProp(name = "titleFontFamily")
    public void setTitleFontFamily(ScreenStackHeaderConfig screenStackHeaderConfig, String str) {
        screenStackHeaderConfig.setTitleFontFamily(str);
    }

    @ReactProp(name = "titleFontSize")
    public void setTitleFontSize(ScreenStackHeaderConfig screenStackHeaderConfig, float f) {
        screenStackHeaderConfig.setTitleFontSize(f);
    }

    @ReactProp(customType = "Color", name = "titleColor")
    public void setTitleColor(ScreenStackHeaderConfig screenStackHeaderConfig, int i) {
        screenStackHeaderConfig.setTitleColor(i);
    }

    @ReactProp(customType = "Color", name = "backgroundColor")
    public void setBackgroundColor(ScreenStackHeaderConfig screenStackHeaderConfig, Integer num) {
        screenStackHeaderConfig.setBackgroundColor(num);
    }

    @ReactProp(name = "hideShadow")
    public void setHideShadow(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setHideShadow(z);
    }

    @ReactProp(name = "hideBackButton")
    public void setHideBackButton(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setHideBackButton(z);
    }

    @ReactProp(name = "topInsetEnabled")
    public void setTopInsetEnabled(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setTopInsetEnabled(z);
    }

    @ReactProp(customType = "Color", name = "color")
    public void setColor(ScreenStackHeaderConfig screenStackHeaderConfig, int i) {
        screenStackHeaderConfig.setTintColor(i);
    }

    @ReactProp(name = "hidden")
    public void setHidden(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setHidden(z);
    }

    @ReactProp(name = "translucent")
    public void setTranslucent(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setTranslucent(z);
    }

    @ReactProp(name = "backButtonInCustomView")
    public void setBackButtonInCustomView(ScreenStackHeaderConfig screenStackHeaderConfig, boolean z) {
        screenStackHeaderConfig.setBackButtonInCustomView(z);
    }

    @ReactProp(name = "direction")
    public void setDirection(ScreenStackHeaderConfig screenStackHeaderConfig, String str) {
        screenStackHeaderConfig.setDirection(str);
    }

    @ReactProp(name = "screenOrientation")
    public void setScreenOrientation(ScreenStackHeaderConfig screenStackHeaderConfig, String str) {
        screenStackHeaderConfig.setScreenOrientation(str);
    }
}
