package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1961bv;
import com.google.android.play.core.tasks.C2106i;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.au */
class C2055au<T> extends C1961bv {

    /* renamed from: a */
    final C2106i<T> f942a;

    /* renamed from: b */
    final /* synthetic */ C2056av f943b;

    C2055au(C2056av avVar, C2106i<T> iVar) {
        this.f943b = avVar;
        this.f942a = iVar;
    }

    /* renamed from: a */
    public final void mo34176a() throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onCompleteInstallForAppUpdate", new Object[0]);
    }

    /* renamed from: a */
    public final void mo34177a(int i) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onCompleteInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo34178a(int i, Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onCancelInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo34179a(Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onDeferredInstall", new Object[0]);
    }

    /* renamed from: a */
    public void mo34180a(List<Bundle> list) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onGetSessionStates", new Object[0]);
    }

    /* renamed from: b */
    public final void mo34181b() throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onGetSplitsForAppUpdate", new Object[0]);
    }

    /* renamed from: b */
    public void mo34182b(int i, Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onGetSession(%d)", Integer.valueOf(i));
    }

    /* renamed from: b */
    public void mo34183b(Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onDeferredLanguageInstall", new Object[0]);
    }

    /* renamed from: c */
    public void mo34184c(int i, Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onStartInstall(%d)", Integer.valueOf(i));
    }

    /* renamed from: c */
    public void mo34185c(Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onDeferredLanguageUninstall", new Object[0]);
    }

    /* renamed from: d */
    public void mo34186d(Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        C2056av.f944b.mo34142c("onDeferredUninstall", new Object[0]);
    }

    /* renamed from: e */
    public final void mo34187e(Bundle bundle) throws RemoteException {
        this.f943b.f946a.mo34150a();
        int i = bundle.getInt("error_code");
        C2056av.f944b.mo34141b("onError(%d)", Integer.valueOf(i));
        this.f942a.mo34397b((Exception) new SplitInstallException(i));
    }
}
