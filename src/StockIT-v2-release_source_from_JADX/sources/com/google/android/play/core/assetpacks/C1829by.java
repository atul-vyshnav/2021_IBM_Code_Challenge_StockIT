package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.google.android.play.core.assetpacks.by */
final class C1829by extends OutputStream {

    /* renamed from: a */
    private final C1862dd f541a = new C1862dd();

    /* renamed from: b */
    private final File f542b;

    /* renamed from: c */
    private final C1876dr f543c;

    /* renamed from: d */
    private long f544d;

    /* renamed from: e */
    private long f545e;

    /* renamed from: f */
    private FileOutputStream f546f;

    /* renamed from: g */
    private C1882dx f547g;

    C1829by(File file, C1876dr drVar) {
        this.f542b = file;
        this.f543c = drVar;
    }

    public final void write(int i) throws IOException {
        write(new byte[]{(byte) i});
    }

    public final void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public final void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        while (i2 > 0) {
            if (this.f544d == 0 && this.f545e == 0) {
                int a = this.f541a.mo34060a(bArr, i, i2);
                if (a != -1) {
                    i += a;
                    i2 -= a;
                    C1882dx a2 = this.f541a.mo34061a();
                    this.f547g = a2;
                    if (a2.mo34099g()) {
                        this.f544d = 0;
                        this.f543c.mo34087b(this.f547g.mo34100h(), this.f547g.mo34100h().length);
                        this.f545e = (long) this.f547g.mo34100h().length;
                    } else if (!this.f547g.mo34093b() || this.f547g.mo34092a()) {
                        byte[] h = this.f547g.mo34100h();
                        this.f543c.mo34087b(h, h.length);
                        this.f544d = this.f547g.mo34095d();
                    } else {
                        this.f543c.mo34082a(this.f547g.mo34100h());
                        File file = new File(this.f542b, this.f547g.mo34094c());
                        file.getParentFile().mkdirs();
                        this.f544d = this.f547g.mo34095d();
                        this.f546f = new FileOutputStream(file);
                    }
                } else {
                    return;
                }
            }
            if (!this.f547g.mo34092a()) {
                if (this.f547g.mo34099g()) {
                    this.f543c.mo34079a(this.f545e, bArr, i, i2);
                    this.f545e += (long) i2;
                    i3 = i2;
                } else if (this.f547g.mo34093b()) {
                    i3 = (int) Math.min((long) i2, this.f544d);
                    this.f546f.write(bArr, i, i3);
                    long j = this.f544d - ((long) i3);
                    this.f544d = j;
                    if (j == 0) {
                        this.f546f.close();
                    }
                } else {
                    i3 = (int) Math.min((long) i2, this.f544d);
                    int length = this.f547g.mo34100h().length;
                    this.f543c.mo34079a((((long) length) + this.f547g.mo34095d()) - this.f544d, bArr, i, i3);
                    this.f544d -= (long) i3;
                }
                i += i3;
                i2 -= i3;
            }
        }
    }
}
