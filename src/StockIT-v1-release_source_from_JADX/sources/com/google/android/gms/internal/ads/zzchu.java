package com.google.android.gms.internal.ads;

import com.thunkable.live.BuildConfig;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
final /* synthetic */ class zzchu implements zzqu {
    private final zzbgj zzeqk;

    zzchu(zzbgj zzbgj) {
        this.zzeqk = zzbgj;
    }

    public final void zza(zzqr zzqr) {
        zzbgj zzbgj = this.zzeqk;
        HashMap hashMap = new HashMap();
        hashMap.put("isVisible", zzqr.zzbro ? BuildConfig.VERSION_NAME : "0");
        zzbgj.zza("onAdVisibilityChanged", (Map<String, ?>) hashMap);
    }
}
