package com.google.android.play.core.splitinstall;

import com.google.android.play.core.internal.C1978cl;
import com.google.android.play.core.internal.C1980cn;
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManager;
import java.io.File;

/* renamed from: com.google.android.play.core.splitinstall.j */
public final class C2069j implements C1980cn<C2068i> {

    /* renamed from: a */
    private final C1980cn<C2094w> f969a;

    /* renamed from: b */
    private final C1980cn<FakeSplitInstallManager> f970b;

    /* renamed from: c */
    private final C1980cn<File> f971c;

    public C2069j(C1980cn<C2094w> cnVar, C1980cn<FakeSplitInstallManager> cnVar2, C1980cn<File> cnVar3) {
        this.f969a = cnVar;
        this.f970b = cnVar2;
        this.f971c = cnVar3;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        return new C2068i(C1978cl.m770b(this.f969a), C1978cl.m770b(this.f970b), C1978cl.m770b(this.f971c));
    }
}
