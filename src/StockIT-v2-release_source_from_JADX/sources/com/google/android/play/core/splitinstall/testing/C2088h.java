package com.google.android.play.core.splitinstall.testing;

import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.h */
final /* synthetic */ class C2088h implements Runnable {

    /* renamed from: a */
    private final FakeSplitInstallManager f1029a;

    /* renamed from: b */
    private final List f1030b;

    /* renamed from: c */
    private final List f1031c;

    /* renamed from: d */
    private final List f1032d;

    /* renamed from: e */
    private final long f1033e;

    C2088h(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2, List list3, long j) {
        this.f1029a = fakeSplitInstallManager;
        this.f1030b = list;
        this.f1031c = list2;
        this.f1032d = list3;
        this.f1033e = j;
    }

    public final void run() {
        this.f1029a.mo34364a(this.f1030b, this.f1031c, this.f1032d, this.f1033e);
    }
}
