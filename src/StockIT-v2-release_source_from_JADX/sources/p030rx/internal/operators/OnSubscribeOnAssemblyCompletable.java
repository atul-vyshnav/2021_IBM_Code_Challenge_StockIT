package p030rx.internal.operators;

import p030rx.Completable;
import p030rx.CompletableSubscriber;
import p030rx.Subscription;
import p030rx.exceptions.AssemblyStackTraceException;

/* renamed from: rx.internal.operators.OnSubscribeOnAssemblyCompletable */
public final class OnSubscribeOnAssemblyCompletable<T> implements Completable.OnSubscribe {
    public static volatile boolean fullStackTrace;
    final Completable.OnSubscribe source;
    final String stacktrace = OnSubscribeOnAssembly.createStacktrace();

    public OnSubscribeOnAssemblyCompletable(Completable.OnSubscribe onSubscribe) {
        this.source = onSubscribe;
    }

    public void call(CompletableSubscriber completableSubscriber) {
        this.source.call(new OnAssemblyCompletableSubscriber(completableSubscriber, this.stacktrace));
    }

    /* renamed from: rx.internal.operators.OnSubscribeOnAssemblyCompletable$OnAssemblyCompletableSubscriber */
    static final class OnAssemblyCompletableSubscriber implements CompletableSubscriber {
        final CompletableSubscriber actual;
        final String stacktrace;

        public OnAssemblyCompletableSubscriber(CompletableSubscriber completableSubscriber, String str) {
            this.actual = completableSubscriber;
            this.stacktrace = str;
        }

        public void onSubscribe(Subscription subscription) {
            this.actual.onSubscribe(subscription);
        }

        public void onCompleted() {
            this.actual.onCompleted();
        }

        public void onError(Throwable th) {
            new AssemblyStackTraceException(this.stacktrace).attachTo(th);
            this.actual.onError(th);
        }
    }
}
