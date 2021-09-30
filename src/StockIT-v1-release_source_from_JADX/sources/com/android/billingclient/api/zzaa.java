package com.android.billingclient.api;

import com.android.billingclient.api.Purchase;
import com.google.android.gms.internal.play_billing.zzp;
import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzaa implements Callable<Void> {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchasesResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzaa(BillingClientImpl billingClientImpl, String str, PurchasesResponseListener purchasesResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchasesResponseListener;
    }

    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        Purchase.PurchasesResult zzk = BillingClientImpl.zzk(this.zzc, this.zza);
        if (zzk.getPurchasesList() != null) {
            this.zzb.onQueryPurchasesResponse(zzk.getBillingResult(), zzk.getPurchasesList());
            return null;
        }
        this.zzb.onQueryPurchasesResponse(zzk.getBillingResult(), zzp.zzg());
        return null;
    }
}
