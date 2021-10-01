package com.android.billingclient.api;

import com.google.android.gms.internal.play_billing.zzp;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzu implements Runnable {
    public final /* synthetic */ PurchasesResponseListener zza;

    public /* synthetic */ zzu(PurchasesResponseListener purchasesResponseListener) {
        this.zza = purchasesResponseListener;
    }

    public final void run() {
        this.zza.onQueryPurchasesResponse(zzak.zzr, zzp.zzg());
    }
}
