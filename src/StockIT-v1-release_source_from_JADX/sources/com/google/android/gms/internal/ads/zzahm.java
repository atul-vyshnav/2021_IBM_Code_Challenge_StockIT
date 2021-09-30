package com.google.android.gms.internal.ads;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
final class zzahm implements zzahq<zzbgj> {
    zzahm() {
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        zzbgj zzbgj = (zzbgj) obj;
        String str = (String) map.get("action");
        if ("pause".equals(str)) {
            zzbgj.zzkn();
        } else if ("resume".equals(str)) {
            zzbgj.zzko();
        }
    }
}
