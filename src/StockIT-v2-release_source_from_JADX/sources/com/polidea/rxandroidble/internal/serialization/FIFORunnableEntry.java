package com.polidea.rxandroidble.internal.serialization;

import com.polidea.rxandroidble.internal.operations.Operation;
import java.util.concurrent.atomic.AtomicLong;
import p030rx.Emitter;
import p030rx.Scheduler;
import p030rx.Subscription;

class FIFORunnableEntry<T> implements Comparable<FIFORunnableEntry> {
    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    final Emitter<T> emitter;
    final Operation<T> operation;
    private final long seqNum = SEQUENCE.getAndIncrement();

    FIFORunnableEntry(Operation<T> operation2, Emitter<T> emitter2) {
        this.operation = operation2;
        this.emitter = emitter2;
    }

    public int compareTo(FIFORunnableEntry fIFORunnableEntry) {
        int compareTo = this.operation.compareTo(fIFORunnableEntry.operation);
        if (compareTo != 0 || fIFORunnableEntry.operation == this.operation) {
            return compareTo;
        }
        return this.seqNum < fIFORunnableEntry.seqNum ? -1 : 1;
    }

    public Subscription run(QueueSemaphore queueSemaphore, Scheduler scheduler) {
        return this.operation.run(queueSemaphore).subscribeOn(scheduler).unsubscribeOn(scheduler).subscribe(this.emitter);
    }
}
