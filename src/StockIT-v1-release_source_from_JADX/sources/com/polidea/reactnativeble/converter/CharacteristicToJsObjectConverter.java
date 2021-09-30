package com.polidea.reactnativeble.converter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.polidea.multiplatformbleadapter.Characteristic;
import com.polidea.multiplatformbleadapter.utils.Base64Converter;
import com.polidea.multiplatformbleadapter.utils.UUIDConverter;

public class CharacteristicToJsObjectConverter extends JSObjectConverter<Characteristic> {

    private interface Metadata {
        public static final String DEVICE_ID = "deviceID";

        /* renamed from: ID */
        public static final String f1180ID = "id";
        public static final String IS_INDICATABLE = "isIndicatable";
        public static final String IS_NOTIFIABLE = "isNotifiable";
        public static final String IS_NOTIFYING = "isNotifying";
        public static final String IS_READABLE = "isReadable";
        public static final String IS_WRITABLE_WITHOUT_RESPONSE = "isWritableWithoutResponse";
        public static final String IS_WRITABLE_WITH_RESPONSE = "isWritableWithResponse";
        public static final String SERVICE_ID = "serviceID";
        public static final String SERVICE_UUID = "serviceUUID";
        public static final String UUID = "uuid";
        public static final String VALUE = "value";
    }

    public WritableMap toJSObject(Characteristic characteristic) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", characteristic.getId());
        createMap.putString("uuid", UUIDConverter.fromUUID(characteristic.getUuid()));
        createMap.putInt("serviceID", characteristic.getServiceID());
        createMap.putString("serviceUUID", UUIDConverter.fromUUID(characteristic.getServiceUUID()));
        createMap.putString("deviceID", characteristic.getDeviceId());
        createMap.putBoolean(Metadata.IS_READABLE, characteristic.isReadable());
        createMap.putBoolean(Metadata.IS_WRITABLE_WITH_RESPONSE, characteristic.isWritableWithResponse());
        createMap.putBoolean(Metadata.IS_WRITABLE_WITHOUT_RESPONSE, characteristic.isWritableWithoutResponse());
        createMap.putBoolean(Metadata.IS_NOTIFIABLE, characteristic.isNotifiable());
        createMap.putBoolean(Metadata.IS_INDICATABLE, characteristic.isIndicatable());
        createMap.putBoolean(Metadata.IS_NOTIFYING, characteristic.isNotifying());
        createMap.putString("value", characteristic.getValue() != null ? Base64Converter.encode(characteristic.getValue()) : null);
        return createMap;
    }
}
