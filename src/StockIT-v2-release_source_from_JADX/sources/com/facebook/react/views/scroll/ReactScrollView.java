package com.facebook.react.views.scroll;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.view.ViewCompat;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import java.lang.reflect.Field;
import java.util.List;

public class ReactScrollView extends ScrollView implements ReactClippingViewGroup, ViewGroup.OnHierarchyChangeListener, View.OnLayoutChangeListener {
    private static final String CONTENT_OFFSET_LEFT = "contentOffsetLeft";
    private static final String CONTENT_OFFSET_TOP = "contentOffsetTop";
    private static final int UNSET_CONTENT_OFFSET = -1;
    private static Field sScrollerField = null;
    private static boolean sTriedToGetScrollerField = false;
    /* access modifiers changed from: private */
    public boolean mActivelyScrolling;
    private Rect mClippingRect;
    private View mContentView;
    private float mDecelerationRate;
    private boolean mDisableIntervalMomentum;
    private boolean mDragging;
    private Drawable mEndBackground;
    private int mEndFillColor;
    private FpsListener mFpsListener;
    private final OnScrollDispatchHelper mOnScrollDispatchHelper;
    private String mOverflow;
    /* access modifiers changed from: private */
    public boolean mPagingEnabled;
    /* access modifiers changed from: private */
    public Runnable mPostTouchRunnable;
    private ReactViewBackgroundManager mReactBackgroundManager;
    private final Rect mRect;
    private boolean mRemoveClippedSubviews;
    private boolean mScrollEnabled;
    private String mScrollPerfTag;
    private final OverScroller mScroller;
    /* access modifiers changed from: private */
    public boolean mSendMomentumEvents;
    private int mSnapInterval;
    private List<Integer> mSnapOffsets;
    private boolean mSnapToEnd;
    private boolean mSnapToStart;
    private StateWrapper mStateWrapper;
    private final VelocityHelper mVelocityHelper;
    private int pendingContentOffsetX;
    private int pendingContentOffsetY;

    public ReactScrollView(ReactContext reactContext) {
        this(reactContext, (FpsListener) null);
    }

    public ReactScrollView(ReactContext reactContext, FpsListener fpsListener) {
        super(reactContext);
        this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
        this.mVelocityHelper = new VelocityHelper();
        this.mRect = new Rect();
        this.mOverflow = "hidden";
        this.mPagingEnabled = false;
        this.mScrollEnabled = true;
        this.mFpsListener = null;
        this.mEndFillColor = 0;
        this.mDisableIntervalMomentum = false;
        this.mSnapInterval = 0;
        this.mDecelerationRate = 0.985f;
        this.mSnapToStart = true;
        this.mSnapToEnd = true;
        this.pendingContentOffsetX = -1;
        this.pendingContentOffsetY = -1;
        this.mFpsListener = fpsListener;
        this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
        this.mScroller = getOverScrollerFromParent();
        setOnHierarchyChangeListener(this);
        setScrollBarStyle(33554432);
    }

    private OverScroller getOverScrollerFromParent() {
        if (!sTriedToGetScrollerField) {
            sTriedToGetScrollerField = true;
            try {
                Field declaredField = ScrollView.class.getDeclaredField("mScroller");
                sScrollerField = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                FLog.m104w(ReactConstants.TAG, "Failed to get mScroller field for ScrollView! This app will exhibit the bounce-back scrolling bug :(");
            }
        }
        Field field = sScrollerField;
        if (field == null) {
            return null;
        }
        try {
            Object obj = field.get(this);
            if (obj instanceof OverScroller) {
                return (OverScroller) obj;
            }
            FLog.m104w(ReactConstants.TAG, "Failed to cast mScroller field in ScrollView (probably due to OEM changes to AOSP)! This app will exhibit the bounce-back scrolling bug :(");
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get mScroller from ScrollView!", e);
        }
    }

    public void setDisableIntervalMomentum(boolean z) {
        this.mDisableIntervalMomentum = z;
    }

    public void setSendMomentumEvents(boolean z) {
        this.mSendMomentumEvents = z;
    }

    public void setScrollPerfTag(String str) {
        this.mScrollPerfTag = str;
    }

    public void setScrollEnabled(boolean z) {
        this.mScrollEnabled = z;
    }

    public void setPagingEnabled(boolean z) {
        this.mPagingEnabled = z;
    }

    public void setDecelerationRate(float f) {
        this.mDecelerationRate = f;
        OverScroller overScroller = this.mScroller;
        if (overScroller != null) {
            overScroller.setFriction(1.0f - f);
        }
    }

    public void setSnapInterval(int i) {
        this.mSnapInterval = i;
    }

    public void setSnapOffsets(List<Integer> list) {
        this.mSnapOffsets = list;
    }

    public void setSnapToStart(boolean z) {
        this.mSnapToStart = z;
    }

    public void setSnapToEnd(boolean z) {
        this.mSnapToEnd = z;
    }

    public void flashScrollIndicators() {
        awakenScrollBars();
    }

    public void setOverflow(String str) {
        this.mOverflow = str;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(i, i2);
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = this.pendingContentOffsetX;
        if (i5 == -1) {
            i5 = getScrollX();
        }
        int i6 = this.pendingContentOffsetY;
        if (i6 == -1) {
            i6 = getScrollY();
        }
        reactScrollTo(i5, i6);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (view2 != null) {
            scrollToChild(view2);
        }
        super.requestChildFocus(view, view2);
    }

    private void scrollToChild(View view) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(view, rect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mActivelyScrolling = true;
        if (this.mOnScrollDispatchHelper.onScrollChanged(i, i2)) {
            if (this.mRemoveClippedSubviews) {
                updateClippingRect();
            }
            ReactScrollViewHelper.emitScrollEvent(this, this.mOnScrollDispatchHelper.getXFlingVelocity(), this.mOnScrollDispatchHelper.getYFlingVelocity());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        try {
            if (super.onInterceptTouchEvent(motionEvent)) {
                NativeGestureUtil.notifyNativeGestureStarted(this, motionEvent);
                ReactScrollViewHelper.emitScrollBeginDragEvent(this);
                this.mDragging = true;
                enableFpsListener();
                return true;
            }
        } catch (IllegalArgumentException e) {
            FLog.m105w(ReactConstants.TAG, "Error intercepting touch event.", (Throwable) e);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        this.mVelocityHelper.calculateVelocity(motionEvent);
        if ((motionEvent.getAction() & 255) == 1 && this.mDragging) {
            updateStateOnScroll(getScrollX(), getScrollY());
            float xVelocity = this.mVelocityHelper.getXVelocity();
            float yVelocity = this.mVelocityHelper.getYVelocity();
            ReactScrollViewHelper.emitScrollEndDragEvent(this, xVelocity, yVelocity);
            this.mDragging = false;
            handlePostTouchScrolling(Math.round(xVelocity), Math.round(yVelocity));
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (this.mScrollEnabled || (keyCode != 19 && keyCode != 20)) {
            return super.executeKeyEvent(keyEvent);
        }
        return false;
    }

    public void setRemoveClippedSubviews(boolean z) {
        if (z && this.mClippingRect == null) {
            this.mClippingRect = new Rect();
        }
        this.mRemoveClippedSubviews = z;
        updateClippingRect();
    }

    public boolean getRemoveClippedSubviews() {
        return this.mRemoveClippedSubviews;
    }

    public void updateClippingRect() {
        if (this.mRemoveClippedSubviews) {
            Assertions.assertNotNull(this.mClippingRect);
            ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
            View childAt = getChildAt(0);
            if (childAt instanceof ReactClippingViewGroup) {
                ((ReactClippingViewGroup) childAt).updateClippingRect();
            }
        }
    }

    public void getClippingRect(Rect rect) {
        rect.set((Rect) Assertions.assertNotNull(this.mClippingRect));
    }

    public boolean getChildVisibleRect(View view, Rect rect, Point point) {
        if (ReactFeatureFlags.clipChildRectsIfOverflowIsHidden) {
            return ReactClippingViewGroupHelper.getChildVisibleRectHelper(view, rect, point, this, this.mOverflow);
        }
        return super.getChildVisibleRect(view, rect, point);
    }

    public void fling(int i) {
        float signum = Math.signum(this.mOnScrollDispatchHelper.getYFlingVelocity());
        if (signum == 0.0f) {
            signum = Math.signum((float) i);
        }
        int abs = (int) (((float) Math.abs(i)) * signum);
        if (this.mPagingEnabled) {
            flingAndSnap(abs);
        } else if (this.mScroller != null) {
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            this.mScroller.fling(getScrollX(), getScrollY(), 0, abs, 0, 0, 0, Integer.MAX_VALUE, 0, height / 2);
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            super.fling(abs);
        }
        handlePostTouchScrolling(0, abs);
    }

    private void enableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.enable(this.mScrollPerfTag);
        }
    }

    /* access modifiers changed from: private */
    public void disableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.disable(this.mScrollPerfTag);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.mScrollPerfTag;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isScrollPerfLoggingEnabled() {
        /*
            r1 = this;
            com.facebook.react.views.scroll.FpsListener r0 = r1.mFpsListener
            if (r0 == 0) goto L_0x0010
            java.lang.String r0 = r1.mScrollPerfTag
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.scroll.ReactScrollView.isScrollPerfLoggingEnabled():boolean");
    }

    private int getMaxScrollY() {
        return Math.max(0, this.mContentView.getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
    }

    public void draw(Canvas canvas) {
        char c = 0;
        if (this.mEndFillColor != 0) {
            View childAt = getChildAt(0);
            if (!(this.mEndBackground == null || childAt == null || childAt.getBottom() >= getHeight())) {
                this.mEndBackground.setBounds(0, childAt.getBottom(), getWidth(), getHeight());
                this.mEndBackground.draw(canvas);
            }
        }
        getDrawingRect(this.mRect);
        String str = this.mOverflow;
        if (str.hashCode() != 466743410 || !str.equals(ViewProps.VISIBLE)) {
            c = 65535;
        }
        if (c != 0) {
            canvas.clipRect(this.mRect);
        }
        super.draw(canvas);
    }

    private void handlePostTouchScrolling(int i, int i2) {
        if ((this.mSendMomentumEvents || this.mPagingEnabled || isScrollPerfLoggingEnabled()) && this.mPostTouchRunnable == null) {
            if (this.mSendMomentumEvents) {
                enableFpsListener();
                ReactScrollViewHelper.emitScrollMomentumBeginEvent(this, i, i2);
            }
            this.mActivelyScrolling = false;
            C13141 r3 = new Runnable() {
                private boolean mSnappingToPage = false;

                public void run() {
                    if (ReactScrollView.this.mActivelyScrolling) {
                        boolean unused = ReactScrollView.this.mActivelyScrolling = false;
                        ViewCompat.postOnAnimationDelayed(ReactScrollView.this, this, 20);
                        return;
                    }
                    ReactScrollView reactScrollView = ReactScrollView.this;
                    reactScrollView.updateStateOnScroll(reactScrollView.getScrollX(), ReactScrollView.this.getScrollY());
                    if (!ReactScrollView.this.mPagingEnabled || this.mSnappingToPage) {
                        if (ReactScrollView.this.mSendMomentumEvents) {
                            ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactScrollView.this);
                        }
                        Runnable unused2 = ReactScrollView.this.mPostTouchRunnable = null;
                        ReactScrollView.this.disableFpsListener();
                        return;
                    }
                    this.mSnappingToPage = true;
                    ReactScrollView.this.flingAndSnap(0);
                    ViewCompat.postOnAnimationDelayed(ReactScrollView.this, this, 20);
                }
            };
            this.mPostTouchRunnable = r3;
            ViewCompat.postOnAnimationDelayed(this, r3, 20);
        }
    }

    private int predictFinalScrollPosition(int i) {
        OverScroller overScroller = new OverScroller(getContext());
        overScroller.setFriction(1.0f - this.mDecelerationRate);
        int maxScrollY = getMaxScrollY();
        int height = ((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2;
        overScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, 0, maxScrollY, 0, height);
        return overScroller.getFinalY();
    }

    private void smoothScrollAndSnap(int i) {
        double snapInterval = (double) getSnapInterval();
        double scrollY = (double) getScrollY();
        double d = scrollY / snapInterval;
        int floor = (int) Math.floor(d);
        int ceil = (int) Math.ceil(d);
        int round = (int) Math.round(d);
        int round2 = (int) Math.round(((double) predictFinalScrollPosition(i)) / snapInterval);
        if (i > 0 && ceil == floor) {
            ceil++;
        } else if (i < 0 && floor == ceil) {
            floor--;
        }
        if (i > 0 && round < ceil && round2 > floor) {
            round = ceil;
        } else if (i < 0 && round > floor && round2 < ceil) {
            round = floor;
        }
        double d2 = ((double) round) * snapInterval;
        if (d2 != scrollY) {
            this.mActivelyScrolling = true;
            reactSmoothScrollTo(getScrollX(), (int) d2);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c0, code lost:
        if (getScrollY() <= r4) goto L_0x00c2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0118  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void flingAndSnap(int r19) {
        /*
            r18 = this;
            r0 = r18
            int r1 = r18.getChildCount()
            if (r1 > 0) goto L_0x0009
            return
        L_0x0009:
            int r1 = r0.mSnapInterval
            if (r1 != 0) goto L_0x0015
            java.util.List<java.lang.Integer> r1 = r0.mSnapOffsets
            if (r1 != 0) goto L_0x0015
            r18.smoothScrollAndSnap(r19)
            return
        L_0x0015:
            int r1 = r18.getMaxScrollY()
            int r2 = r18.predictFinalScrollPosition(r19)
            boolean r3 = r0.mDisableIntervalMomentum
            if (r3 == 0) goto L_0x0025
            int r2 = r18.getScrollY()
        L_0x0025:
            int r3 = r18.getHeight()
            int r4 = r18.getPaddingBottom()
            int r3 = r3 - r4
            int r4 = r18.getPaddingTop()
            int r3 = r3 - r4
            java.util.List<java.lang.Integer> r4 = r0.mSnapOffsets
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0080
            java.lang.Object r4 = r4.get(r6)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            java.util.List<java.lang.Integer> r7 = r0.mSnapOffsets
            int r8 = r7.size()
            int r8 = r8 - r5
            java.lang.Object r7 = r7.get(r8)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r10 = r1
            r8 = 0
            r9 = 0
        L_0x0057:
            java.util.List<java.lang.Integer> r11 = r0.mSnapOffsets
            int r11 = r11.size()
            if (r8 >= r11) goto L_0x009c
            java.util.List<java.lang.Integer> r11 = r0.mSnapOffsets
            java.lang.Object r11 = r11.get(r8)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            if (r11 > r2) goto L_0x0074
            int r12 = r2 - r11
            int r13 = r2 - r9
            if (r12 >= r13) goto L_0x0074
            r9 = r11
        L_0x0074:
            if (r11 < r2) goto L_0x007d
            int r12 = r11 - r2
            int r13 = r10 - r2
            if (r12 >= r13) goto L_0x007d
            r10 = r11
        L_0x007d:
            int r8 = r8 + 1
            goto L_0x0057
        L_0x0080:
            int r4 = r18.getSnapInterval()
            double r7 = (double) r4
            double r9 = (double) r2
            double r9 = r9 / r7
            double r11 = java.lang.Math.floor(r9)
            double r11 = r11 * r7
            int r4 = (int) r11
            double r9 = java.lang.Math.ceil(r9)
            double r9 = r9 * r7
            int r7 = (int) r9
            int r10 = java.lang.Math.min(r7, r1)
            r7 = r1
            r9 = r4
            r4 = 0
        L_0x009c:
            int r8 = r2 - r9
            int r11 = r10 - r2
            if (r8 >= r11) goto L_0x00a4
            r12 = r9
            goto L_0x00a5
        L_0x00a4:
            r12 = r10
        L_0x00a5:
            boolean r13 = r0.mSnapToEnd
            if (r13 != 0) goto L_0x00b6
            if (r2 < r7) goto L_0x00b6
            int r4 = r18.getScrollY()
            if (r4 < r7) goto L_0x00b2
            goto L_0x00c2
        L_0x00b2:
            r2 = r19
            r4 = r7
            goto L_0x00df
        L_0x00b6:
            boolean r7 = r0.mSnapToStart
            if (r7 != 0) goto L_0x00c6
            if (r2 > r4) goto L_0x00c6
            int r7 = r18.getScrollY()
            if (r7 > r4) goto L_0x00c3
        L_0x00c2:
            r4 = r2
        L_0x00c3:
            r2 = r19
            goto L_0x00df
        L_0x00c6:
            r13 = 4621819117588971520(0x4024000000000000, double:10.0)
            if (r19 <= 0) goto L_0x00d2
            double r7 = (double) r11
            double r7 = r7 * r13
            int r2 = (int) r7
            int r2 = r19 + r2
            r4 = r10
            goto L_0x00df
        L_0x00d2:
            if (r19 >= 0) goto L_0x00dc
            double r7 = (double) r8
            double r7 = r7 * r13
            int r2 = (int) r7
            int r2 = r19 - r2
            r4 = r9
            goto L_0x00df
        L_0x00dc:
            r2 = r19
            r4 = r12
        L_0x00df:
            int r4 = java.lang.Math.max(r6, r4)
            int r15 = java.lang.Math.min(r4, r1)
            android.widget.OverScroller r7 = r0.mScroller
            if (r7 == 0) goto L_0x0118
            r0.mActivelyScrolling = r5
            int r8 = r18.getScrollX()
            int r9 = r18.getScrollY()
            r10 = 0
            if (r2 == 0) goto L_0x00f9
            goto L_0x00ff
        L_0x00f9:
            int r2 = r18.getScrollY()
            int r2 = r15 - r2
        L_0x00ff:
            r11 = r2
            r12 = 0
            r13 = 0
            r16 = 0
            if (r15 == 0) goto L_0x010c
            if (r15 != r1) goto L_0x0109
            goto L_0x010c
        L_0x0109:
            r17 = 0
            goto L_0x0110
        L_0x010c:
            int r6 = r3 / 2
            r17 = r6
        L_0x0110:
            r14 = r15
            r7.fling(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            r18.postInvalidateOnAnimation()
            goto L_0x011f
        L_0x0118:
            int r1 = r18.getScrollX()
            r0.reactSmoothScrollTo(r1, r15)
        L_0x011f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.scroll.ReactScrollView.flingAndSnap(int):void");
    }

    private int getSnapInterval() {
        int i = this.mSnapInterval;
        if (i != 0) {
            return i;
        }
        return getHeight();
    }

    public void setEndFillColor(int i) {
        if (i != this.mEndFillColor) {
            this.mEndFillColor = i;
            this.mEndBackground = new ColorDrawable(this.mEndFillColor);
        }
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        int maxScrollY;
        OverScroller overScroller = this.mScroller;
        if (!(overScroller == null || this.mContentView == null || overScroller.isFinished() || this.mScroller.getCurrY() == this.mScroller.getFinalY() || i2 < (maxScrollY = getMaxScrollY()))) {
            this.mScroller.abortAnimation();
            i2 = maxScrollY;
        }
        super.onOverScrolled(i, i2, z, z2);
    }

    public void onChildViewAdded(View view, View view2) {
        this.mContentView = view2;
        view2.addOnLayoutChangeListener(this);
    }

    public void onChildViewRemoved(View view, View view2) {
        this.mContentView.removeOnLayoutChangeListener(this);
        this.mContentView = null;
    }

    public void reactSmoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2);
        updateStateOnScroll(i, i2);
        setPendingContentOffsets(i, i2);
    }

    public void reactScrollTo(int i, int i2) {
        scrollTo(i, i2);
        updateStateOnScroll(i, i2);
        setPendingContentOffsets(i, i2);
    }

    private void setPendingContentOffsets(int i, int i2) {
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getWidth() == 0 || childAt.getHeight() == 0) {
            this.pendingContentOffsetX = i;
            this.pendingContentOffsetY = i2;
            return;
        }
        this.pendingContentOffsetX = -1;
        this.pendingContentOffsetY = -1;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int maxScrollY;
        if (this.mContentView != null && getScrollY() > (maxScrollY = getMaxScrollY())) {
            reactScrollTo(getScrollX(), maxScrollY);
        }
    }

    public void setBackgroundColor(int i) {
        this.mReactBackgroundManager.setBackgroundColor(i);
    }

    public void setBorderWidth(int i, float f) {
        this.mReactBackgroundManager.setBorderWidth(i, f);
    }

    public void setBorderColor(int i, float f, float f2) {
        this.mReactBackgroundManager.setBorderColor(i, f, f2);
    }

    public void setBorderRadius(float f) {
        this.mReactBackgroundManager.setBorderRadius(f);
    }

    public void setBorderRadius(float f, int i) {
        this.mReactBackgroundManager.setBorderRadius(f, i);
    }

    public void setBorderStyle(String str) {
        this.mReactBackgroundManager.setBorderStyle(str);
    }

    public void updateState(StateWrapper stateWrapper) {
        this.mStateWrapper = stateWrapper;
    }

    /* access modifiers changed from: private */
    public void updateStateOnScroll(int i, int i2) {
        if (this.mStateWrapper != null) {
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            writableNativeMap.putDouble(CONTENT_OFFSET_LEFT, (double) PixelUtil.toDIPFromPixel((float) i));
            writableNativeMap.putDouble(CONTENT_OFFSET_TOP, (double) PixelUtil.toDIPFromPixel((float) i2));
            this.mStateWrapper.updateState(writableNativeMap);
        }
    }
}
