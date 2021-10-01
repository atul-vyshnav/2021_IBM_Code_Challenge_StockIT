package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.C2106i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.cu */
final /* synthetic */ class C1852cu implements Runnable {

    /* renamed from: a */
    private final C1857cz f604a;

    /* renamed from: b */
    private final List f605b;

    /* renamed from: c */
    private final C2106i f606c;

    /* renamed from: d */
    private final List f607d;

    C1852cu(C1857cz czVar, List list, C2106i iVar, List list2) {
        this.f604a = czVar;
        this.f605b = list;
        this.f606c = iVar;
        this.f607d = list2;
    }

    public final void run() {
        this.f604a.mo34053a(this.f605b, this.f606c, this.f607d);
    }
}
