package p030rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.observers.SerializedSubscriber;

/* renamed from: rx.internal.operators.OperatorSkipUntil */
public final class OperatorSkipUntil<T, U> implements Observable.Operator<T, T> {
    final Observable<U> other;

    public OperatorSkipUntil(Observable<U> observable) {
        this.other = observable;
    }

    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        C37631 r2 = new Subscriber<U>() {
            public void onNext(U u) {
                atomicBoolean.set(true);
                unsubscribe();
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                serializedSubscriber.unsubscribe();
            }

            public void onCompleted() {
                unsubscribe();
            }
        };
        subscriber.add(r2);
        this.other.unsafeSubscribe(r2);
        return new Subscriber<T>(subscriber) {
            public void onNext(T t) {
                if (atomicBoolean.get()) {
                    serializedSubscriber.onNext(t);
                } else {
                    request(1);
                }
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                unsubscribe();
            }

            public void onCompleted() {
                serializedSubscriber.onCompleted();
                unsubscribe();
            }
        };
    }
}
