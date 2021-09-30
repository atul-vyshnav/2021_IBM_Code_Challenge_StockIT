package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.dq */
final class C1875dq {

    /* renamed from: a */
    private final int f686a;

    /* renamed from: b */
    private final String f687b;

    /* renamed from: c */
    private final long f688c;

    /* renamed from: d */
    private final long f689d;

    /* renamed from: e */
    private final int f690e;

    C1875dq() {
    }

    C1875dq(int i, String str, long j, long j2, int i2) {
        this();
        this.f686a = i;
        this.f687b = str;
        this.f688c = j;
        this.f689d = j2;
        this.f690e = i2;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public int mo34069a() {
        return this.f686a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public String mo34070b() {
        return this.f687b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public long mo34071c() {
        return this.f688c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public long mo34072d() {
        return this.f689d;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public int mo34073e() {
        return this.f690e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0013, code lost:
        r1 = r7.f687b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 != r7) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r8 instanceof com.google.android.play.core.assetpacks.C1875dq
            r2 = 0
            if (r1 == 0) goto L_0x0046
            com.google.android.play.core.assetpacks.dq r8 = (com.google.android.play.core.assetpacks.C1875dq) r8
            int r1 = r7.f686a
            int r3 = r8.mo34069a()
            if (r1 != r3) goto L_0x0046
            java.lang.String r1 = r7.f687b
            if (r1 != 0) goto L_0x001e
            java.lang.String r1 = r8.mo34070b()
            if (r1 != 0) goto L_0x0046
            goto L_0x0029
        L_0x001e:
            java.lang.String r3 = r8.mo34070b()
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0029
            goto L_0x0046
        L_0x0029:
            long r3 = r7.f688c
            long r5 = r8.mo34071c()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0046
            long r3 = r7.f689d
            long r5 = r8.mo34072d()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0046
            int r1 = r7.f690e
            int r8 = r8.mo34073e()
            if (r1 != r8) goto L_0x0046
            return r0
        L_0x0046:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C1875dq.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = (this.f686a ^ 1000003) * 1000003;
        String str = this.f687b;
        int hashCode = str == null ? 0 : str.hashCode();
        long j = this.f688c;
        long j2 = this.f689d;
        return ((((((i ^ hashCode) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f690e;
    }

    public String toString() {
        int i = this.f686a;
        String str = this.f687b;
        long j = this.f688c;
        long j2 = this.f689d;
        int i2 = this.f690e;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 157);
        sb.append("SliceCheckpoint{fileExtractionStatus=");
        sb.append(i);
        sb.append(", filePath=");
        sb.append(str);
        sb.append(", fileOffset=");
        sb.append(j);
        sb.append(", remainingBytes=");
        sb.append(j2);
        sb.append(", previousChunk=");
        sb.append(i2);
        sb.append("}");
        return sb.toString();
    }
}
