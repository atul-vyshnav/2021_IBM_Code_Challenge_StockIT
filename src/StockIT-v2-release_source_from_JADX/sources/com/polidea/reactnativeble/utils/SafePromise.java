package com.polidea.reactnativeble.utils;

import com.facebook.react.bridge.Promise;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class SafePromise {
    private AtomicBoolean isFinished = new AtomicBoolean();
    private Promise promise;

    public SafePromise(Promise promise2) {
        this.promise = promise2;
    }

    public void resolve(@Nullable Object obj) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.resolve(obj);
        }
    }

    public void reject(String str, String str2) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.reject(str, str2);
        }
    }

    public void reject(String str, Throwable th) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.reject(str, th);
        }
    }

    public void reject(String str, String str2, Throwable th) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.reject(str, str2, th);
        }
    }

    @Deprecated
    public void reject(String str) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.reject(str);
        }
    }

    public void reject(Throwable th) {
        if (this.isFinished.compareAndSet(false, true)) {
            this.promise.reject(th);
        }
    }
}
