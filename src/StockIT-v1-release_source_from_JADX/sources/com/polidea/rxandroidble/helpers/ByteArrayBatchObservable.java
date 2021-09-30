package com.polidea.rxandroidble.helpers;

import java.nio.ByteBuffer;
import p030rx.Observable;
import p030rx.Observer;
import p030rx.functions.Action2;
import p030rx.functions.Func0;
import p030rx.observables.SyncOnSubscribe;

public class ByteArrayBatchObservable extends Observable<byte[]> {
    public ByteArrayBatchObservable(byte[] bArr, int i) {
        super(createSyncOnSubscribe(copy(bArr), i));
        if (i <= 0) {
            throw new IllegalArgumentException("maxBatchSize must be >0 but found: " + i);
        }
    }

    private static SyncOnSubscribe<ByteBuffer, byte[]> createSyncOnSubscribe(final byte[] bArr, final int i) {
        return SyncOnSubscribe.createSingleState(new Func0<ByteBuffer>() {
            public ByteBuffer call() {
                return ByteBuffer.wrap(bArr);
            }
        }, new Action2<ByteBuffer, Observer<? super byte[]>>() {
            public void call(ByteBuffer byteBuffer, Observer<? super byte[]> observer) {
                int min = Math.min(byteBuffer.remaining(), i);
                if (min == 0) {
                    observer.onCompleted();
                    return;
                }
                byte[] bArr = new byte[min];
                byteBuffer.get(bArr);
                observer.onNext(bArr);
            }
        });
    }

    private static byte[] copy(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }
}
