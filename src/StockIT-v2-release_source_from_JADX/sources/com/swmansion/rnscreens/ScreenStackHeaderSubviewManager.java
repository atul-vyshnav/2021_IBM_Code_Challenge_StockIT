package com.swmansion.rnscreens;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.facebook.react.views.view.ReactViewManager;
import com.swmansion.rnscreens.ScreenStackHeaderSubview;

@ReactModule(name = "RNSScreenStackHeaderSubview")
public class ScreenStackHeaderSubviewManager extends ReactViewManager {
    protected static final String REACT_CLASS = "RNSScreenStackHeaderSubview";

    public String getName() {
        return REACT_CLASS;
    }

    public ReactViewGroup createViewInstance(ThemedReactContext themedReactContext) {
        return new ScreenStackHeaderSubview(themedReactContext);
    }

    @ReactProp(name = "type")
    public void setType(ScreenStackHeaderSubview screenStackHeaderSubview, String str) {
        if ("left".equals(str)) {
            screenStackHeaderSubview.setType(ScreenStackHeaderSubview.Type.LEFT);
        } else if (TtmlNode.CENTER.equals(str)) {
            screenStackHeaderSubview.setType(ScreenStackHeaderSubview.Type.CENTER);
        } else if ("right".equals(str)) {
            screenStackHeaderSubview.setType(ScreenStackHeaderSubview.Type.RIGHT);
        } else if ("back".equals(str)) {
            screenStackHeaderSubview.setType(ScreenStackHeaderSubview.Type.BACK);
        }
    }
}
