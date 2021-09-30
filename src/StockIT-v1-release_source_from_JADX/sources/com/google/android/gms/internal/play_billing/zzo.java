package com.google.android.gms.internal.play_billing;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
final class zzo extends zzp {
    final transient int zza;
    final transient int zzb;
    final /* synthetic */ zzp zzc;

    zzo(zzp zzp, int i, int i2) {
        this.zzc = zzp;
        this.zza = i;
        this.zzb = i2;
    }

    public final Object get(int i) {
        zzj.zza(i, this.zzb, "index");
        return this.zzc.get(i + this.zza);
    }

    public final int size() {
        return this.zzb;
    }

    public final /* bridge */ /* synthetic */ List subList(int i, int i2) {
        return subList(i, i2);
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        return this.zzc.zzc() + this.zza + this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return this.zzc.zzc() + this.zza;
    }

    /* access modifiers changed from: package-private */
    public final Object[] zze() {
        return this.zzc.zze();
    }

    public final zzp zzf(int i, int i2) {
        zzj.zzc(i, i2, this.zzb);
        zzp zzp = this.zzc;
        int i3 = this.zza;
        return zzp.subList(i + i3, i2 + i3);
    }
}
