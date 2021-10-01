package com.google.android.play.core.internal;

/* renamed from: com.google.android.play.core.internal.cf */
final class C1972cf extends C1969cc {

    /* renamed from: a */
    private final C1971ce f820a = new C1971ce();

    C1972cf() {
    }

    /* renamed from: a */
    public final void mo34191a(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.f820a.mo34194a(th).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }
}
