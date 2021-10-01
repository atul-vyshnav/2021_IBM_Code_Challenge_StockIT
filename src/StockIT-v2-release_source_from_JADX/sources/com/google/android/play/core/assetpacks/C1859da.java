package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import java.io.File;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.da */
public final class C1859da implements C1980cn<C1857cz> {

    /* renamed from: a */
    private final C1980cn<String> f630a;

    /* renamed from: b */
    private final C1980cn<C1800aw> f631b;

    /* renamed from: c */
    private final C1980cn<C1830bz> f632c;

    /* renamed from: d */
    private final C1980cn<Context> f633d;

    /* renamed from: e */
    private final C1980cn<C1870dl> f634e;

    /* renamed from: f */
    private final C1980cn<Executor> f635f;

    public C1859da(C1980cn<String> cnVar, C1980cn<C1800aw> cnVar2, C1980cn<C1830bz> cnVar3, C1980cn<Context> cnVar4, C1980cn<C1870dl> cnVar5, C1980cn<Executor> cnVar6) {
        this.f630a = cnVar;
        this.f631b = cnVar2;
        this.f632c = cnVar3;
        this.f633d = cnVar4;
        this.f634e = cnVar5;
        this.f635f = cnVar6;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        String a = this.f630a.mo33855a();
        C1800aw a2 = this.f631b.mo33855a();
        C1830bz a3 = this.f632c.mo33855a();
        Context b = ((C1898s) this.f633d).mo33855a();
        C1870dl a4 = this.f634e.mo33855a();
        return new C1857cz(a != null ? new File(b.getExternalFilesDir((String) null), a) : b.getExternalFilesDir((String) null), a2, a3, b, a4, C1978cl.m770b(this.f635f));
    }
}
