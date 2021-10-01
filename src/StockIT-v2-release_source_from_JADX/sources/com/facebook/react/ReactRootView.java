package com.facebook.react;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.appregistry.AppRegistry;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactRoot;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.systrace.Systrace;

public class ReactRootView extends FrameLayout implements RootView, ReactRoot {
    private static final String TAG = "ReactRootView";
    private final ReactAndroidHWInputDeviceHelper mAndroidHWInputDeviceHelper;
    private Bundle mAppProperties;
    private CustomGlobalLayoutListener mCustomGlobalLayoutListener;
    private int mHeightMeasureSpec;
    private String mInitialUITemplate;
    /* access modifiers changed from: private */
    public boolean mIsAttachedToInstance;
    private String mJSModuleName;
    private JSTouchDispatcher mJSTouchDispatcher;
    /* access modifiers changed from: private */
    public int mLastHeight;
    private int mLastWidth;
    /* access modifiers changed from: private */
    public ReactInstanceManager mReactInstanceManager;
    private ReactRootViewEventListener mRootViewEventListener;
    private int mRootViewTag;
    private boolean mShouldLogContentAppeared;
    private int mUIManagerType;
    private final boolean mUseSurface;
    private boolean mWasMeasured;
    private int mWidthMeasureSpec;

    public interface ReactRootViewEventListener {
        void onAttachedToReactInstance(ReactRootView reactRootView);
    }

    public ViewGroup getRootViewGroup() {
        return this;
    }

    public ReactRootView(Context context) {
        super(context);
        this.mAndroidHWInputDeviceHelper = new ReactAndroidHWInputDeviceHelper(this);
        this.mWasMeasured = false;
        this.mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mLastWidth = 0;
        this.mLastHeight = 0;
        this.mUIManagerType = 1;
        this.mUseSurface = false;
        init();
    }

    public ReactRootView(Context context, boolean z) {
        super(context);
        this.mAndroidHWInputDeviceHelper = new ReactAndroidHWInputDeviceHelper(this);
        this.mWasMeasured = false;
        this.mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mLastWidth = 0;
        this.mLastHeight = 0;
        this.mUIManagerType = 1;
        this.mUseSurface = z;
        init();
    }

    public ReactRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAndroidHWInputDeviceHelper = new ReactAndroidHWInputDeviceHelper(this);
        this.mWasMeasured = false;
        this.mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mLastWidth = 0;
        this.mLastHeight = 0;
        this.mUIManagerType = 1;
        this.mUseSurface = false;
        init();
    }

    public ReactRootView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAndroidHWInputDeviceHelper = new ReactAndroidHWInputDeviceHelper(this);
        this.mWasMeasured = false;
        this.mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mLastWidth = 0;
        this.mLastHeight = 0;
        this.mUIManagerType = 1;
        this.mUseSurface = false;
        init();
    }

    private void init() {
        setClipChildren(false);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029 A[Catch:{ all -> 0x00b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0039 A[Catch:{ all -> 0x00b4 }, LOOP:0: B:19:0x0033->B:21:0x0039, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005d A[Catch:{ all -> 0x00b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006c A[Catch:{ all -> 0x00b4 }, LOOP:1: B:28:0x0066->B:30:0x006c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0097 A[Catch:{ all -> 0x00b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009b A[ADDED_TO_REGION, Catch:{ all -> 0x00b4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r11, int r12) {
        /*
            r10 = this;
            boolean r0 = r10.mUseSurface
            if (r0 == 0) goto L_0x0008
            super.onMeasure(r11, r12)
            return
        L_0x0008:
            r0 = 0
            java.lang.String r2 = "ReactRootView.onMeasure"
            com.facebook.systrace.Systrace.beginSection(r0, r2)
            int r2 = r10.mWidthMeasureSpec     // Catch:{ all -> 0x00b4 }
            r3 = 1
            r4 = 0
            if (r11 != r2) goto L_0x001c
            int r2 = r10.mHeightMeasureSpec     // Catch:{ all -> 0x00b4 }
            if (r12 == r2) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            r2 = 0
            goto L_0x001d
        L_0x001c:
            r2 = 1
        L_0x001d:
            r10.mWidthMeasureSpec = r11     // Catch:{ all -> 0x00b4 }
            r10.mHeightMeasureSpec = r12     // Catch:{ all -> 0x00b4 }
            int r5 = android.view.View.MeasureSpec.getMode(r11)     // Catch:{ all -> 0x00b4 }
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 == r6) goto L_0x0031
            if (r5 != 0) goto L_0x002c
            goto L_0x0031
        L_0x002c:
            int r11 = android.view.View.MeasureSpec.getSize(r11)     // Catch:{ all -> 0x00b4 }
            goto L_0x0057
        L_0x0031:
            r11 = 0
            r5 = 0
        L_0x0033:
            int r7 = r10.getChildCount()     // Catch:{ all -> 0x00b4 }
            if (r5 >= r7) goto L_0x0057
            android.view.View r7 = r10.getChildAt(r5)     // Catch:{ all -> 0x00b4 }
            int r8 = r7.getLeft()     // Catch:{ all -> 0x00b4 }
            int r9 = r7.getMeasuredWidth()     // Catch:{ all -> 0x00b4 }
            int r8 = r8 + r9
            int r9 = r7.getPaddingLeft()     // Catch:{ all -> 0x00b4 }
            int r8 = r8 + r9
            int r7 = r7.getPaddingRight()     // Catch:{ all -> 0x00b4 }
            int r8 = r8 + r7
            int r11 = java.lang.Math.max(r11, r8)     // Catch:{ all -> 0x00b4 }
            int r5 = r5 + 1
            goto L_0x0033
        L_0x0057:
            int r5 = android.view.View.MeasureSpec.getMode(r12)     // Catch:{ all -> 0x00b4 }
            if (r5 == r6) goto L_0x0065
            if (r5 != 0) goto L_0x0060
            goto L_0x0065
        L_0x0060:
            int r12 = android.view.View.MeasureSpec.getSize(r12)     // Catch:{ all -> 0x00b4 }
            goto L_0x008a
        L_0x0065:
            r12 = 0
        L_0x0066:
            int r5 = r10.getChildCount()     // Catch:{ all -> 0x00b4 }
            if (r4 >= r5) goto L_0x008a
            android.view.View r5 = r10.getChildAt(r4)     // Catch:{ all -> 0x00b4 }
            int r6 = r5.getTop()     // Catch:{ all -> 0x00b4 }
            int r7 = r5.getMeasuredHeight()     // Catch:{ all -> 0x00b4 }
            int r6 = r6 + r7
            int r7 = r5.getPaddingTop()     // Catch:{ all -> 0x00b4 }
            int r6 = r6 + r7
            int r5 = r5.getPaddingBottom()     // Catch:{ all -> 0x00b4 }
            int r6 = r6 + r5
            int r12 = java.lang.Math.max(r12, r6)     // Catch:{ all -> 0x00b4 }
            int r4 = r4 + 1
            goto L_0x0066
        L_0x008a:
            r10.setMeasuredDimension(r11, r12)     // Catch:{ all -> 0x00b4 }
            r10.mWasMeasured = r3     // Catch:{ all -> 0x00b4 }
            com.facebook.react.ReactInstanceManager r3 = r10.mReactInstanceManager     // Catch:{ all -> 0x00b4 }
            if (r3 == 0) goto L_0x009b
            boolean r3 = r10.mIsAttachedToInstance     // Catch:{ all -> 0x00b4 }
            if (r3 != 0) goto L_0x009b
            r10.attachToReactInstanceManager()     // Catch:{ all -> 0x00b4 }
            goto L_0x00ac
        L_0x009b:
            if (r2 != 0) goto L_0x00a5
            int r2 = r10.mLastWidth     // Catch:{ all -> 0x00b4 }
            if (r2 != r11) goto L_0x00a5
            int r2 = r10.mLastHeight     // Catch:{ all -> 0x00b4 }
            if (r2 == r12) goto L_0x00ac
        L_0x00a5:
            int r2 = r10.mWidthMeasureSpec     // Catch:{ all -> 0x00b4 }
            int r3 = r10.mHeightMeasureSpec     // Catch:{ all -> 0x00b4 }
            r10.updateRootLayoutSpecs(r2, r3)     // Catch:{ all -> 0x00b4 }
        L_0x00ac:
            r10.mLastWidth = r11     // Catch:{ all -> 0x00b4 }
            r10.mLastHeight = r12     // Catch:{ all -> 0x00b4 }
            com.facebook.systrace.Systrace.endSection(r0)
            return
        L_0x00b4:
            r11 = move-exception
            com.facebook.systrace.Systrace.endSection(r0)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.ReactRootView.onMeasure(int, int):void");
    }

    public void onChildStartedNativeGesture(MotionEvent motionEvent) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
            FLog.m104w(TAG, "Unable to dispatch touch to JS as the catalyst instance has not been attached");
        } else if (this.mJSTouchDispatcher == null) {
            FLog.m104w(TAG, "Unable to dispatch touch to JS before the dispatcher is available");
        } else {
            this.mJSTouchDispatcher.onChildStartedNativeGesture(motionEvent, ((UIManagerModule) this.mReactInstanceManager.getCurrentReactContext().getNativeModule(UIManagerModule.class)).getEventDispatcher());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        dispatchJSTouchEvent(motionEvent);
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        dispatchJSTouchEvent(motionEvent);
        super.onTouchEvent(motionEvent);
        return true;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (StackOverflowError e) {
            handleException(e);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
            FLog.m104w(TAG, "Unable to handle key event as the catalyst instance has not been attached");
            return super.dispatchKeyEvent(keyEvent);
        }
        this.mAndroidHWInputDeviceHelper.handleKeyEvent(keyEvent);
        return super.dispatchKeyEvent(keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
            FLog.m104w(TAG, "Unable to handle focus changed event as the catalyst instance has not been attached");
            super.onFocusChanged(z, i, rect);
            return;
        }
        this.mAndroidHWInputDeviceHelper.clearFocus();
        super.onFocusChanged(z, i, rect);
    }

    public void requestChildFocus(View view, View view2) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
            FLog.m104w(TAG, "Unable to handle child focus changed event as the catalyst instance has not been attached");
            super.requestChildFocus(view, view2);
            return;
        }
        this.mAndroidHWInputDeviceHelper.onFocusChanged(view2);
        super.requestChildFocus(view, view2);
    }

    private void dispatchJSTouchEvent(MotionEvent motionEvent) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
            FLog.m104w(TAG, "Unable to dispatch touch to JS as the catalyst instance has not been attached");
        } else if (this.mJSTouchDispatcher == null) {
            FLog.m104w(TAG, "Unable to dispatch touch to JS before the dispatcher is available");
        } else {
            this.mJSTouchDispatcher.handleTouchEvent(motionEvent, ((UIManagerModule) this.mReactInstanceManager.getCurrentReactContext().getNativeModule(UIManagerModule.class)).getEventDispatcher());
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(z);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mUseSurface) {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIsAttachedToInstance) {
            removeOnGlobalLayoutListener();
            getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsAttachedToInstance) {
            removeOnGlobalLayoutListener();
        }
    }

    private void removeOnGlobalLayoutListener() {
        getViewTreeObserver().removeOnGlobalLayoutListener(getCustomGlobalLayoutListener());
    }

    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (this.mShouldLogContentAppeared) {
            this.mShouldLogContentAppeared = false;
            if (this.mJSModuleName != null) {
                ReactMarker.logMarker(ReactMarkerConstants.CONTENT_APPEARED, this.mJSModuleName, this.mRootViewTag);
            }
        }
    }

    public void startReactApplication(ReactInstanceManager reactInstanceManager, String str) {
        startReactApplication(reactInstanceManager, str, (Bundle) null);
    }

    public void startReactApplication(ReactInstanceManager reactInstanceManager, String str, Bundle bundle) {
        startReactApplication(reactInstanceManager, str, bundle, (String) null);
    }

    public void startReactApplication(ReactInstanceManager reactInstanceManager, String str, Bundle bundle, String str2) {
        Systrace.beginSection(0, "startReactApplication");
        try {
            UiThreadUtil.assertOnUiThread();
            Assertions.assertCondition(this.mReactInstanceManager == null, "This root view has already been attached to a catalyst instance manager");
            this.mReactInstanceManager = reactInstanceManager;
            this.mJSModuleName = str;
            this.mAppProperties = bundle;
            this.mInitialUITemplate = str2;
            reactInstanceManager.createReactContextInBackground();
            attachToReactInstanceManager();
        } finally {
            Systrace.endSection(0);
        }
    }

    public int getWidthMeasureSpec() {
        return this.mWidthMeasureSpec;
    }

    public int getHeightMeasureSpec() {
        return this.mHeightMeasureSpec;
    }

    public void setShouldLogContentAppeared(boolean z) {
        this.mShouldLogContentAppeared = z;
    }

    public String getSurfaceID() {
        Bundle appProperties = getAppProperties();
        if (appProperties != null) {
            return appProperties.getString("surfaceID");
        }
        return null;
    }

    private void updateRootLayoutSpecs(int i, int i2) {
        UIManager uIManager;
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null) {
            FLog.m104w(TAG, "Unable to update root layout specs for uninitialized ReactInstanceManager");
            return;
        }
        ReactContext currentReactContext = reactInstanceManager.getCurrentReactContext();
        if (currentReactContext != null && (uIManager = UIManagerHelper.getUIManager(currentReactContext, getUIManagerType())) != null) {
            uIManager.updateRootLayoutSpecs(getRootViewTag(), i, i2);
        }
    }

    public void unmountReactApplication() {
        UiThreadUtil.assertOnUiThread();
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager != null && this.mIsAttachedToInstance) {
            reactInstanceManager.detachRootView(this);
            this.mIsAttachedToInstance = false;
        }
        this.mReactInstanceManager = null;
        this.mShouldLogContentAppeared = false;
    }

    public void onStage(int i) {
        if (i == 101) {
            onAttachedToReactInstance();
        }
    }

    public void onAttachedToReactInstance() {
        this.mJSTouchDispatcher = new JSTouchDispatcher(this);
        ReactRootViewEventListener reactRootViewEventListener = this.mRootViewEventListener;
        if (reactRootViewEventListener != null) {
            reactRootViewEventListener.onAttachedToReactInstance(this);
        }
    }

    public void setEventListener(ReactRootViewEventListener reactRootViewEventListener) {
        this.mRootViewEventListener = reactRootViewEventListener;
    }

    public String getJSModuleName() {
        return (String) Assertions.assertNotNull(this.mJSModuleName);
    }

    public Bundle getAppProperties() {
        return this.mAppProperties;
    }

    public String getInitialUITemplate() {
        return this.mInitialUITemplate;
    }

    public void setAppProperties(Bundle bundle) {
        UiThreadUtil.assertOnUiThread();
        this.mAppProperties = bundle;
        if (getRootViewTag() != 0) {
            runApplication();
        }
    }

    public void runApplication() {
        Systrace.beginSection(0, "ReactRootView.runApplication");
        try {
            if (this.mReactInstanceManager != null) {
                if (this.mIsAttachedToInstance) {
                    ReactContext currentReactContext = this.mReactInstanceManager.getCurrentReactContext();
                    if (currentReactContext == null) {
                        Systrace.endSection(0);
                        return;
                    }
                    CatalystInstance catalystInstance = currentReactContext.getCatalystInstance();
                    String jSModuleName = getJSModuleName();
                    if (!this.mUseSurface) {
                        if (this.mWasMeasured) {
                            updateRootLayoutSpecs(this.mWidthMeasureSpec, this.mHeightMeasureSpec);
                        }
                        WritableNativeMap writableNativeMap = new WritableNativeMap();
                        writableNativeMap.putDouble("rootTag", (double) getRootViewTag());
                        Bundle appProperties = getAppProperties();
                        if (appProperties != null) {
                            writableNativeMap.putMap("initialProps", Arguments.fromBundle(appProperties));
                        }
                        this.mShouldLogContentAppeared = true;
                        FLog.m64e(TAG, "runApplication: call AppRegistry.runApplication");
                        ((AppRegistry) catalystInstance.getJSModule(AppRegistry.class)).runApplication(jSModuleName, writableNativeMap);
                    }
                    Systrace.endSection(0);
                }
            }
        } finally {
            Systrace.endSection(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void simulateAttachForTesting() {
        this.mIsAttachedToInstance = true;
        this.mJSTouchDispatcher = new JSTouchDispatcher(this);
    }

    private CustomGlobalLayoutListener getCustomGlobalLayoutListener() {
        if (this.mCustomGlobalLayoutListener == null) {
            this.mCustomGlobalLayoutListener = new CustomGlobalLayoutListener();
        }
        return this.mCustomGlobalLayoutListener;
    }

    private void attachToReactInstanceManager() {
        Systrace.beginSection(0, "attachToReactInstanceManager");
        if (!this.mIsAttachedToInstance) {
            try {
                this.mIsAttachedToInstance = true;
                ((ReactInstanceManager) Assertions.assertNotNull(this.mReactInstanceManager)).attachRootView(this);
                getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener());
            } finally {
                Systrace.endSection(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        Assertions.assertCondition(!this.mIsAttachedToInstance, "The application this ReactRootView was rendering was not unmounted before the ReactRootView was garbage collected. This usually means that your application is leaking large amounts of memory. To solve this, make sure to call ReactRootView#unmountReactApplication in the onDestroy() of your hosting Activity or in the onDestroyView() of your hosting Fragment.");
    }

    public int getRootViewTag() {
        return this.mRootViewTag;
    }

    public void setRootViewTag(int i) {
        this.mRootViewTag = i;
    }

    public void handleException(Throwable th) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager == null || reactInstanceManager.getCurrentReactContext() == null) {
            throw new RuntimeException(th);
        }
        this.mReactInstanceManager.getCurrentReactContext().handleException(new IllegalViewOperationException(th.getMessage(), this, th));
    }

    public void setIsFabric(boolean z) {
        this.mUIManagerType = z ? 2 : 1;
    }

    public int getUIManagerType() {
        return this.mUIManagerType;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return this.mReactInstanceManager;
    }

    /* access modifiers changed from: package-private */
    public void sendEvent(String str, WritableMap writableMap) {
        ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
        if (reactInstanceManager != null) {
            ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
        }
    }

    private class CustomGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private int mDeviceRotation = 0;
        private int mKeyboardHeight = 0;
        private final int mMinKeyboardHeightDetected;
        private final Rect mVisibleViewArea;

        CustomGlobalLayoutListener() {
            DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(ReactRootView.this.getContext().getApplicationContext());
            this.mVisibleViewArea = new Rect();
            this.mMinKeyboardHeightDetected = (int) PixelUtil.toPixelFromDIP(60.0f);
        }

        public void onGlobalLayout() {
            if (ReactRootView.this.mReactInstanceManager != null && ReactRootView.this.mIsAttachedToInstance && ReactRootView.this.mReactInstanceManager.getCurrentReactContext() != null) {
                checkForKeyboardEvents();
                checkForDeviceOrientationChanges();
                checkForDeviceDimensionsChanges();
            }
        }

        private void checkForKeyboardEvents() {
            ReactRootView.this.getRootView().getWindowVisibleDisplayFrame(this.mVisibleViewArea);
            int i = DisplayMetricsHolder.getWindowDisplayMetrics().heightPixels - this.mVisibleViewArea.bottom;
            boolean z = true;
            if (this.mKeyboardHeight != i && i > this.mMinKeyboardHeightDetected) {
                this.mKeyboardHeight = i;
                ReactRootView.this.sendEvent("keyboardDidShow", createKeyboardEventPayload((double) PixelUtil.toDIPFromPixel((float) this.mVisibleViewArea.bottom), (double) PixelUtil.toDIPFromPixel((float) this.mVisibleViewArea.left), (double) PixelUtil.toDIPFromPixel((float) this.mVisibleViewArea.width()), (double) PixelUtil.toDIPFromPixel((float) this.mKeyboardHeight)));
                return;
            }
            if (this.mKeyboardHeight == 0 || i > this.mMinKeyboardHeightDetected) {
                z = false;
            }
            if (z) {
                this.mKeyboardHeight = 0;
                ReactRootView reactRootView = ReactRootView.this;
                reactRootView.sendEvent("keyboardDidHide", createKeyboardEventPayload((double) PixelUtil.toDIPFromPixel((float) reactRootView.mLastHeight), 0.0d, (double) PixelUtil.toDIPFromPixel((float) this.mVisibleViewArea.width()), 0.0d));
            }
        }

        private void checkForDeviceOrientationChanges() {
            int rotation = ((WindowManager) ReactRootView.this.getContext().getSystemService("window")).getDefaultDisplay().getRotation();
            if (this.mDeviceRotation != rotation) {
                this.mDeviceRotation = rotation;
                DisplayMetricsHolder.initDisplayMetrics(ReactRootView.this.getContext().getApplicationContext());
                emitOrientationChanged(rotation);
            }
        }

        private void checkForDeviceDimensionsChanges() {
            emitUpdateDimensionsEvent();
        }

        private void emitOrientationChanged(int i) {
            String str;
            double d;
            double d2;
            boolean z = true;
            if (i != 0) {
                if (i == 1) {
                    d = -90.0d;
                    str = "landscape-primary";
                } else if (i == 2) {
                    d2 = 180.0d;
                    str = "portrait-secondary";
                } else if (i == 3) {
                    d = 90.0d;
                    str = "landscape-secondary";
                } else {
                    return;
                }
                WritableMap createMap = Arguments.createMap();
                createMap.putString("name", str);
                createMap.putDouble("rotationDegrees", d);
                createMap.putBoolean("isLandscape", z);
                ReactRootView.this.sendEvent("namedOrientationDidChange", createMap);
            }
            d2 = 0.0d;
            str = "portrait-primary";
            d = d2;
            z = false;
            WritableMap createMap2 = Arguments.createMap();
            createMap2.putString("name", str);
            createMap2.putDouble("rotationDegrees", d);
            createMap2.putBoolean("isLandscape", z);
            ReactRootView.this.sendEvent("namedOrientationDidChange", createMap2);
        }

        private void emitUpdateDimensionsEvent() {
            ((DeviceInfoModule) ReactRootView.this.mReactInstanceManager.getCurrentReactContext().getNativeModule(DeviceInfoModule.class)).emitUpdateDimensionsEvent();
        }

        private WritableMap createKeyboardEventPayload(double d, double d2, double d3, double d4) {
            WritableMap createMap = Arguments.createMap();
            WritableMap createMap2 = Arguments.createMap();
            createMap2.putDouble("height", d4);
            createMap2.putDouble("screenX", d2);
            createMap2.putDouble("width", d3);
            createMap2.putDouble("screenY", d);
            createMap.putMap("endCoordinates", createMap2);
            createMap.putString("easing", "keyboard");
            createMap.putDouble("duration", 0.0d);
            return createMap;
        }
    }
}
