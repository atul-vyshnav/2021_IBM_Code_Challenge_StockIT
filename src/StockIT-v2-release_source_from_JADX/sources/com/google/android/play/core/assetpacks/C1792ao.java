package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.ao */
final class C1792ao extends C1788ak<Void> {

    /* renamed from: c */
    final int f391c;

    /* renamed from: d */
    final String f392d;

    /* renamed from: e */
    final int f393e;

    /* renamed from: f */
    final /* synthetic */ C1795ar f394f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1792ao(C1795ar arVar, C2106i<Void> iVar, int i, String str, int i2) {
        super(arVar, iVar);
        this.f394f = arVar;
        this.f391c = i;
        this.f392d = str;
        this.f393e = i2;
    }

    /* renamed from: a */
    public final void mo33932a(Bundle bundle) {
        this.f394f.f403e.mo34150a();
        int i = bundle.getInt("error_code");
        C1795ar.f399a.mo34141b("onError(%d), retrying notifyModuleCompleted...", Integer.valueOf(i));
        int i2 = this.f393e;
        if (i2 > 0) {
            this.f394f.m327a(this.f391c, this.f392d, i2 - 1);
        }
    }
}
