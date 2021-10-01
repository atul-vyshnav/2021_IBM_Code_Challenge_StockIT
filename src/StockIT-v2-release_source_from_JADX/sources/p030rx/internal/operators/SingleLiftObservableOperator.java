package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.Subscriber;
import p030rx.exceptions.Exceptions;
import p030rx.internal.operators.SingleFromObservable;
import p030rx.internal.producers.SingleProducer;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.SingleLiftObservableOperator */
public final class SingleLiftObservableOperator<T, R> implements Single.OnSubscribe<R> {
    final Observable.Operator<? extends R, ? super T> lift;
    final Single.OnSubscribe<T> source;

    public SingleLiftObservableOperator(Single.OnSubscribe<T> onSubscribe, Observable.Operator<? extends R, ? super T> operator) {
        this.source = onSubscribe;
        this.lift = operator;
    }

    public void call(SingleSubscriber<? super R> singleSubscriber) {
        SingleFromObservable.WrapSingleIntoSubscriber wrapSingleIntoSubscriber = new SingleFromObservable.WrapSingleIntoSubscriber(singleSubscriber);
        singleSubscriber.add(wrapSingleIntoSubscriber);
        try {
            Subscriber subscriber = (Subscriber) RxJavaHooks.onSingleLift(this.lift).call(wrapSingleIntoSubscriber);
            SingleSubscriber wrap = wrap(subscriber);
            subscriber.onStart();
            this.source.call(wrap);
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (SingleSubscriber<?>) singleSubscriber);
        }
    }

    public static <T> SingleSubscriber<T> wrap(Subscriber<T> subscriber) {
        WrapSubscriberIntoSingle wrapSubscriberIntoSingle = new WrapSubscriberIntoSingle(subscriber);
        subscriber.add(wrapSubscriberIntoSingle);
        return wrapSubscriberIntoSingle;
    }

    /* renamed from: rx.internal.operators.SingleLiftObservableOperator$WrapSubscriberIntoSingle */
    static final class WrapSubscriberIntoSingle<T> extends SingleSubscriber<T> {
        final Subscriber<? super T> actual;

        WrapSubscriberIntoSingle(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void onSuccess(T t) {
            this.actual.setProducer(new SingleProducer(this.actual, t));
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }
    }
}
