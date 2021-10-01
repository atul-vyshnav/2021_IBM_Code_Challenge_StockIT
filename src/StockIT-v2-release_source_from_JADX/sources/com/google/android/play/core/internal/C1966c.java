package com.google.android.play.core.internal;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/* renamed from: com.google.android.play.core.internal.c */
final class C1966c implements C1939b {

    /* renamed from: a */
    private final FileChannel f811a;

    /* renamed from: b */
    private final long f812b;

    /* renamed from: c */
    private final long f813c;

    public C1966c(FileChannel fileChannel, long j, long j2) {
        this.f811a = fileChannel;
        this.f812b = j;
        this.f813c = j2;
    }

    /* renamed from: a */
    public final long mo34131a() {
        return this.f813c;
    }

    /* renamed from: a */
    public final void mo34132a(MessageDigest[] messageDigestArr, long j, int i) throws IOException {
        MappedByteBuffer map = this.f811a.map(FileChannel.MapMode.READ_ONLY, this.f812b + j, (long) i);
        map.load();
        for (MessageDigest update : messageDigestArr) {
            map.position(0);
            update.update(map);
        }
    }
}
