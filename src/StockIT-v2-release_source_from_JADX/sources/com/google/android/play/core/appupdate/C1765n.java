package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.appupdate.n */
final class C1765n extends C1764m<Void> {
    C1765n(C1767p pVar, C2106i<Void> iVar) {
        super(pVar, new C1918af("OnCompleteUpdateCallback"), iVar);
    }

    /* renamed from: b */
    public final void mo33863b(Bundle bundle) throws RemoteException {
        super.mo33863b(bundle);
        if (bundle.getInt("error.code", -2) != 0) {
            this.f284b.mo34397b((Exception) new InstallException(bundle.getInt("error.code", -2)));
        } else {
            this.f284b.mo34398b(null);
        }
    }
}
