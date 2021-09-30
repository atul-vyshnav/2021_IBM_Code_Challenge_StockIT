package com.polidea.rxandroidble.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.polidea.rxandroidble.RxBleConnection;

public final class DeviceModule_ProvideConnectionStateRelayFactory implements Factory<BehaviorRelay<RxBleConnection.RxBleConnectionState>> {
    private static final DeviceModule_ProvideConnectionStateRelayFactory INSTANCE = new DeviceModule_ProvideConnectionStateRelayFactory();

    public BehaviorRelay<RxBleConnection.RxBleConnectionState> get() {
        return (BehaviorRelay) Preconditions.checkNotNull(DeviceModule.provideConnectionStateRelay(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static DeviceModule_ProvideConnectionStateRelayFactory create() {
        return INSTANCE;
    }

    public static BehaviorRelay<RxBleConnection.RxBleConnectionState> proxyProvideConnectionStateRelay() {
        return (BehaviorRelay) Preconditions.checkNotNull(DeviceModule.provideConnectionStateRelay(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
