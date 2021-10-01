package com.polidea.multiplatformbleadapter;

public enum ConnectionState {
    CONNECTING("connecting"),
    CONNECTED("connected"),
    DISCONNECTING("disconnecting"),
    DISCONNECTED("disconnected");
    
    public final String value;

    private ConnectionState(String str) {
        this.value = str;
    }
}
