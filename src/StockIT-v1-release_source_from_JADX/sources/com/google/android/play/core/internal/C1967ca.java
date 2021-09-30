package com.google.android.play.core.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.google.android.play.core.internal.ca */
public abstract class C1967ca implements Closeable {
    /* renamed from: a */
    public abstract long mo33992a();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract InputStream mo33993a(long j, long j2) throws IOException;

    /* renamed from: b */
    public synchronized InputStream mo34189b() throws IOException {
        return mo33993a(0, mo33992a());
    }
}
