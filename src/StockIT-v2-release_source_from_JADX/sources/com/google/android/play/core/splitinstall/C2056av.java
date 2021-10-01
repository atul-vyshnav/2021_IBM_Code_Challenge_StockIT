package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1928ap;
import com.google.android.play.core.internal.C1960bu;
import com.google.android.play.core.internal.C1964by;
import com.google.android.play.core.splitcompat.C2032p;
import com.google.android.play.core.tasks.C2106i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.av */
final class C2056av {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final C1918af f944b = new C1918af("SplitInstallService");

    /* renamed from: c */
    private static final Intent f945c = new Intent("com.google.android.play.core.splitinstall.BIND_SPLIT_INSTALL_SERVICE").setPackage("com.android.vending");

    /* renamed from: a */
    C1928ap<C1960bu> f946a;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final String f947d;

    public C2056av(Context context) {
        this.f947d = context.getPackageName();
        if (C1964by.m751a(context)) {
            this.f946a = new C1928ap(C2032p.m933a(context), f944b, "SplitInstallService", f945c, C2038ad.f917a);
        }
    }

    /* renamed from: a */
    static /* synthetic */ ArrayList m980a(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("module_name", (String) it.next());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* renamed from: b */
    static /* synthetic */ Bundle m981b() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 10800);
        return bundle;
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [java.util.List, java.util.Collection] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.util.ArrayList m982b(java.util.Collection r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r6.size()
            r0.<init>(r1)
            int r1 = r6.size()
            r2 = 0
        L_0x000e:
            if (r2 >= r1) goto L_0x0026
            java.lang.Object r3 = r6.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            java.lang.String r5 = "language"
            r4.putString(r5, r3)
            r0.add(r4)
            int r2 = r2 + 1
            goto L_0x000e
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.splitinstall.C2056av.m982b(java.util.Collection):java.util.ArrayList");
    }

    /* renamed from: d */
    private static <T> Task<T> m984d() {
        f944b.mo34141b("onError(%d)", -14);
        return Tasks.m1078a((Exception) new SplitInstallException(-14));
    }

    /* renamed from: a */
    public final Task<List<SplitInstallSessionState>> mo34333a() {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("getSessionStates", new Object[0]);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2045ak(this, iVar, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<SplitInstallSessionState> mo34334a(int i) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("getSessionState(%d)", Integer.valueOf(i));
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2044aj(this, iVar, i, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<Integer> mo34335a(Collection<String> collection, Collection<String> collection2) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("startInstall(%s,%s)", collection, collection2);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2039ae(this, iVar, collection, collection2, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: a */
    public final Task<Void> mo34336a(List<String> list) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("deferredUninstall(%s)", list);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2040af(this, iVar, list, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: b */
    public final Task<Void> mo34337b(int i) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("cancelInstall(%d)", Integer.valueOf(i));
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2046al(this, iVar, i, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: b */
    public final Task<Void> mo34338b(List<String> list) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("deferredInstall(%s)", list);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2041ag(this, iVar, list, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: c */
    public final Task<Void> mo34339c(List<String> list) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("deferredLanguageInstall(%s)", list);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2042ah(this, iVar, list, iVar));
        return iVar.mo34394a();
    }

    /* renamed from: d */
    public final Task<Void> mo34340d(List<String> list) {
        if (this.f946a == null) {
            return m984d();
        }
        f944b.mo34142c("deferredLanguageUninstall(%s)", list);
        C2106i iVar = new C2106i();
        this.f946a.mo34151a((C1919ag) new C2043ai(this, iVar, list, iVar));
        return iVar.mo34394a();
    }
}
