package com.polidea.rxandroidble.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.ConnectionSetup;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.internal.connection.ConnectionComponent;
import com.polidea.rxandroidble.internal.serialization.ClientOperationQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.functions.Func0;

public class ConnectorImpl implements Connector {
    /* access modifiers changed from: private */
    public final Scheduler callbacksScheduler;
    /* access modifiers changed from: private */
    public final ClientOperationQueue clientOperationQueue;
    /* access modifiers changed from: private */
    public final ConnectionComponent.Builder connectionComponentBuilder;

    @Inject
    public ConnectorImpl(ClientOperationQueue clientOperationQueue2, ConnectionComponent.Builder builder, @Named("bluetooth_callbacks") Scheduler scheduler) {
        this.clientOperationQueue = clientOperationQueue2;
        this.connectionComponentBuilder = builder;
        this.callbacksScheduler = scheduler;
    }

    public Observable<RxBleConnection> prepareConnection(final ConnectionSetup connectionSetup) {
        return Observable.defer(new Func0<Observable<RxBleConnection>>() {
            public Observable<RxBleConnection> call() {
                final ConnectionComponent build = ConnectorImpl.this.connectionComponentBuilder.connectionModule(new ConnectionModule(connectionSetup)).build();
                Observable fromCallable = Observable.fromCallable(new Callable<RxBleConnection>() {
                    public RxBleConnection call() throws Exception {
                        return build.rxBleConnection();
                    }
                });
                Observable queue = ConnectorImpl.this.clientOperationQueue.queue(build.connectOperation());
                Observable observeDisconnect = build.gattCallback().observeDisconnect();
                final Set<ConnectionSubscriptionWatcher> connectionSubscriptionWatchers = build.connectionSubscriptionWatchers();
                return Observable.merge(fromCallable.delaySubscription(queue), observeDisconnect).doOnSubscribe(new Action0() {
                    public void call() {
                        for (ConnectionSubscriptionWatcher onConnectionSubscribed : connectionSubscriptionWatchers) {
                            onConnectionSubscribed.onConnectionSubscribed();
                        }
                    }
                }).doOnUnsubscribe(new Action0() {
                    public void call() {
                        for (ConnectionSubscriptionWatcher onConnectionUnsubscribed : connectionSubscriptionWatchers) {
                            onConnectionUnsubscribed.onConnectionUnsubscribed();
                        }
                    }
                }).subscribeOn(ConnectorImpl.this.callbacksScheduler).unsubscribeOn(ConnectorImpl.this.callbacksScheduler);
            }
        });
    }
}
