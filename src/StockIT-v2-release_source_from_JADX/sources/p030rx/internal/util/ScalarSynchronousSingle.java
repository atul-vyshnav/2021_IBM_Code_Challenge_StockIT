package p030rx.internal.util;

import p030rx.Scheduler;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.functions.Action0;
import p030rx.functions.Func1;
import p030rx.internal.schedulers.EventLoopsScheduler;

/* renamed from: rx.internal.util.ScalarSynchronousSingle */
public final class ScalarSynchronousSingle<T> extends Single<T> {
    final T value;

    public static <T> ScalarSynchronousSingle<T> create(T t) {
        return new ScalarSynchronousSingle<>(t);
    }

    protected ScalarSynchronousSingle(final T t) {
        super(new Single.OnSubscribe<T>() {
            public void call(SingleSubscriber<? super T> singleSubscriber) {
                singleSubscriber.onSuccess(t);
            }
        });
        this.value = t;
    }

    public T get() {
        return this.value;
    }

    public Single<T> scalarScheduleOn(Scheduler scheduler) {
        if (scheduler instanceof EventLoopsScheduler) {
            return create(new DirectScheduledEmission((EventLoopsScheduler) scheduler, this.value));
        }
        return create(new NormalScheduledEmission(scheduler, this.value));
    }

    /* renamed from: rx.internal.util.ScalarSynchronousSingle$DirectScheduledEmission */
    static final class DirectScheduledEmission<T> implements Single.OnSubscribe<T> {

        /* renamed from: es */
        private final EventLoopsScheduler f1367es;
        private final T value;

        DirectScheduledEmission(EventLoopsScheduler eventLoopsScheduler, T t) {
            this.f1367es = eventLoopsScheduler;
            this.value = t;
        }

        public void call(SingleSubscriber<? super T> singleSubscriber) {
            singleSubscriber.add(this.f1367es.scheduleDirect(new ScalarSynchronousSingleAction(singleSubscriber, this.value)));
        }
    }

    /* renamed from: rx.internal.util.ScalarSynchronousSingle$NormalScheduledEmission */
    static final class NormalScheduledEmission<T> implements Single.OnSubscribe<T> {
        private final Scheduler scheduler;
        private final T value;

        NormalScheduledEmission(Scheduler scheduler2, T t) {
            this.scheduler = scheduler2;
            this.value = t;
        }

        public void call(SingleSubscriber<? super T> singleSubscriber) {
            Scheduler.Worker createWorker = this.scheduler.createWorker();
            singleSubscriber.add(createWorker);
            createWorker.schedule(new ScalarSynchronousSingleAction(singleSubscriber, this.value));
        }
    }

    /* renamed from: rx.internal.util.ScalarSynchronousSingle$ScalarSynchronousSingleAction */
    static final class ScalarSynchronousSingleAction<T> implements Action0 {
        private final SingleSubscriber<? super T> subscriber;
        private final T value;

        ScalarSynchronousSingleAction(SingleSubscriber<? super T> singleSubscriber, T t) {
            this.subscriber = singleSubscriber;
            this.value = t;
        }

        public void call() {
            try {
                this.subscriber.onSuccess(this.value);
            } catch (Throwable th) {
                this.subscriber.onError(th);
            }
        }
    }

    public <R> Single<R> scalarFlatMap(final Func1<? super T, ? extends Single<? extends R>> func1) {
        return create(new Single.OnSubscribe<R>() {
            public void call(final SingleSubscriber<? super R> singleSubscriber) {
                Single single = (Single) func1.call(ScalarSynchronousSingle.this.value);
                if (single instanceof ScalarSynchronousSingle) {
                    singleSubscriber.onSuccess(((ScalarSynchronousSingle) single).value);
                    return;
                }
                C38291 r1 = new SingleSubscriber<R>() {
                    public void onError(Throwable th) {
                        singleSubscriber.onError(th);
                    }

                    public void onSuccess(R r) {
                        singleSubscriber.onSuccess(r);
                    }
                };
                singleSubscriber.add(r1);
                single.subscribe(r1);
            }
        });
    }
}
