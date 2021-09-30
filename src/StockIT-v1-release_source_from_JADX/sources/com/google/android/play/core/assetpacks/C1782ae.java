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

/* renamed from: com.google.android.play.core.assetpacks.ae */
final class C1782ae extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f360a;

    /* renamed from: b */
    final /* synthetic */ Map f361b;

    /* renamed from: c */
    final /* synthetic */ C2106i f362c;

    /* renamed from: d */
    final /* synthetic */ C1803az f363d;

    /* renamed from: e */
    final /* synthetic */ C1795ar f364e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1782ae(C1795ar arVar, C2106i iVar, List list, Map map, C2106i iVar2, C1803az azVar) {
        super(iVar);
        this.f364e = arVar;
        this.f360a = list;
        this.f361b = map;
        this.f362c = iVar2;
        this.f363d = azVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        ArrayList a = C1795ar.m325a((Collection) this.f360a);
        try {
            String a2 = this.f364e.f401c;
            Bundle b = C1795ar.m331b(this.f361b);
            C1795ar arVar = this.f364e;
            ((C1996s) this.f364e.f403e.mo34152b()).mo34236c(a2, (List<Bundle>) a, b, (C1998u) new C1793ap(arVar, this.f362c, arVar.f402d, this.f363d));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "getPackStates(%s)", this.f360a);
            this.f362c.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
