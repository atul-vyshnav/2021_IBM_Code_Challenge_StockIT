package com.polidea.rxandroidble.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.internal.util.UUIDUtil;

public final class InternalScanResultCreator_Factory implements Factory<InternalScanResultCreator> {
    private final Provider<UUIDUtil> uuidUtilProvider;

    public InternalScanResultCreator_Factory(Provider<UUIDUtil> provider) {
        this.uuidUtilProvider = provider;
    }

    public InternalScanResultCreator get() {
        return new InternalScanResultCreator(this.uuidUtilProvider.get());
    }

    public static InternalScanResultCreator_Factory create(Provider<UUIDUtil> provider) {
        return new InternalScanResultCreator_Factory(provider);
    }
}
