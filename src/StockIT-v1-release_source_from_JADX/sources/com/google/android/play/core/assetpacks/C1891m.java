package com.google.android.play.core.assetpacks;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.google.android.play.core.assetpacks.m */
final /* synthetic */ class C1891m implements ThreadFactory {

    /* renamed from: a */
    static final ThreadFactory f740a = new C1891m();

    private C1891m() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "UpdateListenerExecutor");
    }
}
