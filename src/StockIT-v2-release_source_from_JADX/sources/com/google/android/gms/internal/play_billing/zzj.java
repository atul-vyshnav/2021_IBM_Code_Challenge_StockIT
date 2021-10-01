package com.google.android.gms.internal.play_billing;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class zzj {
    public static int zzb(int i, int i2, @NullableDecl String str) {
        if (i >= 0 && i <= i2) {
            return i;
        }
        throw new IndexOutOfBoundsException(zzd(i, i2, "index"));
    }

    private static String zzd(int i, int i2, @NullableDecl String str) {
        if (i < 0) {
            return zzk.zza("%s (%s) must not be negative", str, Integer.valueOf(i));
        } else if (i2 >= 0) {
            return zzk.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
        } else {
            StringBuilder sb = new StringBuilder(26);
            sb.append("negative size: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void zzc(int i, int i2, int i3) {
        String str;
        if (i < 0 || i2 < i || i2 > i3) {
            if (i < 0 || i > i3) {
                str = zzd(i, i3, "start index");
            } else if (i2 < 0 || i2 > i3) {
                str = zzd(i2, i3, "end index");
            } else {
                str = zzk.zza("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i));
            }
            throw new IndexOutOfBoundsException(str);
        }
    }

    public static int zza(int i, int i2, @NullableDecl String str) {
        String str2;
        if (i >= 0 && i < i2) {
            return i;
        }
        if (i < 0) {
            str2 = zzk.zza("%s (%s) must not be negative", "index", Integer.valueOf(i));
        } else if (i2 < 0) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("negative size: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else {
            str2 = zzk.zza("%s (%s) must be less than size (%s)", "index", Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IndexOutOfBoundsException(str2);
    }
}
