package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.b */
final /* synthetic */ class C2082b implements C2090j {

    /* renamed from: a */
    private final Integer f1010a;

    /* renamed from: b */
    private final int f1011b;

    /* renamed from: c */
    private final int f1012c;

    /* renamed from: d */
    private final Long f1013d;

    /* renamed from: e */
    private final Long f1014e;

    /* renamed from: f */
    private final List f1015f;

    /* renamed from: g */
    private final List f1016g;

    C2082b(Integer num, int i, int i2, Long l, Long l2, List list, List list2) {
        this.f1010a = num;
        this.f1011b = i;
        this.f1012c = i2;
        this.f1013d = l;
        this.f1014e = l2;
        this.f1015f = list;
        this.f1016g = list2;
    }

    /* renamed from: a */
    public final SplitInstallSessionState mo34366a(SplitInstallSessionState splitInstallSessionState) {
        return FakeSplitInstallManager.m1042a(this.f1010a, this.f1011b, this.f1012c, this.f1013d, this.f1014e, this.f1015f, this.f1016g, splitInstallSessionState);
    }
}
