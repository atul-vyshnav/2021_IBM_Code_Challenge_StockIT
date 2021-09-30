package com.google.android.play.core.appupdate;

import com.google.android.play.core.appupdate.AppUpdateOptions;

/* renamed from: com.google.android.play.core.appupdate.u */
final class C1772u extends AppUpdateOptions.Builder {

    /* renamed from: a */
    private Integer f326a;

    /* renamed from: b */
    private Boolean f327b;

    C1772u() {
    }

    public final AppUpdateOptions build() {
        String str = "";
        if (this.f326a == null) {
            str = str.concat(" appUpdateType");
        }
        if (this.f327b == null) {
            str = String.valueOf(str).concat(" allowAssetPackDeletion");
        }
        if (str.isEmpty()) {
            return new C1773v(this.f326a.intValue(), this.f327b.booleanValue());
        }
        String valueOf = String.valueOf(str);
        throw new IllegalStateException(valueOf.length() != 0 ? "Missing required properties:".concat(valueOf) : new String("Missing required properties:"));
    }

    public final AppUpdateOptions.Builder setAllowAssetPackDeletion(boolean z) {
        this.f327b = Boolean.valueOf(z);
        return this;
    }

    public final AppUpdateOptions.Builder setAppUpdateType(int i) {
        this.f326a = Integer.valueOf(i);
        return this;
    }
}
