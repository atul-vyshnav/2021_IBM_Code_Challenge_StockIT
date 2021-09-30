package com.google.android.play.core.splitinstall.testing;

import android.content.Context;
import com.google.android.play.core.internal.C1980cn;
import com.google.android.play.core.splitinstall.C2076p;
import com.google.android.play.core.splitinstall.C2097z;
import java.io.File;

/* renamed from: com.google.android.play.core.splitinstall.testing.k */
public final class C2091k implements C1980cn<FakeSplitInstallManager> {

    /* renamed from: a */
    private final C1980cn<Context> f1040a;

    /* renamed from: b */
    private final C1980cn<File> f1041b;

    /* renamed from: c */
    private final C1980cn<C2076p> f1042c;

    public C2091k(C1980cn<Context> cnVar, C1980cn<File> cnVar2, C1980cn<C2076p> cnVar3) {
        this.f1040a = cnVar;
        this.f1041b = cnVar2;
        this.f1042c = cnVar3;
    }

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo33855a() {
        return new FakeSplitInstallManager(((C2097z) this.f1040a).mo33855a(), this.f1041b.mo33855a(), this.f1042c.mo33855a());
    }
}
