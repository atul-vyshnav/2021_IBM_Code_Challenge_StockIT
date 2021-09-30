package com.polidea.rxandroidble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.polidea.rxandroidble.exceptions.BleCharacteristicNotFoundException;
import com.polidea.rxandroidble.exceptions.BleServiceNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import p030rx.Observable;
import p030rx.functions.Func1;

public class RxBleDeviceServices {
    /* access modifiers changed from: private */
    public final List<BluetoothGattService> bluetoothGattServices;

    public RxBleDeviceServices(List<BluetoothGattService> list) {
        this.bluetoothGattServices = list;
    }

    public List<BluetoothGattService> getBluetoothGattServices() {
        return this.bluetoothGattServices;
    }

    public Observable<BluetoothGattService> getService(final UUID uuid) {
        return Observable.from(this.bluetoothGattServices).takeFirst(new Func1<BluetoothGattService, Boolean>() {
            public Boolean call(BluetoothGattService bluetoothGattService) {
                return Boolean.valueOf(bluetoothGattService.getUuid().equals(uuid));
            }
        }).switchIfEmpty(Observable.error(new BleServiceNotFoundException(uuid)));
    }

    public Observable<BluetoothGattCharacteristic> getCharacteristic(final UUID uuid) {
        return Observable.fromCallable(new Callable<BluetoothGattCharacteristic>() {
            public BluetoothGattCharacteristic call() throws Exception {
                for (BluetoothGattService characteristic : RxBleDeviceServices.this.bluetoothGattServices) {
                    BluetoothGattCharacteristic characteristic2 = characteristic.getCharacteristic(uuid);
                    if (characteristic2 != null) {
                        return characteristic2;
                    }
                }
                throw new BleCharacteristicNotFoundException(uuid);
            }
        });
    }

    public Observable<BluetoothGattCharacteristic> getCharacteristic(UUID uuid, final UUID uuid2) {
        return getService(uuid).map(new Func1<BluetoothGattService, BluetoothGattCharacteristic>() {
            public BluetoothGattCharacteristic call(BluetoothGattService bluetoothGattService) {
                return bluetoothGattService.getCharacteristic(uuid2);
            }
        }).takeFirst(new Func1<BluetoothGattCharacteristic, Boolean>() {
            public Boolean call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return Boolean.valueOf(bluetoothGattCharacteristic != null);
            }
        }).switchIfEmpty(Observable.error(new BleCharacteristicNotFoundException(uuid2)));
    }

    public Observable<BluetoothGattDescriptor> getDescriptor(UUID uuid, final UUID uuid2) {
        return getCharacteristic(uuid).map(new Func1<BluetoothGattCharacteristic, BluetoothGattDescriptor>() {
            public BluetoothGattDescriptor call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return bluetoothGattCharacteristic.getDescriptor(uuid2);
            }
        }).filter(new Func1<Object, Boolean>() {
            public Boolean call(Object obj) {
                return Boolean.valueOf(obj != null);
            }
        });
    }

    public Observable<BluetoothGattDescriptor> getDescriptor(UUID uuid, final UUID uuid2, final UUID uuid3) {
        return getService(uuid).map(new Func1<BluetoothGattService, BluetoothGattCharacteristic>() {
            public BluetoothGattCharacteristic call(BluetoothGattService bluetoothGattService) {
                return bluetoothGattService.getCharacteristic(uuid2);
            }
        }).map(new Func1<BluetoothGattCharacteristic, BluetoothGattDescriptor>() {
            public BluetoothGattDescriptor call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return bluetoothGattCharacteristic.getDescriptor(uuid3);
            }
        });
    }
}
