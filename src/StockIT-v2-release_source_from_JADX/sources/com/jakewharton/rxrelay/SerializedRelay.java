package com.jakewharton.rxrelay;

import p030rx.Observable;
import p030rx.Subscriber;

public class SerializedRelay<T, R> extends Relay<T, R> {
    private final SerializedAction1<T> action;
    private final Relay<T, R> actual;

    public SerializedRelay(final Relay<T, R> relay) {
        super(new Observable.OnSubscribe<R>() {
            public void call(Subscriber<? super R> subscriber) {
                Relay.this.unsafeSubscribe(subscriber);
            }
        });
        this.actual = relay;
        this.action = new SerializedAction1<>(relay);
    }

    public void call(T t) {
        this.action.call(t);
    }

    public boolean hasObservers() {
        return this.actual.hasObservers();
    }
}
