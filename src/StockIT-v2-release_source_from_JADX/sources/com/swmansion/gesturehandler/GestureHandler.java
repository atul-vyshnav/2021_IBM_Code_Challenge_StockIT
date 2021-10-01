package com.swmansion.gesturehandler;

import android.view.MotionEvent;
import android.view.View;
import com.facebook.react.bridge.UiThreadUtil;
import com.swmansion.gesturehandler.GestureHandler;
import java.util.Arrays;

public class GestureHandler<T extends GestureHandler> {
    public static final int DIRECTION_DOWN = 8;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 4;
    private static final int HIT_SLOP_BOTTOM_IDX = 3;
    private static final int HIT_SLOP_HEIGHT_IDX = 5;
    private static final int HIT_SLOP_LEFT_IDX = 0;
    public static final float HIT_SLOP_NONE = Float.NaN;
    private static final int HIT_SLOP_RIGHT_IDX = 2;
    private static final int HIT_SLOP_TOP_IDX = 1;
    private static final int HIT_SLOP_WIDTH_IDX = 4;
    private static int MAX_POINTERS_COUNT = 12;
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_BEGAN = 2;
    public static final int STATE_CANCELLED = 3;
    public static final int STATE_END = 5;
    public static final int STATE_FAILED = 1;
    public static final int STATE_UNDETERMINED = 0;
    private static short sNextEventCoalescingKey;
    private static MotionEvent.PointerCoords[] sPointerCoords;
    private static MotionEvent.PointerProperties[] sPointerProps;
    int mActivationIndex;
    private boolean mEnabled = true;
    private short mEventCoalescingKey;
    private float[] mHitSlop;
    private GestureHandlerInteractionController mInteractionController;
    boolean mIsActive;
    boolean mIsAwaiting;
    private float mLastEventOffsetX;
    private float mLastEventOffsetY;
    private float mLastX;
    private float mLastY;
    private OnTouchEventListener<T> mListener;
    private int mNumberOfPointers = 0;
    private GestureHandlerOrchestrator mOrchestrator;
    private boolean mShouldCancelWhenOutside;
    private int mState = 0;
    private int mTag;
    private final int[] mTrackedPointerIDs = new int[MAX_POINTERS_COUNT];
    private int mTrackedPointersCount = 0;
    private View mView;
    private boolean mWithinBounds;

    /* renamed from: mX */
    private float f1195mX;

    /* renamed from: mY */
    private float f1196mY;

    public static String stateToString(int i) {
        if (i == 0) {
            return "UNDETERMINED";
        }
        if (i == 1) {
            return "FAILED";
        }
        if (i == 2) {
            return "BEGIN";
        }
        if (i == 3) {
            return "CANCELLED";
        }
        if (i == 4) {
            return "ACTIVE";
        }
        if (i != 5) {
            return null;
        }
        return "END";
    }

    /* access modifiers changed from: protected */
    public void onCancel() {
    }

    /* access modifiers changed from: protected */
    public void onReset() {
    }

    /* access modifiers changed from: protected */
    public void onStateChange(int i, int i2) {
    }

    private static void initPointerProps(int i) {
        if (sPointerProps == null) {
            int i2 = MAX_POINTERS_COUNT;
            sPointerProps = new MotionEvent.PointerProperties[i2];
            sPointerCoords = new MotionEvent.PointerCoords[i2];
        }
        while (i > 0) {
            MotionEvent.PointerProperties[] pointerPropertiesArr = sPointerProps;
            int i3 = i - 1;
            if (pointerPropertiesArr[i3] == null) {
                pointerPropertiesArr[i3] = new MotionEvent.PointerProperties();
                sPointerCoords[i3] = new MotionEvent.PointerCoords();
                i--;
            } else {
                return;
            }
        }
    }

    private static boolean hitSlopSet(float f) {
        return !Float.isNaN(f);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStateChange(int i, int i2) {
        OnTouchEventListener<T> onTouchEventListener = this.mListener;
        if (onTouchEventListener != null) {
            onTouchEventListener.onStateChange(this, i, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchTouchEvent(MotionEvent motionEvent) {
        OnTouchEventListener<T> onTouchEventListener = this.mListener;
        if (onTouchEventListener != null) {
            onTouchEventListener.onTouchEvent(this, motionEvent);
        }
    }

    public boolean hasCommonPointers(GestureHandler gestureHandler) {
        int i = 0;
        while (true) {
            int[] iArr = this.mTrackedPointerIDs;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] != -1 && gestureHandler.mTrackedPointerIDs[i] != -1) {
                return true;
            }
            i++;
        }
    }

    public T setShouldCancelWhenOutside(boolean z) {
        this.mShouldCancelWhenOutside = z;
        return this;
    }

    public T setEnabled(boolean z) {
        if (this.mView != null) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    GestureHandler.this.cancel();
                }
            });
        }
        this.mEnabled = z;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public T setHitSlop(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.mHitSlop == null) {
            this.mHitSlop = new float[6];
        }
        float[] fArr = this.mHitSlop;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        fArr[3] = f4;
        fArr[4] = f5;
        fArr[5] = f6;
        if (hitSlopSet(f5) && hitSlopSet(f) && hitSlopSet(f3)) {
            throw new IllegalArgumentException("Cannot have all of left, right and width defined");
        } else if (hitSlopSet(f5) && !hitSlopSet(f) && !hitSlopSet(f3)) {
            throw new IllegalArgumentException("When width is set one of left or right pads need to be defined");
        } else if (hitSlopSet(f6) && hitSlopSet(f4) && hitSlopSet(f2)) {
            throw new IllegalArgumentException("Cannot have all of top, bottom and height defined");
        } else if (!hitSlopSet(f6) || hitSlopSet(f4) || hitSlopSet(f2)) {
            return this;
        } else {
            throw new IllegalArgumentException("When height is set one of top or bottom pads need to be defined");
        }
    }

    public T setHitSlop(float f) {
        return setHitSlop(f, f, f, f, Float.NaN, Float.NaN);
    }

    public T setInteractionController(GestureHandlerInteractionController gestureHandlerInteractionController) {
        this.mInteractionController = gestureHandlerInteractionController;
        return this;
    }

    public void setTag(int i) {
        this.mTag = i;
    }

    public int getTag() {
        return this.mTag;
    }

    public View getView() {
        return this.mView;
    }

    public float getX() {
        return this.f1195mX;
    }

    public float getY() {
        return this.f1196mY;
    }

    public int getNumberOfPointers() {
        return this.mNumberOfPointers;
    }

    public boolean isWithinBounds() {
        return this.mWithinBounds;
    }

    public short getEventCoalescingKey() {
        return this.mEventCoalescingKey;
    }

    public final void prepare(View view, GestureHandlerOrchestrator gestureHandlerOrchestrator) {
        if (this.mView == null && this.mOrchestrator == null) {
            Arrays.fill(this.mTrackedPointerIDs, -1);
            this.mTrackedPointersCount = 0;
            this.mState = 0;
            this.mView = view;
            this.mOrchestrator = gestureHandlerOrchestrator;
            return;
        }
        throw new IllegalStateException("Already prepared or hasn't been reset");
    }

    private int findNextLocalPointerId() {
        int i = 0;
        while (i < this.mTrackedPointersCount) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.mTrackedPointerIDs;
                if (i2 < iArr.length && iArr[i2] != i) {
                    i2++;
                }
            }
            if (i2 == this.mTrackedPointerIDs.length) {
                return i;
            }
            i++;
        }
        return i;
    }

    public void startTrackingPointer(int i) {
        int[] iArr = this.mTrackedPointerIDs;
        if (iArr[i] == -1) {
            iArr[i] = findNextLocalPointerId();
            this.mTrackedPointersCount++;
        }
    }

    public void stopTrackingPointer(int i) {
        int[] iArr = this.mTrackedPointerIDs;
        if (iArr[i] != -1) {
            iArr[i] = -1;
            this.mTrackedPointersCount--;
        }
    }

    private boolean needAdapt(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != this.mTrackedPointersCount) {
            return true;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.mTrackedPointerIDs;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] != -1 && iArr[i] != i) {
                return true;
            }
            i++;
        }
    }

    private MotionEvent adaptEvent(MotionEvent motionEvent) {
        int i;
        MotionEvent motionEvent2 = motionEvent;
        if (!needAdapt(motionEvent)) {
            return motionEvent2;
        }
        int actionMasked = motionEvent.getActionMasked();
        int i2 = 2;
        int i3 = 5;
        if (actionMasked == 0 || actionMasked == 5) {
            int actionIndex = motionEvent.getActionIndex();
            if (this.mTrackedPointerIDs[motionEvent2.getPointerId(actionIndex)] != -1) {
                if (this.mTrackedPointersCount == 1) {
                    i3 = 0;
                }
                i2 = i3;
            }
            int i4 = i2;
            i = actionIndex;
            actionMasked = i4;
        } else {
            int i5 = 6;
            if (actionMasked == 1 || actionMasked == 6) {
                int actionIndex2 = motionEvent.getActionIndex();
                if (this.mTrackedPointerIDs[motionEvent2.getPointerId(actionIndex2)] != -1) {
                    if (this.mTrackedPointersCount == 1) {
                        i5 = 1;
                    }
                    i = actionIndex2;
                    actionMasked = i5;
                } else {
                    i = actionIndex2;
                    actionMasked = 2;
                }
            } else {
                i = -1;
            }
        }
        initPointerProps(this.mTrackedPointersCount);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        motionEvent2.setLocation(motionEvent.getRawX(), motionEvent.getRawY());
        int pointerCount = motionEvent.getPointerCount();
        int i6 = actionMasked;
        int i7 = 0;
        for (int i8 = 0; i8 < pointerCount; i8++) {
            int pointerId = motionEvent2.getPointerId(i8);
            if (this.mTrackedPointerIDs[pointerId] != -1) {
                motionEvent2.getPointerProperties(i8, sPointerProps[i7]);
                sPointerProps[i7].id = this.mTrackedPointerIDs[pointerId];
                motionEvent2.getPointerCoords(i8, sPointerCoords[i7]);
                if (i8 == i) {
                    i6 |= i7 << 8;
                }
                i7++;
            }
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), i6, i7, sPointerProps, sPointerCoords, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags());
        motionEvent2.setLocation(x, y);
        obtain.setLocation(x, y);
        return obtain;
    }

    public final void handle(MotionEvent motionEvent) {
        int i;
        if (this.mEnabled && (i = this.mState) != 3 && i != 1 && i != 5 && this.mTrackedPointersCount >= 1) {
            MotionEvent adaptEvent = adaptEvent(motionEvent);
            this.f1195mX = adaptEvent.getX();
            this.f1196mY = adaptEvent.getY();
            this.mNumberOfPointers = adaptEvent.getPointerCount();
            boolean isWithinBounds = isWithinBounds(this.mView, this.f1195mX, this.f1196mY);
            this.mWithinBounds = isWithinBounds;
            if (!this.mShouldCancelWhenOutside || isWithinBounds) {
                this.mLastX = GestureUtils.getLastPointerX(adaptEvent, true);
                this.mLastY = GestureUtils.getLastPointerY(adaptEvent, true);
                this.mLastEventOffsetX = adaptEvent.getRawX() - adaptEvent.getX();
                this.mLastEventOffsetY = adaptEvent.getRawY() - adaptEvent.getY();
                onHandle(adaptEvent);
                if (adaptEvent != motionEvent) {
                    adaptEvent.recycle();
                    return;
                }
                return;
            }
            int i2 = this.mState;
            if (i2 == 4) {
                cancel();
            } else if (i2 == 2) {
                fail();
            }
        }
    }

    private void moveToState(int i) {
        UiThreadUtil.assertOnUiThread();
        int i2 = this.mState;
        if (i2 != i) {
            this.mState = i;
            if (i == 4) {
                short s = sNextEventCoalescingKey;
                sNextEventCoalescingKey = (short) (s + 1);
                this.mEventCoalescingKey = s;
            }
            this.mOrchestrator.onHandlerStateChange(this, i, i2);
            onStateChange(i, i2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r0 = r3.mState;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean wantEvents() {
        /*
            r3 = this;
            boolean r0 = r3.mEnabled
            r1 = 1
            if (r0 == 0) goto L_0x0014
            int r0 = r3.mState
            if (r0 == r1) goto L_0x0014
            r2 = 3
            if (r0 == r2) goto L_0x0014
            r2 = 5
            if (r0 == r2) goto L_0x0014
            int r0 = r3.mTrackedPointersCount
            if (r0 <= 0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.gesturehandler.GestureHandler.wantEvents():boolean");
    }

    public int getState() {
        return this.mState;
    }

    public boolean shouldRequireToWaitForFailure(GestureHandler gestureHandler) {
        GestureHandlerInteractionController gestureHandlerInteractionController;
        if (gestureHandler == this || (gestureHandlerInteractionController = this.mInteractionController) == null) {
            return false;
        }
        return gestureHandlerInteractionController.shouldRequireHandlerToWaitForFailure(this, gestureHandler);
    }

    public boolean shouldWaitForHandlerFailure(GestureHandler gestureHandler) {
        GestureHandlerInteractionController gestureHandlerInteractionController;
        if (gestureHandler == this || (gestureHandlerInteractionController = this.mInteractionController) == null) {
            return false;
        }
        return gestureHandlerInteractionController.shouldWaitForHandlerFailure(this, gestureHandler);
    }

    public boolean shouldRecognizeSimultaneously(GestureHandler gestureHandler) {
        if (gestureHandler == this) {
            return true;
        }
        GestureHandlerInteractionController gestureHandlerInteractionController = this.mInteractionController;
        if (gestureHandlerInteractionController != null) {
            return gestureHandlerInteractionController.shouldRecognizeSimultaneously(this, gestureHandler);
        }
        return false;
    }

    public boolean shouldBeCancelledBy(GestureHandler gestureHandler) {
        GestureHandlerInteractionController gestureHandlerInteractionController;
        if (gestureHandler == this || (gestureHandlerInteractionController = this.mInteractionController) == null) {
            return false;
        }
        return gestureHandlerInteractionController.shouldHandlerBeCancelledBy(this, gestureHandler);
    }

    public boolean isWithinBounds(View view, float f, float f2) {
        float f3;
        float width = (float) view.getWidth();
        float height = (float) view.getHeight();
        float[] fArr = this.mHitSlop;
        float f4 = 0.0f;
        if (fArr != null) {
            float f5 = fArr[0];
            float f6 = fArr[1];
            float f7 = fArr[2];
            float f8 = fArr[3];
            float f9 = hitSlopSet(f5) ? 0.0f - f5 : 0.0f;
            if (hitSlopSet(f6)) {
                f4 = 0.0f - f6;
            }
            if (hitSlopSet(f7)) {
                width += f7;
            }
            if (hitSlopSet(f8)) {
                height += f8;
            }
            float[] fArr2 = this.mHitSlop;
            float f10 = fArr2[4];
            float f11 = fArr2[5];
            if (hitSlopSet(f10)) {
                if (!hitSlopSet(f5)) {
                    f9 = width - f10;
                } else if (!hitSlopSet(f7)) {
                    width = f10 + f9;
                }
            }
            if (hitSlopSet(f11)) {
                if (!hitSlopSet(f6)) {
                    f4 = height - f11;
                } else if (!hitSlopSet(f8)) {
                    height = f4 + f11;
                }
            }
            f3 = f4;
            f4 = f9;
        } else {
            f3 = 0.0f;
        }
        if (f < f4 || f > width || f2 < f3 || f2 > height) {
            return false;
        }
        return true;
    }

    public final void cancel() {
        int i = this.mState;
        if (i == 4 || i == 0 || i == 2) {
            onCancel();
            moveToState(3);
        }
    }

    public final void fail() {
        int i = this.mState;
        if (i == 4 || i == 0 || i == 2) {
            moveToState(1);
        }
    }

    public final void activate() {
        int i = this.mState;
        if (i == 0 || i == 2) {
            moveToState(4);
        }
    }

    public final void begin() {
        if (this.mState == 0) {
            moveToState(2);
        }
    }

    public final void end() {
        int i = this.mState;
        if (i == 2 || i == 4) {
            moveToState(5);
        }
    }

    /* access modifiers changed from: protected */
    public void onHandle(MotionEvent motionEvent) {
        moveToState(1);
    }

    public final void reset() {
        this.mView = null;
        this.mOrchestrator = null;
        Arrays.fill(this.mTrackedPointerIDs, -1);
        this.mTrackedPointersCount = 0;
        onReset();
    }

    public GestureHandler setOnTouchEventListener(OnTouchEventListener<T> onTouchEventListener) {
        this.mListener = onTouchEventListener;
        return this;
    }

    public String toString() {
        View view = this.mView;
        String simpleName = view == null ? null : view.getClass().getSimpleName();
        return getClass().getSimpleName() + "@[" + this.mTag + "]:" + simpleName;
    }

    public float getLastAbsolutePositionX() {
        return this.mLastX;
    }

    public float getLastAbsolutePositionY() {
        return this.mLastY;
    }

    public float getLastRelativePositionX() {
        return this.mLastX - this.mLastEventOffsetX;
    }

    public float getLastRelativePositionY() {
        return this.mLastY - this.mLastEventOffsetY;
    }
}
