package com.facebook.common.references;

import android.graphics.Bitmap;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public abstract class CloseableReference<T> implements Cloneable, Closeable {
    private static final ResourceReleaser<Closeable> DEFAULT_CLOSEABLE_RELEASER = new ResourceReleaser<Closeable>() {
        public void release(Closeable closeable) {
            try {
                Closeables.close(closeable, true);
            } catch (IOException unused) {
            }
        }
    };
    private static final LeakHandler DEFAULT_LEAK_HANDLER = new LeakHandler() {
        public boolean requiresStacktrace() {
            return false;
        }

        public void reportLeak(SharedReference<Object> sharedReference, @Nullable Throwable th) {
            FLog.m102w((Class<?>) CloseableReference.TAG, "Finalized without closing: %x %x (type = %s)", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(sharedReference)), sharedReference.get().getClass().getName());
        }
    };
    public static final int REF_TYPE_DEFAULT = 0;
    public static final int REF_TYPE_FINALIZER = 1;
    public static final int REF_TYPE_NOOP = 3;
    public static final int REF_TYPE_REF_COUNT = 2;
    /* access modifiers changed from: private */
    public static Class<CloseableReference> TAG = CloseableReference.class;
    private static int sBitmapCloseableRefType;
    protected boolean mIsClosed = false;
    protected final LeakHandler mLeakHandler;
    protected final SharedReference<T> mSharedReference;
    @Nullable
    protected final Throwable mStacktrace;

    public @interface CloseableRefType {
    }

    public interface LeakHandler {
        void reportLeak(SharedReference<Object> sharedReference, @Nullable Throwable th);

        boolean requiresStacktrace();
    }

    public abstract CloseableReference<T> clone();

    public static boolean useGc() {
        return sBitmapCloseableRefType == 3;
    }

    public static void setDisableCloseableReferencesForBitmaps(int i) {
        sBitmapCloseableRefType = i;
    }

    protected CloseableReference(SharedReference<T> sharedReference, LeakHandler leakHandler, @Nullable Throwable th) {
        this.mSharedReference = (SharedReference) Preconditions.checkNotNull(sharedReference);
        sharedReference.addReference();
        this.mLeakHandler = leakHandler;
        this.mStacktrace = th;
    }

    protected CloseableReference(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler, @Nullable Throwable th) {
        this.mSharedReference = new SharedReference<>(t, resourceReleaser);
        this.mLeakHandler = leakHandler;
        this.mStacktrace = th;
    }

    /* renamed from: of */
    public static <T extends Closeable> CloseableReference<T> m128of(T t) {
        return m130of(t, DEFAULT_CLOSEABLE_RELEASER);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m130of(T t, ResourceReleaser<T> resourceReleaser) {
        return m131of(t, resourceReleaser, DEFAULT_LEAK_HANDLER);
    }

    /* renamed from: of */
    public static <T extends Closeable> CloseableReference<T> m129of(T t, LeakHandler leakHandler) {
        Throwable th = null;
        if (t == null) {
            return null;
        }
        ResourceReleaser<Closeable> resourceReleaser = DEFAULT_CLOSEABLE_RELEASER;
        if (leakHandler.requiresStacktrace()) {
            th = new Throwable();
        }
        return m132of(t, resourceReleaser, leakHandler, th);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m131of(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler) {
        Throwable th = null;
        if (t == null) {
            return null;
        }
        if (leakHandler.requiresStacktrace()) {
            th = new Throwable();
        }
        return m132of(t, resourceReleaser, leakHandler, th);
    }

    /* renamed from: of */
    public static <T> CloseableReference<T> m132of(T t, ResourceReleaser<T> resourceReleaser, LeakHandler leakHandler, @Nullable Throwable th) {
        if (t == null) {
            return null;
        }
        if ((t instanceof Bitmap) || (t instanceof HasBitmap)) {
            int i = sBitmapCloseableRefType;
            if (i == 1) {
                return new FinalizerCloseableReference(t, resourceReleaser, leakHandler, th);
            }
            if (i == 2) {
                return new RefCountCloseableReference(t, resourceReleaser, leakHandler, th);
            }
            if (i == 3) {
                return new NoOpCloseableReference(t, resourceReleaser, leakHandler, th);
            }
        }
        return new DefaultCloseableReference(t, resourceReleaser, leakHandler, th);
    }

    public synchronized T get() {
        Preconditions.checkState(!this.mIsClosed);
        return this.mSharedReference.get();
    }

    @Nullable
    public synchronized CloseableReference<T> cloneOrNull() {
        if (!isValid()) {
            return null;
        }
        return clone();
    }

    public synchronized boolean isValid() {
        return !this.mIsClosed;
    }

    public synchronized SharedReference<T> getUnderlyingReferenceTestOnly() {
        return this.mSharedReference;
    }

    public int getValueHash() {
        if (isValid()) {
            return System.identityHashCode(this.mSharedReference.get());
        }
        return 0;
    }

    public void close() {
        synchronized (this) {
            if (!this.mIsClosed) {
                this.mIsClosed = true;
                this.mSharedReference.deleteReference();
            }
        }
    }

    public static boolean isValid(@Nullable CloseableReference<?> closeableReference) {
        return closeableReference != null && closeableReference.isValid();
    }

    @Nullable
    public static <T> CloseableReference<T> cloneOrNull(@Nullable CloseableReference<T> closeableReference) {
        if (closeableReference != null) {
            return closeableReference.cloneOrNull();
        }
        return null;
    }

    public static <T> List<CloseableReference<T>> cloneOrNull(Collection<CloseableReference<T>> collection) {
        if (collection == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (CloseableReference<T> cloneOrNull : collection) {
            arrayList.add(cloneOrNull(cloneOrNull));
        }
        return arrayList;
    }

    public static void closeSafely(@Nullable CloseableReference<?> closeableReference) {
        if (closeableReference != null) {
            closeableReference.close();
        }
    }

    public static void closeSafely(@Nullable Iterable<? extends CloseableReference<?>> iterable) {
        if (iterable != null) {
            for (CloseableReference closeSafely : iterable) {
                closeSafely((CloseableReference<?>) closeSafely);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            synchronized (this) {
                if (this.mIsClosed) {
                    super.finalize();
                    return;
                }
                this.mLeakHandler.reportLeak(this.mSharedReference, this.mStacktrace);
                close();
                super.finalize();
            }
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }
}
