package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.c */
final class C2100c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1065a;

    /* renamed from: b */
    final /* synthetic */ C2101d f1066b;

    C2100c(C2101d dVar, Task task) {
        this.f1066b = dVar;
        this.f1065a = task;
    }

    public final void run() {
        synchronized (this.f1066b.f1068b) {
            if (this.f1066b.f1069c != null) {
                this.f1066b.f1069c.onFailure(this.f1065a.getException());
            }
        }
    }
}
