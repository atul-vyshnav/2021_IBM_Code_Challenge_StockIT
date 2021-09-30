package com.jakewharton.rxrelay;

import com.jakewharton.rxrelay.RelaySubscriptionManager;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.schedulers.TestScheduler;

public final class TestRelay<T> extends Relay<T, T> {
    private final Scheduler.Worker innerScheduler;
    private final RelaySubscriptionManager<T> state;

    public static <T> TestRelay<T> create(TestScheduler testScheduler) {
        final RelaySubscriptionManager relaySubscriptionManager = new RelaySubscriptionManager();
        relaySubscriptionManager.onAdded = new Action1<RelaySubscriptionManager.RelayObserver<T>>() {
            public void call(RelaySubscriptionManager.RelayObserver<T> relayObserver) {
                relayObserver.emitFirst(relaySubscriptionManager.getLatest());
            }
        };
        return new TestRelay<>(relaySubscriptionManager, relaySubscriptionManager, testScheduler);
    }

    private TestRelay(Observable.OnSubscribe<T> onSubscribe, RelaySubscriptionManager<T> relaySubscriptionManager, TestScheduler testScheduler) {
        super(onSubscribe);
        this.state = relaySubscriptionManager;
        this.innerScheduler = testScheduler.createWorker();
    }

    public void call(T t) {
        call(t, 0);
    }

    /* access modifiers changed from: package-private */
    public void _call(T t) {
        for (RelaySubscriptionManager.RelayObserver onNext : this.state.observers()) {
            onNext.onNext(t);
        }
    }

    public void call(final T t, long j) {
        this.innerScheduler.schedule(new Action0() {
            public void call() {
                TestRelay.this._call(t);
            }
        }, j, TimeUnit.MILLISECONDS);
    }

    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }
}
