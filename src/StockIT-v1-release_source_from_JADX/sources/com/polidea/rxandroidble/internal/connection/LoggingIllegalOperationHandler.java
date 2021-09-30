package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.BleIllegalOperationException;
import com.polidea.rxandroidble.internal.RxBleLog;

public class LoggingIllegalOperationHandler extends IllegalOperationHandler {
    @Inject
    public LoggingIllegalOperationHandler(IllegalOperationMessageCreator illegalOperationMessageCreator) {
        super(illegalOperationMessageCreator);
    }

    public BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        RxBleLog.m1118w(this.messageCreator.createMismatchMessage(bluetoothGattCharacteristic, i), new Object[0]);
        return null;
    }
}
