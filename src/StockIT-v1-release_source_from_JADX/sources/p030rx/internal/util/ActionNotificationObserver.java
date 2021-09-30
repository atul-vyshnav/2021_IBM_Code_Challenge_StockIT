package p030rx.internal.util;

import p030rx.Notification;
import p030rx.Observer;
import p030rx.functions.Action1;

/* renamed from: rx.internal.util.ActionNotificationObserver */
public final class ActionNotificationObserver<T> implements Observer<T> {
    final Action1<Notification<? super T>> onNotification;

    public ActionNotificationObserver(Action1<Notification<? super T>> action1) {
        this.onNotification = action1;
    }

    public void onNext(T t) {
        this.onNotification.call(Notification.createOnNext(t));
    }

    public void onError(Throwable th) {
        this.onNotification.call(Notification.createOnError(th));
    }

    public void onCompleted() {
        this.onNotification.call(Notification.createOnCompleted());
    }
}
