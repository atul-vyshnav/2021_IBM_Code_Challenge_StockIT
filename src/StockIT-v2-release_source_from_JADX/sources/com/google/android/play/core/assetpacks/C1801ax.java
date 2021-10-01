package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.common.C1907a;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.ax */
public final class C1801ax implements C1980cn<C1800aw> {

    /* renamed from: a */
    private final C1980cn<Context> f424a;

    /* renamed from: b */
    private final C1980cn<C1847cp> f425b;

    /* renamed from: c */
    private final C1980cn<C1827bw> f426c;

    /* renamed from: d */
    private final C1980cn<C1902w> f427d;

    /* renamed from: e */
    private final C1980cn<C1830bz> f428e;

    /* renamed from: f */
    private final C1980cn<C1818bn> f429f;

    /* renamed from: g */
    private final C1980cn<C1907a> f430g;

    /* renamed from: h */
    private final C1980cn<Executor> f431h;

    /* renamed from: i */
    private final C1980cn<Executor> f432i;

    public C1801ax(C1980cn<Context> cnVar, C1980cn<C1847cp> cnVar2, C1980cn<C1827bw> cnVar3, C1980cn<C1902w> cnVar4, C1980cn<C1830bz> cnVar5, C1980cn<C1818bn> cnVar6, C1980cn<C1907a> cnVar7, C1980cn<Executor> cnVar8, C1980cn<Executor> cnVar9) {
        this.f424a = cnVar;
        this.f425b = cnVar2;
        this.f426c = cnVar3;
        this.f427d = cnVar4;
        this.f428e = cnVar5;
        this.f429f = cnVar6;
        this.f430g = cnVar7;
        this.f431h = cnVar8;
        this.f432i = cnVar9;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        Context b = ((C1898s) this.f424a).mo33855a();
        C1847cp a = this.f425b.mo33855a();
        C1827bw a2 = this.f426c.mo33855a();
        C1976cj<C1902w> b2 = C1978cl.m770b(this.f427d);
        C1830bz a3 = this.f428e.mo33855a();
        C1818bn a4 = this.f429f.mo33855a();
        return new C1800aw(b, a, a2, b2, a3, a4, this.f430g.mo33855a(), C1978cl.m770b(this.f431h), C1978cl.m770b(this.f432i));
    }
}
