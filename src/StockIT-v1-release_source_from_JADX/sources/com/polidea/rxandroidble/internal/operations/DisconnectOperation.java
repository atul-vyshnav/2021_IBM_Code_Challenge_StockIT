package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.internal.QueueOperation;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Scheduler;
import p030rx.Subscriber;
import p030rx.functions.Action0;
import p030rx.functions.Func1;

public class DisconnectOperation extends QueueOperation<Void> {
    private final BluetoothGattProvider bluetoothGattProvider;
    private final Scheduler bluetoothInteractionScheduler;
    private final BluetoothManager bluetoothManager;
    private final ConnectionStateChangeListener connectionStateChangeListener;
    private final String macAddress;
    private final RxBleGattCallback rxBleGattCallback;
    private final TimeoutConfiguration timeoutConfiguration;

    @Inject
    DisconnectOperation(RxBleGattCallback rxBleGattCallback2, BluetoothGattProvider bluetoothGattProvider2, @Named("mac-address") String str, BluetoothManager bluetoothManager2, @Named("bluetooth_interaction") Scheduler scheduler, @Named("disconnect-timeout") TimeoutConfiguration timeoutConfiguration2, ConnectionStateChangeListener connectionStateChangeListener2) {
        this.rxBleGattCallback = rxBleGattCallback2;
        this.bluetoothGattProvider = bluetoothGattProvider2;
        this.macAddress = str;
        this.bluetoothManager = bluetoothManager2;
        this.bluetoothInteractionScheduler = scheduler;
        this.timeoutConfiguration = timeoutConfiguration2;
        this.connectionStateChangeListener = connectionStateChangeListener2;
    }

    /* access modifiers changed from: protected */
    public void protectedRun(final Emitter<Void> emitter, final QueueReleaseInterface queueReleaseInterface) {
        this.connectionStateChangeListener.onConnectionStateChange(RxBleConnection.RxBleConnectionState.DISCONNECTING);
        BluetoothGatt bluetoothGatt = this.bluetoothGattProvider.getBluetoothGatt();
        if (bluetoothGatt == null) {
            RxBleLog.m1118w("Disconnect operation has been executed but GATT instance was null - considering disconnected.", new Object[0]);
            considerGattDisconnected(emitter, queueReleaseInterface);
            return;
        }
        (isDisconnected(bluetoothGatt) ? Observable.just(bluetoothGatt) : disconnect(bluetoothGatt)).observeOn(this.bluetoothInteractionScheduler).subscribe(new Observer<BluetoothGatt>() {
            public void onNext(BluetoothGatt bluetoothGatt) {
                bluetoothGatt.close();
            }

            public void onError(Throwable th) {
                RxBleLog.m1119w(th, "Disconnect operation has been executed but finished with an error - considering disconnected.", new Object[0]);
                DisconnectOperation.this.considerGattDisconnected(emitter, queueReleaseInterface);
            }

            public void onCompleted() {
                DisconnectOperation.this.considerGattDisconnected(emitter, queueReleaseInterface);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void considerGattDisconnected(Emitter<Void> emitter, QueueReleaseInterface queueReleaseInterface) {
        this.connectionStateChangeListener.onConnectionStateChange(RxBleConnection.RxBleConnectionState.DISCONNECTED);
        queueReleaseInterface.release();
        emitter.onCompleted();
    }

    private boolean isDisconnected(BluetoothGatt bluetoothGatt) {
        return this.bluetoothManager.getConnectionState(bluetoothGatt.getDevice(), 7) == 0;
    }

    private Observable<BluetoothGatt> disconnect(BluetoothGatt bluetoothGatt) {
        return new DisconnectGattObservable(bluetoothGatt, this.rxBleGattCallback, this.bluetoothInteractionScheduler).timeout(this.timeoutConfiguration.timeout, this.timeoutConfiguration.timeoutTimeUnit, Observable.just(bluetoothGatt), this.timeoutConfiguration.timeoutScheduler);
    }

    private static class DisconnectGattObservable extends Observable<BluetoothGatt> {
        DisconnectGattObservable(final BluetoothGatt bluetoothGatt, final RxBleGattCallback rxBleGattCallback, final Scheduler scheduler) {
            super(new Observable.OnSubscribe<BluetoothGatt>() {
                public void call(Subscriber<? super BluetoothGatt> subscriber) {
                    RxBleGattCallback.this.getOnConnectionStateChange().takeFirst(new Func1<RxBleConnection.RxBleConnectionState, Boolean>() {
                        public Boolean call(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
                            return Boolean.valueOf(rxBleConnectionState == RxBleConnection.RxBleConnectionState.DISCONNECTED);
                        }
                    }).map(new Func1<RxBleConnection.RxBleConnectionState, BluetoothGatt>() {
                        public BluetoothGatt call(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
                            return bluetoothGatt;
                        }
                    }).subscribe(subscriber);
                    scheduler.createWorker().schedule(new Action0() {
                        public void call() {
                            bluetoothGatt.disconnect();
                        }
                    });
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public BleException provideException(DeadObjectException deadObjectException) {
        return new BleDisconnectedException(deadObjectException, this.macAddress, -1);
    }
}
