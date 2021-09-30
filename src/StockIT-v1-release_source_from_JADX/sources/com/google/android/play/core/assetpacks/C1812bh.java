package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bh */
final class C1812bh extends AssetPackState {

    /* renamed from: a */
    private final String f457a;

    /* renamed from: b */
    private final int f458b;

    /* renamed from: c */
    private final int f459c;

    /* renamed from: d */
    private final long f460d;

    /* renamed from: e */
    private final long f461e;

    /* renamed from: f */
    private final int f462f;

    C1812bh(String str, int i, int i2, long j, long j2, int i3) {
        if (str != null) {
            this.f457a = str;
            this.f458b = i;
            this.f459c = i2;
            this.f460d = j;
            this.f461e = j2;
            this.f462f = i3;
            return;
        }
        throw new NullPointerException("Null name");
    }

    public final long bytesDownloaded() {
        return this.f460d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackState) {
            AssetPackState assetPackState = (AssetPackState) obj;
            return this.f457a.equals(assetPackState.name()) && this.f458b == assetPackState.status() && this.f459c == assetPackState.errorCode() && this.f460d == assetPackState.bytesDownloaded() && this.f461e == assetPackState.totalBytesToDownload() && this.f462f == assetPackState.transferProgressPercentage();
        }
    }

    public final int errorCode() {
        return this.f459c;
    }

    public final int hashCode() {
        int hashCode = this.f457a.hashCode();
        int i = this.f458b;
        int i2 = this.f459c;
        long j = this.f460d;
        long j2 = this.f461e;
        return ((((((((((hashCode ^ 1000003) * 1000003) ^ i) * 1000003) ^ i2) * 1000003) ^ ((int) ((j >>> 32) ^ j))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.f462f;
    }

    public final String name() {
        return this.f457a;
    }

    public final int status() {
        return this.f458b;
    }

    public final String toString() {
        String str = this.f457a;
        int i = this.f458b;
        int i2 = this.f459c;
        long j = this.f460d;
        long j2 = this.f461e;
        int i3 = this.f462f;
        StringBuilder sb = new StringBuilder(str.length() + 185);
        sb.append("AssetPackState{name=");
        sb.append(str);
        sb.append(", status=");
        sb.append(i);
        sb.append(", errorCode=");
        sb.append(i2);
        sb.append(", bytesDownloaded=");
        sb.append(j);
        sb.append(", totalBytesToDownload=");
        sb.append(j2);
        sb.append(", transferProgressPercentage=");
        sb.append(i3);
        sb.append("}");
        return sb.toString();
    }

    public final long totalBytesToDownload() {
        return this.f461e;
    }

    public final int transferProgressPercentage() {
        return this.f462f;
    }
}
