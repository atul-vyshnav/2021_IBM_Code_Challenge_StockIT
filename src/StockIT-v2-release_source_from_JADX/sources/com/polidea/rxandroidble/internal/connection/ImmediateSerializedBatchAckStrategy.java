package com.polidea.rxandroidble.internal.connection;

import com.polidea.rxandroidble.RxBleConnection;
import p030rx.Observable;

public class ImmediateSerializedBatchAckStrategy implements RxBleConnection.WriteOperationAckStrategy {
    public Observable<Boolean> call(Observable<Boolean> observable) {
        return observable;
    }
}
