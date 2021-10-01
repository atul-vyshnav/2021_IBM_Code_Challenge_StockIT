package com.facebook.common.references;

import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

public class FinalizerCloseableReference<T> extends CloseableReference<T> {
    private static final String TAG = "FinalizerCloseableReference";

    public CloseableReference<T> clone() {
        return this;
    }

    public void close() {
    }

    FinalizerCloseableReference(T t, ResourceReleaser<T> resourceReleaser, CloseableReference.LeakHandler leakHandler, @Nullable Throwable th) {
        super(t, resourceReleaser, leakHandler, th);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            synchronized (this) {
                if (this.mIsClosed) {
                    super.finalize();
                    return;
                }
                FLog.m106w(TAG, "Finalized without closing: %x %x (type = %s)", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.mSharedReference)), this.mSharedReference.get().getClass().getName());
                this.mSharedReference.deleteReference();
                super.finalize();
            }
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }
}
