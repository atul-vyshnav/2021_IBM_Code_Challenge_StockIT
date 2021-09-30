package com.google.android.play.core.splitinstall;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ai */
final class C2043ai extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f931a;

    /* renamed from: b */
    final /* synthetic */ C2106i f932b;

    /* renamed from: c */
    final /* synthetic */ C2056av f933c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2043ai(C2056av avVar, C2106i iVar, List list, C2106i iVar2) {
        super(iVar);
        this.f933c = avVar;
        this.f931a = list;
        this.f932b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f933c.f946a.mo34152b().mo34175e(this.f933c.f947d, C2056av.m982b((Collection) this.f931a), C2056av.m981b(), new C2050ap(this.f933c, this.f932b));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "deferredLanguageUninstall(%s)", this.f931a);
            this.f932b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
