package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.m */
public abstract class C1990m extends C1987j implements C1991n {
    /* renamed from: a */
    public static C1991n m807a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.appupdate.protocol.IAppUpdateService");
        return queryLocalInterface instanceof C1991n ? (C1991n) queryLocalInterface : new C1989l(iBinder);
    }
}
