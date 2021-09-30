package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzj implements Runnable {
    public final /* synthetic */ AcknowledgePurchaseResponseListener zza;

    public /* synthetic */ zzj(AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        this.zza = acknowledgePurchaseResponseListener;
    }

    public final void run() {
        this.zza.onAcknowledgePurchaseResponse(zzak.zzr);
    }
}
