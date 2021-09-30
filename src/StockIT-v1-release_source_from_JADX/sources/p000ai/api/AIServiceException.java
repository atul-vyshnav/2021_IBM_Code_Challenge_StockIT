package p000ai.api;

import p000ai.api.model.AIResponse;
import p000ai.api.util.StringUtils;

/* renamed from: ai.api.AIServiceException */
public class AIServiceException extends Exception {
    private static final long serialVersionUID = 1;
    private final AIResponse aiResponse;

    public AIServiceException() {
        this.aiResponse = null;
    }

    public AIServiceException(String str, Throwable th) {
        super(str, th);
        this.aiResponse = null;
    }

    public AIServiceException(String str) {
        super(str);
        this.aiResponse = null;
    }

    public AIServiceException(AIResponse aIResponse) {
        this.aiResponse = aIResponse;
    }

    public AIResponse getResponse() {
        return this.aiResponse;
    }

    public String getMessage() {
        AIResponse aIResponse = this.aiResponse;
        if (!(aIResponse == null || aIResponse.getStatus() == null)) {
            String errorDetails = this.aiResponse.getStatus().getErrorDetails();
            if (!StringUtils.isEmpty(errorDetails)) {
                return errorDetails;
            }
        }
        return super.getMessage();
    }
}
