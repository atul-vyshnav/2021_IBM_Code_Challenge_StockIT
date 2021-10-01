package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import p000ai.api.util.StringUtils;

/* renamed from: ai.api.model.AIRequest */
public class AIRequest extends QuestionMetadata implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("confidence")
    private float[] confidence;
    @SerializedName("contexts")
    private List<AIContext> contexts;
    @SerializedName("event")
    private AIEvent event;
    @SerializedName("query")
    private String[] query;
    @SerializedName("resetContexts")
    private Boolean resetContexts;

    public AIRequest() {
    }

    public AIRequest(String str) {
        setQuery(str);
    }

    public void setQuery(String str) {
        if (!StringUtils.isEmpty(str)) {
            this.query = new String[]{str};
            this.confidence = null;
            return;
        }
        throw new IllegalStateException("Query must not be empty");
    }

    public void setQuery(String[] strArr, float[] fArr) {
        if (strArr == null) {
            throw new IllegalStateException("Query array must not be null");
        } else if (fArr == null && strArr.length > 1) {
            throw new IllegalStateException("Then confidences array is null, query must be one or zero item length");
        } else if (fArr == null || strArr.length == fArr.length) {
            this.query = strArr;
            this.confidence = fArr;
        } else {
            throw new IllegalStateException("Query and confidence arrays must be equals size");
        }
    }

    public float[] getConfidence() {
        return this.confidence;
    }

    public void setConfidence(float[] fArr) {
        this.confidence = fArr;
    }

    public void setResetContexts(Boolean bool) {
        this.resetContexts = bool;
    }

    public Boolean getResetContexts() {
        return this.resetContexts;
    }

    public void setContexts(List<AIContext> list) {
        this.contexts = list;
    }

    public void addContext(AIContext aIContext) {
        if (this.contexts == null) {
            this.contexts = new ArrayList(1);
        }
        this.contexts.add(aIContext);
    }

    public void setEvent(AIEvent aIEvent) {
        this.event = aIEvent;
    }

    public String toString() {
        return String.format("AIRequest{query=%s, resetContexts=%s, language='%s', timezone='%s'}", new Object[]{Arrays.toString(this.query), this.resetContexts, getLanguage(), getTimezone()});
    }
}
