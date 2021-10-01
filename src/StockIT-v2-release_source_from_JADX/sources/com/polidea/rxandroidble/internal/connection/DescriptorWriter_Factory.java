package com.polidea.rxandroidble.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.internal.operations.OperationsProvider;
import com.polidea.rxandroidble.internal.serialization.ConnectionOperationQueue;

public final class DescriptorWriter_Factory implements Factory<DescriptorWriter> {
    private final Provider<ConnectionOperationQueue> operationQueueProvider;
    private final Provider<OperationsProvider> operationsProvider;

    public DescriptorWriter_Factory(Provider<ConnectionOperationQueue> provider, Provider<OperationsProvider> provider2) {
        this.operationQueueProvider = provider;
        this.operationsProvider = provider2;
    }

    public DescriptorWriter get() {
        return new DescriptorWriter(this.operationQueueProvider.get(), this.operationsProvider.get());
    }

    public static DescriptorWriter_Factory create(Provider<ConnectionOperationQueue> provider, Provider<OperationsProvider> provider2) {
        return new DescriptorWriter_Factory(provider, provider2);
    }

    public static DescriptorWriter newDescriptorWriter(ConnectionOperationQueue connectionOperationQueue, OperationsProvider operationsProvider2) {
        return new DescriptorWriter(connectionOperationQueue, operationsProvider2);
    }
}
