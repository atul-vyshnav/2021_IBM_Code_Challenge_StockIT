package com.polidea.multiplatformbleadapter.utils;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble.RxBleCustomOperation;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Scheduler;

public class RefreshGattCustomOperation implements RxBleCustomOperation<Boolean> {
    public Observable<Boolean> asObservable(final BluetoothGatt bluetoothGatt, RxBleGattCallback rxBleGattCallback, Scheduler scheduler) throws Throwable {
        return Observable.amb(Observable.fromCallable(new Callable<Boolean>() {
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0049  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Boolean call() throws java.lang.Exception {
                /*
                    r6 = this;
                    r0 = 0
                    android.bluetooth.BluetoothGatt r1 = r4     // Catch:{ Exception -> 0x0037 }
                    java.lang.Class r1 = r1.getClass()     // Catch:{ Exception -> 0x0037 }
                    java.lang.String r2 = "refresh"
                    java.lang.Class[] r3 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0037 }
                    java.lang.reflect.Method r1 = r1.getMethod(r2, r3)     // Catch:{ Exception -> 0x0037 }
                    if (r1 != 0) goto L_0x001a
                    java.lang.String r1 = "Could not find function BluetoothGatt.refresh()"
                    java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0037 }
                    com.polidea.rxandroidble.internal.RxBleLog.m1110d(r1, r2)     // Catch:{ Exception -> 0x0037 }
                    r1 = 0
                    goto L_0x0041
                L_0x001a:
                    android.bluetooth.BluetoothGatt r2 = r4     // Catch:{ Exception -> 0x0037 }
                    java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0037 }
                    java.lang.Object r1 = r1.invoke(r2, r3)     // Catch:{ Exception -> 0x0037 }
                    java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ Exception -> 0x0037 }
                    boolean r1 = r1.booleanValue()     // Catch:{ Exception -> 0x0037 }
                    if (r1 != 0) goto L_0x0041
                    java.lang.String r2 = "BluetoothGatt.refresh() returned false"
                    java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0032 }
                    com.polidea.rxandroidble.internal.RxBleLog.m1110d(r2, r3)     // Catch:{ Exception -> 0x0032 }
                    goto L_0x0041
                L_0x0032:
                    r2 = move-exception
                    r5 = r2
                    r2 = r1
                    r1 = r5
                    goto L_0x0039
                L_0x0037:
                    r1 = move-exception
                    r2 = 0
                L_0x0039:
                    java.lang.Object[] r3 = new java.lang.Object[r0]
                    java.lang.String r4 = "Could not call function BluetoothGatt.refresh()"
                    com.polidea.rxandroidble.internal.RxBleLog.m1111d(r1, r4, r3)
                    r1 = r2
                L_0x0041:
                    r2 = 1
                    java.lang.Object[] r2 = new java.lang.Object[r2]
                    if (r1 == 0) goto L_0x0049
                    java.lang.String r3 = "Success"
                    goto L_0x004b
                L_0x0049:
                    java.lang.String r3 = "Failure"
                L_0x004b:
                    r2[r0] = r3
                    java.lang.String r0 = "Calling BluetoothGatt.refresh() status: %s"
                    com.polidea.rxandroidble.internal.RxBleLog.m1114i(r0, r2)
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.polidea.multiplatformbleadapter.utils.RefreshGattCustomOperation.C24781.call():java.lang.Boolean");
            }
        }).subscribeOn(scheduler).delay(1, TimeUnit.SECONDS, scheduler), rxBleGattCallback.observeDisconnect());
    }
}
