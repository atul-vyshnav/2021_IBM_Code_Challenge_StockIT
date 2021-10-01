package com.google.android.gms.internal.ads;

import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzcev implements zzeoy<zzcab<zzbwl>> {
    private final zzeph<Executor> zzesr;
    private final zzeph<zzczn> zzfop;

    private zzcev(zzeph<zzczn> zzeph, zzeph<Executor> zzeph2) {
        this.zzfop = zzeph;
        this.zzesr = zzeph2;
    }

    public static zzcev zzx(zzeph<zzczn> zzeph, zzeph<Executor> zzeph2) {
        return new zzcev(zzeph, zzeph2);
    }

    public final /* synthetic */ Object get() {
        return (zzcab) zzepe.zza(new zzcab(this.zzfop.get(), this.zzesr.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}
