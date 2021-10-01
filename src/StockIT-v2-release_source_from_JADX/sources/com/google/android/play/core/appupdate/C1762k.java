package com.google.android.play.core.appupdate;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.k */
final class C1762k extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ String f277a;

    /* renamed from: b */
    final /* synthetic */ C2106i f278b;

    /* renamed from: c */
    final /* synthetic */ C1767p f279c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1762k(C1767p pVar, C2106i iVar, String str, C2106i iVar2) {
        super(iVar);
        this.f279c = pVar;
        this.f277a = str;
        this.f278b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            this.f279c.f290a.mo34152b().mo34227a(this.f279c.f291d, C1767p.m254a(this.f279c, this.f277a), new C1766o(this.f279c, this.f278b, this.f277a));
        } catch (RemoteException e) {
            C1767p.f288b.mo34140a((Throwable) e, "requestUpdateInfo(%s)", this.f277a);
            this.f278b.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
