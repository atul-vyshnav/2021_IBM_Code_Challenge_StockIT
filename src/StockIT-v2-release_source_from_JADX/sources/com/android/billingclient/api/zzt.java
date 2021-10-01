package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzt implements Runnable {
    public final /* synthetic */ PurchaseHistoryResponseListener zza;

    public /* synthetic */ zzt(PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zza = purchaseHistoryResponseListener;
    }

    public final void run() {
        this.zza.onPurchaseHistoryResponse(zzak.zzr, (List<PurchaseHistoryRecord>) null);
    }
}
