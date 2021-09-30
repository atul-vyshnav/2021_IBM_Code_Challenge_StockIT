package p030rx.plugins;

import p030rx.Completable;

/* renamed from: rx.plugins.RxJavaCompletableExecutionHook */
public abstract class RxJavaCompletableExecutionHook {
    @Deprecated
    public Completable.OnSubscribe onCreate(Completable.OnSubscribe onSubscribe) {
        return onSubscribe;
    }

    @Deprecated
    public Completable.Operator onLift(Completable.Operator operator) {
        return operator;
    }

    @Deprecated
    public Throwable onSubscribeError(Throwable th) {
        return th;
    }

    @Deprecated
    public Completable.OnSubscribe onSubscribeStart(Completable completable, Completable.OnSubscribe onSubscribe) {
        return onSubscribe;
    }
}
