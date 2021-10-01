package com.google.android.gms.internal.vision;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.util.Log;
import com.google.android.exoplayer2.extractor.p012ts.TsExtractor;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public final class zzbc {
    private static volatile zzcn<Boolean> zzfv = zzcn.zzbx();
    private static final Object zzfw = new Object();

    private static boolean zzh(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo("com.google.android.gms", 0).flags & TsExtractor.TS_STREAM_TYPE_AC3) != 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public static boolean zza(Context context, Uri uri) {
        boolean z;
        String authority = uri.getAuthority();
        boolean z2 = false;
        if (!"com.google.android.gms.phenotype".equals(authority)) {
            StringBuilder sb = new StringBuilder(String.valueOf(authority).length() + 91);
            sb.append(authority);
            sb.append(" is an unsupported authority. Only com.google.android.gms.phenotype authority is supported.");
            Log.e("PhenotypeClientHelper", sb.toString());
            return false;
        } else if (zzfv.isPresent()) {
            return zzfv.get().booleanValue();
        } else {
            synchronized (zzfw) {
                if (zzfv.isPresent()) {
                    boolean booleanValue = zzfv.get().booleanValue();
                    return booleanValue;
                }
                if (!"com.google.android.gms".equals(context.getPackageName())) {
                    ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.phenotype", 0);
                    if (resolveContentProvider == null || !"com.google.android.gms".equals(resolveContentProvider.packageName)) {
                        z = false;
                        if (z && zzh(context)) {
                            z2 = true;
                        }
                        zzfv = zzcn.zzb(Boolean.valueOf(z2));
                        return zzfv.get().booleanValue();
                    }
                }
                z = true;
                z2 = true;
                zzfv = zzcn.zzb(Boolean.valueOf(z2));
                return zzfv.get().booleanValue();
            }
        }
    }
}
