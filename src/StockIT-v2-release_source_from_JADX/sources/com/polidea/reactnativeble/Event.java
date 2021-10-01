package com.polidea.reactnativeble;

public enum Event {
    ScanEvent("ScanEvent"),
    ReadEvent("ReadEvent"),
    StateChangeEvent("StateChangeEvent"),
    RestoreStateEvent("RestoreStateEvent"),
    DisconnectionEvent("DisconnectionEvent");
    
    public String name;

    private Event(String str) {
        this.name = str;
    }
}
