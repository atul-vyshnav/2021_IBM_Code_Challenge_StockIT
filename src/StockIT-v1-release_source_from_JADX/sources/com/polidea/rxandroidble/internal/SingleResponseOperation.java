package com.polidea.rxandroidble.internal;

import android.bluetooth.BluetoothGatt;
import android.os.DeadObjectException;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble.internal.util.QueueReleasingEmitterWrapper;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Subscription;

public abstract class SingleResponseOperation<T> extends QueueOperation<T> {
    private final BluetoothGatt bluetoothGatt;
    private final BleGattOperationType operationType;
    private final RxBleGattCallback rxBleGattCallback;
    private final TimeoutConfiguration timeoutConfiguration;

    /* access modifiers changed from: protected */
    public abstract Observable<T> getCallback(RxBleGattCallback rxBleGattCallback2);

    /* access modifiers changed from: protected */
    public abstract boolean startOperation(BluetoothGatt bluetoothGatt2);

    public SingleResponseOperation(BluetoothGatt bluetoothGatt2, RxBleGattCallback rxBleGattCallback2, BleGattOperationType bleGattOperationType, TimeoutConfiguration timeoutConfiguration2) {
        this.bluetoothGatt = bluetoothGatt2;
        this.rxBleGattCallback = rxBleGattCallback2;
        this.operationType = bleGattOperationType;
        this.timeoutConfiguration = timeoutConfiguration2;
    }

    /* access modifiers changed from: protected */
    public final void protectedRun(Emitter<T> emitter, QueueReleaseInterface queueReleaseInterface) throws Throwable {
        QueueReleasingEmitterWrapper queueReleasingEmitterWrapper = new QueueReleasingEmitterWrapper(emitter, queueReleaseInterface);
        Subscription subscribe = getCallback(this.rxBleGattCallback).first().timeout(this.timeoutConfiguration.timeout, this.timeoutConfiguration.timeoutTimeUnit, timeoutFallbackProcedure(this.bluetoothGatt, this.rxBleGattCallback, this.timeoutConfiguration.timeoutScheduler), this.timeoutConfiguration.timeoutScheduler).subscribe(queueReleasingEmitterWrapper);
        if (!startOperation(this.bluetoothGatt)) {
            subscribe.unsubscribe();
            queueReleasingEmitterWrapper.onError(new BleGattCannotStartException(this.bluetoothGatt, this.operationType));
        }
    }

    /* access modifiers changed from: protected */
    public Observable<T> timeoutFallbackProcedure(BluetoothGatt bluetoothGatt2, RxBleGattCallback rxBleGattCallback2, Scheduler scheduler) {
        return Observable.error(new BleGattCallbackTimeoutException(this.bluetoothGatt, this.operationType));
    }

    /* access modifiers changed from: protected */
    public BleException provideException(DeadObjectException deadObjectException) {
        return new BleDisconnectedException(deadObjectException, this.bluetoothGatt.getDevice().getAddress(), -1);
    }
}
