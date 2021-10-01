package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1992o;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.m */
class C1764m<T> extends C1992o {

    /* renamed from: a */
    final C1918af f283a;

    /* renamed from: b */
    final C2106i<T> f284b;

    /* renamed from: c */
    final /* synthetic */ C1767p f285c;

    C1764m(C1767p pVar, C1918af afVar, C2106i<T> iVar) {
        this.f285c = pVar;
        this.f283a = afVar;
        this.f284b = iVar;
    }

    /* renamed from: a */
    public void mo33862a(Bundle bundle) throws RemoteException {
        this.f285c.f290a.mo34150a();
        this.f283a.mo34142c("onRequestInfo", new Object[0]);
    }

    /* renamed from: b */
    public void mo33863b(Bundle bundle) throws RemoteException {
        this.f285c.f290a.mo34150a();
        this.f283a.mo34142c("onCompleteUpdate", new Object[0]);
    }
}
