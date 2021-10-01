package com.polidea.multiplatformbleadapter.utils;

import java.util.UUID;

public class IdGeneratorKey {
    private final String deviceAddress;

    /* renamed from: id */
    private final int f1178id;
    private final UUID uuid;

    public IdGeneratorKey(String str, UUID uuid2, int i) {
        this.deviceAddress = str;
        this.uuid = uuid2;
        this.f1178id = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IdGeneratorKey idGeneratorKey = (IdGeneratorKey) obj;
        if (this.f1178id == idGeneratorKey.f1178id && this.deviceAddress.equals(idGeneratorKey.deviceAddress)) {
            return this.uuid.equals(idGeneratorKey.uuid);
        }
        return false;
    }

    public int hashCode() {
        return (((this.deviceAddress.hashCode() * 31) + this.uuid.hashCode()) * 31) + this.f1178id;
    }
}
