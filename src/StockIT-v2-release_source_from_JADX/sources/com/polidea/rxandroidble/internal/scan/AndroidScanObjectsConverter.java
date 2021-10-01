package com.polidea.rxandroidble.internal.scan;

import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class AndroidScanObjectsConverter {
    private final int deviceSdk;

    @Inject
    public AndroidScanObjectsConverter(@Named("device-sdk") int i) {
        this.deviceSdk = i;
    }

    public ScanSettings toNativeSettings(com.polidea.rxandroidble.scan.ScanSettings scanSettings) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (this.deviceSdk >= 23) {
            setMarshmallowSettings(scanSettings, builder);
        }
        return builder.setReportDelay(scanSettings.getReportDelayMillis()).setScanMode(scanSettings.getScanMode()).build();
    }

    private void setMarshmallowSettings(com.polidea.rxandroidble.scan.ScanSettings scanSettings, ScanSettings.Builder builder) {
        builder.setCallbackType(scanSettings.getCallbackType()).setMatchMode(scanSettings.getMatchMode()).setNumOfMatches(scanSettings.getNumOfMatches());
    }

    public List<ScanFilter> toNativeFilters(com.polidea.rxandroidble.scan.ScanFilter... scanFilterArr) {
        if (!(scanFilterArr != null && scanFilterArr.length > 0)) {
            return null;
        }
        ArrayList arrayList = new ArrayList(scanFilterArr.length);
        for (com.polidea.rxandroidble.scan.ScanFilter scanFilter : scanFilterArr) {
            arrayList.add(toNative(scanFilter));
        }
        return arrayList;
    }

    private ScanFilter toNative(com.polidea.rxandroidble.scan.ScanFilter scanFilter) {
        ScanFilter.Builder builder = new ScanFilter.Builder();
        if (scanFilter.getServiceDataUuid() != null) {
            builder.setServiceData(scanFilter.getServiceDataUuid(), scanFilter.getServiceData(), scanFilter.getServiceDataMask());
        }
        return builder.setDeviceAddress(scanFilter.getDeviceAddress()).setDeviceName(scanFilter.getDeviceName()).setManufacturerData(scanFilter.getManufacturerId(), scanFilter.getManufacturerData(), scanFilter.getManufacturerDataMask()).setServiceUuid(scanFilter.getServiceUuid(), scanFilter.getServiceUuidMask()).build();
    }
}
