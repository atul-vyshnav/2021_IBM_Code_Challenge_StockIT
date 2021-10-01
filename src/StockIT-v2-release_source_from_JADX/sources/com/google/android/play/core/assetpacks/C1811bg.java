package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.bg */
final class C1811bg extends AssetPackLocation {

    /* renamed from: a */
    private final int f454a;

    /* renamed from: b */
    private final String f455b;

    /* renamed from: c */
    private final String f456c;

    C1811bg(int i, String str, String str2) {
        this.f454a = i;
        this.f455b = str;
        this.f456c = str2;
    }

    public final String assetsPath() {
        return this.f456c;
    }

    public final boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AssetPackLocation) {
            AssetPackLocation assetPackLocation = (AssetPackLocation) obj;
            if (this.f454a == assetPackLocation.packStorageMethod() && ((str = this.f455b) != null ? str.equals(assetPackLocation.path()) : assetPackLocation.path() == null)) {
                String str2 = this.f456c;
                String assetsPath = assetPackLocation.assetsPath();
                if (str2 != null ? str2.equals(assetsPath) : assetsPath == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = (this.f454a ^ 1000003) * 1000003;
        String str = this.f455b;
        int i2 = 0;
        int hashCode = (i ^ (str == null ? 0 : str.hashCode())) * 1000003;
        String str2 = this.f456c;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return hashCode ^ i2;
    }

    public final int packStorageMethod() {
        return this.f454a;
    }

    public final String path() {
        return this.f455b;
    }

    public final String toString() {
        int i = this.f454a;
        String str = this.f455b;
        String str2 = this.f456c;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str2).length());
        sb.append("AssetPackLocation{packStorageMethod=");
        sb.append(i);
        sb.append(", path=");
        sb.append(str);
        sb.append(", assetsPath=");
        sb.append(str2);
        sb.append("}");
        return sb.toString();
    }
}
