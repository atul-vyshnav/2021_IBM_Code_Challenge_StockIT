package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.SparseArray;
import com.polidea.multiplatformbleadapter.errors.BleError;
import com.polidea.multiplatformbleadapter.errors.BleErrorCode;
import com.polidea.multiplatformbleadapter.errors.BleErrorUtils;
import com.polidea.multiplatformbleadapter.errors.ErrorConverter;
import com.polidea.multiplatformbleadapter.exceptions.CannotMonitorCharacteristicException;
import com.polidea.multiplatformbleadapter.utils.Base64Converter;
import com.polidea.multiplatformbleadapter.utils.Constants;
import com.polidea.multiplatformbleadapter.utils.DisposableMap;
import com.polidea.multiplatformbleadapter.utils.IdGenerator;
import com.polidea.multiplatformbleadapter.utils.LogLevel;
import com.polidea.multiplatformbleadapter.utils.RefreshGattCustomOperation;
import com.polidea.multiplatformbleadapter.utils.SafeExecutor;
import com.polidea.multiplatformbleadapter.utils.ServiceFactory;
import com.polidea.multiplatformbleadapter.utils.UUIDConverter;
import com.polidea.multiplatformbleadapter.utils.mapper.RxBleDeviceToDeviceMapper;
import com.polidea.multiplatformbleadapter.utils.mapper.RxScanResultToScanResultMapper;
import com.polidea.rxandroidble.NotificationSetupMode;
import com.polidea.rxandroidble.RxBleAdapterStateObservable;
import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.scan.ScanFilter;
import com.polidea.rxandroidble.scan.ScanResult;
import com.polidea.rxandroidble.scan.ScanSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.Subscription;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.functions.Func0;
import p030rx.functions.Func1;
import p030rx.schedulers.Schedulers;

public class BleModule implements BleAdapter {
    /* access modifiers changed from: private */
    public HashMap<String, RxBleConnection> activeConnections = new HashMap<>();
    private Subscription adapterStateChangesSubscription;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    /* access modifiers changed from: private */
    public HashMap<String, Device> connectedDevices = new HashMap<>();
    private final DisposableMap connectingDevices = new DisposableMap();
    private Context context;
    private int currentLogLevel = Integer.MAX_VALUE;
    /* access modifiers changed from: private */
    public SparseArray<Characteristic> discoveredCharacteristics = new SparseArray<>();
    /* access modifiers changed from: private */
    public SparseArray<Descriptor> discoveredDescriptors = new SparseArray<>();
    /* access modifiers changed from: private */
    public HashMap<String, Device> discoveredDevices = new HashMap<>();
    /* access modifiers changed from: private */
    public SparseArray<Service> discoveredServices = new SparseArray<>();
    /* access modifiers changed from: private */
    public final ErrorConverter errorConverter = new ErrorConverter();
    /* access modifiers changed from: private */
    public final DisposableMap pendingTransactions = new DisposableMap();
    private RxBleClient rxBleClient;
    /* access modifiers changed from: private */
    public RxBleDeviceToDeviceMapper rxBleDeviceToDeviceMapper = new RxBleDeviceToDeviceMapper();
    /* access modifiers changed from: private */
    public RxScanResultToScanResultMapper rxScanResultToScanResultMapper = new RxScanResultToScanResultMapper();
    private Subscription scanSubscription;
    /* access modifiers changed from: private */
    public ServiceFactory serviceFactory = new ServiceFactory();

    private String mapNativeAdapterStateToLocalBluetoothState(int i) {
        switch (i) {
            case 10:
                return Constants.BluetoothState.POWERED_OFF;
            case 11:
            case 13:
                return Constants.BluetoothState.RESETTING;
            case 12:
                return Constants.BluetoothState.POWERED_ON;
            default:
                return Constants.BluetoothState.UNKNOWN;
        }
    }

    public BleModule(Context context2) {
        this.context = context2;
        BluetoothManager bluetoothManager2 = (BluetoothManager) context2.getSystemService("bluetooth");
        this.bluetoothManager = bluetoothManager2;
        this.bluetoothAdapter = bluetoothManager2.getAdapter();
    }

    public void createClient(String str, OnEventCallback<String> onEventCallback, OnEventCallback<Integer> onEventCallback2) {
        this.rxBleClient = RxBleClient.create(this.context);
        this.adapterStateChangesSubscription = monitorAdapterStateChanges(this.context, onEventCallback);
        if (str != null) {
            onEventCallback2.onEvent(null);
        }
    }

    public void destroyClient() {
        Subscription subscription = this.adapterStateChangesSubscription;
        if (subscription != null) {
            subscription.unsubscribe();
            this.adapterStateChangesSubscription = null;
        }
        Subscription subscription2 = this.scanSubscription;
        if (subscription2 != null && !subscription2.isUnsubscribed()) {
            this.scanSubscription.unsubscribe();
            this.scanSubscription = null;
        }
        this.pendingTransactions.removeAllSubscriptions();
        this.connectingDevices.removeAllSubscriptions();
        this.discoveredServices.clear();
        this.discoveredCharacteristics.clear();
        this.discoveredDescriptors.clear();
        this.connectedDevices.clear();
        this.activeConnections.clear();
        this.discoveredDevices.clear();
        this.rxBleClient = null;
        IdGenerator.clear();
    }

    public void enable(String str, OnSuccessCallback<Void> onSuccessCallback, OnErrorCallback onErrorCallback) {
        changeAdapterState(RxBleAdapterStateObservable.BleAdapterState.STATE_ON, str, onSuccessCallback, onErrorCallback);
    }

    public void disable(String str, OnSuccessCallback<Void> onSuccessCallback, OnErrorCallback onErrorCallback) {
        changeAdapterState(RxBleAdapterStateObservable.BleAdapterState.STATE_OFF, str, onSuccessCallback, onErrorCallback);
    }

    public String getCurrentState() {
        if (!supportsBluetoothLowEnergy()) {
            return Constants.BluetoothState.UNSUPPORTED;
        }
        if (this.bluetoothManager == null) {
            return Constants.BluetoothState.POWERED_OFF;
        }
        return mapNativeAdapterStateToLocalBluetoothState(this.bluetoothAdapter.getState());
    }

    public void startDeviceScan(String[] strArr, int i, int i2, OnEventCallback<ScanResult> onEventCallback, OnErrorCallback onErrorCallback) {
        UUID[] uuidArr;
        if (strArr != null) {
            uuidArr = UUIDConverter.convert(strArr);
            if (uuidArr == null) {
                onErrorCallback.onError(BleErrorUtils.invalidIdentifiers(strArr));
                return;
            }
        } else {
            uuidArr = null;
        }
        safeStartDeviceScan(uuidArr, i, i2, onEventCallback, onErrorCallback);
    }

    public void stopDeviceScan() {
        Subscription subscription = this.scanSubscription;
        if (subscription != null) {
            subscription.unsubscribe();
            this.scanSubscription = null;
        }
    }

    public void requestConnectionPriorityForDevice(String str, int i, final String str2, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            final Device deviceById = getDeviceById(str);
            RxBleConnection connectionOrEmitError = getConnectionOrEmitError(deviceById.getId(), onErrorCallback);
            if (connectionOrEmitError != null) {
                final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
                if (Build.VERSION.SDK_INT >= 21) {
                    this.pendingTransactions.replaceSubscription(str2, connectionOrEmitError.requestConnectionPriority(i, 1, TimeUnit.MILLISECONDS).doOnUnsubscribe(new Action0() {
                        public void call() {
                            safeExecutor.error(BleErrorUtils.cancelled());
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }
                    }).subscribe(new Action0() {
                        public void call() {
                            safeExecutor.success(deviceById);
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }
                    }, new Action1<Throwable>() {
                        public void call(Throwable th) {
                            safeExecutor.error(BleModule.this.errorConverter.toError(th));
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }
                    }));
                    return;
                }
                onSuccessCallback.onSuccess(deviceById);
            }
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void readRSSIForDevice(String str, final String str2, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            final Device deviceById = getDeviceById(str);
            RxBleConnection connectionOrEmitError = getConnectionOrEmitError(deviceById.getId(), onErrorCallback);
            if (connectionOrEmitError != null) {
                final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
                this.pendingTransactions.replaceSubscription(str2, connectionOrEmitError.readRssi().doOnUnsubscribe(new Action0() {
                    public void call() {
                        safeExecutor.error(BleErrorUtils.cancelled());
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }
                }).subscribe(new Observer<Integer>() {
                    public void onCompleted() {
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }

                    public void onError(Throwable th) {
                        safeExecutor.error(BleModule.this.errorConverter.toError(th));
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }

                    public void onNext(Integer num) {
                        deviceById.setRssi(num);
                        safeExecutor.success(deviceById);
                    }
                }));
            }
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void requestMTUForDevice(String str, int i, final String str2, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            final Device deviceById = getDeviceById(str);
            RxBleConnection connectionOrEmitError = getConnectionOrEmitError(deviceById.getId(), onErrorCallback);
            if (connectionOrEmitError != null) {
                final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
                if (Build.VERSION.SDK_INT >= 21) {
                    this.pendingTransactions.replaceSubscription(str2, connectionOrEmitError.requestMtu(i).doOnUnsubscribe(new Action0() {
                        public void call() {
                            safeExecutor.error(BleErrorUtils.cancelled());
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }
                    }).subscribe(new Observer<Integer>() {
                        public void onCompleted() {
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }

                        public void onError(Throwable th) {
                            safeExecutor.error(BleModule.this.errorConverter.toError(th));
                            BleModule.this.pendingTransactions.removeSubscription(str2);
                        }

                        public void onNext(Integer num) {
                            deviceById.setMtu(num);
                            safeExecutor.success(deviceById);
                        }
                    }));
                    return;
                }
                onSuccessCallback.onSuccess(deviceById);
            }
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void getKnownDevices(String[] strArr, OnSuccessCallback<Device[]> onSuccessCallback, OnErrorCallback onErrorCallback) {
        if (this.rxBleClient == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to get known devices", (Integer) null));
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (str == null) {
                onErrorCallback.onError(BleErrorUtils.invalidIdentifiers(strArr));
                return;
            }
            Device device = this.discoveredDevices.get(str);
            if (device != null) {
                arrayList.add(device);
            }
        }
        onSuccessCallback.onSuccess(arrayList.toArray(new Device[arrayList.size()]));
    }

    public void getConnectedDevices(String[] strArr, OnSuccessCallback<Device[]> onSuccessCallback, OnErrorCallback onErrorCallback) {
        if (this.rxBleClient == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to get connected devices", (Integer) null));
        } else if (strArr.length == 0) {
            onSuccessCallback.onSuccess(new Device[0]);
        } else {
            int length = strArr.length;
            UUID[] uuidArr = new UUID[length];
            int i = 0;
            while (i < strArr.length) {
                UUID convert = UUIDConverter.convert(strArr[i]);
                if (convert == null) {
                    onErrorCallback.onError(BleErrorUtils.invalidIdentifiers(strArr));
                    return;
                } else {
                    uuidArr[i] = convert;
                    i++;
                }
            }
            ArrayList arrayList = new ArrayList();
            for (Device next : this.connectedDevices.values()) {
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (next.getServiceByUUID(uuidArr[i2]) != null) {
                        arrayList.add(next);
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            onSuccessCallback.onSuccess(arrayList.toArray(new Device[arrayList.size()]));
        }
    }

    public void connectToDevice(String str, ConnectionOptions connectionOptions, OnSuccessCallback<Device> onSuccessCallback, OnEventCallback<ConnectionState> onEventCallback, OnErrorCallback onErrorCallback) {
        RxBleClient rxBleClient2 = this.rxBleClient;
        if (rxBleClient2 == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to connect to device", (Integer) null));
            return;
        }
        RxBleDevice bleDevice = rxBleClient2.getBleDevice(str);
        if (bleDevice == null) {
            onErrorCallback.onError(BleErrorUtils.deviceNotFound(str));
            return;
        }
        safeConnectToDevice(bleDevice, connectionOptions.getAutoConnect().booleanValue(), connectionOptions.getRequestMTU(), connectionOptions.getRefreshGattMoment(), connectionOptions.getTimeoutInMillis(), connectionOptions.getConnectionPriority(), onSuccessCallback, onEventCallback, onErrorCallback);
    }

    public void cancelDeviceConnection(String str, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleClient rxBleClient2 = this.rxBleClient;
        if (rxBleClient2 == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to cancel device connection", (Integer) null));
            return;
        }
        RxBleDevice bleDevice = rxBleClient2.getBleDevice(str);
        if (this.connectingDevices.removeSubscription(str) && bleDevice != null) {
            onSuccessCallback.onSuccess(this.rxBleDeviceToDeviceMapper.map(bleDevice));
        } else if (bleDevice == null) {
            onErrorCallback.onError(BleErrorUtils.deviceNotFound(str));
        } else {
            onErrorCallback.onError(BleErrorUtils.deviceNotConnected(str));
        }
    }

    public void isDeviceConnected(String str, OnSuccessCallback<Boolean> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleClient rxBleClient2 = this.rxBleClient;
        if (rxBleClient2 == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to check if device is connected", (Integer) null));
            return;
        }
        RxBleDevice bleDevice = rxBleClient2.getBleDevice(str);
        if (bleDevice == null) {
            onErrorCallback.onError(BleErrorUtils.deviceNotFound(str));
        } else {
            onSuccessCallback.onSuccess(Boolean.valueOf(bleDevice.getConnectionState().equals(RxBleConnection.RxBleConnectionState.CONNECTED)));
        }
    }

    public void discoverAllServicesAndCharacteristicsForDevice(String str, String str2, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeDiscoverAllServicesAndCharacteristicsForDevice(getDeviceById(str), str2, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public List<Service> getServicesForDevice(String str) throws BleError {
        Device deviceById = getDeviceById(str);
        List<Service> services = deviceById.getServices();
        if (services != null) {
            return services;
        }
        throw BleErrorUtils.deviceServicesNotDiscovered(deviceById.getId());
    }

    public List<Characteristic> getCharacteristicsForDevice(String str, String str2) throws BleError {
        UUID convert = UUIDConverter.convert(str2);
        if (convert != null) {
            Service serviceByUUID = getDeviceById(str).getServiceByUUID(convert);
            if (serviceByUUID != null) {
                return serviceByUUID.getCharacteristics();
            }
            throw BleErrorUtils.serviceNotFound(str2);
        }
        throw BleErrorUtils.invalidIdentifiers(str2);
    }

    public List<Characteristic> getCharacteristicsForService(int i) throws BleError {
        Service service = this.discoveredServices.get(i);
        if (service != null) {
            return service.getCharacteristics();
        }
        throw BleErrorUtils.serviceNotFound(Integer.toString(i));
    }

    public List<Descriptor> descriptorsForDevice(String str, String str2, String str3) throws BleError {
        UUID[] convert = UUIDConverter.convert(str2, str3);
        if (convert != null) {
            Service serviceByUUID = getDeviceById(str).getServiceByUUID(convert[0]);
            if (serviceByUUID != null) {
                Characteristic characteristicByUUID = serviceByUUID.getCharacteristicByUUID(convert[1]);
                if (characteristicByUUID != null) {
                    return characteristicByUUID.getDescriptors();
                }
                throw BleErrorUtils.characteristicNotFound(str3);
            }
            throw BleErrorUtils.serviceNotFound(str2);
        }
        throw BleErrorUtils.invalidIdentifiers(str2, str3);
    }

    public List<Descriptor> descriptorsForService(int i, String str) throws BleError {
        UUID convert = UUIDConverter.convert(str);
        if (convert != null) {
            Service service = this.discoveredServices.get(i);
            if (service != null) {
                Characteristic characteristicByUUID = service.getCharacteristicByUUID(convert);
                if (characteristicByUUID != null) {
                    return characteristicByUUID.getDescriptors();
                }
                throw BleErrorUtils.characteristicNotFound(str);
            }
            throw BleErrorUtils.serviceNotFound(Integer.toString(i));
        }
        throw BleErrorUtils.invalidIdentifiers(str);
    }

    public List<Descriptor> descriptorsForCharacteristic(int i) throws BleError {
        Characteristic characteristic = this.discoveredCharacteristics.get(i);
        if (characteristic != null) {
            return characteristic.getDescriptors();
        }
        throw BleErrorUtils.characteristicNotFound(Integer.toString(i));
    }

    public void readCharacteristicForDevice(String str, String str2, String str3, String str4, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(str, str2, str3, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeReadCharacteristicForDevice(characteristicOrEmitError, str4, onSuccessCallback, onErrorCallback);
        }
    }

    public void readCharacteristicForService(int i, String str, String str2, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, str, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeReadCharacteristicForDevice(characteristicOrEmitError, str2, onSuccessCallback, onErrorCallback);
        }
    }

    public void readCharacteristic(int i, String str, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeReadCharacteristicForDevice(characteristicOrEmitError, str, onSuccessCallback, onErrorCallback);
        }
    }

    public void writeCharacteristicForDevice(String str, String str2, String str3, String str4, boolean z, String str5, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(str, str2, str3, onErrorCallback);
        if (characteristicOrEmitError != null) {
            writeCharacteristicWithValue(characteristicOrEmitError, str4, Boolean.valueOf(z), str5, onSuccessCallback, onErrorCallback);
        }
    }

    public void writeCharacteristicForService(int i, String str, String str2, boolean z, String str3, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, str, onErrorCallback);
        if (characteristicOrEmitError != null) {
            writeCharacteristicWithValue(characteristicOrEmitError, str2, Boolean.valueOf(z), str3, onSuccessCallback, onErrorCallback);
        }
    }

    public void writeCharacteristic(int i, String str, boolean z, String str2, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, onErrorCallback);
        if (characteristicOrEmitError != null) {
            writeCharacteristicWithValue(characteristicOrEmitError, str, Boolean.valueOf(z), str2, onSuccessCallback, onErrorCallback);
        }
    }

    public void monitorCharacteristicForDevice(String str, String str2, String str3, String str4, OnEventCallback<Characteristic> onEventCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(str, str2, str3, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeMonitorCharacteristicForDevice(characteristicOrEmitError, str4, onEventCallback, onErrorCallback);
        }
    }

    public void monitorCharacteristicForService(int i, String str, String str2, OnEventCallback<Characteristic> onEventCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, str, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeMonitorCharacteristicForDevice(characteristicOrEmitError, str2, onEventCallback, onErrorCallback);
        }
    }

    public void monitorCharacteristic(int i, String str, OnEventCallback<Characteristic> onEventCallback, OnErrorCallback onErrorCallback) {
        Characteristic characteristicOrEmitError = getCharacteristicOrEmitError(i, onErrorCallback);
        if (characteristicOrEmitError != null) {
            safeMonitorCharacteristicForDevice(characteristicOrEmitError, str, onEventCallback, onErrorCallback);
        }
    }

    public void readDescriptorForDevice(String str, String str2, String str3, String str4, String str5, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeReadDescriptorForDevice(getDescriptor(str, str2, str3, str4), str5, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void readDescriptorForService(int i, String str, String str2, String str3, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeReadDescriptorForDevice(getDescriptor(i, str, str2), str3, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void readDescriptorForCharacteristic(int i, String str, String str2, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeReadDescriptorForDevice(getDescriptor(i, str), str2, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void readDescriptor(int i, String str, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeReadDescriptorForDevice(getDescriptor(i), str, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    private void safeReadDescriptorForDevice(final Descriptor descriptor, final String str, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleConnection connectionOrEmitError = getConnectionOrEmitError(descriptor.getDeviceId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
            this.pendingTransactions.replaceSubscription(str, connectionOrEmitError.readDescriptor(descriptor.getNativeDescriptor()).doOnUnsubscribe(new Action0() {
                public void call() {
                    safeExecutor.error(BleErrorUtils.cancelled());
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }
            }).subscribe(new Observer<byte[]>() {
                public void onCompleted() {
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onError(Throwable th) {
                    safeExecutor.error(BleModule.this.errorConverter.toError(th));
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onNext(byte[] bArr) {
                    descriptor.logValue("Read from", bArr);
                    descriptor.setValue(bArr);
                    safeExecutor.success(new Descriptor(descriptor));
                }
            }));
        }
    }

    public void writeDescriptorForDevice(String str, String str2, String str3, String str4, String str5, String str6, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeWriteDescriptorForDevice(getDescriptor(str, str2, str3, str4), str5, str6, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void writeDescriptorForService(int i, String str, String str2, String str3, String str4, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeWriteDescriptorForDevice(getDescriptor(i, str, str2), str3, str4, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void writeDescriptorForCharacteristic(int i, String str, String str2, String str3, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeWriteDescriptorForDevice(getDescriptor(i, str), str2, str3, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    public void writeDescriptor(int i, String str, String str2, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            safeWriteDescriptorForDevice(getDescriptor(i), str, str2, onSuccessCallback, onErrorCallback);
        } catch (BleError e) {
            onErrorCallback.onError(e);
        }
    }

    private void safeWriteDescriptorForDevice(final Descriptor descriptor, String str, final String str2, OnSuccessCallback<Descriptor> onSuccessCallback, OnErrorCallback onErrorCallback) {
        BluetoothGattDescriptor nativeDescriptor = descriptor.getNativeDescriptor();
        if (nativeDescriptor.getUuid().equals(Constants.CLIENT_CHARACTERISTIC_CONFIG_UUID)) {
            onErrorCallback.onError(BleErrorUtils.descriptorWriteNotAllowed(UUIDConverter.fromUUID(nativeDescriptor.getUuid())));
            return;
        }
        RxBleConnection connectionOrEmitError = getConnectionOrEmitError(descriptor.getDeviceId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            try {
                byte[] decode = Base64Converter.decode(str);
                final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
                this.pendingTransactions.replaceSubscription(str2, connectionOrEmitError.writeDescriptor(nativeDescriptor, decode).doOnUnsubscribe(new Action0() {
                    public void call() {
                        safeExecutor.error(BleErrorUtils.cancelled());
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }
                }).subscribe(new Observer<byte[]>() {
                    public void onCompleted() {
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }

                    public void onError(Throwable th) {
                        safeExecutor.error(BleModule.this.errorConverter.toError(th));
                        BleModule.this.pendingTransactions.removeSubscription(str2);
                    }

                    public void onNext(byte[] bArr) {
                        descriptor.logValue("Write to", bArr);
                        descriptor.setValue(bArr);
                        safeExecutor.success(new Descriptor(descriptor));
                    }
                }));
            } catch (Throwable unused) {
                onErrorCallback.onError(BleErrorUtils.invalidWriteDataForDescriptor(str, UUIDConverter.fromUUID(nativeDescriptor.getUuid())));
            }
        }
    }

    private Descriptor getDescriptor(String str, String str2, String str3, String str4) throws BleError {
        UUID[] convert = UUIDConverter.convert(str2, str3, str4);
        if (convert != null) {
            Device device = this.connectedDevices.get(str);
            if (device != null) {
                Service serviceByUUID = device.getServiceByUUID(convert[0]);
                if (serviceByUUID != null) {
                    Characteristic characteristicByUUID = serviceByUUID.getCharacteristicByUUID(convert[1]);
                    if (characteristicByUUID != null) {
                        Descriptor descriptorByUUID = characteristicByUUID.getDescriptorByUUID(convert[2]);
                        if (descriptorByUUID != null) {
                            return descriptorByUUID;
                        }
                        throw BleErrorUtils.descriptorNotFound(str4);
                    }
                    throw BleErrorUtils.characteristicNotFound(str3);
                }
                throw BleErrorUtils.serviceNotFound(str2);
            }
            throw BleErrorUtils.deviceNotConnected(str);
        }
        throw BleErrorUtils.invalidIdentifiers(str2, str3, str4);
    }

    private Descriptor getDescriptor(int i, String str, String str2) throws BleError {
        UUID[] convert = UUIDConverter.convert(str, str2);
        if (convert != null) {
            Service service = this.discoveredServices.get(i);
            if (service != null) {
                Characteristic characteristicByUUID = service.getCharacteristicByUUID(convert[0]);
                if (characteristicByUUID != null) {
                    Descriptor descriptorByUUID = characteristicByUUID.getDescriptorByUUID(convert[1]);
                    if (descriptorByUUID != null) {
                        return descriptorByUUID;
                    }
                    throw BleErrorUtils.descriptorNotFound(str2);
                }
                throw BleErrorUtils.characteristicNotFound(str);
            }
            throw BleErrorUtils.serviceNotFound(Integer.toString(i));
        }
        throw BleErrorUtils.invalidIdentifiers(str, str2);
    }

    private Descriptor getDescriptor(int i, String str) throws BleError {
        UUID convert = UUIDConverter.convert(str);
        if (convert != null) {
            Characteristic characteristic = this.discoveredCharacteristics.get(i);
            if (characteristic != null) {
                Descriptor descriptorByUUID = characteristic.getDescriptorByUUID(convert);
                if (descriptorByUUID != null) {
                    return descriptorByUUID;
                }
                throw BleErrorUtils.descriptorNotFound(str);
            }
            throw BleErrorUtils.characteristicNotFound(Integer.toString(i));
        }
        throw BleErrorUtils.invalidIdentifiers(str);
    }

    private Descriptor getDescriptor(int i) throws BleError {
        Descriptor descriptor = this.discoveredDescriptors.get(i);
        if (descriptor != null) {
            return descriptor;
        }
        throw BleErrorUtils.descriptorNotFound(Integer.toString(i));
    }

    public void cancelTransaction(String str) {
        this.pendingTransactions.removeSubscription(str);
    }

    public void setLogLevel(String str) {
        int logLevel = LogLevel.toLogLevel(str);
        this.currentLogLevel = logLevel;
        RxBleLog.setLogLevel(logLevel);
    }

    public String getLogLevel() {
        return LogLevel.fromLogLevel(this.currentLogLevel);
    }

    private Subscription monitorAdapterStateChanges(Context context2, final OnEventCallback<String> onEventCallback) {
        if (!supportsBluetoothLowEnergy()) {
            return null;
        }
        return new RxBleAdapterStateObservable(context2).map(new Func1<RxBleAdapterStateObservable.BleAdapterState, String>() {
            public String call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return BleModule.this.mapRxBleAdapterStateToLocalBluetoothState(bleAdapterState);
            }
        }).subscribe(new Action1<String>() {
            public void call(String str) {
                onEventCallback.onEvent(str);
            }
        });
    }

    private boolean supportsBluetoothLowEnergy() {
        return this.context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    /* access modifiers changed from: private */
    public String mapRxBleAdapterStateToLocalBluetoothState(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
        if (bleAdapterState == RxBleAdapterStateObservable.BleAdapterState.STATE_ON) {
            return Constants.BluetoothState.POWERED_ON;
        }
        return bleAdapterState == RxBleAdapterStateObservable.BleAdapterState.STATE_OFF ? Constants.BluetoothState.POWERED_OFF : Constants.BluetoothState.RESETTING;
    }

    private void changeAdapterState(final RxBleAdapterStateObservable.BleAdapterState bleAdapterState, final String str, OnSuccessCallback<Void> onSuccessCallback, OnErrorCallback onErrorCallback) {
        boolean z;
        if (this.bluetoothManager == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothStateChangeFailed, "BluetoothManager is null", (Integer) null));
            return;
        }
        final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
        Subscription subscribe = new RxBleAdapterStateObservable(this.context).takeUntil(new Func1<RxBleAdapterStateObservable.BleAdapterState, Boolean>() {
            public Boolean call(RxBleAdapterStateObservable.BleAdapterState bleAdapterState) {
                return Boolean.valueOf(bleAdapterState == bleAdapterState);
            }
        }).toCompletable().doOnUnsubscribe(new Action0() {
            public void call() {
                safeExecutor.error(BleErrorUtils.cancelled());
                BleModule.this.pendingTransactions.removeSubscription(str);
            }
        }).subscribe(new Action0() {
            public void call() {
                safeExecutor.success(null);
                BleModule.this.pendingTransactions.removeSubscription(str);
            }
        }, new Action1<Throwable>() {
            public void call(Throwable th) {
                safeExecutor.error(BleModule.this.errorConverter.toError(th));
                BleModule.this.pendingTransactions.removeSubscription(str);
            }
        });
        if (bleAdapterState == RxBleAdapterStateObservable.BleAdapterState.STATE_ON) {
            z = this.bluetoothAdapter.enable();
        } else {
            z = this.bluetoothAdapter.disable();
        }
        if (!z) {
            subscribe.unsubscribe();
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothStateChangeFailed, String.format("Couldn't set bluetooth adapter state to %s", new Object[]{bleAdapterState.toString()}), (Integer) null));
            return;
        }
        this.pendingTransactions.replaceSubscription(str, subscribe);
    }

    private void safeStartDeviceScan(UUID[] uuidArr, int i, int i2, final OnEventCallback<ScanResult> onEventCallback, final OnErrorCallback onErrorCallback) {
        int i3;
        if (this.rxBleClient == null) {
            onErrorCallback.onError(new BleError(BleErrorCode.BluetoothManagerDestroyed, "BleManager not created when tried to start device scan", (Integer) null));
            return;
        }
        ScanSettings build = new ScanSettings.Builder().setScanMode(i).setCallbackType(i2).build();
        if (uuidArr == null) {
            i3 = 0;
        } else {
            i3 = uuidArr.length;
        }
        ScanFilter[] scanFilterArr = new ScanFilter[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            scanFilterArr[i4] = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(uuidArr[i4].toString())).build();
        }
        this.scanSubscription = this.rxBleClient.scanBleDevices(build, scanFilterArr).subscribe(new Action1<ScanResult>() {
            public void call(ScanResult scanResult) {
                String macAddress = scanResult.getBleDevice().getMacAddress();
                if (!BleModule.this.discoveredDevices.containsKey(macAddress)) {
                    BleModule.this.discoveredDevices.put(macAddress, BleModule.this.rxBleDeviceToDeviceMapper.map(scanResult.getBleDevice()));
                }
                onEventCallback.onEvent(BleModule.this.rxScanResultToScanResultMapper.map(scanResult));
            }
        }, (Action1<Throwable>) new Action1<Throwable>() {
            public void call(Throwable th) {
                onErrorCallback.onError(BleModule.this.errorConverter.toError(th));
            }
        });
    }

    private Device getDeviceById(String str) throws BleError {
        Device device = this.connectedDevices.get(str);
        if (device != null) {
            return device;
        }
        throw BleErrorUtils.deviceNotConnected(str);
    }

    private RxBleConnection getConnectionOrEmitError(String str, OnErrorCallback onErrorCallback) {
        RxBleConnection rxBleConnection = this.activeConnections.get(str);
        if (rxBleConnection != null) {
            return rxBleConnection;
        }
        onErrorCallback.onError(BleErrorUtils.deviceNotConnected(str));
        return null;
    }

    private void safeConnectToDevice(final RxBleDevice rxBleDevice, boolean z, final int i, RefreshGattMoment refreshGattMoment, final Long l, final int i2, OnSuccessCallback<Device> onSuccessCallback, final OnEventCallback<ConnectionState> onEventCallback, OnErrorCallback onErrorCallback) {
        final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
        Observable doOnUnsubscribe = rxBleDevice.establishConnection(z).doOnSubscribe(new Action0() {
            public void call() {
                onEventCallback.onEvent(ConnectionState.CONNECTING);
            }
        }).doOnUnsubscribe(new Action0() {
            public void call() {
                safeExecutor.error(BleErrorUtils.cancelled());
                BleModule.this.onDeviceDisconnected(rxBleDevice);
                onEventCallback.onEvent(ConnectionState.DISCONNECTED);
            }
        });
        if (refreshGattMoment == RefreshGattMoment.ON_CONNECTED) {
            doOnUnsubscribe = doOnUnsubscribe.flatMap(new Func1<RxBleConnection, Observable<RxBleConnection>>() {
                public Observable<RxBleConnection> call(final RxBleConnection rxBleConnection) {
                    return rxBleConnection.queue(new RefreshGattCustomOperation()).map(new Func1<Boolean, RxBleConnection>() {
                        public RxBleConnection call(Boolean bool) {
                            return rxBleConnection;
                        }
                    });
                }
            });
        }
        if (i2 > 0 && Build.VERSION.SDK_INT >= 21) {
            doOnUnsubscribe = doOnUnsubscribe.flatMap(new Func1<RxBleConnection, Observable<RxBleConnection>>() {
                public Observable<RxBleConnection> call(RxBleConnection rxBleConnection) {
                    return rxBleConnection.requestConnectionPriority(i2, 1, TimeUnit.MILLISECONDS).andThen(Observable.just(rxBleConnection));
                }
            });
        }
        if (i > 0 && Build.VERSION.SDK_INT >= 21) {
            doOnUnsubscribe = doOnUnsubscribe.flatMap(new Func1<RxBleConnection, Observable<RxBleConnection>>() {
                public Observable<RxBleConnection> call(final RxBleConnection rxBleConnection) {
                    return rxBleConnection.requestMtu(i).map(new Func1<Integer, RxBleConnection>() {
                        public RxBleConnection call(Integer num) {
                            return rxBleConnection;
                        }
                    });
                }
            });
        }
        if (l != null) {
            doOnUnsubscribe = doOnUnsubscribe.timeout(new Func0<Observable<Long>>() {
                public Observable<Long> call() {
                    return Observable.timer(l.longValue(), TimeUnit.MILLISECONDS);
                }
            }, new Func1<RxBleConnection, Observable<Long>>() {
                public Observable<Long> call(RxBleConnection rxBleConnection) {
                    return Observable.never();
                }
            });
        }
        this.connectingDevices.replaceSubscription(rxBleDevice.getMacAddress(), doOnUnsubscribe.subscribe(new Observer<RxBleConnection>() {
            public void onCompleted() {
            }

            public void onError(Throwable th) {
                safeExecutor.error(BleModule.this.errorConverter.toError(th));
                BleModule.this.onDeviceDisconnected(rxBleDevice);
            }

            public void onNext(RxBleConnection rxBleConnection) {
                Device map = BleModule.this.rxBleDeviceToDeviceMapper.map(rxBleDevice);
                onEventCallback.onEvent(ConnectionState.CONNECTED);
                BleModule.this.cleanServicesAndCharacteristicsForDevice(map);
                BleModule.this.connectedDevices.put(rxBleDevice.getMacAddress(), map);
                BleModule.this.activeConnections.put(rxBleDevice.getMacAddress(), rxBleConnection);
                safeExecutor.success(map);
            }
        }));
    }

    /* access modifiers changed from: private */
    public void onDeviceDisconnected(RxBleDevice rxBleDevice) {
        this.activeConnections.remove(rxBleDevice.getMacAddress());
        Device remove = this.connectedDevices.remove(rxBleDevice.getMacAddress());
        if (remove != null) {
            cleanServicesAndCharacteristicsForDevice(remove);
            this.connectingDevices.removeSubscription(remove.getId());
        }
    }

    private void safeDiscoverAllServicesAndCharacteristicsForDevice(final Device device, final String str, OnSuccessCallback<Device> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleConnection connectionOrEmitError = getConnectionOrEmitError(device.getId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
            this.pendingTransactions.replaceSubscription(str, connectionOrEmitError.discoverServices().doOnUnsubscribe(new Action0() {
                public void call() {
                    safeExecutor.error(BleErrorUtils.cancelled());
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }
            }).subscribe(new Observer<RxBleDeviceServices>() {
                public void onCompleted() {
                    safeExecutor.success(device);
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onError(Throwable th) {
                    safeExecutor.error(BleModule.this.errorConverter.toError(th));
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onNext(RxBleDeviceServices rxBleDeviceServices) {
                    ArrayList arrayList = new ArrayList();
                    for (BluetoothGattService next : rxBleDeviceServices.getBluetoothGattServices()) {
                        Service create = BleModule.this.serviceFactory.create(device.getId(), next);
                        BleModule.this.discoveredServices.put(create.getId(), create);
                        arrayList.add(create);
                        for (BluetoothGattCharacteristic next2 : next.getCharacteristics()) {
                            Characteristic characteristic = new Characteristic(create, next2);
                            BleModule.this.discoveredCharacteristics.put(characteristic.getId(), characteristic);
                            for (BluetoothGattDescriptor descriptor : next2.getDescriptors()) {
                                Descriptor descriptor2 = new Descriptor(characteristic, descriptor);
                                BleModule.this.discoveredDescriptors.put(descriptor2.getId(), descriptor2);
                            }
                        }
                    }
                    device.setServices(arrayList);
                }
            }));
        }
    }

    private void safeReadCharacteristicForDevice(final Characteristic characteristic, final String str, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleConnection connectionOrEmitError = getConnectionOrEmitError(characteristic.getDeviceId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
            this.pendingTransactions.replaceSubscription(str, connectionOrEmitError.readCharacteristic(characteristic.gattCharacteristic).doOnUnsubscribe(new Action0() {
                public void call() {
                    safeExecutor.error(BleErrorUtils.cancelled());
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }
            }).subscribe(new Observer<byte[]>() {
                public void onCompleted() {
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onError(Throwable th) {
                    safeExecutor.error(BleModule.this.errorConverter.toError(th));
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onNext(byte[] bArr) {
                    characteristic.logValue("Read from", bArr);
                    characteristic.setValue(bArr);
                    safeExecutor.success(new Characteristic(characteristic));
                }
            }));
        }
    }

    private void writeCharacteristicWithValue(Characteristic characteristic, String str, Boolean bool, String str2, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        try {
            byte[] decode = Base64Converter.decode(str);
            characteristic.setWriteType(bool.booleanValue() ? 2 : 1);
            safeWriteCharacteristicForDevice(characteristic, decode, str2, onSuccessCallback, onErrorCallback);
        } catch (Throwable unused) {
            onErrorCallback.onError(BleErrorUtils.invalidWriteDataForCharacteristic(str, UUIDConverter.fromUUID(characteristic.getUuid())));
        }
    }

    private void safeWriteCharacteristicForDevice(final Characteristic characteristic, byte[] bArr, final String str, OnSuccessCallback<Characteristic> onSuccessCallback, OnErrorCallback onErrorCallback) {
        RxBleConnection connectionOrEmitError = getConnectionOrEmitError(characteristic.getDeviceId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            final SafeExecutor safeExecutor = new SafeExecutor(onSuccessCallback, onErrorCallback);
            this.pendingTransactions.replaceSubscription(str, connectionOrEmitError.writeCharacteristic(characteristic.gattCharacteristic, bArr).doOnUnsubscribe(new Action0() {
                public void call() {
                    safeExecutor.error(BleErrorUtils.cancelled());
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }
            }).subscribe(new Observer<byte[]>() {
                public void onCompleted() {
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onError(Throwable th) {
                    safeExecutor.error(BleModule.this.errorConverter.toError(th));
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }

                public void onNext(byte[] bArr) {
                    characteristic.logValue("Write to", bArr);
                    characteristic.setValue(bArr);
                    safeExecutor.success(new Characteristic(characteristic));
                }
            }));
        }
    }

    private void safeMonitorCharacteristicForDevice(final Characteristic characteristic, final String str, OnEventCallback<Characteristic> onEventCallback, OnErrorCallback onErrorCallback) {
        final RxBleConnection connectionOrEmitError = getConnectionOrEmitError(characteristic.getDeviceId(), onErrorCallback);
        if (connectionOrEmitError != null) {
            final SafeExecutor safeExecutor = new SafeExecutor((OnSuccessCallback) null, onErrorCallback);
            final String str2 = str;
            final Characteristic characteristic2 = characteristic;
            final OnEventCallback<Characteristic> onEventCallback2 = onEventCallback;
            this.pendingTransactions.replaceSubscription(str, Observable.defer(new Func0<Observable<Observable<byte[]>>>() {
                public Observable<Observable<byte[]>> call() {
                    NotificationSetupMode notificationSetupMode = characteristic.getGattDescriptor(Constants.CLIENT_CHARACTERISTIC_CONFIG_UUID) != null ? NotificationSetupMode.QUICK_SETUP : NotificationSetupMode.COMPAT;
                    if (characteristic.isNotifiable()) {
                        return connectionOrEmitError.setupNotification(characteristic.gattCharacteristic, notificationSetupMode);
                    }
                    if (characteristic.isIndicatable()) {
                        return connectionOrEmitError.setupIndication(characteristic.gattCharacteristic, notificationSetupMode);
                    }
                    return Observable.error(new CannotMonitorCharacteristicException(characteristic));
                }
            }).flatMap(new Func1<Observable<byte[]>, Observable<byte[]>>() {
                public Observable<byte[]> call(Observable<byte[]> observable) {
                    return observable;
                }
            }).onBackpressureBuffer().observeOn(Schedulers.computation()).doOnUnsubscribe(new Action0() {
                public void call() {
                    safeExecutor.error(BleErrorUtils.cancelled());
                    BleModule.this.pendingTransactions.removeSubscription(str);
                }
            }).subscribe(new Observer<byte[]>() {
                public void onCompleted() {
                    BleModule.this.pendingTransactions.removeSubscription(str2);
                }

                public void onError(Throwable th) {
                    safeExecutor.error(BleModule.this.errorConverter.toError(th));
                    BleModule.this.pendingTransactions.removeSubscription(str2);
                }

                public void onNext(byte[] bArr) {
                    characteristic2.logValue("Notification from", bArr);
                    characteristic2.setValue(bArr);
                    onEventCallback2.onEvent(new Characteristic(characteristic2));
                }
            }));
        }
    }

    private Characteristic getCharacteristicOrEmitError(String str, String str2, String str3, OnErrorCallback onErrorCallback) {
        UUID[] convert = UUIDConverter.convert(str2, str3);
        if (convert == null) {
            onErrorCallback.onError(BleErrorUtils.invalidIdentifiers(str2, str3));
            return null;
        }
        Device device = this.connectedDevices.get(str);
        if (device == null) {
            onErrorCallback.onError(BleErrorUtils.deviceNotConnected(str));
            return null;
        }
        Service serviceByUUID = device.getServiceByUUID(convert[0]);
        if (serviceByUUID == null) {
            onErrorCallback.onError(BleErrorUtils.serviceNotFound(str2));
            return null;
        }
        Characteristic characteristicByUUID = serviceByUUID.getCharacteristicByUUID(convert[1]);
        if (characteristicByUUID != null) {
            return characteristicByUUID;
        }
        onErrorCallback.onError(BleErrorUtils.characteristicNotFound(str3));
        return null;
    }

    private Characteristic getCharacteristicOrEmitError(int i, String str, OnErrorCallback onErrorCallback) {
        UUID convert = UUIDConverter.convert(str);
        if (convert == null) {
            onErrorCallback.onError(BleErrorUtils.invalidIdentifiers(str));
            return null;
        }
        Service service = this.discoveredServices.get(i);
        if (service == null) {
            onErrorCallback.onError(BleErrorUtils.serviceNotFound(Integer.toString(i)));
            return null;
        }
        Characteristic characteristicByUUID = service.getCharacteristicByUUID(convert);
        if (characteristicByUUID != null) {
            return characteristicByUUID;
        }
        onErrorCallback.onError(BleErrorUtils.characteristicNotFound(str));
        return null;
    }

    private Characteristic getCharacteristicOrEmitError(int i, OnErrorCallback onErrorCallback) {
        Characteristic characteristic = this.discoveredCharacteristics.get(i);
        if (characteristic != null) {
            return characteristic;
        }
        onErrorCallback.onError(BleErrorUtils.characteristicNotFound(Integer.toString(i)));
        return null;
    }

    /* access modifiers changed from: private */
    public void cleanServicesAndCharacteristicsForDevice(Device device) {
        for (int size = this.discoveredServices.size() - 1; size >= 0; size--) {
            int keyAt = this.discoveredServices.keyAt(size);
            if (this.discoveredServices.get(keyAt).getDeviceID().equals(device.getId())) {
                this.discoveredServices.remove(keyAt);
            }
        }
        for (int size2 = this.discoveredCharacteristics.size() - 1; size2 >= 0; size2--) {
            int keyAt2 = this.discoveredCharacteristics.keyAt(size2);
            if (this.discoveredCharacteristics.get(keyAt2).getDeviceId().equals(device.getId())) {
                this.discoveredCharacteristics.remove(keyAt2);
            }
        }
        for (int size3 = this.discoveredDescriptors.size() - 1; size3 >= 0; size3--) {
            int keyAt3 = this.discoveredDescriptors.keyAt(size3);
            if (this.discoveredDescriptors.get(keyAt3).getDeviceId().equals(device.getId())) {
                this.discoveredDescriptors.remove(keyAt3);
            }
        }
    }
}
