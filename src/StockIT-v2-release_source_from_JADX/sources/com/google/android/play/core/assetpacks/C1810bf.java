package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bf */
final class C1810bf extends AssetLocation {

    /* renamed from: a */
    private final String f451a;

    /* renamed from: b */
    private final long f452b;

    /* renamed from: c */
    private final long f453c;

    C1810bf(String str, long j, long j2) {
        if (str != null) {
            this.f451a = str;
            this.f452b = j;
            this.f453c = j2;
            return;
        }
        throw new NullPointerException("Null path");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetLocation) {
            AssetLocation assetLocation = (AssetLocation) obj;
            return this.f451a.equals(assetLocation.path()) && this.f452b == assetLocation.offset() && this.f453c == assetLocation.size();
        }
    }

    public final int hashCode() {
        int hashCode = this.f451a.hashCode();
        long j = this.f452b;
        long j2 = this.f453c;
        return ((((hashCode ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2));
    }

    public final long offset() {
        return this.f452b;
    }

    public final String path() {
        return this.f451a;
    }

    public final long size() {
        return this.f453c;
    }

    public final String toString() {
        String str = this.f451a;
        long j = this.f452b;
        long j2 = this.f453c;
        StringBuilder sb = new StringBuilder(str.length() + 76);
        sb.append("AssetLocation{path=");
        sb.append(str);
        sb.append(", offset=");
        sb.append(j);
        sb.append(", size=");
        sb.append(j2);
        sb.append("}");
        return sb.toString();
    }
}
