package p030rx.internal.operators;

import p030rx.Single;
import p030rx.SingleSubscriber;
import p030rx.exceptions.AssemblyStackTraceException;

/* renamed from: rx.internal.operators.OnSubscribeOnAssemblySingle */
public final class OnSubscribeOnAssemblySingle<T> implements Single.OnSubscribe<T> {
    public static volatile boolean fullStackTrace;
    final Single.OnSubscribe<T> source;
    final String stacktrace = OnSubscribeOnAssembly.createStacktrace();

    public OnSubscribeOnAssemblySingle(Single.OnSubscribe<T> onSubscribe) {
        this.source = onSubscribe;
    }

    public void call(SingleSubscriber<? super T> singleSubscriber) {
        this.source.call(new OnAssemblySingleSubscriber(singleSubscriber, this.stacktrace));
    }

    /* renamed from: rx.internal.operators.OnSubscribeOnAssemblySingle$OnAssemblySingleSubscriber */
    static final class OnAssemblySingleSubscriber<T> extends SingleSubscriber<T> {
        final SingleSubscriber<? super T> actual;
        final String stacktrace;

        public OnAssemblySingleSubscriber(SingleSubscriber<? super T> singleSubscriber, String str) {
            this.actual = singleSubscriber;
            this.stacktrace = str;
            singleSubscriber.add(this);
        }

        public void onError(Throwable th) {
            new AssemblyStackTraceException(this.stacktrace).attachTo(th);
            this.actual.onError(th);
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }
    }
}
