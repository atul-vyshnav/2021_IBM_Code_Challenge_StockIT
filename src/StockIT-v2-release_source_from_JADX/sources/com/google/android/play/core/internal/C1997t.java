package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.t */
public abstract class C1997t extends C1987j implements C1998u {
    public C1997t() {
        super("com.google.android.play.core.assetpacks.protocol.IAssetModuleServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo34134a(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 2:
                mo33931a(parcel.readInt(), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 3:
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33930a(readInt);
                return true;
            case 4:
                int readInt2 = parcel.readInt();
                Bundle bundle2 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33936b(readInt2);
                return true;
            case 5:
                mo33934a((List<Bundle>) parcel.createTypedArrayList(Bundle.CREATOR));
                return true;
            case 6:
                Bundle bundle3 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33937b((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 7:
                mo33932a((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 8:
                Bundle bundle4 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33939c((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 10:
                Bundle bundle5 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33941d((Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 11:
                mo33933a((Bundle) C1988k.m802a(parcel, Bundle.CREATOR), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 12:
                mo33938b((Bundle) C1988k.m802a(parcel, Bundle.CREATOR), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 13:
                mo33940c((Bundle) C1988k.m802a(parcel, Bundle.CREATOR), (Bundle) C1988k.m802a(parcel, Bundle.CREATOR));
                return true;
            case 14:
                Bundle bundle6 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                Bundle bundle7 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33935b();
                return true;
            case 15:
                Bundle bundle8 = (Bundle) C1988k.m802a(parcel, Bundle.CREATOR);
                mo33929a();
                return true;
            default:
                return false;
        }
    }
}
