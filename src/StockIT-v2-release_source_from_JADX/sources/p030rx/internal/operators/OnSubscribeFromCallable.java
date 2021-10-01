package p030rx.internal.operators;

import java.util.concurrent.Callable;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.internal.producers.SingleDelayedProducer;

/* renamed from: rx.internal.operators.OnSubscribeFromCallable */
public final class OnSubscribeFromCallable<T> implements Observable.OnSubscribe<T> {
    private final Callable<? extends T> resultFactory;

    public OnSubscribeFromCallable(Callable<? extends T> callable) {
        this.resultFactory = callable;
    }

    public void call(Subscriber<? super T> subscriber) {
        SingleDelayedProducer singleDelayedProducer = new SingleDelayedProducer(subscriber);
        subscriber.setProducer(singleDelayedProducer);
        try {
            singleDelayedProducer.setValue(this.resultFactory.call());
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (Observer<?>) subscriber);
        }
    }
}
