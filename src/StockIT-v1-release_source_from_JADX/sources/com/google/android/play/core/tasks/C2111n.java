package com.google.android.play.core.tasks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.android.play.core.tasks.n */
final class C2111n implements OnSuccessListener, OnFailureListener {

    /* renamed from: a */
    private final CountDownLatch f1085a = new CountDownLatch(1);

    private C2111n() {
    }

    /* synthetic */ C2111n(byte[] bArr) {
    }

    /* renamed from: a */
    public final void mo34405a() throws InterruptedException {
        this.f1085a.await();
    }

    /* renamed from: a */
    public final boolean mo34406a(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.f1085a.await(j, timeUnit);
    }

    public final void onFailure(Exception exc) {
        this.f1085a.countDown();
    }

    public final void onSuccess(Object obj) {
        this.f1085a.countDown();
    }
}
