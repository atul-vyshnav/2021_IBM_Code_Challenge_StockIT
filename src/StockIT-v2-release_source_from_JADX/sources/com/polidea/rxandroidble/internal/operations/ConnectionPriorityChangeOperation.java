package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;

public class ConnectionPriorityChangeOperation extends SingleResponseOperation<Long> {
    private final int connectionPriority;
    private final Scheduler delayScheduler;
    private final long operationTimeout;
    private final TimeUnit timeUnit;

    @Inject
    ConnectionPriorityChangeOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, TimeoutConfiguration timeoutConfiguration, int i, long j, TimeUnit timeUnit2, Scheduler scheduler) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.CONNECTION_PRIORITY_CHANGE, timeoutConfiguration);
        this.connectionPriority = i;
        this.operationTimeout = j;
        this.timeUnit = timeUnit2;
        this.delayScheduler = scheduler;
    }

    /* access modifiers changed from: protected */
    public Observable<Long> getCallback(RxBleGattCallback rxBleGattCallback) {
        return Observable.timer(this.operationTimeout, this.timeUnit, this.delayScheduler);
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) throws IllegalArgumentException, BleGattCannotStartException {
        return bluetoothGatt.requestConnectionPriority(this.connectionPriority);
    }
}
