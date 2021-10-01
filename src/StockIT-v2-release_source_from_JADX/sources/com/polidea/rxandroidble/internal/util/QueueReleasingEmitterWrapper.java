package com.polidea.rxandroidble.internal.util;

import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import java.util.concurrent.atomic.AtomicBoolean;
import p030rx.Emitter;
import p030rx.Observer;
import p030rx.functions.Cancellable;

public class QueueReleasingEmitterWrapper<T> implements Observer<T>, Cancellable {
    private final Emitter<T> emitter;
    private final AtomicBoolean isEmitterCanceled = new AtomicBoolean(false);
    private final QueueReleaseInterface queueReleaseInterface;

    public QueueReleasingEmitterWrapper(Emitter<T> emitter2, QueueReleaseInterface queueReleaseInterface2) {
        this.emitter = emitter2;
        this.queueReleaseInterface = queueReleaseInterface2;
        emitter2.setCancellation(this);
    }

    public void onCompleted() {
        this.queueReleaseInterface.release();
        this.emitter.onCompleted();
    }

    public void onError(Throwable th) {
        this.queueReleaseInterface.release();
        this.emitter.onError(th);
    }

    public void onNext(T t) {
        this.emitter.onNext(t);
    }

    public synchronized void cancel() throws Exception {
        this.isEmitterCanceled.set(true);
    }

    public synchronized boolean isWrappedEmitterUnsubscribed() {
        return this.isEmitterCanceled.get();
    }
}
