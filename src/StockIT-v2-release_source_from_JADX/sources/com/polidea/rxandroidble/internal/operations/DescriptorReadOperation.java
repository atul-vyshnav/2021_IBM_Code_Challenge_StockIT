package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import p030rx.Observable;
import p030rx.functions.Func1;

public class DescriptorReadOperation extends SingleResponseOperation<ByteAssociation<BluetoothGattDescriptor>> {
    /* access modifiers changed from: private */
    public final BluetoothGattDescriptor bluetoothGattDescriptor;

    @Inject
    DescriptorReadOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration, BluetoothGattDescriptor bluetoothGattDescriptor2) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.DESCRIPTOR_READ, timeoutConfiguration);
        this.bluetoothGattDescriptor = bluetoothGattDescriptor2;
    }

    /* access modifiers changed from: protected */
    public Observable<ByteAssociation<BluetoothGattDescriptor>> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnDescriptorRead().filter(new Func1<ByteAssociation<BluetoothGattDescriptor>, Boolean>() {
            public Boolean call(ByteAssociation<BluetoothGattDescriptor> byteAssociation) {
                return Boolean.valueOf(((BluetoothGattDescriptor) byteAssociation.first).equals(DescriptorReadOperation.this.bluetoothGattDescriptor));
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.readDescriptor(this.bluetoothGattDescriptor);
    }
}
