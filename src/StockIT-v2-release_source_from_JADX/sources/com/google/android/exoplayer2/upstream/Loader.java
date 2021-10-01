package com.google.android.exoplayer2.upstream;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public final class Loader implements LoaderErrorThrower {
    private static final int ACTION_TYPE_DONT_RETRY = 2;
    private static final int ACTION_TYPE_DONT_RETRY_FATAL = 3;
    private static final int ACTION_TYPE_RETRY = 0;
    private static final int ACTION_TYPE_RETRY_AND_RESET_ERROR_COUNT = 1;
    public static final LoadErrorAction DONT_RETRY = new LoadErrorAction(2, C1379C.TIME_UNSET);
    public static final LoadErrorAction DONT_RETRY_FATAL = new LoadErrorAction(3, C1379C.TIME_UNSET);
    public static final LoadErrorAction RETRY = createRetryAction(false, C1379C.TIME_UNSET);
    public static final LoadErrorAction RETRY_RESET_ERROR_COUNT = createRetryAction(true, C1379C.TIME_UNSET);
    /* access modifiers changed from: private */
    public LoadTask<? extends Loadable> currentTask;
    /* access modifiers changed from: private */
    public final ExecutorService downloadExecutorService;
    /* access modifiers changed from: private */
    public IOException fatalError;

    public interface Callback<T extends Loadable> {
        void onLoadCanceled(T t, long j, long j2, boolean z);

        void onLoadCompleted(T t, long j, long j2);

        LoadErrorAction onLoadError(T t, long j, long j2, IOException iOException, int i);
    }

    public interface Loadable {
        void cancelLoad();

        void load() throws IOException, InterruptedException;
    }

    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    public static final class UnexpectedLoaderException extends IOException {
        public UnexpectedLoaderException(Throwable th) {
            super("Unexpected " + th.getClass().getSimpleName() + ": " + th.getMessage(), th);
        }
    }

    public static final class LoadErrorAction {
        /* access modifiers changed from: private */
        public final long retryDelayMillis;
        /* access modifiers changed from: private */
        public final int type;

        private LoadErrorAction(int i, long j) {
            this.type = i;
            this.retryDelayMillis = j;
        }

        public boolean isRetry() {
            int i = this.type;
            return i == 0 || i == 1;
        }
    }

    public Loader(String str) {
        this.downloadExecutorService = Util.newSingleThreadExecutor(str);
    }

    public static LoadErrorAction createRetryAction(boolean z, long j) {
        return new LoadErrorAction(z ? 1 : 0, j);
    }

    public <T extends Loadable> long startLoading(T t, Callback<T> callback, int i) {
        Looper myLooper = Looper.myLooper();
        Assertions.checkState(myLooper != null);
        this.fatalError = null;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        new LoadTask(myLooper, t, callback, i, elapsedRealtime).start(0);
        return elapsedRealtime;
    }

    public boolean isLoading() {
        return this.currentTask != null;
    }

    public void cancelLoading() {
        this.currentTask.cancel(false);
    }

    public void release() {
        release((ReleaseCallback) null);
    }

    public void release(ReleaseCallback releaseCallback) {
        LoadTask<? extends Loadable> loadTask = this.currentTask;
        if (loadTask != null) {
            loadTask.cancel(true);
        }
        if (releaseCallback != null) {
            this.downloadExecutorService.execute(new ReleaseTask(releaseCallback));
        }
        this.downloadExecutorService.shutdown();
    }

    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    public void maybeThrowError(int i) throws IOException {
        IOException iOException = this.fatalError;
        if (iOException == null) {
            LoadTask<? extends Loadable> loadTask = this.currentTask;
            if (loadTask != null) {
                if (i == Integer.MIN_VALUE) {
                    i = loadTask.defaultMinRetryCount;
                }
                loadTask.maybeThrowError(i);
                return;
            }
            return;
        }
        throw iOException;
    }

    private final class LoadTask<T extends Loadable> extends Handler implements Runnable {
        private static final int MSG_CANCEL = 1;
        private static final int MSG_END_OF_SOURCE = 2;
        private static final int MSG_FATAL_ERROR = 4;
        private static final int MSG_IO_EXCEPTION = 3;
        private static final int MSG_START = 0;
        private static final String TAG = "LoadTask";
        private Callback<T> callback;
        private volatile boolean canceled;
        private IOException currentError;
        public final int defaultMinRetryCount;
        private int errorCount;
        private volatile Thread executorThread;
        private final T loadable;
        private volatile boolean released;
        private final long startTimeMs;

        public LoadTask(Looper looper, T t, Callback<T> callback2, int i, long j) {
            super(looper);
            this.loadable = t;
            this.callback = callback2;
            this.defaultMinRetryCount = i;
            this.startTimeMs = j;
        }

        public void maybeThrowError(int i) throws IOException {
            IOException iOException = this.currentError;
            if (iOException != null && this.errorCount > i) {
                throw iOException;
            }
        }

        public void start(long j) {
            Assertions.checkState(Loader.this.currentTask == null);
            LoadTask unused = Loader.this.currentTask = this;
            if (j > 0) {
                sendEmptyMessageDelayed(0, j);
            } else {
                execute();
            }
        }

        public void cancel(boolean z) {
            this.released = z;
            this.currentError = null;
            if (hasMessages(0)) {
                removeMessages(0);
                if (!z) {
                    sendEmptyMessage(1);
                }
            } else {
                this.canceled = true;
                this.loadable.cancelLoad();
                if (this.executorThread != null) {
                    this.executorThread.interrupt();
                }
            }
            if (z) {
                finish();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.callback.onLoadCanceled(this.loadable, elapsedRealtime, elapsedRealtime - this.startTimeMs, true);
                this.callback = null;
            }
        }

        public void run() {
            try {
                this.executorThread = Thread.currentThread();
                if (!this.canceled) {
                    TraceUtil.beginSection("load:" + this.loadable.getClass().getSimpleName());
                    this.loadable.load();
                    TraceUtil.endSection();
                }
                if (!this.released) {
                    sendEmptyMessage(2);
                }
            } catch (IOException e) {
                if (!this.released) {
                    obtainMessage(3, e).sendToTarget();
                }
            } catch (InterruptedException unused) {
                Assertions.checkState(this.canceled);
                if (!this.released) {
                    sendEmptyMessage(2);
                }
            } catch (Exception e2) {
                Log.m170e(TAG, "Unexpected exception loading stream", e2);
                if (!this.released) {
                    obtainMessage(3, new UnexpectedLoaderException(e2)).sendToTarget();
                }
            } catch (OutOfMemoryError e3) {
                Log.m170e(TAG, "OutOfMemory error loading stream", e3);
                if (!this.released) {
                    obtainMessage(3, new UnexpectedLoaderException(e3)).sendToTarget();
                }
            } catch (Error e4) {
                Log.m170e(TAG, "Unexpected error loading stream", e4);
                if (!this.released) {
                    obtainMessage(4, e4).sendToTarget();
                }
                throw e4;
            } catch (Throwable th) {
                TraceUtil.endSection();
                throw th;
            }
        }

        public void handleMessage(Message message) {
            long j;
            if (!this.released) {
                if (message.what == 0) {
                    execute();
                } else if (message.what != 4) {
                    finish();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long j2 = elapsedRealtime - this.startTimeMs;
                    if (this.canceled) {
                        this.callback.onLoadCanceled(this.loadable, elapsedRealtime, j2, false);
                        return;
                    }
                    int i = message.what;
                    if (i == 1) {
                        this.callback.onLoadCanceled(this.loadable, elapsedRealtime, j2, false);
                    } else if (i == 2) {
                        try {
                            this.callback.onLoadCompleted(this.loadable, elapsedRealtime, j2);
                        } catch (RuntimeException e) {
                            Log.m170e(TAG, "Unexpected exception handling load completed", e);
                            IOException unused = Loader.this.fatalError = new UnexpectedLoaderException(e);
                        }
                    } else if (i == 3) {
                        IOException iOException = (IOException) message.obj;
                        this.currentError = iOException;
                        int i2 = this.errorCount + 1;
                        this.errorCount = i2;
                        LoadErrorAction onLoadError = this.callback.onLoadError(this.loadable, elapsedRealtime, j2, iOException, i2);
                        if (onLoadError.type == 3) {
                            IOException unused2 = Loader.this.fatalError = this.currentError;
                        } else if (onLoadError.type != 2) {
                            if (onLoadError.type == 1) {
                                this.errorCount = 1;
                            }
                            if (onLoadError.retryDelayMillis != C1379C.TIME_UNSET) {
                                j = onLoadError.retryDelayMillis;
                            } else {
                                j = getRetryDelayMillis();
                            }
                            start(j);
                        }
                    }
                } else {
                    throw ((Error) message.obj);
                }
            }
        }

        private void execute() {
            this.currentError = null;
            Loader.this.downloadExecutorService.execute(Loader.this.currentTask);
        }

        private void finish() {
            LoadTask unused = Loader.this.currentTask = null;
        }

        private long getRetryDelayMillis() {
            return (long) Math.min((this.errorCount - 1) * 1000, 5000);
        }
    }

    private static final class ReleaseTask implements Runnable {
        private final ReleaseCallback callback;

        public ReleaseTask(ReleaseCallback releaseCallback) {
            this.callback = releaseCallback;
        }

        public void run() {
            this.callback.onLoaderReleased();
        }
    }
}
