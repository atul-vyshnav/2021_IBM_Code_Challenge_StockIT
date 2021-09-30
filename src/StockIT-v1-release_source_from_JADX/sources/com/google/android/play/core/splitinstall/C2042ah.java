package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ah */
final class C2042ah extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f928a;

    /* renamed from: b */
    final /* synthetic */ C2106i f929b;

    /* renamed from: c */
    final /* synthetic */ C2056av f930c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2042ah(C2056av avVar, C2106i iVar, List list, C2106i iVar2) {
        super(iVar);
        this.f930c = avVar;
        this.f928a = list;
        this.f929b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f930c.f946a.mo34152b().mo34174d(this.f930c.f947d, C2056av.m982b((Collection) this.f928a), C2056av.m981b(), new C2049ao(this.f930c, this.f929b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "deferredLanguageInstall(%s)", this.f928a);
            this.f929b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
