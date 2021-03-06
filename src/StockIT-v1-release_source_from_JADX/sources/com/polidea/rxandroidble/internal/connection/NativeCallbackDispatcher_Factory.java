package com.polidea.rxandroidble.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class NativeCallbackDispatcher_Factory implements Factory<NativeCallbackDispatcher> {
    private static final NativeCallbackDispatcher_Factory INSTANCE = new NativeCallbackDispatcher_Factory();

    public NativeCallbackDispatcher get() {
        return new NativeCallbackDispatcher();
    }

    public static NativeCallbackDispatcher_Factory create() {
        return INSTANCE;
    }

    public static NativeCallbackDispatcher newNativeCallbackDispatcher() {
        return new NativeCallbackDispatcher();
    }
}
