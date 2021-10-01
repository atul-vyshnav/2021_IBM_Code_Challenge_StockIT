package p030rx.internal.operators;

import p030rx.Observable;
import p030rx.Subscriber;

/* renamed from: rx.internal.operators.NeverObservableHolder */
public enum NeverObservableHolder implements Observable.OnSubscribe<Object> {
    INSTANCE;
    
    static final Observable<Object> NEVER = null;

    public void call(Subscriber<? super Object> subscriber) {
    }

    static {
        NeverObservableHolder neverObservableHolder;
        NEVER = Observable.unsafeCreate(neverObservableHolder);
    }

    public static <T> Observable<T> instance() {
        return NEVER;
    }
}
