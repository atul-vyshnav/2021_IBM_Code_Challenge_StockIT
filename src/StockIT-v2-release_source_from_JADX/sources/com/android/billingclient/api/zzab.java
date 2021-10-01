package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzab implements Callable<Void> {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchaseHistoryResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzab(BillingClientImpl billingClientImpl, String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchaseHistoryResponseListener;
    }

    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzag zzi = BillingClientImpl.zzi(this.zzc, this.zza);
        this.zzb.onPurchaseHistoryResponse(zzi.zza(), zzi.zzb());
        return null;
    }
}
