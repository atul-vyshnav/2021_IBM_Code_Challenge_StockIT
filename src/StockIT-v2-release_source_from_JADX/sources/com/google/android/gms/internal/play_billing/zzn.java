package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzn<E> extends zzl<E> {
    private final zzp<E> zza;

    zzn(zzp<E> zzp, int i) {
        super(zzp.size(), i);
        this.zza = zzp;
    }

    /* access modifiers changed from: protected */
    public final E zza(int i) {
        return this.zza.get(i);
    }
}
