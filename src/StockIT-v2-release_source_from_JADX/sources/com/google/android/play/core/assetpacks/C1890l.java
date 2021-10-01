package com.google.android.play.core.assetpacks;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.play.core.assetpacks.l */
final /* synthetic */ class C1890l implements ThreadFactory {

    /* renamed from: a */
    static final ThreadFactory f739a = new C1890l();

    private C1890l() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "AssetPackBackgroundExecutor");
    }
}
