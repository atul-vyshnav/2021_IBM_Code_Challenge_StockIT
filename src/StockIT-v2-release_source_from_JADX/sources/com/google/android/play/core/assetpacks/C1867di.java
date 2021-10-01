package com.google.android.play.core.assetpacks;

/* renamed from: com.google.android.play.core.assetpacks.di */
final /* synthetic */ class C1867di implements Runnable {

    /* renamed from: a */
    private final C1806bb f656a;

    private C1867di(C1806bb bbVar) {
        this.f656a = bbVar;
    }

    /* renamed from: a */
    static Runnable m512a(C1806bb bbVar) {
        return new C1867di(bbVar);
    }

    public final void run() {
        this.f656a.mo33976c();
    }
}
