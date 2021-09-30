package com.polidea.rxandroidble;

import android.content.ContentResolver;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble.ClientComponent;

public final class ClientComponent_ClientModule_ProvideContentResolverFactory implements Factory<ContentResolver> {
    private final ClientComponent.ClientModule module;

    public ClientComponent_ClientModule_ProvideContentResolverFactory(ClientComponent.ClientModule clientModule) {
        this.module = clientModule;
    }

    public ContentResolver get() {
        return (ContentResolver) Preconditions.checkNotNull(this.module.provideContentResolver(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static ClientComponent_ClientModule_ProvideContentResolverFactory create(ClientComponent.ClientModule clientModule) {
        return new ClientComponent_ClientModule_ProvideContentResolverFactory(clientModule);
    }

    public static ContentResolver proxyProvideContentResolver(ClientComponent.ClientModule clientModule) {
        return (ContentResolver) Preconditions.checkNotNull(clientModule.provideContentResolver(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
