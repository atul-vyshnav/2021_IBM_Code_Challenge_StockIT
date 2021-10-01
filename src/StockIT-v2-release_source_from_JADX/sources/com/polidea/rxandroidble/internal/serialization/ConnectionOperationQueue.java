package com.polidea.rxandroidble.internal.serialization;

import com.polidea.rxandroidble.exceptions.BleException;

public interface ConnectionOperationQueue extends ClientOperationQueue {
    void terminate(BleException bleException);
}
