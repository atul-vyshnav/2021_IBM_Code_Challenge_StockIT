package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallSessionState;

/* renamed from: com.google.android.play.core.splitinstall.testing.f */
final /* synthetic */ class C2086f implements Runnable {

    /* renamed from: a */
    private final FakeSplitInstallManager f1022a;

    /* renamed from: b */
    private final SplitInstallSessionState f1023b;

    C2086f(FakeSplitInstallManager fakeSplitInstallManager, SplitInstallSessionState splitInstallSessionState) {
        this.f1022a = fakeSplitInstallManager;
        this.f1023b = splitInstallSessionState;
    }

    public final void run() {
        this.f1022a.mo34362a(this.f1023b);
    }
}
