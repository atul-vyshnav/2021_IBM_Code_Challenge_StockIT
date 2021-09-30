package com.polidea.rxandroidble.internal.serialization;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.internal.connection.DisconnectionRouterOutput;
import java.util.concurrent.ExecutorService;
import p030rx.Scheduler;

public final class ConnectionOperationQueueImpl_Factory implements Factory<ConnectionOperationQueueImpl> {
    private final Provider<Scheduler> callbackSchedulerProvider;
    private final Provider<String> deviceMacAddressProvider;
    private final Provider<DisconnectionRouterOutput> disconnectionRouterOutputProvider;
    private final Provider<ExecutorService> executorServiceProvider;

    public ConnectionOperationQueueImpl_Factory(Provider<String> provider, Provider<DisconnectionRouterOutput> provider2, Provider<ExecutorService> provider3, Provider<Scheduler> provider4) {
        this.deviceMacAddressProvider = provider;
        this.disconnectionRouterOutputProvider = provider2;
        this.executorServiceProvider = provider3;
        this.callbackSchedulerProvider = provider4;
    }

    public ConnectionOperationQueueImpl get() {
        return new ConnectionOperationQueueImpl(this.deviceMacAddressProvider.get(), this.disconnectionRouterOutputProvider.get(), this.executorServiceProvider.get(), this.callbackSchedulerProvider.get());
    }

    public static ConnectionOperationQueueImpl_Factory create(Provider<String> provider, Provider<DisconnectionRouterOutput> provider2, Provider<ExecutorService> provider3, Provider<Scheduler> provider4) {
        return new ConnectionOperationQueueImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static ConnectionOperationQueueImpl newConnectionOperationQueueImpl(String str, DisconnectionRouterOutput disconnectionRouterOutput, ExecutorService executorService, Scheduler scheduler) {
        return new ConnectionOperationQueueImpl(str, disconnectionRouterOutput, executorService, scheduler);
    }
}
