package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.cq */
public final class C1848cq implements C1980cn<C1847cp> {

    /* renamed from: a */
    private final C1980cn<C1806bb> f591a;

    /* renamed from: b */
    private final C1980cn<C1902w> f592b;

    /* renamed from: c */
    private final C1980cn<C1830bz> f593c;

    /* renamed from: d */
    private final C1980cn<Executor> f594d;

    public C1848cq(C1980cn<C1806bb> cnVar, C1980cn<C1902w> cnVar2, C1980cn<C1830bz> cnVar3, C1980cn<Executor> cnVar4) {
        this.f591a = cnVar;
        this.f592b = cnVar2;
        this.f593c = cnVar3;
        this.f594d = cnVar4;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        C1806bb a = this.f591a.mo33855a();
        return new C1847cp(a, C1978cl.m770b(this.f592b), this.f593c.mo33855a(), C1978cl.m770b(this.f594d));
    }
}
