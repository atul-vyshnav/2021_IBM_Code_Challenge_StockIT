package com.polidea.rxandroidble.internal.operations;

import com.polidea.rxandroidble.internal.Priority;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import p030rx.Observable;

public interface Operation<T> extends Comparable<Operation<?>> {
    Priority definedPriority();

    Observable<T> run(QueueReleaseInterface queueReleaseInterface);
}
