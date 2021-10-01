package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.OnSubscribeLift */
public final class OnSubscribeLift<T, R> implements Observable.OnSubscribe<R> {
    final Observable.Operator<? extends R, ? super T> operator;
    final Observable.OnSubscribe<T> parent;

    public OnSubscribeLift(Observable.OnSubscribe<T> onSubscribe, Observable.Operator<? extends R, ? super T> operator2) {
        this.parent = onSubscribe;
        this.operator = operator2;
    }

    public void call(Subscriber<? super R> subscriber) {
        Subscriber subscriber2;
        try {
            subscriber2 = (Subscriber) RxJavaHooks.onObservableLift(this.operator).call(subscriber);
            subscriber2.onStart();
            this.parent.call(subscriber2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            subscriber.onError(th);
        }
    }
}
