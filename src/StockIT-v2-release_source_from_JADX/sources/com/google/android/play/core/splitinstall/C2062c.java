package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import com.google.android.play.core.splitinstall.testing.C2091k;
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManager;
import java.io.File;

/* renamed from: com.google.android.play.core.splitinstall.c */
public final class C2062c implements C2072m {

    /* renamed from: a */
    private C1980cn<Context> f954a;

    /* renamed from: b */
    private C1980cn<C2056av> f955b;

    /* renamed from: c */
    private C1980cn<C2080t> f956c;

    /* renamed from: d */
    private C1980cn<C2076p> f957d = C1978cl.m769a(C2077q.m1031a(this.f954a));

    /* renamed from: e */
    private C1980cn<C2058ax> f958e;

    /* renamed from: f */
    private C1980cn<C2094w> f959f;

    /* renamed from: g */
    private C1980cn<File> f960g;

    /* renamed from: h */
    private C1980cn<FakeSplitInstallManager> f961h;

    /* renamed from: i */
    private C1980cn<C2068i> f962i;

    /* renamed from: j */
    private C1980cn<SplitInstallManager> f963j;

    /* synthetic */ C2062c(C2096y yVar) {
        C2097z zVar = new C2097z(yVar);
        this.f954a = zVar;
        this.f955b = C1978cl.m769a(new C2057aw(zVar));
        this.f956c = C1978cl.m769a(new C2036ab(yVar));
        C1980cn<C2058ax> a = C1978cl.m769a(new C2059ay(this.f954a));
        this.f958e = a;
        this.f959f = C1978cl.m769a(new C2095x(this.f955b, this.f956c, this.f957d, a));
        C1980cn<File> a2 = C1978cl.m769a(new C2035aa(this.f954a));
        this.f960g = a2;
        C1980cn<FakeSplitInstallManager> a3 = C1978cl.m769a(new C2091k(this.f954a, a2, this.f957d));
        this.f961h = a3;
        C1980cn<C2068i> a4 = C1978cl.m769a(new C2069j(this.f959f, a3, this.f960g));
        this.f962i = a4;
        this.f963j = C1978cl.m769a(new C2037ac(yVar, a4));
    }

    /* renamed from: a */
    public final SplitInstallManager mo34346a() {
        return this.f963j.mo33855a();
    }

    /* renamed from: b */
    public final File mo34347b() {
        return this.f960g.mo33855a();
    }
}
