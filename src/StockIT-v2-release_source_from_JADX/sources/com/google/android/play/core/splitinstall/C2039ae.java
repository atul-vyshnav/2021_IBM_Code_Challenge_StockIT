package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1919ag;
import com.google.android.play.core.internal.C1962bw;
import com.google.android.play.core.tasks.C2106i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.ae */
final class C2039ae extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ Collection f918a;

    /* renamed from: b */
    final /* synthetic */ Collection f919b;

    /* renamed from: c */
    final /* synthetic */ C2106i f920c;

    /* renamed from: d */
    final /* synthetic */ C2056av f921d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2039ae(C2056av avVar, C2106i iVar, Collection collection, Collection collection2, C2106i iVar2) {
        super(iVar);
        this.f921d = avVar;
        this.f918a = collection;
        this.f919b = collection2;
        this.f920c = iVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo33861a() {
        ArrayList a = C2056av.m980a(this.f918a);
        a.addAll(C2056av.m982b(this.f919b));
        try {
            this.f921d.f946a.mo34152b().mo34171a(this.f921d.f947d, (List<Bundle>) a, C2056av.m981b(), (C1962bw) new C2054at(this.f921d, this.f920c));
        } catch (RemoteException e) {
            C2056av.f944b.mo34140a((Throwable) e, "startInstall(%s,%s)", this.f918a, this.f919b);
            this.f920c.mo34397b((Exception) new RuntimeException(e));
        }
    }
}
