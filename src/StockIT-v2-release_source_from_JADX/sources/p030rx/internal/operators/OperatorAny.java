package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Func1;
import p030rx.internal.producers.SingleDelayedProducer;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.OperatorAny */
public final class OperatorAny<T> implements Observable.Operator<Boolean, T> {
    final Func1<? super T, Boolean> predicate;
    final boolean returnOnEmpty;

    public OperatorAny(Func1<? super T, Boolean> func1, boolean z) {
        this.predicate = func1;
        this.returnOnEmpty = z;
    }

    public Subscriber<? super T> call(final Subscriber<? super Boolean> subscriber) {
        final SingleDelayedProducer singleDelayedProducer = new SingleDelayedProducer(subscriber);
        C36921 r1 = new Subscriber<T>() {
            boolean done;
            boolean hasElements;

            public void onNext(T t) {
                if (!this.done) {
                    this.hasElements = true;
                    try {
                        if (OperatorAny.this.predicate.call(t).booleanValue()) {
                            this.done = true;
                            singleDelayedProducer.setValue(Boolean.valueOf(true ^ OperatorAny.this.returnOnEmpty));
                            unsubscribe();
                        }
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, (Observer<?>) this, (Object) t);
                    }
                }
            }

            public void onError(Throwable th) {
                if (!this.done) {
                    this.done = true;
                    subscriber.onError(th);
                    return;
                }
                RxJavaHooks.onError(th);
            }

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    if (this.hasElements) {
                        singleDelayedProducer.setValue(false);
                    } else {
                        singleDelayedProducer.setValue(Boolean.valueOf(OperatorAny.this.returnOnEmpty));
                    }
                }
            }
        };
        subscriber.add(r1);
        subscriber.setProducer(singleDelayedProducer);
        return r1;
    }
}
