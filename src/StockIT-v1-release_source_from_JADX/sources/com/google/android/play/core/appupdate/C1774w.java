package com.google.android.play.core.appupdate;

import android.content.Context;
import com.google.android.play.core.splitcompat.C2032p;

/* renamed from: com.google.android.play.core.appupdate.w */
public final /* synthetic */ class C1774w {

    /* renamed from: a */
    private static C1776y f330a;

    /* renamed from: a */
    static synchronized C1776y m276a(Context context) {
        C1776y yVar;
        synchronized (C1774w.class) {
            if (f330a == null) {
                C1775x xVar = new C1775x((byte[]) null);
                xVar.mo33893a(new C1758g(C2032p.m933a(context)));
                f330a = xVar.mo33892a();
            }
            yVar = f330a;
        }
        return yVar;
    }
}
