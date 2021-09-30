package com.google.android.play.core.splitinstall;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.play.core.splitinstall.o */
public final class C2075o {

    /* renamed from: a */
    private static final AtomicReference<C2074n> f977a = new AtomicReference<>((Object) null);

    /* renamed from: a */
    static C2074n m1022a() {
        return f977a.get();
    }

    /* renamed from: a */
    public static void m1023a(C2074n nVar) {
        f977a.compareAndSet((Object) null, nVar);
    }
}
