package com.google.android.play.core.tasks;

import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.b */
final class C2099b<ResultT> implements C2104g<ResultT> {

    /* renamed from: a */
    private final Executor f1062a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Object f1063b = new Object();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final OnCompleteListener<ResultT> f1064c;

    public C2099b(Executor executor, OnCompleteListener<ResultT> onCompleteListener) {
        this.f1062a = executor;
        this.f1064c = onCompleteListener;
    }

    /* renamed from: a */
    public final void mo34389a(Task<ResultT> task) {
        synchronized (this.f1063b) {
            if (this.f1064c != null) {
                this.f1062a.execute(new C2098a(this, task));
            }
        }
    }
}
