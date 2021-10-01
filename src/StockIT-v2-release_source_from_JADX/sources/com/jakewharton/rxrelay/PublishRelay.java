package com.jakewharton.rxrelay;

import com.jakewharton.rxrelay.RelaySubscriptionManager;
import p030rx.Observable;

public class PublishRelay<T> extends Relay<T, T> {
    private final RelaySubscriptionManager<T> state;

    public static <T> PublishRelay<T> create() {
        RelaySubscriptionManager relaySubscriptionManager = new RelaySubscriptionManager();
        return new PublishRelay<>(relaySubscriptionManager, relaySubscriptionManager);
    }

    protected PublishRelay(Observable.OnSubscribe<T> onSubscribe, RelaySubscriptionManager<T> relaySubscriptionManager) {
        super(onSubscribe);
        this.state = relaySubscriptionManager;
    }

    public void call(T t) {
        for (RelaySubscriptionManager.RelayObserver onNext : this.state.observers()) {
            onNext.onNext(t);
        }
    }

    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }
}
