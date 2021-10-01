package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1976cj;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.play.core.assetpacks.bw */
final class C1827bw {

    /* renamed from: a */
    private static final C1918af f523a = new C1918af("ExtractorLooper");

    /* renamed from: b */
    private final C1847cp f524b;

    /* renamed from: c */
    private final C1824bt f525c;

    /* renamed from: d */
    private final C1880dv f526d;

    /* renamed from: e */
    private final C1864df f527e;

    /* renamed from: f */
    private final C1868dj f528f;

    /* renamed from: g */
    private final C1873do f529g;

    /* renamed from: h */
    private final C1976cj<C1902w> f530h;

    /* renamed from: i */
    private final C1850cs f531i;

    /* renamed from: j */
    private final AtomicBoolean f532j = new AtomicBoolean(false);

    C1827bw(C1847cp cpVar, C1976cj<C1902w> cjVar, C1824bt btVar, C1880dv dvVar, C1864df dfVar, C1868dj djVar, C1873do doVar, C1850cs csVar) {
        this.f524b = cpVar;
        this.f530h = cjVar;
        this.f525c = btVar;
        this.f526d = dvVar;
        this.f527e = dfVar;
        this.f528f = djVar;
        this.f529g = doVar;
        this.f531i = csVar;
    }

    /* renamed from: a */
    private final void m428a(int i, Exception exc) {
        try {
            this.f524b.mo34044d(i);
            this.f524b.mo34031a(i);
        } catch (C1826bv unused) {
            f523a.mo34141b("Error during error handling: %s", exc.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo34020a() {
        f523a.mo34139a("Run extractor loop", new Object[0]);
        if (this.f532j.compareAndSet(false, true)) {
            while (true) {
                C1849cr crVar = null;
                try {
                    crVar = this.f531i.mo34045a();
                } catch (C1826bv e) {
                    f523a.mo34141b("Error while getting next extraction task: %s", e.getMessage());
                    if (e.f522a >= 0) {
                        this.f530h.mo34195a().mo33946a(e.f522a);
                        m428a(e.f522a, e);
                    }
                }
                if (crVar != null) {
                    try {
                        if (crVar instanceof C1823bs) {
                            this.f525c.mo34019a((C1823bs) crVar);
                        } else if (crVar instanceof C1879du) {
                            this.f526d.mo34091a((C1879du) crVar);
                        } else if (crVar instanceof C1863de) {
                            this.f527e.mo34064a((C1863de) crVar);
                        } else if (crVar instanceof C1866dh) {
                            this.f528f.mo34066a((C1866dh) crVar);
                        } else if (crVar instanceof C1872dn) {
                            this.f529g.mo34068a((C1872dn) crVar);
                        } else {
                            f523a.mo34141b("Unknown task type: %s", crVar.getClass().getName());
                        }
                    } catch (Exception e2) {
                        f523a.mo34141b("Error during extraction task: %s", e2.getMessage());
                        this.f530h.mo34195a().mo33946a(crVar.f595j);
                        m428a(crVar.f595j, e2);
                    }
                } else {
                    this.f532j.set(false);
                    return;
                }
            }
        } else {
            f523a.mo34143d("runLoop already looping; return", new Object[0]);
        }
    }
}
