package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.aj */
final class C1787aj extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ C2106i f385a;

    /* renamed from: b */
    final /* synthetic */ C1795ar f386b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1787aj(C1795ar arVar, C2106i iVar, C2106i iVar2) {
        super(iVar);
        this.f386b = arVar;
        this.f385a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f386b.f404f.mo34152b()).mo34233b(this.f386b.f401c, C1795ar.m341e(), new C1791an(this.f386b, this.f385a));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "keepAlive", new Object[0]);
        }
    }
}
