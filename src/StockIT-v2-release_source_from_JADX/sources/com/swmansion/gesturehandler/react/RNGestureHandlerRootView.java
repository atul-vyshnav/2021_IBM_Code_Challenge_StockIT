package com.swmansion.gesturehandler.react;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.views.view.ReactViewGroup;

public class RNGestureHandlerRootView extends ReactViewGroup {
    private boolean mEnabled;
    private RNGestureHandlerRootHelper mRootHelper;

    private static boolean hasGestureHandlerEnabledRootView(ViewGroup viewGroup) {
        UiThreadUtil.assertOnUiThread();
        for (ViewParent parent = viewGroup.getParent(); parent != null; parent = parent.getParent()) {
            if ((parent instanceof RNGestureHandlerEnabledRootView) || (parent instanceof RNGestureHandlerRootView)) {
                return true;
            }
        }
        return false;
    }

    public RNGestureHandlerRootView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean z = !hasGestureHandlerEnabledRootView(this);
        this.mEnabled = z;
        if (!z) {
            Log.i(ReactConstants.TAG, "[GESTURE HANDLER] Gesture handler is already enabled for a parent view");
        }
        if (this.mEnabled && this.mRootHelper == null) {
            this.mRootHelper = new RNGestureHandlerRootHelper((ReactContext) getContext(), this);
        }
    }

    public void tearDown() {
        RNGestureHandlerRootHelper rNGestureHandlerRootHelper = this.mRootHelper;
        if (rNGestureHandlerRootHelper != null) {
            rNGestureHandlerRootHelper.tearDown();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnabled || !((RNGestureHandlerRootHelper) Assertions.assertNotNull(this.mRootHelper)).dispatchTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.mEnabled) {
            ((RNGestureHandlerRootHelper) Assertions.assertNotNull(this.mRootHelper)).requestDisallowInterceptTouchEvent(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }
}
