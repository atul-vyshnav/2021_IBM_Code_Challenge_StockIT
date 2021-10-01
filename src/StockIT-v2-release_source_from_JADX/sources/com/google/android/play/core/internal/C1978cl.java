package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.cl */
public final class C1978cl<T> implements C1980cn, C1976cj {

    /* renamed from: a */
    private static final Object f823a = new Object();

    /* renamed from: b */
    private volatile C1980cn<T> f824b;

    /* renamed from: c */
    private volatile Object f825c = f823a;

    private C1978cl(C1980cn<T> cnVar) {
        this.f824b = cnVar;
    }

    /* renamed from: a */
    public static <P extends C1980cn<T>, T> C1980cn<T> m769a(P p) {
        C1956bq.m713a(p);
        return p instanceof C1978cl ? p : new C1978cl(p);
    }

    /* renamed from: b */
    public static <P extends C1980cn<T>, T> C1976cj<T> m770b(P p) {
        if (p instanceof C1976cj) {
            return (C1976cj) p;
        }
        C1956bq.m713a(p);
        return new C1978cl(p);
    }

    /* renamed from: a */
    public final T mo33855a() {
        T t = this.f825c;
        if (t == f823a) {
            synchronized (this) {
                t = this.f825c;
                if (t == f823a) {
                    t = this.f824b.mo33855a();
                    T t2 = this.f825c;
                    if (t2 != f823a && !(t2 instanceof C1979cm)) {
                        if (t2 != t) {
                            String valueOf = String.valueOf(t2);
                            String valueOf2 = String.valueOf(t);
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 118 + String.valueOf(valueOf2).length());
                            sb.append("Scoped provider was invoked recursively returning different results: ");
                            sb.append(valueOf);
                            sb.append(" & ");
                            sb.append(valueOf2);
                            sb.append(". This is likely due to a circular dependency.");
                            throw new IllegalStateException(sb.toString());
                        }
                    }
                    this.f825c = t;
                    this.f824b = null;
                }
            }
        }
        return t;
    }
}
