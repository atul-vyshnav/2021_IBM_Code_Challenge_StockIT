package p000ai.api.util;

import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: ai.api.util.VoiceActivityDetector */
public class VoiceActivityDetector {
    private static final double ENERGY_FACTOR = 3.1d;
    public static final int FRAME_SIZE_IN_BYTES = 320;
    private static final int MAX_CZ = 15;
    private static final long MAX_SILENCE_MILLIS = 3500;
    private static final int MIN_CZ = 5;
    private static final long MIN_SILENCE_MILLIS = 800;
    private static final int MIN_SPEECH_SEQUENCE_COUNT = 3;
    public static final int NOISE_BYTES = 4800;
    private static final int NOISE_FRAMES = 15;
    private static final int SEQUENCE_LENGTH_MILLIS = 30;
    private static final long SILENCE_DIFF_MILLIS = 2700;
    public static final String TAG = VoiceActivityDetector.class.getName();
    private boolean enabled = true;
    private SpeechEventsListener eventsListener;
    private int frameNumber;
    private long lastActiveTime = -1;
    private long lastSequenceTime = 0;
    private double noiseEnergy = 0.0d;
    private boolean process = true;
    private final int sampleRate;
    private int sequenceCounter = 0;
    private long silenceMillis = MAX_SILENCE_MILLIS;
    private int size = 0;
    private boolean speechActive = false;
    private double sum = 0.0d;
    private long time = 0;

    /* renamed from: ai.api.util.VoiceActivityDetector$SpeechEventsListener */
    public interface SpeechEventsListener {
        void onSpeechBegin();

        void onSpeechCancel();

        void onSpeechEnd();
    }

    public VoiceActivityDetector(int i) {
        this.sampleRate = i;
    }

    public void processBuffer(byte[] bArr, int i) {
        if (this.process) {
            boolean isFrameActive = isFrameActive(ByteBuffer.wrap(bArr, 0, i).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer());
            long j = (long) (((this.frameNumber * (i / 2)) * 1000) / this.sampleRate);
            this.time = j;
            if (isFrameActive) {
                long j2 = this.lastActiveTime;
                if (j2 < 0 || j - j2 >= 30) {
                    this.sequenceCounter = 1;
                } else {
                    int i2 = this.sequenceCounter + 1;
                    this.sequenceCounter = i2;
                    if (i2 >= 3) {
                        if (!this.speechActive) {
                            onSpeechBegin();
                        }
                        this.lastSequenceTime = this.time;
                        this.silenceMillis = Math.max(MIN_SILENCE_MILLIS, this.silenceMillis - 675);
                    }
                }
                this.lastActiveTime = this.time;
            } else if (j - this.lastSequenceTime <= this.silenceMillis) {
            } else {
                if (this.speechActive) {
                    onSpeechEnd();
                } else {
                    onSpeechCancel();
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isFrameActive(java.nio.ShortBuffer r15) {
        /*
            r14 = this;
            int r0 = r15.limit()
            int r1 = r14.size
            int r1 = r1 + r0
            r14.size = r1
            r1 = 0
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x000f:
            r7 = 1
            if (r4 >= r0) goto L_0x003d
            short r8 = r15.get(r4)
            double r9 = (double) r8
            r11 = 4674736138332667904(0x40dfffc000000000, double:32767.0)
            double r9 = r9 / r11
            float r9 = (float) r9
            float r10 = r9 * r9
            double r10 = (double) r10
            double r12 = (double) r0
            double r10 = r10 / r12
            double r2 = r2 + r10
            double r10 = r14.sum
            int r8 = r8 * r8
            double r12 = (double) r8
            double r10 = r10 + r12
            r14.sum = r10
            r8 = 0
            int r8 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            r7 = -1
        L_0x0033:
            if (r6 == 0) goto L_0x0039
            if (r7 == r6) goto L_0x0039
            int r5 = r5 + 1
        L_0x0039:
            int r4 = r4 + 1
            r6 = r7
            goto L_0x000f
        L_0x003d:
            int r15 = r14.frameNumber
            int r15 = r15 + r7
            r14.frameNumber = r15
            r0 = 15
            if (r15 >= r0) goto L_0x004f
            double r4 = r14.noiseEnergy
            r6 = 4624633867356078080(0x402e000000000000, double:15.0)
            double r2 = r2 / r6
            double r4 = r4 + r2
            r14.noiseEnergy = r4
            goto L_0x0062
        L_0x004f:
            r15 = 5
            if (r5 < r15) goto L_0x0062
            if (r5 > r0) goto L_0x0062
            double r4 = r14.noiseEnergy
            r8 = 4614162998222441677(0x4008cccccccccccd, double:3.1)
            double r4 = r4 * r8
            int r15 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r15 <= 0) goto L_0x0062
            r1 = 1
        L_0x0062:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.util.VoiceActivityDetector.isFrameActive(java.nio.ShortBuffer):boolean");
    }

    public double calculateRms() {
        double sqrt = Math.sqrt(this.sum / ((double) this.size)) / 100.0d;
        this.sum = 0.0d;
        this.size = 0;
        return sqrt;
    }

    public void reset() {
        this.time = 0;
        this.frameNumber = 0;
        this.noiseEnergy = 0.0d;
        this.lastActiveTime = -1;
        this.lastSequenceTime = 0;
        this.sequenceCounter = 0;
        this.silenceMillis = MAX_SILENCE_MILLIS;
        this.speechActive = false;
        this.process = true;
    }

    public void setSpeechListener(SpeechEventsListener speechEventsListener) {
        this.eventsListener = speechEventsListener;
    }

    private void onSpeechEnd() {
        SpeechEventsListener speechEventsListener;
        Log.v(TAG, "onSpeechEnd");
        this.speechActive = false;
        this.process = false;
        if (this.enabled && (speechEventsListener = this.eventsListener) != null) {
            speechEventsListener.onSpeechEnd();
        }
    }

    private void onSpeechCancel() {
        Log.v(TAG, "onSpeechCancel");
        this.speechActive = false;
        this.process = false;
        SpeechEventsListener speechEventsListener = this.eventsListener;
        if (speechEventsListener != null) {
            speechEventsListener.onSpeechCancel();
        }
    }

    private void onSpeechBegin() {
        Log.v(TAG, "onSpeechBegin");
        this.speechActive = true;
        SpeechEventsListener speechEventsListener = this.eventsListener;
        if (speechEventsListener != null) {
            speechEventsListener.onSpeechBegin();
        }
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }
}
