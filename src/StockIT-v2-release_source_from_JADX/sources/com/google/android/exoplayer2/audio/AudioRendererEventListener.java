package com.google.android.exoplayer2.audio;

import android.os.Handler;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Assertions;

public interface AudioRendererEventListener {
    void onAudioDecoderInitialized(String str, long j, long j2);

    void onAudioDisabled(DecoderCounters decoderCounters);

    void onAudioEnabled(DecoderCounters decoderCounters);

    void onAudioInputFormatChanged(Format format);

    void onAudioSessionId(int i);

    void onAudioSinkUnderrun(int i, long j, long j2);

    public static final class EventDispatcher {
        private final Handler handler;
        private final AudioRendererEventListener listener;

        public EventDispatcher(Handler handler2, AudioRendererEventListener audioRendererEventListener) {
            this.handler = audioRendererEventListener != null ? (Handler) Assertions.checkNotNull(handler2) : null;
            this.listener = audioRendererEventListener;
        }

        public void enabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable(decoderCounters) {
                    public final /* synthetic */ DecoderCounters f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AudioRendererEventListener.EventDispatcher.this.lambda$enabled$0$AudioRendererEventListener$EventDispatcher(this.f$1);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$enabled$0$AudioRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            this.listener.onAudioEnabled(decoderCounters);
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
                        AudioRendererEventListener.EventDispatcher.this.mo18145xba417f1c(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        /* renamed from: lambda$decoderInitialized$1$AudioRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo18145xba417f1c(String str, long j, long j2) {
            this.listener.onAudioDecoderInitialized(str, j, j2);
        }

        public void inputFormatChanged(Format format) {
            if (this.listener != null) {
                this.handler.post(new Runnable(format) {
                    public final /* synthetic */ Format f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AudioRendererEventListener.EventDispatcher.this.mo18148x2eadf638(this.f$1);
                    }
                });
            }
        }

        /* renamed from: lambda$inputFormatChanged$2$AudioRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo18148x2eadf638(Format format) {
            this.listener.onAudioInputFormatChanged(format);
        }

        public void audioTrackUnderrun(int i, long j, long j2) {
            if (this.listener != null) {
                this.handler.post(new Runnable(i, j, j2) {
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ long f$2;
                    public final /* synthetic */ long f$3;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r5;
                    }

                    public final void run() {
                        AudioRendererEventListener.EventDispatcher.this.mo18144xe45e91e2(this.f$1, this.f$2, this.f$3);
                    }
                });
            }
        }

        /* renamed from: lambda$audioTrackUnderrun$3$AudioRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo18144xe45e91e2(int i, long j, long j2) {
            this.listener.onAudioSinkUnderrun(i, j, j2);
        }

        public void disabled(DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable(decoderCounters) {
                    public final /* synthetic */ DecoderCounters f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AudioRendererEventListener.EventDispatcher.this.lambda$disabled$4$AudioRendererEventListener$EventDispatcher(this.f$1);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$disabled$4$AudioRendererEventListener$EventDispatcher(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            this.listener.onAudioDisabled(decoderCounters);
        }

        public void audioSessionId(int i) {
            if (this.listener != null) {
                this.handler.post(new Runnable(i) {
                    public final /* synthetic */ int f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        AudioRendererEventListener.EventDispatcher.this.mo18143xc1c634cd(this.f$1);
                    }
                });
            }
        }

        /* renamed from: lambda$audioSessionId$5$AudioRendererEventListener$EventDispatcher */
        public /* synthetic */ void mo18143xc1c634cd(int i) {
            this.listener.onAudioSessionId(i);
        }
    }
}
