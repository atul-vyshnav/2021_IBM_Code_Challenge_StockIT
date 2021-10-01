package com.polidea.rxandroidble.internal.scan;

import android.os.ParcelUuid;
import android.util.SparseArray;
import com.polidea.rxandroidble.scan.ScanRecord;
import java.util.List;
import java.util.Map;

public class ScanRecordImplNativeWrapper implements ScanRecord {
    private final android.bluetooth.le.ScanRecord nativeScanRecord;

    public ScanRecordImplNativeWrapper(android.bluetooth.le.ScanRecord scanRecord) {
        this.nativeScanRecord = scanRecord;
    }

    public int getAdvertiseFlags() {
        return this.nativeScanRecord.getAdvertiseFlags();
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.nativeScanRecord.getServiceUuids();
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.nativeScanRecord.getManufacturerSpecificData();
    }

    public byte[] getManufacturerSpecificData(int i) {
        return this.nativeScanRecord.getManufacturerSpecificData(i);
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.nativeScanRecord.getServiceData();
    }

    public byte[] getServiceData(ParcelUuid parcelUuid) {
        return this.nativeScanRecord.getServiceData(parcelUuid);
    }

    public int getTxPowerLevel() {
        return this.nativeScanRecord.getTxPowerLevel();
    }

    public String getDeviceName() {
        return this.nativeScanRecord.getDeviceName();
    }

    public byte[] getBytes() {
        return this.nativeScanRecord.getBytes();
    }
}
