package com.polidea.rxandroidble.internal.serialization;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.connection.ConnectionScope;
import com.polidea.rxandroidble.internal.connection.ConnectionSubscriptionWatcher;
import com.polidea.rxandroidble.internal.connection.DisconnectionRouterOutput;
import com.polidea.rxandroidble.internal.operations.Operation;
import com.polidea.rxandroidble.internal.util.OperationLogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.Subscription;
import p030rx.functions.Action1;
import p030rx.functions.Cancellable;

@ConnectionScope
public class ConnectionOperationQueueImpl implements ConnectionOperationQueue, ConnectionSubscriptionWatcher {
    private final String deviceMacAddress;
    private BleException disconnectionException = null;
    private final DisconnectionRouterOutput disconnectionRouterOutput;
    private Subscription disconnectionThrowableSubscription;
    /* access modifiers changed from: private */
    public final OperationPriorityFifoBlockingQueue queue = new OperationPriorityFifoBlockingQueue();
    private final Future<?> runnableFuture;
    /* access modifiers changed from: private */
    public volatile boolean shouldRun = true;

    @Inject
    ConnectionOperationQueueImpl(@Named("mac-address") String str, DisconnectionRouterOutput disconnectionRouterOutput2, @Named("executor_connection_queue") ExecutorService executorService, @Named("bluetooth_interaction") final Scheduler scheduler) {
        this.deviceMacAddress = str;
        this.disconnectionRouterOutput = disconnectionRouterOutput2;
        this.runnableFuture = executorService.submit(new Runnable() {
            public void run() {
                while (true) {
                    if (!ConnectionOperationQueueImpl.this.shouldRun) {
                        break;
                    }
                    try {
                        FIFORunnableEntry<?> take = ConnectionOperationQueueImpl.this.queue.take();
                        Operation<T> operation = take.operation;
                        long currentTimeMillis = System.currentTimeMillis();
                        OperationLogger.logOperationStarted(operation);
                        QueueSemaphore queueSemaphore = new QueueSemaphore();
                        take.emitter.setSubscription(take.run(queueSemaphore, scheduler));
                        queueSemaphore.awaitRelease();
                        OperationLogger.logOperationFinished(operation, currentTimeMillis, System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        synchronized (ConnectionOperationQueueImpl.this) {
                            if (!ConnectionOperationQueueImpl.this.shouldRun) {
                                ConnectionOperationQueueImpl.this.flushQueue();
                                RxBleLog.m1110d("Terminated.", new Object[0]);
                                return;
                            }
                            RxBleLog.m1113e(e, "Error while processing connection operation queue", new Object[0]);
                        }
                    }
                }
                while (true) {
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public synchronized void flushQueue() {
        while (!this.queue.isEmpty()) {
            this.queue.takeNow().emitter.onError(this.disconnectionException);
        }
    }

    public synchronized <T> Observable<T> queue(final Operation<T> operation) {
        if (!this.shouldRun) {
            return Observable.error(this.disconnectionException);
        }
        return Observable.create(new Action1<Emitter<T>>() {
            public void call(Emitter<T> emitter) {
                final FIFORunnableEntry fIFORunnableEntry = new FIFORunnableEntry(operation, emitter);
                emitter.setCancellation(new Cancellable() {
                    public void cancel() throws Exception {
                        if (ConnectionOperationQueueImpl.this.queue.remove(fIFORunnableEntry)) {
                            OperationLogger.logOperationRemoved(operation);
                        }
                    }
                });
                OperationLogger.logOperationQueued(operation);
                ConnectionOperationQueueImpl.this.queue.add(fIFORunnableEntry);
            }
        }, Emitter.BackpressureMode.NONE);
    }

    public synchronized void terminate(BleException bleException) {
        if (this.disconnectionException == null) {
            RxBleLog.m1114i("Connection operations queue to be terminated (" + this.deviceMacAddress + ')', new Object[0]);
            this.shouldRun = false;
            this.disconnectionException = bleException;
            this.runnableFuture.cancel(true);
        }
    }

    public void onConnectionSubscribed() {
        this.disconnectionThrowableSubscription = this.disconnectionRouterOutput.asValueOnlyObservable().subscribe(new Action1<BleException>() {
            public void call(BleException bleException) {
                ConnectionOperationQueueImpl.this.terminate(bleException);
            }
        });
    }

    public void onConnectionUnsubscribed() {
        this.disconnectionThrowableSubscription.unsubscribe();
        this.disconnectionThrowableSubscription = null;
        terminate(new BleDisconnectedException(this.deviceMacAddress, -1));
    }
}
