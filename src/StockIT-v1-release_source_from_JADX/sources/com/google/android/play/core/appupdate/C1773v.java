package com.google.android.play.core.appupdate;

/* renamed from: com.google.android.play.core.appupdate.v */
final class C1773v extends AppUpdateOptions {

    /* renamed from: a */
    private final int f328a;

    /* renamed from: b */
    private final boolean f329b;

    /* synthetic */ C1773v(int i, boolean z) {
        this.f328a = i;
        this.f329b = z;
    }

    public final boolean allowAssetPackDeletion() {
        return this.f329b;
    }

    public final int appUpdateType() {
        return this.f328a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AppUpdateOptions) {
            AppUpdateOptions appUpdateOptions = (AppUpdateOptions) obj;
            return this.f328a == appUpdateOptions.appUpdateType() && this.f329b == appUpdateOptions.allowAssetPackDeletion();
        }
    }

    public final int hashCode() {
        return ((this.f328a ^ 1000003) * 1000003) ^ (true != this.f329b ? 1237 : 1231);
    }

    public final String toString() {
        int i = this.f328a;
        boolean z = this.f329b;
        StringBuilder sb = new StringBuilder(73);
        sb.append("AppUpdateOptions{appUpdateType=");
        sb.append(i);
        sb.append(", allowAssetPackDeletion=");
        sb.append(z);
        sb.append("}");
        return sb.toString();
    }
}
