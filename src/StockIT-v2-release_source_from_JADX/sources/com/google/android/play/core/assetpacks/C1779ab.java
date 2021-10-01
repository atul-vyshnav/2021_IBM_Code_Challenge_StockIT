package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.internal.C1998u;
import com.google.android.play.core.tasks.C2106i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.ab */
final class C1779ab extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f349a;

    /* renamed from: b */
    final /* synthetic */ Map f350b;

    /* renamed from: c */
    final /* synthetic */ C2106i f351c;

    /* renamed from: d */
    final /* synthetic */ List f352d;

    /* renamed from: e */
    final /* synthetic */ C1795ar f353e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1779ab(C1795ar arVar, C2106i iVar, List list, Map map, C2106i iVar2, List list2) {
        super(iVar);
        this.f353e = arVar;
        this.f349a = list;
        this.f350b = map;
        this.f351c = iVar2;
        this.f352d = list2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        ArrayList a = C1795ar.m325a((Collection) this.f349a);
        try {
            String a2 = this.f353e.f401c;
            Bundle b = C1795ar.m331b(this.f350b);
            C1795ar arVar = this.f353e;
            ((C1996s) this.f353e.f403e.mo34152b()).mo34231a(a2, (List<Bundle>) a, b, (C1998u) new C1794aq(arVar, this.f351c, arVar.f402d, this.f352d));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "startDownload(%s)", this.f349a);
            this.f351c.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
