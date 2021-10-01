package com.google.android.play.core.assetpacks;

import java.util.Arrays;

/* renamed from: com.google.android.play.core.assetpacks.dx */
final class C1882dx {

    /* renamed from: a */
    private final String f708a;

    /* renamed from: b */
    private final long f709b;

    /* renamed from: c */
    private final int f710c;

    /* renamed from: d */
    private final boolean f711d;

    /* renamed from: e */
    private final boolean f712e;

    /* renamed from: f */
    private final byte[] f713f;

    C1882dx() {
    }

    C1882dx(String str, long j, int i, boolean z, boolean z2, byte[] bArr) {
        this();
        this.f708a = str;
        this.f709b = j;
        this.f710c = i;
        this.f711d = z;
        this.f712e = z2;
        this.f713f = bArr;
    }

    /* renamed from: a */
    static C1882dx m544a(String str, long j, int i, boolean z, byte[] bArr, boolean z2) {
        return new C1882dx(str, j, i, z, z2, bArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo34092a() {
        if (mo34094c() == null) {
            return false;
        }
        return mo34094c().endsWith("/");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo34093b() {
        return mo34096e() == 0;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public String mo34094c() {
        return this.f708a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public long mo34095d() {
        return this.f709b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public int mo34096e() {
        return this.f710c;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1882dx) {
            C1882dx dxVar = (C1882dx) obj;
            String str = this.f708a;
            if (str != null ? str.equals(dxVar.mo34094c()) : dxVar.mo34094c() == null) {
                if (this.f709b == dxVar.mo34095d() && this.f710c == dxVar.mo34096e() && this.f711d == dxVar.mo34098f() && this.f712e == dxVar.mo34099g()) {
                    if (Arrays.equals(this.f713f, dxVar instanceof C1882dx ? dxVar.f713f : dxVar.mo34100h())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public boolean mo34098f() {
        return this.f711d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: g */
    public boolean mo34099g() {
        return this.f712e;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: h */
    public byte[] mo34100h() {
        return this.f713f;
    }

    public int hashCode() {
        String str = this.f708a;
        int hashCode = str == null ? 0 : str.hashCode();
        long j = this.f709b;
        int i = 1237;
        int i2 = (((((((hashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ this.f710c) * 1000003) ^ (true != this.f711d ? 1237 : 1231)) * 1000003;
        if (true == this.f712e) {
            i = 1231;
        }
        return ((i2 ^ i) * 1000003) ^ Arrays.hashCode(this.f713f);
    }

    public String toString() {
        String str = this.f708a;
        long j = this.f709b;
        int i = this.f710c;
        boolean z = this.f711d;
        boolean z2 = this.f712e;
        String arrays = Arrays.toString(this.f713f);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 126 + String.valueOf(arrays).length());
        sb.append("ZipEntry{name=");
        sb.append(str);
        sb.append(", size=");
        sb.append(j);
        sb.append(", compressionMethod=");
        sb.append(i);
        sb.append(", isPartial=");
        sb.append(z);
        sb.append(", isEndOfArchive=");
        sb.append(z2);
        sb.append(", headerBytes=");
        sb.append(arrays);
        sb.append("}");
        return sb.toString();
    }
}
