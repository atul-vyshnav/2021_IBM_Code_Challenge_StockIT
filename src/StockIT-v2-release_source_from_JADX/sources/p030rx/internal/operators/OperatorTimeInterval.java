package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Subscriber;
import p030rx.schedulers.TimeInterval;

/* renamed from: rx.internal.operators.OperatorTimeInterval */
public final class OperatorTimeInterval<T> implements Observable.Operator<TimeInterval<T>, T> {
    final Scheduler scheduler;

    public OperatorTimeInterval(Scheduler scheduler2) {
        this.scheduler = scheduler2;
    }

    public Subscriber<? super T> call(final Subscriber<? super TimeInterval<T>> subscriber) {
        return new Subscriber<T>(subscriber) {
            private long lastTimestamp = OperatorTimeInterval.this.scheduler.now();

            public void onNext(T t) {
                long now = OperatorTimeInterval.this.scheduler.now();
                subscriber.onNext(new TimeInterval(now - this.lastTimestamp, t));
                this.lastTimestamp = now;
            }

            public void onCompleted() {
                subscriber.onCompleted();
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }
        };
    }
}
