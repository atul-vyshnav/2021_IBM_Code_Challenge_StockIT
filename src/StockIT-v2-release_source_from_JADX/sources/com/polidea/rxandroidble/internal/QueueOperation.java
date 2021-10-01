package com.polidea.rxandroidble.internal;

import android.os.DeadObjectException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.internal.operations.Operation;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.functions.Action1;

public abstract class QueueOperation<T> implements Operation<T> {
    /* access modifiers changed from: protected */
    public abstract void protectedRun(Emitter<T> emitter, QueueReleaseInterface queueReleaseInterface) throws Throwable;

    /* access modifiers changed from: protected */
    public abstract BleException provideException(DeadObjectException deadObjectException);

    public final Observable<T> run(final QueueReleaseInterface queueReleaseInterface) {
        return Observable.create(new Action1<Emitter<T>>() {
            public void call(Emitter<T> emitter) {
                try {
                    QueueOperation.this.protectedRun(emitter, queueReleaseInterface);
                } catch (DeadObjectException e) {
                    emitter.onError(QueueOperation.this.provideException(e));
                } catch (Throwable th) {
                    emitter.onError(th);
                }
            }
        }, Emitter.BackpressureMode.NONE);
    }

    public Priority definedPriority() {
        return Priority.NORMAL;
    }

    public int compareTo(Operation operation) {
        return operation.definedPriority().priority - definedPriority().priority;
    }
}
