package com.swmansion.rnscreens;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ScreenDisappearEvent extends Event<ScreenAppearEvent> {
    public static final String EVENT_NAME = "topDisappear";

    public short getCoalescingKey() {
        return 0;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public ScreenDisappearEvent(int i) {
        super(i);
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), Arguments.createMap());
    }
}
