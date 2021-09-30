package com.polidea.rxandroidble.internal.scan;

import com.polidea.rxandroidble.scan.ScanFilter;

public class EmulatedScanFilterMatcher {
    private final ScanFilter[] scanFilters;

    public EmulatedScanFilterMatcher(ScanFilter... scanFilterArr) {
        this.scanFilters = scanFilterArr;
    }

    public boolean matches(RxBleInternalScanResult rxBleInternalScanResult) {
        ScanFilter[] scanFilterArr = this.scanFilters;
        if (scanFilterArr == null || scanFilterArr.length == 0) {
            return true;
        }
        for (ScanFilter matches : scanFilterArr) {
            if (matches.matches(rxBleInternalScanResult)) {
                return true;
            }
        }
        return false;
    }
}
