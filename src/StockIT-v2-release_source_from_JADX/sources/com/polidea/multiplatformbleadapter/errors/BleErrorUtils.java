package com.polidea.multiplatformbleadapter.errors;

public class BleErrorUtils {
    public static BleError cancelled() {
        return new BleError(BleErrorCode.OperationCancelled, (String) null, (Integer) null);
    }

    public static BleError invalidIdentifiers(String... strArr) {
        StringBuilder sb = new StringBuilder();
        for (String append : strArr) {
            sb.append(append);
            sb.append(", ");
        }
        BleError bleError = new BleError(BleErrorCode.InvalidIdentifiers, (String) null, (Integer) null);
        bleError.internalMessage = sb.toString();
        return bleError;
    }

    public static BleError deviceNotFound(String str) {
        BleError bleError = new BleError(BleErrorCode.DeviceNotFound, (String) null, (Integer) null);
        bleError.deviceID = str;
        return bleError;
    }

    public static BleError deviceNotConnected(String str) {
        BleError bleError = new BleError(BleErrorCode.DeviceNotConnected, (String) null, (Integer) null);
        bleError.deviceID = str;
        return bleError;
    }

    public static BleError characteristicNotFound(String str) {
        BleError bleError = new BleError(BleErrorCode.CharacteristicNotFound, (String) null, (Integer) null);
        bleError.characteristicUUID = str;
        return bleError;
    }

    public static BleError invalidWriteDataForCharacteristic(String str, String str2) {
        BleError bleError = new BleError(BleErrorCode.CharacteristicInvalidDataFormat, (String) null, (Integer) null);
        bleError.characteristicUUID = str2;
        bleError.internalMessage = str;
        return bleError;
    }

    public static BleError descriptorNotFound(String str) {
        BleError bleError = new BleError(BleErrorCode.DescriptorNotFound, (String) null, (Integer) null);
        bleError.descriptorUUID = str;
        return bleError;
    }

    public static BleError invalidWriteDataForDescriptor(String str, String str2) {
        BleError bleError = new BleError(BleErrorCode.DescriptorInvalidDataFormat, (String) null, (Integer) null);
        bleError.descriptorUUID = str2;
        bleError.internalMessage = str;
        return bleError;
    }

    public static BleError descriptorWriteNotAllowed(String str) {
        BleError bleError = new BleError(BleErrorCode.DescriptorWriteNotAllowed, (String) null, (Integer) null);
        bleError.descriptorUUID = str;
        return bleError;
    }

    public static BleError serviceNotFound(String str) {
        BleError bleError = new BleError(BleErrorCode.ServiceNotFound, (String) null, (Integer) null);
        bleError.serviceUUID = str;
        return bleError;
    }

    public static BleError cannotMonitorCharacteristic(String str, String str2, String str3, String str4) {
        BleError bleError = new BleError(BleErrorCode.CharacteristicNotifyChangeFailed, str, (Integer) null);
        bleError.deviceID = str2;
        bleError.serviceUUID = str3;
        bleError.characteristicUUID = str4;
        return bleError;
    }

    public static BleError deviceServicesNotDiscovered(String str) {
        BleError bleError = new BleError(BleErrorCode.ServicesNotDiscovered, (String) null, (Integer) null);
        bleError.deviceID = str;
        return bleError;
    }
}
