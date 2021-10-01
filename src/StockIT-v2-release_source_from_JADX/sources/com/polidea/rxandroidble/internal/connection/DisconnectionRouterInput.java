package com.polidea.rxandroidble.internal.connection;

import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleGattException;

interface DisconnectionRouterInput {
    void onDisconnectedException(BleDisconnectedException bleDisconnectedException);

    void onGattConnectionStateException(BleGattException bleGattException);
}
