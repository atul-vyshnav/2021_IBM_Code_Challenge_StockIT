package com.polidea.rxandroidble.internal.serialization;

import com.polidea.rxandroidble.internal.operations.Operation;
import p030rx.Observable;

public interface ClientOperationQueue {
    <T> Observable<T> queue(Operation<T> operation);
}
