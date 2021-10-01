package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.j */
public abstract class C2107j extends RuntimeException {
    public C2107j(String str) {
        super(str);
    }

    public C2107j(Throwable th) {
        super(th);
    }

    public abstract int getErrorCode();
}
