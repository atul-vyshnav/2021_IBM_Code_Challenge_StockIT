package com.google.android.play.core.assetpacks;

import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.dd */
final class C1862dd {

    /* renamed from: a */
    private byte[] f639a = new byte[4096];

    /* renamed from: b */
    private int f640b;

    /* renamed from: c */
    private long f641c;

    /* renamed from: d */
    private long f642d;

    /* renamed from: e */
    private int f643e;

    /* renamed from: f */
    private int f644f;

    /* renamed from: g */
    private int f645g;

    /* renamed from: h */
    private boolean f646h;

    /* renamed from: i */
    private String f647i;

    public C1862dd() {
        mo34063c();
    }

    /* renamed from: a */
    private final int m504a(int i, byte[] bArr, int i2, int i3) {
        int i4 = this.f640b;
        if (i4 >= i) {
            return 0;
        }
        int min = Math.min(i3, i - i4);
        System.arraycopy(bArr, i2, this.f639a, this.f640b, min);
        int i5 = this.f640b + min;
        this.f640b = i5;
        if (i5 < i) {
            return -1;
        }
        return min;
    }

    /* renamed from: a */
    public final int mo34060a(byte[] bArr, int i, int i2) {
        int a = m504a(30, bArr, i, i2);
        if (a == -1) {
            return -1;
        }
        if (this.f641c == -1) {
            long b = C1860db.m499b(this.f639a, 0);
            this.f641c = b;
            if (b == 67324752) {
                this.f646h = false;
                this.f642d = C1860db.m499b(this.f639a, 18);
                this.f645g = C1860db.m501c(this.f639a, 8);
                this.f643e = C1860db.m501c(this.f639a, 26);
                int c = this.f643e + 30 + C1860db.m501c(this.f639a, 28);
                this.f644f = c;
                int length = this.f639a.length;
                if (length < c) {
                    do {
                        length += length;
                    } while (length < c);
                    this.f639a = Arrays.copyOf(this.f639a, length);
                }
            } else {
                this.f646h = true;
            }
        }
        int a2 = m504a(this.f644f, bArr, i + a, i2 - a);
        if (a2 == -1) {
            return -1;
        }
        int i3 = a + a2;
        if (!this.f646h && this.f647i == null) {
            this.f647i = new String(this.f639a, 30, this.f643e);
        }
        return i3;
    }

    /* renamed from: a */
    public final C1882dx mo34061a() {
        int i = this.f640b;
        int i2 = this.f644f;
        if (i < i2) {
            return C1882dx.m544a(this.f647i, this.f642d, this.f645g, true, Arrays.copyOf(this.f639a, i), this.f646h);
        }
        C1882dx a = C1882dx.m544a(this.f647i, this.f642d, this.f645g, false, Arrays.copyOf(this.f639a, i2), this.f646h);
        mo34063c();
        return a;
    }

    /* renamed from: b */
    public final int mo34062b() {
        return this.f644f;
    }

    /* renamed from: c */
    public final void mo34063c() {
        this.f640b = 0;
        this.f643e = -1;
        this.f641c = -1;
        this.f646h = false;
        this.f644f = 30;
        this.f642d = -1;
        this.f645g = -1;
        this.f647i = null;
    }
}
