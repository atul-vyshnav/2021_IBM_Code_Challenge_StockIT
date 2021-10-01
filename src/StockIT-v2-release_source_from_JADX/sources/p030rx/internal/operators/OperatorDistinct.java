package p030rx.internal.operators;

import java.util.HashSet;
import java.util.Set;
import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.functions.Func1;
import p030rx.internal.util.UtilityFunctions;

/* renamed from: rx.internal.operators.OperatorDistinct */
public final class OperatorDistinct<T, U> implements Observable.Operator<T, T> {
    final Func1<? super T, ? extends U> keySelector;

    /* renamed from: rx.internal.operators.OperatorDistinct$Holder */
    static final class Holder {
        static final OperatorDistinct<?, ?> INSTANCE = new OperatorDistinct<>(UtilityFunctions.identity());

        Holder() {
        }
    }

    public static <T> OperatorDistinct<T, T> instance() {
        return Holder.INSTANCE;
    }

    public OperatorDistinct(Func1<? super T, ? extends U> func1) {
        this.keySelector = func1;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            Set<U> keyMemory = new HashSet();

            public void onNext(T t) {
                if (this.keyMemory.add(OperatorDistinct.this.keySelector.call(t))) {
                    subscriber.onNext(t);
                } else {
                    request(1);
                }
            }

            public void onError(Throwable th) {
                this.keyMemory = null;
                subscriber.onError(th);
            }

            public void onCompleted() {
                this.keyMemory = null;
                subscriber.onCompleted();
            }
        };
    }
}
