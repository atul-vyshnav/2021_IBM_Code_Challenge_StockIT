package com.polidea.rxandroidble;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.Lazy;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.ClientComponent;
import com.polidea.rxandroidble.RxBleAdapterStateObservable;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.polidea.rxandroidble.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble.internal.operations.LegacyScanOperation;
import com.polidea.rxandroidble.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble.internal.scan.RxBleInternalScanResultLegacy;
import com.polidea.rxandroidble.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble.internal.scan.ScanSetup;
import com.polidea.rxandroidble.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble.internal.util.ClientStateObservable;
import com.polidea.rxandroidble.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble.internal.util.UUIDUtil;
import com.polidea.rxandroidble.scan.ScanFilter;
import com.polidea.rxandroidble.scan.ScanResult;
import com.polidea.rxandroidble.scan.ScanSettings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.functions.Func0;
import p030rx.functions.Func1;

class RxBleClientImpl extends RxBleClient {
    /* access modifiers changed from: private */
    public final Scheduler bluetoothInteractionScheduler;
    private final ClientComponent.ClientComponentFinalizer clientComponentFinalizer;
    /* access modifiers changed from: private */
    public final Func1<RxBleInternalScanResult, ScanResult> internalToExternalScanResultMapFunction;
    private final Lazy<ClientStateObservable> lazyClientStateObservable;
    private final LocationServicesStatus locationServicesStatus;
    /* access modifiers changed from: private */
    public final ClientOperationQueue operationQueue;
    /* access modifiers changed from: private */
    public final Map<Set<UUID>, Observable<RxBleScanResult>> queuedScanOperations = new HashMap();
    private final Observable<RxBleAdapterStateObservable.BleAdapterState> rxBleAdapterStateObservable;
    private final RxBleAdapterWrapper rxBleAdapterWrapper;
    private final RxBleDeviceProvider rxBleDeviceProvider;
    /* access modifiers changed from: private */
    public final ScanPreconditionsVerifier scanPreconditionVerifier;
    /* access modifiers changed from: private */
    public final ScanSetupBuilder scanSetupBuilder;
    private final UUIDUtil uuidUtil;

    @Inject
    RxBleClientImpl(RxBleAdapterWrapper rxBleAdapterWrapper2, ClientOperationQueue clientOperationQueue, Observable<RxBleAdapterStateObservable.BleAdapterState> observable, UUIDUtil uUIDUtil, LocationServicesStatus locationServicesStatus2, Lazy<ClientStateObservable> lazy, RxBleDeviceProvider rxBleDeviceProvider2, ScanSetupBuilder scanSetupBuilder2, ScanPreconditionsVerifier scanPreconditionsVerifier, Func1<RxBleInternalScanResult, ScanResult> func1, @Named("bluetooth_interaction") Scheduler scheduler, ClientComponent.ClientComponentFinalizer clientComponentFinalizer2) {
        this.uuidUtil = uUIDUtil;
        this.operationQueue = clientOperationQueue;
        this.rxBleAdapterWrapper = rxBleAdapterWrapper2;
        this.rxBleAdapterStateObservable = observable;
        this.locationServicesStatus = locationServicesStatus2;
        this.lazyClientStateObservable = lazy;
        this.rxBleDeviceProvider = rxBleDeviceProvider2;
        this.scanSetupBuilder = scanSetupBuilder2;
        this.scanPreconditionVerifier = scanPreconditionsVerifier;
        this.internalToExternalScanResultMapFunction = func1;
        this.bluetoothInteractionScheduler = scheduler;
        this.clientComponentFinalizer = clientComponentFinalizer2;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        this.clientComponentFinalizer.onFinalize();
        super.finalize();
    }

    public RxBleDevice getBleDevice(String str) {
        guardBluetoothAdapterAvailable();
        return this.rxBleDeviceProvider.getBleDevice(str);
    }

    public Set<RxBleDevice> getBondedDevices() {
        guardBluetoothAdapterAvailable();
        HashSet hashSet = new HashSet();
        for (BluetoothDevice address : this.rxBleAdapterWrapper.getBondedDevices()) {
            hashSet.add(getBleDevice(address.getAddress()));
        }
        return hashSet;
    }

    public Observable<ScanResult> scanBleDevices(final ScanSettings scanSettings, final ScanFilter... scanFilterArr) {
        return Observable.defer(new Func0<Observable<ScanResult>>() {
            public Observable<ScanResult> call() {
                RxBleClientImpl.this.scanPreconditionVerifier.verify();
                ScanSetup build = RxBleClientImpl.this.scanSetupBuilder.build(scanSettings, scanFilterArr);
                return RxBleClientImpl.this.operationQueue.queue(build.scanOperation).unsubscribeOn(RxBleClientImpl.this.bluetoothInteractionScheduler).compose(build.scanOperationBehaviourEmulatorTransformer).map(RxBleClientImpl.this.internalToExternalScanResultMapFunction).mergeWith(RxBleClientImpl.this.bluetoothAdapterOffExceptionObservable());
            }
        });
    }

    public Observable<RxBleScanResult> scanBleDevices(final UUID... uuidArr) {
        return Observable.defer(new Func0<Observable<RxBleScanResult>>() {
            public Observable<RxBleScanResult> call() {
                RxBleClientImpl.this.scanPreconditionVerifier.verify();
                return RxBleClientImpl.this.initializeScan(uuidArr);
            }
        });
    }

    /* access modifiers changed from: private */
    public Observable<RxBleScanResult> initializeScan(UUID[] uuidArr) {
        Observable<RxBleScanResult> observable;
        Set<UUID> distinctSet = this.uuidUtil.toDistinctSet(uuidArr);
        synchronized (this.queuedScanOperations) {
            observable = this.queuedScanOperations.get(distinctSet);
            if (observable == null) {
                observable = createScanOperationApi18(uuidArr);
                this.queuedScanOperations.put(distinctSet, observable);
            }
        }
        return observable;
    }

    /* access modifiers changed from: private */
    public <T> Observable<T> bluetoothAdapterOffExceptionObservable() {
        return this.rxBleAdapterStateObservable.filter(new Func1<RxBleAdapterStateObservable.BleAdapterState, Boolean>() {
            public Boolean call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Boolean.valueOf(bleAdapterState != RxBleAdapterStateObservable.BleAdapterState.STATE_ON);
            }
        }).first().flatMap(new Func1<RxBleAdapterStateObservable.BleAdapterState, Observable<? extends T>>() {
            public Observable<? extends T> call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Observable.error(new BleScanException(1));
            }
        });
    }

    /* access modifiers changed from: private */
    public RxBleScanResult convertToPublicScanResult(RxBleInternalScanResultLegacy rxBleInternalScanResultLegacy) {
        return new RxBleScanResult(getBleDevice(rxBleInternalScanResultLegacy.getBluetoothDevice().getAddress()), rxBleInternalScanResultLegacy.getRssi(), rxBleInternalScanResultLegacy.getScanRecord());
    }

    private Observable<RxBleScanResult> createScanOperationApi18(UUID[] uuidArr) {
        final Set<UUID> distinctSet = this.uuidUtil.toDistinctSet(uuidArr);
        return this.operationQueue.queue(new LegacyScanOperation(uuidArr, this.rxBleAdapterWrapper, this.uuidUtil)).doOnUnsubscribe(new Action0() {
            public void call() {
                synchronized (RxBleClientImpl.this.queuedScanOperations) {
                    RxBleClientImpl.this.queuedScanOperations.remove(distinctSet);
                }
            }
        }).mergeWith(bluetoothAdapterOffExceptionObservable()).map(new Func1<RxBleInternalScanResultLegacy, RxBleScanResult>() {
            public RxBleScanResult call(RxBleInternalScanResultLegacy rxBleInternalScanResultLegacy) {
                return RxBleClientImpl.this.convertToPublicScanResult(rxBleInternalScanResultLegacy);
            }
        }).share();
    }

    private void guardBluetoothAdapterAvailable() {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            throw new UnsupportedOperationException("RxAndroidBle library needs a BluetoothAdapter to be available in the system to work. If this is a test on an emulator then you can use 'https://github.com/Polidea/RxAndroidBle/tree/master/mockrxandroidble'");
        }
    }

    public Observable<RxBleClient.State> observeStateChanges() {
        return this.lazyClientStateObservable.get();
    }

    public RxBleClient.State getState() {
        if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
            return RxBleClient.State.BLUETOOTH_NOT_AVAILABLE;
        }
        if (!this.locationServicesStatus.isLocationPermissionOk()) {
            return RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED;
        }
        if (!this.rxBleAdapterWrapper.isBluetoothEnabled()) {
            return RxBleClient.State.BLUETOOTH_NOT_ENABLED;
        }
        if (!this.locationServicesStatus.isLocationProviderOk()) {
            return RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED;
        }
        return RxBleClient.State.READY;
    }
}
