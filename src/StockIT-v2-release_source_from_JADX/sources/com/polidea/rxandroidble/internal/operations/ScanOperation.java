package com.polidea.rxandroidble.internal.operations;

import android.os.DeadObjectException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.polidea.rxandroidble.internal.QueueOperation;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;
import p030rx.Emitter;
import p030rx.functions.Cancellable;

public abstract class ScanOperation<SCAN_RESULT_TYPE, SCAN_CALLBACK_TYPE> extends QueueOperation<SCAN_RESULT_TYPE> {
    /* access modifiers changed from: private */
    public final RxBleAdapterWrapper rxBleAdapterWrapper;

    /* access modifiers changed from: package-private */
    public abstract SCAN_CALLBACK_TYPE createScanCallback(Emitter<SCAN_RESULT_TYPE> emitter);

    /* access modifiers changed from: package-private */
    public abstract boolean startScan(RxBleAdapterWrapper rxBleAdapterWrapper2, SCAN_CALLBACK_TYPE scan_callback_type);

    /* access modifiers changed from: package-private */
    public abstract void stopScan(RxBleAdapterWrapper rxBleAdapterWrapper2, SCAN_CALLBACK_TYPE scan_callback_type);

    ScanOperation(RxBleAdapterWrapper rxBleAdapterWrapper2) {
        this.rxBleAdapterWrapper = rxBleAdapterWrapper2;
    }

    /* access modifiers changed from: protected */
    public final void protectedRun(Emitter<SCAN_RESULT_TYPE> emitter, QueueReleaseInterface queueReleaseInterface) {
        final Object createScanCallback = createScanCallback(emitter);
        try {
            emitter.setCancellation(new Cancellable() {
                public void cancel() throws Exception {
                    RxBleLog.m1114i("Scan operation is requested to stop.", new Object[0]);
                    ScanOperation scanOperation = ScanOperation.this;
                    scanOperation.stopScan(scanOperation.rxBleAdapterWrapper, createScanCallback);
                }
            });
            RxBleLog.m1114i("Scan operation is requested to start.", new Object[0]);
            if (!startScan(this.rxBleAdapterWrapper, createScanCallback)) {
                emitter.onError(new BleScanException(0));
            }
        } catch (Throwable th) {
            queueReleaseInterface.release();
            throw th;
        }
        queueReleaseInterface.release();
    }

    /* access modifiers changed from: protected */
    public BleException provideException(DeadObjectException deadObjectException) {
        return new BleScanException(1, (Throwable) deadObjectException);
    }
}
