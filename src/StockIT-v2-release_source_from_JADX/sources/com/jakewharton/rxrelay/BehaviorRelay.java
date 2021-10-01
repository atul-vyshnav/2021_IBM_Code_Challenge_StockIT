package com.jakewharton.rxrelay;

import com.jakewharton.rxrelay.RelaySubscriptionManager;
import java.lang.reflect.Array;
import p030rx.Observable;
import p030rx.functions.Action1;

public class BehaviorRelay<T> extends Relay<T, T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final RelaySubscriptionManager<T> state;

    public static <T> BehaviorRelay<T> create() {
        return create((Object) null, false);
    }

    public static <T> BehaviorRelay<T> create(T t) {
        return create(t, true);
    }

    private static <T> BehaviorRelay<T> create(T t, boolean z) {
        final RelaySubscriptionManager relaySubscriptionManager = new RelaySubscriptionManager();
        if (z) {
            relaySubscriptionManager.setLatest(NotificationLite.next(t));
        }
        relaySubscriptionManager.onAdded = new Action1<RelaySubscriptionManager.RelayObserver<T>>() {
            public void call(RelaySubscriptionManager.RelayObserver<T> relayObserver) {
                relayObserver.emitFirst(relaySubscriptionManager.getLatest());
            }
        };
        return new BehaviorRelay<>(relaySubscriptionManager, relaySubscriptionManager);
    }

    protected BehaviorRelay(Observable.OnSubscribe<T> onSubscribe, RelaySubscriptionManager<T> relaySubscriptionManager) {
        super(onSubscribe);
        this.state = relaySubscriptionManager;
    }

    public void call(T t) {
        if (this.state.getLatest() == null || this.state.active) {
            Object next = NotificationLite.next(t);
            for (RelaySubscriptionManager.RelayObserver emitNext : this.state.next(next)) {
                emitNext.emitNext(next);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int subscriberCount() {
        return this.state.observers().length;
    }

    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    public boolean hasValue() {
        return this.state.getLatest() != null;
    }

    public T getValue() {
        Object latest = this.state.getLatest();
        if (latest != null) {
            return NotificationLite.getValue(latest);
        }
        return null;
    }

    public T[] getValues(T[] tArr) {
        Object latest = this.state.getLatest();
        if (latest != null) {
            if (tArr.length == 0) {
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1);
            }
            tArr[0] = NotificationLite.getValue(latest);
            if (tArr.length > 1) {
                tArr[1] = null;
            }
        } else if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }

    public Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }
}
