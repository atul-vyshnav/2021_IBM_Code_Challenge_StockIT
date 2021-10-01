package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.internal.util.CharacteristicPropertiesParser;
import java.util.Locale;

public class IllegalOperationMessageCreator {
    private CharacteristicPropertiesParser propertiesParser;

    @Inject
    public IllegalOperationMessageCreator(CharacteristicPropertiesParser characteristicPropertiesParser) {
        this.propertiesParser = characteristicPropertiesParser;
    }

    public String createMismatchMessage(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        return String.format(Locale.getDefault(), "Characteristic %s supports properties: %s (%d) does not have any property matching %s (%d)", new Object[]{bluetoothGattCharacteristic.getUuid(), this.propertiesParser.propertiesIntToString(bluetoothGattCharacteristic.getProperties()), Integer.valueOf(bluetoothGattCharacteristic.getProperties()), this.propertiesParser.propertiesIntToString(i), Integer.valueOf(i)});
    }
}
