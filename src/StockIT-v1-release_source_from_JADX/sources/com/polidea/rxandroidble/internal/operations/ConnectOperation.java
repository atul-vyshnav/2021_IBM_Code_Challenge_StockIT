package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.QueueOperation;
import com.polidea.rxandroidble.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble.internal.util.BleConnectionCompat;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Subscription;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.functions.Cancellable;
import p030rx.functions.Func0;
import p030rx.functions.Func1;

public class ConnectOperation extends QueueOperation<BluetoothGatt> {
    /* access modifiers changed from: private */
    public final boolean autoConnect;
    /* access modifiers changed from: private */
    public final BluetoothDevice bluetoothDevice;
    /* access modifiers changed from: private */
    public final BluetoothGattProvider bluetoothGattProvider;
    /* access modifiers changed from: private */
    public final TimeoutConfiguration connectTimeout;
    /* access modifiers changed from: private */
    public final BleConnectionCompat connectionCompat;
    /* access modifiers changed from: private */
    public final ConnectionStateChangeListener connectionStateChangedAction;
    /* access modifiers changed from: private */
    public final RxBleGattCallback rxBleGattCallback;

    @Inject
    ConnectOperation(BluetoothDevice bluetoothDevice2, BleConnectionCompat bleConnectionCompat, RxBleGattCallback rxBleGattCallback2, BluetoothGattProvider bluetoothGattProvider2, @Named("connect-timeout") TimeoutConfiguration timeoutConfiguration, @Named("autoConnect") boolean z, ConnectionStateChangeListener connectionStateChangeListener) {
        this.bluetoothDevice = bluetoothDevice2;
        this.connectionCompat = bleConnectionCompat;
        this.rxBleGattCallback = rxBleGattCallback2;
        this.bluetoothGattProvider = bluetoothGattProvider2;
        this.connectTimeout = timeoutConfiguration;
        this.autoConnect = z;
        this.connectionStateChangedAction = connectionStateChangeListener;
    }

    /* access modifiers changed from: protected */
    public void protectedRun(Emitter<BluetoothGatt> emitter, final QueueReleaseInterface queueReleaseInterface) {
        C26491 r0 = new Action0() {
            public void call() {
                queueReleaseInterface.release();
            }
        };
        emitter.setSubscription(getConnectedBluetoothGatt().compose(wrapWithTimeoutWhenNotAutoconnecting()).doOnUnsubscribe(r0).doOnTerminate(r0).subscribe(emitter));
        if (this.autoConnect) {
            queueReleaseInterface.release();
        }
    }

    private Observable.Transformer<BluetoothGatt, BluetoothGatt> wrapWithTimeoutWhenNotAutoconnecting() {
        return new Observable.Transformer<BluetoothGatt, BluetoothGatt>() {
            public Observable<BluetoothGatt> call(Observable<BluetoothGatt> observable) {
                if (ConnectOperation.this.autoConnect) {
                    return observable;
                }
                return observable.timeout(ConnectOperation.this.connectTimeout.timeout, ConnectOperation.this.connectTimeout.timeoutTimeUnit, ConnectOperation.this.prepareConnectionTimeoutErrorObservable(), ConnectOperation.this.connectTimeout.timeoutScheduler);
            }
        };
    }

    /* access modifiers changed from: private */
    public Observable<BluetoothGatt> prepareConnectionTimeoutErrorObservable() {
        return Observable.fromCallable(new Func0<BluetoothGatt>() {
            public BluetoothGatt call() {
                throw new BleGattCallbackTimeoutException(ConnectOperation.this.bluetoothGattProvider.getBluetoothGatt(), BleGattOperationType.CONNECTION_STATE);
            }
        });
    }

    private Observable<BluetoothGatt> getConnectedBluetoothGatt() {
        return Observable.create(new Action1<Emitter<BluetoothGatt>>() {
            public void call(Emitter<BluetoothGatt> emitter) {
                final Subscription subscribe = Observable.fromCallable(new Func0<BluetoothGatt>() {
                    public BluetoothGatt call() {
                        ConnectOperation.this.connectionStateChangedAction.onConnectionStateChange(RxBleConnection.RxBleConnectionState.CONNECTED);
                        return ConnectOperation.this.bluetoothGattProvider.getBluetoothGatt();
                    }
                }).delaySubscription(ConnectOperation.this.rxBleGattCallback.getOnConnectionStateChange().takeFirst(new Func1<RxBleConnection.RxBleConnectionState, Boolean>() {
                    public Boolean call(RxBleConnection.RxBleConnectionState rxBleConnectionState) {
                        return Boolean.valueOf(rxBleConnectionState == RxBleConnection.RxBleConnectionState.CONNECTED);
                    }
                })).mergeWith(ConnectOperation.this.rxBleGattCallback.observeDisconnect()).take(1).subscribe(emitter);
                emitter.setCancellation(new Cancellable() {
                    public void cancel() throws Exception {
                        subscribe.unsubscribe();
                    }
                });
                ConnectOperation.this.connectionStateChangedAction.onConnectionStateChange(RxBleConnection.RxBleConnectionState.CONNECTING);
                ConnectOperation.this.bluetoothGattProvider.updateBluetoothGatt(ConnectOperation.this.connectionCompat.connectGatt(ConnectOperation.this.bluetoothDevice, ConnectOperation.this.autoConnect, ConnectOperation.this.rxBleGattCallback.getBluetoothGattCallback()));
            }
        }, Emitter.BackpressureMode.NONE);
    }

    /* access modifiers changed from: protected */
    public BleException provideException(DeadObjectException deadObjectException) {
        return new BleDisconnectedException(deadObjectException, this.bluetoothDevice.getAddress(), -1);
    }
}
