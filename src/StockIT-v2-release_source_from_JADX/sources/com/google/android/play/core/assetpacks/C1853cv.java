package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.C2106i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.cv */
final /* synthetic */ class C1853cv implements Runnable {

    /* renamed from: a */
    private final C1857cz f608a;

    /* renamed from: b */
    private final List f609b;

    /* renamed from: c */
    private final C1803az f610c;

    /* renamed from: d */
    private final C2106i f611d;

    C1853cv(C1857cz czVar, List list, C1803az azVar, C2106i iVar) {
        this.f608a = czVar;
        this.f609b = list;
        this.f610c = azVar;
        this.f611d = iVar;
    }

    public final void run() {
        this.f608a.mo34052a(this.f609b, this.f610c, this.f611d);
    }
}
