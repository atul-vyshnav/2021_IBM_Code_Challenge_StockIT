package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.multiplatformbleadapter.utils.ByteUtils;
import com.polidea.multiplatformbleadapter.utils.IdGenerator;
import com.polidea.multiplatformbleadapter.utils.IdGeneratorKey;
import com.polidea.rxandroidble.internal.RxBleLog;
import java.util.UUID;

public class Descriptor {
    private final int characteristicId;
    private final UUID characteristicUuid;
    private final BluetoothGattDescriptor descriptor;
    private final String deviceId;

    /* renamed from: id */
    private final int f1175id;
    private final int serviceId;
    private final UUID serviceUuid;
    private final UUID uuid;
    private byte[] value = null;

    public Descriptor(Characteristic characteristic, BluetoothGattDescriptor bluetoothGattDescriptor) {
        this.characteristicId = characteristic.getId();
        this.characteristicUuid = characteristic.getUuid();
        this.serviceId = characteristic.getServiceID();
        this.serviceUuid = characteristic.getServiceUUID();
        this.descriptor = bluetoothGattDescriptor;
        String deviceId2 = characteristic.getDeviceId();
        this.deviceId = deviceId2;
        this.f1175id = IdGenerator.getIdForKey(new IdGeneratorKey(deviceId2, this.descriptor.getUuid(), this.characteristicId));
        this.uuid = bluetoothGattDescriptor.getUuid();
    }

    public Descriptor(int i, int i2, UUID uuid2, UUID uuid3, String str, BluetoothGattDescriptor bluetoothGattDescriptor, int i3, UUID uuid4) {
        this.characteristicId = i;
        this.serviceId = i2;
        this.characteristicUuid = uuid2;
        this.serviceUuid = uuid3;
        this.deviceId = str;
        this.descriptor = bluetoothGattDescriptor;
        this.f1175id = i3;
        this.uuid = uuid4;
    }

    public Descriptor(Descriptor descriptor2) {
        this.characteristicUuid = descriptor2.characteristicUuid;
        this.characteristicId = descriptor2.characteristicId;
        this.serviceUuid = descriptor2.serviceUuid;
        this.serviceId = descriptor2.serviceId;
        this.deviceId = descriptor2.deviceId;
        this.descriptor = descriptor2.descriptor;
        this.f1175id = descriptor2.f1175id;
        this.uuid = descriptor2.uuid;
        byte[] bArr = descriptor2.value;
        if (bArr != null) {
            this.value = (byte[]) bArr.clone();
        }
    }

    public int getId() {
        return this.f1175id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public int getCharacteristicId() {
        return this.characteristicId;
    }

    public int getServiceId() {
        return this.serviceId;
    }

    public UUID getCharacteristicUuid() {
        return this.characteristicUuid;
    }

    public UUID getServiceUuid() {
        return this.serviceUuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] bArr) {
        this.value = bArr;
    }

    public void setValueFromCache() {
        this.value = this.descriptor.getValue();
    }

    public BluetoothGattDescriptor getNativeDescriptor() {
        return this.descriptor;
    }

    public void logValue(String str, byte[] bArr) {
        if (bArr == null) {
            bArr = this.descriptor.getValue();
        }
        String bytesToHex = bArr != null ? ByteUtils.bytesToHex(bArr) : "(null)";
        RxBleLog.m1116v(str + " Descriptor(uuid: " + this.descriptor.getUuid().toString() + ", id: " + this.f1175id + ", value: " + bytesToHex + ")", new Object[0]);
    }
}
