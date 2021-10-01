package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Producer;
import p030rx.Scheduler;
import p030rx.Subscriber;
import p030rx.functions.Action0;

/* renamed from: rx.internal.operators.OperatorSubscribeOn */
public final class OperatorSubscribeOn<T> implements Observable.OnSubscribe<T> {
    final boolean requestOn;
    final Scheduler scheduler;
    final Observable<T> source;

    public OperatorSubscribeOn(Observable<T> observable, Scheduler scheduler2, boolean z) {
        this.scheduler = scheduler2;
        this.source = observable;
        this.requestOn = z;
    }

    public void call(Subscriber<? super T> subscriber) {
        Scheduler.Worker createWorker = this.scheduler.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(subscriber, this.requestOn, createWorker, this.source);
        subscriber.add(subscribeOnSubscriber);
        subscriber.add(createWorker);
        createWorker.schedule(subscribeOnSubscriber);
    }

    /* renamed from: rx.internal.operators.OperatorSubscribeOn$SubscribeOnSubscriber */
    static final class SubscribeOnSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> actual;
        final boolean requestOn;
        Observable<T> source;

        /* renamed from: t */
        Thread f1358t;
        final Scheduler.Worker worker;

        SubscribeOnSubscriber(Subscriber<? super T> subscriber, boolean z, Scheduler.Worker worker2, Observable<T> observable) {
            this.actual = subscriber;
            this.requestOn = z;
            this.worker = worker2;
            this.source = observable;
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            try {
                this.actual.onError(th);
            } finally {
                this.worker.unsubscribe();
            }
        }

        public void onCompleted() {
            try {
                this.actual.onCompleted();
            } finally {
                this.worker.unsubscribe();
            }
        }

        public void call() {
            Observable<T> observable = this.source;
            this.source = null;
            this.f1358t = Thread.currentThread();
            observable.unsafeSubscribe(this);
        }

        public void setProducer(final Producer producer) {
            this.actual.setProducer(new Producer() {
                public void request(final long j) {
                    if (SubscribeOnSubscriber.this.f1358t == Thread.currentThread() || !SubscribeOnSubscriber.this.requestOn) {
                        producer.request(j);
                    } else {
                        SubscribeOnSubscriber.this.worker.schedule(new Action0() {
                            public void call() {
                                producer.request(j);
                            }
                        });
                    }
                }
            });
        }
    }
}
