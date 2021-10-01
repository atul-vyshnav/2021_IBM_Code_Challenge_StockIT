package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.view.Surface;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

public interface VideoRendererEventListener {
    void onDroppedFrames(int i, long j);

    void onRenderedFirstFrame(Surface surface);

    void onVideoDecoderInitialized(String str, long j, long j2);

    void onVideoDisabled(DecoderCounters decoderCounters);

    void onVideoEnabled(DecoderCounters decoderCounters);

    void onVideoInputFormatChanged(Format format);

    void onVideoSizeChanged(int i, int i2, int i3, float f);

    public static final class EventDispatcher {
        private final Handler handler;
        private final VideoRendererEventListener listener;

        public EventDispatcher(Handler handler2, VideoRendererEventListener videoRendererEventListener) {
            this.handler = videoRendererEventListener != null ? (Handler) Assertions.checkNotNull(handler2) : null;
            this.listener = videoRendererEventListener;
        }

        public void enabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable(decoderCounters) {
                    public final /* synthetic */ DecoderCounters f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.lambda$enabled$0$VideoRendererEventListener$EventDispatcher(this.f$1);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$enabled$0$VideoRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            this.listener.onVideoEnabled(decoderCounters);
        }

        public void decoderInitialized(String str, long j, long j2) {
            if (this.listener != null) {
                this.handler.post(new Runnable(str, j, j2) {
                    public final /* synthetic */ String f$1;
                    public final /* synthetic */ long f$2;
                    public final /* synthetic */ long f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r5;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.mo20319x9a08f997(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderInitialized$1$VideoRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo20319x9a08f997(String str, long j, long j2) {
            this.listener.onVideoDecoderInitialized(str, j, j2);
        }

        public void inputFormatChanged(Format format) {
            if (this.listener != null) {
                this.handler.post(new Runnable(format) {
                    public final /* synthetic */ Format f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.mo20323xe7570b3(this.f$1);
                    }
                });
            }
        }

        /* renamed from: lambda$inputFormatChanged$2$VideoRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo20323xe7570b3(Format format) {
            this.listener.onVideoInputFormatChanged(format);
        }

        public void droppedFrames(int i, long j) {
            if (this.listener != null) {
                this.handler.post(new Runnable(i, j) {
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ long f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.mo20321xf7e95759(this.f$1, this.f$2);
                    }
                });
            }
        }

        /* renamed from: lambda$droppedFrames$3$VideoRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo20321xf7e95759(int i, long j) {
            this.listener.onDroppedFrames(i, j);
        }

        public void videoSizeChanged(int i, int i2, int i3, float f) {
            if (this.listener != null) {
                this.handler.post(new Runnable(i, i2, i3, f) {
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ int f$2;
                    public final /* synthetic */ int f$3;
                    public final /* synthetic */ float f$4;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.mo20325x6ff94f6c(this.f$1, this.f$2, this.f$3, this.f$4);
                    }
                });
            }
        }

        /* renamed from: lambda$videoSizeChanged$4$VideoRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo20325x6ff94f6c(int i, int i2, int i3, float f) {
            this.listener.onVideoSizeChanged(i, i2, i3, f);
        }

        public void renderedFirstFrame(Surface surface) {
            if (this.listener != null) {
                this.handler.post(new Runnable(surface) {
                    public final /* synthetic */ Surface f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.mo20324x44bb7f11(this.f$1);
                    }
                });
            }
        }

        /* renamed from: lambda$renderedFirstFrame$5$VideoRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo20324x44bb7f11(Surface surface) {
            this.listener.onRenderedFirstFrame(surface);
        }

        public void disabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable(decoderCounters) {
                    public final /* synthetic */ DecoderCounters f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        VideoRendererEventListener.EventDispatcher.this.lambda$disabled$6$VideoRendererEventListener$EventDispatcher(this.f$1);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$disabled$6$VideoRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            this.listener.onVideoDisabled(decoderCounters);
        }
    }
}
