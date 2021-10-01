package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;
import p030rx.functions.Func2;

/* renamed from: rx.internal.operators.OperatorSkipWhile */
public final class OperatorSkipWhile<T> implements Observable.Operator<T, T> {
    final Func2<? super T, Integer, Boolean> predicate;

    public OperatorSkipWhile(Func2<? super T, Integer, Boolean> func2) {
        this.predicate = func2;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            int index;
            boolean skipping = true;

            public void onNext(T t) {
                if (!this.skipping) {
                    subscriber.onNext(t);
                    return;
                }
                try {
                    Func2<? super T, Integer, Boolean> func2 = OperatorSkipWhile.this.predicate;
                    int i = this.index;
                    this.index = i + 1;
                    if (!func2.call(t, Integer.valueOf(i)).booleanValue()) {
                        this.skipping = false;
                        subscriber.onNext(t);
                        return;
                    }
                    request(1);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer<?>) subscriber, (Object) t);
                }
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onCompleted() {
                subscriber.onCompleted();
            }
        };
    }

    public static <T> Func2<T, Integer, Boolean> toPredicate2(final Func1<? super T, Boolean> func1) {
        return new Func2<T, Integer, Boolean>() {
            public Boolean call(T t, Integer num) {
                return (Boolean) func1.call(t);
            }
        };
    }
}
