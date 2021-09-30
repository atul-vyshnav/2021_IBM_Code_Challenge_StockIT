package com.polidea.rxandroidble.internal;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ClientScope;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.internal.DeviceComponent;
import com.polidea.rxandroidble.internal.cache.DeviceComponentCache;
import java.util.Map;

@ClientScope
public class RxBleDeviceProvider {
    private final Map<String, DeviceComponent> cachedDeviceComponents;
    private final Provider<DeviceComponent.Builder> deviceComponentBuilder;

    @Inject
    public RxBleDeviceProvider(DeviceComponentCache deviceComponentCache, Provider<DeviceComponent.Builder> provider) {
        this.cachedDeviceComponents = deviceComponentCache;
        this.deviceComponentBuilder = provider;
    }

    public RxBleDevice getBleDevice(String str) {
        DeviceComponent deviceComponent = this.cachedDeviceComponents.get(str);
        if (deviceComponent != null) {
            return deviceComponent.provideDevice();
        }
        synchronized (this.cachedDeviceComponents) {
            DeviceComponent deviceComponent2 = this.cachedDeviceComponents.get(str);
            if (deviceComponent2 != null) {
                RxBleDevice provideDevice = deviceComponent2.provideDevice();
                return provideDevice;
            }
            DeviceComponent build = this.deviceComponentBuilder.get().deviceModule(new DeviceModule(str)).build();
            RxBleDevice provideDevice2 = build.provideDevice();
            this.cachedDeviceComponents.put(str, build);
            return provideDevice2;
        }
    }
}
