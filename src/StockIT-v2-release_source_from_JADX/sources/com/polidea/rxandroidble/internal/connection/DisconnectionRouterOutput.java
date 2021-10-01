package com.polidea.rxandroidble.internal.connection;

import com.polidea.rxandroidble.exceptions.BleException;
import p030rx.Observable;

public interface DisconnectionRouterOutput {
    <T> Observable<T> asErrorOnlyObservable();

    Observable<BleException> asValueOnlyObservable();
}
