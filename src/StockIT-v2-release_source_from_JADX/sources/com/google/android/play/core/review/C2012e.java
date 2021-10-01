package com.google.android.play.core.review;

import android.os.RemoteException;
import com.google.android.play.core.common.PlayCoreVersion;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.review.e */
final class C2012e extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ C2106i f856a;

    /* renamed from: b */
    final /* synthetic */ C2015h f857b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2012e(C2015h hVar, C2106i iVar, C2106i iVar2) {
        super(iVar);
        this.f857b = hVar;
        this.f856a = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f857b.f862a.mo34152b().mo34133a(this.f857b.f863c, PlayCoreVersion.m592a(), new C2014g(this.f857b, this.f856a));
        } catch (RemoteException e) {
            C2015h.f861b.mo34140a((Throwable) e, "error requesting in-app review for %s", this.f857b.f863c);
            this.f856a.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
