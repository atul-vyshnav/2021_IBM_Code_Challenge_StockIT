package p030rx;

import p030rx.internal.util.SubscriptionList;

/* renamed from: rx.SingleSubscriber */
public abstract class SingleSubscriber<T> implements Subscription {

    /* renamed from: cs */
    private final SubscriptionList f1340cs = new SubscriptionList();

    public abstract void onError(Throwable th);

    public abstract void onSuccess(T t);

    public final void add(Subscription subscription) {
        this.f1340cs.add(subscription);
    }

    public final void unsubscribe() {
        this.f1340cs.unsubscribe();
    }

    public final boolean isUnsubscribed() {
        return this.f1340cs.isUnsubscribed();
    }
}
