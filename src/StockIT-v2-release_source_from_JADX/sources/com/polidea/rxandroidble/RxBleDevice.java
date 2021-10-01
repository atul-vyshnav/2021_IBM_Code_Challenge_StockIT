package com.polidea.rxandroidble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.polidea.rxandroidble.RxBleConnection;
import p030rx.Observable;

public interface RxBleDevice {
    @Deprecated
    Observable<RxBleConnection> establishConnection(Context context, boolean z);

    Observable<RxBleConnection> establishConnection(boolean z);

    Observable<RxBleConnection> establishConnection(boolean z, Timeout timeout);

    BluetoothDevice getBluetoothDevice();

    RxBleConnection.RxBleConnectionState getConnectionState();

    String getMacAddress();

    String getName();

    Observable<RxBleConnection.RxBleConnectionState> observeConnectionStateChanges();
}
