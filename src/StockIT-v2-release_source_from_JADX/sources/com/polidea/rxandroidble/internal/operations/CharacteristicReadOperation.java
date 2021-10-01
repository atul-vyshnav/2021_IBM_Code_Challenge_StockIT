package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import java.util.UUID;
import p030rx.Observable;
import p030rx.functions.Func1;

public class CharacteristicReadOperation extends SingleResponseOperation<byte[]> {
    /* access modifiers changed from: private */
    public final BluetoothGattCharacteristic bluetoothGattCharacteristic;

    CharacteristicReadOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration, BluetoothGattCharacteristic bluetoothGattCharacteristic2) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.CHARACTERISTIC_READ, timeoutConfiguration);
        this.bluetoothGattCharacteristic = bluetoothGattCharacteristic2;
    }

    /* access modifiers changed from: protected */
    public Observable<byte[]> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnCharacteristicRead().filter(new Func1<ByteAssociation<UUID>, Boolean>() {
            public Boolean call(ByteAssociation<UUID> byteAssociation) {
                return Boolean.valueOf(((UUID) byteAssociation.first).equals(CharacteristicReadOperation.this.bluetoothGattCharacteristic.getUuid()));
            }
        }).map(new Func1<ByteAssociation<UUID>, byte[]>() {
            public byte[] call(ByteAssociation<UUID> byteAssociation) {
                return byteAssociation.second;
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.readCharacteristic(this.bluetoothGattCharacteristic);
    }
}
