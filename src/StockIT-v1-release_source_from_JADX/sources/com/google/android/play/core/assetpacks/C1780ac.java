package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1996s;
import com.google.android.play.core.internal.C1998u;
import com.google.android.play.core.tasks.C2106i;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.ac */
final class C1780ac extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ List f354a;

    /* renamed from: b */
    final /* synthetic */ C2106i f355b;

    /* renamed from: c */
    final /* synthetic */ C1795ar f356c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1780ac(C1795ar arVar, C2106i iVar, List list, C2106i iVar2) {
        super(iVar);
        this.f356c = arVar;
        this.f354a = list;
        this.f355b = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        try {
            ((C1996s) this.f356c.f403e.mo34152b()).mo34234b(this.f356c.f401c, (List<Bundle>) C1795ar.m325a((Collection) this.f354a), C1795ar.m341e(), (C1998u) new C1788ak(this.f356c, this.f355b, (byte[]) null));
        } catch (RemoteException e) {
            C1795ar.f399a.mo34140a((Throwable) e, "cancelDownloads(%s)", this.f354a);
        }
    }
}
