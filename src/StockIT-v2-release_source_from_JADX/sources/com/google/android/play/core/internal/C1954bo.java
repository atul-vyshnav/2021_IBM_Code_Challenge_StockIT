package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.bo */
public final class C1954bo extends RuntimeException {
    public C1954bo(String str) {
        super(str);
    }

    public C1954bo(Throwable th) {
        super("Failed to initialize FileStorage", th);
    }
}
