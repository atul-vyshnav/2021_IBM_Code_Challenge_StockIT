package com.google.android.play.core.splitinstall.testing;

import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.g */
final /* synthetic */ class C2087g implements Runnable {

    /* renamed from: a */
    private final FakeSplitInstallManager f1024a;

    /* renamed from: b */
    private final long f1025b;

    /* renamed from: c */
    private final List f1026c;

    /* renamed from: d */
    private final List f1027d;

    /* renamed from: e */
    private final List f1028e;

    C2087g(FakeSplitInstallManager fakeSplitInstallManager, long j, List list, List list2, List list3) {
        this.f1024a = fakeSplitInstallManager;
        this.f1025b = j;
        this.f1026c = list;
        this.f1027d = list2;
        this.f1028e = list3;
    }

    public final void run() {
        this.f1024a.mo34361a(this.f1025b, this.f1026c, this.f1027d, this.f1028e);
    }
}
