package com.google.android.gms.internal.ads;

import com.thunkable.live.BuildConfig;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
final class zzahi implements zzahq<zzbgj> {
    zzahi() {
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        ((zzbgj) obj).zzal(BuildConfig.VERSION_NAME.equals(map.get("custom_close")));
    }
}
