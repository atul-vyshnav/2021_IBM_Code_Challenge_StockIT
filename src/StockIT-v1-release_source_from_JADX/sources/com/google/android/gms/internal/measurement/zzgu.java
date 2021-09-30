package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
final class zzgu<T> implements zzhd<T> {
    private final zzgo zza;
    private final zzhv<?, ?> zzb;
    private final boolean zzc;
    private final zzes<?> zzd;

    private zzgu(zzhv<?, ?> zzhv, zzes<?> zzes, zzgo zzgo) {
        this.zzb = zzhv;
        this.zzc = zzes.zza(zzgo);
        this.zzd = zzes;
        this.zza = zzgo;
    }

    static <T> zzgu<T> zza(zzhv<?, ?> zzhv, zzes<?> zzes, zzgo zzgo) {
        return new zzgu<>(zzhv, zzes, zzgo);
    }

    public final T zza() {
        return this.zza.zzbr().zzs();
    }

    public final boolean zza(T t, T t2) {
        if (!this.zzb.zzb(t).equals(this.zzb.zzb(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza((Object) t).equals(this.zzd.zza((Object) t2));
        }
        return true;
    }

    public final int zza(T t) {
        int hashCode = this.zzb.zzb(t).hashCode();
        return this.zzc ? (hashCode * 53) + this.zzd.zza((Object) t).hashCode() : hashCode;
    }

    public final void zzb(T t, T t2) {
        zzhf.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzhf.zza(this.zzd, t, t2);
        }
    }

    public final void zza(T t, zzis zzis) throws IOException {
        Iterator<Map.Entry<?, Object>> zzd2 = this.zzd.zza((Object) t).zzd();
        while (zzd2.hasNext()) {
            Map.Entry next = zzd2.next();
            zzey zzey = (zzey) next.getKey();
            if (zzey.zzc() != zzip.MESSAGE || zzey.zzd() || zzey.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof zzfr) {
                zzis.zza(zzey.zza(), (Object) ((zzfr) next).zza().zzc());
            } else {
                zzis.zza(zzey.zza(), next.getValue());
            }
        }
        zzhv<?, ?> zzhv = this.zzb;
        zzhv.zzb(zzhv.zzb(t), zzis);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.internal.measurement.zzfd$zzf} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.measurement.zzdt r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.measurement.zzfd r0 = (com.google.android.gms.internal.measurement.zzfd) r0
            com.google.android.gms.internal.measurement.zzhy r1 = r0.zzb
            com.google.android.gms.internal.measurement.zzhy r2 = com.google.android.gms.internal.measurement.zzhy.zza()
            if (r1 != r2) goto L_0x0011
            com.google.android.gms.internal.measurement.zzhy r1 = com.google.android.gms.internal.measurement.zzhy.zzb()
            r0.zzb = r1
        L_0x0011:
            com.google.android.gms.internal.measurement.zzfd$zzd r10 = (com.google.android.gms.internal.measurement.zzfd.zzd) r10
            r10.zza()
            r10 = 0
            r0 = r10
        L_0x0018:
            if (r12 >= r13) goto L_0x00a4
            int r4 = com.google.android.gms.internal.measurement.zzdq.zza(r11, r12, r14)
            int r2 = r14.zza
            r12 = 11
            r3 = 2
            if (r2 == r12) goto L_0x0051
            r12 = r2 & 7
            if (r12 != r3) goto L_0x004c
            com.google.android.gms.internal.measurement.zzes<?> r12 = r9.zzd
            com.google.android.gms.internal.measurement.zzeq r0 = r14.zzd
            com.google.android.gms.internal.measurement.zzgo r3 = r9.zza
            int r5 = r2 >>> 3
            java.lang.Object r12 = r12.zza(r0, r3, r5)
            r0 = r12
            com.google.android.gms.internal.measurement.zzfd$zzf r0 = (com.google.android.gms.internal.measurement.zzfd.zzf) r0
            if (r0 != 0) goto L_0x0043
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.measurement.zzdq.zza((int) r2, (byte[]) r3, (int) r4, (int) r5, (com.google.android.gms.internal.measurement.zzhy) r6, (com.google.android.gms.internal.measurement.zzdt) r7)
            goto L_0x0018
        L_0x0043:
            com.google.android.gms.internal.measurement.zzgz.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L_0x004c:
            int r12 = com.google.android.gms.internal.measurement.zzdq.zza((int) r2, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.measurement.zzdt) r14)
            goto L_0x0018
        L_0x0051:
            r12 = 0
            r2 = r10
        L_0x0053:
            if (r4 >= r13) goto L_0x0099
            int r4 = com.google.android.gms.internal.measurement.zzdq.zza(r11, r4, r14)
            int r5 = r14.zza
            int r6 = r5 >>> 3
            r7 = r5 & 7
            if (r6 == r3) goto L_0x007b
            r8 = 3
            if (r6 == r8) goto L_0x0065
            goto L_0x0090
        L_0x0065:
            if (r0 != 0) goto L_0x0072
            if (r7 != r3) goto L_0x0090
            int r4 = com.google.android.gms.internal.measurement.zzdq.zze(r11, r4, r14)
            java.lang.Object r2 = r14.zzc
            com.google.android.gms.internal.measurement.zzdu r2 = (com.google.android.gms.internal.measurement.zzdu) r2
            goto L_0x0053
        L_0x0072:
            com.google.android.gms.internal.measurement.zzgz.zza()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L_0x007b:
            if (r7 != 0) goto L_0x0090
            int r4 = com.google.android.gms.internal.measurement.zzdq.zza(r11, r4, r14)
            int r12 = r14.zza
            com.google.android.gms.internal.measurement.zzes<?> r0 = r9.zzd
            com.google.android.gms.internal.measurement.zzeq r5 = r14.zzd
            com.google.android.gms.internal.measurement.zzgo r6 = r9.zza
            java.lang.Object r0 = r0.zza(r5, r6, r12)
            com.google.android.gms.internal.measurement.zzfd$zzf r0 = (com.google.android.gms.internal.measurement.zzfd.zzf) r0
            goto L_0x0053
        L_0x0090:
            r6 = 12
            if (r5 == r6) goto L_0x0099
            int r4 = com.google.android.gms.internal.measurement.zzdq.zza((int) r5, (byte[]) r11, (int) r4, (int) r13, (com.google.android.gms.internal.measurement.zzdt) r14)
            goto L_0x0053
        L_0x0099:
            if (r2 == 0) goto L_0x00a1
            int r12 = r12 << 3
            r12 = r12 | r3
            r1.zza((int) r12, (java.lang.Object) r2)
        L_0x00a1:
            r12 = r4
            goto L_0x0018
        L_0x00a4:
            if (r12 != r13) goto L_0x00a7
            return
        L_0x00a7:
            com.google.android.gms.internal.measurement.zzfo r10 = com.google.android.gms.internal.measurement.zzfo.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzgu.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzdt):void");
    }

    public final void zza(T t, zzhe zzhe, zzeq zzeq) throws IOException {
        boolean z;
        zzhv<?, ?> zzhv = this.zzb;
        zzes<?> zzes = this.zzd;
        Object zzc2 = zzhv.zzc(t);
        zzew<?> zzb2 = zzes.zzb(t);
        do {
            try {
                if (zzhe.zza() == Integer.MAX_VALUE) {
                    zzhv.zzb((Object) t, zzc2);
                    return;
                }
                int zzb3 = zzhe.zzb();
                if (zzb3 == 11) {
                    int i = 0;
                    Object obj = null;
                    zzdu zzdu = null;
                    while (zzhe.zza() != Integer.MAX_VALUE) {
                        int zzb4 = zzhe.zzb();
                        if (zzb4 == 16) {
                            i = zzhe.zzo();
                            obj = zzes.zza(zzeq, this.zza, i);
                        } else if (zzb4 == 26) {
                            if (obj != null) {
                                zzes.zza(zzhe, obj, zzeq, zzb2);
                            } else {
                                zzdu = zzhe.zzn();
                            }
                        } else if (!zzhe.zzc()) {
                            break;
                        }
                    }
                    if (zzhe.zzb() != 12) {
                        throw zzfo.zze();
                    } else if (zzdu != null) {
                        if (obj != null) {
                            zzes.zza(zzdu, obj, zzeq, zzb2);
                        } else {
                            zzhv.zza(zzc2, i, zzdu);
                        }
                    }
                } else if ((zzb3 & 7) == 2) {
                    Object zza2 = zzes.zza(zzeq, this.zza, zzb3 >>> 3);
                    if (zza2 != null) {
                        zzes.zza(zzhe, zza2, zzeq, zzb2);
                    } else {
                        z = zzhv.zza(zzc2, zzhe);
                        continue;
                    }
                } else {
                    z = zzhe.zzc();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzhv.zzb((Object) t, zzc2);
            }
        } while (z);
    }

    public final void zzc(T t) {
        this.zzb.zzd(t);
        this.zzd.zzc(t);
    }

    public final boolean zzd(T t) {
        return this.zzd.zza((Object) t).zzf();
    }

    public final int zzb(T t) {
        zzhv<?, ?> zzhv = this.zzb;
        int zze = zzhv.zze(zzhv.zzb(t)) + 0;
        return this.zzc ? zze + this.zzd.zza((Object) t).zzg() : zze;
    }
}
