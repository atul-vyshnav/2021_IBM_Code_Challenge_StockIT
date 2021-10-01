package com.android.billingclient.api;

import com.google.android.gms.internal.play_billing.zza;
import java.util.concurrent.Future;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final /* synthetic */ class zzw implements Runnable {
    public final /* synthetic */ Future zza;
    public final /* synthetic */ Runnable zzb;

    public /* synthetic */ zzw(Future future, Runnable runnable) {
        this.zza = future;
        this.zzb = runnable;
    }

    public final void run() {
        Future future = this.zza;
        Runnable runnable = this.zzb;
        if (!future.isDone() && !future.isCancelled()) {
            future.cancel(true);
            zza.zzk("BillingClient", "Async task is taking too long, cancel it!");
            if (runnable != null) {
                runnable.run();
            }
        }
    }
}
