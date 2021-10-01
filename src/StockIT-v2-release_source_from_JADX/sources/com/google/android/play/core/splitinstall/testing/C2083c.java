package com.google.android.play.core.splitinstall.testing;

import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.c */
final /* synthetic */ class C2083c implements Runnable {

    /* renamed from: a */
    private final FakeSplitInstallManager f1017a;

    /* renamed from: b */
    private final List f1018b;

    /* renamed from: c */
    private final List f1019c;

    C2083c(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2) {
        this.f1017a = fakeSplitInstallManager;
        this.f1018b = list;
        this.f1019c = list2;
    }

    public final void run() {
        this.f1017a.mo34363a(this.f1018b, this.f1019c);
    }
}
