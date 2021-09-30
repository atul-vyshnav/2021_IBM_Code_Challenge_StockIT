package com.polidea.rxandroidble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble.exceptions.BleGattException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import p030rx.Completable;
import p030rx.Observable;

public interface RxBleConnection {
    public static final int GATT_MTU_MAXIMUM = 517;
    public static final int GATT_MTU_MINIMUM = 23;
    public static final int GATT_READ_MTU_OVERHEAD = 1;
    public static final int GATT_WRITE_MTU_OVERHEAD = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionPriority {
    }

    @Deprecated
    public interface Connector {
        Observable<RxBleConnection> prepareConnection(boolean z);
    }

    public interface LongWriteOperationBuilder {
        Observable<byte[]> build();

        LongWriteOperationBuilder setBytes(byte[] bArr);

        LongWriteOperationBuilder setCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic);

        LongWriteOperationBuilder setCharacteristicUuid(UUID uuid);

        LongWriteOperationBuilder setMaxBatchSize(int i);

        LongWriteOperationBuilder setWriteOperationAckStrategy(WriteOperationAckStrategy writeOperationAckStrategy);

        LongWriteOperationBuilder setWriteOperationRetryStrategy(WriteOperationRetryStrategy writeOperationRetryStrategy);
    }

    public interface WriteOperationAckStrategy extends Observable.Transformer<Boolean, Boolean> {
    }

    LongWriteOperationBuilder createNewLongWriteBuilder();

    Observable<RxBleDeviceServices> discoverServices();

    Observable<RxBleDeviceServices> discoverServices(long j, TimeUnit timeUnit);

    Observable<BluetoothGattCharacteristic> getCharacteristic(UUID uuid);

    int getMtu();

    <T> Observable<T> queue(RxBleCustomOperation<T> rxBleCustomOperation);

    Observable<byte[]> readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<byte[]> readCharacteristic(UUID uuid);

    Observable<byte[]> readDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor);

    Observable<byte[]> readDescriptor(UUID uuid, UUID uuid2, UUID uuid3);

    Observable<Integer> readRssi();

    Completable requestConnectionPriority(int i, long j, TimeUnit timeUnit);

    Observable<Integer> requestMtu(int i);

    Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic bluetoothGattCharacteristic, NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupIndication(UUID uuid);

    Observable<Observable<byte[]>> setupIndication(UUID uuid, NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupNotification(UUID uuid);

    Observable<Observable<byte[]>> setupNotification(UUID uuid, NotificationSetupMode notificationSetupMode);

    @Deprecated
    Observable<BluetoothGattCharacteristic> writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<byte[]> writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr);

    Observable<byte[]> writeCharacteristic(UUID uuid, byte[] bArr);

    Observable<byte[]> writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor, byte[] bArr);

    Observable<byte[]> writeDescriptor(UUID uuid, UUID uuid2, UUID uuid3, byte[] bArr);

    public enum RxBleConnectionState {
        CONNECTING("CONNECTING"),
        CONNECTED("CONNECTED"),
        DISCONNECTED("DISCONNECTED"),
        DISCONNECTING("DISCONNECTING");
        
        private final String description;

        private RxBleConnectionState(String str) {
            this.description = str;
        }

        public String toString() {
            return "RxBleConnectionState{" + this.description + '}';
        }
    }

    public interface WriteOperationRetryStrategy extends Observable.Transformer<LongWriteFailure, LongWriteFailure> {

        public static class LongWriteFailure {
            final int batchIndex;
            final BleGattException cause;

            public LongWriteFailure(int i, BleGattException bleGattException) {
                this.batchIndex = i;
                this.cause = bleGattException;
            }

            public int getBatchIndex() {
                return this.batchIndex;
            }

            public BleGattException getCause() {
                return this.cause;
            }
        }
    }
}
