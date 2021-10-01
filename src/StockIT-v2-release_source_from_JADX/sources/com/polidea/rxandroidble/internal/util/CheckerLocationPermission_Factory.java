package com.polidea.rxandroidble.internal.util;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class CheckerLocationPermission_Factory implements Factory<CheckerLocationPermission> {
    private final Provider<Context> contextProvider;

    public CheckerLocationPermission_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public CheckerLocationPermission get() {
        return new CheckerLocationPermission(this.contextProvider.get());
    }

    public static CheckerLocationPermission_Factory create(Provider<Context> provider) {
        return new CheckerLocationPermission_Factory(provider);
    }
}
