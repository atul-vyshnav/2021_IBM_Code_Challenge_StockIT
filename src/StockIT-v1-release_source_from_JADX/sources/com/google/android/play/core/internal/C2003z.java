package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.z */
public final class C2003z extends C1986i implements C1914ab {
    C2003z(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.inappreview.protocol.IInAppReviewService");
    }

    /* renamed from: a */
    public final void mo34133a(String str, Bundle bundle, C1916ad adVar) throws RemoteException {
        Parcel a = mo34222a();
        a.writeString(str);
        C1988k.m804a(a, (Parcelable) bundle);
        C1988k.m803a(a, (IInterface) adVar);
        mo34223a(2, a);
    }
}
