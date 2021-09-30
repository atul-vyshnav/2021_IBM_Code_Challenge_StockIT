package com.google.android.play.core.internal;

import android.os.IBinder;

/* renamed from: com.google.android.play.core.internal.ah */
final /* synthetic */ class C1920ah implements IBinder.DeathRecipient {

    /* renamed from: a */
    private final C1928ap f775a;

    C1920ah(C1928ap apVar) {
        this.f775a = apVar;
    }

    public final void binderDied() {
        this.f775a.mo34153c();
    }
}
