package com.google.android.play.core.review;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.review.g */
final class C2014g extends C2013f<ReviewInfo> {
    C2014g(C2015h hVar, C2106i iVar) {
        super(hVar, new C1918af("OnRequestInstallCallback"), iVar);
    }

    /* renamed from: a */
    public final void mo34135a(Bundle bundle) throws RemoteException {
        super.mo34135a(bundle);
        this.f859b.mo34398b(ReviewInfo.m877a((PendingIntent) bundle.get("confirmation_intent")));
    }
}
