package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.splitcompat.C2032p;

/* renamed from: com.google.android.play.core.splitinstall.k */
public final class C2070k {

    /* renamed from: a */
    private static C2072m f972a;

    private C2070k() {
    }

    /* renamed from: a */
    public static synchronized C2072m m1015a(Context context) {
        C2072m mVar;
        synchronized (C2070k.class) {
            if (f972a == null) {
                C2061b bVar = new C2061b((byte[]) null);
                bVar.mo34345a(new C2096y(C2032p.m933a(context)));
                f972a = bVar.mo34344a();
            }
            mVar = f972a;
        }
        return mVar;
    }
}
