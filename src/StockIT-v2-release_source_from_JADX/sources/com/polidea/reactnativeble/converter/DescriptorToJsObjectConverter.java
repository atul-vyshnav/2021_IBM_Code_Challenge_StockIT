package com.polidea.reactnativeble.converter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.polidea.multiplatformbleadapter.Descriptor;
import com.polidea.multiplatformbleadapter.utils.Base64Converter;
import com.polidea.multiplatformbleadapter.utils.UUIDConverter;

public class DescriptorToJsObjectConverter extends JSObjectConverter<Descriptor> {

    private interface Metadata {
        public static final String CHARACTERISTIC_ID = "characteristicID";
        public static final String CHARACTERISTIC_UUID = "characteristicUUID";
        public static final String DEVICE_ID = "deviceID";

        /* renamed from: ID */
        public static final String f1181ID = "id";
        public static final String SERVICE_ID = "serviceID";
        public static final String SERVICE_UUID = "serviceUUID";
        public static final String UUID = "uuid";
        public static final String VALUE = "value";
    }

    public WritableMap toJSObject(Descriptor descriptor) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", descriptor.getId());
        createMap.putString("uuid", UUIDConverter.fromUUID(descriptor.getUuid()));
        createMap.putInt(Metadata.CHARACTERISTIC_ID, descriptor.getCharacteristicId());
        createMap.putString(Metadata.CHARACTERISTIC_UUID, UUIDConverter.fromUUID(descriptor.getCharacteristicUuid()));
        createMap.putInt("serviceID", descriptor.getServiceId());
        createMap.putString("serviceUUID", UUIDConverter.fromUUID(descriptor.getServiceUuid()));
        createMap.putString("deviceID", descriptor.getDeviceId());
        if (descriptor.getValue() == null) {
            descriptor.setValueFromCache();
        }
        createMap.putString("value", descriptor.getValue() != null ? Base64Converter.encode(descriptor.getValue()) : null);
        return createMap;
    }
}
