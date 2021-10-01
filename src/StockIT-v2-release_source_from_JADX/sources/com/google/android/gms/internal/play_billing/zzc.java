package com.google.android.gms.internal.play_billing;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public abstract class zzc extends zzf implements zzd {
    public static zzd zzn(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.vending.billing.IInAppBillingService");
        if (queryLocalInterface instanceof zzd) {
            return (zzd) queryLocalInterface;
        }
        return new zzb(iBinder);
    }
}
