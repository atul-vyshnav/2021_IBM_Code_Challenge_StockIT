package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Subscriber;

/* renamed from: rx.internal.operators.OnSubscribeThrow */
public final class OnSubscribeThrow<T> implements Observable.OnSubscribe<T> {
    private final Throwable exception;

    public OnSubscribeThrow(Throwable th) {
        this.exception = th;
    }

    public void call(Subscriber<? super T> subscriber) {
        subscriber.onError(this.exception);
    }
}
