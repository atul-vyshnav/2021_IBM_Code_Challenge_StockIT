package com.android.billingclient.api;

import android.content.Context;
import android.content.IntentFilter;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzh {
    private final Context zza;
    /* access modifiers changed from: private */
    public final zzg zzb;

    zzh(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        this.zza = context;
        this.zzb = new zzg(this, purchasesUpdatedListener, (zzf) null);
    }

    /* access modifiers changed from: package-private */
    public final PurchasesUpdatedListener zzb() {
        return this.zzb.zzb;
    }

    /* access modifiers changed from: package-private */
    public final void zzc() {
        this.zzb.zzc(this.zza);
    }

    /* access modifiers changed from: package-private */
    public final void zzd() {
        this.zzb.zzb(this.zza, new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED"));
    }
}
