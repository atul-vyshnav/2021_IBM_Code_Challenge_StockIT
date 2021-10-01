package com.google.android.gms.vision.clearcut;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.vision.zzea;
import com.google.android.gms.internal.vision.zzgs;
import com.google.android.gms.vision.C1574L;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public class LogUtils {
    public static zzea.zza zza(Context context) {
        zzea.zza.C3925zza zzl = zzea.zza.zzcj().zzl(context.getPackageName());
        String zzb = zzb(context);
        if (zzb != null) {
            zzl.zzm(zzb);
        }
        return (zzea.zza) ((zzgs) zzl.zzgc());
    }

    private static String zzb(Context context) {
        try {
            return Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            C1574L.m225e(e, "Unable to find calling package info for %s", context.getPackageName());
            return null;
        }
    }
}
