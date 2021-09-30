package com.google.android.play.core.tasks;

import com.google.android.play.core.internal.C1934av;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {
    private Tasks() {
    }

    /* renamed from: a */
    public static <ResultT> Task<ResultT> m1078a(Exception exc) {
        C2110m mVar = new C2110m();
        mVar.mo34401a(exc);
        return mVar;
    }

    /* renamed from: a */
    public static <ResultT> Task<ResultT> m1079a(ResultT resultt) {
        C2110m mVar = new C2110m();
        mVar.mo34402a(resultt);
        return mVar;
    }

    /* renamed from: a */
    private static <ResultT> ResultT m1080a(Task<ResultT> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    /* renamed from: a */
    private static void m1081a(Task<?> task, C2111n nVar) {
        task.addOnSuccessListener(TaskExecutors.f1059a, nVar);
        task.addOnFailureListener(TaskExecutors.f1059a, nVar);
    }

    public static <ResultT> ResultT await(Task<ResultT> task) throws ExecutionException, InterruptedException {
        C1934av.m656a(task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return m1080a(task);
        }
        C2111n nVar = new C2111n((byte[]) null);
        m1081a(task, nVar);
        nVar.mo34405a();
        return m1080a(task);
    }

    public static <ResultT> ResultT await(Task<ResultT> task, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        C1934av.m656a(task, (Object) "Task must not be null");
        C1934av.m656a(timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return m1080a(task);
        }
        C2111n nVar = new C2111n((byte[]) null);
        m1081a(task, nVar);
        if (nVar.mo34406a(j, timeUnit)) {
            return m1080a(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }
}
