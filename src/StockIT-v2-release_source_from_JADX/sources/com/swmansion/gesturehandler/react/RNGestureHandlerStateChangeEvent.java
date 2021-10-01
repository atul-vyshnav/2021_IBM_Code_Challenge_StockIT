package com.swmansion.gesturehandler.react;

import androidx.core.util.Pools;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.swmansion.gesturehandler.GestureHandler;

public class RNGestureHandlerStateChangeEvent extends Event<RNGestureHandlerStateChangeEvent> {
    private static final Pools.SynchronizedPool<RNGestureHandlerStateChangeEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(7);
    public static final String EVENT_NAME = "onGestureHandlerStateChange";
    private static final int TOUCH_EVENTS_POOL_SIZE = 7;
    private WritableMap mExtraData;

    public boolean canCoalesce() {
        return false;
    }

    public short getCoalescingKey() {
        return 0;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public static RNGestureHandlerStateChangeEvent obtain(GestureHandler gestureHandler, int i, int i2, RNGestureHandlerEventDataExtractor rNGestureHandlerEventDataExtractor) {
        RNGestureHandlerStateChangeEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new RNGestureHandlerStateChangeEvent();
        }
        acquire.init(gestureHandler, i, i2, rNGestureHandlerEventDataExtractor);
        return acquire;
    }

    private RNGestureHandlerStateChangeEvent() {
    }

    private void init(GestureHandler gestureHandler, int i, int i2, RNGestureHandlerEventDataExtractor rNGestureHandlerEventDataExtractor) {
        super.init(gestureHandler.getView().getId());
        WritableMap createMap = Arguments.createMap();
        this.mExtraData = createMap;
        if (rNGestureHandlerEventDataExtractor != null) {
            rNGestureHandlerEventDataExtractor.extractEventData(gestureHandler, createMap);
        }
        this.mExtraData.putInt("handlerTag", gestureHandler.getTag());
        this.mExtraData.putInt("state", i);
        this.mExtraData.putInt("oldState", i2);
    }

    public void onDispose() {
        this.mExtraData = null;
        EVENTS_POOL.release(this);
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), EVENT_NAME, this.mExtraData);
    }
}
