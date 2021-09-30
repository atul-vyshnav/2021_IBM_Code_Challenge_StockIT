package com.google.android.play.core.assetpacks;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.bm */
final class C1817bm extends FilterInputStream {

    /* renamed from: a */
    private final C1862dd f469a = new C1862dd();

    /* renamed from: b */
    private byte[] f470b = new byte[4096];

    /* renamed from: c */
    private long f471c;

    /* renamed from: d */
    private boolean f472d = false;

    /* renamed from: e */
    private boolean f473e = false;

    C1817bm(InputStream inputStream) {
        super(inputStream);
    }

    /* renamed from: a */
    private final int m409a(byte[] bArr, int i, int i2) throws IOException {
        return Math.max(0, super.read(bArr, i, i2));
    }

    /* renamed from: a */
    private final boolean m410a(int i) throws IOException {
        int a = m409a(this.f470b, 0, i);
        if (a != i) {
            int i2 = i - a;
            if (m409a(this.f470b, a, i2) != i2) {
                this.f469a.mo34060a(this.f470b, 0, a);
                return false;
            }
        }
        this.f469a.mo34060a(this.f470b, 0, i);
        return true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C1882dx mo34008a() throws IOException {
        byte[] bArr;
        if (this.f471c > 0) {
            do {
                bArr = this.f470b;
            } while (read(bArr, 0, bArr.length) != -1);
        }
        if (this.f472d || this.f473e) {
            return new C1882dx((String) null, -1, -1, false, false, (byte[]) null);
        }
        if (!m410a(30)) {
            this.f472d = true;
            return this.f469a.mo34061a();
        }
        C1882dx a = this.f469a.mo34061a();
        if (a.mo34099g()) {
            this.f473e = true;
            return a;
        } else if (a.mo34095d() != 4294967295L) {
            int b = this.f469a.mo34062b() - 30;
            long j = (long) b;
            int length = this.f470b.length;
            if (j > ((long) length)) {
                do {
                    length += length;
                } while (((long) length) < j);
                this.f470b = Arrays.copyOf(this.f470b, length);
            }
            if (!m410a(b)) {
                this.f472d = true;
                return this.f469a.mo34061a();
            }
            C1882dx a2 = this.f469a.mo34061a();
            this.f471c = a2.mo34095d();
            return a2;
        } else {
            throw new C1826bv("Files bigger than 4GiB are not supported.");
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo34009b() {
        return this.f472d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final boolean mo34010c() {
        return this.f473e;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final long mo34011d() {
        return this.f471c;
    }

    public final int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.f471c;
        if (j <= 0 || this.f472d) {
            return -1;
        }
        int a = m409a(bArr, i, (int) Math.min(j, (long) i2));
        this.f471c -= (long) a;
        if (a != 0) {
            return a;
        }
        this.f472d = true;
        return 0;
    }
}
