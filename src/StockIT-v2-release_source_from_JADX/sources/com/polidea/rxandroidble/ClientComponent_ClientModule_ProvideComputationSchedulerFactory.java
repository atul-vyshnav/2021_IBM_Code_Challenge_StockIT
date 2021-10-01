package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;
import p030rx.Scheduler;

public final class ClientComponent_ClientModule_ProvideComputationSchedulerFactory implements Factory<Scheduler> {
    private static final ClientComponent_ClientModule_ProvideComputationSchedulerFactory INSTANCE = new ClientComponent_ClientModule_ProvideComputationSchedulerFactory();

    public Scheduler get() {
        return (Scheduler) Preconditions.checkNotNull(ClientComponent.ClientModule.provideComputationScheduler(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static ClientComponent_ClientModule_ProvideComputationSchedulerFactory create() {
        return INSTANCE;
    }

    public static Scheduler proxyProvideComputationScheduler() {
        return (Scheduler) Preconditions.checkNotNull(ClientComponent.ClientModule.provideComputationScheduler(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
