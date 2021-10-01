package com.polidea.rxandroidble.helpers;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import p030rx.Observable;

public final class LocationServicesOkObservable_Factory implements Factory<LocationServicesOkObservable> {
    private final Provider<Observable<Boolean>> locationServicesOkObsImplProvider;

    public LocationServicesOkObservable_Factory(Provider<Observable<Boolean>> provider) {
        this.locationServicesOkObsImplProvider = provider;
    }

    public LocationServicesOkObservable get() {
        return new LocationServicesOkObservable(this.locationServicesOkObsImplProvider.get());
    }

    public static LocationServicesOkObservable_Factory create(Provider<Observable<Boolean>> provider) {
        return new LocationServicesOkObservable_Factory(provider);
    }

    public static LocationServicesOkObservable newLocationServicesOkObservable(Observable<Boolean> observable) {
        return new LocationServicesOkObservable(observable);
    }
}
