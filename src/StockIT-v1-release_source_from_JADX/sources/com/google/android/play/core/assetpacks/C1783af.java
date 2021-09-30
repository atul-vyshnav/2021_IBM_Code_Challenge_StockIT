package com.google.android.play.core.assetpacks;

import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.internal.C1998u;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.af */
final class C1783af extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ int f365a;

    /* renamed from: b */
    final /* synthetic */ String f366b;

    /* renamed from: c */
    final /* synthetic */ String f367c;

    /* renamed from: d */
    final /* synthetic */ int f368d;

    /* renamed from: e */
    final /* synthetic */ C2106i f369e;

    /* renamed from: f */
    final /* synthetic */ C1795ar f370f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1783af(C1795ar arVar, C2106i iVar, int i, String str, String str2, int i2, C2106i iVar2) {
        super(iVar);
        this.f370f = arVar;
        this.f365a = i;
        this.f366b = str;
        this.f367c = str2;
        this.f368d = i2;
        this.f369e = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f370f.f403e.mo34152b()).mo34229a(this.f370f.f401c, C1795ar.m337c(this.f365a, this.f366b, this.f367c, this.f368d), C1795ar.m341e(), (C1998u) new C1788ak(this.f370f, this.f369e, (char[]) null));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "notifyChunkTransferred", new Object[0]);
        }
    }
}
