package com.polidea.rxandroidble.internal;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import bleshadow.javax.inject.Inject;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.polidea.rxandroidble.ConnectionSetup;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.Timeout;
import com.polidea.rxandroidble.exceptions.BleAlreadyConnectedException;
import com.polidea.rxandroidble.internal.connection.Connector;
import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Observable;
import p030rx.functions.Action0;
import p030rx.functions.Func0;

@DeviceScope
class RxBleDeviceImpl implements RxBleDevice {
    /* access modifiers changed from: private */
    public final BluetoothDevice bluetoothDevice;
    private final BehaviorRelay<RxBleConnection.RxBleConnectionState> connectionStateRelay;
    /* access modifiers changed from: private */
    public final Connector connector;
    /* access modifiers changed from: private */
    public AtomicBoolean isConnected = new AtomicBoolean(false);

    @Inject
    RxBleDeviceImpl(BluetoothDevice bluetoothDevice2, Connector connector2, BehaviorRelay<RxBleConnection.RxBleConnectionState> behaviorRelay) {
        this.bluetoothDevice = bluetoothDevice2;
        this.connector = connector2;
        this.connectionStateRelay = behaviorRelay;
    }

    public Observable<RxBleConnection.RxBleConnectionState> observeConnectionStateChanges() {
        return this.connectionStateRelay.distinctUntilChanged().skip(1);
    }

    public RxBleConnection.RxBleConnectionState getConnectionState() {
        return this.connectionStateRelay.getValue();
    }

    @Deprecated
    public Observable<RxBleConnection> establishConnection(Context context, boolean z) {
        return establishConnection(z);
    }

    public Observable<RxBleConnection> establishConnection(boolean z) {
        return establishConnection(new ConnectionSetup.Builder().setAutoConnect(z).setSuppressIllegalOperationCheck(true).build());
    }

    public Observable<RxBleConnection> establishConnection(boolean z, Timeout timeout) {
        return establishConnection(new ConnectionSetup.Builder().setAutoConnect(z).setOperationTimeout(timeout).setSuppressIllegalOperationCheck(true).build());
    }

    public Observable<RxBleConnection> establishConnection(final ConnectionSetup connectionSetup) {
        return Observable.defer(new Func0<Observable<RxBleConnection>>() {
            public Observable<RxBleConnection> call() {
                if (RxBleDeviceImpl.this.isConnected.compareAndSet(false, true)) {
                    return RxBleDeviceImpl.this.connector.prepareConnection(connectionSetup).doOnUnsubscribe(new Action0() {
                        public void call() {
                            RxBleDeviceImpl.this.isConnected.set(false);
                        }
                    });
                }
                return Observable.error(new BleAlreadyConnectedException(RxBleDeviceImpl.this.bluetoothDevice.getAddress()));
            }
        });
    }

    public String getName() {
        return this.bluetoothDevice.getName();
    }

    public String getMacAddress() {
        return this.bluetoothDevice.getAddress();
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RxBleDeviceImpl)) {
            return false;
        }
        return this.bluetoothDevice.equals(((RxBleDeviceImpl) obj).bluetoothDevice);
    }

    public int hashCode() {
        return this.bluetoothDevice.hashCode();
    }

    public String toString() {
        return "RxBleDeviceImpl{bluetoothDevice=" + this.bluetoothDevice.getName() + '(' + this.bluetoothDevice.getAddress() + ')' + '}';
    }
}
