package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.dk */
public final class C1869dk implements C1980cn<C1868dj> {

    /* renamed from: a */
    private final C1980cn<C1806bb> f662a;

    /* renamed from: b */
    private final C1980cn<C1902w> f663b;

    /* renamed from: c */
    private final C1980cn<C1847cp> f664c;

    /* renamed from: d */
    private final C1980cn<Executor> f665d;

    /* renamed from: e */
    private final C1980cn<C1830bz> f666e;

    public C1869dk(C1980cn<C1806bb> cnVar, C1980cn<C1902w> cnVar2, C1980cn<C1847cp> cnVar3, C1980cn<Executor> cnVar4, C1980cn<C1830bz> cnVar5) {
        this.f662a = cnVar;
        this.f663b = cnVar2;
        this.f664c = cnVar3;
        this.f665d = cnVar4;
        this.f666e = cnVar5;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        C1806bb a = this.f662a.mo33855a();
        return new C1868dj(a, C1978cl.m770b(this.f663b), this.f664c.mo33855a(), C1978cl.m770b(this.f665d), this.f666e.mo33855a());
    }
}
