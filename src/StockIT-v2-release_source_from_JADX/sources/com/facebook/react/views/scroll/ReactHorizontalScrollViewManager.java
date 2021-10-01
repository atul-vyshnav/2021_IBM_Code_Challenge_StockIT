package com.facebook.react.views.scroll;

import android.util.DisplayMetrics;
import androidx.core.view.ViewCompat;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.views.scroll.ReactScrollViewCommandHelper;
import com.facebook.yoga.YogaConstants;
import java.util.ArrayList;

@ReactModule(name = "AndroidHorizontalScrollView")
public class ReactHorizontalScrollViewManager extends ViewGroupManager<ReactHorizontalScrollView> implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactHorizontalScrollView> {
    public static final String REACT_CLASS = "AndroidHorizontalScrollView";
    private static final int[] SPACING_TYPES = {8, 0, 2, 1, 3};
    private FpsListener mFpsListener;

    public String getName() {
        return REACT_CLASS;
    }

    public ReactHorizontalScrollViewManager() {
        this((FpsListener) null);
    }

    public ReactHorizontalScrollViewManager(FpsListener fpsListener) {
        this.mFpsListener = null;
        this.mFpsListener = fpsListener;
    }

    public ReactHorizontalScrollView createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactHorizontalScrollView(themedReactContext, this.mFpsListener);
    }

    public Object updateState(ReactHorizontalScrollView reactHorizontalScrollView, ReactStylesDiffMap reactStylesDiffMap, StateWrapper stateWrapper) {
        reactHorizontalScrollView.updateState(stateWrapper);
        return null;
    }

    @ReactProp(defaultBoolean = true, name = "scrollEnabled")
    public void setScrollEnabled(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setScrollEnabled(z);
    }

    @ReactProp(name = "showsHorizontalScrollIndicator")
    public void setShowsHorizontalScrollIndicator(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setHorizontalScrollBarEnabled(z);
    }

    @ReactProp(name = "decelerationRate")
    public void setDecelerationRate(ReactHorizontalScrollView reactHorizontalScrollView, float f) {
        reactHorizontalScrollView.setDecelerationRate(f);
    }

    @ReactProp(name = "disableIntervalMomentum")
    public void setDisableIntervalMomentum(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setDisableIntervalMomentum(z);
    }

    @ReactProp(name = "snapToInterval")
    public void setSnapToInterval(ReactHorizontalScrollView reactHorizontalScrollView, float f) {
        reactHorizontalScrollView.setSnapInterval((int) (f * DisplayMetricsHolder.getScreenDisplayMetrics().density));
    }

    @ReactProp(name = "snapToOffsets")
    public void setSnapToOffsets(ReactHorizontalScrollView reactHorizontalScrollView, ReadableArray readableArray) {
        DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getScreenDisplayMetrics();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < readableArray.size(); i++) {
            arrayList.add(Integer.valueOf((int) (readableArray.getDouble(i) * ((double) screenDisplayMetrics.density))));
        }
        reactHorizontalScrollView.setSnapOffsets(arrayList);
    }

    @ReactProp(name = "snapToStart")
    public void setSnapToStart(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setSnapToStart(z);
    }

    @ReactProp(name = "snapToEnd")
    public void setSnapToEnd(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setSnapToEnd(z);
    }

    @ReactProp(name = "removeClippedSubviews")
    public void setRemoveClippedSubviews(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setRemoveClippedSubviews(z);
    }

    @ReactProp(name = "sendMomentumEvents")
    public void setSendMomentumEvents(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setSendMomentumEvents(z);
    }

    @ReactProp(name = "scrollPerfTag")
    public void setScrollPerfTag(ReactHorizontalScrollView reactHorizontalScrollView, String str) {
        reactHorizontalScrollView.setScrollPerfTag(str);
    }

    @ReactProp(name = "pagingEnabled")
    public void setPagingEnabled(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setPagingEnabled(z);
    }

    @ReactProp(name = "overScrollMode")
    public void setOverScrollMode(ReactHorizontalScrollView reactHorizontalScrollView, String str) {
        reactHorizontalScrollView.setOverScrollMode(ReactScrollViewHelper.parseOverScrollMode(str));
    }

    @ReactProp(name = "nestedScrollEnabled")
    public void setNestedScrollEnabled(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        ViewCompat.setNestedScrollingEnabled(reactHorizontalScrollView, z);
    }

    public void receiveCommand(ReactHorizontalScrollView reactHorizontalScrollView, int i, ReadableArray readableArray) {
        ReactScrollViewCommandHelper.receiveCommand(this, reactHorizontalScrollView, i, readableArray);
    }

    public void receiveCommand(ReactHorizontalScrollView reactHorizontalScrollView, String str, ReadableArray readableArray) {
        ReactScrollViewCommandHelper.receiveCommand(this, reactHorizontalScrollView, str, readableArray);
    }

    public void flashScrollIndicators(ReactHorizontalScrollView reactHorizontalScrollView) {
        reactHorizontalScrollView.flashScrollIndicators();
    }

    public void scrollTo(ReactHorizontalScrollView reactHorizontalScrollView, ReactScrollViewCommandHelper.ScrollToCommandData scrollToCommandData) {
        if (scrollToCommandData.mAnimated) {
            reactHorizontalScrollView.reactSmoothScrollTo(scrollToCommandData.mDestX, scrollToCommandData.mDestY);
        } else {
            reactHorizontalScrollView.reactScrollTo(scrollToCommandData.mDestX, scrollToCommandData.mDestY);
        }
    }

    public void scrollToEnd(ReactHorizontalScrollView reactHorizontalScrollView, ReactScrollViewCommandHelper.ScrollToEndCommandData scrollToEndCommandData) {
        int width = reactHorizontalScrollView.getChildAt(0).getWidth() + reactHorizontalScrollView.getPaddingRight();
        if (scrollToEndCommandData.mAnimated) {
            reactHorizontalScrollView.reactSmoothScrollTo(width, reactHorizontalScrollView.getScrollY());
        } else {
            reactHorizontalScrollView.reactScrollTo(width, reactHorizontalScrollView.getScrollY());
        }
    }

    @ReactProp(customType = "Color", defaultInt = 0, name = "endFillColor")
    public void setBottomFillColor(ReactHorizontalScrollView reactHorizontalScrollView, int i) {
        reactHorizontalScrollView.setEndFillColor(i);
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
    public void setBorderRadius(ReactHorizontalScrollView reactHorizontalScrollView, int i, float f) {
        if (!YogaConstants.isUndefined(f)) {
            f = PixelUtil.toPixelFromDIP(f);
        }
        if (i == 0) {
            reactHorizontalScrollView.setBorderRadius(f);
        } else {
            reactHorizontalScrollView.setBorderRadius(f, i - 1);
        }
    }

    @ReactProp(name = "borderStyle")
    public void setBorderStyle(ReactHorizontalScrollView reactHorizontalScrollView, String str) {
        reactHorizontalScrollView.setBorderStyle(str);
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
    public void setBorderWidth(ReactHorizontalScrollView reactHorizontalScrollView, int i, float f) {
        if (!YogaConstants.isUndefined(f)) {
            f = PixelUtil.toPixelFromDIP(f);
        }
        reactHorizontalScrollView.setBorderWidth(SPACING_TYPES[i], f);
    }

    @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
    public void setBorderColor(ReactHorizontalScrollView reactHorizontalScrollView, int i, Integer num) {
        float f = Float.NaN;
        float intValue = num == null ? Float.NaN : (float) (num.intValue() & ViewCompat.MEASURED_SIZE_MASK);
        if (num != null) {
            f = (float) (num.intValue() >>> 24);
        }
        reactHorizontalScrollView.setBorderColor(SPACING_TYPES[i], intValue, f);
    }

    @ReactProp(name = "overflow")
    public void setOverflow(ReactHorizontalScrollView reactHorizontalScrollView, String str) {
        reactHorizontalScrollView.setOverflow(str);
    }

    @ReactProp(name = "persistentScrollbar")
    public void setPersistentScrollbar(ReactHorizontalScrollView reactHorizontalScrollView, boolean z) {
        reactHorizontalScrollView.setScrollbarFadingEnabled(!z);
    }

    @ReactProp(name = "fadingEdgeLength")
    public void setFadingEdgeLength(ReactHorizontalScrollView reactHorizontalScrollView, int i) {
        if (i > 0) {
            reactHorizontalScrollView.setHorizontalFadingEdgeEnabled(true);
            reactHorizontalScrollView.setFadingEdgeLength(i);
            return;
        }
        reactHorizontalScrollView.setHorizontalFadingEdgeEnabled(false);
        reactHorizontalScrollView.setFadingEdgeLength(0);
    }

    @ReactProp(name = "contentOffset")
    public void setContentOffset(ReactHorizontalScrollView reactHorizontalScrollView, ReadableMap readableMap) {
        if (readableMap != null) {
            double d = 0.0d;
            double d2 = readableMap.hasKey("x") ? readableMap.getDouble("x") : 0.0d;
            if (readableMap.hasKey("y")) {
                d = readableMap.getDouble("y");
            }
            reactHorizontalScrollView.reactScrollTo((int) PixelUtil.toPixelFromDIP(d2), (int) PixelUtil.toPixelFromDIP(d));
            return;
        }
        reactHorizontalScrollView.reactScrollTo(0, 0);
    }
}
