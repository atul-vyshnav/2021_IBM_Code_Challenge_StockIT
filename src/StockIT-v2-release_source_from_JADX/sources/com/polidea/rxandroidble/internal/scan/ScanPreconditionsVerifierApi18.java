package com.polidea.rxandroidble.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.polidea.rxandroidble.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;

public class ScanPreconditionsVerifierApi18 implements ScanPreconditionsVerifier {
    final LocationServicesStatus locationServicesStatus;
    final RxBleAdapterWrapper rxBleAdapterWrapper;

    @Inject
    public ScanPreconditionsVerifierApi18(RxBleAdapterWrapper rxBleAdapterWrapper2, LocationServicesStatus locationServicesStatus2) {
        this.rxBleAdapterWrapper = rxBleAdapterWrapper2;
        this.locationServicesStatus = locationServicesStatus2;
    }

    public void verify() {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            throw new BleScanException(2);
        } else if (!this.rxBleAdapterWrapper.isBluetoothEnabled()) {
            throw new BleScanException(1);
        } else if (!this.locationServicesStatus.isLocationPermissionOk()) {
            throw new BleScanException(3);
        } else if (!this.locationServicesStatus.isLocationProviderOk()) {
            throw new BleScanException(4);
        }
    }
}
