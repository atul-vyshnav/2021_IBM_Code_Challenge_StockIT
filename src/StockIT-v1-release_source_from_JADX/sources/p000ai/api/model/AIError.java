package p000ai.api.model;

import java.io.Serializable;
import p000ai.api.AIServiceException;

/* renamed from: ai.api.model.AIError */
public class AIError implements Serializable {
    private static final long serialVersionUID = 1;
    private final AIResponse aiResponse;
    private AIServiceException exception;
    private final String message;

    public AIError(String str) {
        this.aiResponse = null;
        this.message = str;
    }

    public AIError(AIServiceException aIServiceException) {
        this.aiResponse = aIServiceException.getResponse();
        this.message = aIServiceException.getMessage();
        this.exception = aIServiceException;
    }

    public AIError(AIResponse aIResponse) {
        this.aiResponse = aIResponse;
        if (aIResponse == null) {
            this.message = "API.AI service returns empty result";
        } else if (aIResponse.getStatus() != null) {
            this.message = aIResponse.getStatus().getErrorDetails();
        } else {
            this.message = "API.AI service returns error code with empty status";
        }
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        AIServiceException aIServiceException = this.exception;
        if (aIServiceException != null) {
            return aIServiceException.toString();
        }
        return this.message;
    }
}
