package com.google.android.play.core.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* renamed from: com.google.android.play.core.internal.a */
final class C1912a implements C1939b {

    /* renamed from: a */
    private final ByteBuffer f771a;

    public C1912a(ByteBuffer byteBuffer) {
        this.f771a = byteBuffer.slice();
    }

    /* renamed from: a */
    public final long mo34131a() {
        return (long) this.f771a.capacity();
    }

    /* renamed from: a */
    public final void mo34132a(MessageDigest[] messageDigestArr, long j, int i) throws IOException {
        ByteBuffer slice;
        synchronized (this.f771a) {
            int i2 = (int) j;
            this.f771a.position(i2);
            this.f771a.limit(i2 + i);
            slice = this.f771a.slice();
        }
        for (MessageDigest update : messageDigestArr) {
            slice.position(0);
            update.update(slice);
        }
    }
}
