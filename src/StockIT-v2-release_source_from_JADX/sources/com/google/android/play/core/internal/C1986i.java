package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.play.core.internal.i */
public class C1986i implements IInterface {

    /* renamed from: a */
    private final IBinder f833a;

    /* renamed from: b */
    private final String f834b;

    protected C1986i(IBinder iBinder, String str) {
        this.f833a = iBinder;
        this.f834b = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final Parcel mo34222a() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.f834b);
        return obtain;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo34223a(int i, Parcel parcel) throws RemoteException {
        try {
            this.f833a.transact(i, parcel, (Parcel) null, 1);
        } finally {
            parcel.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.f833a;
    }
}
