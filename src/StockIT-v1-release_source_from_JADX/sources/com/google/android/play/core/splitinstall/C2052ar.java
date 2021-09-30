package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.splitinstall.ar */
final class C2052ar extends C2055au<SplitInstallSessionState> {
    C2052ar(C2056av avVar, C2106i<SplitInstallSessionState> iVar) {
        super(avVar, iVar);
    }

    /* renamed from: b */
    public final void mo34182b(int i, Bundle bundle) throws RemoteException {
        super.mo34182b(i, bundle);
        this.f942a.mo34398b(SplitInstallSessionState.m940a(bundle));
    }
}
