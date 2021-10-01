package com.google.android.gms.internal.ads;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzcie implements zzeoy<zzcib> {
    private final zzeph<String> zzfun;
    private final zzeph<zzcei> zzfym;
    private final zzeph<zzcdx> zzged;

    private zzcie(zzeph<String> zzeph, zzeph<zzcdx> zzeph2, zzeph<zzcei> zzeph3) {
        this.zzfun = zzeph;
        this.zzged = zzeph2;
        this.zzfym = zzeph3;
    }

    public static zzcie zzl(zzeph<String> zzeph, zzeph<zzcdx> zzeph2, zzeph<zzcei> zzeph3) {
        return new zzcie(zzeph, zzeph2, zzeph3);
    }

    public final /* synthetic */ Object get() {
        return new zzcib(this.zzfun.get(), this.zzged.get(), this.zzfym.get());
    }
}
