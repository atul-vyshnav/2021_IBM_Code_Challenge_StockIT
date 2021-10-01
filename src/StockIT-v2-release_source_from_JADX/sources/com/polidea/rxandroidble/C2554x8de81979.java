package com.polidea.rxandroidble;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ClientComponent;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifierApi24;

/* renamed from: com.polidea.rxandroidble.ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory */
public final class C2554x8de81979 implements Factory<ScanPreconditionsVerifier> {
    private final Provider<Integer> deviceSdkProvider;
    private final Provider<ScanPreconditionsVerifierApi18> scanPreconditionVerifierForApi18Provider;
    private final Provider<ScanPreconditionsVerifierApi24> scanPreconditionVerifierForApi24Provider;

    public C2554x8de81979(Provider<Integer> provider, Provider<ScanPreconditionsVerifierApi18> provider2, Provider<ScanPreconditionsVerifierApi24> provider3) {
        this.deviceSdkProvider = provider;
        this.scanPreconditionVerifierForApi18Provider = provider2;
        this.scanPreconditionVerifierForApi24Provider = provider3;
    }

    public ScanPreconditionsVerifier get() {
        return (ScanPreconditionsVerifier) Preconditions.checkNotNull(ClientComponent.ClientModule.provideScanPreconditionVerifier(this.deviceSdkProvider.get().intValue(), this.scanPreconditionVerifierForApi18Provider, this.scanPreconditionVerifierForApi24Provider), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static C2554x8de81979 create(Provider<Integer> provider, Provider<ScanPreconditionsVerifierApi18> provider2, Provider<ScanPreconditionsVerifierApi24> provider3) {
        return new C2554x8de81979(provider, provider2, provider3);
    }

    public static ScanPreconditionsVerifier proxyProvideScanPreconditionVerifier(int i, Provider<ScanPreconditionsVerifierApi18> provider, Provider<ScanPreconditionsVerifierApi24> provider2) {
        return (ScanPreconditionsVerifier) Preconditions.checkNotNull(ClientComponent.ClientModule.provideScanPreconditionVerifier(i, provider, provider2), "Cannot return null from a non-@Nullable @Provides method");
    }
}
