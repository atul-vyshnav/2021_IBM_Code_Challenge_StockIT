package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;
import java.util.concurrent.ExecutorService;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideBluetoothCallbacksExecutorServiceFactory */
public final class C2545x4a2ba98e implements Factory<ExecutorService> {
    private static final C2545x4a2ba98e INSTANCE = new C2545x4a2ba98e();

    public ExecutorService get() {
        return (ExecutorService) Preconditions.checkNotNull(ClientComponent.ClientModule.provideBluetoothCallbacksExecutorService(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2545x4a2ba98e create() {
        return INSTANCE;
    }

    public static ExecutorService proxyProvideBluetoothCallbacksExecutorService() {
        return (ExecutorService) Preconditions.checkNotNull(ClientComponent.ClientModule.provideBluetoothCallbacksExecutorService(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
