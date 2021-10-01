package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import com.google.android.play.core.splitinstall.C2076p;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.k */
public final class C1889k implements C1980cn<C1888j> {

    /* renamed from: a */
    private final C1980cn<C1806bb> f731a;

    /* renamed from: b */
    private final C1980cn<C1902w> f732b;

    /* renamed from: c */
    private final C1980cn<C1800aw> f733c;

    /* renamed from: d */
    private final C1980cn<C2076p> f734d;

    /* renamed from: e */
    private final C1980cn<C1847cp> f735e;

    /* renamed from: f */
    private final C1980cn<C1830bz> f736f;

    /* renamed from: g */
    private final C1980cn<C1818bn> f737g;

    /* renamed from: h */
    private final C1980cn<Executor> f738h;

    public C1889k(C1980cn<C1806bb> cnVar, C1980cn<C1902w> cnVar2, C1980cn<C1800aw> cnVar3, C1980cn<C2076p> cnVar4, C1980cn<C1847cp> cnVar5, C1980cn<C1830bz> cnVar6, C1980cn<C1818bn> cnVar7, C1980cn<Executor> cnVar8) {
        this.f731a = cnVar;
        this.f732b = cnVar2;
        this.f733c = cnVar3;
        this.f734d = cnVar4;
        this.f735e = cnVar5;
        this.f736f = cnVar6;
        this.f737g = cnVar7;
        this.f738h = cnVar8;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        C1806bb a = this.f731a.mo33855a();
        C1976cj<C1902w> b = C1978cl.m770b(this.f732b);
        C1800aw a2 = this.f733c.mo33855a();
        return new C1888j(a, b, a2, this.f734d.mo33855a(), this.f735e.mo33855a(), this.f736f.mo33855a(), this.f737g.mo33855a(), C1978cl.m770b(this.f738h));
    }
}
