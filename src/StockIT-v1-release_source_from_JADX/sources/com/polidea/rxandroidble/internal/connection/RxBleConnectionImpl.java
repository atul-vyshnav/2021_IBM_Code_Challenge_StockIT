package com.polidea.rxandroidble.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.NotificationSetupMode;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleCustomOperation;
import com.polidea.rxandroidble.RxBleDeviceServices;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.internal.QueueOperation;
import com.polidea.rxandroidble.internal.operations.OperationsProvider;
import com.polidea.rxandroidble.internal.serialization.ConnectionOperationQueue;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import com.polidea.rxandroidble.internal.util.QueueReleasingEmitterWrapper;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import p030rx.Completable;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.functions.Func1;

@ConnectionScope
public class RxBleConnectionImpl implements RxBleConnection {
    /* access modifiers changed from: private */
    public final BluetoothGatt bluetoothGatt;
    /* access modifiers changed from: private */
    public final Scheduler callbackScheduler;
    private final DescriptorWriter descriptorWriter;
    /* access modifiers changed from: private */
    public final RxBleGattCallback gattCallback;
    private final IllegalOperationChecker illegalOperationChecker;
    private final Provider<RxBleConnection.LongWriteOperationBuilder> longWriteOperationBuilderProvider;
    private final MtuProvider mtuProvider;
    private final NotificationAndIndicationManager notificationIndicationManager;
    private final ConnectionOperationQueue operationQueue;
    private final OperationsProvider operationsProvider;
    private final ServiceDiscoveryManager serviceDiscoveryManager;

    @Inject
    public RxBleConnectionImpl(ConnectionOperationQueue connectionOperationQueue, RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt2, ServiceDiscoveryManager serviceDiscoveryManager2, NotificationAndIndicationManager notificationAndIndicationManager, MtuProvider mtuProvider2, DescriptorWriter descriptorWriter2, OperationsProvider operationsProvider2, Provider<RxBleConnection.LongWriteOperationBuilder> provider, @Named("bluetooth_interaction") Scheduler scheduler, IllegalOperationChecker illegalOperationChecker2) {
        this.operationQueue = connectionOperationQueue;
        this.gattCallback = rxBleGattCallback;
        this.bluetoothGatt = bluetoothGatt2;
        this.serviceDiscoveryManager = serviceDiscoveryManager2;
        this.notificationIndicationManager = notificationAndIndicationManager;
        this.mtuProvider = mtuProvider2;
        this.descriptorWriter = descriptorWriter2;
        this.operationsProvider = operationsProvider2;
        this.longWriteOperationBuilderProvider = provider;
        this.callbackScheduler = scheduler;
        this.illegalOperationChecker = illegalOperationChecker2;
    }

    public RxBleConnection.LongWriteOperationBuilder createNewLongWriteBuilder() {
        return this.longWriteOperationBuilderProvider.get();
    }

    public Completable requestConnectionPriority(int i, long j, TimeUnit timeUnit) {
        if (i != 2 && i != 0 && i != 1) {
            return Completable.error((Throwable) new IllegalArgumentException("Connection priority must have valid value from BluetoothGatt (received " + i + ")"));
        } else if (j <= 0) {
            return Completable.error((Throwable) new IllegalArgumentException("Delay must be bigger than 0"));
        } else {
            return this.operationQueue.queue(this.operationsProvider.provideConnectionPriorityChangeOperation(i, j, timeUnit)).toCompletable();
        }
    }

    public Observable<Integer> requestMtu(int i) {
        return this.operationQueue.queue(this.operationsProvider.provideMtuChangeOperation(i));
    }

    public int getMtu() {
        return this.mtuProvider.getMtu();
    }

    public Observable<RxBleDeviceServices> discoverServices() {
        return this.serviceDiscoveryManager.getDiscoverServicesObservable(20, TimeUnit.SECONDS);
    }

    public Observable<RxBleDeviceServices> discoverServices(long j, TimeUnit timeUnit) {
        return this.serviceDiscoveryManager.getDiscoverServicesObservable(j, timeUnit);
    }

    public Observable<BluetoothGattCharacteristic> getCharacteristic(final UUID uuid) {
        return discoverServices().flatMap(new Func1<RxBleDeviceServices, Observable<? extends BluetoothGattCharacteristic>>() {
            public Observable<? extends BluetoothGattCharacteristic> call(RxBleDeviceServices rxBleDeviceServices) {
                return rxBleDeviceServices.getCharacteristic(uuid);
            }
        });
    }

    public Observable<Observable<byte[]>> setupNotification(UUID uuid) {
        return setupNotification(uuid, NotificationSetupMode.DEFAULT);
    }

    public Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return setupNotification(bluetoothGattCharacteristic, NotificationSetupMode.DEFAULT);
    }

    public Observable<Observable<byte[]>> setupNotification(UUID uuid, final NotificationSetupMode notificationSetupMode) {
        return getCharacteristic(uuid).flatMap(new Func1<BluetoothGattCharacteristic, Observable<? extends Observable<byte[]>>>() {
            public Observable<? extends Observable<byte[]>> call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return RxBleConnectionImpl.this.setupNotification(bluetoothGattCharacteristic, notificationSetupMode);
            }
        });
    }

    public Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, NotificationSetupMode notificationSetupMode) {
        return this.illegalOperationChecker.checkAnyPropertyMatches(bluetoothGattCharacteristic, 16).andThen(this.notificationIndicationManager.setupServerInitiatedCharacteristicRead(bluetoothGattCharacteristic, notificationSetupMode, false));
    }

    public Observable<Observable<byte[]>> setupIndication(UUID uuid) {
        return setupIndication(uuid, NotificationSetupMode.DEFAULT);
    }

    public Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return setupIndication(bluetoothGattCharacteristic, NotificationSetupMode.DEFAULT);
    }

    public Observable<Observable<byte[]>> setupIndication(UUID uuid, final NotificationSetupMode notificationSetupMode) {
        return getCharacteristic(uuid).flatMap(new Func1<BluetoothGattCharacteristic, Observable<? extends Observable<byte[]>>>() {
            public Observable<? extends Observable<byte[]>> call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return RxBleConnectionImpl.this.setupIndication(bluetoothGattCharacteristic, notificationSetupMode);
            }
        });
    }

    public Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic bluetoothGattCharacteristic, NotificationSetupMode notificationSetupMode) {
        return this.illegalOperationChecker.checkAnyPropertyMatches(bluetoothGattCharacteristic, 32).andThen(this.notificationIndicationManager.setupServerInitiatedCharacteristicRead(bluetoothGattCharacteristic, notificationSetupMode, true));
    }

    public Observable<byte[]> readCharacteristic(UUID uuid) {
        return getCharacteristic(uuid).flatMap(new Func1<BluetoothGattCharacteristic, Observable<? extends byte[]>>() {
            public Observable<? extends byte[]> call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return RxBleConnectionImpl.this.readCharacteristic(bluetoothGattCharacteristic);
            }
        });
    }

    public Observable<byte[]> readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return this.illegalOperationChecker.checkAnyPropertyMatches(bluetoothGattCharacteristic, 2).andThen(this.operationQueue.queue(this.operationsProvider.provideReadCharacteristic(bluetoothGattCharacteristic)));
    }

    public Observable<byte[]> writeCharacteristic(UUID uuid, final byte[] bArr) {
        return getCharacteristic(uuid).flatMap(new Func1<BluetoothGattCharacteristic, Observable<? extends byte[]>>() {
            public Observable<? extends byte[]> call(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return RxBleConnectionImpl.this.writeCharacteristic(bluetoothGattCharacteristic, bArr);
            }
        });
    }

    @Deprecated
    public Observable<BluetoothGattCharacteristic> writeCharacteristic(final BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return writeCharacteristic(bluetoothGattCharacteristic, bluetoothGattCharacteristic.getValue()).map(new Func1<byte[], BluetoothGattCharacteristic>() {
            public BluetoothGattCharacteristic call(byte[] bArr) {
                return bluetoothGattCharacteristic;
            }
        });
    }

    public Observable<byte[]> writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
        return this.illegalOperationChecker.checkAnyPropertyMatches(bluetoothGattCharacteristic, 76).andThen(this.operationQueue.queue(this.operationsProvider.provideWriteCharacteristic(bluetoothGattCharacteristic, bArr)));
    }

    public Observable<byte[]> readDescriptor(final UUID uuid, final UUID uuid2, final UUID uuid3) {
        return discoverServices().flatMap(new Func1<RxBleDeviceServices, Observable<BluetoothGattDescriptor>>() {
            public Observable<BluetoothGattDescriptor> call(RxBleDeviceServices rxBleDeviceServices) {
                return rxBleDeviceServices.getDescriptor(uuid, uuid2, uuid3);
            }
        }).flatMap(new Func1<BluetoothGattDescriptor, Observable<byte[]>>() {
            public Observable<byte[]> call(BluetoothGattDescriptor bluetoothGattDescriptor) {
                return RxBleConnectionImpl.this.readDescriptor(bluetoothGattDescriptor);
            }
        });
    }

    public Observable<byte[]> readDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        return this.operationQueue.queue(this.operationsProvider.provideReadDescriptor(bluetoothGattDescriptor)).map(new Func1<ByteAssociation<BluetoothGattDescriptor>, byte[]>() {
            public byte[] call(ByteAssociation<BluetoothGattDescriptor> byteAssociation) {
                return byteAssociation.second;
            }
        });
    }

    public Observable<byte[]> writeDescriptor(final UUID uuid, final UUID uuid2, final UUID uuid3, final byte[] bArr) {
        return discoverServices().flatMap(new Func1<RxBleDeviceServices, Observable<BluetoothGattDescriptor>>() {
            public Observable<BluetoothGattDescriptor> call(RxBleDeviceServices rxBleDeviceServices) {
                return rxBleDeviceServices.getDescriptor(uuid, uuid2, uuid3);
            }
        }).flatMap(new Func1<BluetoothGattDescriptor, Observable<? extends byte[]>>() {
            public Observable<? extends byte[]> call(BluetoothGattDescriptor bluetoothGattDescriptor) {
                return RxBleConnectionImpl.this.writeDescriptor(bluetoothGattDescriptor, bArr);
            }
        });
    }

    public Observable<byte[]> writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor, byte[] bArr) {
        return this.descriptorWriter.writeDescriptor(bluetoothGattDescriptor, bArr);
    }

    public Observable<Integer> readRssi() {
        return this.operationQueue.queue(this.operationsProvider.provideRssiReadOperation());
    }

    public <T> Observable<T> queue(final RxBleCustomOperation<T> rxBleCustomOperation) {
        return this.operationQueue.queue(new QueueOperation<T>() {
            /* JADX INFO: finally extract failed */
            /* access modifiers changed from: protected */
            public void protectedRun(Emitter<T> emitter, QueueReleaseInterface queueReleaseInterface) throws Throwable {
                try {
                    Observable asObservable = rxBleCustomOperation.asObservable(RxBleConnectionImpl.this.bluetoothGatt, RxBleConnectionImpl.this.gattCallback, RxBleConnectionImpl.this.callbackScheduler);
                    if (asObservable != null) {
                        asObservable.doOnTerminate(clearNativeCallbackReferenceAction()).subscribe(new QueueReleasingEmitterWrapper(emitter, queueReleaseInterface));
                        return;
                    }
                    queueReleaseInterface.release();
                    throw new IllegalArgumentException("The custom operation asObservable method must return a non-null observable");
                } catch (Throwable th) {
                    queueReleaseInterface.release();
                    throw th;
                }
            }

            private Action0 clearNativeCallbackReferenceAction() {
                return new Action0() {
                    public void call() {
                        RxBleConnectionImpl.this.gattCallback.setNativeCallback((BluetoothGattCallback) null);
                    }
                };
            }

            /* access modifiers changed from: protected */
            public BleException provideException(DeadObjectException deadObjectException) {
                return new BleDisconnectedException(deadObjectException, RxBleConnectionImpl.this.bluetoothGatt.getDevice().getAddress(), -1);
            }
        });
    }
}
