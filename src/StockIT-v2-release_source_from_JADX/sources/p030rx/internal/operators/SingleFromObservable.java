package p030rx.internal.operators;

import java.util.NoSuchElementException;
import p030rx.Observable;
import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.Subscriber;
import p030rx.plugins.RxJavaHooks;

/* renamed from: rx.internal.operators.SingleFromObservable */
public final class SingleFromObservable<T> implements Single.OnSubscribe<T> {
    final Observable.OnSubscribe<T> source;

    public SingleFromObservable(Observable.OnSubscribe<T> onSubscribe) {
        this.source = onSubscribe;
    }

    public void call(SingleSubscriber<? super T> singleSubscriber) {
        WrapSingleIntoSubscriber wrapSingleIntoSubscriber = new WrapSingleIntoSubscriber(singleSubscriber);
        singleSubscriber.add(wrapSingleIntoSubscriber);
        this.source.call(wrapSingleIntoSubscriber);
    }

    /* renamed from: rx.internal.operators.SingleFromObservable$WrapSingleIntoSubscriber */
    static final class WrapSingleIntoSubscriber<T> extends Subscriber<T> {
        static final int STATE_DONE = 2;
        static final int STATE_EMPTY = 0;
        static final int STATE_HAS_VALUE = 1;
        final SingleSubscriber<? super T> actual;
        int state;
        T value;

        WrapSingleIntoSubscriber(SingleSubscriber<? super T> singleSubscriber) {
            this.actual = singleSubscriber;
        }

        public void onNext(T t) {
            int i = this.state;
            if (i == 0) {
                this.state = 1;
                this.value = t;
            } else if (i == 1) {
                this.state = 2;
                this.actual.onError(new IndexOutOfBoundsException("The upstream produced more than one value"));
            }
        }

        public void onError(Throwable th) {
            if (this.state == 2) {
                RxJavaHooks.onError(th);
                return;
            }
            this.value = null;
            this.actual.onError(th);
        }

        public void onCompleted() {
            int i = this.state;
            if (i == 0) {
                this.actual.onError(new NoSuchElementException());
            } else if (i == 1) {
                this.state = 2;
                T t = this.value;
                this.value = null;
                this.actual.onSuccess(t);
            }
        }
    }
}
