package com.polidea.rxandroidble.internal.util;

import com.polidea.rxandroidble.RxBleAdapterStateObservable;
import com.polidea.rxandroidble.RxBleClient;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Single;
import p030rx.functions.Func1;

public class ClientStateObservable extends Observable<RxBleClient.State> {
    /* JADX WARNING: Illegal instructions before constructor call */
    @bleshadow.javax.inject.Inject
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected ClientStateObservable(com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper r9, p030rx.Observable<com.polidea.rxandroidble.RxBleAdapterStateObservable.BleAdapterState> r10, @bleshadow.javax.inject.Named("location-ok-boolean-observable") p030rx.Observable<java.lang.Boolean> r11, com.polidea.rxandroidble.internal.util.LocationServicesStatus r12, @bleshadow.javax.inject.Named("timeout") p030rx.Scheduler r13) {
        /*
            r8 = this;
            rx.internal.operators.OnSubscribeCreate r0 = new rx.internal.operators.OnSubscribeCreate
            com.polidea.rxandroidble.internal.util.ClientStateObservable$1 r7 = new com.polidea.rxandroidble.internal.util.ClientStateObservable$1
            r1 = r7
            r2 = r9
            r3 = r12
            r4 = r13
            r5 = r10
            r6 = r11
            r1.<init>(r3, r4, r5, r6)
            rx.Emitter$BackpressureMode r9 = p030rx.Emitter.BackpressureMode.LATEST
            r0.<init>(r7, r9)
            r8.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.polidea.rxandroidble.internal.util.ClientStateObservable.<init>(com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper, rx.Observable, rx.Observable, com.polidea.rxandroidble.internal.util.LocationServicesStatus, rx.Scheduler):void");
    }

    /* access modifiers changed from: private */
    public static Single<Boolean> checkPermissionUntilGranted(final LocationServicesStatus locationServicesStatus, Scheduler scheduler) {
        return Observable.interval(0, 1, TimeUnit.SECONDS, scheduler).map(new Func1<Long, Boolean>() {
            public Boolean call(Long l) {
                return Boolean.valueOf(locationServicesStatus.isLocationPermissionOk());
            }
        }).takeWhile(new Func1<Boolean, Boolean>() {
            public Boolean call(Boolean bool) {
                return Boolean.valueOf(!bool.booleanValue());
            }
        }).count().toSingle().map(new Func1<Integer, Boolean>() {
            public Boolean call(Integer num) {
                return Boolean.valueOf(num.intValue() == 0);
            }
        });
    }

    /* access modifiers changed from: private */
    public static Observable<RxBleClient.State> checkAdapterAndServicesState(Boolean bool, RxBleAdapterWrapper rxBleAdapterWrapper, Observable<RxBleAdapterStateObservable.BleAdapterState> observable, final Observable<Boolean> observable2) {
        Observable<R> switchMap = observable.startWith(rxBleAdapterWrapper.isBluetoothEnabled() ? RxBleAdapterStateObservable.BleAdapterState.STATE_ON : RxBleAdapterStateObservable.BleAdapterState.STATE_OFF).switchMap(new Func1<RxBleAdapterStateObservable.BleAdapterState, Observable<RxBleClient.State>>() {
            public Observable<RxBleClient.State> call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                if (bleAdapterState != RxBleAdapterStateObservable.BleAdapterState.STATE_ON) {
                    return Observable.just(RxBleClient.State.BLUETOOTH_NOT_ENABLED);
                }
                return observable2.map(new Func1<Boolean, RxBleClient.State>() {
                    public RxBleClient.State call(Boolean bool) {
                        return bool.booleanValue() ? RxBleClient.State.READY : RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED;
                    }
                });
            }
        });
        return bool.booleanValue() ? switchMap.skip(1) : switchMap;
    }
}
