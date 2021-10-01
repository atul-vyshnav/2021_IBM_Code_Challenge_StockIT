package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.bs */
public final class C1958bs extends C1986i implements C1960bu {
    C1958bs(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.splitinstall.protocol.ISplitInstallService");
    }

    /* renamed from: a */
    public final void mo34168a(String str, int i, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeInt(i);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(4, a);
    }

    /* renamed from: a */
    public final void mo34169a(String str, int i, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeInt(i);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(5, a);
    }

    /* renamed from: a */
    public final void mo34170a(String str, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(6, a);
    }

    /* renamed from: a */
    public final void mo34171a(String str, List<Bundle> list, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(2, a);
    }

    /* renamed from: b */
    public final void mo34172b(String str, List<Bundle> list, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(7, a);
    }

    /* renamed from: c */
    public final void mo34173c(String str, List<Bundle> list, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(8, a);
    }

    /* renamed from: d */
    public final void mo34174d(String str, List<Bundle> list, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(13, a);
    }

    /* renamed from: e */
    public final void mo34175e(String str, List<Bundle> list, Bundle bundle, C1962bw bwVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) bwVar);
        mo34223a(14, a);
    }
}
