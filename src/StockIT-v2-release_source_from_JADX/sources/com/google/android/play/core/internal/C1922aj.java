package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.aj */
final class C1922aj extends C1919ag {

    /* renamed from: a */
    final /* synthetic */ C1928ap f778a;

    C1922aj(C1928ap apVar) {
        this.f778a = apVar;
    }

    /* renamed from: a */
    public final void mo33861a() {
        if (this.f778a.f794l != null) {
            this.f778a.f785c.mo34142c("Unbind from service.", new Object[0]);
            this.f778a.f784b.unbindService(this.f778a.f793k);
            this.f778a.f788f = false;
            this.f778a.f794l = null;
            this.f778a.f793k = null;
        }
    }
}
