package com.google.android.play.core.assetpacks;

import com.google.android.play.core.common.C1907a;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1956bq;
import com.google.android.play.core.internal.C1967ca;
import com.google.android.play.core.internal.C1975ci;
import com.google.android.play.core.internal.C1976cj;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

/* renamed from: com.google.android.play.core.assetpacks.do */
final class C1873do {

    /* renamed from: a */
    private static final C1918af f679a = new C1918af("PatchSliceTaskHandler");

    /* renamed from: b */
    private final C1806bb f680b;

    /* renamed from: c */
    private final C1976cj<C1902w> f681c;

    /* renamed from: d */
    private final C1907a f682d;

    C1873do(C1806bb bbVar, C1976cj<C1902w> cjVar, C1907a aVar) {
        this.f680b = bbVar;
        this.f681c = cjVar;
        this.f682d = aVar;
    }

    /* renamed from: a */
    public final void mo34068a(C1872dn dnVar) {
        InputStream inputStream;
        Throwable th;
        C1872dn dnVar2 = dnVar;
        File a = this.f680b.mo33963a(dnVar2.f596k, dnVar2.f671a, dnVar2.f672b);
        C1806bb bbVar = this.f680b;
        String str = dnVar2.f596k;
        int i = dnVar2.f671a;
        long j = dnVar2.f672b;
        File file = new File(bbVar.mo33970b(str, i, j), dnVar2.f676f);
        try {
            inputStream = dnVar2.f678h;
            if (dnVar2.f675e == 2) {
                inputStream = new GZIPInputStream(inputStream, 8192);
            }
            C1809be beVar = new C1809be(a, file);
            if (this.f682d.mo34121a()) {
                File a2 = this.f680b.mo33964a(dnVar2.f596k, dnVar2.f673c, dnVar2.f674d, dnVar2.f676f);
                if (!a2.exists()) {
                    a2.mkdirs();
                }
                C1876dr drVar = new C1876dr(this.f680b, dnVar2.f596k, dnVar2.f673c, dnVar2.f674d, dnVar2.f676f);
                C1956bq.m712a((C1967ca) beVar, inputStream, (OutputStream) new C1829by(a2, drVar), dnVar2.f677g);
                drVar.mo34086b(0);
            } else {
                File file2 = new File(this.f680b.mo33986f(dnVar2.f596k, dnVar2.f673c, dnVar2.f674d, dnVar2.f676f), "slice.zip.tmp");
                if (file2.getParentFile() != null && !file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                }
                C1956bq.m712a((C1967ca) beVar, inputStream, (OutputStream) new FileOutputStream(file2), dnVar2.f677g);
                if (!file2.renameTo(this.f680b.mo33983e(dnVar2.f596k, dnVar2.f673c, dnVar2.f674d, dnVar2.f676f))) {
                    throw new C1826bv(String.format("Error moving patch for slice %s of pack %s.", new Object[]{dnVar2.f676f, dnVar2.f596k}), dnVar2.f595j);
                }
            }
            inputStream.close();
            if (this.f682d.mo34121a()) {
                f679a.mo34142c("Patching and extraction finished for slice %s of pack %s.", dnVar2.f676f, dnVar2.f596k);
            } else {
                f679a.mo34142c("Patching finished for slice %s of pack %s.", dnVar2.f676f, dnVar2.f596k);
            }
            this.f681c.mo34195a().mo33948a(dnVar2.f595j, dnVar2.f596k, dnVar2.f676f, 0);
            try {
                dnVar2.f678h.close();
                return;
            } catch (IOException unused) {
                f679a.mo34143d("Could not close file for slice %s of pack %s.", dnVar2.f676f, dnVar2.f596k);
                return;
            }
        } catch (IOException e) {
            f679a.mo34141b("IOException during patching %s.", e.getMessage());
            throw new C1826bv(String.format("Error patching slice %s of pack %s.", new Object[]{dnVar2.f676f, dnVar2.f596k}), e, dnVar2.f595j);
        } catch (Throwable th2) {
            C1975ci.m765a(th, th2);
        }
        throw th;
    }
}
