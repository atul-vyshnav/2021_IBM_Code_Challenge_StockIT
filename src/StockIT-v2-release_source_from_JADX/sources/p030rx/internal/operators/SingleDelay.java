package p030rx.internal.operators;

import java.util.concurrent.TimeUnit;
import p030rx.Scheduler;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.functions.Action0;

/* renamed from: rx.internal.operators.SingleDelay */
public final class SingleDelay<T> implements Single.OnSubscribe<T> {
    final long delay;
    final Scheduler scheduler;
    final Single.OnSubscribe<T> source;
    final TimeUnit unit;

    public SingleDelay(Single.OnSubscribe<T> onSubscribe, long j, TimeUnit timeUnit, Scheduler scheduler2) {
        this.source = onSubscribe;
        this.scheduler = scheduler2;
        this.delay = j;
        this.unit = timeUnit;
    }

    public void call(SingleSubscriber<? super T> singleSubscriber) {
        Scheduler.Worker createWorker = this.scheduler.createWorker();
        ObserveOnSingleSubscriber observeOnSingleSubscriber = new ObserveOnSingleSubscriber(singleSubscriber, createWorker, this.delay, this.unit);
        singleSubscriber.add(createWorker);
        singleSubscriber.add(observeOnSingleSubscriber);
        this.source.call(observeOnSingleSubscriber);
    }

    /* renamed from: rx.internal.operators.SingleDelay$ObserveOnSingleSubscriber */
    static final class ObserveOnSingleSubscriber<T> extends SingleSubscriber<T> implements Action0 {
        final SingleSubscriber<? super T> actual;
        final long delay;
        Throwable error;
        final TimeUnit unit;
        T value;

        /* renamed from: w */
        final Scheduler.Worker f1360w;

        public ObserveOnSingleSubscriber(SingleSubscriber<? super T> singleSubscriber, Scheduler.Worker worker, long j, TimeUnit timeUnit) {
            this.actual = singleSubscriber;
            this.f1360w = worker;
            this.delay = j;
            this.unit = timeUnit;
        }

        public void onSuccess(T t) {
            this.value = t;
            this.f1360w.schedule(this, this.delay, this.unit);
        }

        public void onError(Throwable th) {
            this.error = th;
            this.f1360w.schedule(this, this.delay, this.unit);
        }

        public void call() {
            try {
                Throwable th = this.error;
                if (th != null) {
                    this.error = null;
                    this.actual.onError(th);
                } else {
                    T t = this.value;
                    this.value = null;
                    this.actual.onSuccess(t);
                }
            } finally {
                this.f1360w.unsubscribe();
            }
        }
    }
}
