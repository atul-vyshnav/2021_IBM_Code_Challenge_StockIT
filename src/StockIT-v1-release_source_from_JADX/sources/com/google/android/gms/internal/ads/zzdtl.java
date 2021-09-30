package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.exoplayer2.DefaultLoadControl;

/* compiled from: com.google.android.gms:play-services-gass@@19.4.0 */
public final class zzdtl {
    public static zzduv zza(Context context, int i, zzgo zzgo, String str, String str2, String str3, zzdtc zzdtc) {
        return new zzdto(context, 1, zzgo, str, str2, str3, zzdtc).zzef(DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
    }
}
