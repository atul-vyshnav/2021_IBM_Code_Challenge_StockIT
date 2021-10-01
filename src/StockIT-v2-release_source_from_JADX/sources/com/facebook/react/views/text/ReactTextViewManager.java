package com.facebook.react.views.text;

import android.content.Context;
import android.text.Spannable;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.IViewManagerWithChildren;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.yoga.YogaMeasureMode;
import java.util.Map;

@ReactModule(name = "RCTText")
public class ReactTextViewManager extends ReactTextAnchorViewManager<ReactTextView, ReactTextShadowNode> implements IViewManagerWithChildren {
    public static final String REACT_CLASS = "RCTText";
    protected ReactTextViewManagerCallback mReactTextViewManagerCallback;

    public String getName() {
        return REACT_CLASS;
    }

    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    public ReactTextView createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactTextView(themedReactContext);
    }

    public void updateExtraData(ReactTextView reactTextView, Object obj) {
        ReactTextUpdate reactTextUpdate = (ReactTextUpdate) obj;
        if (reactTextUpdate.containsImages()) {
            TextInlineImageSpan.possiblyUpdateInlineImageSpans(reactTextUpdate.getText(), reactTextView);
        }
        reactTextView.setText(reactTextUpdate);
    }

    public ReactTextShadowNode createShadowNodeInstance() {
        return new ReactTextShadowNode();
    }

    public ReactTextShadowNode createShadowNodeInstance(ReactTextViewManagerCallback reactTextViewManagerCallback) {
        return new ReactTextShadowNode(reactTextViewManagerCallback);
    }

    public Class<ReactTextShadowNode> getShadowNodeClass() {
        return ReactTextShadowNode.class;
    }

    /* access modifiers changed from: protected */
    public void onAfterUpdateTransaction(ReactTextView reactTextView) {
        super.onAfterUpdateTransaction(reactTextView);
        reactTextView.updateView();
    }

    public Object updateState(ReactTextView reactTextView, ReactStylesDiffMap reactStylesDiffMap, StateWrapper stateWrapper) {
        ReadableNativeMap state = stateWrapper.getState();
        ReadableNativeMap map = state.getMap("attributedString");
        ReadableNativeMap map2 = state.getMap("paragraphAttributes");
        Spannable orCreateSpannableForText = TextLayoutManager.getOrCreateSpannableForText(reactTextView.getContext(), map, this.mReactTextViewManagerCallback);
        reactTextView.setSpanned(orCreateSpannableForText);
        return new ReactTextUpdate(orCreateSpannableForText, state.hasKey("mostRecentEventCount") ? state.getInt("mostRecentEventCount") : -1, false, TextAttributeProps.getTextAlignment(reactStylesDiffMap), TextAttributeProps.getTextBreakStrategy(map2.getString(ViewProps.TEXT_BREAK_STRATEGY)), TextAttributeProps.getJustificationMode(reactStylesDiffMap));
    }

    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.m141of("topTextLayout", MapBuilder.m140of("registrationName", "onTextLayout"), "topInlineViewLayout", MapBuilder.m140of("registrationName", "onInlineViewLayout"));
    }

    public long measure(Context context, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2, int[] iArr) {
        return TextLayoutManager.measureText(context, readableMap, readableMap2, f, yogaMeasureMode, f2, yogaMeasureMode2, this.mReactTextViewManagerCallback, iArr);
    }

    public void setPadding(ReactTextView reactTextView, int i, int i2, int i3, int i4) {
        reactTextView.setPadding(i, i2, i3, i4);
    }
}
