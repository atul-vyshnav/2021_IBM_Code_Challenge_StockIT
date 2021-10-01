package p030rx.internal.operators;

import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Subscriber;
import p030rx.functions.Action0;
import p030rx.observers.SerializedSubscriber;

/* renamed from: rx.internal.operators.OperatorTakeTimed */
public final class OperatorTakeTimed<T> implements Observable.Operator<T, T> {
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    public OperatorTakeTimed(long j, TimeUnit timeUnit, Scheduler scheduler2) {
        this.time = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
    }

    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Scheduler.Worker createWorker = this.scheduler.createWorker();
        subscriber.add(createWorker);
        TakeSubscriber takeSubscriber = new TakeSubscriber(new SerializedSubscriber(subscriber));
        createWorker.schedule(takeSubscriber, this.time, this.unit);
        return takeSubscriber;
    }

    /* renamed from: rx.internal.operators.OperatorTakeTimed$TakeSubscriber */
    static final class TakeSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> child;

        public TakeSubscriber(Subscriber<? super T> subscriber) {
            super(subscriber);
            this.child = subscriber;
        }

        public void onNext(T t) {
            this.child.onNext(t);
        }

        public void onError(Throwable th) {
            this.child.onError(th);
            unsubscribe();
        }

        public void onCompleted() {
            this.child.onCompleted();
            unsubscribe();
        }

        public void call() {
            onCompleted();
        }
    }
}
