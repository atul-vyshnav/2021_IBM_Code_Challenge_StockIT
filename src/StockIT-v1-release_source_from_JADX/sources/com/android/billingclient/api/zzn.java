package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzn implements Callable {
    public final /* synthetic */ BillingClientImpl zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzn(BillingClientImpl billingClientImpl, String str) {
        this.zza = billingClientImpl;
        this.zzb = str;
    }

    public final Object call() {
        return this.zza.zzn(this.zzb);
    }
}
