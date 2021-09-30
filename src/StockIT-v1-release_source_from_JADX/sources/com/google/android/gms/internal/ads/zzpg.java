package com.google.android.gms.internal.ads;

import com.google.android.exoplayer2.util.MimeTypes;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzpg {
    public static boolean zzbf(String str) {
        return MimeTypes.BASE_TYPE_AUDIO.equals(zzbh(str));
    }

    public static boolean zzbg(String str) {
        return MimeTypes.BASE_TYPE_VIDEO.equals(zzbh(str));
    }

    private static String zzbh(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(47);
        if (indexOf != -1) {
            return str.substring(0, indexOf);
        }
        String valueOf = String.valueOf(str);
        throw new IllegalArgumentException(valueOf.length() != 0 ? "Invalid mime type: ".concat(valueOf) : new String("Invalid mime type: "));
    }
}
