package p000ai.api.android;

import android.content.Context;
import androidx.core.content.ContextCompat;
import java.util.Collection;
import java.util.List;
import p000ai.api.AIListener;
import p000ai.api.AIServiceException;
import p000ai.api.RequestExtras;
import p000ai.api.android.AIConfiguration;
import p000ai.api.model.AIContext;
import p000ai.api.model.AIError;
import p000ai.api.model.AIRequest;
import p000ai.api.model.AIResponse;
import p000ai.api.model.Entity;
import p000ai.api.services.GoogleRecognitionServiceImpl;
import p000ai.api.services.SpeaktoitRecognitionServiceImpl;

/* renamed from: ai.api.android.AIService */
public abstract class AIService {
    private static final String TAG = AIService.class.getName();
    /* access modifiers changed from: protected */
    public final AIDataService aiDataService;
    /* access modifiers changed from: protected */
    public final AIConfiguration config;
    protected final Context context;
    private AIListener listener;

    public abstract void cancel();

    public void pause() {
    }

    public void resume() {
    }

    public abstract void startListening();

    public abstract void startListening(RequestExtras requestExtras);

    public abstract void startListening(List<AIContext> list);

    public abstract void stopListening();

    public static AIService getService(Context context2, AIConfiguration aIConfiguration) {
        if (aIConfiguration.getRecognitionEngine() == AIConfiguration.RecognitionEngine.Google) {
            return new GoogleRecognitionServiceImpl(context2, aIConfiguration);
        }
        if (aIConfiguration.getRecognitionEngine() == AIConfiguration.RecognitionEngine.System) {
            return new GoogleRecognitionServiceImpl(context2, aIConfiguration);
        }
        if (aIConfiguration.getRecognitionEngine() == AIConfiguration.RecognitionEngine.Speaktoit) {
            return new SpeaktoitRecognitionServiceImpl(context2, aIConfiguration);
        }
        throw new UnsupportedOperationException("This engine still not supported");
    }

    protected AIService(AIConfiguration aIConfiguration, Context context2) {
        this.config = aIConfiguration;
        this.context = context2;
        this.aiDataService = new AIDataService(context2, aIConfiguration);
    }

    public void setListener(AIListener aIListener) {
        this.listener = aIListener;
    }

    /* access modifiers changed from: protected */
    public void onResult(AIResponse aIResponse) {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onResult(aIResponse);
        }
    }

    /* access modifiers changed from: protected */
    public void onError(AIError aIError) {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onError(aIError);
        }
    }

    /* access modifiers changed from: protected */
    public void onAudioLevelChanged(float f) {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onAudioLevel(f);
        }
    }

    /* access modifiers changed from: protected */
    public void onListeningStarted() {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onListeningStarted();
        }
    }

    /* access modifiers changed from: protected */
    public void onListeningCancelled() {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onListeningCanceled();
        }
    }

    /* access modifiers changed from: protected */
    public void onListeningFinished() {
        AIListener aIListener = this.listener;
        if (aIListener != null) {
            aIListener.onListeningFinished();
        }
    }

    public AIResponse textRequest(AIRequest aIRequest) throws AIServiceException {
        return this.aiDataService.request(aIRequest);
    }

    public AIResponse textRequest(String str, RequestExtras requestExtras) throws AIServiceException {
        AIRequest aIRequest = new AIRequest(str);
        if (requestExtras != null) {
            requestExtras.copyTo(aIRequest);
        }
        return this.aiDataService.request(aIRequest);
    }

    public boolean resetContexts() {
        return this.aiDataService.resetContexts();
    }

    public AIResponse uploadUserEntity(Entity entity) throws AIServiceException {
        return this.aiDataService.uploadUserEntity(entity);
    }

    public AIResponse uploadUserEntities(Collection<Entity> collection) throws AIServiceException {
        return this.aiDataService.uploadUserEntities(collection);
    }

    /* access modifiers changed from: protected */
    public boolean checkPermissions() {
        try {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.RECORD_AUDIO") == 0;
        } catch (Throwable unused) {
            return true;
        }
    }
}
