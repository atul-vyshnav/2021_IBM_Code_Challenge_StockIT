package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.d */
final class C2101d<ResultT> implements C2104g<ResultT> {

    /* renamed from: a */
    private final Executor f1067a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1068b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnFailureListener f1069c;

    public C2101d(Executor executor, OnFailureListener onFailureListener) {
        this.f1067a = executor;
        this.f1069c = onFailureListener;
    }

    /* renamed from: a */
    public final void mo34389a(Task<ResultT> task) {
        if (!task.isSuccessful()) {
            synchronized (this.f1068b) {
                if (this.f1069c != null) {
                    this.f1067a.execute(new C2100c(this, task));
                }
            }
        }
    }
}
