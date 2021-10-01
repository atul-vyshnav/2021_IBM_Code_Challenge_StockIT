package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.tasks.C2106i;
import java.util.Map;

/* renamed from: com.google.android.play.core.assetpacks.ad */
final class C1781ad extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ Map f357a;

    /* renamed from: b */
    final /* synthetic */ C2106i f358b;

    /* renamed from: c */
    final /* synthetic */ C1795ar f359c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1781ad(C1795ar arVar, C2106i iVar, Map map, C2106i iVar2) {
        super(iVar);
        this.f359c = arVar;
        this.f357a = map;
        this.f358b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f359c.f403e.mo34152b()).mo34230a(this.f359c.f401c, C1795ar.m331b(this.f357a), new C1790am(this.f359c, this.f358b));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "syncPacks", new Object[0]);
            this.f358b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
