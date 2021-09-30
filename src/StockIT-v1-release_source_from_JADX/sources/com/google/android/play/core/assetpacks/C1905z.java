package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.OnSuccessListener;

/* renamed from: com.google.android.play.core.assetpacks.z */
final /* synthetic */ class C1905z implements OnSuccessListener {

    /* renamed from: a */
    private final C1795ar f755a;

    C1905z(C1795ar arVar) {
        this.f755a = arVar;
    }

    public final void onSuccess(Object obj) {
        AssetPackStates assetPackStates = (AssetPackStates) obj;
        this.f755a.mo33945a();
    }
}
