package com.google.android.play.core.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.core.internal.am */
final class C1925am extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ IBinder f779a;

    /* renamed from: b */
    final /* synthetic */ C1927ao f780b;

    C1925am(C1927ao aoVar, IBinder iBinder) {
        this.f780b = aoVar;
        this.f779a = iBinder;
    }

    /* renamed from: a */
    public final void mo33861a() {
        C1928ap apVar = this.f780b.f782a;
        apVar.f794l = (IInterface) apVar.f790h.mo33860a(this.f779a);
        C1928ap.m635f(this.f780b.f782a);
        this.f780b.f782a.f788f = false;
        for (Runnable run : this.f780b.f782a.f787e) {
            run.run();
        }
        this.f780b.f782a.f787e.clear();
    }
}
