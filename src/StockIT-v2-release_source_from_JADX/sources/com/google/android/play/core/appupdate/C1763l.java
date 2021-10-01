package com.google.android.play.core.appupdate;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.l */
final class C1763l extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ C2106i f280a;

    /* renamed from: b */
    final /* synthetic */ String f281b;

    /* renamed from: c */
    final /* synthetic */ C1767p f282c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1763l(C1767p pVar, C2106i iVar, C2106i iVar2, String str) {
        super(iVar);
        this.f282c = pVar;
        this.f280a = iVar2;
        this.f281b = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f282c.f290a.mo34152b().mo34228b(this.f282c.f291d, C1767p.m260d(), new C1765n(this.f282c, this.f280a));
        } catch (RemoteException e) {
            C1767p.f288b.mo34140a((Throwable) e, "completeUpdate(%s)", this.f281b);
            this.f280a.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
