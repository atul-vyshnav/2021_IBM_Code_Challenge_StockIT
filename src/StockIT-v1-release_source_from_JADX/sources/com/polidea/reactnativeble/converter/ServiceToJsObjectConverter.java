package com.polidea.reactnativeble.converter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.polidea.multiplatformbleadapter.Service;
import com.polidea.multiplatformbleadapter.utils.UUIDConverter;

public class ServiceToJsObjectConverter extends JSObjectConverter<Service> {

    private interface Metadata {
        public static final String DEVICE_ID = "deviceID";

        /* renamed from: ID */
        public static final String f1184ID = "id";
        public static final String IS_PRIMARY = "isPrimary";
        public static final String UUID = "uuid";
    }

    public WritableMap toJSObject(Service service) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", service.getId());
        createMap.putString("uuid", UUIDConverter.fromUUID(service.getUuid()));
        createMap.putString("deviceID", service.getDeviceID());
        createMap.putBoolean(Metadata.IS_PRIMARY, service.isPrimary());
        return createMap;
    }
}
