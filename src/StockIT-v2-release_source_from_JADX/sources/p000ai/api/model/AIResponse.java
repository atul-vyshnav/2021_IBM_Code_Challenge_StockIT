package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/* renamed from: ai.api.model.AIResponse */
public class AIResponse implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("id")

    /* renamed from: id */
    private String f1id;
    @SerializedName("lang")
    private String lang;
    @SerializedName("result")
    private Result result;
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("status")
    private Status status;
    @SerializedName("timestamp")
    private Date timestamp;

    public String getId() {
        return this.f1id;
    }

    public void setId(String str) {
        this.f1id = str;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result2) {
        this.result = result2;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status2) {
        this.status = status2;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public boolean isError() {
        Status status2 = this.status;
        return (status2 == null || status2.getCode() == null || this.status.getCode().intValue() < 400) ? false : true;
    }

    public String toString() {
        return String.format("AIResponse{id='%s', timestamp=%s, result=%s, status=%s, sessionId=%s}", new Object[]{this.f1id, this.timestamp, this.result, this.status, this.sessionId});
    }

    public void cleanup() {
        Result result2 = this.result;
        if (result2 != null) {
            result2.trimParameters();
        }
    }
}
