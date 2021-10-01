package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay.PublishRelay;
import com.jakewharton.rxrelay.SerializedRelay;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble.exceptions.BleGattDescriptorException;
import com.polidea.rxandroidble.exceptions.BleGattException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import com.polidea.rxandroidble.internal.util.CharacteristicChangedEvent;
import java.util.UUID;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Func1;

@ConnectionScope
public class RxBleGattCallback {
    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        private boolean isDisconnectedOrDisconnecting(int i) {
            return i == 0 || i == 3;
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            RxBleLog.m1110d("onConnectionStateChange newState=%d status=%d", Integer.valueOf(i2), Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeConnectionStateCallback(bluetoothGatt, i, i2);
            super.onConnectionStateChange(bluetoothGatt, i, i2);
            RxBleGattCallback.this.bluetoothGattProvider.updateBluetoothGatt(bluetoothGatt);
            if (isDisconnectedOrDisconnecting(i2)) {
                RxBleGattCallback.this.disconnectionRouter.onDisconnectedException(new BleDisconnectedException(bluetoothGatt.getDevice().getAddress(), i));
            } else if (i != 0) {
                RxBleGattCallback.this.disconnectionRouter.onGattConnectionStateException(new BleGattException(bluetoothGatt, i, BleGattOperationType.CONNECTION_STATE));
            }
            RxBleGattCallback.this.connectionStatePublishRelay.call(RxBleGattCallback.this.mapConnectionStateToRxBleConnectionStatus(i2));
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            RxBleLog.m1110d("onServicesDiscovered status=%d", Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeServicesDiscoveredCallback(bluetoothGatt, i);
            super.onServicesDiscovered(bluetoothGatt, i);
            if (RxBleGattCallback.this.servicesDiscoveredOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.servicesDiscoveredOutput, bluetoothGatt, i, BleGattOperationType.SERVICE_DISCOVERY)) {
                    RxBleGattCallback.this.servicesDiscoveredOutput.valueRelay.call(new RxBleDeviceServices(bluetoothGatt.getServices()));
                }
            }
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            RxBleLog.m1110d("onCharacteristicRead characteristic=%s status=%d", bluetoothGattCharacteristic.getUuid(), Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReadCallback(bluetoothGatt, bluetoothGattCharacteristic, i);
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (RxBleGattCallback.this.readCharacteristicOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.readCharacteristicOutput, bluetoothGatt, bluetoothGattCharacteristic, i, BleGattOperationType.CHARACTERISTIC_READ)) {
                    RxBleGattCallback.this.readCharacteristicOutput.valueRelay.call(new ByteAssociation(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue()));
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            RxBleLog.m1110d("onCharacteristicWrite characteristic=%s status=%d", bluetoothGattCharacteristic.getUuid(), Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeWriteCallback(bluetoothGatt, bluetoothGattCharacteristic, i);
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (RxBleGattCallback.this.writeCharacteristicOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.writeCharacteristicOutput, bluetoothGatt, bluetoothGattCharacteristic, i, BleGattOperationType.CHARACTERISTIC_WRITE)) {
                    RxBleGattCallback.this.writeCharacteristicOutput.valueRelay.call(new ByteAssociation(bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getValue()));
                }
            }
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            RxBleLog.m1110d("onCharacteristicChanged characteristic=%s", bluetoothGattCharacteristic.getUuid());
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeChangedCallback(bluetoothGatt, bluetoothGattCharacteristic);
            super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
            if (RxBleGattCallback.this.changedCharacteristicSerializedPublishRelay.hasObservers()) {
                RxBleGattCallback.this.changedCharacteristicSerializedPublishRelay.call(new CharacteristicChangedEvent(bluetoothGattCharacteristic.getUuid(), Integer.valueOf(bluetoothGattCharacteristic.getInstanceId()), bluetoothGattCharacteristic.getValue()));
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            RxBleLog.m1110d("onCharacteristicRead descriptor=%s status=%d", bluetoothGattDescriptor.getUuid(), Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeDescriptorReadCallback(bluetoothGatt, bluetoothGattDescriptor, i);
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
            if (RxBleGattCallback.this.readDescriptorOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.readDescriptorOutput, bluetoothGatt, bluetoothGattDescriptor, i, BleGattOperationType.DESCRIPTOR_READ)) {
                    RxBleGattCallback.this.readDescriptorOutput.valueRelay.call(new ByteAssociation(bluetoothGattDescriptor, bluetoothGattDescriptor.getValue()));
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            RxBleLog.m1110d("onDescriptorWrite descriptor=%s status=%d", bluetoothGattDescriptor.getUuid(), Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeDescriptorWriteCallback(bluetoothGatt, bluetoothGattDescriptor, i);
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
            if (RxBleGattCallback.this.writeDescriptorOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.writeDescriptorOutput, bluetoothGatt, bluetoothGattDescriptor, i, BleGattOperationType.DESCRIPTOR_WRITE)) {
                    RxBleGattCallback.this.writeDescriptorOutput.valueRelay.call(new ByteAssociation(bluetoothGattDescriptor, bluetoothGattDescriptor.getValue()));
                }
            }
        }

        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
            RxBleLog.m1110d("onReliableWriteCompleted status=%d", Integer.valueOf(i));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReliableWriteCallback(bluetoothGatt, i);
            super.onReliableWriteCompleted(bluetoothGatt, i);
        }

        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            RxBleLog.m1110d("onReadRemoteRssi rssi=%d status=%d", Integer.valueOf(i), Integer.valueOf(i2));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeReadRssiCallback(bluetoothGatt, i, i2);
            super.onReadRemoteRssi(bluetoothGatt, i, i2);
            if (RxBleGattCallback.this.readRssiOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.readRssiOutput, bluetoothGatt, i2, BleGattOperationType.READ_RSSI)) {
                    RxBleGattCallback.this.readRssiOutput.valueRelay.call(Integer.valueOf(i));
                }
            }
        }

        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            RxBleLog.m1110d("onMtuChanged mtu=%d status=%d", Integer.valueOf(i), Integer.valueOf(i2));
            RxBleGattCallback.this.nativeCallbackDispatcher.notifyNativeMtuChangedCallback(bluetoothGatt, i, i2);
            super.onMtuChanged(bluetoothGatt, i, i2);
            if (RxBleGattCallback.this.changedMtuOutput.hasObservers()) {
                RxBleGattCallback rxBleGattCallback = RxBleGattCallback.this;
                if (!rxBleGattCallback.propagateErrorIfOccurred(rxBleGattCallback.changedMtuOutput, bluetoothGatt, i2, BleGattOperationType.ON_MTU_CHANGED)) {
                    RxBleGattCallback.this.changedMtuOutput.valueRelay.call(Integer.valueOf(i));
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final BluetoothGattProvider bluetoothGattProvider;
    private final Scheduler callbackScheduler;
    /* access modifiers changed from: private */
    public final SerializedRelay<CharacteristicChangedEvent, CharacteristicChangedEvent> changedCharacteristicSerializedPublishRelay = PublishRelay.create().toSerialized();
    /* access modifiers changed from: private */
    public final Output<Integer> changedMtuOutput = new Output<>();
    /* access modifiers changed from: private */
    public final PublishRelay<RxBleConnection.RxBleConnectionState> connectionStatePublishRelay = PublishRelay.create();
    /* access modifiers changed from: private */
    public final DisconnectionRouter disconnectionRouter;
    private final Func1<BleGattException, Observable<?>> errorMapper = new Func1<BleGattException, Observable<?>>() {
        public Observable<?> call(BleGattException bleGattException) {
            return Observable.error(bleGattException);
        }
    };
    /* access modifiers changed from: private */
    public final NativeCallbackDispatcher nativeCallbackDispatcher;
    /* access modifiers changed from: private */
    public final Output<ByteAssociation<UUID>> readCharacteristicOutput = new Output<>();
    /* access modifiers changed from: private */
    public final Output<ByteAssociation<BluetoothGattDescriptor>> readDescriptorOutput = new Output<>();
    /* access modifiers changed from: private */
    public final Output<Integer> readRssiOutput = new Output<>();
    /* access modifiers changed from: private */
    public final Output<RxBleDeviceServices> servicesDiscoveredOutput = new Output<>();
    /* access modifiers changed from: private */
    public final Output<ByteAssociation<UUID>> writeCharacteristicOutput = new Output<>();
    /* access modifiers changed from: private */
    public final Output<ByteAssociation<BluetoothGattDescriptor>> writeDescriptorOutput = new Output<>();

    private boolean isException(int i) {
        return i != 0;
    }

    @Inject
    public RxBleGattCallback(@Named("bluetooth_callbacks") Scheduler scheduler, BluetoothGattProvider bluetoothGattProvider2, DisconnectionRouter disconnectionRouter2, NativeCallbackDispatcher nativeCallbackDispatcher2) {
        this.callbackScheduler = scheduler;
        this.bluetoothGattProvider = bluetoothGattProvider2;
        this.disconnectionRouter = disconnectionRouter2;
        this.nativeCallbackDispatcher = nativeCallbackDispatcher2;
    }

    /* access modifiers changed from: private */
    public RxBleConnection.RxBleConnectionState mapConnectionStateToRxBleConnectionStatus(int i) {
        if (i == 1) {
            return RxBleConnection.RxBleConnectionState.CONNECTING;
        }
        if (i == 2) {
            return RxBleConnection.RxBleConnectionState.CONNECTED;
        }
        if (i != 3) {
            return RxBleConnection.RxBleConnectionState.DISCONNECTED;
        }
        return RxBleConnection.RxBleConnectionState.DISCONNECTING;
    }

    /* access modifiers changed from: private */
    public boolean propagateErrorIfOccurred(Output output, BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattCharacteristicException(bluetoothGatt, bluetoothGattCharacteristic, i, bleGattOperationType));
    }

    /* access modifiers changed from: private */
    public boolean propagateErrorIfOccurred(Output output, BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattDescriptorException(bluetoothGatt, bluetoothGattDescriptor, i, bleGattOperationType));
    }

    /* access modifiers changed from: private */
    public boolean propagateErrorIfOccurred(Output output, BluetoothGatt bluetoothGatt, int i, BleGattOperationType bleGattOperationType) {
        return isException(i) && propagateStatusError(output, new BleGattException(bluetoothGatt, i, bleGattOperationType));
    }

    private boolean propagateStatusError(Output output, BleGattException bleGattException) {
        output.errorRelay.call(bleGattException);
        return true;
    }

    private <T> Observable<T> withDisconnectionHandling(Output<T> output) {
        return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), output.valueRelay, output.errorRelay.flatMap(this.errorMapper));
    }

    public BluetoothGattCallback getBluetoothGattCallback() {
        return this.bluetoothGattCallback;
    }

    public <T> Observable<T> observeDisconnect() {
        return this.disconnectionRouter.asErrorOnlyObservable();
    }

    public Observable<RxBleConnection.RxBleConnectionState> getOnConnectionStateChange() {
        return this.connectionStatePublishRelay.observeOn(this.callbackScheduler);
    }

    public Observable<RxBleDeviceServices> getOnServicesDiscovered() {
        return withDisconnectionHandling(this.servicesDiscoveredOutput).observeOn(this.callbackScheduler);
    }

    public Observable<Integer> getOnMtuChanged() {
        return withDisconnectionHandling(this.changedMtuOutput).observeOn(this.callbackScheduler);
    }

    public Observable<ByteAssociation<UUID>> getOnCharacteristicRead() {
        return withDisconnectionHandling(this.readCharacteristicOutput).observeOn(this.callbackScheduler);
    }

    public Observable<ByteAssociation<UUID>> getOnCharacteristicWrite() {
        return withDisconnectionHandling(this.writeCharacteristicOutput).observeOn(this.callbackScheduler);
    }

    public Observable<CharacteristicChangedEvent> getOnCharacteristicChanged() {
        return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), (Observable) this.changedCharacteristicSerializedPublishRelay).observeOn(this.callbackScheduler);
    }

    public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorRead() {
        return withDisconnectionHandling(this.readDescriptorOutput).observeOn(this.callbackScheduler);
    }

    public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorWrite() {
        return withDisconnectionHandling(this.writeDescriptorOutput).observeOn(this.callbackScheduler);
    }

    public Observable<Integer> getOnRssiRead() {
        return withDisconnectionHandling(this.readRssiOutput).observeOn(this.callbackScheduler);
    }

    public void setNativeCallback(BluetoothGattCallback bluetoothGattCallback2) {
        this.nativeCallbackDispatcher.setNativeCallback(bluetoothGattCallback2);
    }

    private static class Output<T> {
        final PublishRelay<BleGattException> errorRelay = PublishRelay.create();
        final PublishRelay<T> valueRelay = PublishRelay.create();

        Output() {
        }

        /* access modifiers changed from: package-private */
        public boolean hasObservers() {
            return this.valueRelay.hasObservers() || this.errorRelay.hasObservers();
        }
    }
}
