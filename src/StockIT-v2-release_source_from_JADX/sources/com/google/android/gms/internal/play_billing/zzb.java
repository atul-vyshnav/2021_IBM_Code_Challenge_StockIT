package com.google.android.gms.internal.play_billing;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.billingclient.api.BillingClient;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class zzb extends zze implements zzd {
    zzb(IBinder iBinder) {
        super(iBinder, "com.android.vending.billing.IInAppBillingService");
    }

    public final int zza(int i, String str, String str2) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(3);
        zzn.writeString(str);
        zzn.writeString(str2);
        Parcel zzo = zzo(5, zzn);
        int readInt = zzo.readInt();
        zzo.recycle();
        return readInt;
    }

    public final int zzb(int i, String str, String str2) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(i);
        zzn.writeString(str);
        zzn.writeString(str2);
        Parcel zzo = zzo(1, zzn);
        int readInt = zzo.readInt();
        zzo.recycle();
        return readInt;
    }

    public final int zzc(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(7);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(10, zzn);
        int readInt = zzo.readInt();
        zzo.recycle();
        return readInt;
    }

    public final Bundle zzd(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(9);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(902, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zze(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(9);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(12, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zzf(int i, String str, String str2, String str3, String str4) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(3);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(str3);
        zzn.writeString((String) null);
        Parcel zzo = zzo(3, zzn);
        Bundle bundle = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle;
    }

    public final Bundle zzg(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(i);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(str3);
        zzn.writeString((String) null);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(8, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zzh(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(6);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(str3);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(9, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zzi(int i, String str, String str2, String str3) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(3);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(str3);
        Parcel zzo = zzo(4, zzn);
        Bundle bundle = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle;
    }

    public final Bundle zzj(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(9);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(str3);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(11, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zzk(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(3);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(2, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }

    public final Bundle zzl(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(10);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzg.zzb(zzn, bundle);
        zzg.zzb(zzn, bundle2);
        Parcel zzo = zzo(901, zzn);
        Bundle bundle3 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle3;
    }

    public final Bundle zzm(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel zzn = zzn();
        zzn.writeInt(8);
        zzn.writeString(str);
        zzn.writeString(str2);
        zzn.writeString(BillingClient.SkuType.SUBS);
        zzg.zzb(zzn, bundle);
        Parcel zzo = zzo(801, zzn);
        Bundle bundle2 = (Bundle) zzg.zza(zzo, Bundle.CREATOR);
        zzo.recycle();
        return bundle2;
    }
}
