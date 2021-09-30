package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzq<E> extends zzp<E> {
    static final zzp<Object> zza = new zzq(new Object[0], 0);
    final transient Object[] zzb;

    zzq(Object[] objArr, int i) {
        this.zzb = objArr;
    }

    public final E get(int i) {
        zzj.zza(i, 0, "index");
        return this.zzb[i];
    }

    public final int size() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, 0);
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zze() {
        return this.zzb;
    }
}
