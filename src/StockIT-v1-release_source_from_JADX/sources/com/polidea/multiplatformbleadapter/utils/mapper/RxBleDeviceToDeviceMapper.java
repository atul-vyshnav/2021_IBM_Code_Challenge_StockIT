package com.polidea.multiplatformbleadapter.utils.mapper;

import com.polidea.multiplatformbleadapter.Device;
import com.polidea.rxandroidble.RxBleDevice;

public class RxBleDeviceToDeviceMapper {
    public Device map(RxBleDevice rxBleDevice) {
        return new Device(rxBleDevice.getMacAddress(), rxBleDevice.getName());
    }
}
