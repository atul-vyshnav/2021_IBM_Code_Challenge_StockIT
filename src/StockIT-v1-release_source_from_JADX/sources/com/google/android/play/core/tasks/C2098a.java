package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.a */
final class C2098a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1060a;

    /* renamed from: b */
    final /* synthetic */ C2099b f1061b;

    C2098a(C2099b bVar, Task task) {
        this.f1061b = bVar;
        this.f1060a = task;
    }

    public final void run() {
        synchronized (this.f1061b.f1063b) {
            if (this.f1061b.f1064c != null) {
                this.f1061b.f1064c.onComplete(this.f1060a);
            }
        }
    }
}
