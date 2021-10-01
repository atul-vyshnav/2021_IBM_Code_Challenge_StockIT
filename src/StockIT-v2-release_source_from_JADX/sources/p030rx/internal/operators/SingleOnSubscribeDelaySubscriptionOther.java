package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.Subscriber;
import p030rx.plugins.RxJavaHooks;
import p030rx.subscriptions.SerialSubscription;

/* renamed from: rx.internal.operators.SingleOnSubscribeDelaySubscriptionOther */
public final class SingleOnSubscribeDelaySubscriptionOther<T> implements Single.OnSubscribe<T> {
    final Single<? extends T> main;
    final Observable<?> other;

    public SingleOnSubscribeDelaySubscriptionOther(Single<? extends T> single, Observable<?> observable) {
        this.main = single;
        this.other = observable;
    }

    public void call(final SingleSubscriber<? super T> singleSubscriber) {
        final C37991 r0 = new SingleSubscriber<T>() {
            public void onSuccess(T t) {
                singleSubscriber.onSuccess(t);
            }

            public void onError(Throwable th) {
                singleSubscriber.onError(th);
            }
        };
        final SerialSubscription serialSubscription = new SerialSubscription();
        singleSubscriber.add(serialSubscription);
        C38002 r3 = new Subscriber<Object>() {
            boolean done;

            public void onNext(Object obj) {
                onCompleted();
            }

            public void onError(Throwable th) {
                if (this.done) {
                    RxJavaHooks.onError(th);
                    return;
                }
                this.done = true;
                r0.onError(th);
            }

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    serialSubscription.set(r0);
                    SingleOnSubscribeDelaySubscriptionOther.this.main.subscribe(r0);
                }
            }
        };
        serialSubscription.set(r3);
        this.other.subscribe(r3);
    }
}
