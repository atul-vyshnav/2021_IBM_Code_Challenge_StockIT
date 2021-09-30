package com.polidea.rxandroidble.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble.exceptions.BleException;
import com.polidea.rxandroidble.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble.exceptions.BleGattException;
import com.polidea.rxandroidble.exceptions.BleGattOperationType;
import com.polidea.rxandroidble.internal.QueueOperation;
import com.polidea.rxandroidble.internal.connection.PayloadSizeLimitProvider;
import com.polidea.rxandroidble.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble.internal.util.ByteAssociation;
import com.polidea.rxandroidble.internal.util.QueueReleasingEmitterWrapper;
import java.nio.ByteBuffer;
import java.util.UUID;
import p030rx.Emitter;
import p030rx.Observable;
import p030rx.Scheduler;
import p030rx.functions.Action0;
import p030rx.functions.Action1;
import p030rx.functions.Func1;

public class CharacteristicLongWriteOperation extends QueueOperation<byte[]> {
    private final PayloadSizeLimitProvider batchSizeProvider;
    private final BluetoothGatt bluetoothGatt;
    private final BluetoothGattCharacteristic bluetoothGattCharacteristic;
    private final Scheduler bluetoothInteractionScheduler;
    /* access modifiers changed from: private */
    public final byte[] bytesToWrite;
    private final RxBleGattCallback rxBleGattCallback;
    private byte[] tempBatchArray;
    private final TimeoutConfiguration timeoutConfiguration;
    private final RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy;
    private final RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy;

    CharacteristicLongWriteOperation(BluetoothGatt bluetoothGatt2, RxBleGattCallback rxBleGattCallback2, @Named("bluetooth_interaction") Scheduler scheduler, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration2, BluetoothGattCharacteristic bluetoothGattCharacteristic2, PayloadSizeLimitProvider payloadSizeLimitProvider, RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy2, RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy2, byte[] bArr) {
        this.bluetoothGatt = bluetoothGatt2;
        this.rxBleGattCallback = rxBleGattCallback2;
        this.bluetoothInteractionScheduler = scheduler;
        this.timeoutConfiguration = timeoutConfiguration2;
        this.bluetoothGattCharacteristic = bluetoothGattCharacteristic2;
        this.batchSizeProvider = payloadSizeLimitProvider;
        this.writeOperationAckStrategy = writeOperationAckStrategy2;
        this.writeOperationRetryStrategy = writeOperationRetryStrategy2;
        this.bytesToWrite = bArr;
    }

    /* access modifiers changed from: protected */
    public void protectedRun(Emitter<byte[]> emitter, QueueReleaseInterface queueReleaseInterface) throws Throwable {
        int payloadSizeLimit = this.batchSizeProvider.getPayloadSizeLimit();
        if (payloadSizeLimit > 0) {
            Observable error = Observable.error(new BleGattCallbackTimeoutException(this.bluetoothGatt, BleGattOperationType.CHARACTERISTIC_LONG_WRITE));
            ByteBuffer wrap = ByteBuffer.wrap(this.bytesToWrite);
            final QueueReleasingEmitterWrapper queueReleasingEmitterWrapper = new QueueReleasingEmitterWrapper(emitter, queueReleaseInterface);
            writeBatchAndObserve(payloadSizeLimit, wrap).subscribeOn(this.bluetoothInteractionScheduler).takeFirst(writeResponseForMatchingCharacteristic(this.bluetoothGattCharacteristic)).timeout(this.timeoutConfiguration.timeout, this.timeoutConfiguration.timeoutTimeUnit, error, this.timeoutConfiguration.timeoutScheduler).repeatWhen(m1120x3cbe3949(this.writeOperationAckStrategy, wrap, queueReleasingEmitterWrapper)).retryWhen(errorIsRetryableAndAccordingTo(this.writeOperationRetryStrategy, wrap, payloadSizeLimit)).toCompletable().subscribe(new Action0() {
                public void call() {
                    queueReleasingEmitterWrapper.onNext(CharacteristicLongWriteOperation.this.bytesToWrite);
                    queueReleasingEmitterWrapper.onCompleted();
                }
            }, new Action1<Throwable>() {
                public void call(Throwable th) {
                    queueReleasingEmitterWrapper.onError(th);
                }
            });
            return;
        }
        throw new IllegalArgumentException("batchSizeProvider value must be greater than zero (now: " + payloadSizeLimit + ")");
    }

    /* access modifiers changed from: protected */
    public BleException provideException(DeadObjectException deadObjectException) {
        return new BleDisconnectedException(deadObjectException, this.bluetoothGatt.getDevice().getAddress(), -1);
    }

    private Observable<ByteAssociation<UUID>> writeBatchAndObserve(final int i, final ByteBuffer byteBuffer) {
        final Observable<ByteAssociation<UUID>> onCharacteristicWrite = this.rxBleGattCallback.getOnCharacteristicWrite();
        return Observable.create(new Action1<Emitter<ByteAssociation<UUID>>>() {
            public void call(Emitter<ByteAssociation<UUID>> emitter) {
                emitter.setSubscription(onCharacteristicWrite.subscribe(emitter));
                try {
                    CharacteristicLongWriteOperation.this.writeData(CharacteristicLongWriteOperation.this.getNextBatch(byteBuffer, i));
                } catch (Throwable th) {
                    emitter.onError(th);
                }
            }
        }, Emitter.BackpressureMode.BUFFER);
    }

    /* access modifiers changed from: private */
    public byte[] getNextBatch(ByteBuffer byteBuffer, int i) {
        int min = Math.min(byteBuffer.remaining(), i);
        byte[] bArr = this.tempBatchArray;
        if (bArr == null || bArr.length != min) {
            this.tempBatchArray = new byte[min];
        }
        byteBuffer.get(this.tempBatchArray);
        return this.tempBatchArray;
    }

    /* access modifiers changed from: private */
    public void writeData(byte[] bArr) {
        this.bluetoothGattCharacteristic.setValue(bArr);
        if (!this.bluetoothGatt.writeCharacteristic(this.bluetoothGattCharacteristic)) {
            throw new BleGattCannotStartException(this.bluetoothGatt, BleGattOperationType.CHARACTERISTIC_LONG_WRITE);
        }
    }

    private static Func1<ByteAssociation<UUID>, Boolean> writeResponseForMatchingCharacteristic(final BluetoothGattCharacteristic bluetoothGattCharacteristic2) {
        return new Func1<ByteAssociation<UUID>, Boolean>() {
            public Boolean call(ByteAssociation<UUID> byteAssociation) {
                return Boolean.valueOf(((UUID) byteAssociation.first).equals(bluetoothGattCharacteristic2.getUuid()));
            }
        };
    }

    /* renamed from: bufferIsNotEmptyAndOperationHasBeenAcknowledgedAndNotUnsubscribed */
    private static Func1<Observable<? extends Void>, Observable<?>> m1120x3cbe3949(final RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy2, final ByteBuffer byteBuffer, final QueueReleasingEmitterWrapper<byte[]> queueReleasingEmitterWrapper) {
        return new Func1<Observable<? extends Void>, Observable<?>>() {
            public Observable<?> call(Observable<? extends Void> observable) {
                return ((Observable) writeOperationAckStrategy2.call(observable.takeWhile(notUnsubscribed(queueReleasingEmitterWrapper)).map(bufferIsNotEmpty(byteBuffer)))).takeWhile(bufferIsNotEmpty(byteBuffer));
            }

            private Func1<Object, Boolean> bufferIsNotEmpty(final ByteBuffer byteBuffer) {
                return new Func1<Object, Boolean>() {
                    public Boolean call(Object obj) {
                        return Boolean.valueOf(byteBuffer.hasRemaining());
                    }
                };
            }

            private Func1<Object, Boolean> notUnsubscribed(final QueueReleasingEmitterWrapper<byte[]> queueReleasingEmitterWrapper) {
                return new Func1<Object, Boolean>() {
                    public Boolean call(Object obj) {
                        return Boolean.valueOf(!queueReleasingEmitterWrapper.isWrappedEmitterUnsubscribed());
                    }
                };
            }
        };
    }

    private static Func1<Observable<? extends Throwable>, Observable<?>> errorIsRetryableAndAccordingTo(final RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy2, final ByteBuffer byteBuffer, final int i) {
        return new Func1<Observable<? extends Throwable>, Observable<?>>() {
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(toLongWriteFailureOrError()).doOnNext(repositionByteBufferForRetry()).compose(writeOperationRetryStrategy2);
            }

            private Func1<Throwable, Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>> toLongWriteFailureOrError() {
                return new Func1<Throwable, Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>>() {
                    public Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> call(Throwable th) {
                        if (!(th instanceof BleGattCharacteristicException) && !(th instanceof BleGattCannotStartException)) {
                            return Observable.error(th);
                        }
                        C26426 r0 = C26426.this;
                        return Observable.just(new RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure(r0.calculateFailedBatchIndex(byteBuffer, i), (BleGattException) th));
                    }
                };
            }

            private Action1<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> repositionByteBufferForRetry() {
                return new Action1<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>() {
                    public void call(RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure longWriteFailure) {
                        byteBuffer.position(longWriteFailure.getBatchIndex() * i);
                    }
                };
            }

            /* access modifiers changed from: private */
            public int calculateFailedBatchIndex(ByteBuffer byteBuffer, int i) {
                return ((int) Math.ceil((double) (((float) byteBuffer.position()) / ((float) i)))) - 1;
            }
        };
    }
}
