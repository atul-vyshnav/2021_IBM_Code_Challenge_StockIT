package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzv implements Runnable {
    public final /* synthetic */ SkuDetailsResponseListener zza;

    public /* synthetic */ zzv(SkuDetailsResponseListener skuDetailsResponseListener) {
        this.zza = skuDetailsResponseListener;
    }

    public final void run() {
        this.zza.onSkuDetailsResponse(zzak.zzr, (List<SkuDetails>) null);
    }
}
