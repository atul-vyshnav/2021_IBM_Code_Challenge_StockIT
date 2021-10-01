package com.google.android.play.core.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.google.android.play.core.internal.ce */
final class C1971ce {

    /* renamed from: a */
    private final ConcurrentHashMap<C1970cd, List<Throwable>> f818a = new ConcurrentHashMap<>(16, 0.75f, 10);

    /* renamed from: b */
    private final ReferenceQueue<Throwable> f819b = new ReferenceQueue<>();

    C1971ce() {
    }

    /* renamed from: a */
    public final List<Throwable> mo34194a(Throwable th) {
        while (true) {
            Reference<? extends Throwable> poll = this.f819b.poll();
            if (poll == null) {
                break;
            }
            this.f818a.remove(poll);
        }
        List<Throwable> list = this.f818a.get(new C1970cd(th, (ReferenceQueue<Throwable>) null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> putIfAbsent = this.f818a.putIfAbsent(new C1970cd(th, this.f819b), vector);
        return putIfAbsent == null ? vector : putIfAbsent;
    }
}
