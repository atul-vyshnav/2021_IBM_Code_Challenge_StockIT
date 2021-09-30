package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1918af;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.google.android.play.core.assetpacks.dv */
final class C1880dv {

    /* renamed from: a */
    private static final C1918af f705a = new C1918af("VerifySliceTaskHandler");

    /* renamed from: b */
    private final C1806bb f706b;

    C1880dv(C1806bb bbVar) {
        this.f706b = bbVar;
    }

    /* renamed from: a */
    private final void m541a(C1879du duVar, File file) {
        try {
            File f = this.f706b.mo33986f(duVar.f596k, duVar.f701a, duVar.f702b, duVar.f703c);
            if (f.exists()) {
                try {
                    if (C1860db.m496a(C1878dt.m539a(file, f)).equals(duVar.f704d)) {
                        f705a.mo34142c("Verification of slice %s of pack %s successful.", duVar.f703c, duVar.f596k);
                        return;
                    }
                    throw new C1826bv(String.format("Verification failed for slice %s.", new Object[]{duVar.f703c}), duVar.f595j);
                } catch (NoSuchAlgorithmException e) {
                    throw new C1826bv("SHA256 algorithm not supported.", e, duVar.f595j);
                } catch (IOException e2) {
                    throw new C1826bv(String.format("Could not digest file during verification for slice %s.", new Object[]{duVar.f703c}), e2, duVar.f595j);
                }
            } else {
                throw new C1826bv(String.format("Cannot find metadata files for slice %s.", new Object[]{duVar.f703c}), duVar.f595j);
            }
        } catch (IOException e3) {
            throw new C1826bv(String.format("Could not reconstruct slice archive during verification for slice %s.", new Object[]{duVar.f703c}), e3, duVar.f595j);
        }
    }

    /* renamed from: a */
    public final void mo34091a(C1879du duVar) {
        File a = this.f706b.mo33964a(duVar.f596k, duVar.f701a, duVar.f702b, duVar.f703c);
        if (a.exists()) {
            m541a(duVar, a);
            File b = this.f706b.mo33971b(duVar.f596k, duVar.f701a, duVar.f702b, duVar.f703c);
            if (!b.exists()) {
                b.mkdirs();
            }
            if (!a.renameTo(b)) {
                throw new C1826bv(String.format("Failed to move slice %s after verification.", new Object[]{duVar.f703c}), duVar.f595j);
            }
            return;
        }
        throw new C1826bv(String.format("Cannot find unverified files for slice %s.", new Object[]{duVar.f703c}), duVar.f595j);
    }
}
