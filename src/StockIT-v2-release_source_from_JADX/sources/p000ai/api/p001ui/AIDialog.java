package p000ai.api.p001ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.TextView;
import java.util.List;
import p000ai.api.AIServiceException;
import p000ai.api.C0001R;
import p000ai.api.PartialResultsListener;
import p000ai.api.android.AIConfiguration;
import p000ai.api.android.AIService;
import p000ai.api.model.AIError;
import p000ai.api.model.AIRequest;
import p000ai.api.model.AIResponse;
import p000ai.api.p001ui.AIButton;

/* renamed from: ai.api.ui.AIDialog */
public class AIDialog {
    private static final String TAG = AIDialog.class.getName();
    private final AIButton aiButton;
    private final AIConfiguration config;
    private final Context context;
    /* access modifiers changed from: private */
    public final Dialog dialog;
    /* access modifiers changed from: private */
    public final Handler handler;
    /* access modifiers changed from: private */
    public final TextView partialResultsTextView;
    /* access modifiers changed from: private */
    public AIDialogListener resultsListener;

    /* renamed from: ai.api.ui.AIDialog$AIDialogListener */
    public interface AIDialogListener {
        void onCancelled();

        void onError(AIError aIError);

        void onResult(AIResponse aIResponse);
    }

    public AIDialog(Context context2, AIConfiguration aIConfiguration) {
        this(context2, aIConfiguration, C0001R.layout.aidialog);
    }

    public AIDialog(Context context2, AIConfiguration aIConfiguration, int i) {
        this.context = context2;
        this.config = aIConfiguration;
        this.dialog = new Dialog(context2);
        this.handler = new Handler(Looper.getMainLooper());
        this.dialog.setCanceledOnTouchOutside(true);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(i);
        this.partialResultsTextView = (TextView) this.dialog.findViewById(C0001R.C0004id.partialResultsTextView);
        AIButton aIButton = (AIButton) this.dialog.findViewById(C0001R.C0004id.micButton);
        this.aiButton = aIButton;
        aIButton.initialize(aIConfiguration);
        setAIButtonCallback(this.aiButton);
    }

    public void setResultsListener(AIDialogListener aIDialogListener) {
        this.resultsListener = aIDialogListener;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void showAndListen() {
        this.handler.post(new Runnable() {
            public void run() {
                AIDialog.this.resetControls();
                AIDialog.this.dialog.show();
                AIDialog.this.startListening();
            }
        });
    }

    public AIResponse textRequest(AIRequest aIRequest) throws AIServiceException {
        return this.aiButton.textRequest(aIRequest);
    }

    public AIResponse textRequest(String str) throws AIServiceException {
        return textRequest(new AIRequest(str));
    }

    /* access modifiers changed from: private */
    public void resetControls() {
        TextView textView = this.partialResultsTextView;
        if (textView != null) {
            textView.setText("");
        }
    }

    private void setAIButtonCallback(AIButton aIButton) {
        aIButton.setResultsListener(new AIButton.AIButtonListener() {
            public void onResult(AIResponse aIResponse) {
                AIDialog.this.close();
                if (AIDialog.this.resultsListener != null) {
                    AIDialog.this.resultsListener.onResult(aIResponse);
                }
            }

            public void onError(AIError aIError) {
                if (AIDialog.this.resultsListener != null) {
                    AIDialog.this.resultsListener.onError(aIError);
                }
            }

            public void onCancelled() {
                AIDialog.this.close();
                if (AIDialog.this.resultsListener != null) {
                    AIDialog.this.resultsListener.onCancelled();
                }
            }
        });
        aIButton.setPartialResultsListener(new PartialResultsListener() {
            public void onPartialResults(List<String> list) {
                final String str = list.get(0);
                if (!TextUtils.isEmpty(str)) {
                    AIDialog.this.handler.post(new Runnable() {
                        public void run() {
                            if (AIDialog.this.partialResultsTextView != null) {
                                AIDialog.this.partialResultsTextView.setText(str);
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startListening() {
        AIButton aIButton = this.aiButton;
        if (aIButton != null) {
            aIButton.startListening();
        }
    }

    public void close() {
        this.handler.post(new Runnable() {
            public void run() {
                AIDialog.this.dialog.dismiss();
            }
        });
    }

    public AIService getAIService() {
        return this.aiButton.getAIService();
    }

    public void pause() {
        AIButton aIButton = this.aiButton;
        if (aIButton != null) {
            aIButton.pause();
        }
    }

    public void resume() {
        AIButton aIButton = this.aiButton;
        if (aIButton != null) {
            aIButton.resume();
        }
    }
}
