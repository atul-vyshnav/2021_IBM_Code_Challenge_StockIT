package com.google.android.gms.internal.ads;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzasn extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzasn> CREATOR = new zzasm();
    private final ApplicationInfo applicationInfo;
    private final int versionCode;
    private final zzbbx zzbpe;
    private final zzvn zzbpf;
    private final float zzbsa;
    private final String zzbuu;
    private final String zzcja;
    private final boolean zzdko;
    private final zzadu zzdly;
    private final List<String> zzdlz;
    private final int zzdoc;
    private final int zzdod;
    private final Bundle zzdqq;
    private final zzvg zzdqr;
    private final PackageInfo zzdqs;
    private final String zzdqt;
    private final String zzdqu;
    private final Bundle zzdqv;
    private final int zzdqw;
    private final Bundle zzdqx;
    private final boolean zzdqy;
    private final String zzdqz;
    private final long zzdra;
    private final String zzdrb;
    private final List<String> zzdrc;
    private final String zzdrd;
    private final List<String> zzdre;
    private final long zzdrf;
    private final String zzdrg;
    private final float zzdrh;
    private final int zzdri;
    private final int zzdrj;
    private final boolean zzdrk;
    private final boolean zzdrl;
    private final String zzdrm;
    private final boolean zzdrn;
    private final String zzdro;
    private final int zzdrp;
    private final Bundle zzdrq;
    private final String zzdrr;
    private final zzyu zzdrs;
    private final boolean zzdrt;
    private final Bundle zzdru;
    private final String zzdrv;
    private final String zzdrw;
    private final String zzdrx;
    private final boolean zzdry;
    private final List<Integer> zzdrz;
    private final String zzdsa;
    private final List<String> zzdsb;
    private final int zzdsc;
    private final boolean zzdsd;
    private final boolean zzdse;
    private final boolean zzdsf;
    private final ArrayList<String> zzdsg;
    private final String zzdsh;
    private final zzajc zzdsi;
    private final String zzdsj;
    private final Bundle zzdsk;

    zzasn(int i, Bundle bundle, zzvg zzvg, zzvn zzvn, String str, ApplicationInfo applicationInfo2, PackageInfo packageInfo, String str2, String str3, String str4, zzbbx zzbbx, Bundle bundle2, int i2, List<String> list, Bundle bundle3, boolean z, int i3, int i4, float f, String str5, long j, String str6, List<String> list2, String str7, zzadu zzadu, List<String> list3, long j2, String str8, float f2, boolean z2, int i5, int i6, boolean z3, boolean z4, String str9, String str10, boolean z5, int i7, Bundle bundle4, String str11, zzyu zzyu, boolean z6, Bundle bundle5, String str12, String str13, String str14, boolean z7, List<Integer> list4, String str15, List<String> list5, int i8, boolean z8, boolean z9, boolean z10, ArrayList<String> arrayList, String str16, zzajc zzajc, String str17, Bundle bundle6) {
        List<String> list6;
        List<String> list7;
        this.versionCode = i;
        this.zzdqq = bundle;
        this.zzdqr = zzvg;
        this.zzbpf = zzvn;
        this.zzbuu = str;
        this.applicationInfo = applicationInfo2;
        this.zzdqs = packageInfo;
        this.zzdqt = str2;
        this.zzdqu = str3;
        this.zzcja = str4;
        this.zzbpe = zzbbx;
        this.zzdqv = bundle2;
        this.zzdqw = i2;
        this.zzdlz = list;
        if (list3 == null) {
            list6 = Collections.emptyList();
        } else {
            list6 = Collections.unmodifiableList(list3);
        }
        this.zzdre = list6;
        this.zzdqx = bundle3;
        this.zzdqy = z;
        this.zzdoc = i3;
        this.zzdod = i4;
        this.zzbsa = f;
        this.zzdqz = str5;
        this.zzdra = j;
        this.zzdrb = str6;
        if (list2 == null) {
            list7 = Collections.emptyList();
        } else {
            list7 = Collections.unmodifiableList(list2);
        }
        this.zzdrc = list7;
        this.zzdrd = str7;
        this.zzdly = zzadu;
        this.zzdrf = j2;
        this.zzdrg = str8;
        this.zzdrh = f2;
        this.zzdrn = z2;
        this.zzdri = i5;
        this.zzdrj = i6;
        this.zzdrk = z3;
        this.zzdrl = z4;
        this.zzdrm = str9;
        this.zzdro = str10;
        this.zzdko = z5;
        this.zzdrp = i7;
        this.zzdrq = bundle4;
        this.zzdrr = str11;
        this.zzdrs = zzyu;
        this.zzdrt = z6;
        this.zzdru = bundle5;
        this.zzdrv = str12;
        this.zzdrw = str13;
        this.zzdrx = str14;
        this.zzdry = z7;
        this.zzdrz = list4;
        this.zzdsa = str15;
        this.zzdsb = list5;
        this.zzdsc = i8;
        this.zzdsd = z8;
        this.zzdse = z9;
        this.zzdsf = z10;
        this.zzdsg = arrayList;
        this.zzdsh = str16;
        this.zzdsi = zzajc;
        this.zzdsj = str17;
        this.zzdsk = bundle6;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzdqq, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdqr, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbpf, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbuu, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.applicationInfo, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzdqs, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzdqt, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzdqu, false);
        SafeParcelWriter.writeString(parcel, 10, this.zzcja, false);
        SafeParcelWriter.writeParcelable(parcel, 11, this.zzbpe, i, false);
        SafeParcelWriter.writeBundle(parcel, 12, this.zzdqv, false);
        SafeParcelWriter.writeInt(parcel, 13, this.zzdqw);
        SafeParcelWriter.writeStringList(parcel, 14, this.zzdlz, false);
        SafeParcelWriter.writeBundle(parcel, 15, this.zzdqx, false);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzdqy);
        SafeParcelWriter.writeInt(parcel, 18, this.zzdoc);
        SafeParcelWriter.writeInt(parcel, 19, this.zzdod);
        SafeParcelWriter.writeFloat(parcel, 20, this.zzbsa);
        SafeParcelWriter.writeString(parcel, 21, this.zzdqz, false);
        SafeParcelWriter.writeLong(parcel, 25, this.zzdra);
        SafeParcelWriter.writeString(parcel, 26, this.zzdrb, false);
        SafeParcelWriter.writeStringList(parcel, 27, this.zzdrc, false);
        SafeParcelWriter.writeString(parcel, 28, this.zzdrd, false);
        SafeParcelWriter.writeParcelable(parcel, 29, this.zzdly, i, false);
        SafeParcelWriter.writeStringList(parcel, 30, this.zzdre, false);
        SafeParcelWriter.writeLong(parcel, 31, this.zzdrf);
        SafeParcelWriter.writeString(parcel, 33, this.zzdrg, false);
        SafeParcelWriter.writeFloat(parcel, 34, this.zzdrh);
        SafeParcelWriter.writeInt(parcel, 35, this.zzdri);
        SafeParcelWriter.writeInt(parcel, 36, this.zzdrj);
        SafeParcelWriter.writeBoolean(parcel, 37, this.zzdrk);
        SafeParcelWriter.writeBoolean(parcel, 38, this.zzdrl);
        SafeParcelWriter.writeString(parcel, 39, this.zzdrm, false);
        SafeParcelWriter.writeBoolean(parcel, 40, this.zzdrn);
        SafeParcelWriter.writeString(parcel, 41, this.zzdro, false);
        SafeParcelWriter.writeBoolean(parcel, 42, this.zzdko);
        SafeParcelWriter.writeInt(parcel, 43, this.zzdrp);
        SafeParcelWriter.writeBundle(parcel, 44, this.zzdrq, false);
        SafeParcelWriter.writeString(parcel, 45, this.zzdrr, false);
        SafeParcelWriter.writeParcelable(parcel, 46, this.zzdrs, i, false);
        SafeParcelWriter.writeBoolean(parcel, 47, this.zzdrt);
        SafeParcelWriter.writeBundle(parcel, 48, this.zzdru, false);
        SafeParcelWriter.writeString(parcel, 49, this.zzdrv, false);
        SafeParcelWriter.writeString(parcel, 50, this.zzdrw, false);
        SafeParcelWriter.writeString(parcel, 51, this.zzdrx, false);
        SafeParcelWriter.writeBoolean(parcel, 52, this.zzdry);
        SafeParcelWriter.writeIntegerList(parcel, 53, this.zzdrz, false);
        SafeParcelWriter.writeString(parcel, 54, this.zzdsa, false);
        SafeParcelWriter.writeStringList(parcel, 55, this.zzdsb, false);
        SafeParcelWriter.writeInt(parcel, 56, this.zzdsc);
        SafeParcelWriter.writeBoolean(parcel, 57, this.zzdsd);
        SafeParcelWriter.writeBoolean(parcel, 58, this.zzdse);
        SafeParcelWriter.writeBoolean(parcel, 59, this.zzdsf);
        SafeParcelWriter.writeStringList(parcel, 60, this.zzdsg, false);
        SafeParcelWriter.writeString(parcel, 61, this.zzdsh, false);
        SafeParcelWriter.writeParcelable(parcel, 63, this.zzdsi, i, false);
        SafeParcelWriter.writeString(parcel, 64, this.zzdsj, false);
        SafeParcelWriter.writeBundle(parcel, 65, this.zzdsk, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
