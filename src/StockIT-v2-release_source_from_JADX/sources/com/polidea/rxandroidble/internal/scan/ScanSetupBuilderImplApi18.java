package com.polidea.rxandroidble.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.operations.ScanOperationApi18;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble.scan.ScanFilter;
import com.polidea.rxandroidble.scan.ScanSettings;
import p030rx.Observable;

public class ScanSetupBuilderImplApi18 implements ScanSetupBuilder {
    private final InternalScanResultCreator internalScanResultCreator;
    private final RxBleAdapterWrapper rxBleAdapterWrapper;
    private final ScanSettingsEmulator scanSettingsEmulator;

    @Inject
    ScanSetupBuilderImplApi18(RxBleAdapterWrapper rxBleAdapterWrapper2, InternalScanResultCreator internalScanResultCreator2, ScanSettingsEmulator scanSettingsEmulator2) {
        this.rxBleAdapterWrapper = rxBleAdapterWrapper2;
        this.internalScanResultCreator = internalScanResultCreator2;
        this.scanSettingsEmulator = scanSettingsEmulator2;
    }

    public ScanSetup build(ScanSettings scanSettings, ScanFilter... scanFilterArr) {
        final Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateScanMode = this.scanSettingsEmulator.emulateScanMode(scanSettings.getScanMode());
        final Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateCallbackType = this.scanSettingsEmulator.emulateCallbackType(scanSettings.getCallbackType());
        return new ScanSetup(new ScanOperationApi18(this.rxBleAdapterWrapper, this.internalScanResultCreator, new EmulatedScanFilterMatcher(scanFilterArr)), new Observable.Transformer<RxBleInternalScanResult, RxBleInternalScanResult>() {
            public Observable<RxBleInternalScanResult> call(Observable<RxBleInternalScanResult> observable) {
                return observable.compose(emulateScanMode).compose(emulateCallbackType);
            }
        });
    }
}
