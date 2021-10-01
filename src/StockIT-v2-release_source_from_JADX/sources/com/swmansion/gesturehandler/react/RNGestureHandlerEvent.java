package com.swmansion.gesturehandler.react;

import androidx.core.util.Pools;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.swmansion.gesturehandler.GestureHandler;

public class RNGestureHandlerEvent extends Event<RNGestureHandlerEvent> {
    private static final Pools.SynchronizedPool<RNGestureHandlerEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(7);
    public static final String EVENT_NAME = "onGestureHandlerEvent";
    private static final int TOUCH_EVENTS_POOL_SIZE = 7;
    private short mCoalescingKey;
    private WritableMap mExtraData;

    public boolean canCoalesce() {
        return true;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public static RNGestureHandlerEvent obtain(GestureHandler gestureHandler, RNGestureHandlerEventDataExtractor rNGestureHandlerEventDataExtractor) {
        RNGestureHandlerEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new RNGestureHandlerEvent();
        }
        acquire.init(gestureHandler, rNGestureHandlerEventDataExtractor);
        return acquire;
    }

    private RNGestureHandlerEvent() {
    }

    private void init(GestureHandler gestureHandler, RNGestureHandlerEventDataExtractor rNGestureHandlerEventDataExtractor) {
        super.init(gestureHandler.getView().getId());
        WritableMap createMap = Arguments.createMap();
        this.mExtraData = createMap;
        if (rNGestureHandlerEventDataExtractor != null) {
            rNGestureHandlerEventDataExtractor.extractEventData(gestureHandler, createMap);
        }
        this.mExtraData.putInt("handlerTag", gestureHandler.getTag());
        this.mExtraData.putInt("state", gestureHandler.getState());
        this.mCoalescingKey = gestureHandler.getEventCoalescingKey();
    }

    public void onDispose() {
        this.mExtraData = null;
        EVENTS_POOL.release(this);
    }

    public short getCoalescingKey() {
        return this.mCoalescingKey;
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), EVENT_NAME, this.mExtraData);
    }
}
