package com.amplitude.api;

import android.os.Handler;
import android.os.HandlerThread;

public class WorkerThread extends HandlerThread {
    private Handler handler;

    public WorkerThread(String str) {
        super(str);
    }

    /* access modifiers changed from: package-private */
    public Handler getHandler() {
        return this.handler;
    }

    /* access modifiers changed from: package-private */
    public void post(Runnable runnable) {
        waitForInitialization();
        this.handler.post(runnable);
    }

    /* access modifiers changed from: package-private */
    public void postDelayed(Runnable runnable, long j) {
        waitForInitialization();
        this.handler.postDelayed(runnable, j);
    }

    /* access modifiers changed from: package-private */
    public void removeCallbacks(Runnable runnable) {
        waitForInitialization();
        this.handler.removeCallbacks(runnable);
    }

    private synchronized void waitForInitialization() {
        if (this.handler == null) {
            this.handler = new Handler(getLooper());
        }
    }
}
