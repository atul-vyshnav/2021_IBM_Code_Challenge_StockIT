package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;
import java.util.concurrent.ExecutorService;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory */
public final class C2549x11ee2f55 implements Factory<ExecutorService> {
    private static final C2549x11ee2f55 INSTANCE = new C2549x11ee2f55();

    public ExecutorService get() {
        return (ExecutorService) Preconditions.checkNotNull(ClientComponent.ClientModule.provideConnectionQueueExecutorService(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2549x11ee2f55 create() {
        return INSTANCE;
    }

    public static ExecutorService proxyProvideConnectionQueueExecutorService() {
        return (ExecutorService) Preconditions.checkNotNull(ClientComponent.ClientModule.provideConnectionQueueExecutorService(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
