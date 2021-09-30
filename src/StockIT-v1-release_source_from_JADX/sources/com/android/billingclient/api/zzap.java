package com.android.billingclient.api;

import android.text.TextUtils;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class zzap {
    private String zza;

    private zzap() {
    }

    /* synthetic */ zzap(zzao zzao) {
    }

    public final zzap zza(String str) {
        this.zza = str;
        return this;
    }

    public final zzaq zzb() {
        if (!TextUtils.isEmpty(this.zza)) {
            return new zzaq(this.zza, (String) null, (zzao) null);
        }
        throw new IllegalArgumentException("SKU must be set.");
    }
}
