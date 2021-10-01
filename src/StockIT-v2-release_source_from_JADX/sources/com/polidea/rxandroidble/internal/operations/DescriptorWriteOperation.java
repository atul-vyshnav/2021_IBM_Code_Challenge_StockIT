package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import p030rx.Observable;
import p030rx.functions.Func1;

public class DescriptorWriteOperation extends SingleResponseOperation<byte[]> {
    private final int bluetoothGattCharacteristicDefaultWriteType;
    /* access modifiers changed from: private */
    public BluetoothGattDescriptor bluetoothGattDescriptor;
    private byte[] data;

    DescriptorWriteOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration, int i, BluetoothGattDescriptor bluetoothGattDescriptor2, byte[] bArr) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.DESCRIPTOR_WRITE, timeoutConfiguration);
        this.bluetoothGattCharacteristicDefaultWriteType = i;
        this.bluetoothGattDescriptor = bluetoothGattDescriptor2;
        this.data = bArr;
    }

    /* access modifiers changed from: protected */
    public Observable<byte[]> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnDescriptorWrite().filter(new Func1<ByteAssociation<BluetoothGattDescriptor>, Boolean>() {
            public Boolean call(ByteAssociation<BluetoothGattDescriptor> byteAssociation) {
                return Boolean.valueOf(((BluetoothGattDescriptor) byteAssociation.first).equals(DescriptorWriteOperation.this.bluetoothGattDescriptor));
            }
        }).map(new Func1<ByteAssociation<BluetoothGattDescriptor>, byte[]>() {
            public byte[] call(ByteAssociation<BluetoothGattDescriptor> byteAssociation) {
                return byteAssociation.second;
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) {
        this.bluetoothGattDescriptor.setValue(this.data);
        BluetoothGattCharacteristic characteristic = this.bluetoothGattDescriptor.getCharacteristic();
        int writeType = characteristic.getWriteType();
        characteristic.setWriteType(this.bluetoothGattCharacteristicDefaultWriteType);
        boolean writeDescriptor = bluetoothGatt.writeDescriptor(this.bluetoothGattDescriptor);
        characteristic.setWriteType(writeType);
        return writeDescriptor;
    }
}
