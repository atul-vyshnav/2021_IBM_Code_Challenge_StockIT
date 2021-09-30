package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import com.google.android.play.core.tasks.C2106i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.aq */
final class C1794aq extends C1788ak<AssetPackStates> {

    /* renamed from: c */
    private final List<String> f397c;

    /* renamed from: d */
    private final C1830bz f398d;

    C1794aq(C1795ar arVar, C2106i<AssetPackStates> iVar, C1830bz bzVar, List<String> list) {
        super(arVar, iVar);
        this.f398d = bzVar;
        this.f397c = list;
    }

    /* renamed from: a */
    public final void mo33931a(int i, Bundle bundle) {
        super.mo33931a(i, bundle);
        this.f387a.mo34398b(AssetPackStates.m291a(bundle, this.f398d, this.f397c));
    }
}
