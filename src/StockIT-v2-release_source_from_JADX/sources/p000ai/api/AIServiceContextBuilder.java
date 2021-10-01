package p000ai.api;

import java.util.TimeZone;
import java.util.UUID;

/* renamed from: ai.api.AIServiceContextBuilder */
public class AIServiceContextBuilder {
    private String sessionId;
    private TimeZone timeZone;

    public String getSessionId() {
        return this.sessionId;
    }

    public AIServiceContextBuilder setSessionId(String str) {
        if (str != null) {
            this.sessionId = str;
            return this;
        }
        throw new IllegalArgumentException("sessionId cannot be null");
    }

    public AIServiceContextBuilder generateSessionId() {
        this.sessionId = createRandomSessionId();
        return this;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public AIServiceContextBuilder setSessionId(TimeZone timeZone2) {
        this.timeZone = timeZone2;
        return this;
    }

    public AIServiceContext build() {
        if (this.sessionId != null) {
            return new PlainAIServiceContext(this.sessionId, this.timeZone);
        }
        throw new IllegalStateException("Session id is undefined");
    }

    public static AIServiceContext buildFromSessionId(String str) {
        return new AIServiceContextBuilder().setSessionId(str).build();
    }

    private static String createRandomSessionId() {
        return UUID.randomUUID().toString();
    }

    /* renamed from: ai.api.AIServiceContextBuilder$PlainAIServiceContext */
    private static class PlainAIServiceContext implements AIServiceContext {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String sessionId;
        private final TimeZone timeZone;

        static {
            Class<AIServiceContextBuilder> cls = AIServiceContextBuilder.class;
        }

        public PlainAIServiceContext(String str, TimeZone timeZone2) {
            this.sessionId = str;
            this.timeZone = timeZone2;
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public TimeZone getTimeZone() {
            return this.timeZone;
        }
    }
}
