package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.ac */
public abstract class C1915ac extends C1987j implements C1916ad {
    public C1915ac() {
        super("com.google.android.play.core.inappreview.protocol.IInAppReviewServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo34134a(int i, Parcel parcel) throws RemoteException {
        if (i != 2) {
            return false;
        }
        mo34135a((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
        return true;
    }
}
