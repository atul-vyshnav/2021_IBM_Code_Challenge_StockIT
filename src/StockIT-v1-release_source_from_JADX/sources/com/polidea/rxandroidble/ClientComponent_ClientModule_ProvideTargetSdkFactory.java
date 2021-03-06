package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import com.polidea.rxandroidble.ClientComponent;

public final class ClientComponent_ClientModule_ProvideTargetSdkFactory implements Factory<Integer> {
    private final ClientComponent.ClientModule module;

    public ClientComponent_ClientModule_ProvideTargetSdkFactory(ClientComponent.ClientModule clientModule) {
        this.module = clientModule;
    }

    public Integer get() {
        return Integer.valueOf(this.module.provideTargetSdk());
    }

    public static ClientComponent_ClientModule_ProvideTargetSdkFactory create(ClientComponent.ClientModule clientModule) {
        return new ClientComponent_ClientModule_ProvideTargetSdkFactory(clientModule);
    }

    public static int proxyProvideTargetSdk(ClientComponent.ClientModule clientModule) {
        return clientModule.provideTargetSdk();
    }
}
