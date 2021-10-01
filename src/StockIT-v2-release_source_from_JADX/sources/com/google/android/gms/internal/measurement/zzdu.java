package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import kotlin.UByte;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
public abstract class zzdu implements Serializable, Iterable<Byte> {
    public static final zzdu zza = new zzee(zzff.zzb);
    private static final zzea zzb = (zzdr.zza() ? new zzeh((zzdx) null) : new zzdy((zzdx) null));
    private static final Comparator<zzdu> zzd = new zzdw();
    private int zzc = 0;

    zzdu() {
    }

    /* access modifiers changed from: private */
    public static int zzb(byte b) {
        return b & UByte.MAX_VALUE;
    }

    public abstract boolean equals(Object obj);

    public abstract byte zza(int i);

    public abstract int zza();

    /* access modifiers changed from: protected */
    public abstract int zza(int i, int i2, int i3);

    public abstract zzdu zza(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract String zza(Charset charset);

    /* access modifiers changed from: package-private */
    public abstract void zza(zzdv zzdv) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract byte zzb(int i);

    public abstract boolean zzc();

    public static zzdu zza(byte[] bArr, int i, int i2) {
        zzb(i, i + i2, bArr.length);
        return new zzee(zzb.zza(bArr, i, i2));
    }

    static zzdu zza(byte[] bArr) {
        return new zzee(bArr);
    }

    public static zzdu zza(String str) {
        return new zzee(str.getBytes(zzff.zza));
    }

    public final String zzb() {
        return zza() == 0 ? "" : zza(zzff.zza);
    }

    public final int hashCode() {
        int i = this.zzc;
        if (i == 0) {
            int zza2 = zza();
            i = zza(zza2, 0, zza2);
            if (i == 0) {
                i = 1;
            }
            this.zzc = i;
        }
        return i;
    }

    static zzec zzc(int i) {
        return new zzec(i, (zzdx) null);
    }

    /* access modifiers changed from: protected */
    public final int zzd() {
        return this.zzc;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(37);
            sb3.append("End index: ");
            sb3.append(i2);
            sb3.append(" >= ");
            sb3.append(i3);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zza())});
    }

    public /* synthetic */ Iterator iterator() {
        return new zzdx(this);
    }
}
