package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;
import p030rx.functions.Func2;

/* renamed from: rx.internal.operators.OperatorTakeWhile */
public final class OperatorTakeWhile<T> implements Observable.Operator<T, T> {
    final Func2<? super T, ? super Integer, Boolean> predicate;

    public OperatorTakeWhile(final Func1<? super T, Boolean> func1) {
        this(new Func2<T, Integer, Boolean>() {
            public Boolean call(T t, Integer num) {
                return (Boolean) Func1.this.call(t);
            }
        });
    }

    public OperatorTakeWhile(Func2<? super T, ? super Integer, Boolean> func2) {
        this.predicate = func2;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        C37792 r0 = new Subscriber<T>(false, subscriber) {
            private int counter;
            private boolean done;

            public void onNext(T t) {
                try {
                    Func2<? super T, ? super Integer, Boolean> func2 = OperatorTakeWhile.this.predicate;
                    int i = this.counter;
                    this.counter = i + 1;
                    if (func2.call(t, Integer.valueOf(i)).booleanValue()) {
                        subscriber.onNext(t);
                        return;
                    }
                    this.done = true;
                    subscriber.onCompleted();
                    unsubscribe();
                } catch (Throwable th) {
                    this.done = true;
                    Exceptions.throwOrReport(th, (Observer<?>) subscriber, (Object) t);
                    unsubscribe();
                }
            }

            public void onCompleted() {
                if (!this.done) {
                    subscriber.onCompleted();
                }
            }

            public void onError(Throwable th) {
                if (!this.done) {
                    subscriber.onError(th);
                }
            }
        };
        subscriber.add(r0);
        return r0;
    }
}
