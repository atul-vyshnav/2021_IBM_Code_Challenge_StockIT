package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1976cj;
import java.io.File;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.dj */
final class C1868dj {

    /* renamed from: a */
    private final C1806bb f657a;

    /* renamed from: b */
    private final C1976cj<C1902w> f658b;

    /* renamed from: c */
    private final C1847cp f659c;

    /* renamed from: d */
    private final C1976cj<Executor> f660d;

    /* renamed from: e */
    private final C1830bz f661e;

    C1868dj(C1806bb bbVar, C1976cj<C1902w> cjVar, C1847cp cpVar, C1976cj<Executor> cjVar2, C1830bz bzVar) {
        this.f657a = bbVar;
        this.f658b = cjVar;
        this.f659c = cpVar;
        this.f660d = cjVar2;
        this.f661e = bzVar;
    }

    /* renamed from: a */
    public final void mo34066a(C1866dh dhVar) {
        File c = this.f657a.mo33973c(dhVar.f596k, dhVar.f654a, dhVar.f655b);
        File e = this.f657a.mo33982e(dhVar.f596k, dhVar.f654a, dhVar.f655b);
        if (!c.exists() || !e.exists()) {
            throw new C1826bv(String.format("Cannot find pack files to move for pack %s.", new Object[]{dhVar.f596k}), dhVar.f595j);
        }
        File a = this.f657a.mo33963a(dhVar.f596k, dhVar.f654a, dhVar.f655b);
        a.mkdirs();
        if (c.renameTo(a)) {
            new File(this.f657a.mo33963a(dhVar.f596k, dhVar.f654a, dhVar.f655b), "merge.tmp").delete();
            File b = this.f657a.mo33970b(dhVar.f596k, dhVar.f654a, dhVar.f655b);
            b.mkdirs();
            if (e.renameTo(b)) {
                C1806bb bbVar = this.f657a;
                bbVar.getClass();
                this.f660d.mo34195a().execute(C1867di.m512a(bbVar));
                this.f659c.mo34032a(dhVar.f596k, dhVar.f654a, dhVar.f655b);
                this.f661e.mo34025a(dhVar.f596k);
                this.f658b.mo34195a().mo33947a(dhVar.f595j, dhVar.f596k);
                return;
            }
            throw new C1826bv("Cannot move metadata files to final location.", dhVar.f595j);
        }
        throw new C1826bv("Cannot move merged pack files to final location.", dhVar.f595j);
    }
}
