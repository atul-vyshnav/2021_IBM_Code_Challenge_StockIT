package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.internal.C1998u;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.ah */
final class C1785ah extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f376a;

    /* renamed from: b */
    final /* synthetic */ C2106i f377b;

    /* renamed from: c */
    final /* synthetic */ C1795ar f378c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1785ah(C1795ar arVar, C2106i iVar, int i, C2106i iVar2) {
        super(iVar);
        this.f378c = arVar;
        this.f376a = i;
        this.f377b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f378c.f403e.mo34152b()).mo34235c(this.f378c.f401c, C1795ar.m335c(this.f376a), C1795ar.m341e(), (C1998u) new C1788ak(this.f378c, this.f377b, (int[]) null));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "notifySessionFailed", new Object[0]);
        }
    }
}
