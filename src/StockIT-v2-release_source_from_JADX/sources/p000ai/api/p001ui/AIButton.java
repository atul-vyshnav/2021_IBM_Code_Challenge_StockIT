package p000ai.api.p001ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.util.List;
import p000ai.api.AIListener;
import p000ai.api.AIServiceException;
import p000ai.api.C0001R;
import p000ai.api.PartialResultsListener;
import p000ai.api.RequestExtras;
import p000ai.api.android.AIConfiguration;
import p000ai.api.android.AIService;
import p000ai.api.model.AIError;
import p000ai.api.model.AIRequest;
import p000ai.api.model.AIResponse;
import p000ai.api.services.GoogleRecognitionServiceImpl;

/* renamed from: ai.api.ui.AIButton */
public class AIButton extends SoundLevelButton implements AIListener {
    protected static final int[] STATE_INITIALIZING_TTS = {C0001R.attr.state_initializing_tts};
    protected static final int[] STATE_SPEAKING = {C0001R.attr.state_speaking};
    protected static final int[] STATE_WAITING = {C0001R.attr.state_waiting};
    private static final String TAG = AIButton.class.getName();
    private AIService aiService;
    private final WaitingAnimation animation = new WaitingAnimation();
    private boolean animationSecondPhase = false;
    /* access modifiers changed from: private */
    public float animationStage = 0.0f;
    private volatile MicState currentState = MicState.normal;
    /* access modifiers changed from: private */
    public PartialResultsListener partialResultsListener;
    private AIButtonListener resultsListener;

    /* renamed from: ai.api.ui.AIButton$AIButtonListener */
    public interface AIButtonListener {
        void onCancelled();

        void onError(AIError aIError);

        void onResult(AIResponse aIResponse);
    }

    public void onResult(AIResponse aIResponse) {
        post(new Runnable() {
            public void run() {
                AIButton.this.changeState(MicState.normal);
            }
        });
        AIButtonListener aIButtonListener = this.resultsListener;
        if (aIButtonListener != null) {
            aIButtonListener.onResult(aIResponse);
        }
    }

    public void onError(AIError aIError) {
        post(new Runnable() {
            public void run() {
                AIButton.this.changeState(MicState.normal);
            }
        });
        AIButtonListener aIButtonListener = this.resultsListener;
        if (aIButtonListener != null) {
            aIButtonListener.onError(aIError);
        }
    }

    public void onAudioLevel(float f) {
        setSoundLevel(f);
    }

    public void onListeningStarted() {
        post(new Runnable() {
            public void run() {
                AIButton.this.changeState(MicState.listening);
            }
        });
    }

    public void onListeningCanceled() {
        post(new Runnable() {
            public void run() {
                AIButton.this.changeState(MicState.normal);
            }
        });
        AIButtonListener aIButtonListener = this.resultsListener;
        if (aIButtonListener != null) {
            aIButtonListener.onCancelled();
        }
    }

    public void onListeningFinished() {
        post(new Runnable() {
            public void run() {
                AIButton.this.changeState(MicState.busy);
            }
        });
    }

    /* renamed from: ai.api.ui.AIButton$MicState */
    public enum MicState {
        normal,
        busy,
        listening,
        speaking,
        initializingTts;

        public static MicState fromAttrs(TypedArray typedArray) {
            if (typedArray.getBoolean(C0001R.styleable.SoundLevelButton_state_listening, false)) {
                return listening;
            }
            if (typedArray.getBoolean(C0001R.styleable.SoundLevelButton_state_waiting, false)) {
                return busy;
            }
            if (typedArray.getBoolean(C0001R.styleable.SoundLevelButton_state_speaking, false)) {
                return speaking;
            }
            if (typedArray.getBoolean(C0001R.styleable.SoundLevelButton_state_initializing_tts, false)) {
                return initializingTts;
            }
            return normal;
        }
    }

    public AIButton(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public AIButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public AIButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0001R.styleable.SoundLevelButton);
            try {
                this.currentState = MicState.fromAttrs(obtainStyledAttributes);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    public void initialize(AIConfiguration aIConfiguration) {
        AIService service = AIService.getService(getContext(), aIConfiguration);
        this.aiService = service;
        service.setListener(this);
        AIService aIService = this.aiService;
        if (aIService instanceof GoogleRecognitionServiceImpl) {
            ((GoogleRecognitionServiceImpl) aIService).setPartialResultsListener(new PartialResultsListener() {
                public void onPartialResults(List<String> list) {
                    if (AIButton.this.partialResultsListener != null) {
                        AIButton.this.partialResultsListener.onPartialResults(list);
                    }
                }
            });
        }
    }

    public void setResultsListener(AIButtonListener aIButtonListener) {
        this.resultsListener = aIButtonListener;
    }

    public void setPartialResultsListener(PartialResultsListener partialResultsListener2) {
        this.partialResultsListener = partialResultsListener2;
    }

    public void startListening() {
        startListening((RequestExtras) null);
    }

    public void startListening(RequestExtras requestExtras) {
        if (this.aiService == null) {
            throw new IllegalStateException("Call initialize method before usage");
        } else if (this.currentState == MicState.normal) {
            this.aiService.startListening(requestExtras);
        }
    }

    public AIResponse textRequest(AIRequest aIRequest) throws AIServiceException {
        AIService aIService = this.aiService;
        if (aIService != null) {
            return aIService.textRequest(aIRequest);
        }
        throw new IllegalStateException("Call initialize method before usage");
    }

    public AIResponse textRequest(String str) throws AIServiceException {
        return textRequest(new AIRequest(str));
    }

    public AIService getAIService() {
        return this.aiService;
    }

    /* access modifiers changed from: protected */
    public void onClick(View view) {
        super.onClick(view);
        if (this.aiService != null) {
            int i = C00157.$SwitchMap$ai$api$ui$AIButton$MicState[this.currentState.ordinal()];
            if (i == 1) {
                this.aiService.startListening();
            } else if (i != 2) {
                this.aiService.stopListening();
            } else {
                this.aiService.cancel();
            }
        }
    }

    /* renamed from: ai.api.ui.AIButton$7 */
    static /* synthetic */ class C00157 {
        static final /* synthetic */ int[] $SwitchMap$ai$api$ui$AIButton$MicState;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                ai.api.ui.AIButton$MicState[] r0 = p000ai.api.p001ui.AIButton.MicState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$ai$api$ui$AIButton$MicState = r0
                ai.api.ui.AIButton$MicState r1 = p000ai.api.p001ui.AIButton.MicState.normal     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$ai$api$ui$AIButton$MicState     // Catch:{ NoSuchFieldError -> 0x001d }
                ai.api.ui.AIButton$MicState r1 = p000ai.api.p001ui.AIButton.MicState.busy     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$ai$api$ui$AIButton$MicState     // Catch:{ NoSuchFieldError -> 0x0028 }
                ai.api.ui.AIButton$MicState r1 = p000ai.api.p001ui.AIButton.MicState.listening     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$ai$api$ui$AIButton$MicState     // Catch:{ NoSuchFieldError -> 0x0033 }
                ai.api.ui.AIButton$MicState r1 = p000ai.api.p001ui.AIButton.MicState.speaking     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$ai$api$ui$AIButton$MicState     // Catch:{ NoSuchFieldError -> 0x003e }
                ai.api.ui.AIButton$MicState r1 = p000ai.api.p001ui.AIButton.MicState.initializingTts     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p000ai.api.p001ui.AIButton.C00157.<clinit>():void");
        }
    }

    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.currentState != null) {
            int i2 = C00157.$SwitchMap$ai$api$ui$AIButton$MicState[this.currentState.ordinal()];
            if (i2 == 2) {
                mergeDrawableStates(onCreateDrawableState, STATE_WAITING);
            } else if (i2 == 3) {
                mergeDrawableStates(onCreateDrawableState, STATE_LISTENING);
            } else if (i2 == 4) {
                mergeDrawableStates(onCreateDrawableState, STATE_SPEAKING);
            } else if (i2 == 5) {
                mergeDrawableStates(onCreateDrawableState, STATE_INITIALIZING_TTS);
            }
        }
        return onCreateDrawableState;
    }

    public void resume() {
        AIService aIService = this.aiService;
        if (aIService != null) {
            aIService.resume();
        }
    }

    public void pause() {
        cancelListening();
        AIService aIService = this.aiService;
        if (aIService != null) {
            aIService.pause();
        }
    }

    private void cancelListening() {
        if (this.aiService != null && this.currentState != MicState.normal) {
            this.aiService.cancel();
            changeState(MicState.normal);
        }
    }

    /* access modifiers changed from: protected */
    public void changeState(MicState micState) {
        int i = C00157.$SwitchMap$ai$api$ui$AIButton$MicState[micState.ordinal()];
        if (i == 1) {
            stopProcessingAnimation();
            setDrawSoundLevel(false);
        } else if (i == 2) {
            startProcessingAnimation();
            setDrawSoundLevel(false);
        } else if (i == 3) {
            stopProcessingAnimation();
            setDrawSoundLevel(true);
        } else if (i == 4) {
            stopProcessingAnimation();
            setDrawSoundLevel(false);
        } else if (i == 5) {
            stopProcessingAnimation();
            setDrawSoundLevel(false);
        }
        this.currentState = micState;
        refreshDrawableState();
    }

    /* access modifiers changed from: protected */
    public MicState getCurrentState() {
        return this.currentState;
    }

    private void startProcessingAnimation() {
        setDrawCenter(true);
        this.animationSecondPhase = false;
        startAnimation(this.animation);
    }

    private void stopProcessingAnimation() {
        setDrawCenter(false);
        clearAnimation();
        this.animationStage = 0.0f;
        this.animationSecondPhase = false;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public String getDebugState() {
        return super.getDebugState() + "\nst:" + this.currentState;
    }

    /* renamed from: ai.api.ui.AIButton$WaitingAnimation */
    private class WaitingAnimation extends Animation {
        protected WaitingAnimation() {
            setDuration(1500);
            setRepeatCount(-1);
            setRepeatMode(1);
            setInterpolator(new LinearInterpolator());
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float f, Transformation transformation) {
            float unused = AIButton.this.animationStage = f;
            AIButton.this.invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f;
        super.onDraw(canvas);
        float f2 = 0.0f;
        if (this.animationStage > 0.0f || this.animationSecondPhase) {
            float width = ((float) getWidth()) / 2.0f;
            float minRadius = getMinRadius() * 1.25f;
            float f3 = width - minRadius;
            float f4 = width + minRadius;
            RectF rectF = new RectF(f3, f3, f4, f4);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(C0001R.C0002color.icon_orange_color));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth((float) dpToPixels(getContext(), 4.0f));
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setAntiAlias(true);
            float f5 = this.animationStage;
            if (((double) f5) >= 0.5d || this.animationSecondPhase) {
                f2 = (this.animationStage - 0.5f) * 360.0f;
                this.animationSecondPhase = true;
                f = 180.0f;
            } else {
                f = f5 * 360.0f;
            }
            canvas.drawArc(rectF, f2 + 270.0f, f, false, paint);
        }
    }

    private static int dpToPixels(Context context, float f) {
        return Math.round(TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics()));
    }
}
