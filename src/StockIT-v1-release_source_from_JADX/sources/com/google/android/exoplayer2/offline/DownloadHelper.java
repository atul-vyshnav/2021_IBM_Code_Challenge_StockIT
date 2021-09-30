package com.google.android.exoplayer2.offline;

import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.source.TrackGroupArray;
import java.io.IOException;
import java.util.List;

public abstract class DownloadHelper {

    public interface Callback {
        void onPrepareError(DownloadHelper downloadHelper, IOException iOException);

        void onPrepared(DownloadHelper downloadHelper);
    }

    public abstract DownloadAction getDownloadAction(byte[] bArr, List<TrackKey> list);

    public abstract int getPeriodCount();

    public abstract DownloadAction getRemoveAction(byte[] bArr);

    public abstract TrackGroupArray getTrackGroups(int i);

    /* access modifiers changed from: protected */
    public abstract void prepareInternal() throws IOException;

    public void prepare(final Callback callback) {
        final Handler handler = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
        new Thread() {
            public void run() {
                try {
                    DownloadHelper.this.prepareInternal();
                    handler.post(new Runnable(callback) {
                        public final /* synthetic */ DownloadHelper.Callback f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            DownloadHelper.C14401.this.lambda$run$0$DownloadHelper$1(this.f$1);
                        }
                    });
                } catch (IOException e) {
                    handler.post(new Runnable(callback, e) {
                        public final /* synthetic */ DownloadHelper.Callback f$1;
                        public final /* synthetic */ IOException f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void run() {
                            DownloadHelper.C14401.this.lambda$run$1$DownloadHelper$1(this.f$1, this.f$2);
                        }
                    });
                }
            }

            public /* synthetic */ void lambda$run$0$DownloadHelper$1(Callback callback) {
                callback.onPrepared(DownloadHelper.this);
            }

            public /* synthetic */ void lambda$run$1$DownloadHelper$1(Callback callback, IOException iOException) {
                callback.onPrepareError(DownloadHelper.this, iOException);
            }
        }.start();
    }
}
