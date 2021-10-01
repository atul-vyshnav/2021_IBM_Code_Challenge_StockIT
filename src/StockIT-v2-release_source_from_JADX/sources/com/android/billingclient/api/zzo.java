package com.android.billingclient.api;

import android.os.Bundle;
import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzo implements Callable {
    public final /* synthetic */ BillingClientImpl zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ Bundle zzc;

    public /* synthetic */ zzo(BillingClientImpl billingClientImpl, String str, Bundle bundle) {
        this.zza = billingClientImpl;
        this.zzb = str;
        this.zzc = bundle;
    }

    public final Object call() {
        return this.zza.zzg(this.zzb, this.zzc);
    }
}
