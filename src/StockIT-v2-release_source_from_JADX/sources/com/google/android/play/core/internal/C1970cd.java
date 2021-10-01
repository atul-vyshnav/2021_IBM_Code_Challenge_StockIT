package com.google.android.play.core.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* renamed from: com.google.android.play.core.internal.cd */
final class C1970cd extends WeakReference<Throwable> {

    /* renamed from: a */
    private final int f817a;

    public C1970cd(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        this.f817a = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            C1970cd cdVar = (C1970cd) obj;
            return this.f817a == cdVar.f817a && get() == cdVar.get();
        }
    }

    public final int hashCode() {
        return this.f817a;
    }
}
