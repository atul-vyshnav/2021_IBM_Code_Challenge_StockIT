package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.multiplatformbleadapter.utils.ByteUtils;
import com.polidea.multiplatformbleadapter.utils.Constants;
import com.polidea.multiplatformbleadapter.utils.IdGenerator;
import com.polidea.multiplatformbleadapter.utils.IdGeneratorKey;
import com.polidea.rxandroidble.internal.RxBleLog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Characteristic {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private final String deviceID;
    final BluetoothGattCharacteristic gattCharacteristic;

    /* renamed from: id */
    private final int f1174id;
    private final int serviceID;
    private final UUID serviceUUID;
    private byte[] value;

    public void setValue(byte[] bArr) {
        this.value = bArr;
    }

    public Characteristic(Service service, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.deviceID = service.getDeviceID();
        this.serviceUUID = service.getUuid();
        this.serviceID = service.getId();
        this.gattCharacteristic = bluetoothGattCharacteristic;
        this.f1174id = IdGenerator.getIdForKey(new IdGeneratorKey(this.deviceID, bluetoothGattCharacteristic.getUuid(), bluetoothGattCharacteristic.getInstanceId()));
    }

    public Characteristic(int i, Service service, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f1174id = i;
        this.deviceID = service.getDeviceID();
        this.serviceUUID = service.getUuid();
        this.serviceID = service.getId();
        this.gattCharacteristic = bluetoothGattCharacteristic;
    }

    public Characteristic(Characteristic characteristic) {
        this.f1174id = characteristic.f1174id;
        this.serviceID = characteristic.serviceID;
        this.serviceUUID = characteristic.serviceUUID;
        this.deviceID = characteristic.deviceID;
        byte[] bArr = characteristic.value;
        if (bArr != null) {
            this.value = (byte[]) bArr.clone();
        }
        this.gattCharacteristic = characteristic.gattCharacteristic;
    }

    public int getId() {
        return this.f1174id;
    }

    public UUID getUuid() {
        return this.gattCharacteristic.getUuid();
    }

    public int getServiceID() {
        return this.serviceID;
    }

    public UUID getServiceUUID() {
        return this.serviceUUID;
    }

    public String getDeviceId() {
        return this.deviceID;
    }

    public int getInstanceId() {
        return this.gattCharacteristic.getInstanceId();
    }

    public BluetoothGattDescriptor getGattDescriptor(UUID uuid) {
        return this.gattCharacteristic.getDescriptor(uuid);
    }

    public void setWriteType(int i) {
        this.gattCharacteristic.setWriteType(i);
    }

    public boolean isReadable() {
        return (this.gattCharacteristic.getProperties() & 2) != 0;
    }

    public boolean isWritableWithResponse() {
        return (this.gattCharacteristic.getProperties() & 8) != 0;
    }

    public boolean isWritableWithoutResponse() {
        return (this.gattCharacteristic.getProperties() & 4) != 0;
    }

    public boolean isNotifiable() {
        return (this.gattCharacteristic.getProperties() & 16) != 0;
    }

    public List<Descriptor> getDescriptors() {
        ArrayList arrayList = new ArrayList(this.gattCharacteristic.getDescriptors().size());
        for (BluetoothGattDescriptor descriptor : this.gattCharacteristic.getDescriptors()) {
            arrayList.add(new Descriptor(this, descriptor));
        }
        return arrayList;
    }

    public boolean isNotifying() {
        byte[] value2;
        BluetoothGattDescriptor descriptor = this.gattCharacteristic.getDescriptor(Constants.CLIENT_CHARACTERISTIC_CONFIG_UUID);
        if (descriptor == null || (value2 = descriptor.getValue()) == null || (value2[0] & 1) == 0) {
            return false;
        }
        return true;
    }

    public boolean isIndicatable() {
        return (this.gattCharacteristic.getProperties() & 32) != 0;
    }

    public byte[] getValue() {
        return this.value;
    }

    public Descriptor getDescriptorByUUID(UUID uuid) {
        BluetoothGattDescriptor descriptor = this.gattCharacteristic.getDescriptor(uuid);
        if (descriptor == null) {
            return null;
        }
        return new Descriptor(this, descriptor);
    }

    /* access modifiers changed from: package-private */
    public void logValue(String str, byte[] bArr) {
        if (bArr == null) {
            bArr = this.gattCharacteristic.getValue();
        }
        String bytesToHex = bArr != null ? ByteUtils.bytesToHex(bArr) : "(null)";
        RxBleLog.m1116v(str + " Characteristic(uuid: " + this.gattCharacteristic.getUuid().toString() + ", id: " + this.f1174id + ", value: " + bytesToHex + ")", new Object[0]);
    }
}
