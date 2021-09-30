package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzm implements Callable {
    public final /* synthetic */ BillingClientImpl zza;
    public final /* synthetic */ SkuDetails zzb;
    public final /* synthetic */ String zzc;

    public /* synthetic */ zzm(BillingClientImpl billingClientImpl, SkuDetails skuDetails, String str) {
        this.zza = billingClientImpl;
        this.zzb = skuDetails;
        this.zzc = str;
    }

    public final Object call() {
        return this.zza.zzf(this.zzb, this.zzc);
    }
}
