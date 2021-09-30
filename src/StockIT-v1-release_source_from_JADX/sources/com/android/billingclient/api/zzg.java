package com.android.billingclient.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.internal.play_billing.zza;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzg extends BroadcastReceiver {
    final /* synthetic */ zzh zza;
    /* access modifiers changed from: private */
    public final PurchasesUpdatedListener zzb;
    private boolean zzc;

    /* synthetic */ zzg(zzh zzh, PurchasesUpdatedListener purchasesUpdatedListener, zzf zzf) {
        this.zza = zzh;
        this.zzb = purchasesUpdatedListener;
    }

    public final void onReceive(Context context, Intent intent) {
        this.zzb.onPurchasesUpdated(zza.zzg(intent, "BillingBroadcastManager"), zza.zzi(intent.getExtras()));
    }

    public final void zzb(Context context, IntentFilter intentFilter) {
        if (!this.zzc) {
            context.registerReceiver(this.zza.zzb, intentFilter);
            this.zzc = true;
        }
    }

    public final void zzc(Context context) {
        if (this.zzc) {
            context.unregisterReceiver(this.zza.zzb);
            this.zzc = false;
            return;
        }
        zza.zzk("BillingBroadcastManager", "Receiver is not registered.");
    }
}
