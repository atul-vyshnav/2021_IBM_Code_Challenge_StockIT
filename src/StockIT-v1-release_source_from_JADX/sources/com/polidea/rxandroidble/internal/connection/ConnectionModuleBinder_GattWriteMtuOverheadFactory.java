package com.polidea.rxandroidble.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class ConnectionModuleBinder_GattWriteMtuOverheadFactory implements Factory<Integer> {
    private static final ConnectionModuleBinder_GattWriteMtuOverheadFactory INSTANCE = new ConnectionModuleBinder_GattWriteMtuOverheadFactory();

    public Integer get() {
        return Integer.valueOf(ConnectionModuleBinder.gattWriteMtuOverhead());
    }

    public static ConnectionModuleBinder_GattWriteMtuOverheadFactory create() {
        return INSTANCE;
    }

    public static int proxyGattWriteMtuOverhead() {
        return ConnectionModuleBinder.gattWriteMtuOverhead();
    }
}
