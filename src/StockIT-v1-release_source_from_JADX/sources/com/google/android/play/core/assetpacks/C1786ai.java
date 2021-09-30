package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.ai */
final class C1786ai extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f379a;

    /* renamed from: b */
    final /* synthetic */ String f380b;

    /* renamed from: c */
    final /* synthetic */ String f381c;

    /* renamed from: d */
    final /* synthetic */ int f382d;

    /* renamed from: e */
    final /* synthetic */ C2106i f383e;

    /* renamed from: f */
    final /* synthetic */ C1795ar f384f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1786ai(C1795ar arVar, C2106i iVar, int i, String str, String str2, int i2, C2106i iVar2) {
        super(iVar);
        this.f384f = arVar;
        this.f379a = i;
        this.f380b = str;
        this.f381c = str2;
        this.f382d = i2;
        this.f383e = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f384f.f403e.mo34152b()).mo34237d(this.f384f.f401c, C1795ar.m337c(this.f379a, this.f380b, this.f381c, this.f382d), C1795ar.m341e(), new C1789al(this.f384f, this.f383e));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34141b("getChunkFileDescriptor(%s, %s, %d, session=%d)", this.f380b, this.f381c, Integer.valueOf(this.f382d), Integer.valueOf(this.f379a));
            this.f383e.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
