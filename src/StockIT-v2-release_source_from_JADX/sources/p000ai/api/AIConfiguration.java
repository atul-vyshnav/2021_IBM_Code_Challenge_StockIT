package p000ai.api;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import p000ai.api.util.StringUtils;

/* renamed from: ai.api.AIConfiguration */
public class AIConfiguration implements Cloneable {
    protected static final String CONTEXTS_ENDPOINT = "contexts";
    protected static final String CURRENT_PROTOCOL_VERSION = "20150910";
    protected static final String QUESTION_ENDPOINT = "query";
    private static final String SERVICE_PROD_URL = "https://api.api.ai/v1/";
    /* access modifiers changed from: private */
    public static Map<String, SupportedLanguages> STRING_TO_LANGUAGE = new HashMap();
    protected static final String USER_ENTITIES_ENDPOINT = "userEntities";
    private final String apiKey;
    private final SupportedLanguages language;
    private String protocolVersion;
    private Proxy proxy;
    private String serviceUrl;
    private boolean writeSoundLog;

    public AIConfiguration(String str, SupportedLanguages supportedLanguages) {
        this.writeSoundLog = false;
        if (str != null) {
            this.apiKey = str;
            this.language = supportedLanguages == null ? SupportedLanguages.DEFAULT : supportedLanguages;
            this.protocolVersion = CURRENT_PROTOCOL_VERSION;
            this.serviceUrl = SERVICE_PROD_URL;
            return;
        }
        throw new IllegalArgumentException("clientAccessToken");
    }

    public AIConfiguration(String str) {
        this(str, (SupportedLanguages) null);
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getLanguage() {
        return this.language.languageTag;
    }

    public String getApiAiLanguage() {
        return this.language.apiaiLanguage;
    }

    public void setWriteSoundLog(boolean z) {
        this.writeSoundLog = z;
    }

    public boolean isWriteSoundLog() {
        return this.writeSoundLog;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(String str) {
        this.protocolVersion = str;
    }

    public void setServiceUrl(String str) {
        this.serviceUrl = str;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy2) {
        this.proxy = proxy2;
    }

    public AIConfiguration clone() {
        try {
            return (AIConfiguration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public String getQuestionUrl(String str) {
        if (StringUtils.isEmpty(this.protocolVersion)) {
            return String.format("%s%s?sessionId=%s", new Object[]{this.serviceUrl, "query", str});
        }
        return String.format("%s%s?v=%s&sessionId=%s", new Object[]{this.serviceUrl, "query", this.protocolVersion, str});
    }

    /* access modifiers changed from: package-private */
    public String getUserEntitiesEndpoint(String str) {
        if (StringUtils.isEmpty(this.protocolVersion)) {
            return String.format("%s%s?sessionId=%s", new Object[]{this.serviceUrl, USER_ENTITIES_ENDPOINT, str});
        }
        return String.format("%s%s?v=%s&sessionId=%s", new Object[]{this.serviceUrl, USER_ENTITIES_ENDPOINT, this.protocolVersion, str});
    }

    /* access modifiers changed from: package-private */
    public String getContextsUrl(String str) {
        return getContextsUrl(str, "");
    }

    /* access modifiers changed from: package-private */
    public String getContextsUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.serviceUrl);
        sb.append(CONTEXTS_ENDPOINT);
        if (!StringUtils.isEmpty(str2)) {
            try {
                sb.append("/");
                sb.append(URLEncoder.encode(str2, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        sb.append("?");
        if (!StringUtils.isEmpty(this.protocolVersion)) {
            sb.append("v=");
            sb.append(this.protocolVersion);
            sb.append("&");
        }
        sb.append("sessionId=");
        sb.append(str);
        return sb.toString();
    }

    /* renamed from: ai.api.AIConfiguration$SupportedLanguages */
    public enum SupportedLanguages {
        ChineseChina("zh-CN"),
        ChineseHongKong("zh-HK"),
        ChineseTaiwan("zh-TW"),
        English("en"),
        EnglishUS("en-US", "en"),
        EnglishGB("en-GB", "en"),
        Dutch("nl"),
        French("fr"),
        German("de"),
        Italian("it"),
        Japanese("ja"),
        Korean("ko"),
        Portuguese("pt"),
        PortugueseBrazil("pt-BR"),
        Russian("ru"),
        Spanish("es"),
        Ukrainian("uk");
        
        public static SupportedLanguages DEFAULT;
        /* access modifiers changed from: private */
        public final String apiaiLanguage;
        /* access modifiers changed from: private */
        public final String languageTag;

        static {
            SupportedLanguages supportedLanguages;
            DEFAULT = supportedLanguages;
        }

        private SupportedLanguages(String str) {
            this(r1, r2, str, str);
        }

        private SupportedLanguages(String str, String str2) {
            this.languageTag = str;
            this.apiaiLanguage = str2;
            SupportedLanguages supportedLanguages = (SupportedLanguages) AIConfiguration.STRING_TO_LANGUAGE.put(str, this);
        }

        public static SupportedLanguages fromLanguageTag(String str) {
            SupportedLanguages supportedLanguages = (SupportedLanguages) AIConfiguration.STRING_TO_LANGUAGE.get(str);
            return supportedLanguages != null ? supportedLanguages : DEFAULT;
        }
    }
}
