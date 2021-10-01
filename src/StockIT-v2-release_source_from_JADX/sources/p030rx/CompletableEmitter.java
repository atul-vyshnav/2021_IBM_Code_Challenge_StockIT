package p030rx;

import p030rx.functions.Cancellable;

/* renamed from: rx.CompletableEmitter */
public interface CompletableEmitter {
    void onCompleted();

    void onError(Throwable th);

    void setCancellation(Cancellable cancellable);

    void setSubscription(Subscription subscription);
}
