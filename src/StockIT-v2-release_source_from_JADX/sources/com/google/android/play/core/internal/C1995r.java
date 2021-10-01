package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.r */
public abstract class C1995r extends C1987j implements C1996s {
    /* renamed from: a */
    public static C1996s m823a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.assetpacks.protocol.IAssetModuleService");
        return queryLocalInterface instanceof C1996s ? (C1996s) queryLocalInterface : new C1994q(iBinder);
    }
}
