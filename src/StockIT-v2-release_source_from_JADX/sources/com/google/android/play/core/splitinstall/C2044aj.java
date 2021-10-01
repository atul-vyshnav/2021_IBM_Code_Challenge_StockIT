package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.splitinstall.aj */
final class C2044aj extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f934a;

    /* renamed from: b */
    final /* synthetic */ C2106i f935b;

    /* renamed from: c */
    final /* synthetic */ C2056av f936c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2044aj(C2056av avVar, C2106i iVar, int i, C2106i iVar2) {
        super(iVar);
        this.f936c = avVar;
        this.f934a = i;
        this.f935b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f936c.f946a.mo34152b().mo34169a(this.f936c.f947d, this.f934a, new C2052ar(this.f936c, this.f935b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "getSessionState(%d)", Integer.valueOf(this.f934a));
            this.f935b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
