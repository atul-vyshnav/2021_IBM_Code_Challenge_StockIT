package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ClientComponent;
import java.util.concurrent.ExecutorService;
import p030rx.Scheduler;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory */
public final class C2548xd84c18d9 implements Factory<Scheduler> {
    private final Provider<ExecutorService> serviceProvider;

    public C2548xd84c18d9(Provider<ExecutorService> provider) {
        this.serviceProvider = provider;
    }

    public Scheduler get() {
        return (Scheduler) Preconditions.checkNotNull(ClientComponent.ClientModule.provideBluetoothInteractionScheduler(this.serviceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2548xd84c18d9 create(Provider<ExecutorService> provider) {
        return new C2548xd84c18d9(provider);
    }

    public static Scheduler proxyProvideBluetoothInteractionScheduler(ExecutorService executorService) {
        return (Scheduler) Preconditions.checkNotNull(ClientComponent.ClientModule.provideBluetoothInteractionScheduler(executorService), "Cannot return null from a non-@Nullable @Provides method");
    }
}
