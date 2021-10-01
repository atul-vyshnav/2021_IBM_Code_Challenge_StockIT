package com.google.android.play.core.install;

/* renamed from: com.google.android.play.core.install.a */
final class C1910a extends InstallState {

    /* renamed from: a */
    private final int f764a;

    /* renamed from: b */
    private final long f765b;

    /* renamed from: c */
    private final long f766c;

    /* renamed from: d */
    private final int f767d;

    /* renamed from: e */
    private final String f768e;

    C1910a(int i, long j, long j2, int i2, String str) {
        this.f764a = i;
        this.f765b = j;
        this.f766c = j2;
        this.f767d = i2;
        if (str != null) {
            this.f768e = str;
            return;
        }
        throw new NullPointerException("Null packageName");
    }

    public final long bytesDownloaded() {
        return this.f765b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof InstallState) {
            InstallState installState = (InstallState) obj;
            return this.f764a == installState.installStatus() && this.f765b == installState.bytesDownloaded() && this.f766c == installState.totalBytesToDownload() && this.f767d == installState.installErrorCode() && this.f768e.equals(installState.packageName());
        }
    }

    public final int hashCode() {
        int i = this.f764a;
        long j = this.f765b;
        long j2 = this.f766c;
        return ((((((((i ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f767d) * 1000003) ^ this.f768e.hashCode();
    }

    public final int installErrorCode() {
        return this.f767d;
    }

    public final int installStatus() {
        return this.f764a;
    }

    public final String packageName() {
        return this.f768e;
    }

    public final String toString() {
        int i = this.f764a;
        long j = this.f765b;
        long j2 = this.f766c;
        int i2 = this.f767d;
        String str = this.f768e;
        StringBuilder sb = new StringBuilder(str.length() + 164);
        sb.append("InstallState{installStatus=");
        sb.append(i);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", installErrorCode=");
        sb.append(i2);
        sb.append(", packageName=");
        sb.append(str);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f766c;
    }
}
