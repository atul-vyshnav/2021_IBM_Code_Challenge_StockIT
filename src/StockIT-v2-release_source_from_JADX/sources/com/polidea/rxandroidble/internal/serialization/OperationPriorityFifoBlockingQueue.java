package com.polidea.rxandroidble.internal.serialization;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

class OperationPriorityFifoBlockingQueue {

    /* renamed from: q */
    private final PriorityBlockingQueue<FIFORunnableEntry> f1185q = new PriorityBlockingQueue<>();

    OperationPriorityFifoBlockingQueue() {
    }

    public void add(FIFORunnableEntry fIFORunnableEntry) {
        this.f1185q.add(fIFORunnableEntry);
    }

    public FIFORunnableEntry<?> take() throws InterruptedException {
        return this.f1185q.take();
    }

    public FIFORunnableEntry<?> takeNow() {
        return this.f1185q.poll();
    }

    public boolean isEmpty() {
        return this.f1185q.isEmpty();
    }

    public boolean remove(FIFORunnableEntry fIFORunnableEntry) {
        Iterator<FIFORunnableEntry> it = this.f1185q.iterator();
        while (it.hasNext()) {
            FIFORunnableEntry next = it.next();
            if (next == fIFORunnableEntry) {
                return this.f1185q.remove(next);
            }
        }
        return false;
    }
}
