package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C2106i;

/* renamed from: com.google.android.play.core.assetpacks.ap */
final class C1793ap extends C1788ak<AssetPackStates> {

    /* renamed from: c */
    private final C1830bz f395c;

    /* renamed from: d */
    private final C1803az f396d;

    C1793ap(C1795ar arVar, C2106i<AssetPackStates> iVar, C1830bz bzVar, C1803az azVar) {
        super(arVar, iVar);
        this.f395c = bzVar;
        this.f396d = azVar;
    }

    /* renamed from: c */
    public final void mo33940c(Bundle bundle, Bundle bundle2) {
        super.mo33940c(bundle, bundle2);
        this.f387a.mo34398b(AssetPackStates.m290a(bundle, this.f395c, this.f396d));
    }
}
