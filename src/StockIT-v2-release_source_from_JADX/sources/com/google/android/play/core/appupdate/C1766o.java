package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.o */
final class C1766o extends C1764m<AppUpdateInfo> {

    /* renamed from: d */
    final /* synthetic */ C1767p f286d;

    /* renamed from: e */
    private final String f287e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1766o(C1767p pVar, C2106i<AppUpdateInfo> iVar, String str) {
        super(pVar, new C1918af("OnRequestInstallCallback"), iVar);
        this.f286d = pVar;
        this.f287e = str;
    }

    /* renamed from: a */
    public final void mo33862a(Bundle bundle) throws RemoteException {
        super.mo33862a(bundle);
        if (bundle.getInt("error.code", -2) != 0) {
            this.f284b.mo34397b((Exception) new InstallException(bundle.getInt("error.code", -2)));
        } else {
            this.f284b.mo34398b(C1767p.m255a(this.f286d, bundle, this.f287e));
        }
    }
}
