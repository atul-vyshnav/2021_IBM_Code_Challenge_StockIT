package p030rx.internal.operators;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Completable;
import p030rx.CompletableSubscriber;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.Subscription;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.SingleTakeUntilCompletable */
public final class SingleTakeUntilCompletable<T> implements Single.OnSubscribe<T> {
    final Completable other;
    final Single.OnSubscribe<T> source;

    public SingleTakeUntilCompletable(Single.OnSubscribe<T> onSubscribe, Completable completable) {
        this.source = onSubscribe;
        this.other = completable;
    }

    public void call(SingleSubscriber<? super T> singleSubscriber) {
        TakeUntilSourceSubscriber takeUntilSourceSubscriber = new TakeUntilSourceSubscriber(singleSubscriber);
        singleSubscriber.add(takeUntilSourceSubscriber);
        this.other.subscribe((CompletableSubscriber) takeUntilSourceSubscriber);
        this.source.call(takeUntilSourceSubscriber);
    }

    /* renamed from: rx.internal.operators.SingleTakeUntilCompletable$TakeUntilSourceSubscriber */
    static final class TakeUntilSourceSubscriber<T> extends SingleSubscriber<T> implements CompletableSubscriber {
        final SingleSubscriber<? super T> actual;
        final AtomicBoolean once = new AtomicBoolean();

        TakeUntilSourceSubscriber(SingleSubscriber<? super T> singleSubscriber) {
            this.actual = singleSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            add(subscription);
        }

        public void onSuccess(T t) {
            if (this.once.compareAndSet(false, true)) {
                unsubscribe();
                this.actual.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                unsubscribe();
                this.actual.onError(th);
                return;
            }
            RxJavaHooks.onError(th);
        }

        public void onCompleted() {
            onError(new CancellationException("Single::takeUntil(Completable) - Stream was canceled before emitting a terminal event."));
        }
    }
}
