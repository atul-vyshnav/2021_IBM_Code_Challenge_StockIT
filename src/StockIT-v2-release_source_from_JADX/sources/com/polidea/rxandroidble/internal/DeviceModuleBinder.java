package com.polidea.rxandroidble.internal;

import bleshadow.dagger.Binds;
import bleshadow.dagger.Module;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.internal.connection.Connector;
import com.polidea.rxandroidble.internal.connection.ConnectorImpl;

@Module
abstract class DeviceModuleBinder {
    /* access modifiers changed from: package-private */
    @Binds
    public abstract Connector bindConnector(ConnectorImpl connectorImpl);

    /* access modifiers changed from: package-private */
    @Binds
    public abstract RxBleDevice bindDevice(RxBleDeviceImpl rxBleDeviceImpl);

    DeviceModuleBinder() {
    }
}
