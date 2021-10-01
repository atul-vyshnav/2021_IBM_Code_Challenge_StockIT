package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.o */
public abstract class C1992o extends C1987j implements C1993p {
    public C1992o() {
        super("com.google.android.play.core.appupdate.protocol.IAppUpdateServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo34134a(int i, Parcel parcel) throws RemoteException {
        if (i == 2) {
            mo33862a((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
            return true;
        } else if (i != 3) {
            return false;
        } else {
            mo33863b((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
            return true;
        }
    }
}
