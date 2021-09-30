package com.google.android.play.core.splitinstall;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C2106i;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.as */
final class C2053as extends C2055au<List<SplitInstallSessionState>> {
    C2053as(C2056av avVar, C2106i<List<SplitInstallSessionState>> iVar) {
        super(avVar, iVar);
    }

    /* renamed from: a */
    public final void mo34180a(List<Bundle> list) throws RemoteException {
        super.mo34180a(list);
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(SplitInstallSessionState.m940a(list.get(i)));
        }
        this.f942a.mo34398b(arrayList);
    }
}
