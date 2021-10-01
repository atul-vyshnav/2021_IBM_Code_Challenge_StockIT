package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.operations.OperationsProvider;
import com.polidea.rxandroidble.internal.serialization.ConnectionOperationQueue;
import p030rx.Observable;

@ConnectionScope
class DescriptorWriter {
    private final ConnectionOperationQueue operationQueue;
    private final OperationsProvider operationsProvider;

    @Inject
    DescriptorWriter(ConnectionOperationQueue connectionOperationQueue, OperationsProvider operationsProvider2) {
        this.operationQueue = connectionOperationQueue;
        this.operationsProvider = operationsProvider2;
    }

    /* access modifiers changed from: package-private */
    public Observable<byte[]> writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor, byte[] bArr) {
        return this.operationQueue.queue(this.operationsProvider.provideWriteDescriptor(bluetoothGattDescriptor, bArr));
    }
}
