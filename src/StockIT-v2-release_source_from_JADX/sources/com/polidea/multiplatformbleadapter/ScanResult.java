package com.polidea.multiplatformbleadapter;

import java.util.UUID;

public class ScanResult {
    private AdvertisementData advertisementData;
    private String deviceId;
    private String deviceName;
    private boolean isConnectable;
    private int mtu;
    private UUID[] overflowServiceUUIDs;
    private int rssi;

    public ScanResult(String str, String str2, int i, int i2, boolean z, UUID[] uuidArr, AdvertisementData advertisementData2) {
        this.deviceId = str;
        this.deviceName = str2;
        this.rssi = i;
        this.mtu = i2;
        this.isConnectable = z;
        this.overflowServiceUUIDs = uuidArr;
        this.advertisementData = advertisementData2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }

    public int getMtu() {
        return this.mtu;
    }

    public void setMtu(int i) {
        this.mtu = i;
    }

    public boolean isConnectable() {
        return this.isConnectable;
    }

    public void setConnectable(boolean z) {
        this.isConnectable = z;
    }

    public UUID[] getOverflowServiceUUIDs() {
        return this.overflowServiceUUIDs;
    }

    public void setOverflowServiceUUIDs(UUID[] uuidArr) {
        this.overflowServiceUUIDs = uuidArr;
    }

    public AdvertisementData getAdvertisementData() {
        return this.advertisementData;
    }

    public void setAdvertisementData(AdvertisementData advertisementData2) {
        this.advertisementData = advertisementData2;
    }
}
