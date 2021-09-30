package com.google.android.p011a;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.a.a */
/* compiled from: BaseProxy */
public class C1354a implements IInterface {

    /* renamed from: a */
    private final IBinder f114a;

    /* renamed from: b */
    private final String f115b;

    protected C1354a(IBinder iBinder, String str) {
        this.f114a = iBinder;
        this.f115b = str;
    }

    public final IBinder asBinder() {
        return this.f114a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final Parcel mo17426a() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.f115b);
        return obtain;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final Parcel mo17427a(Parcel parcel) throws RemoteException {
        parcel = Parcel.obtain();
        try {
            this.f114a.transact(1, parcel, parcel, 0);
            parcel.readException();
            return parcel;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            parcel.recycle();
        }
    }
}
