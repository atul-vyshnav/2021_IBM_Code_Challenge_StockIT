package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.aa */
final class C1778aa extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ String f346a;

    /* renamed from: b */
    final /* synthetic */ C2106i f347b;

    /* renamed from: c */
    final /* synthetic */ C1795ar f348c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1778aa(C1795ar arVar, C2106i iVar, String str, C2106i iVar2) {
        super(iVar);
        this.f348c = arVar;
        this.f346a = str;
        this.f347b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f348c.f403e.mo34152b()).mo34238e(this.f348c.f401c, C1795ar.m336c(0, this.f346a), C1795ar.m341e(), new C1788ak(this.f348c, this.f347b, (short[]) null));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "removePack(%s)", this.f346a);
        }
    }
}
