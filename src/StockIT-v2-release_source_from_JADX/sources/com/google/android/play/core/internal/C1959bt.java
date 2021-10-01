package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.bt */
public abstract class C1959bt extends C1987j implements C1960bu {
    /* renamed from: a */
    public static C1960bu m727a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.splitinstall.protocol.ISplitInstallService");
        return queryLocalInterface instanceof C1960bu ? (C1960bu) queryLocalInterface : new C1958bs(iBinder);
    }
}
