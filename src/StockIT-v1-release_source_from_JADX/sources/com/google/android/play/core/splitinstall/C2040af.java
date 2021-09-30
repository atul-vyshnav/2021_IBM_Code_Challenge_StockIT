package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.af */
final class C2040af extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f922a;

    /* renamed from: b */
    final /* synthetic */ C2106i f923b;

    /* renamed from: c */
    final /* synthetic */ C2056av f924c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2040af(C2056av avVar, C2106i iVar, List list, C2106i iVar2) {
        super(iVar);
        this.f924c = avVar;
        this.f922a = list;
        this.f923b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f924c.f946a.mo34152b().mo34172b(this.f924c.f947d, C2056av.m980a((Collection) this.f922a), C2056av.m981b(), new C2051aq(this.f924c, this.f923b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "deferredUninstall(%s)", this.f922a);
            this.f923b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
