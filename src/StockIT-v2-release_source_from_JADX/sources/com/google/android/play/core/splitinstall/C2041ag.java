package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ag */
final class C2041ag extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f925a;

    /* renamed from: b */
    final /* synthetic */ C2106i f926b;

    /* renamed from: c */
    final /* synthetic */ C2056av f927c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2041ag(C2056av avVar, C2106i iVar, List list, C2106i iVar2) {
        super(iVar);
        this.f927c = avVar;
        this.f925a = list;
        this.f926b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f927c.f946a.mo34152b().mo34173c(this.f927c.f947d, C2056av.m980a((Collection) this.f925a), C2056av.m981b(), new C2048an(this.f927c, this.f926b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "deferredInstall(%s)", this.f925a);
            this.f926b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
