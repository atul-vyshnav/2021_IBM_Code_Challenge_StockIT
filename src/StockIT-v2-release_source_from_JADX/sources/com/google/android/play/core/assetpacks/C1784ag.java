package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.internal.C1998u;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.ag */
final class C1784ag extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f371a;

    /* renamed from: b */
    final /* synthetic */ String f372b;

    /* renamed from: c */
    final /* synthetic */ C2106i f373c;

    /* renamed from: d */
    final /* synthetic */ int f374d;

    /* renamed from: e */
    final /* synthetic */ C1795ar f375e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1784ag(C1795ar arVar, C2106i iVar, int i, String str, C2106i iVar2, int i2) {
        super(iVar);
        this.f375e = arVar;
        this.f371a = i;
        this.f372b = str;
        this.f373c = iVar2;
        this.f374d = i2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f375e.f403e.mo34152b()).mo34232b(this.f375e.f401c, C1795ar.m336c(this.f371a, this.f372b), C1795ar.m341e(), (C1998u) new C1792ao(this.f375e, this.f373c, this.f371a, this.f372b, this.f374d));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "notifyModuleCompleted", new Object[0]);
        }
    }
}
