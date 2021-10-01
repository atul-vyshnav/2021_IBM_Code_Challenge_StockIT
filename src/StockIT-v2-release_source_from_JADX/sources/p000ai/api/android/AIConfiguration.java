package p000ai.api.android;

import android.content.res.AssetFileDescriptor;
import p000ai.api.AIConfiguration;

/* renamed from: ai.api.android.AIConfiguration */
public class AIConfiguration extends p000ai.api.AIConfiguration {
    private boolean normalizeInputSound = false;
    private final RecognitionEngine recognitionEngine;
    private AssetFileDescriptor recognizerCancelSound;
    private AssetFileDescriptor recognizerStartSound;
    private AssetFileDescriptor recognizerStopSound;
    private boolean voiceActivityDetectionEnabled = true;

    /* renamed from: ai.api.android.AIConfiguration$RecognitionEngine */
    public enum RecognitionEngine {
        Google,
        System,
        Speaktoit
    }

    public AIConfiguration(String str, AIConfiguration.SupportedLanguages supportedLanguages, RecognitionEngine recognitionEngine2) {
        super(str, supportedLanguages);
        this.recognitionEngine = recognitionEngine2;
        if (recognitionEngine2 == RecognitionEngine.Speaktoit && supportedLanguages == AIConfiguration.SupportedLanguages.Korean) {
            throw new UnsupportedOperationException("Only System recognition supported for Korean language");
        }
    }

    public RecognitionEngine getRecognitionEngine() {
        return this.recognitionEngine;
    }

    public boolean isVoiceActivityDetectionEnabled() {
        return this.voiceActivityDetectionEnabled;
    }

    public void setVoiceActivityDetectionEnabled(boolean z) {
        this.voiceActivityDetectionEnabled = z;
    }

    public void setNormalizeInputSound(boolean z) {
        this.normalizeInputSound = z;
    }

    public boolean isNormalizeInputSound() {
        return this.normalizeInputSound;
    }

    public AssetFileDescriptor getRecognizerStartSound() {
        return this.recognizerStartSound;
    }

    public void setRecognizerStartSound(AssetFileDescriptor assetFileDescriptor) {
        this.recognizerStartSound = assetFileDescriptor;
    }

    public AssetFileDescriptor getRecognizerStopSound() {
        return this.recognizerStopSound;
    }

    public void setRecognizerStopSound(AssetFileDescriptor assetFileDescriptor) {
        this.recognizerStopSound = assetFileDescriptor;
    }

    public AssetFileDescriptor getRecognizerCancelSound() {
        return this.recognizerCancelSound;
    }

    public void setRecognizerCancelSound(AssetFileDescriptor assetFileDescriptor) {
        this.recognizerCancelSound = assetFileDescriptor;
    }
}
