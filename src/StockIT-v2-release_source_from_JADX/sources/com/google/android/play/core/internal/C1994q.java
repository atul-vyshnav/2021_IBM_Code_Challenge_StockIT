package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.q */
public final class C1994q extends C1986i implements C1996s {
    C1994q(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetModuleService");
    }

    /* renamed from: a */
    public final void mo34229a(String str, Bundle bundle, Bundle bundle2, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(6, a);
    }

    /* renamed from: a */
    public final void mo34230a(String str, Bundle bundle, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(5, a);
    }

    /* renamed from: a */
    public final void mo34231a(String str, List<Bundle> list, Bundle bundle, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(2, a);
    }

    /* renamed from: b */
    public final void mo34232b(String str, Bundle bundle, Bundle bundle2, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(7, a);
    }

    /* renamed from: b */
    public final void mo34233b(String str, Bundle bundle, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(10, a);
    }

    /* renamed from: b */
    public final void mo34234b(String str, List<Bundle> list, Bundle bundle, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(14, a);
    }

    /* renamed from: c */
    public final void mo34235c(String str, Bundle bundle, Bundle bundle2, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(9, a);
    }

    /* renamed from: c */
    public final void mo34236c(String str, List<Bundle> list, Bundle bundle, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        a.writeTypedList(list);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(12, a);
    }

    /* renamed from: d */
    public final void mo34237d(String str, Bundle bundle, Bundle bundle2, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(11, a);
    }

    /* renamed from: e */
    public final void mo34238e(String str, Bundle bundle, Bundle bundle2, C1998u uVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        C1988k.m803a(a, (IInterface) uVar);
        mo34223a(13, a);
    }
}
