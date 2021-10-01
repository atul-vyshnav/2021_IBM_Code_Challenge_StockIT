package p000ai.api.model;

import com.facebook.common.statfs.StatFsHelper;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: ai.api.model.Status */
public class Status implements Serializable {
    private static final Map<Integer, String> errorDescriptions = new HashMap();
    private static final Map<Integer, String> errorTypes = new HashMap();
    private static final long serialVersionUID = 1;
    @SerializedName("code")
    private Integer code;
    @SerializedName("errorDetails")
    private String errorDetails;
    @SerializedName("errorID")
    private String errorID;
    @SerializedName("errorType")
    private String errorType;

    public Status() {
        Map<Integer, String> map = errorDescriptions;
        Integer valueOf = Integer.valueOf(StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB);
        map.put(valueOf, "Some required parameter is missing or has wrong value. Details will be in the errorDetails field.");
        errorTypes.put(valueOf, "bad_request");
        errorDescriptions.put(401, "Authorization failed. Please check your access keys.");
        errorTypes.put(401, "unauthorized");
        errorDescriptions.put(404, "Uri is not found or some resource with provided id is not found.");
        errorTypes.put(404, "not_found");
        errorDescriptions.put(405, "Attempting to use POST with a GET-only endpoint, or vice-versa.");
        errorTypes.put(405, "not_allowed");
        errorDescriptions.put(406, "Uploaded files have some problems with it.");
        errorTypes.put(406, "not_acceptable");
        errorDescriptions.put(409, "The request could not be completed due to a conflict with the current state of the resource. This code is only allowed in situations where it is expected that the user might be able to resolve the conflict and resubmit the request.");
        errorTypes.put(409, "conflict");
    }

    public static Status fromResponseCode(int i) {
        Status status = new Status();
        status.setCode(Integer.valueOf(i));
        if (errorTypes.containsKey(Integer.valueOf(i))) {
            status.setErrorType(errorTypes.get(Integer.valueOf(i)));
        }
        if (errorDescriptions.containsKey(Integer.valueOf(i))) {
            status.setErrorDetails(errorDescriptions.get(Integer.valueOf(i)));
        }
        return status;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String str) {
        this.errorType = str;
    }

    public String getErrorDetails() {
        Integer num = this.code;
        if (num == null || !errorDescriptions.containsKey(num)) {
            return this.errorDetails;
        }
        return errorDescriptions.get(this.code);
    }

    public void setErrorDetails(String str) {
        this.errorDetails = str;
    }

    public String getErrorID() {
        return this.errorID;
    }

    public void setErrorID(String str) {
        this.errorID = str;
    }

    public String toString() {
        return String.format("Status{code=%d, errorType='%s', errorDetails='%s'}", new Object[]{this.code, this.errorType, this.errorDetails});
    }
}
