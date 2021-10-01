package com.swmansion.rnscreens;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ScreenDismissedEvent extends Event<ScreenDismissedEvent> {
    public static final String EVENT_NAME = "topDismissed";

    public short getCoalescingKey() {
        return 0;
    }

    public String getEventName() {
        return EVENT_NAME;
    }

    public ScreenDismissedEvent(int i) {
        super(i);
    }

    public void dispatch(RCTEventEmitter rCTEventEmitter) {
        rCTEventEmitter.receiveEvent(getViewTag(), getEventName(), Arguments.createMap());
    }
}
