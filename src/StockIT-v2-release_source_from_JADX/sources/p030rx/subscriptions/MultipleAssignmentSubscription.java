package p030rx.subscriptions;

import p030rx.Subscription;
import p030rx.internal.subscriptions.SequentialSubscription;

/* renamed from: rx.subscriptions.MultipleAssignmentSubscription */
public final class MultipleAssignmentSubscription implements Subscription {
    final SequentialSubscription state = new SequentialSubscription();

    public boolean isUnsubscribed() {
        return this.state.isUnsubscribed();
    }

    public void unsubscribe() {
        this.state.unsubscribe();
    }

    public void set(Subscription subscription) {
        if (subscription != null) {
            this.state.replace(subscription);
            return;
        }
        throw new IllegalArgumentException("Subscription can not be null");
    }

    public Subscription get() {
        return this.state.current();
    }
}
