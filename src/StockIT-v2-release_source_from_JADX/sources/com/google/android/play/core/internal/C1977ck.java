package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.ck */
public final class C1977ck<T> implements C1980cn<T> {

    /* renamed from: a */
    private C1980cn<T> f822a;

    /* renamed from: a */
    public static <T> void m767a(C1980cn<T> cnVar, C1980cn<T> cnVar2) {
        C1956bq.m713a(cnVar2);
        C1977ck ckVar = (C1977ck) cnVar;
        if (ckVar.f822a == null) {
            ckVar.f822a = cnVar2;
            return;
        }
        throw new IllegalStateException();
    }

    /* renamed from: a */
    public final T mo33855a() {
        C1980cn<T> cnVar = this.f822a;
        if (cnVar != null) {
            return cnVar.mo33855a();
        }
        throw new IllegalStateException();
    }
}
