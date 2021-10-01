package com.google.android.play.core.splitcompat;

import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.f */
final class C2022f implements C2024h {

    /* renamed from: a */
    final /* synthetic */ Set f879a;

    /* renamed from: b */
    final /* synthetic */ C2033q f880b;

    /* renamed from: c */
    final /* synthetic */ C2027k f881c;

    C2022f(C2027k kVar, Set set, C2033q qVar) {
        this.f881c = kVar;
        this.f879a = set;
        this.f880b = qVar;
    }

    /* renamed from: a */
    public final void mo34286a(ZipFile zipFile, Set<C2026j> set) throws IOException {
        this.f879a.addAll(C2027k.m926a(this.f881c, (Set) set, this.f880b, zipFile));
    }
}
