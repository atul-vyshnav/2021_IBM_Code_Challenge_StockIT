package com.google.android.play.core.review;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C1915ac;
import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.review.f */
class C2013f<T> extends C1915ac {

    /* renamed from: a */
    final C1918af f858a;

    /* renamed from: b */
    final C2106i<T> f859b;

    /* renamed from: c */
    final /* synthetic */ C2015h f860c;

    C2013f(C2015h hVar, C1918af afVar, C2106i<T> iVar) {
        this.f860c = hVar;
        this.f858a = afVar;
        this.f859b = iVar;
    }

    /* renamed from: a */
    public void mo34135a(Bundle bundle) throws RemoteException {
        this.f860c.f862a.mo34150a();
        this.f858a.mo34142c("onGetLaunchReviewFlowInfo", new Object[0]);
    }
}
