package p030rx.internal.operators;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Action0;
import p030rx.internal.producers.SingleProducer;
import p030rx.subscriptions.Subscriptions;

/* renamed from: rx.internal.operators.OnSubscribeToObservableFuture */
public final class OnSubscribeToObservableFuture {
    private OnSubscribeToObservableFuture() {
        throw new IllegalStateException("No instances!");
    }

    /* renamed from: rx.internal.operators.OnSubscribeToObservableFuture$ToObservableFuture */
    static class ToObservableFuture<T> implements Observable.OnSubscribe<T> {
        final Future<? extends T> that;
        private final long time;
        private final TimeUnit unit;

        public ToObservableFuture(Future<? extends T> future) {
            this.that = future;
            this.time = 0;
            this.unit = null;
        }

        public ToObservableFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
            this.that = future;
            this.time = j;
            this.unit = timeUnit;
        }

        public void call(Subscriber<? super T> subscriber) {
            subscriber.add(Subscriptions.create(new Action0() {
                public void call() {
                    ToObservableFuture.this.that.cancel(true);
                }
            }));
            try {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.setProducer(new SingleProducer(subscriber, this.unit == null ? this.that.get() : this.that.get(this.time, this.unit)));
                }
            } catch (Throwable th) {
                if (!subscriber.isUnsubscribed()) {
                    Exceptions.throwOrReport(th, (Observer<?>) subscriber);
                }
            }
        }
    }

    public static <T> Observable.OnSubscribe<T> toObservableFuture(Future<? extends T> future) {
        return new ToObservableFuture(future);
    }

    public static <T> Observable.OnSubscribe<T> toObservableFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
        return new ToObservableFuture(future, j, timeUnit);
    }
}
