package com.google.android.play.core.assetpacks;

import android.content.Context;
import com.google.android.play.core.common.C1907a;
import com.google.android.play.core.common.C1909c;
import com.google.android.play.core.internal.C1977ck;
import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import com.google.android.play.core.splitinstall.C2076p;
import com.google.android.play.core.splitinstall.C2077q;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.br */
public final class C1822br implements C1777a {

    /* renamed from: a */
    private final C1893n f477a;

    /* renamed from: b */
    private C1980cn<Context> f478b;

    /* renamed from: c */
    private C1980cn<C1870dl> f479c;

    /* renamed from: d */
    private C1980cn<C1806bb> f480d;

    /* renamed from: e */
    private C1980cn<C1830bz> f481e;

    /* renamed from: f */
    private C1980cn<C1795ar> f482f;

    /* renamed from: g */
    private C1980cn<String> f483g = C1978cl.m769a(new C1899t(this.f478b));

    /* renamed from: h */
    private C1980cn<C1902w> f484h = new C1977ck();

    /* renamed from: i */
    private C1980cn<Executor> f485i;

    /* renamed from: j */
    private C1980cn<C1847cp> f486j;

    /* renamed from: k */
    private C1980cn<C1800aw> f487k;

    /* renamed from: l */
    private C1980cn<C1824bt> f488l;

    /* renamed from: m */
    private C1980cn<C1880dv> f489m;

    /* renamed from: n */
    private C1980cn<C1864df> f490n;

    /* renamed from: o */
    private C1980cn<C1868dj> f491o;

    /* renamed from: p */
    private C1980cn<C1907a> f492p;

    /* renamed from: q */
    private C1980cn<C1873do> f493q;

    /* renamed from: r */
    private C1980cn<C1815bk> f494r;

    /* renamed from: s */
    private C1980cn<C1850cs> f495s;

    /* renamed from: t */
    private C1980cn<C1827bw> f496t;

    /* renamed from: u */
    private C1980cn<C1818bn> f497u;

    /* renamed from: v */
    private C1980cn<Executor> f498v;

    /* renamed from: w */
    private C1980cn<C1857cz> f499w;

    /* renamed from: x */
    private C1980cn<C2076p> f500x;

    /* renamed from: y */
    private C1980cn<C1888j> f501y;

    /* renamed from: z */
    private C1980cn<AssetPackManager> f502z;

    /* synthetic */ C1822br(C1893n nVar) {
        this.f477a = nVar;
        C1898s sVar = new C1898s(nVar);
        this.f478b = sVar;
        C1980cn<C1870dl> a = C1978cl.m769a(new C1871dm(sVar));
        this.f479c = a;
        this.f480d = C1978cl.m769a(new C1807bc(this.f478b, a));
        C1980cn<C1830bz> a2 = C1978cl.m769a(C1832ca.f550a);
        this.f481e = a2;
        this.f482f = C1978cl.m769a(new C1796as(this.f478b, a2));
        C1980cn<Executor> a3 = C1978cl.m769a(C1894o.f744a);
        this.f485i = a3;
        this.f486j = C1978cl.m769a(new C1848cq(this.f480d, this.f484h, this.f481e, a3));
        C1977ck ckVar = new C1977ck();
        this.f487k = ckVar;
        this.f488l = C1978cl.m769a(new C1825bu(this.f480d, this.f484h, ckVar, this.f481e));
        this.f489m = C1978cl.m769a(new C1881dw(this.f480d));
        this.f490n = C1978cl.m769a(new C1865dg(this.f480d));
        this.f491o = C1978cl.m769a(new C1869dk(this.f480d, this.f484h, this.f486j, this.f485i, this.f481e));
        C1980cn<C1907a> a4 = C1978cl.m769a(C1909c.m597b());
        this.f492p = a4;
        this.f493q = C1978cl.m769a(new C1874dp(this.f480d, this.f484h, a4));
        C1980cn<C1815bk> a5 = C1978cl.m769a(new C1816bl(this.f484h));
        this.f494r = a5;
        C1980cn<C1850cs> a6 = C1978cl.m769a(new C1851ct(this.f486j, this.f480d, a5));
        this.f495s = a6;
        this.f496t = C1978cl.m769a(new C1828bx(this.f486j, this.f484h, this.f488l, this.f489m, this.f490n, this.f491o, this.f493q, a6));
        this.f497u = C1978cl.m769a(C1819bo.f475a);
        C1980cn<Executor> a7 = C1978cl.m769a(C1900u.f752a);
        this.f498v = a7;
        C1977ck.m767a(this.f487k, C1978cl.m769a(new C1801ax(this.f478b, this.f486j, this.f496t, this.f484h, this.f481e, this.f497u, this.f492p, this.f485i, a7)));
        C1980cn<C1857cz> a8 = C1978cl.m769a(new C1859da(this.f483g, this.f487k, this.f481e, this.f478b, this.f479c, this.f485i));
        this.f499w = a8;
        C1977ck.m767a(this.f484h, C1978cl.m769a(new C1897r(this.f478b, this.f482f, a8)));
        C1980cn<C2076p> a9 = C1978cl.m769a(C2077q.m1031a(this.f478b));
        this.f500x = a9;
        C1980cn<C1888j> a10 = C1978cl.m769a(new C1889k(this.f480d, this.f484h, this.f487k, a9, this.f486j, this.f481e, this.f497u, this.f485i));
        this.f501y = a10;
        this.f502z = C1978cl.m769a(new C1896q(a10, this.f478b));
    }

    /* renamed from: a */
    public final AssetPackManager mo33927a() {
        return this.f502z.mo33855a();
    }

    /* renamed from: a */
    public final void mo33928a(AssetPackExtractionService assetPackExtractionService) {
        assetPackExtractionService.f339a = C1898s.m572a(this.f477a);
        assetPackExtractionService.f340b = this.f501y.mo33855a();
        assetPackExtractionService.f341c = this.f480d.mo33855a();
    }
}
