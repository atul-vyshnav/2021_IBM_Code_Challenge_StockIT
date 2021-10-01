package com.google.android.play.core.splitinstall.testing;

import android.content.Intent;
import com.google.android.play.core.splitinstall.C2063d;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.testing.i */
final class C2089i implements C2063d {

    /* renamed from: a */
    final /* synthetic */ List f1034a;

    /* renamed from: b */
    final /* synthetic */ List f1035b;

    /* renamed from: c */
    final /* synthetic */ long f1036c;

    /* renamed from: d */
    final /* synthetic */ boolean f1037d;

    /* renamed from: e */
    final /* synthetic */ List f1038e;

    /* renamed from: f */
    final /* synthetic */ FakeSplitInstallManager f1039f;

    C2089i(FakeSplitInstallManager fakeSplitInstallManager, List list, List list2, long j, boolean z, List list3) {
        this.f1039f = fakeSplitInstallManager;
        this.f1034a = list;
        this.f1035b = list2;
        this.f1036c = j;
        this.f1037d = z;
        this.f1038e = list3;
    }

    /* renamed from: a */
    public final void mo34348a() {
        this.f1039f.f1004l.addAll(this.f1034a);
        this.f1039f.f1005m.addAll(this.f1035b);
        this.f1039f.m1050a(5, 0, Long.valueOf(this.f1036c), Long.valueOf(this.f1036c), (List<String>) null, (Integer) null, (List<String>) null);
    }

    /* renamed from: a */
    public final void mo34349a(int i) {
        this.f1039f.m1049a(i);
    }

    /* renamed from: b */
    public final void mo34350b() {
        if (!this.f1037d) {
            this.f1039f.m1048a((List<Intent>) this.f1038e, (List<String>) this.f1034a, (List<String>) this.f1035b, this.f1036c, true);
        }
    }
}
