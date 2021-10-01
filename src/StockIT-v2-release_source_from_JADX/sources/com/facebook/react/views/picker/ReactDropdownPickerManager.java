package com.facebook.react.views.picker;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.AndroidDropdownPickerManagerDelegate;
import com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface;

@ReactModule(name = "AndroidDropdownPicker")
public class ReactDropdownPickerManager extends ReactPickerManager implements AndroidDropdownPickerManagerInterface<ReactPicker> {
    public static final String REACT_CLASS = "AndroidDropdownPicker";
    private final ViewManagerDelegate<ReactPicker> mDelegate = new AndroidDropdownPickerManagerDelegate(this);

    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(customType = "Color", name = "color")
    public /* bridge */ /* synthetic */ void setColor(View view, Integer num) {
        super.setColor((ReactPicker) view, num);
    }

    @ReactProp(defaultBoolean = true, name = "enabled")
    public /* bridge */ /* synthetic */ void setEnabled(View view, boolean z) {
        super.setEnabled((ReactPicker) view, z);
    }

    @ReactProp(name = "items")
    public /* bridge */ /* synthetic */ void setItems(View view, ReadableArray readableArray) {
        super.setItems((ReactPicker) view, readableArray);
    }

    @ReactProp(name = "prompt")
    public /* bridge */ /* synthetic */ void setPrompt(View view, String str) {
        super.setPrompt((ReactPicker) view, str);
    }

    @ReactProp(name = "selected")
    public /* bridge */ /* synthetic */ void setSelected(View view, int i) {
        super.setSelected((ReactPicker) view, i);
    }

    /* access modifiers changed from: protected */
    public ReactPicker createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactPicker((Context) themedReactContext, 1);
    }

    /* access modifiers changed from: protected */
    public ViewManagerDelegate<ReactPicker> getDelegate() {
        return this.mDelegate;
    }

    public void setBackgroundColor(ReactPicker reactPicker, int i) {
        reactPicker.setStagedBackgroundColor(Integer.valueOf(i));
    }
}
