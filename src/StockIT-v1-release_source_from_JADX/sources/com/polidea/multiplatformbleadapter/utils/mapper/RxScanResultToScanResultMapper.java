package com.polidea.multiplatformbleadapter.utils.mapper;

import com.polidea.multiplatformbleadapter.AdvertisementData;
import com.polidea.multiplatformbleadapter.ScanResult;
import java.util.UUID;

public class RxScanResultToScanResultMapper {
    public ScanResult map(com.polidea.rxandroidble.scan.ScanResult scanResult) {
        return new ScanResult(scanResult.getBleDevice().getMacAddress(), scanResult.getBleDevice().getName(), scanResult.getRssi(), 23, false, (UUID[]) null, AdvertisementData.parseScanResponseData(scanResult.getScanRecord().getBytes()));
    }
}
