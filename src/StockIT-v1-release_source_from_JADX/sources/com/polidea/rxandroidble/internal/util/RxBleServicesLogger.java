package com.polidea.rxandroidble.internal.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.utils.StandardUUIDsParser;

public class RxBleServicesLogger {
    private final CharacteristicPropertiesParser characteristicPropertiesParser;

    @Inject
    RxBleServicesLogger(CharacteristicPropertiesParser characteristicPropertiesParser2) {
        this.characteristicPropertiesParser = characteristicPropertiesParser2;
    }

    public void log(RxBleDeviceServices rxBleDeviceServices, BluetoothDevice bluetoothDevice) {
        if (RxBleLog.isAtLeast(2)) {
            RxBleLog.m1116v("Preparing services description", new Object[0]);
            RxBleLog.m1116v(prepareServicesDescription(rxBleDeviceServices, bluetoothDevice), new Object[0]);
        }
    }

    private String prepareServicesDescription(RxBleDeviceServices rxBleDeviceServices, BluetoothDevice bluetoothDevice) {
        StringBuilder sb = new StringBuilder();
        appendDeviceHeader(bluetoothDevice, sb);
        for (BluetoothGattService appendServiceDescription : rxBleDeviceServices.getBluetoothGattServices()) {
            sb.append(10);
            appendServiceDescription(sb, appendServiceDescription);
        }
        sb.append("\n--------------- ====== Finished peripheral content ====== ---------------");
        return sb.toString();
    }

    private void appendServiceDescription(StringBuilder sb, BluetoothGattService bluetoothGattService) {
        appendServiceHeader(sb, bluetoothGattService);
        sb.append("-> Characteristics:");
        for (BluetoothGattCharacteristic next : bluetoothGattService.getCharacteristics()) {
            appendCharacteristicNameHeader(sb, next);
            appendCharacteristicProperties(sb, next);
            appendDescriptors(sb, next);
        }
    }

    private void appendDescriptors(StringBuilder sb, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (!bluetoothGattCharacteristic.getDescriptors().isEmpty()) {
            appendDescriptorsHeader(sb);
            for (BluetoothGattDescriptor appendDescriptorNameHeader : bluetoothGattCharacteristic.getDescriptors()) {
                appendDescriptorNameHeader(sb, appendDescriptorNameHeader);
            }
        }
    }

    private void appendDescriptorsHeader(StringBuilder sb) {
        sb.append(10);
        sb.append(9);
        sb.append("  ");
        sb.append("-> Descriptors: ");
    }

    private void appendCharacteristicNameHeader(StringBuilder sb, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        sb.append(10);
        sb.append(9);
        sb.append("* ");
        sb.append(createCharacteristicName(bluetoothGattCharacteristic));
        sb.append(" (");
        sb.append(bluetoothGattCharacteristic.getUuid().toString());
        sb.append(")");
    }

    private void appendDescriptorNameHeader(StringBuilder sb, BluetoothGattDescriptor bluetoothGattDescriptor) {
        sb.append(10);
        sb.append(9);
        sb.append(9);
        sb.append("* ");
        sb.append(createDescriptorName(bluetoothGattDescriptor));
        sb.append(" (");
        sb.append(bluetoothGattDescriptor.getUuid().toString());
        sb.append(")");
    }

    private String createDescriptorName(BluetoothGattDescriptor bluetoothGattDescriptor) {
        String descriptorName = StandardUUIDsParser.getDescriptorName(bluetoothGattDescriptor.getUuid());
        return descriptorName == null ? "Unknown descriptor" : descriptorName;
    }

    private void appendCharacteristicProperties(StringBuilder sb, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        sb.append(10);
        sb.append(9);
        sb.append("  ");
        sb.append("Properties: ");
        sb.append(this.characteristicPropertiesParser.propertiesIntToString(bluetoothGattCharacteristic.getProperties()));
    }

    private String createCharacteristicName(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        String characteristicName = StandardUUIDsParser.getCharacteristicName(bluetoothGattCharacteristic.getUuid());
        return characteristicName == null ? "Unknown characteristic" : characteristicName;
    }

    private void appendDeviceHeader(BluetoothDevice bluetoothDevice, StringBuilder sb) {
        sb.append("--------------- ====== Printing peripheral content ====== ---------------\n");
        sb.append("PERIPHERAL ADDRESS: ");
        sb.append(bluetoothDevice.getAddress());
        sb.append(10);
        sb.append("PERIPHERAL NAME: ");
        sb.append(bluetoothDevice.getName());
        sb.append(10);
        sb.append("-------------------------------------------------------------------------");
    }

    private void appendServiceHeader(StringBuilder sb, BluetoothGattService bluetoothGattService) {
        sb.append("\n");
        sb.append(createServiceType(bluetoothGattService));
        sb.append(" - ");
        sb.append(createServiceName(bluetoothGattService));
        sb.append(" (");
        sb.append(bluetoothGattService.getUuid().toString());
        sb.append(")\n");
        sb.append("Instance ID: ");
        sb.append(bluetoothGattService.getInstanceId());
        sb.append(10);
    }

    private String createServiceName(BluetoothGattService bluetoothGattService) {
        String serviceName = StandardUUIDsParser.getServiceName(bluetoothGattService.getUuid());
        return serviceName == null ? "Unknown service" : serviceName;
    }

    private String createServiceType(BluetoothGattService bluetoothGattService) {
        return bluetoothGattService.getType() == 0 ? "Primary Service" : "Secondary Service";
    }
}
