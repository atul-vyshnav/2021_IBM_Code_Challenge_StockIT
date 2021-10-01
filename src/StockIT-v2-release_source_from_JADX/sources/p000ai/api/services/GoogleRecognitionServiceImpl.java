package p000ai.api.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import p000ai.api.AIServiceException;
import p000ai.api.PartialResultsListener;
import p000ai.api.RequestExtras;
import p000ai.api.android.AIConfiguration;
import p000ai.api.android.AIService;
import p000ai.api.model.AIContext;
import p000ai.api.model.AIError;
import p000ai.api.model.AIRequest;
import p000ai.api.model.AIResponse;
import p000ai.api.model.Entity;
import p000ai.api.util.RecognizerChecker;
import p000ai.api.util.VersionConfig;

/* renamed from: ai.api.services.GoogleRecognitionServiceImpl */
public class GoogleRecognitionServiceImpl extends AIService {
    private static final long STOP_DELAY = 1000;
    /* access modifiers changed from: private */
    public static final String TAG = GoogleRecognitionServiceImpl.class.getName();
    /* access modifiers changed from: private */
    public final Map<Integer, String> errorMessages;
    private final Handler handler = new Handler();
    private PartialResultsListener partialResultsListener;
    /* access modifiers changed from: private */
    public volatile boolean recognitionActive = false;
    /* access modifiers changed from: private */
    public RequestExtras requestExtras;
    private SpeechRecognizer speechRecognizer;
    private final Object speechRecognizerLock = new Object();
    private Runnable stopRunnable;
    private final VersionConfig versionConfig;
    /* access modifiers changed from: private */
    public volatile boolean wasReadyForSpeech;

    public void resume() {
    }

    public GoogleRecognitionServiceImpl(Context context, AIConfiguration aIConfiguration) {
        super(aIConfiguration, context);
        HashMap hashMap = new HashMap();
        this.errorMessages = hashMap;
        hashMap.put(1, "Network operation timed out.");
        this.errorMessages.put(2, "Other network related errors.");
        this.errorMessages.put(3, "Audio recording error.");
        this.errorMessages.put(4, "Server sends error status.");
        this.errorMessages.put(5, "Other client side errors.");
        this.errorMessages.put(6, "No speech input.");
        this.errorMessages.put(7, "No recognition result matched.");
        this.errorMessages.put(8, "RecognitionService busy.");
        this.errorMessages.put(9, "Insufficient permissions.");
        if (RecognizerChecker.findGoogleRecognizer(context) == null) {
            Log.w(TAG, "Google Recognizer application not found on device. Quality of the recognition may be low. Please check if Google Search application installed and enabled.");
        }
        VersionConfig init = VersionConfig.init(context);
        this.versionConfig = init;
        if (init.isAutoStopRecognizer()) {
            this.stopRunnable = new Runnable() {
                public void run() {
                    GoogleRecognitionServiceImpl.this.stopListening();
                }
            };
        }
    }

    /* access modifiers changed from: private */
    public void updateStopRunnable(int i) {
        Runnable runnable = this.stopRunnable;
        if (runnable == null) {
            return;
        }
        if (i == 0) {
            this.handler.removeCallbacks(runnable);
        } else if (i == 1) {
            this.handler.removeCallbacks(runnable);
            this.handler.postDelayed(this.stopRunnable, 1000);
        }
    }

    /* access modifiers changed from: protected */
    public void initializeRecognizer() {
        if (this.speechRecognizer == null) {
            synchronized (this.speechRecognizerLock) {
                if (this.speechRecognizer != null) {
                    this.speechRecognizer.destroy();
                    this.speechRecognizer = null;
                }
                SpeechRecognizer createSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this.context, RecognizerChecker.findGoogleRecognizer(this.context));
                this.speechRecognizer = createSpeechRecognizer;
                createSpeechRecognizer.setRecognitionListener(new InternalRecognitionListener());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void clearRecognizer() {
        Log.d(TAG, "clearRecognizer");
        if (this.speechRecognizer != null) {
            synchronized (this.speechRecognizerLock) {
                if (this.speechRecognizer != null) {
                    this.speechRecognizer.destroy();
                    this.speechRecognizer = null;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void sendRequest(AIRequest aIRequest, final RequestExtras requestExtras2) {
        if (aIRequest != null) {
            new AsyncTask<AIRequest, Integer, AIResponse>() {
                private AIError aiError;

                /* access modifiers changed from: protected */
                public AIResponse doInBackground(AIRequest... aIRequestArr) {
                    try {
                        return GoogleRecognitionServiceImpl.this.aiDataService.request(aIRequestArr[0], requestExtras2);
                    } catch (AIServiceException e) {
                        this.aiError = new AIError(e);
                        return null;
                    }
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(AIResponse aIResponse) {
                    if (aIResponse != null) {
                        GoogleRecognitionServiceImpl.this.onResult(aIResponse);
                    } else {
                        GoogleRecognitionServiceImpl.this.onError(this.aiError);
                    }
                }
            }.execute(new AIRequest[]{aIRequest});
            return;
        }
        throw new IllegalArgumentException("aiRequest must be not null");
    }

    public void startListening() {
        startListening(new RequestExtras());
    }

    public void startListening(List<AIContext> list) {
        startListening(new RequestExtras(list, (List<Entity>) null));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:10|11|12|13|14|15|16|24) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x002d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startListening(p000ai.api.RequestExtras r3) {
        /*
            r2 = this;
            boolean r0 = r2.recognitionActive
            if (r0 != 0) goto L_0x0032
            java.lang.Object r0 = r2.speechRecognizerLock
            monitor-enter(r0)
            r2.requestExtras = r3     // Catch:{ all -> 0x002f }
            boolean r3 = r2.checkPermissions()     // Catch:{ all -> 0x002f }
            if (r3 != 0) goto L_0x001b
            ai.api.model.AIError r3 = new ai.api.model.AIError     // Catch:{ all -> 0x002f }
            java.lang.String r1 = "RECORD_AUDIO permission is denied. Please request permission from user."
            r3.<init>((java.lang.String) r1)     // Catch:{ all -> 0x002f }
            r2.onError(r3)     // Catch:{ all -> 0x002f }
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return
        L_0x001b:
            r2.initializeRecognizer()     // Catch:{ all -> 0x002f }
            r3 = 1
            r2.recognitionActive = r3     // Catch:{ all -> 0x002f }
            android.content.Intent r3 = r2.createRecognitionIntent()     // Catch:{ all -> 0x002f }
            r1 = 0
            r2.wasReadyForSpeech = r1     // Catch:{ SecurityException -> 0x002d }
            android.speech.SpeechRecognizer r1 = r2.speechRecognizer     // Catch:{ SecurityException -> 0x002d }
            r1.startListening(r3)     // Catch:{ SecurityException -> 0x002d }
        L_0x002d:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            goto L_0x0040
        L_0x002f:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            throw r3
        L_0x0032:
            java.lang.String r3 = TAG
            java.lang.String r0 = "Trying to start recognition while another recognition active"
            android.util.Log.w(r3, r0)
            boolean r3 = r2.wasReadyForSpeech
            if (r3 != 0) goto L_0x0040
            r2.cancel()
        L_0x0040:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.services.GoogleRecognitionServiceImpl.startListening(ai.api.RequestExtras):void");
    }

    private Intent createRecognitionIntent() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        String replace = this.config.getLanguage().replace('-', '_');
        intent.putExtra("android.speech.extra.LANGUAGE", replace);
        intent.putExtra("android.speech.extra.LANGUAGE_PREFERENCE", replace);
        intent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        intent.putExtra("calling_package", this.context.getPackageName());
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{replace});
        return intent;
    }

    public void stopListening() {
        synchronized (this.speechRecognizerLock) {
            if (this.speechRecognizer != null) {
                this.speechRecognizer.stopListening();
            }
        }
    }

    public void cancel() {
        synchronized (this.speechRecognizerLock) {
            if (this.recognitionActive) {
                this.recognitionActive = false;
                if (this.speechRecognizer != null) {
                    this.speechRecognizer.cancel();
                }
                onListeningCancelled();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        stopListening();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0023 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void restartRecognition() {
        /*
            r3 = this;
            r0 = 0
            r3.updateStopRunnable(r0)
            r3.recognitionActive = r0
            java.lang.Object r1 = r3.speechRecognizerLock
            monitor-enter(r1)
            android.speech.SpeechRecognizer r2 = r3.speechRecognizer     // Catch:{ Exception -> 0x0023 }
            if (r2 == 0) goto L_0x0026
            android.speech.SpeechRecognizer r2 = r3.speechRecognizer     // Catch:{ Exception -> 0x0023 }
            r2.cancel()     // Catch:{ Exception -> 0x0023 }
            android.content.Intent r2 = r3.createRecognitionIntent()     // Catch:{ Exception -> 0x0023 }
            r3.wasReadyForSpeech = r0     // Catch:{ Exception -> 0x0023 }
            android.speech.SpeechRecognizer r0 = r3.speechRecognizer     // Catch:{ Exception -> 0x0023 }
            r0.startListening(r2)     // Catch:{ Exception -> 0x0023 }
            r0 = 1
            r3.recognitionActive = r0     // Catch:{ Exception -> 0x0023 }
            goto L_0x0026
        L_0x0021:
            r0 = move-exception
            goto L_0x0028
        L_0x0023:
            r3.stopListening()     // Catch:{ all -> 0x0021 }
        L_0x0026:
            monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            return
        L_0x0028:
            monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.services.GoogleRecognitionServiceImpl.restartRecognition():void");
    }

    public void pause() {
        clearRecognizer();
    }

    public void setPartialResultsListener(PartialResultsListener partialResultsListener2) {
        this.partialResultsListener = partialResultsListener2;
    }

    /* access modifiers changed from: protected */
    public void onPartialResults(List<String> list) {
        PartialResultsListener partialResultsListener2 = this.partialResultsListener;
        if (partialResultsListener2 != null) {
            partialResultsListener2.onPartialResults(list);
        }
    }

    /* access modifiers changed from: private */
    public void stopInternal() {
        updateStopRunnable(0);
        if (this.versionConfig.isDestroyRecognizer()) {
            clearRecognizer();
        }
        this.recognitionActive = false;
    }

    /* renamed from: ai.api.services.GoogleRecognitionServiceImpl$InternalRecognitionListener */
    private class InternalRecognitionListener implements RecognitionListener {
        public void onBeginningOfSpeech() {
        }

        public void onBufferReceived(byte[] bArr) {
        }

        public void onEvent(int i, Bundle bundle) {
        }

        private InternalRecognitionListener() {
        }

        public void onReadyForSpeech(Bundle bundle) {
            if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                GoogleRecognitionServiceImpl.this.onListeningStarted();
            }
            boolean unused = GoogleRecognitionServiceImpl.this.wasReadyForSpeech = true;
        }

        public void onRmsChanged(float f) {
            if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                GoogleRecognitionServiceImpl.this.onAudioLevelChanged(f);
            }
        }

        public void onEndOfSpeech() {
            if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                GoogleRecognitionServiceImpl.this.onListeningFinished();
            }
        }

        public void onError(int i) {
            AIError aIError;
            if (i != 7 || GoogleRecognitionServiceImpl.this.wasReadyForSpeech) {
                if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                    if (GoogleRecognitionServiceImpl.this.errorMessages.containsKey(Integer.valueOf(i))) {
                        aIError = new AIError("Speech recognition engine error: " + ((String) GoogleRecognitionServiceImpl.this.errorMessages.get(Integer.valueOf(i))));
                    } else {
                        aIError = new AIError("Speech recognition engine error: " + i);
                    }
                    GoogleRecognitionServiceImpl.this.onError(aIError);
                }
                GoogleRecognitionServiceImpl.this.stopInternal();
                return;
            }
            Log.d(GoogleRecognitionServiceImpl.TAG, "SpeechRecognizer.ERROR_NO_MATCH, restartRecognition()");
            GoogleRecognitionServiceImpl.this.restartRecognition();
        }

        public void onResults(Bundle bundle) {
            if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                float[] fArr = null;
                if (Build.VERSION.SDK_INT >= 14) {
                    fArr = bundle.getFloatArray("confidence_scores");
                }
                if (stringArrayList == null || stringArrayList.isEmpty()) {
                    GoogleRecognitionServiceImpl.this.onResult(new AIResponse());
                } else {
                    AIRequest aIRequest = new AIRequest();
                    if (fArr != null) {
                        aIRequest.setQuery((String[]) stringArrayList.toArray(new String[stringArrayList.size()]), fArr);
                    } else {
                        aIRequest.setQuery(stringArrayList.get(0));
                    }
                    GoogleRecognitionServiceImpl.this.onPartialResults(stringArrayList);
                    GoogleRecognitionServiceImpl googleRecognitionServiceImpl = GoogleRecognitionServiceImpl.this;
                    googleRecognitionServiceImpl.sendRequest(aIRequest, googleRecognitionServiceImpl.requestExtras);
                }
            }
            GoogleRecognitionServiceImpl.this.stopInternal();
        }

        public void onPartialResults(Bundle bundle) {
            if (GoogleRecognitionServiceImpl.this.recognitionActive) {
                GoogleRecognitionServiceImpl.this.updateStopRunnable(1);
                ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
                if (stringArrayList != null && !stringArrayList.isEmpty()) {
                    GoogleRecognitionServiceImpl.this.onPartialResults(stringArrayList);
                }
            }
        }
    }
}
