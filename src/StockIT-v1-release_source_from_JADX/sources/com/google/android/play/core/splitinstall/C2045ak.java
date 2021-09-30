package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.splitinstall.ak */
final class C2045ak extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ C2106i f937a;

    /* renamed from: b */
    final /* synthetic */ C2056av f938b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2045ak(C2056av avVar, C2106i iVar, C2106i iVar2) {
        super(iVar);
        this.f938b = avVar;
        this.f937a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f938b.f946a.mo34152b().mo34170a(this.f938b.f947d, new C2053as(this.f938b, this.f937a));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "getSessionStates", new Object[0]);
            this.f937a.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
