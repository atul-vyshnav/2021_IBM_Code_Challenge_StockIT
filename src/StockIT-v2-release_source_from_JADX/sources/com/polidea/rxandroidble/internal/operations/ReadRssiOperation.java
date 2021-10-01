package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import p030rx.Observable;

public class ReadRssiOperation extends SingleResponseOperation<Integer> {
    @Inject
    ReadRssiOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.READ_RSSI, timeoutConfiguration);
    }

    /* access modifiers changed from: protected */
    public Observable<Integer> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnRssiRead();
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.readRemoteRssi();
    }
}
