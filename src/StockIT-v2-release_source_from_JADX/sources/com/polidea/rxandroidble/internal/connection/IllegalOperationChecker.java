package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.BleIllegalOperationException;
import p030rx.Completable;
import p030rx.functions.Action0;

public class IllegalOperationChecker {
    /* access modifiers changed from: private */
    public IllegalOperationHandler resultHandler;

    @Inject
    public IllegalOperationChecker(IllegalOperationHandler illegalOperationHandler) {
        this.resultHandler = illegalOperationHandler;
    }

    public Completable checkAnyPropertyMatches(final BluetoothGattCharacteristic bluetoothGattCharacteristic, final int i) {
        return Completable.fromAction(new Action0() {
            public void call() {
                BleIllegalOperationException handleMismatchData;
                if ((bluetoothGattCharacteristic.getProperties() & i) == 0 && (handleMismatchData = IllegalOperationChecker.this.resultHandler.handleMismatchData(bluetoothGattCharacteristic, i)) != null) {
                    throw handleMismatchData;
                }
            }
        });
    }
}
