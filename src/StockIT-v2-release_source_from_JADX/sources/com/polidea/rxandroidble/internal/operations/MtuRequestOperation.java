package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import p030rx.Observable;

public class MtuRequestOperation extends SingleResponseOperation<Integer> {
    private final int mtu;

    @Inject
    MtuRequestOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, TimeoutConfiguration timeoutConfiguration, int i) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.ON_MTU_CHANGED, timeoutConfiguration);
        this.mtu = i;
    }

    /* access modifiers changed from: protected */
    public Observable<Integer> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnMtuChanged();
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.requestMtu(this.mtu);
    }
}
