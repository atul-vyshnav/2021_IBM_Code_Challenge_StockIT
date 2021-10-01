package com.polidea.rxandroidble.internal.serialization;

import com.polidea.rxandroidble.internal.RxBleLog;
import java.util.concurrent.atomic.AtomicBoolean;

class QueueSemaphore implements QueueReleaseInterface, QueueAwaitReleaseInterface {
    private final AtomicBoolean isReleased = new AtomicBoolean(false);

    QueueSemaphore() {
    }

    public synchronized void awaitRelease() throws InterruptedException {
        while (!this.isReleased.get()) {
            try {
                wait();
            } catch (InterruptedException e) {
                if (!this.isReleased.get()) {
                    RxBleLog.m1119w(e, "Queue's awaitRelease() has been interrupted abruptly while it wasn't released by the release() method.", new Object[0]);
                }
            }
        }
    }

    public synchronized void release() {
        if (this.isReleased.compareAndSet(false, true)) {
            notify();
        }
    }
}
