package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1918af;
import java.io.File;
import java.io.IOException;

/* renamed from: com.google.android.play.core.assetpacks.df */
final class C1864df {

    /* renamed from: a */
    private static final C1918af f651a = new C1918af("MergeSliceTaskHandler");

    /* renamed from: b */
    private final C1806bb f652b;

    C1864df(C1806bb bbVar) {
        this.f652b = bbVar;
    }

    /* renamed from: a */
    private static void m509a(File file, File file2) {
        if (file.isDirectory()) {
            file2.mkdirs();
            for (File file3 : file.listFiles()) {
                m509a(file3, new File(file2, file3.getName()));
            }
            if (!file.delete()) {
                String valueOf = String.valueOf(file);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 28);
                sb.append("Unable to delete directory: ");
                sb.append(valueOf);
                throw new C1826bv(sb.toString());
            }
        } else if (file2.exists()) {
            String valueOf2 = String.valueOf(file2);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 51);
            sb2.append("File clashing with existing file from other slice: ");
            sb2.append(valueOf2);
            throw new C1826bv(sb2.toString());
        } else if (!file.renameTo(file2)) {
            String valueOf3 = String.valueOf(file);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 21);
            sb3.append("Unable to move file: ");
            sb3.append(valueOf3);
            throw new C1826bv(sb3.toString());
        }
    }

    /* renamed from: a */
    public final void mo34064a(C1863de deVar) {
        File b = this.f652b.mo33971b(deVar.f596k, deVar.f648a, deVar.f649b, deVar.f650c);
        if (b.exists()) {
            File c = this.f652b.mo33973c(deVar.f596k, deVar.f648a, deVar.f649b);
            if (!c.exists()) {
                c.mkdirs();
            }
            m509a(b, c);
            try {
                this.f652b.mo33966a(deVar.f596k, deVar.f648a, deVar.f649b, this.f652b.mo33977d(deVar.f596k, deVar.f648a, deVar.f649b) + 1);
            } catch (IOException e) {
                f651a.mo34141b("Writing merge checkpoint failed with %s.", e.getMessage());
                throw new C1826bv("Writing merge checkpoint failed.", e, deVar.f595j);
            }
        } else {
            throw new C1826bv(String.format("Cannot find verified files for slice %s.", new Object[]{deVar.f650c}), deVar.f595j);
        }
    }
}
