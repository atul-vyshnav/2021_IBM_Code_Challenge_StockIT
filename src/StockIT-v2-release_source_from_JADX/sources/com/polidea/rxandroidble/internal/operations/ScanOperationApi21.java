package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.scan.AndroidScanObjectsConverter;
import com.polidea.rxandroidble.internal.scan.EmulatedScanFilterMatcher;
import com.polidea.rxandroidble.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble.scan.ScanFilter;
import com.polidea.rxandroidble.scan.ScanSettings;
import java.util.List;
import p030rx.Emitter;

public class ScanOperationApi21 extends ScanOperation<RxBleInternalScanResult, ScanCallback> {
    private final AndroidScanObjectsConverter androidScanObjectsConverter;
    /* access modifiers changed from: private */
    public final EmulatedScanFilterMatcher emulatedScanFilterMatcher;
    /* access modifiers changed from: private */
    public final InternalScanResultCreator internalScanResultCreator;
    private final ScanFilter[] scanFilters;
    private final ScanSettings scanSettings;

    public ScanOperationApi21(RxBleAdapterWrapper rxBleAdapterWrapper, InternalScanResultCreator internalScanResultCreator2, AndroidScanObjectsConverter androidScanObjectsConverter2, ScanSettings scanSettings2, EmulatedScanFilterMatcher emulatedScanFilterMatcher2, ScanFilter[] scanFilterArr) {
        super(rxBleAdapterWrapper);
        this.internalScanResultCreator = internalScanResultCreator2;
        this.scanSettings = scanSettings2;
        this.emulatedScanFilterMatcher = emulatedScanFilterMatcher2;
        this.scanFilters = scanFilterArr;
        this.androidScanObjectsConverter = androidScanObjectsConverter2;
    }

    /* access modifiers changed from: package-private */
    public ScanCallback createScanCallback(final Emitter<RxBleInternalScanResult> emitter) {
        return new ScanCallback() {
            public void onScanResult(int i, ScanResult scanResult) {
                RxBleInternalScanResult create = ScanOperationApi21.this.internalScanResultCreator.create(i, scanResult);
                if (ScanOperationApi21.this.emulatedScanFilterMatcher.matches(create)) {
                    emitter.onNext(create);
                }
            }

            public void onBatchScanResults(List<ScanResult> list) {
                for (ScanResult create : list) {
                    RxBleInternalScanResult create2 = ScanOperationApi21.this.internalScanResultCreator.create(create);
                    if (ScanOperationApi21.this.emulatedScanFilterMatcher.matches(create2)) {
                        emitter.onNext(create2);
                    }
                }
            }

            public void onScanFailed(int i) {
                emitter.onError(new BleScanException(ScanOperationApi21.errorCodeToBleErrorCode(i)));
            }
        };
    }

    /* access modifiers changed from: package-private */
    public boolean startScan(RxBleAdapterWrapper rxBleAdapterWrapper, ScanCallback scanCallback) {
        rxBleAdapterWrapper.startLeScan(this.androidScanObjectsConverter.toNativeFilters(this.scanFilters), this.androidScanObjectsConverter.toNativeSettings(this.scanSettings), scanCallback);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void stopScan(RxBleAdapterWrapper rxBleAdapterWrapper, ScanCallback scanCallback) {
        rxBleAdapterWrapper.stopLeScan(scanCallback);
    }

    /* access modifiers changed from: private */
    public static int errorCodeToBleErrorCode(int i) {
        if (i == 1) {
            return 5;
        }
        if (i == 2) {
            return 6;
        }
        if (i == 3) {
            return 7;
        }
        if (i == 4) {
            return 8;
        }
        if (i == 5) {
            return 9;
        }
        RxBleLog.m1118w("Encountered unknown scanning error code: %d -> check android.bluetooth.le.ScanCallback", new Object[0]);
        return Integer.MAX_VALUE;
    }
}
