package p030rx.internal.operators;

import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Subscriber;
import p030rx.functions.Action0;

/* renamed from: rx.internal.operators.OnSubscribeSkipTimed */
public final class OnSubscribeSkipTimed<T> implements Observable.OnSubscribe<T> {
    final Scheduler scheduler;
    final Observable<T> source;
    final long time;
    final TimeUnit unit;

    public OnSubscribeSkipTimed(Observable<T> observable, long j, TimeUnit timeUnit, Scheduler scheduler2) {
        this.source = observable;
        this.time = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
    }

    public void call(Subscriber<? super T> subscriber) {
        Scheduler.Worker createWorker = this.scheduler.createWorker();
        SkipTimedSubscriber skipTimedSubscriber = new SkipTimedSubscriber(subscriber);
        skipTimedSubscriber.add(createWorker);
        subscriber.add(skipTimedSubscriber);
        createWorker.schedule(skipTimedSubscriber, this.time, this.unit);
        this.source.unsafeSubscribe(skipTimedSubscriber);
    }

    /* renamed from: rx.internal.operators.OnSubscribeSkipTimed$SkipTimedSubscriber */
    static final class SkipTimedSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> child;
        volatile boolean gate;

        SkipTimedSubscriber(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        public void call() {
            this.gate = true;
        }

        public void onNext(T t) {
            if (this.gate) {
                this.child.onNext(t);
            }
        }

        public void onError(Throwable th) {
            try {
                this.child.onError(th);
            } finally {
                unsubscribe();
            }
        }

        public void onCompleted() {
            try {
                this.child.onCompleted();
            } finally {
                unsubscribe();
            }
        }
    }
}
