package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-vision@@19.0.0 */
public final class zzx extends zzb implements zzy {
    zzx(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.text.internal.client.INativeTextRecognizer");
    }

    public final zzac[] zza(IObjectWrapper iObjectWrapper, zzp zzp, zzae zzae) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzd.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzd.zza(obtainAndWriteInterfaceToken, (Parcelable) zzp);
        zzd.zza(obtainAndWriteInterfaceToken, (Parcelable) zzae);
        Parcel zza = zza(3, obtainAndWriteInterfaceToken);
        zzac[] zzacArr = (zzac[]) zza.createTypedArray(zzac.CREATOR);
        zza.recycle();
        return zzacArr;
    }

    public final void zzq() throws RemoteException {
        zzb(2, obtainAndWriteInterfaceToken());
    }
}
