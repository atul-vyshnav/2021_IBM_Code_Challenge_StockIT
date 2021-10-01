package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Service {
    private final BluetoothGattService btGattService;
    private final String deviceID;

    /* renamed from: id */
    private final int f1177id;

    public Service(int i, String str, BluetoothGattService bluetoothGattService) {
        this.f1177id = i;
        this.deviceID = str;
        this.btGattService = bluetoothGattService;
    }

    public int getId() {
        return this.f1177id;
    }

    public UUID getUuid() {
        return this.btGattService.getUuid();
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    public boolean isPrimary() {
        return this.btGattService.getType() == 0;
    }

    public Characteristic getCharacteristicByUUID(UUID uuid) {
        BluetoothGattCharacteristic characteristic = this.btGattService.getCharacteristic(uuid);
        if (characteristic == null) {
            return null;
        }
        return new Characteristic(this, characteristic);
    }

    public List<Characteristic> getCharacteristics() {
        ArrayList arrayList = new ArrayList(this.btGattService.getCharacteristics().size());
        for (BluetoothGattCharacteristic characteristic : this.btGattService.getCharacteristics()) {
            arrayList.add(new Characteristic(this, characteristic));
        }
        return arrayList;
    }
}
