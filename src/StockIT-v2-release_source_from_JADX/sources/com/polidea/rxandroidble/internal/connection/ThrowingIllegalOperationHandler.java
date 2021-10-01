package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.BleIllegalOperationException;
import com.polidea.rxandroidble.internal.RxBleLog;

public class ThrowingIllegalOperationHandler extends IllegalOperationHandler {
    @Inject
    public ThrowingIllegalOperationHandler(IllegalOperationMessageCreator illegalOperationMessageCreator) {
        super(illegalOperationMessageCreator);
    }

    public BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        String createMismatchMessage = this.messageCreator.createMismatchMessage(bluetoothGattCharacteristic, i);
        RxBleLog.m1112e(createMismatchMessage, new Object[0]);
        return new BleIllegalOperationException(createMismatchMessage, bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getProperties(), i);
    }
}
