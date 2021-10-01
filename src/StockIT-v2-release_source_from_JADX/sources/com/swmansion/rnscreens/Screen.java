package com.swmansion.rnscreens;

import android.graphics.Paint;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;

public class Screen extends ViewGroup {
    /* access modifiers changed from: private */
    public static View.OnAttachStateChangeListener sShowSoftKeyboardOnAttach = new View.OnAttachStateChangeListener() {
        public void onViewDetachedFromWindow(View view) {
        }

        public void onViewAttachedToWindow(View view) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
            view.removeOnAttachStateChangeListener(Screen.sShowSoftKeyboardOnAttach);
        }
    };
    private ActivityState mActivityState;
    private ScreenContainer mContainer;
    private ScreenFragment mFragment;
    private boolean mGestureEnabled = true;
    private ReplaceAnimation mReplaceAnimation = ReplaceAnimation.POP;
    private StackAnimation mStackAnimation = StackAnimation.DEFAULT;
    private StackPresentation mStackPresentation = StackPresentation.PUSH;
    private boolean mTransitioning;

    public enum ActivityState {
        INACTIVE,
        TRANSITIONING_OR_BELOW_TOP,
        ON_TOP
    }

    public enum ReplaceAnimation {
        PUSH,
        POP
    }

    public enum StackAnimation {
        DEFAULT,
        NONE,
        FADE,
        SLIDE_FROM_RIGHT,
        SLIDE_FROM_LEFT
    }

    public enum StackPresentation {
        PUSH,
        MODAL,
        TRANSPARENT_MODAL
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
    }

    public void setLayerType(int i, Paint paint) {
    }

    /* access modifiers changed from: protected */
    public void onAnimationStart() {
        super.onAnimationStart();
        ScreenFragment screenFragment = this.mFragment;
        if (screenFragment != null) {
            screenFragment.onViewAnimationStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onAnimationEnd() {
        super.onAnimationEnd();
        ScreenFragment screenFragment = this.mFragment;
        if (screenFragment != null) {
            screenFragment.onViewAnimationEnd();
        }
    }

    public Screen(ReactContext reactContext) {
        super(reactContext);
        setLayoutParams(new WindowManager.LayoutParams(2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            final int i5 = i3 - i;
            final int i6 = i4 - i2;
            ReactContext reactContext = (ReactContext) getContext();
            final ReactContext reactContext2 = reactContext;
            reactContext.runOnNativeModulesQueueThread(new GuardedRunnable(reactContext) {
                public void runGuarded() {
                    ((UIManagerModule) reactContext2.getNativeModule(UIManagerModule.class)).updateNodeSize(Screen.this.getId(), i5, i6);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            while (focusedChild instanceof ViewGroup) {
                focusedChild = ((ViewGroup) focusedChild).getFocusedChild();
            }
            if (focusedChild instanceof TextView) {
                TextView textView = (TextView) focusedChild;
                if (textView.getShowSoftInputOnFocus()) {
                    textView.addOnAttachStateChangeListener(sShowSoftKeyboardOnAttach);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ScreenStackHeaderConfig getHeaderConfig() {
        View childAt = getChildAt(0);
        if (childAt instanceof ScreenStackHeaderConfig) {
            return (ScreenStackHeaderConfig) childAt;
        }
        return null;
    }

    public void setTransitioning(boolean z) {
        if (this.mTransitioning != z) {
            this.mTransitioning = z;
            boolean hasWebView = hasWebView(this);
            int i = 2;
            if (!hasWebView || getLayerType() == 2) {
                if (!z || hasWebView) {
                    i = 0;
                }
                super.setLayerType(i, (Paint) null);
            }
        }
    }

    private boolean hasWebView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof WebView) {
                return true;
            }
            if ((childAt instanceof ViewGroup) && hasWebView((ViewGroup) childAt)) {
                return true;
            }
        }
        return false;
    }

    public void setStackPresentation(StackPresentation stackPresentation) {
        this.mStackPresentation = stackPresentation;
    }

    public void setStackAnimation(StackAnimation stackAnimation) {
        this.mStackAnimation = stackAnimation;
    }

    public void setReplaceAnimation(ReplaceAnimation replaceAnimation) {
        this.mReplaceAnimation = replaceAnimation;
    }

    public void setGestureEnabled(boolean z) {
        this.mGestureEnabled = z;
    }

    public StackAnimation getStackAnimation() {
        return this.mStackAnimation;
    }

    public ReplaceAnimation getReplaceAnimation() {
        return this.mReplaceAnimation;
    }

    public StackPresentation getStackPresentation() {
        return this.mStackPresentation;
    }

    /* access modifiers changed from: protected */
    public void setContainer(ScreenContainer screenContainer) {
        this.mContainer = screenContainer;
    }

    /* access modifiers changed from: protected */
    public void setFragment(ScreenFragment screenFragment) {
        this.mFragment = screenFragment;
    }

    /* access modifiers changed from: protected */
    public ScreenFragment getFragment() {
        return this.mFragment;
    }

    /* access modifiers changed from: protected */
    public ScreenContainer getContainer() {
        return this.mContainer;
    }

    public void setActivityState(ActivityState activityState) {
        if (activityState != this.mActivityState) {
            this.mActivityState = activityState;
            ScreenContainer screenContainer = this.mContainer;
            if (screenContainer != null) {
                screenContainer.notifyChildUpdate();
            }
        }
    }

    public ActivityState getActivityState() {
        return this.mActivityState;
    }

    public boolean isGestureEnabled() {
        return this.mGestureEnabled;
    }
}
