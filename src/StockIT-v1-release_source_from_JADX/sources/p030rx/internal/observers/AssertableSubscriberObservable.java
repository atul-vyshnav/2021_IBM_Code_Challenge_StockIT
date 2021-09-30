package p030rx.internal.observers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import p030rx.Producer;
import p030rx.Subscriber;
import p030rx.functions.Action0;
import p030rx.observers.AssertableSubscriber;
import p030rx.observers.TestSubscriber;

/* renamed from: rx.internal.observers.AssertableSubscriberObservable */
public class AssertableSubscriberObservable<T> extends Subscriber<T> implements AssertableSubscriber<T> {

    /* renamed from: ts */
    private final TestSubscriber<T> f1341ts;

    public AssertableSubscriberObservable(TestSubscriber<T> testSubscriber) {
        this.f1341ts = testSubscriber;
    }

    public static <T> AssertableSubscriberObservable<T> create(long j) {
        TestSubscriber testSubscriber = new TestSubscriber(j);
        AssertableSubscriberObservable<T> assertableSubscriberObservable = new AssertableSubscriberObservable<>(testSubscriber);
        assertableSubscriberObservable.add(testSubscriber);
        return assertableSubscriberObservable;
    }

    public void onStart() {
        this.f1341ts.onStart();
    }

    public void onCompleted() {
        this.f1341ts.onCompleted();
    }

    public void setProducer(Producer producer) {
        this.f1341ts.setProducer(producer);
    }

    public final int getCompletions() {
        return this.f1341ts.getCompletions();
    }

    public void onError(Throwable th) {
        this.f1341ts.onError(th);
    }

    public List<Throwable> getOnErrorEvents() {
        return this.f1341ts.getOnErrorEvents();
    }

    public void onNext(T t) {
        this.f1341ts.onNext(t);
    }

    public final int getValueCount() {
        return this.f1341ts.getValueCount();
    }

    public AssertableSubscriber<T> requestMore(long j) {
        this.f1341ts.requestMore(j);
        return this;
    }

    public List<T> getOnNextEvents() {
        return this.f1341ts.getOnNextEvents();
    }

    public AssertableSubscriber<T> assertReceivedOnNext(List<T> list) {
        this.f1341ts.assertReceivedOnNext(list);
        return this;
    }

    public final AssertableSubscriber<T> awaitValueCount(int i, long j, TimeUnit timeUnit) {
        if (this.f1341ts.awaitValueCount(i, j, timeUnit)) {
            return this;
        }
        throw new AssertionError("Did not receive enough values in time. Expected: " + i + ", Actual: " + this.f1341ts.getValueCount());
    }

    public AssertableSubscriber<T> assertTerminalEvent() {
        this.f1341ts.assertTerminalEvent();
        return this;
    }

    public AssertableSubscriber<T> assertUnsubscribed() {
        this.f1341ts.assertUnsubscribed();
        return this;
    }

    public AssertableSubscriber<T> assertNoErrors() {
        this.f1341ts.assertNoErrors();
        return this;
    }

    public AssertableSubscriber<T> awaitTerminalEvent() {
        this.f1341ts.awaitTerminalEvent();
        return this;
    }

    public AssertableSubscriber<T> awaitTerminalEvent(long j, TimeUnit timeUnit) {
        this.f1341ts.awaitTerminalEvent(j, timeUnit);
        return this;
    }

    public AssertableSubscriber<T> awaitTerminalEventAndUnsubscribeOnTimeout(long j, TimeUnit timeUnit) {
        this.f1341ts.awaitTerminalEventAndUnsubscribeOnTimeout(j, timeUnit);
        return this;
    }

    public Thread getLastSeenThread() {
        return this.f1341ts.getLastSeenThread();
    }

    public AssertableSubscriber<T> assertCompleted() {
        this.f1341ts.assertCompleted();
        return this;
    }

    public AssertableSubscriber<T> assertNotCompleted() {
        this.f1341ts.assertNotCompleted();
        return this;
    }

    public AssertableSubscriber<T> assertError(Class<? extends Throwable> cls) {
        this.f1341ts.assertError(cls);
        return this;
    }

    public AssertableSubscriber<T> assertError(Throwable th) {
        this.f1341ts.assertError(th);
        return this;
    }

    public AssertableSubscriber<T> assertNoTerminalEvent() {
        this.f1341ts.assertNoTerminalEvent();
        return this;
    }

    public AssertableSubscriber<T> assertNoValues() {
        this.f1341ts.assertNoValues();
        return this;
    }

    public AssertableSubscriber<T> assertValueCount(int i) {
        this.f1341ts.assertValueCount(i);
        return this;
    }

    public AssertableSubscriber<T> assertValues(T... tArr) {
        this.f1341ts.assertValues(tArr);
        return this;
    }

    public AssertableSubscriber<T> assertValue(T t) {
        this.f1341ts.assertValue(t);
        return this;
    }

    public final AssertableSubscriber<T> assertValuesAndClear(T t, T... tArr) {
        this.f1341ts.assertValuesAndClear(t, tArr);
        return this;
    }

    public final AssertableSubscriber<T> perform(Action0 action0) {
        action0.call();
        return this;
    }

    public String toString() {
        return this.f1341ts.toString();
    }

    public final AssertableSubscriber<T> assertResult(T... tArr) {
        this.f1341ts.assertValues(tArr);
        this.f1341ts.assertNoErrors();
        this.f1341ts.assertCompleted();
        return this;
    }

    public final AssertableSubscriber<T> assertFailure(Class<? extends Throwable> cls, T... tArr) {
        this.f1341ts.assertValues(tArr);
        this.f1341ts.assertError(cls);
        this.f1341ts.assertNotCompleted();
        return this;
    }

    public final AssertableSubscriber<T> assertFailureAndMessage(Class<? extends Throwable> cls, String str, T... tArr) {
        this.f1341ts.assertValues(tArr);
        this.f1341ts.assertError(cls);
        this.f1341ts.assertNotCompleted();
        String message = this.f1341ts.getOnErrorEvents().get(0).getMessage();
        if (message == str || (str != null && str.equals(message))) {
            return this;
        }
        throw new AssertionError("Error message differs. Expected: '" + str + "', Received: '" + message + "'");
    }
}
