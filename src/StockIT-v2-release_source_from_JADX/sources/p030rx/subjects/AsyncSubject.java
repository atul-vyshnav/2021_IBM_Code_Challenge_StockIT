package p030rx.subjects;

import java.util.ArrayList;
import p030rx.Observable;
import p030rx.exceptions.Exceptions;
import p030rx.functions.Action1;
import p030rx.internal.operators.NotificationLite;
import p030rx.internal.producers.SingleProducer;
import p030rx.subjects.SubjectSubscriptionManager;

/* renamed from: rx.subjects.AsyncSubject */
public final class AsyncSubject<T> extends Subject<T, T> {
    volatile Object lastValue;
    final SubjectSubscriptionManager<T> state;

    public static <T> AsyncSubject<T> create() {
        final SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() {
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                Object latest = subjectSubscriptionManager.getLatest();
                if (latest == null || NotificationLite.isCompleted(latest)) {
                    subjectObserver.onCompleted();
                } else if (NotificationLite.isError(latest)) {
                    subjectObserver.onError(NotificationLite.getError(latest));
                } else {
                    subjectObserver.actual.setProducer(new SingleProducer(subjectObserver.actual, NotificationLite.getValue(latest)));
                }
            }
        };
        return new AsyncSubject<>(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    protected AsyncSubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(onSubscribe);
        this.state = subjectSubscriptionManager;
    }

    public void onCompleted() {
        if (this.state.active) {
            Object obj = this.lastValue;
            if (obj == null) {
                obj = NotificationLite.completed();
            }
            for (SubjectSubscriptionManager.SubjectObserver subjectObserver : this.state.terminate(obj)) {
                if (obj == NotificationLite.completed()) {
                    subjectObserver.onCompleted();
                } else {
                    subjectObserver.actual.setProducer(new SingleProducer(subjectObserver.actual, NotificationLite.getValue(obj)));
                }
            }
        }
    }

    public void onError(Throwable th) {
        if (this.state.active) {
            ArrayList arrayList = null;
            for (SubjectSubscriptionManager.SubjectObserver onError : this.state.terminate(NotificationLite.error(th))) {
                try {
                    onError.onError(th);
                } catch (Throwable th2) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(th2);
                }
            }
            Exceptions.throwIfAny(arrayList);
        }
    }

    public void onNext(T t) {
        this.lastValue = NotificationLite.next(t);
    }

    public boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    public boolean hasValue() {
        return !NotificationLite.isError(this.state.getLatest()) && NotificationLite.isNext(this.lastValue);
    }

    public boolean hasThrowable() {
        return NotificationLite.isError(this.state.getLatest());
    }

    public boolean hasCompleted() {
        Object latest = this.state.getLatest();
        return latest != null && !NotificationLite.isError(latest);
    }

    public T getValue() {
        Object obj = this.lastValue;
        if (NotificationLite.isError(this.state.getLatest()) || !NotificationLite.isNext(obj)) {
            return null;
        }
        return NotificationLite.getValue(obj);
    }

    public Throwable getThrowable() {
        Object latest = this.state.getLatest();
        if (NotificationLite.isError(latest)) {
            return NotificationLite.getError(latest);
        }
        return null;
    }
}
