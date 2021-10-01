package com.polidea.rxandroidble.internal.connection;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.operations.DisconnectOperation;
import com.polidea.rxandroidble.internal.serialization.ClientOperationQueue;
import p030rx.functions.Action1;
import p030rx.functions.Actions;

@ConnectionScope
class DisconnectAction implements ConnectionSubscriptionWatcher {
    private final ClientOperationQueue clientOperationQueue;
    private final DisconnectOperation operationDisconnect;

    public void onConnectionSubscribed() {
    }

    @Inject
    DisconnectAction(ClientOperationQueue clientOperationQueue2, DisconnectOperation disconnectOperation) {
        this.clientOperationQueue = clientOperationQueue2;
        this.operationDisconnect = disconnectOperation;
    }

    public void onConnectionUnsubscribed() {
        this.clientOperationQueue.queue(this.operationDisconnect).subscribe(Actions.empty(), (Action1<Throwable>) Actions.toAction1(Actions.empty()));
    }
}
