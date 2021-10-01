package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ClientComponent;
import com.polidea.rxandroidble.internal.util.LocationServicesOkObservableApi23;
import p030rx.Observable;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory */
public final class C2552x61f40e72 implements Factory<Observable<Boolean>> {
    private final Provider<Integer> deviceSdkProvider;
    private final Provider<LocationServicesOkObservableApi23> locationServicesOkObservableApi23Provider;
    private final ClientComponent.ClientModule module;

    public C2552x61f40e72(ClientComponent.ClientModule clientModule, Provider<Integer> provider, Provider<LocationServicesOkObservableApi23> provider2) {
        this.module = clientModule;
        this.deviceSdkProvider = provider;
        this.locationServicesOkObservableApi23Provider = provider2;
    }

    public Observable<Boolean> get() {
        return (Observable) Preconditions.checkNotNull(this.module.provideLocationServicesOkObservable(this.deviceSdkProvider.get().intValue(), this.locationServicesOkObservableApi23Provider), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2552x61f40e72 create(ClientComponent.ClientModule clientModule, Provider<Integer> provider, Provider<LocationServicesOkObservableApi23> provider2) {
        return new C2552x61f40e72(clientModule, provider, provider2);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [bleshadow.javax.inject.Provider, bleshadow.javax.inject.Provider<com.polidea.rxandroidble.internal.util.LocationServicesOkObservableApi23>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static p030rx.Observable<java.lang.Boolean> proxyProvideLocationServicesOkObservable(com.polidea.rxandroidble.ClientComponent.ClientModule r0, int r1, bleshadow.javax.inject.Provider<com.polidea.rxandroidble.internal.util.LocationServicesOkObservableApi23> r2) {
        /*
            rx.Observable r0 = r0.provideLocationServicesOkObservable(r1, r2)
            java.lang.String r1 = "Cannot return null from a non-@Nullable @Provides method"
            java.lang.Object r0 = bleshadow.dagger.internal.Preconditions.checkNotNull(r0, r1)
            rx.Observable r0 = (p030rx.Observable) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.polidea.rxandroidble.C2552x61f40e72.proxyProvideLocationServicesOkObservable(com.polidea.rxandroidble.ClientComponent$ClientModule, int, bleshadow.javax.inject.Provider):rx.Observable");
    }
}
