package com.google.android.play.core.appupdate;

import android.content.Context;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;

/* renamed from: com.google.android.play.core.appupdate.y */
public final class C1776y {

    /* renamed from: a */
    private C1980cn<Context> f332a;

    /* renamed from: b */
    private C1980cn<C1769r> f333b;

    /* renamed from: c */
    private C1980cn<C1767p> f334c;

    /* renamed from: d */
    private C1980cn<C1752a> f335d;

    /* renamed from: e */
    private C1980cn<C1756e> f336e;

    /* renamed from: f */
    private C1980cn<AppUpdateManager> f337f;

    /* synthetic */ C1776y(C1758g gVar) {
        C1760i iVar = new C1760i(gVar);
        this.f332a = iVar;
        C1980cn<C1769r> a = C1978cl.m769a(new C1770s(iVar));
        this.f333b = a;
        this.f334c = C1978cl.m769a(new C1768q(this.f332a, a));
        C1980cn<C1752a> a2 = C1978cl.m769a(new C1753b(this.f332a));
        this.f335d = a2;
        C1980cn<C1756e> a3 = C1978cl.m769a(new C1757f(this.f334c, a2, this.f332a));
        this.f336e = a3;
        this.f337f = C1978cl.m769a(new C1759h(a3));
    }

    /* renamed from: a */
    public final AppUpdateManager mo33894a() {
        return this.f337f.mo33855a();
    }
}
