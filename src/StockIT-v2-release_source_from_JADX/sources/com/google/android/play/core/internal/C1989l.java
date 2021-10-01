package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.l */
public final class C1989l extends C1986i implements C1991n {
    C1989l(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.appupdate.protocol.IAppUpdateService");
    }

    /* renamed from: a */
    public final void mo34227a(String str, Bundle bundle, C1993p pVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) pVar);
        mo34223a(2, a);
    }

    /* renamed from: b */
    public final void mo34228b(String str, Bundle bundle, C1993p pVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) pVar);
        mo34223a(3, a);
    }
}
