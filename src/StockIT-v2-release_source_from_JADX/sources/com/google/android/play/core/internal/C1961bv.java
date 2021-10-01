package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.bv */
public abstract class C1961bv extends C1987j implements C1962bw {
    public C1961bv() {
        super("com.google.android.play.core.splitinstall.protocol.ISplitInstallServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo34134a(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 2:
                mo34184c(parcel.readInt(), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 3:
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo34177a(readInt);
                return true;
            case 4:
                mo34178a(parcel.readInt(), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 5:
                mo34182b(parcel.readInt(), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 6:
                mo34187e((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 7:
                mo34180a((List<Bundle>) parcel.createTypedArrayList(Bundle.CREATOR));
                return true;
            case 8:
                mo34186d((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 9:
                mo34179a((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 10:
                Bundle bundle2 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo34181b();
                return true;
            case 11:
                Bundle bundle3 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo34176a();
                return true;
            case 12:
                mo34183b((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 13:
                mo34185c((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
