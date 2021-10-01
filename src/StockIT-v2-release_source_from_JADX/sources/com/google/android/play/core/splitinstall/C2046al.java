package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1962bw;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.splitinstall.al */
final class C2046al extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f939a;

    /* renamed from: b */
    final /* synthetic */ C2106i f940b;

    /* renamed from: c */
    final /* synthetic */ C2056av f941c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2046al(C2056av avVar, C2106i iVar, int i, C2106i iVar2) {
        super(iVar);
        this.f941c = avVar;
        this.f939a = i;
        this.f940b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f941c.f946a.mo34152b().mo34168a(this.f941c.f947d, this.f939a, C2056av.m981b(), (C1962bw) new C2047am(this.f941c, this.f940b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "cancelInstall(%d)", Integer.valueOf(this.f939a));
            this.f940b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
