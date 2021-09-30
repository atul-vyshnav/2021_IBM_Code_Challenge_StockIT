package com.polidea.rxandroidble.internal.connection;

import com.polidea.rxandroidble.ConnectionSetup;
import com.polidea.rxandroidble.RxBleConnection;
import p030rx.Observable;

public interface Connector {
    Observable<RxBleConnection> prepareConnection(ConnectionSetup connectionSetup);
}
