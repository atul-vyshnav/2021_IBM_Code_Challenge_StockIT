package com.polidea.reactnativeble.converter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.polidea.multiplatformbleadapter.errors.BleError;
import com.polidea.reactnativeble.converter.DescriptorToJsObjectConverter;

public class BleErrorToJsObjectConverter {
    public ReadableArray toJSCallback(BleError bleError) {
        WritableArray createArray = Arguments.createArray();
        createArray.pushString(toJs(bleError));
        createArray.pushNull();
        return createArray;
    }

    public String toJs(BleError bleError) {
        Arguments.createArray();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"errorCode\":");
        sb.append(bleError.errorCode.code);
        sb.append(",\"attErrorCode\":");
        if (bleError.androidCode == null || bleError.androidCode.intValue() >= 128 || bleError.androidCode.intValue() < 0) {
            sb.append("null");
        } else {
            sb.append(bleError.androidCode.intValue());
        }
        sb.append(",\"iosErrorCode\": null");
        sb.append(",\"androidErrorCode\":");
        if (bleError.androidCode == null || bleError.androidCode.intValue() < 128) {
            sb.append("null");
        } else {
            sb.append(bleError.androidCode.intValue());
        }
        appendString(sb, "reason", bleError.reason);
        appendString(sb, "deviceID", bleError.deviceID);
        appendString(sb, "serviceUUID", bleError.serviceUUID);
        appendString(sb, DescriptorToJsObjectConverter.Metadata.CHARACTERISTIC_UUID, bleError.characteristicUUID);
        appendString(sb, "descriptorUUID", bleError.descriptorUUID);
        appendString(sb, "internalMessage", bleError.internalMessage);
        sb.append("}");
        return sb.toString();
    }

    private void appendString(StringBuilder sb, String str, String str2) {
        sb.append(",\"");
        sb.append(str);
        sb.append("\":");
        if (str2 == null) {
            sb.append("null");
            return;
        }
        sb.append("\"");
        sb.append(str2);
        sb.append("\"");
    }
}
