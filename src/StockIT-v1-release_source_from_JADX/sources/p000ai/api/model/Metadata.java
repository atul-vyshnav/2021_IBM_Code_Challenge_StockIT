package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* renamed from: ai.api.model.Metadata */
public class Metadata implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("intentId")
    private String intentId;
    @SerializedName("intentName")
    private String intentName;
    @SerializedName("webhookUsed")
    private String webhookUsed;

    public String getIntentName() {
        return this.intentName;
    }

    public void setIntentName(String str) {
        this.intentName = str;
    }

    public String getIntentId() {
        return this.intentId;
    }

    public void setIntentId(String str) {
        this.intentId = str;
    }

    public boolean isWebhookUsed() {
        String str = this.webhookUsed;
        if (str != null) {
            return Boolean.valueOf(str).booleanValue();
        }
        return false;
    }

    public void setWebhookUsed(boolean z) {
        this.webhookUsed = Boolean.toString(z);
    }
}
