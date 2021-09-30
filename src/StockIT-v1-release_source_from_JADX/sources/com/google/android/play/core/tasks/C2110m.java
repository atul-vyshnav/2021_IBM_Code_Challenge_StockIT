package com.google.android.play.core.tasks;

import com.google.android.play.core.internal.C1934av;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.tasks.m */
final class C2110m<ResultT> extends Task<ResultT> {

    /* renamed from: a */
    private final Object f1080a = new Object();

    /* renamed from: b */
    private final C2105h<ResultT> f1081b = new C2105h<>();

    /* renamed from: c */
    private boolean f1082c;

    /* renamed from: d */
    private ResultT f1083d;

    /* renamed from: e */
    private Exception f1084e;

    C2110m() {
    }

    /* renamed from: a */
    private final void m1099a() {
        C1934av.m657a(this.f1082c, (Object) "Task is not yet complete");
    }

    /* renamed from: b */
    private final void m1100b() {
        C1934av.m657a(!this.f1082c, (Object) "Task is already complete");
    }

    /* renamed from: c */
    private final void m1101c() {
        synchronized (this.f1080a) {
            if (this.f1082c) {
                this.f1081b.mo34392a(this);
            }
        }
    }

    /* renamed from: a */
    public final void mo34401a(Exception exc) {
        synchronized (this.f1080a) {
            m1100b();
            this.f1082c = true;
            this.f1084e = exc;
        }
        this.f1081b.mo34392a(this);
    }

    /* renamed from: a */
    public final void mo34402a(ResultT resultt) {
        synchronized (this.f1080a) {
            m1100b();
            this.f1082c = true;
            this.f1083d = resultt;
        }
        this.f1081b.mo34392a(this);
    }

    public final Task<ResultT> addOnCompleteListener(OnCompleteListener<ResultT> onCompleteListener) {
        this.f1081b.mo34393a(new C2099b(TaskExecutors.MAIN_THREAD, onCompleteListener));
        m1101c();
        return this;
    }

    public final Task<ResultT> addOnCompleteListener(Executor executor, OnCompleteListener<ResultT> onCompleteListener) {
        this.f1081b.mo34393a(new C2099b(executor, onCompleteListener));
        m1101c();
        return this;
    }

    public final Task<ResultT> addOnFailureListener(OnFailureListener onFailureListener) {
        addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
        return this;
    }

    public final Task<ResultT> addOnFailureListener(Executor executor, OnFailureListener onFailureListener) {
        this.f1081b.mo34393a(new C2101d(executor, onFailureListener));
        m1101c();
        return this;
    }

    public final Task<ResultT> addOnSuccessListener(OnSuccessListener<? super ResultT> onSuccessListener) {
        addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
        return this;
    }

    public final Task<ResultT> addOnSuccessListener(Executor executor, OnSuccessListener<? super ResultT> onSuccessListener) {
        this.f1081b.mo34393a(new C2103f(executor, onSuccessListener));
        m1101c();
        return this;
    }

    /* renamed from: b */
    public final boolean mo34403b(Exception exc) {
        synchronized (this.f1080a) {
            if (this.f1082c) {
                return false;
            }
            this.f1082c = true;
            this.f1084e = exc;
            this.f1081b.mo34392a(this);
            return true;
        }
    }

    /* renamed from: b */
    public final boolean mo34404b(ResultT resultt) {
        synchronized (this.f1080a) {
            if (this.f1082c) {
                return false;
            }
            this.f1082c = true;
            this.f1083d = resultt;
            this.f1081b.mo34392a(this);
            return true;
        }
    }

    public final Exception getException() {
        Exception exc;
        synchronized (this.f1080a) {
            exc = this.f1084e;
        }
        return exc;
    }

    public final ResultT getResult() {
        ResultT resultt;
        synchronized (this.f1080a) {
            m1099a();
            Exception exc = this.f1084e;
            if (exc == null) {
                resultt = this.f1083d;
            } else {
                throw new RuntimeExecutionException(exc);
            }
        }
        return resultt;
    }

    public final <X extends Throwable> ResultT getResult(Class<X> cls) throws Throwable {
        ResultT resultt;
        synchronized (this.f1080a) {
            m1099a();
            if (!cls.isInstance(this.f1084e)) {
                Exception exc = this.f1084e;
                if (exc == null) {
                    resultt = this.f1083d;
                } else {
                    throw new RuntimeExecutionException(exc);
                }
            } else {
                throw ((Throwable) cls.cast(this.f1084e));
            }
        }
        return resultt;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.f1080a) {
            z = this.f1082c;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.f1080a) {
            z = false;
            if (this.f1082c && this.f1084e == null) {
                z = true;
            }
        }
        return z;
    }
}
