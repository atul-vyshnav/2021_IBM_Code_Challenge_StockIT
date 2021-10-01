package com.polidea.rxandroidble.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay.PublishRelay;
import com.polidea.rxandroidble.RxBleAdapterStateObservable;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.exceptions.BleGattException;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;
import p030rx.Observable;
import p030rx.functions.Func1;

@ConnectionScope
class DisconnectionRouter implements DisconnectionRouterInput, DisconnectionRouterOutput {
    private final PublishRelay<BleException> disconnectionErrorInputRelay = PublishRelay.create();
    private final Observable<BleException> disconnectionErrorOutputObservable;

    @Inject
    DisconnectionRouter(@Named("mac-address") final String str, RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable) {
        Observable<BleException> cache = Observable.merge(this.disconnectionErrorInputRelay, (PublishRelay<BleException>) observable.map(new Func1<RxBleAdapterStateObservable.BleAdapterState, Boolean>() {
            public Boolean call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Boolean.valueOf(bleAdapterState.isUsable());
            }
        }).startWith(Boolean.valueOf(rxBleAdapterWrapper.isBluetoothEnabled())).filter(new Func1<Boolean, Boolean>() {
            public Boolean call(Boolean bool) {
                return Boolean.valueOf(!bool.booleanValue());
            }
        }).map(new Func1<Boolean, BleException>() {
            public BleException call(Boolean bool) {
                return BleDisconnectedException.adapterDisabled(str);
            }
        })).first().cache();
        this.disconnectionErrorOutputObservable = cache;
        cache.subscribe();
    }

    public void onDisconnectedException(BleDisconnectedException bleDisconnectedException) {
        this.disconnectionErrorInputRelay.call(bleDisconnectedException);
    }

    public void onGattConnectionStateException(BleGattException bleGattException) {
        this.disconnectionErrorInputRelay.call(bleGattException);
    }

    public Observable<BleException> asValueOnlyObservable() {
        return this.disconnectionErrorOutputObservable;
    }

    public <T> Observable<T> asErrorOnlyObservable() {
        return this.disconnectionErrorOutputObservable.flatMap(new Func1<BleException, Observable<T>>() {
            public Observable<T> call(BleException bleException) {
                return Observable.error(bleException);
            }
        });
    }
}
