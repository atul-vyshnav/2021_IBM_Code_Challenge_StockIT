package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.OnFailureListener;

/* renamed from: com.google.android.play.core.assetpacks.h */
final /* synthetic */ class C1886h implements OnFailureListener {

    /* renamed from: a */
    static final OnFailureListener f717a = new C1886h();

    private C1886h() {
    }

    public final void onFailure(Exception exc) {
        C1888j.f720a.mo34143d(String.format("Could not sync active asset packs. %s", new Object[]{exc}), new Object[0]);
    }
}
