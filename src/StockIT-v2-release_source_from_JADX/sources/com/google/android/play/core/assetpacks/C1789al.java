package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.al */
final class C1789al extends C1788ak<ParcelFileDescriptor> {
    C1789al(C1795ar arVar, C2106i<ParcelFileDescriptor> iVar) {
        super(arVar, iVar);
    }

    /* renamed from: b */
    public final void mo33938b(Bundle bundle, Bundle bundle2) throws RemoteException {
        super.mo33938b(bundle, bundle2);
        this.f387a.mo34398b((ParcelFileDescriptor) bundle.getParcelable("chunk_file_descriptor"));
    }
}
