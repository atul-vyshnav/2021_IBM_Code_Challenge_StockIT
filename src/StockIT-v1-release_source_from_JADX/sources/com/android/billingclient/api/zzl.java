package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzl implements Callable {
    public final /* synthetic */ BillingClientImpl zza;
    public final /* synthetic */ ConsumeParams zzb;
    public final /* synthetic */ ConsumeResponseListener zzc;

    public /* synthetic */ zzl(BillingClientImpl billingClientImpl, ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) {
        this.zza = billingClientImpl;
        this.zzb = consumeParams;
        this.zzc = consumeResponseListener;
    }

    public final Object call() {
        this.zza.zzp(this.zzb, this.zzc);
        return null;
    }
}
