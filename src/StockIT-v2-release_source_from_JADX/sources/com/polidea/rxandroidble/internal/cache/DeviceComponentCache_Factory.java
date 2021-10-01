package com.polidea.rxandroidble.internal.cache;

import bleshadow.dagger.internal.Factory;

public final class DeviceComponentCache_Factory implements Factory<DeviceComponentCache> {
    private static final DeviceComponentCache_Factory INSTANCE = new DeviceComponentCache_Factory();

    public DeviceComponentCache get() {
        return new DeviceComponentCache();
    }

    public static DeviceComponentCache_Factory create() {
        return INSTANCE;
    }
}
