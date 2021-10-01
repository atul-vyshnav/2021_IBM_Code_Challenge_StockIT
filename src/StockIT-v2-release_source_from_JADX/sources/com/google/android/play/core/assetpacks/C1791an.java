package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.an */
final class C1791an extends C1788ak<Void> {

    /* renamed from: c */
    final /* synthetic */ C1795ar f390c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1791an(C1795ar arVar, C2106i<Void> iVar) {
        super(arVar, iVar);
        this.f390c = arVar;
    }

    /* renamed from: a */
    public final void mo33933a(Bundle bundle, Bundle bundle2) {
        super.mo33933a(bundle, bundle2);
        if (!this.f390c.f405g.compareAndSet(true, false)) {
            C1795ar.f399a.mo34143d("Expected keepingAlive to be true, but was false.", new Object[0]);
        }
        if (bundle.getBoolean("keep_alive")) {
            this.f390c.mo33945a();
        }
    }
}
