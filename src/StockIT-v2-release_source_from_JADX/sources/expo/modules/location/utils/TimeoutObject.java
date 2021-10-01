package expo.modules.location.utils;

import android.os.Handler;

public class TimeoutObject {
    /* access modifiers changed from: private */
    public boolean mIsDone = false;
    /* access modifiers changed from: private */
    public TimeoutListener mListener;
    private Long mTimeout;

    public interface TimeoutListener {
        void onTimeout();
    }

    public TimeoutObject(Long l) {
        this.mTimeout = l;
    }

    public void onTimeout(TimeoutListener timeoutListener) {
        this.mListener = timeoutListener;
    }

    public void start() {
        if (this.mTimeout != null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    synchronized (this) {
                        if (!TimeoutObject.this.mIsDone && TimeoutObject.this.mListener != null) {
                            TimeoutObject.this.mListener.onTimeout();
                        }
                        boolean unused = TimeoutObject.this.mIsDone = true;
                    }
                }
            }, this.mTimeout.longValue());
        }
    }

    public boolean markDoneIfNotTimedOut() {
        synchronized (this) {
            if (this.mIsDone) {
                return false;
            }
            this.mIsDone = true;
            return true;
        }
    }
}
