package com.polidea.rxandroidble.internal;

import bleshadow.dagger.Subcomponent;
import com.polidea.rxandroidble.RxBleDevice;

@DeviceScope
@Subcomponent(modules = {DeviceModule.class, DeviceModuleBinder.class})
public interface DeviceComponent {

    public interface Builder {
        DeviceComponent build();

        Builder deviceModule(DeviceModule deviceModule);
    }

    @DeviceScope
    RxBleDevice provideDevice();
}
