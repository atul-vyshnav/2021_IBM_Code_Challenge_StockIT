package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideDisableNotificationValueFactory */
public final class C2550x975cafc6 implements Factory<byte[]> {
    private static final C2550x975cafc6 INSTANCE = new C2550x975cafc6();

    public byte[] get() {
        return (byte[]) Preconditions.checkNotNull(ClientComponent.ClientModule.provideDisableNotificationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2550x975cafc6 create() {
        return INSTANCE;
    }

    public static byte[] proxyProvideDisableNotificationValue() {
        return (byte[]) Preconditions.checkNotNull(ClientComponent.ClientModule.provideDisableNotificationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
