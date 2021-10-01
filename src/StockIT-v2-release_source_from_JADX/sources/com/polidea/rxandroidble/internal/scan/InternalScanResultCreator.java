package com.polidea.rxandroidble.internal.scan;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.ClientScope;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.util.UUIDUtil;
import com.polidea.rxandroidble.scan.ScanCallbackType;

@ClientScope
public class InternalScanResultCreator {
    private final UUIDUtil uuidUtil;

    @Inject
    public InternalScanResultCreator(UUIDUtil uUIDUtil) {
        this.uuidUtil = uUIDUtil;
    }

    public RxBleInternalScanResult create(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        return new RxBleInternalScanResult(bluetoothDevice, i, System.nanoTime(), this.uuidUtil.parseFromBytes(bArr), ScanCallbackType.CALLBACK_TYPE_UNSPECIFIED);
    }

    public RxBleInternalScanResult create(ScanResult scanResult) {
        return new RxBleInternalScanResult(scanResult.getDevice(), scanResult.getRssi(), scanResult.getTimestampNanos(), new ScanRecordImplNativeWrapper(scanResult.getScanRecord()), ScanCallbackType.CALLBACK_TYPE_BATCH);
    }

    public RxBleInternalScanResult create(int i, ScanResult scanResult) {
        return new RxBleInternalScanResult(scanResult.getDevice(), scanResult.getRssi(), scanResult.getTimestampNanos(), new ScanRecordImplNativeWrapper(scanResult.getScanRecord()), toScanCallbackType(i));
    }

    private static ScanCallbackType toScanCallbackType(int i) {
        if (i == 1) {
            return ScanCallbackType.CALLBACK_TYPE_ALL_MATCHES;
        }
        if (i == 2) {
            return ScanCallbackType.CALLBACK_TYPE_FIRST_MATCH;
        }
        if (i == 4) {
            return ScanCallbackType.CALLBACK_TYPE_MATCH_LOST;
        }
        RxBleLog.m1118w("Unknown callback type %d -> check android.bluetooth.le.ScanSettings", new Object[0]);
        return ScanCallbackType.CALLBACK_TYPE_UNKNOWN;
    }
}
