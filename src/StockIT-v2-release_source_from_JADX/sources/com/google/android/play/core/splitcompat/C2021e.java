package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.e */
final class C2021e implements C2024h {

    /* renamed from: a */
    final /* synthetic */ C2033q f875a;

    /* renamed from: b */
    final /* synthetic */ Set f876b;

    /* renamed from: c */
    final /* synthetic */ AtomicBoolean f877c;

    /* renamed from: d */
    final /* synthetic */ C2027k f878d;

    C2021e(C2027k kVar, C2033q qVar, Set set, AtomicBoolean atomicBoolean) {
        this.f878d = kVar;
        this.f875a = qVar;
        this.f876b = set;
        this.f877c = atomicBoolean;
    }

    /* renamed from: a */
    public final void mo34286a(ZipFile zipFile, Set<C2026j> set) throws IOException {
        this.f878d.m929a(this.f875a, set, new C2020d(this));
    }
}
