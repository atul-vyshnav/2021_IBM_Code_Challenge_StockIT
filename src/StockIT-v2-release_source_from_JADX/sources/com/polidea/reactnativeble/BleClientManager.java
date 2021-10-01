package com.polidea.reactnativeble;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.polidea.multiplatformbleadapter.BleAdapter;
import com.polidea.multiplatformbleadapter.BleAdapterFactory;
import com.polidea.multiplatformbleadapter.Characteristic;
import com.polidea.multiplatformbleadapter.ConnectionOptions;
import com.polidea.multiplatformbleadapter.ConnectionState;
import com.polidea.multiplatformbleadapter.Descriptor;
import com.polidea.multiplatformbleadapter.Device;
import com.polidea.multiplatformbleadapter.OnErrorCallback;
import com.polidea.multiplatformbleadapter.OnEventCallback;
import com.polidea.multiplatformbleadapter.OnSuccessCallback;
import com.polidea.multiplatformbleadapter.RefreshGattMoment;
import com.polidea.multiplatformbleadapter.Service;
import com.polidea.multiplatformbleadapter.errors.BleError;
import com.polidea.reactnativeble.converter.BleErrorToJsObjectConverter;
import com.polidea.reactnativeble.converter.CharacteristicToJsObjectConverter;
import com.polidea.reactnativeble.converter.DescriptorToJsObjectConverter;
import com.polidea.reactnativeble.converter.DeviceToJsObjectConverter;
import com.polidea.reactnativeble.converter.ScanResultToJsObjectConverter;
import com.polidea.reactnativeble.converter.ServiceToJsObjectConverter;
import com.polidea.reactnativeble.utils.ReadableArrayConverter;
import com.polidea.reactnativeble.utils.SafePromise;
import com.polidea.rxandroidble.ClientComponent;
import com.polidea.rxandroidble.internal.connection.ConnectionComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BleClientManager extends ReactContextBaseJavaModule {
    private static final String NAME = "BleClientManager";
    private BleAdapter bleAdapter;
    /* access modifiers changed from: private */
    public final CharacteristicToJsObjectConverter characteristicConverter = new CharacteristicToJsObjectConverter();
    /* access modifiers changed from: private */
    public final DescriptorToJsObjectConverter descriptorConverter = new DescriptorToJsObjectConverter();
    /* access modifiers changed from: private */
    public final DeviceToJsObjectConverter deviceConverter = new DeviceToJsObjectConverter();
    /* access modifiers changed from: private */
    public final BleErrorToJsObjectConverter errorConverter = new BleErrorToJsObjectConverter();
    /* access modifiers changed from: private */
    public final ScanResultToJsObjectConverter scanResultConverter = new ScanResultToJsObjectConverter();
    private final ServiceToJsObjectConverter serviceConverter = new ServiceToJsObjectConverter();

    public String getName() {
        return NAME;
    }

    public BleClientManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        for (Event event : Event.values()) {
            hashMap.put(event.name, event.name);
        }
        return hashMap;
    }

    @ReactMethod
    public void createClient(String str) {
        BleAdapter newAdapter = BleAdapterFactory.getNewAdapter(getReactApplicationContext());
        this.bleAdapter = newAdapter;
        newAdapter.createClient(str, new OnEventCallback<String>() {
            public void onEvent(String str) {
                BleClientManager.this.sendEvent(Event.StateChangeEvent, str);
            }
        }, new OnEventCallback<Integer>() {
            public void onEvent(Integer num) {
                BleClientManager.this.sendEvent(Event.RestoreStateEvent, (Object) null);
            }
        });
    }

    @ReactMethod
    public void destroyClient() {
        this.bleAdapter.destroyClient();
        this.bleAdapter = null;
    }

    @ReactMethod
    public void cancelTransaction(String str) {
        this.bleAdapter.cancelTransaction(str);
    }

    @ReactMethod
    public void setLogLevel(String str) {
        this.bleAdapter.setLogLevel(str);
    }

    @ReactMethod
    public void logLevel(Promise promise) {
        promise.resolve(this.bleAdapter.getLogLevel());
    }

    @ReactMethod
    public void enable(String str, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.enable(str, new OnSuccessCallback<Void>() {
            public void onSuccess(Void voidR) {
                safePromise.resolve((Object) null);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void disable(String str, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.disable(str, new OnSuccessCallback<Void>() {
            public void onSuccess(Void voidR) {
                safePromise.resolve((Object) null);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void state(Promise promise) {
        promise.resolve(this.bleAdapter.getCurrentState());
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    @com.facebook.react.bridge.ReactMethod
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startDeviceScan(com.facebook.react.bridge.ReadableArray r9, com.facebook.react.bridge.ReadableMap r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            if (r10 == 0) goto L_0x0031
            java.lang.String r2 = "scanMode"
            boolean r3 = r10.hasKey(r2)
            if (r3 == 0) goto L_0x0018
            com.facebook.react.bridge.ReadableType r3 = r10.getType(r2)
            com.facebook.react.bridge.ReadableType r4 = com.facebook.react.bridge.ReadableType.Number
            if (r3 != r4) goto L_0x0018
            int r0 = r10.getInt(r2)
        L_0x0018:
            java.lang.String r2 = "callbackType"
            boolean r3 = r10.hasKey(r2)
            if (r3 == 0) goto L_0x002f
            com.facebook.react.bridge.ReadableType r3 = r10.getType(r2)
            com.facebook.react.bridge.ReadableType r4 = com.facebook.react.bridge.ReadableType.Number
            if (r3 != r4) goto L_0x002f
            int r1 = r10.getInt(r2)
            r4 = r0
            r5 = r1
            goto L_0x0033
        L_0x002f:
            r4 = r0
            goto L_0x0032
        L_0x0031:
            r4 = 0
        L_0x0032:
            r5 = 1
        L_0x0033:
            com.polidea.multiplatformbleadapter.BleAdapter r2 = r8.bleAdapter
            if (r9 == 0) goto L_0x003c
            java.lang.String[] r9 = com.polidea.reactnativeble.utils.ReadableArrayConverter.toStringArray(r9)
            goto L_0x003d
        L_0x003c:
            r9 = 0
        L_0x003d:
            r3 = r9
            com.polidea.reactnativeble.BleClientManager$7 r6 = new com.polidea.reactnativeble.BleClientManager$7
            r6.<init>()
            com.polidea.reactnativeble.BleClientManager$8 r7 = new com.polidea.reactnativeble.BleClientManager$8
            r7.<init>()
            r2.startDeviceScan(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.polidea.reactnativeble.BleClientManager.startDeviceScan(com.facebook.react.bridge.ReadableArray, com.facebook.react.bridge.ReadableMap):void");
    }

    @ReactMethod
    public void stopDeviceScan() {
        this.bleAdapter.stopDeviceScan();
    }

    @ReactMethod
    public void devices(ReadableArray readableArray, final Promise promise) {
        this.bleAdapter.getKnownDevices(ReadableArrayConverter.toStringArray(readableArray), new OnSuccessCallback<Device[]>() {
            public void onSuccess(Device[] deviceArr) {
                WritableArray createArray = Arguments.createArray();
                for (Device jSObject : deviceArr) {
                    createArray.pushMap(BleClientManager.this.deviceConverter.toJSObject(jSObject));
                }
                promise.resolve(createArray);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void connectedDevices(ReadableArray readableArray, final Promise promise) {
        this.bleAdapter.getConnectedDevices(ReadableArrayConverter.toStringArray(readableArray), new OnSuccessCallback<Device[]>() {
            public void onSuccess(Device[] deviceArr) {
                WritableArray createArray = Arguments.createArray();
                for (Device jSObject : deviceArr) {
                    createArray.pushMap(BleClientManager.this.deviceConverter.toJSObject(jSObject));
                }
                promise.resolve(createArray);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void requestConnectionPriorityForDevice(String str, int i, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.requestConnectionPriorityForDevice(str, i, str2, new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void requestMTUForDevice(String str, int i, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.requestMTUForDevice(str, i, str2, new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readRSSIForDevice(String str, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.readRSSIForDevice(str, str2, new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void connectToDevice(String str, ReadableMap readableMap, Promise promise) {
        int i;
        RefreshGattMoment refreshGattMoment;
        int i2;
        Integer num;
        ReadableMap readableMap2 = readableMap;
        final SafePromise safePromise = new SafePromise(promise);
        Long l = null;
        boolean z = false;
        if (readableMap2 != null) {
            boolean z2 = (!readableMap2.hasKey(ConnectionComponent.NamedBooleans.AUTO_CONNECT) || readableMap2.getType(ConnectionComponent.NamedBooleans.AUTO_CONNECT) != ReadableType.Boolean) ? false : readableMap2.getBoolean(ConnectionComponent.NamedBooleans.AUTO_CONNECT);
            int i3 = (!readableMap2.hasKey("requestMTU") || readableMap2.getType("requestMTU") != ReadableType.Number) ? 0 : readableMap2.getInt("requestMTU");
            RefreshGattMoment byName = (!readableMap2.hasKey("refreshGatt") || readableMap2.getType("refreshGatt") != ReadableType.String) ? null : RefreshGattMoment.getByName(readableMap2.getString("refreshGatt"));
            Integer valueOf = (!readableMap2.hasKey(ClientComponent.NamedSchedulers.TIMEOUT) || readableMap2.getType(ClientComponent.NamedSchedulers.TIMEOUT) != ReadableType.Number) ? null : Integer.valueOf(readableMap2.getInt(ClientComponent.NamedSchedulers.TIMEOUT));
            if (!readableMap2.hasKey("connectionPriority") || readableMap2.getType("connectionPriority") != ReadableType.Number) {
                z = z2;
                num = valueOf;
                i = 0;
            } else {
                i = readableMap2.getInt("connectionPriority");
                z = z2;
                num = valueOf;
            }
            refreshGattMoment = byName;
            i2 = i3;
        } else {
            num = null;
            refreshGattMoment = null;
            i2 = 0;
            i = 0;
        }
        BleAdapter bleAdapter2 = this.bleAdapter;
        Boolean valueOf2 = Boolean.valueOf(z);
        if (num != null) {
            l = Long.valueOf(num.longValue());
        }
        final String str2 = str;
        bleAdapter2.connectToDevice(str, new ConnectionOptions(valueOf2, i2, refreshGattMoment, l, i), new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnEventCallback<ConnectionState>() {
            public void onEvent(ConnectionState connectionState) {
                if (connectionState == ConnectionState.DISCONNECTED) {
                    WritableArray createArray = Arguments.createArray();
                    createArray.pushNull();
                    WritableMap createMap = Arguments.createMap();
                    createMap.putString("id", str2);
                    createArray.pushMap(createMap);
                    BleClientManager.this.sendEvent(Event.DisconnectionEvent, createArray);
                }
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void cancelDeviceConnection(String str, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.cancelDeviceConnection(str, new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void isDeviceConnected(String str, final Promise promise) {
        this.bleAdapter.isDeviceConnected(str, new OnSuccessCallback<Boolean>() {
            public void onSuccess(Boolean bool) {
                promise.resolve(bool);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void discoverAllServicesAndCharacteristicsForDevice(String str, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.discoverAllServicesAndCharacteristicsForDevice(str, str2, new OnSuccessCallback<Device>() {
            public void onSuccess(Device device) {
                safePromise.resolve(BleClientManager.this.deviceConverter.toJSObject(device));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void servicesForDevice(String str, Promise promise) {
        try {
            List<Service> servicesForDevice = this.bleAdapter.getServicesForDevice(str);
            WritableArray createArray = Arguments.createArray();
            for (Service jSObject : servicesForDevice) {
                createArray.pushMap(this.serviceConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void characteristicsForDevice(String str, String str2, Promise promise) {
        try {
            List<Characteristic> characteristicsForDevice = this.bleAdapter.getCharacteristicsForDevice(str, str2);
            WritableArray createArray = Arguments.createArray();
            for (Characteristic jSObject : characteristicsForDevice) {
                createArray.pushMap(this.characteristicConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void characteristicsForService(int i, Promise promise) {
        try {
            List<Characteristic> characteristicsForService = this.bleAdapter.getCharacteristicsForService(i);
            WritableArray createArray = Arguments.createArray();
            for (Characteristic jSObject : characteristicsForService) {
                createArray.pushMap(this.characteristicConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void descriptorsForDevice(String str, String str2, String str3, Promise promise) {
        try {
            List<Descriptor> descriptorsForDevice = this.bleAdapter.descriptorsForDevice(str, str2, str3);
            WritableArray createArray = Arguments.createArray();
            for (Descriptor jSObject : descriptorsForDevice) {
                createArray.pushMap(this.descriptorConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void descriptorsForService(int i, String str, Promise promise) {
        try {
            List<Descriptor> descriptorsForService = this.bleAdapter.descriptorsForService(i, str);
            WritableArray createArray = Arguments.createArray();
            for (Descriptor jSObject : descriptorsForService) {
                createArray.pushMap(this.descriptorConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void descriptorsForCharacteristic(int i, Promise promise) {
        try {
            List<Descriptor> descriptorsForCharacteristic = this.bleAdapter.descriptorsForCharacteristic(i);
            WritableArray createArray = Arguments.createArray();
            for (Descriptor jSObject : descriptorsForCharacteristic) {
                createArray.pushMap(this.descriptorConverter.toJSObject(jSObject));
            }
            promise.resolve(createArray);
        } catch (BleError e) {
            promise.reject((String) null, this.errorConverter.toJs(e));
        }
    }

    @ReactMethod
    public void writeCharacteristicForDevice(String str, String str2, String str3, String str4, Boolean bool, String str5, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.writeCharacteristicForDevice(str, str2, str3, str4, bool.booleanValue(), str5, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeCharacteristicForService(int i, String str, String str2, Boolean bool, String str3, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.writeCharacteristicForService(i, str, str2, bool.booleanValue(), str3, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeCharacteristic(int i, String str, Boolean bool, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.writeCharacteristic(i, str, bool.booleanValue(), str2, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readCharacteristicForDevice(String str, String str2, String str3, String str4, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.readCharacteristicForDevice(str, str2, str3, str4, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readCharacteristicForService(int i, String str, String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.readCharacteristicForService(i, str, str2, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readCharacteristic(int i, String str, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.readCharacteristic(i, str, new OnSuccessCallback<Characteristic>() {
            public void onSuccess(Characteristic characteristic) {
                safePromise.resolve(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void monitorCharacteristicForDevice(String str, String str2, String str3, final String str4, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.monitorCharacteristicForDevice(str, str2, str3, str4, new OnEventCallback<Characteristic>() {
            public void onEvent(Characteristic characteristic) {
                WritableArray createArray = Arguments.createArray();
                createArray.pushNull();
                createArray.pushMap(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
                createArray.pushString(str4);
                BleClientManager.this.sendEvent(Event.ReadEvent, createArray);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void monitorCharacteristicForService(int i, String str, final String str2, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.monitorCharacteristicForService(i, str, str2, new OnEventCallback<Characteristic>() {
            public void onEvent(Characteristic characteristic) {
                WritableArray createArray = Arguments.createArray();
                createArray.pushNull();
                createArray.pushMap(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
                createArray.pushString(str2);
                BleClientManager.this.sendEvent(Event.ReadEvent, createArray);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void monitorCharacteristic(int i, final String str, Promise promise) {
        final SafePromise safePromise = new SafePromise(promise);
        this.bleAdapter.monitorCharacteristic(i, str, new OnEventCallback<Characteristic>() {
            public void onEvent(Characteristic characteristic) {
                WritableArray createArray = Arguments.createArray();
                createArray.pushNull();
                createArray.pushMap(BleClientManager.this.characteristicConverter.toJSObject(characteristic));
                createArray.pushString(str);
                BleClientManager.this.sendEvent(Event.ReadEvent, createArray);
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                safePromise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readDescriptorForDevice(String str, String str2, String str3, String str4, String str5, final Promise promise) {
        this.bleAdapter.readDescriptorForDevice(str, str2, str3, str4, str5, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readDescriptorForService(int i, String str, String str2, String str3, final Promise promise) {
        this.bleAdapter.readDescriptorForService(i, str, str2, str3, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readDescriptorForCharacteristic(int i, String str, String str2, final Promise promise) {
        this.bleAdapter.readDescriptorForCharacteristic(i, str, str2, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void readDescriptor(int i, String str, final Promise promise) {
        this.bleAdapter.readDescriptor(i, str, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeDescriptorForDevice(String str, String str2, String str3, String str4, String str5, String str6, Promise promise) {
        final Promise promise2 = promise;
        this.bleAdapter.writeDescriptorForDevice(str, str2, str3, str4, str5, str6, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise2.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise2.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeDescriptorForService(int i, String str, String str2, String str3, String str4, final Promise promise) {
        this.bleAdapter.writeDescriptorForService(i, str, str2, str3, str4, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeDescriptorForCharacteristic(int i, String str, String str2, String str3, final Promise promise) {
        this.bleAdapter.writeDescriptorForCharacteristic(i, str, str2, str3, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    @ReactMethod
    public void writeDescriptor(int i, String str, String str2, final Promise promise) {
        this.bleAdapter.writeDescriptor(i, str, str2, new OnSuccessCallback<Descriptor>() {
            public void onSuccess(Descriptor descriptor) {
                promise.resolve(BleClientManager.this.descriptorConverter.toJSObject(descriptor));
            }
        }, new OnErrorCallback() {
            public void onError(BleError bleError) {
                promise.reject((String) null, BleClientManager.this.errorConverter.toJs(bleError));
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendEvent(Event event, Object obj) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(event.name, obj);
    }
}
