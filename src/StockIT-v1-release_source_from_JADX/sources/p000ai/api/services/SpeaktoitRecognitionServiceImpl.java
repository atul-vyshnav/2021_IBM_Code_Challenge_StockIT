package p000ai.api.services;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p000ai.api.AIServiceException;
import p000ai.api.RequestExtras;
import p000ai.api.android.AIConfiguration;
import p000ai.api.android.AIService;
import p000ai.api.model.AIContext;
import p000ai.api.model.AIError;
import p000ai.api.model.AIResponse;
import p000ai.api.model.Entity;
import p000ai.api.util.VoiceActivityDetector;

@Deprecated
/* renamed from: ai.api.services.SpeaktoitRecognitionServiceImpl */
public class SpeaktoitRecognitionServiceImpl extends AIService implements VoiceActivityDetector.SpeechEventsListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final int AUDIO_FORMAT = 2;
    private static final int CHANNEL_CONFIG = 16;
    private static final int SAMPLE_RATE_IN_HZ = 16000;
    public static final String TAG = SpeaktoitRecognitionServiceImpl.class.getName();
    private AudioRecord audioRecord;
    private final ExecutorService eventsExecutor = Executors.newSingleThreadExecutor();
    private RequestExtras extras;
    private volatile boolean isRecording = false;
    private MediaPlayer mediaPlayer;
    private RecognizeTask recognizeTask;
    private final Object recognizerLock = new Object();
    /* access modifiers changed from: private */
    public final VoiceActivityDetector vad = new VoiceActivityDetector(SAMPLE_RATE_IN_HZ);

    public void onSpeechBegin() {
    }

    public SpeaktoitRecognitionServiceImpl(Context context, AIConfiguration aIConfiguration) {
        super(aIConfiguration, context);
        init();
    }

    private void init() {
        synchronized (this.recognizerLock) {
            this.audioRecord = new AudioRecord(1, SAMPLE_RATE_IN_HZ, 16, 2, AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, 16, 2));
            this.vad.setEnabled(this.config.isVoiceActivityDetectionEnabled());
            this.vad.setSpeechListener(this);
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mediaPlayer = mediaPlayer2;
            mediaPlayer2.setOnErrorListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
        }
    }

    public void startListening() {
        startListening(new RequestExtras());
    }

    public void startListening(List<AIContext> list) {
        startListening(new RequestExtras(list, (List<Entity>) null));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startListening(p000ai.api.RequestExtras r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.recognizerLock
            monitor-enter(r0)
            boolean r1 = r2.isRecording     // Catch:{ all -> 0x0041 }
            if (r1 != 0) goto L_0x0038
            boolean r1 = r2.checkPermissions()     // Catch:{ all -> 0x0041 }
            if (r1 != 0) goto L_0x0019
            ai.api.model.AIError r3 = new ai.api.model.AIError     // Catch:{ all -> 0x0041 }
            java.lang.String r1 = "RECORD_AUDIO permission is denied. Please request permission from user."
            r3.<init>((java.lang.String) r1)     // Catch:{ all -> 0x0041 }
            r2.onError(r3)     // Catch:{ all -> 0x0041 }
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            return
        L_0x0019:
            r1 = 1
            r2.isRecording = r1     // Catch:{ all -> 0x0041 }
            r2.extras = r3     // Catch:{ all -> 0x0041 }
            ai.api.android.AIConfiguration r3 = r2.config     // Catch:{ all -> 0x0041 }
            android.content.res.AssetFileDescriptor r3 = r3.getRecognizerStartSound()     // Catch:{ all -> 0x0041 }
            if (r3 == 0) goto L_0x0032
            boolean r3 = r2.playSound(r3)     // Catch:{ all -> 0x0041 }
            if (r3 != 0) goto L_0x003f
            ai.api.RequestExtras r3 = r2.extras     // Catch:{ all -> 0x0041 }
            r2.startRecording(r3)     // Catch:{ all -> 0x0041 }
            goto L_0x003f
        L_0x0032:
            ai.api.RequestExtras r3 = r2.extras     // Catch:{ all -> 0x0041 }
            r2.startRecording(r3)     // Catch:{ all -> 0x0041 }
            goto L_0x003f
        L_0x0038:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0041 }
            java.lang.String r1 = "Trying start listening when it already active"
            android.util.Log.w(r3, r1)     // Catch:{ all -> 0x0041 }
        L_0x003f:
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            return
        L_0x0041:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0041 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.services.SpeaktoitRecognitionServiceImpl.startListening(ai.api.RequestExtras):void");
    }

    private void startRecording(RequestExtras requestExtras) {
        this.vad.reset();
        this.audioRecord.startRecording();
        onListeningStarted();
        RecognizeTask recognizeTask2 = new RecognizeTask(new RecorderStream(this.audioRecord), requestExtras);
        this.recognizeTask = recognizeTask2;
        recognizeTask2.execute(new Void[0]);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:10|11) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        android.util.Log.w(TAG, "Attempt to stop audioRecord when it is stopped");
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stopListening() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.recognizerLock
            monitor-enter(r0)
            boolean r1 = r3.isRecording     // Catch:{ all -> 0x0027 }
            if (r1 == 0) goto L_0x0025
            android.media.AudioRecord r1 = r3.audioRecord     // Catch:{ IllegalStateException -> 0x001e }
            r1.stop()     // Catch:{ IllegalStateException -> 0x001e }
            r1 = 0
            r3.isRecording = r1     // Catch:{ IllegalStateException -> 0x001e }
            ai.api.android.AIConfiguration r1 = r3.config     // Catch:{ IllegalStateException -> 0x001e }
            android.content.res.AssetFileDescriptor r1 = r1.getRecognizerStopSound()     // Catch:{ IllegalStateException -> 0x001e }
            if (r1 == 0) goto L_0x001a
            r3.playSound(r1)     // Catch:{ IllegalStateException -> 0x001e }
        L_0x001a:
            r3.onListeningFinished()     // Catch:{ IllegalStateException -> 0x001e }
            goto L_0x0025
        L_0x001e:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0027 }
            java.lang.String r2 = "Attempt to stop audioRecord when it is stopped"
            android.util.Log.w(r1, r2)     // Catch:{ all -> 0x0027 }
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x0027 }
            return
        L_0x0027:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0027 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.services.SpeaktoitRecognitionServiceImpl.stopListening():void");
    }

    public void cancel() {
        synchronized (this.recognizerLock) {
            if (this.isRecording) {
                this.audioRecord.stop();
                this.isRecording = false;
                AssetFileDescriptor recognizerCancelSound = this.config.getRecognizerCancelSound();
                if (recognizerCancelSound != null) {
                    playSound(recognizerCancelSound);
                }
            }
            if (this.recognizeTask != null) {
                this.recognizeTask.cancel(true);
            }
            onListeningCancelled();
        }
    }

    public void pause() {
        synchronized (this.recognizerLock) {
            if (this.isRecording) {
                this.audioRecord.stop();
                this.isRecording = false;
            }
            this.audioRecord.release();
            this.audioRecord = null;
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    public void resume() {
        init();
    }

    private boolean playSound(AssetFileDescriptor assetFileDescriptor) {
        try {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public void onSpeechEnd() {
        this.eventsExecutor.submit(new Runnable() {
            public void run() {
                SpeaktoitRecognitionServiceImpl.this.stopListening();
            }
        });
    }

    public void onSpeechCancel() {
        this.eventsExecutor.submit(new Runnable() {
            public void run() {
                SpeaktoitRecognitionServiceImpl.this.cancel();
            }
        });
    }

    /* renamed from: ai.api.services.SpeaktoitRecognitionServiceImpl$RecorderStream */
    private class RecorderStream extends InputStream {
        float alignment;
        private final AudioRecord audioRecord;
        private byte[] bytes;
        private final Object bytesLock;
        float count;
        private final float dbLevel;
        int extent;
        int max;
        int min;
        int offset;

        private RecorderStream(AudioRecord audioRecord2) {
            this.dbLevel = (float) Math.pow(10.0d, -0.05d);
            this.bytesLock = new Object();
            this.offset = 0;
            this.max = 0;
            this.min = 0;
            this.alignment = 0.0f;
            this.count = 1.0f;
            this.audioRecord = audioRecord2;
        }

        public int read() throws IOException {
            byte[] bArr = new byte[1];
            this.audioRecord.read(bArr, 0, 1);
            return bArr[0];
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.audioRecord.read(bArr, i, i2);
            if (read > 0) {
                synchronized (this.bytesLock) {
                    if (SpeaktoitRecognitionServiceImpl.this.config.isNormalizeInputSound()) {
                        normalize(bArr, read);
                    }
                    byte[] bArr2 = this.bytes;
                    int length = bArr2 != null ? bArr2.length : 0;
                    byte[] bArr3 = new byte[(length + read)];
                    this.bytes = bArr3;
                    if (length > 0) {
                        System.arraycopy(bArr2, 0, bArr3, 0, length);
                    }
                    System.arraycopy(bArr, 0, this.bytes, length, read);
                    while (this.bytes.length >= 320) {
                        byte[] bArr4 = new byte[VoiceActivityDetector.FRAME_SIZE_IN_BYTES];
                        System.arraycopy(this.bytes, 0, bArr4, 0, VoiceActivityDetector.FRAME_SIZE_IN_BYTES);
                        SpeaktoitRecognitionServiceImpl.this.vad.processBuffer(bArr4, VoiceActivityDetector.FRAME_SIZE_IN_BYTES);
                        byte[] bArr5 = this.bytes;
                        int length2 = bArr5.length - VoiceActivityDetector.FRAME_SIZE_IN_BYTES;
                        byte[] bArr6 = new byte[length2];
                        this.bytes = bArr6;
                        System.arraycopy(bArr5, VoiceActivityDetector.FRAME_SIZE_IN_BYTES, bArr6, 0, length2);
                    }
                    SpeaktoitRecognitionServiceImpl.this.onAudioLevelChanged((float) SpeaktoitRecognitionServiceImpl.this.vad.calculateRms());
                }
            }
            if (read != 0) {
                return read;
            }
            return -3;
        }

        private void normalize(byte[] bArr, int i) {
            int i2 = 4800 - this.offset;
            if (i >= i2) {
                ByteBuffer order = ByteBuffer.wrap(bArr, i2, i - i2).order(ByteOrder.LITTLE_ENDIAN);
                ShortBuffer asShortBuffer = order.asShortBuffer();
                for (int i3 = 0; i3 < asShortBuffer.limit(); i3++) {
                    short s = asShortBuffer.get(i3);
                    this.max = Math.max(this.max, s);
                    this.min = Math.min(this.min, s);
                    float f = this.count;
                    this.alignment = (((f - 1.0f) / f) * this.alignment) + (((float) s) / f);
                    this.count = f + 1.0f;
                }
                int max2 = Math.max(Math.abs(this.max), Math.abs(this.min));
                this.extent = max2;
                float f2 = (this.dbLevel * 32767.0f) / ((float) max2);
                for (int i4 = 0; i4 < asShortBuffer.limit(); i4++) {
                    order.putShort((short) ((int) ((((float) asShortBuffer.get(i4)) - this.alignment) * f2)));
                }
            }
            this.offset += Math.min(i, i2);
        }
    }

    /* renamed from: ai.api.services.SpeaktoitRecognitionServiceImpl$RecognizeTask */
    private class RecognizeTask extends AsyncTask<Void, Void, AIResponse> {
        private AIError aiError;
        private final RecorderStream recorderStream;
        private final RequestExtras requestExtras;

        private RecognizeTask(RecorderStream recorderStream2, RequestExtras requestExtras2) {
            this.recorderStream = recorderStream2;
            this.requestExtras = requestExtras2;
        }

        /* access modifiers changed from: protected */
        public AIResponse doInBackground(Void... voidArr) {
            try {
                return SpeaktoitRecognitionServiceImpl.this.aiDataService.voiceRequest((InputStream) this.recorderStream, this.requestExtras);
            } catch (AIServiceException e) {
                this.aiError = new AIError(e);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(AIResponse aIResponse) {
            if (!isCancelled()) {
                if (aIResponse != null) {
                    SpeaktoitRecognitionServiceImpl.this.onResult(aIResponse);
                    return;
                }
                SpeaktoitRecognitionServiceImpl.this.cancel();
                SpeaktoitRecognitionServiceImpl.this.onError(this.aiError);
            }
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer2) {
        if (this.isRecording) {
            startRecording(this.extras);
        }
    }

    public boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
        if (!this.isRecording) {
            return false;
        }
        startRecording(this.extras);
        return false;
    }
}
