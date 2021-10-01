package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.internal.operations.OperationsProvider;
import com.polidea.rxandroidble.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble.internal.serialization.ConnectionOperationQueue;
import java.util.List;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.functions.Func0;
import p030rx.functions.Func1;
import p030rx.schedulers.Schedulers;
import p030rx.subjects.BehaviorSubject;
import p030rx.subjects.SerializedSubject;

@ConnectionScope
class ServiceDiscoveryManager {
    /* access modifiers changed from: private */
    public final BluetoothGatt bluetoothGatt;
    private Observable<RxBleDeviceServices> deviceServicesObservable;
    /* access modifiers changed from: private */
    public boolean hasCachedResults = false;
    /* access modifiers changed from: private */
    public final OperationsProvider operationProvider;
    /* access modifiers changed from: private */
    public final ConnectionOperationQueue operationQueue;
    /* access modifiers changed from: private */
    public SerializedSubject<TimeoutConfiguration, TimeoutConfiguration> timeoutBehaviorSubject = BehaviorSubject.create().toSerialized();

    @Inject
    ServiceDiscoveryManager(ConnectionOperationQueue connectionOperationQueue, BluetoothGatt bluetoothGatt2, OperationsProvider operationsProvider) {
        this.operationQueue = connectionOperationQueue;
        this.bluetoothGatt = bluetoothGatt2;
        this.operationProvider = operationsProvider;
        reset();
    }

    /* access modifiers changed from: package-private */
    public Observable<RxBleDeviceServices> getDiscoverServicesObservable(final long j, final TimeUnit timeUnit) {
        if (this.hasCachedResults) {
            return this.deviceServicesObservable;
        }
        return this.deviceServicesObservable.doOnSubscribe(new Action0() {
            public void call() {
                ServiceDiscoveryManager.this.timeoutBehaviorSubject.onNext(new TimeoutConfiguration(j, timeUnit, Schedulers.computation()));
            }
        });
    }

    /* access modifiers changed from: private */
    public void reset() {
        this.hasCachedResults = false;
        this.deviceServicesObservable = Observable.fromCallable(new Func0<List<BluetoothGattService>>() {
            public List<BluetoothGattService> call() {
                return ServiceDiscoveryManager.this.bluetoothGatt.getServices();
            }
        }).filter(new Func1<List<BluetoothGattService>, Boolean>() {
            public Boolean call(List<BluetoothGattService> list) {
                return Boolean.valueOf(list.size() > 0);
            }
        }).map(new Func1<List<BluetoothGattService>, RxBleDeviceServices>() {
            public RxBleDeviceServices call(List<BluetoothGattService> list) {
                return new RxBleDeviceServices(list);
            }
        }).switchIfEmpty(getTimeoutConfiguration().flatMap(scheduleActualDiscoveryWithTimeout())).doOnNext(new Action1<RxBleDeviceServices>() {
            public void call(RxBleDeviceServices rxBleDeviceServices) {
                boolean unused = ServiceDiscoveryManager.this.hasCachedResults = true;
            }
        }).doOnError(new Action1<Throwable>() {
            public void call(Throwable th) {
                ServiceDiscoveryManager.this.reset();
            }
        }).cacheWithInitialCapacity(1);
    }

    private Observable<TimeoutConfiguration> getTimeoutConfiguration() {
        return this.timeoutBehaviorSubject.take(1);
    }

    private Func1<TimeoutConfiguration, Observable<RxBleDeviceServices>> scheduleActualDiscoveryWithTimeout() {
        return new Func1<TimeoutConfiguration, Observable<RxBleDeviceServices>>() {
            public Observable<RxBleDeviceServices> call(TimeoutConfiguration timeoutConfiguration) {
                return ServiceDiscoveryManager.this.operationQueue.queue(ServiceDiscoveryManager.this.operationProvider.provideServiceDiscoveryOperation(timeoutConfiguration.timeout, timeoutConfiguration.timeoutTimeUnit));
            }
        };
    }
}
