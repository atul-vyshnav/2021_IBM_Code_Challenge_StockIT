package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func2;

/* renamed from: rx.internal.operators.OnSubscribeReduceSeed */
public final class OnSubscribeReduceSeed<T, R> implements Observable.OnSubscribe<R> {
    final R initialValue;
    final Func2<R, ? super T, R> reducer;
    final Observable<T> source;

    public OnSubscribeReduceSeed(Observable<T> observable, R r, Func2<R, ? super T, R> func2) {
        this.source = observable;
        this.initialValue = r;
        this.reducer = func2;
    }

    public void call(Subscriber<? super R> subscriber) {
        new ReduceSeedSubscriber(subscriber, this.initialValue, this.reducer).subscribeTo(this.source);
    }

    /* renamed from: rx.internal.operators.OnSubscribeReduceSeed$ReduceSeedSubscriber */
    static final class ReduceSeedSubscriber<T, R> extends DeferredScalarSubscriber<T, R> {
        final Func2<R, ? super T, R> reducer;

        public ReduceSeedSubscriber(Subscriber<? super R> subscriber, R r, Func2<R, ? super T, R> func2) {
            super(subscriber);
            this.value = r;
            this.hasValue = true;
            this.reducer = func2;
        }

        public void onNext(T t) {
            try {
                this.value = this.reducer.call(this.value, t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                unsubscribe();
                this.actual.onError(th);
            }
        }
    }
}
