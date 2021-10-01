package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.SingleResponseOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.util.RxBleServicesLogger;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action1;
import p030rx.functions.Func0;
import p030rx.functions.Func1;

public class ServiceDiscoveryOperation extends SingleResponseOperation<RxBleDeviceServices> {
    /* access modifiers changed from: private */
    public final RxBleServicesLogger bleServicesLogger;
    /* access modifiers changed from: private */
    public final BluetoothGatt bluetoothGatt;

    ServiceDiscoveryOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt2, RxBleServicesLogger rxBleServicesLogger, TimeoutConfiguration timeoutConfiguration) {
        super(bluetoothGatt2, rxBleGattCallback, BleGattOperationType.SERVICE_DISCOVERY, timeoutConfiguration);
        this.bluetoothGatt = bluetoothGatt2;
        this.bleServicesLogger = rxBleServicesLogger;
    }

    /* access modifiers changed from: protected */
    public Observable<RxBleDeviceServices> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnServicesDiscovered().doOnNext(new Action1<RxBleDeviceServices>() {
            public void call(RxBleDeviceServices rxBleDeviceServices) {
                ServiceDiscoveryOperation.this.bleServicesLogger.log(rxBleDeviceServices, ServiceDiscoveryOperation.this.bluetoothGatt.getDevice());
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean startOperation(BluetoothGatt bluetoothGatt2) {
        return bluetoothGatt2.discoverServices();
    }

    /* access modifiers changed from: protected */
    public Observable<RxBleDeviceServices> timeoutFallbackProcedure(final BluetoothGatt bluetoothGatt2, RxBleGattCallback rxBleGattCallback, final Scheduler scheduler) {
        return Observable.defer(new Func0<Observable<RxBleDeviceServices>>() {
            public Observable<RxBleDeviceServices> call() {
                if (bluetoothGatt2.getServices().size() == 0) {
                    return Observable.error(new BleGattCallbackTimeoutException(bluetoothGatt2, BleGattOperationType.SERVICE_DISCOVERY));
                }
                return Observable.timer(5, TimeUnit.SECONDS, scheduler).flatMap(new Func1<Long, Observable<RxBleDeviceServices>>() {
                    public Observable<RxBleDeviceServices> call(Long l) {
                        return Observable.fromCallable(new Callable<RxBleDeviceServices>() {
                            public RxBleDeviceServices call() throws Exception {
                                return new RxBleDeviceServices(bluetoothGatt2.getServices());
                            }
                        });
                    }
                });
            }
        });
    }
}
