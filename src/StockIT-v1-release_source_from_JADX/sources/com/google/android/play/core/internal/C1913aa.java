package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.aa */
public abstract class C1913aa extends C1987j implements C1914ab {
    /* renamed from: a */
    public static C1914ab m604a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.inappreview.protocol.IInAppReviewService");
        return queryLocalInterface instanceof C1914ab ? (C1914ab) queryLocalInterface : new C2003z(iBinder);
    }
}
