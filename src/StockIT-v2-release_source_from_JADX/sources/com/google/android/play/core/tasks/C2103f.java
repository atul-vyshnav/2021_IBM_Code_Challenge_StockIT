package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.f */
final class C2103f<ResultT> implements C2104g<ResultT> {

    /* renamed from: a */
    private final Executor f1072a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1073b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnSuccessListener<? super ResultT> f1074c;

    public C2103f(Executor executor, OnSuccessListener<? super ResultT> onSuccessListener) {
        this.f1072a = executor;
        this.f1074c = onSuccessListener;
    }

    /* renamed from: a */
    public final void mo34389a(Task<ResultT> task) {
        if (task.isSuccessful()) {
            synchronized (this.f1073b) {
                if (this.f1074c != null) {
                    this.f1072a.execute(new C2102e(this, task));
                }
            }
        }
    }
}
