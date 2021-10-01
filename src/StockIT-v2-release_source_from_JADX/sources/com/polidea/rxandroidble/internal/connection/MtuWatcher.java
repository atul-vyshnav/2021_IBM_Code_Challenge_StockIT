package com.polidea.rxandroidble.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import p030rx.Observable;
import p030rx.functions.Action1;
import p030rx.subscriptions.SerialSubscription;

@ConnectionScope
class MtuWatcher implements ConnectionSubscriptionWatcher, MtuProvider, Action1<Integer> {
    private Integer currentMtu;
    private final Observable<Integer> mtuObservable;
    private final SerialSubscription serialSubscription = new SerialSubscription();

    @Inject
    MtuWatcher(RxBleGattCallback rxBleGattCallback, @Named("GATT_MTU_MINIMUM") int i) {
        this.mtuObservable = rxBleGattCallback.getOnMtuChanged().retry();
        this.currentMtu = Integer.valueOf(i);
    }

    public int getMtu() {
        return this.currentMtu.intValue();
    }

    public void onConnectionSubscribed() {
        this.serialSubscription.set(this.mtuObservable.subscribe(this));
    }

    public void onConnectionUnsubscribed() {
        this.serialSubscription.unsubscribe();
    }

    public void call(Integer num) {
        this.currentMtu = num;
    }
}
