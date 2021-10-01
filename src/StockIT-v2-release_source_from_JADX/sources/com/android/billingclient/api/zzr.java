package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzr implements Runnable {
    public final /* synthetic */ ConsumeResponseListener zza;
    public final /* synthetic */ ConsumeParams zzb;

    public /* synthetic */ zzr(ConsumeResponseListener consumeResponseListener, ConsumeParams consumeParams) {
        this.zza = consumeResponseListener;
        this.zzb = consumeParams;
    }

    public final void run() {
        this.zza.onConsumeResponse(zzak.zzr, this.zzb.getPurchaseToken());
    }
}
