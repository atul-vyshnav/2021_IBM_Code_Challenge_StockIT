package com.airbnb.android.react.maps;

import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;
import javax.annotation.Nullable;

public class AirMapOverlayManager extends ViewGroupManager<AirMapOverlay> {
    private final DisplayMetrics metrics;

    public String getName() {
        return "AIRMapOverlay";
    }

    public AirMapOverlayManager(ReactApplicationContext reactApplicationContext) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.metrics = new DisplayMetrics();
            ((WindowManager) reactApplicationContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(this.metrics);
            return;
        }
        this.metrics = reactApplicationContext.getResources().getDisplayMetrics();
    }

    public AirMapOverlay createViewInstance(ThemedReactContext themedReactContext) {
        return new AirMapOverlay(themedReactContext);
    }

    @ReactProp(name = "bounds")
    public void setBounds(AirMapOverlay airMapOverlay, ReadableArray readableArray) {
        airMapOverlay.setBounds(readableArray);
    }

    @ReactProp(defaultFloat = 1.0f, name = "zIndex")
    public void setZIndex(AirMapOverlay airMapOverlay, float f) {
        airMapOverlay.setZIndex(f);
    }

    @ReactProp(defaultFloat = 1.0f, name = "opacity")
    public void setOpacity(AirMapOverlay airMapOverlay, float f) {
        airMapOverlay.setTransparency(1.0f - f);
    }

    @ReactProp(name = "image")
    public void setImage(AirMapOverlay airMapOverlay, @Nullable String str) {
        airMapOverlay.setImage(str);
    }

    @ReactProp(defaultBoolean = false, name = "tappable")
    public void setTappable(AirMapOverlay airMapOverlay, boolean z) {
        airMapOverlay.setTappable(z);
    }

    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.m140of("onPress", MapBuilder.m140of("registrationName", "onPress"));
    }
}
