package p030rx;

/* renamed from: rx.CompletableSubscriber */
public interface CompletableSubscriber {
    void onCompleted();

    void onError(Throwable th);

    void onSubscribe(Subscription subscription);
}
