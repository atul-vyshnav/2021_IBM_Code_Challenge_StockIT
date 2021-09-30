package com.google.android.play.core.assetpacks;

import android.content.ComponentName;
import android.content.Context;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.internal.C1956bq;
import com.google.android.play.core.internal.C1980cn;

/* renamed from: com.google.android.play.core.assetpacks.q */
public final class C1896q implements C1980cn<AssetPackManager> {

    /* renamed from: a */
    private final C1980cn<C1888j> f745a;

    /* renamed from: b */
    private final C1980cn<Context> f746b;

    public C1896q(C1980cn<C1888j> cnVar, C1980cn<Context> cnVar2) {
        this.f745a = cnVar;
        this.f746b = cnVar2;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        C1888j a = this.f745a.mo33855a();
        Context b = ((C1898s) this.f746b).mo33855a();
        C1888j jVar = a;
        C1956bq.m711a(b.getPackageManager(), new ComponentName(b.getPackageName(), "com.google.android.play.core.assetpacks.AssetPackExtractionService"), 4);
        PlayCoreDialogWrapperActivity.m591a(b);
        C1956bq.m718b(jVar);
        return jVar;
    }
}
