package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ClientComponent;
import com.polidea.rxandroidble.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble.internal.util.LocationServicesStatusApi18;
import com.polidea.rxandroidble.internal.util.LocationServicesStatusApi23;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideLocationServicesStatusFactory */
public final class C2553x9cd4449f implements Factory<LocationServicesStatus> {
    private final Provider<Integer> deviceSdkProvider;
    private final Provider<LocationServicesStatusApi18> locationServicesStatusApi18Provider;
    private final Provider<LocationServicesStatusApi23> locationServicesStatusApi23Provider;
    private final ClientComponent.ClientModule module;

    public C2553x9cd4449f(ClientComponent.ClientModule clientModule, Provider<Integer> provider, Provider<LocationServicesStatusApi18> provider2, Provider<LocationServicesStatusApi23> provider3) {
        this.module = clientModule;
        this.deviceSdkProvider = provider;
        this.locationServicesStatusApi18Provider = provider2;
        this.locationServicesStatusApi23Provider = provider3;
    }

    public LocationServicesStatus get() {
        return (LocationServicesStatus) Preconditions.checkNotNull(this.module.provideLocationServicesStatus(this.deviceSdkProvider.get().intValue(), this.locationServicesStatusApi18Provider, this.locationServicesStatusApi23Provider), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2553x9cd4449f create(ClientComponent.ClientModule clientModule, Provider<Integer> provider, Provider<LocationServicesStatusApi18> provider2, Provider<LocationServicesStatusApi23> provider3) {
        return new C2553x9cd4449f(clientModule, provider, provider2, provider3);
    }

    public static LocationServicesStatus proxyProvideLocationServicesStatus(ClientComponent.ClientModule clientModule, int i, Provider<LocationServicesStatusApi18> provider, Provider<LocationServicesStatusApi23> provider2) {
        return (LocationServicesStatus) Preconditions.checkNotNull(clientModule.provideLocationServicesStatus(i, provider, provider2), "Cannot return null from a non-@Nullable @Provides method");
    }
}
