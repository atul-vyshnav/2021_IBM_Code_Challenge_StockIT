package com.android.billingclient.api;

import com.android.billingclient.api.Purchase;
import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzz implements Callable<Purchase.PurchasesResult> {
    final /* synthetic */ String zza;
    final /* synthetic */ BillingClientImpl zzb;

    zzz(BillingClientImpl billingClientImpl, String str) {
        this.zzb = billingClientImpl;
        this.zza = str;
    }

    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        return BillingClientImpl.zzk(this.zzb, this.zza);
    }
}
