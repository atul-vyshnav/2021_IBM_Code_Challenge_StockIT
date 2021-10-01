package com.google.android.play.core.internal;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.internal.cb */
public final class C1968cb extends C1967ca {

    /* renamed from: a */
    private final C1967ca f814a;

    /* renamed from: b */
    private final long f815b;

    /* renamed from: c */
    private final long f816c;

    public C1968cb(C1967ca caVar, long j, long j2) {
        this.f814a = caVar;
        long a = m757a(j);
        this.f815b = a;
        this.f816c = m757a(a + j2);
    }

    /* renamed from: a */
    private final long m757a(long j) {
        if (j < 0) {
            return 0;
        }
        return j > this.f814a.mo33992a() ? this.f814a.mo33992a() : j;
    }

    /* renamed from: a */
    public final long mo33992a() {
        return this.f816c - this.f815b;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final InputStream mo33993a(long j, long j2) throws IOException {
        long a = m757a(this.f815b);
        return this.f814a.mo33993a(a, m757a(j2 + a) - a);
    }

    public final void close() throws IOException {
    }
}
