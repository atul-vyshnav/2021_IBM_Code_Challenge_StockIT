package com.polidea.multiplatformbleadapter.errors;

public class BleError extends Throwable {
    public Integer androidCode;
    public String characteristicUUID;
    public String descriptorUUID;
    public String deviceID;
    public BleErrorCode errorCode;
    public String internalMessage;
    public String reason;
    public String serviceUUID;

    public BleError(BleErrorCode bleErrorCode, String str, Integer num) {
        this.errorCode = bleErrorCode;
        this.reason = str;
        this.androidCode = num;
    }

    public String getMessage() {
        return "Error code: " + this.errorCode + ", android code: " + this.androidCode + ", reason" + this.reason + ", deviceId" + this.deviceID + ", serviceUuid" + this.serviceUUID + ", characteristicUuid" + this.characteristicUUID + ", descriptorUuid" + this.descriptorUUID + ", internalMessage" + this.internalMessage;
    }
}
