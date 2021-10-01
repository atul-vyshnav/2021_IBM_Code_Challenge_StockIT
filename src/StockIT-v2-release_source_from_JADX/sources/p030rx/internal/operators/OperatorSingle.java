package p030rx.internal.operators;

import java.util.NoSuchElementException;
import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.internal.producers.SingleProducer;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.OperatorSingle */
public final class OperatorSingle<T> implements Observable.Operator<T, T> {
    private final T defaultValue;
    private final boolean hasDefaultValue;

    /* renamed from: rx.internal.operators.OperatorSingle$Holder */
    static final class Holder {
        static final OperatorSingle<?> INSTANCE = new OperatorSingle<>();

        Holder() {
        }
    }

    public static <T> OperatorSingle<T> instance() {
        return Holder.INSTANCE;
    }

    OperatorSingle() {
        this(false, (Object) null);
    }

    public OperatorSingle(T t) {
        this(true, t);
    }

    private OperatorSingle(boolean z, T t) {
        this.hasDefaultValue = z;
        this.defaultValue = t;
    }

    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        ParentSubscriber parentSubscriber = new ParentSubscriber(subscriber, this.hasDefaultValue, this.defaultValue);
        subscriber.add(parentSubscriber);
        return parentSubscriber;
    }

    /* renamed from: rx.internal.operators.OperatorSingle$ParentSubscriber */
    static final class ParentSubscriber<T> extends Subscriber<T> {
        private final Subscriber<? super T> child;
        private final T defaultValue;
        private final boolean hasDefaultValue;
        private boolean hasTooManyElements;
        private boolean isNonEmpty;
        private T value;

        ParentSubscriber(Subscriber<? super T> subscriber, boolean z, T t) {
            this.child = subscriber;
            this.hasDefaultValue = z;
            this.defaultValue = t;
            request(2);
        }

        public void onNext(T t) {
            if (this.hasTooManyElements) {
                return;
            }
            if (this.isNonEmpty) {
                this.hasTooManyElements = true;
                this.child.onError(new IllegalArgumentException("Sequence contains too many elements"));
                unsubscribe();
                return;
            }
            this.value = t;
            this.isNonEmpty = true;
        }

        public void onCompleted() {
            if (this.hasTooManyElements) {
                return;
            }
            if (this.isNonEmpty) {
                this.child.setProducer(new SingleProducer(this.child, this.value));
            } else if (this.hasDefaultValue) {
                this.child.setProducer(new SingleProducer(this.child, this.defaultValue));
            } else {
                this.child.onError(new NoSuchElementException("Sequence contains no elements"));
            }
        }

        public void onError(Throwable th) {
            if (this.hasTooManyElements) {
                RxJavaHooks.onError(th);
            } else {
                this.child.onError(th);
            }
        }
    }
}
