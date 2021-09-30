package com.polidea.rxandroidble.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import bleshadow.javax.inject.Inject;
import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.functions.Action1;
import p030rx.functions.Cancellable;
import p030rx.internal.operators.OnSubscribeCreate;

public class LocationServicesOkObservableApi23 extends Observable<Boolean> {
    @Inject
    LocationServicesOkObservableApi23(final Context context, final LocationServicesStatus locationServicesStatus) {
        super(new OnSubscribeCreate(new Action1<Emitter<Boolean>>() {
            public void call(final Emitter<Boolean> emitter) {
                boolean isLocationProviderOk = LocationServicesStatus.this.isLocationProviderOk();
                final AtomicBoolean atomicBoolean = new AtomicBoolean(isLocationProviderOk);
                emitter.onNext(Boolean.valueOf(isLocationProviderOk));
                final C27061 r0 = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        boolean isLocationProviderOk = LocationServicesStatus.this.isLocationProviderOk();
                        if (atomicBoolean.compareAndSet(!isLocationProviderOk, isLocationProviderOk)) {
                            emitter.onNext(Boolean.valueOf(isLocationProviderOk));
                        }
                    }
                };
                context.registerReceiver(r0, new IntentFilter("android.location.MODE_CHANGED"));
                emitter.setCancellation(new Cancellable() {
                    public void cancel() throws Exception {
                        context.unregisterReceiver(r0);
                    }
                });
            }
        }, Emitter.BackpressureMode.LATEST));
    }
}
