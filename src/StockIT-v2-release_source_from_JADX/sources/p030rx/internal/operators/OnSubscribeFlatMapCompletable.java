package p030rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p030rx.Completable;
import p030rx.CompletableSubscriber;
import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.Subscription;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;
import p030rx.internal.util.ExceptionsUtils;
import p030rx.plugins.RxJavaHooks;
import p030rx.subscriptions.CompositeSubscription;

/* renamed from: rx.internal.operators.OnSubscribeFlatMapCompletable */
public final class OnSubscribeFlatMapCompletable<T> implements Observable.OnSubscribe<T> {
    final boolean delayErrors;
    final Func1<? super T, ? extends Completable> mapper;
    final int maxConcurrency;
    final Observable<T> source;

    public OnSubscribeFlatMapCompletable(Observable<T> observable, Func1<? super T, ? extends Completable> func1, boolean z, int i) {
        if (func1 == null) {
            throw new NullPointerException("mapper is null");
        } else if (i > 0) {
            this.source = observable;
            this.mapper = func1;
            this.delayErrors = z;
            this.maxConcurrency = i;
        } else {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + i);
        }
    }

    public void call(Subscriber<? super T> subscriber) {
        FlatMapCompletableSubscriber flatMapCompletableSubscriber = new FlatMapCompletableSubscriber(subscriber, this.mapper, this.delayErrors, this.maxConcurrency);
        subscriber.add(flatMapCompletableSubscriber);
        subscriber.add(flatMapCompletableSubscriber.set);
        this.source.unsafeSubscribe(flatMapCompletableSubscriber);
    }

    /* renamed from: rx.internal.operators.OnSubscribeFlatMapCompletable$FlatMapCompletableSubscriber */
    static final class FlatMapCompletableSubscriber<T> extends Subscriber<T> {
        final Subscriber<? super T> actual;
        final boolean delayErrors;
        final AtomicReference<Throwable> errors = new AtomicReference<>();
        final Func1<? super T, ? extends Completable> mapper;
        final int maxConcurrency;
        final CompositeSubscription set = new CompositeSubscription();
        final AtomicInteger wip = new AtomicInteger(1);

        FlatMapCompletableSubscriber(Subscriber<? super T> subscriber, Func1<? super T, ? extends Completable> func1, boolean z, int i) {
            this.actual = subscriber;
            this.mapper = func1;
            this.delayErrors = z;
            this.maxConcurrency = i;
            request(i != Integer.MAX_VALUE ? (long) i : Long.MAX_VALUE);
        }

        public void onNext(T t) {
            try {
                Completable completable = (Completable) this.mapper.call(t);
                if (completable != null) {
                    InnerSubscriber innerSubscriber = new InnerSubscriber();
                    this.set.add(innerSubscriber);
                    this.wip.getAndIncrement();
                    completable.unsafeSubscribe((CompletableSubscriber) innerSubscriber);
                    return;
                }
                throw new NullPointerException("The mapper returned a null Completable");
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                unsubscribe();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            if (this.delayErrors) {
                ExceptionsUtils.addThrowable(this.errors, th);
                onCompleted();
                return;
            }
            this.set.unsubscribe();
            if (this.errors.compareAndSet((Object) null, th)) {
                this.actual.onError(ExceptionsUtils.terminate(this.errors));
            } else {
                RxJavaHooks.onError(th);
            }
        }

        public void onCompleted() {
            done();
        }

        /* access modifiers changed from: package-private */
        public boolean done() {
            if (this.wip.decrementAndGet() != 0) {
                return false;
            }
            Throwable terminate = ExceptionsUtils.terminate(this.errors);
            if (terminate != null) {
                this.actual.onError(terminate);
                return true;
            }
            this.actual.onCompleted();
            return true;
        }

        public void innerError(FlatMapCompletableSubscriber<T>.InnerSubscriber innerSubscriber, Throwable th) {
            this.set.remove(innerSubscriber);
            if (this.delayErrors) {
                ExceptionsUtils.addThrowable(this.errors, th);
                if (!done() && this.maxConcurrency != Integer.MAX_VALUE) {
                    request(1);
                    return;
                }
                return;
            }
            this.set.unsubscribe();
            unsubscribe();
            if (this.errors.compareAndSet((Object) null, th)) {
                this.actual.onError(ExceptionsUtils.terminate(this.errors));
            } else {
                RxJavaHooks.onError(th);
            }
        }

        public void innerComplete(FlatMapCompletableSubscriber<T>.InnerSubscriber innerSubscriber) {
            this.set.remove(innerSubscriber);
            if (!done() && this.maxConcurrency != Integer.MAX_VALUE) {
                request(1);
            }
        }

        /* renamed from: rx.internal.operators.OnSubscribeFlatMapCompletable$FlatMapCompletableSubscriber$InnerSubscriber */
        final class InnerSubscriber extends AtomicReference<Subscription> implements CompletableSubscriber, Subscription {
            private static final long serialVersionUID = -8588259593722659900L;

            InnerSubscriber() {
            }

            public void unsubscribe() {
                Subscription subscription = (Subscription) getAndSet(this);
                if (subscription != null && subscription != this) {
                    subscription.unsubscribe();
                }
            }

            public boolean isUnsubscribed() {
                return get() == this;
            }

            public void onCompleted() {
                FlatMapCompletableSubscriber.this.innerComplete(this);
            }

            public void onError(Throwable th) {
                FlatMapCompletableSubscriber.this.innerError(this, th);
            }

            public void onSubscribe(Subscription subscription) {
                if (!compareAndSet((Object) null, subscription)) {
                    subscription.unsubscribe();
                    if (get() != this) {
                        RxJavaHooks.onError(new IllegalStateException("Subscription already set!"));
                    }
                }
            }
        }
    }
}
