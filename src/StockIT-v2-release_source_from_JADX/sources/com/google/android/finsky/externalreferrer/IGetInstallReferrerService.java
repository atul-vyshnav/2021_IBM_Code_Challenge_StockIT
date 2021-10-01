package com.google.android.finsky.externalreferrer;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.p011a.C1354a;
import com.google.android.p011a.C1355b;
import com.google.android.p011a.C1356c;

public interface IGetInstallReferrerService extends IInterface {

    public static abstract class Stub extends C1355b implements IGetInstallReferrerService {

        public static class Proxy extends C1354a implements IGetInstallReferrerService {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
            }

            /* renamed from: a */
            public final Bundle mo20334a(Bundle bundle) throws RemoteException {
                Parcel a = mo17426a();
                C1356c.m156a(a, (Parcelable) bundle);
                Parcel a2 = mo17427a(a);
                Bundle bundle2 = (Bundle) C1356c.m155a(a2, Bundle.CREATOR);
                a2.recycle();
                return bundle2;
            }
        }

        public Stub() {
            super("com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
        }

        /* renamed from: a */
        public static IGetInstallReferrerService m181a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.finsky.externalreferrer.IGetInstallReferrerService");
            if (queryLocalInterface instanceof IGetInstallReferrerService) {
                return (IGetInstallReferrerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }
    }

    /* renamed from: a */
    Bundle mo20334a(Bundle bundle) throws RemoteException;
}
