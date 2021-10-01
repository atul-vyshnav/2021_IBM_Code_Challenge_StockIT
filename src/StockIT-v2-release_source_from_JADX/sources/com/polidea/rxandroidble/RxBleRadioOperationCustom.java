package com.polidea.rxandroidble;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import p030rx.Observable;
import p030rx.Scheduler;

public interface RxBleRadioOperationCustom<T> extends RxBleCustomOperation<T> {
    Observable<T> asObservable(BluetoothGatt bluetoothGatt, RxBleGattCallback rxBleGattCallback, Scheduler scheduler) throws Throwable;
}
