package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.e */
final class C2102e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Task f1070a;

    /* renamed from: b */
    final /* synthetic */ C2103f f1071b;

    C2102e(C2103f fVar, Task task) {
        this.f1071b = fVar;
        this.f1070a = task;
    }

    public final void run() {
        synchronized (this.f1071b.f1073b) {
            if (this.f1071b.f1074c != null) {
                this.f1071b.f1074c.onSuccess(this.f1070a.getResult());
            }
        }
    }
}
