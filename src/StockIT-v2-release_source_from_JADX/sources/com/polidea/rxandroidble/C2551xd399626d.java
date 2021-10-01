package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideEnableNotificationValueFactory */
public final class C2551xd399626d implements Factory<byte[]> {
    private static final C2551xd399626d INSTANCE = new C2551xd399626d();

    public byte[] get() {
        return (byte[]) Preconditions.checkNotNull(ClientComponent.ClientModule.provideEnableNotificationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2551xd399626d create() {
        return INSTANCE;
    }

    public static byte[] proxyProvideEnableNotificationValue() {
        return (byte[]) Preconditions.checkNotNull(ClientComponent.ClientModule.provideEnableNotificationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
