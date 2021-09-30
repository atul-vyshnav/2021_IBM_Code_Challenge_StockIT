package com.jakewharton.rxrelay;

import p030rx.Observable;
import p030rx.functions.Action1;

public abstract class Relay<T, R> extends Observable<R> implements Action1<T> {
    public abstract boolean hasObservers();

    protected Relay(Observable.OnSubscribe<R> onSubscribe) {
        super(onSubscribe);
    }

    public Action1<T> asAction() {
        return new Action1<T>() {
            public void call(T t) {
                Relay.this.call(t);
            }
        };
    }

    public final SerializedRelay<T, R> toSerialized() {
        if (getClass() == SerializedRelay.class) {
            return (SerializedRelay) this;
        }
        return new SerializedRelay<>(this);
    }
}
