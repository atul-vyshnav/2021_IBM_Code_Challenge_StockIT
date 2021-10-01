package com.google.android.play.core.assetpacks;

import android.os.Bundle;

/* renamed from: com.google.android.play.core.assetpacks.au */
final /* synthetic */ class C1798au implements Runnable {

    /* renamed from: a */
    private final C1800aw f410a;

    /* renamed from: b */
    private final Bundle f411b;

    /* renamed from: c */
    private final AssetPackState f412c;

    C1798au(C1800aw awVar, Bundle bundle, AssetPackState assetPackState) {
        this.f410a = awVar;
        this.f411b = bundle;
        this.f412c = assetPackState;
    }

    public final void run() {
        this.f410a.mo33956a(this.f411b, this.f412c);
    }
}
