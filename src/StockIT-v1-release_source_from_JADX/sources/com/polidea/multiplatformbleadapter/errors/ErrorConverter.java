package com.polidea.multiplatformbleadapter.errors;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.multiplatformbleadapter.Characteristic;
import com.polidea.multiplatformbleadapter.exceptions.CannotMonitorCharacteristicException;
import com.polidea.multiplatformbleadapter.utils.UUIDConverter;
import com.polidea.rxandroidble.exceptions.BleAlreadyConnectedException;
import com.polidea.rxandroidble.exceptions.BleCannotSetCharacteristicNotificationException;
import com.polidea.rxandroidble.exceptions.BleCharacteristicNotFoundException;
import com.polidea.rxandroidble.exceptions.BleConflictingNotificationAlreadySetException;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble.exceptions.BleGattDescriptorException;
import com.polidea.rxandroidble.exceptions.BleGattException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.exceptions.BleScanException;
import com.polidea.rxandroidble.exceptions.BleServiceNotFoundException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ErrorConverter {
    public BleError toError(Throwable th) {
        if (th instanceof CannotMonitorCharacteristicException) {
            Characteristic characteristic = ((CannotMonitorCharacteristicException) th).getCharacteristic();
            return BleErrorUtils.cannotMonitorCharacteristic(th.getMessage(), (String) null, UUIDConverter.fromUUID(characteristic.getServiceUUID()), UUIDConverter.fromUUID(characteristic.getUuid()));
        } else if (th instanceof TimeoutException) {
            return new BleError(BleErrorCode.OperationTimedOut, th.getMessage(), (Integer) null);
        } else {
            if (th instanceof BleAlreadyConnectedException) {
                return new BleError(BleErrorCode.DeviceAlreadyConnected, th.getMessage(), (Integer) null);
            }
            if (th instanceof BleCannotSetCharacteristicNotificationException) {
                BluetoothGattCharacteristic bluetoothGattCharacteristic = ((BleCannotSetCharacteristicNotificationException) th).getBluetoothGattCharacteristic();
                return BleErrorUtils.cannotMonitorCharacteristic(th.getMessage(), (String) null, UUIDConverter.fromUUID(bluetoothGattCharacteristic.getService().getUuid()), UUIDConverter.fromUUID(bluetoothGattCharacteristic.getUuid()));
            } else if (th instanceof BleCharacteristicNotFoundException) {
                UUID charactersisticUUID = ((BleCharacteristicNotFoundException) th).getCharactersisticUUID();
                BleError bleError = new BleError(BleErrorCode.CharacteristicNotFound, th.getMessage(), (Integer) null);
                bleError.characteristicUUID = UUIDConverter.fromUUID(charactersisticUUID);
                return bleError;
            } else if (th instanceof BleConflictingNotificationAlreadySetException) {
                return BleErrorUtils.cannotMonitorCharacteristic(th.getMessage(), (String) null, (String) null, UUIDConverter.fromUUID(((BleConflictingNotificationAlreadySetException) th).getCharacteristicUuid()));
            } else if (th instanceof BleDisconnectedException) {
                BleDisconnectedException bleDisconnectedException = (BleDisconnectedException) th;
                BleError bleError2 = new BleError(BleErrorCode.DeviceDisconnected, th.getMessage(), Integer.valueOf(bleDisconnectedException.state));
                bleError2.deviceID = bleDisconnectedException.bluetoothDeviceAddress;
                return bleError2;
            } else if (th instanceof BleScanException) {
                return toError((BleScanException) th);
            } else {
                if (th instanceof BleServiceNotFoundException) {
                    BleError bleError3 = new BleError(BleErrorCode.ServiceNotFound, th.getMessage(), (Integer) null);
                    bleError3.serviceUUID = UUIDConverter.fromUUID(((BleServiceNotFoundException) th).getServiceUUID());
                    return bleError3;
                } else if (th instanceof BleGattCallbackTimeoutException) {
                    return new BleError(BleErrorCode.OperationTimedOut, th.getMessage(), Integer.valueOf(((BleGattCallbackTimeoutException) th).getStatus()));
                } else {
                    if (th instanceof BleGattCannotStartException) {
                        return new BleError(BleErrorCode.OperationStartFailed, th.getMessage(), Integer.valueOf(((BleGattCannotStartException) th).getStatus()));
                    }
                    if (th instanceof BleGattCharacteristicException) {
                        BleGattCharacteristicException bleGattCharacteristicException = (BleGattCharacteristicException) th;
                        return toError(bleGattCharacteristicException.getStatus(), th.getMessage(), bleGattCharacteristicException.getBleGattOperationType(), bleGattCharacteristicException.getMacAddress(), UUIDConverter.fromUUID(bleGattCharacteristicException.characteristic.getService().getUuid()), UUIDConverter.fromUUID(bleGattCharacteristicException.characteristic.getUuid()), (String) null);
                    } else if (th instanceof BleGattDescriptorException) {
                        BleGattDescriptorException bleGattDescriptorException = (BleGattDescriptorException) th;
                        return toError(bleGattDescriptorException.getStatus(), th.getMessage(), bleGattDescriptorException.getBleGattOperationType(), bleGattDescriptorException.getMacAddress(), UUIDConverter.fromUUID(bleGattDescriptorException.descriptor.getCharacteristic().getService().getUuid()), UUIDConverter.fromUUID(bleGattDescriptorException.descriptor.getCharacteristic().getUuid()), UUIDConverter.fromUUID(bleGattDescriptorException.descriptor.getUuid()));
                    } else if (!(th instanceof BleGattException)) {
                        return new BleError(BleErrorCode.UnknownError, th.toString(), (Integer) null);
                    } else {
                        BleGattException bleGattException = (BleGattException) th;
                        return toError(bleGattException.getStatus(), th.getMessage(), bleGattException.getBleGattOperationType(), bleGattException.getMacAddress(), (String) null, (String) null, (String) null);
                    }
                }
            }
        }
    }

    private BleError toError(int i, String str, BleGattOperationType bleGattOperationType, String str2, String str3, String str4, String str5) {
        if (BleGattOperationType.CONNECTION_STATE == bleGattOperationType) {
            BleError bleError = new BleError(BleErrorCode.DeviceDisconnected, str, Integer.valueOf(i));
            bleError.deviceID = str2;
            return bleError;
        } else if (BleGattOperationType.SERVICE_DISCOVERY == bleGattOperationType) {
            BleError bleError2 = new BleError(BleErrorCode.ServicesDiscoveryFailed, str, Integer.valueOf(i));
            bleError2.deviceID = str2;
            return bleError2;
        } else if (BleGattOperationType.CHARACTERISTIC_READ == bleGattOperationType || BleGattOperationType.CHARACTERISTIC_CHANGED == bleGattOperationType) {
            BleError bleError3 = new BleError(BleErrorCode.CharacteristicReadFailed, str, Integer.valueOf(i));
            bleError3.deviceID = str2;
            bleError3.serviceUUID = str3;
            bleError3.characteristicUUID = str4;
            return bleError3;
        } else if (BleGattOperationType.CHARACTERISTIC_WRITE == bleGattOperationType || BleGattOperationType.CHARACTERISTIC_LONG_WRITE == bleGattOperationType || BleGattOperationType.RELIABLE_WRITE_COMPLETED == bleGattOperationType) {
            BleError bleError4 = new BleError(BleErrorCode.CharacteristicWriteFailed, str, Integer.valueOf(i));
            bleError4.deviceID = str2;
            bleError4.serviceUUID = str3;
            bleError4.characteristicUUID = str4;
            return bleError4;
        } else if (BleGattOperationType.DESCRIPTOR_READ == bleGattOperationType) {
            BleError bleError5 = new BleError(BleErrorCode.DescriptorReadFailed, str, Integer.valueOf(i));
            bleError5.deviceID = str2;
            bleError5.serviceUUID = str3;
            bleError5.characteristicUUID = str4;
            bleError5.descriptorUUID = str5;
            return bleError5;
        } else if (BleGattOperationType.DESCRIPTOR_WRITE == bleGattOperationType) {
            BleError bleError6 = new BleError(BleErrorCode.DescriptorWriteFailed, str, Integer.valueOf(i));
            bleError6.deviceID = str2;
            bleError6.serviceUUID = str3;
            bleError6.characteristicUUID = str4;
            bleError6.descriptorUUID = str5;
            return bleError6;
        } else if (BleGattOperationType.READ_RSSI == bleGattOperationType) {
            BleError bleError7 = new BleError(BleErrorCode.DeviceRSSIReadFailed, str, Integer.valueOf(i));
            bleError7.deviceID = str2;
            return bleError7;
        } else if (BleGattOperationType.ON_MTU_CHANGED == bleGattOperationType) {
            BleError bleError8 = new BleError(BleErrorCode.DeviceMTUChangeFailed, str, Integer.valueOf(i));
            bleError8.deviceID = str2;
            return bleError8;
        } else {
            BleGattOperationType bleGattOperationType2 = BleGattOperationType.CONNECTION_PRIORITY_CHANGE;
            return new BleError(BleErrorCode.UnknownError, str, Integer.valueOf(i));
        }
    }

    private BleError toError(BleScanException bleScanException) {
        int reason = bleScanException.getReason();
        if (reason == 0) {
            return new BleError(BleErrorCode.ScanStartFailed, bleScanException.getMessage(), (Integer) null);
        }
        if (reason == 1) {
            return new BleError(BleErrorCode.BluetoothPoweredOff, bleScanException.getMessage(), (Integer) null);
        }
        if (reason == 2) {
            return new BleError(BleErrorCode.BluetoothUnsupported, bleScanException.getMessage(), (Integer) null);
        }
        if (reason == 3) {
            return new BleError(BleErrorCode.BluetoothUnauthorized, bleScanException.getMessage(), (Integer) null);
        }
        if (reason != 4) {
            return new BleError(BleErrorCode.ScanStartFailed, bleScanException.getMessage(), (Integer) null);
        }
        return new BleError(BleErrorCode.LocationServicesDisabled, bleScanException.getMessage(), (Integer) null);
    }
}
