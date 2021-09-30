package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.x */
public final class C2001x extends C1986i implements C2002y {
    C2001x(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetPackExtractionServiceCallback");
    }

    /* renamed from: a */
    public final void mo34239a(Bundle bundle) throws RemoteException {
        Parcel a = mo34222a();
        C1988k.m804a(a, (Parcelable) bundle);
        mo34223a(3, a);
    }

    /* renamed from: a */
    public final void mo34240a(Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel a = mo34222a();
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m804a(a, (Parcelable) bundle2);
        mo34223a(2, a);
    }

    /* renamed from: b */
    public final void mo34241b(Bundle bundle) throws RemoteException {
        Parcel a = mo34222a();
        C1988k.m804a(a, (Parcelable) bundle);
        mo34223a(4, a);
    }
}
