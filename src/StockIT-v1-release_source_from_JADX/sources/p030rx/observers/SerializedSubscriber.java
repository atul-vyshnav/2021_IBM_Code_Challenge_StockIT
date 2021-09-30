package p030rx.observers;

import p030rx.Observer;
import p030rx.Subscriber;

/* renamed from: rx.observers.SerializedSubscriber */
public class SerializedSubscriber<T> extends Subscriber<T> {

    /* renamed from: s */
    private final Observer<T> f1380s;

    public SerializedSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, true);
    }

    public SerializedSubscriber(Subscriber<? super T> subscriber, boolean z) {
        super(subscriber, z);
        this.f1380s = new SerializedObserver(subscriber);
    }

    public void onCompleted() {
        this.f1380s.onCompleted();
    }

    public void onError(Throwable th) {
        this.f1380s.onError(th);
    }

    public void onNext(T t) {
        this.f1380s.onNext(t);
    }
}
