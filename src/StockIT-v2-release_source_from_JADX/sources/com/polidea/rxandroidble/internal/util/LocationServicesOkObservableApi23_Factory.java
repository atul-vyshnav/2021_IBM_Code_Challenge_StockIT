package com.polidea.rxandroidble.internal.util;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class LocationServicesOkObservableApi23_Factory implements Factory<LocationServicesOkObservableApi23> {
    private final Provider<Context> contextProvider;
    private final Provider<LocationServicesStatus> locationServicesStatusProvider;

    public LocationServicesOkObservableApi23_Factory(Provider<Context> provider, Provider<LocationServicesStatus> provider2) {
        this.contextProvider = provider;
        this.locationServicesStatusProvider = provider2;
    }

    public LocationServicesOkObservableApi23 get() {
        return new LocationServicesOkObservableApi23(this.contextProvider.get(), this.locationServicesStatusProvider.get());
    }

    public static LocationServicesOkObservableApi23_Factory create(Provider<Context> provider, Provider<LocationServicesStatus> provider2) {
        return new LocationServicesOkObservableApi23_Factory(provider, provider2);
    }

    public static LocationServicesOkObservableApi23 newLocationServicesOkObservableApi23(Context context, LocationServicesStatus locationServicesStatus) {
        return new LocationServicesOkObservableApi23(context, locationServicesStatus);
    }
}
