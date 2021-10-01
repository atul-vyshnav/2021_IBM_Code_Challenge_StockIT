package com.google.android.play.core.assetpacks;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.assetpacks.bd */
final class C1808bd extends InputStream {

    /* renamed from: a */
    private final InputStream f446a;

    /* renamed from: b */
    private long f447b;

    C1808bd(InputStream inputStream, long j) {
        this.f446a = inputStream;
        this.f447b = j;
    }

    public final void close() throws IOException {
        super.close();
        this.f446a.close();
        this.f447b = 0;
    }

    public final int read() throws IOException {
        long j = this.f447b;
        if (j <= 0) {
            return -1;
        }
        this.f447b = j - 1;
        return this.f446a.read();
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.f447b;
        if (j <= 0) {
            return -1;
        }
        int read = this.f446a.read(bArr, i, (int) Math.min((long) i2, j));
        if (read != -1) {
            this.f447b -= (long) read;
        }
        return read;
    }
}
