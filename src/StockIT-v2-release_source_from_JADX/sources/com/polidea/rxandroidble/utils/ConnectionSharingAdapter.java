package com.polidea.rxandroidble.utils;

import com.polidea.rxandroidble.RxBleConnection;
import java.util.concurrent.atomic.AtomicReference;
import p030rx.Observable;
import p030rx.functions.Action0;

public class ConnectionSharingAdapter implements Observable.Transformer<RxBleConnection, RxBleConnection> {
    /* access modifiers changed from: private */
    public final AtomicReference<Observable<RxBleConnection>> connectionObservable = new AtomicReference<>();

    public Observable<RxBleConnection> call(Observable<RxBleConnection> observable) {
        synchronized (this.connectionObservable) {
            Observable<RxBleConnection> observable2 = this.connectionObservable.get();
            if (observable2 != null) {
                return observable2;
            }
            Observable<RxBleConnection> refCount = observable.doOnUnsubscribe(new Action0() {
                public void call() {
                    ConnectionSharingAdapter.this.connectionObservable.set((Object) null);
                }
            }).replay(1).refCount();
            this.connectionObservable.set(refCount);
            return refCount;
        }
    }
}
