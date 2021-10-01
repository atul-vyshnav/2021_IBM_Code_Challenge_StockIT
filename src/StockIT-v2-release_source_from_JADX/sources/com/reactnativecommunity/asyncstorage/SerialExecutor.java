package com.reactnativecommunity.asyncstorage;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {
    private final Executor executor;
    private Runnable mActive;
    private final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();

    public SerialExecutor(Executor executor2) {
        this.executor = executor2;
    }

    public synchronized void execute(final Runnable runnable) {
        this.mTasks.offer(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } finally {
                    SerialExecutor.this.scheduleNext();
                }
            }
        });
        if (this.mActive == null) {
            scheduleNext();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void scheduleNext() {
        Runnable poll = this.mTasks.poll();
        this.mActive = poll;
        if (poll != null) {
            this.executor.execute(poll);
        }
    }
}
