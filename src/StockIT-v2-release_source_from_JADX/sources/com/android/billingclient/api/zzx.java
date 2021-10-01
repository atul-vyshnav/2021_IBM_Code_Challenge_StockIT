package com.android.billingclient.api;

import android.os.Bundle;
import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzx implements Callable {
    public final /* synthetic */ BillingClientImpl zza;
    public final /* synthetic */ int zzb;
    public final /* synthetic */ SkuDetails zzc;
    public final /* synthetic */ String zzd;
    public final /* synthetic */ BillingFlowParams zze;
    public final /* synthetic */ Bundle zzf;

    public /* synthetic */ zzx(BillingClientImpl billingClientImpl, int i, SkuDetails skuDetails, String str, BillingFlowParams billingFlowParams, Bundle bundle) {
        this.zza = billingClientImpl;
        this.zzb = i;
        this.zzc = skuDetails;
        this.zzd = str;
        this.zze = billingFlowParams;
        this.zzf = bundle;
    }

    public final Object call() {
        return this.zza.zze(this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
    }
}
