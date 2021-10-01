package p030rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Completable;
import p030rx.CompletableEmitter;
import p030rx.CompletableSubscriber;
import p030rx.Subscription;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Action1;
import p030rx.functions.Cancellable;
import p030rx.internal.subscriptions.CancellableSubscription;
import p030rx.internal.subscriptions.SequentialSubscription;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.CompletableFromEmitter */
public final class CompletableFromEmitter implements Completable.OnSubscribe {
    final Action1<CompletableEmitter> producer;

    public CompletableFromEmitter(Action1<CompletableEmitter> action1) {
        this.producer = action1;
    }

    public void call(CompletableSubscriber completableSubscriber) {
        FromEmitter fromEmitter = new FromEmitter(completableSubscriber);
        completableSubscriber.onSubscribe(fromEmitter);
        try {
            this.producer.call(fromEmitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            fromEmitter.onError(th);
        }
    }

    /* renamed from: rx.internal.operators.CompletableFromEmitter$FromEmitter */
    static final class FromEmitter extends AtomicBoolean implements CompletableEmitter, Subscription {
        private static final long serialVersionUID = 5539301318568668881L;
        final CompletableSubscriber actual;
        final SequentialSubscription resource = new SequentialSubscription();

        public FromEmitter(CompletableSubscriber completableSubscriber) {
            this.actual = completableSubscriber;
        }

        public void onCompleted() {
            if (compareAndSet(false, true)) {
                try {
                    this.actual.onCompleted();
                } finally {
                    this.resource.unsubscribe();
                }
            }
        }

        public void onError(Throwable th) {
            if (compareAndSet(false, true)) {
                try {
                    this.actual.onError(th);
                } finally {
                    this.resource.unsubscribe();
                }
            } else {
                RxJavaHooks.onError(th);
            }
        }

        public void setSubscription(Subscription subscription) {
            this.resource.update(subscription);
        }

        public void setCancellation(Cancellable cancellable) {
            setSubscription(new CancellableSubscription(cancellable));
        }

        public void unsubscribe() {
            if (compareAndSet(false, true)) {
                this.resource.unsubscribe();
            }
        }

        public boolean isUnsubscribed() {
            return get();
        }
    }
}
