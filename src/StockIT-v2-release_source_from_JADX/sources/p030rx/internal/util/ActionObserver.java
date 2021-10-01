package p030rx.internal.util;

import p030rx.Observer;
import p030rx.functions.Action0;
import p030rx.functions.Action1;

/* renamed from: rx.internal.util.ActionObserver */
public final class ActionObserver<T> implements Observer<T> {
    final Action0 onCompleted;
    final Action1<? super Throwable> onError;
    final Action1<? super T> onNext;

    public ActionObserver(Action1<? super T> action1, Action1<? super Throwable> action12, Action0 action0) {
        this.onNext = action1;
        this.onError = action12;
        this.onCompleted = action0;
    }

    public void onNext(T t) {
        this.onNext.call(t);
    }

    public void onError(Throwable th) {
        this.onError.call(th);
    }

    public void onCompleted() {
        this.onCompleted.call();
    }
}
