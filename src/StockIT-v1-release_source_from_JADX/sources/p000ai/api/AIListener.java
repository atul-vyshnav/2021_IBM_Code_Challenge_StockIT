package p000ai.api;

import p000ai.api.model.AIError;
import p000ai.api.model.AIResponse;

/* renamed from: ai.api.AIListener */
public interface AIListener {
    void onAudioLevel(float f);

    void onError(AIError aIError);

    void onListeningCanceled();

    void onListeningFinished();

    void onListeningStarted();

    void onResult(AIResponse aIResponse);
}
