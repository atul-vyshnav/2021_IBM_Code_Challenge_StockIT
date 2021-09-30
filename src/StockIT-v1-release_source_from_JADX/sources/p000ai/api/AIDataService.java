package p000ai.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import p000ai.api.model.AIContext;
import p000ai.api.model.AIRequest;
import p000ai.api.model.AIResponse;
import p000ai.api.model.Entity;
import p000ai.api.util.IOUtils;
import p000ai.api.util.StringUtils;

/* renamed from: ai.api.AIDataService */
public class AIDataService {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String DEFAULT_REQUEST_METHOD = "POST";
    private static final Gson GSON = GsonFactory.getDefaultFactory().getGson();
    private static final Logger Log = LogManager.getLogger((Class<?>) AIDataService.class);
    private static final String REQUEST_METHOD_DELETE = "DELETE";
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";
    private static final AIServiceContext UNDEFINED_SERVICE_CONTEXT = null;
    private final AIConfiguration config;
    private final AIServiceContext defaultServiceContext;

    /* renamed from: ai.api.AIDataService$ApiActiveContextListResponse */
    private interface ApiActiveContextListResponse extends List<AIContext> {
    }

    public AIDataService(AIConfiguration aIConfiguration, AIServiceContext aIServiceContext) {
        if (aIConfiguration != null) {
            this.config = aIConfiguration.clone();
            if (aIServiceContext == null) {
                this.defaultServiceContext = new AIServiceContextBuilder().generateSessionId().build();
            } else {
                this.defaultServiceContext = aIServiceContext;
            }
        } else {
            throw new IllegalArgumentException("config should not be null");
        }
    }

    public AIDataService(AIConfiguration aIConfiguration) {
        this(aIConfiguration, (AIServiceContext) null);
    }

    public AIServiceContext getContext() {
        return this.defaultServiceContext;
    }

    public AIResponse request(AIRequest aIRequest) throws AIServiceException {
        return request(aIRequest, (RequestExtras) null);
    }

    public AIResponse request(AIRequest aIRequest, AIServiceContext aIServiceContext) throws AIServiceException {
        return request(aIRequest, (RequestExtras) null, aIServiceContext);
    }

    public AIResponse request(AIRequest aIRequest, RequestExtras requestExtras) throws AIServiceException {
        return request(aIRequest, requestExtras, UNDEFINED_SERVICE_CONTEXT);
    }

    public AIResponse request(AIRequest aIRequest, RequestExtras requestExtras, AIServiceContext aIServiceContext) throws AIServiceException {
        if (aIRequest != null) {
            Log.debug("Start request");
            try {
                aIRequest.setLanguage(this.config.getApiAiLanguage());
                aIRequest.setSessionId(getSessionId(aIServiceContext));
                if (StringUtils.isEmpty(aIRequest.getTimezone())) {
                    aIRequest.setTimezone(getTimeZone(aIServiceContext));
                }
                Map<String, String> map = null;
                if (requestExtras != null) {
                    fillRequest(aIRequest, requestExtras);
                    map = requestExtras.getAdditionalHeaders();
                }
                String doTextRequest = doTextRequest(this.config.getQuestionUrl(getSessionId(aIServiceContext)), GSON.toJson((Object) aIRequest), map);
                if (!StringUtils.isEmpty(doTextRequest)) {
                    Logger logger = Log;
                    logger.debug("Response json: " + doTextRequest.replaceAll("[\r\n]+", org.apache.commons.lang3.StringUtils.SPACE));
                    AIResponse aIResponse = (AIResponse) GSON.fromJson(doTextRequest, AIResponse.class);
                    if (aIResponse == null) {
                        throw new AIServiceException("API.AI response parsed as null. Check debug log for details.");
                    } else if (!aIResponse.isError()) {
                        aIResponse.cleanup();
                        return aIResponse;
                    } else {
                        throw new AIServiceException(aIResponse);
                    }
                } else {
                    throw new AIServiceException("Empty response from ai service. Please check configuration and Internet connection.");
                }
            } catch (MalformedURLException e) {
                Log.error("Malformed url should not be raised", (Throwable) e);
                throw new AIServiceException("Wrong configuration. Please, connect to API.AI Service support", e);
            } catch (JsonSyntaxException e2) {
                throw new AIServiceException("Wrong service answer format. Please, connect to API.AI Service support", e2);
            }
        } else {
            throw new IllegalArgumentException("Request argument must not be null");
        }
    }

    public AIResponse voiceRequest(InputStream inputStream) throws AIServiceException {
        return voiceRequest(inputStream, new RequestExtras());
    }

    public AIResponse voiceRequest(InputStream inputStream, List<AIContext> list) throws AIServiceException {
        return voiceRequest(inputStream, new RequestExtras(list, (List<Entity>) null));
    }

    public AIResponse voiceRequest(InputStream inputStream, RequestExtras requestExtras) throws AIServiceException {
        return voiceRequest(inputStream, requestExtras, UNDEFINED_SERVICE_CONTEXT);
    }

    public AIResponse voiceRequest(InputStream inputStream, RequestExtras requestExtras, AIServiceContext aIServiceContext) throws AIServiceException {
        Log.debug("Start voice request");
        try {
            AIRequest aIRequest = new AIRequest();
            aIRequest.setLanguage(this.config.getApiAiLanguage());
            aIRequest.setSessionId(getSessionId(aIServiceContext));
            aIRequest.setTimezone(getTimeZone(aIServiceContext));
            Map<String, String> map = null;
            if (requestExtras != null) {
                fillRequest(aIRequest, requestExtras);
                map = requestExtras.getAdditionalHeaders();
            }
            String json = GSON.toJson((Object) aIRequest);
            Logger logger = Log;
            logger.debug("Request json: " + json);
            String doSoundRequest = doSoundRequest(inputStream, json, map);
            if (!StringUtils.isEmpty(doSoundRequest)) {
                Logger logger2 = Log;
                logger2.debug("Response json: " + doSoundRequest);
                AIResponse aIResponse = (AIResponse) GSON.fromJson(doSoundRequest, AIResponse.class);
                if (aIResponse == null) {
                    throw new AIServiceException("API.AI response parsed as null. Check debug log for details.");
                } else if (!aIResponse.isError()) {
                    aIResponse.cleanup();
                    return aIResponse;
                } else {
                    throw new AIServiceException(aIResponse);
                }
            } else {
                throw new AIServiceException("Empty response from ai service. Please check configuration.");
            }
        } catch (MalformedURLException e) {
            Log.error("Malformed url should not be raised", (Throwable) e);
            throw new AIServiceException("Wrong configuration. Please, connect to AI Service support", e);
        } catch (JsonSyntaxException e2) {
            throw new AIServiceException("Wrong service answer format. Please, connect to API.AI Service support", e2);
        }
    }

    public boolean resetContexts() {
        AIRequest aIRequest = new AIRequest();
        aIRequest.setQuery("empty_query_for_resetting_contexts");
        aIRequest.setResetContexts(true);
        try {
            return !request(aIRequest).isError();
        } catch (AIServiceException e) {
            Log.error("Exception while contexts clean.", (Throwable) e);
            return false;
        }
    }

    public List<AIContext> getActiveContexts() throws AIServiceException {
        return getActiveContexts(UNDEFINED_SERVICE_CONTEXT);
    }

    public List<AIContext> getActiveContexts(AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            return (List) doRequest(ApiActiveContextListResponse.class, this.config.getContextsUrl(getSessionId(aIServiceContext)), REQUEST_METHOD_GET);
        } catch (BadResponseStatusException e) {
            throw new AIServiceException(e.response);
        }
    }

    public AIContext getActiveContext(String str) throws AIServiceException {
        return getActiveContext(str, UNDEFINED_SERVICE_CONTEXT);
    }

    public AIContext getActiveContext(String str, AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            return (AIContext) doRequest(AIContext.class, this.config.getContextsUrl(getSessionId(aIServiceContext), str), REQUEST_METHOD_GET);
        } catch (BadResponseStatusException e) {
            if (e.response.getStatus().getCode().intValue() == 404) {
                return null;
            }
            throw new AIServiceException(e.response);
        }
    }

    public List<String> addActiveContext(Iterable<AIContext> iterable) throws AIServiceException {
        return addActiveContext(iterable, UNDEFINED_SERVICE_CONTEXT);
    }

    public List<String> addActiveContext(Iterable<AIContext> iterable, AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            return ((ApiActiveContextNamesResponse) doRequest(iterable, (Type) ApiActiveContextNamesResponse.class, this.config.getContextsUrl(getSessionId(aIServiceContext)), "POST")).names;
        } catch (BadResponseStatusException e) {
            throw new AIServiceException(e.response);
        }
    }

    public String addActiveContext(AIContext aIContext) throws AIServiceException {
        return addActiveContext(aIContext, UNDEFINED_SERVICE_CONTEXT);
    }

    public String addActiveContext(AIContext aIContext, AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            ApiActiveContextNamesResponse apiActiveContextNamesResponse = (ApiActiveContextNamesResponse) doRequest(aIContext, (Type) ApiActiveContextNamesResponse.class, this.config.getContextsUrl(getSessionId(aIServiceContext)), "POST");
            if (apiActiveContextNamesResponse.names == null || apiActiveContextNamesResponse.names.size() <= 0) {
                return null;
            }
            return apiActiveContextNamesResponse.names.get(0);
        } catch (BadResponseStatusException e) {
            throw new AIServiceException(e.response);
        }
    }

    public void resetActiveContexts() throws AIServiceException {
        resetActiveContexts(UNDEFINED_SERVICE_CONTEXT);
    }

    public void resetActiveContexts(AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            doRequest(AIResponse.class, this.config.getContextsUrl(getSessionId(aIServiceContext)), REQUEST_METHOD_DELETE);
        } catch (BadResponseStatusException e) {
            throw new AIServiceException(e.response);
        }
    }

    public boolean removeActiveContext(String str) throws AIServiceException {
        return removeActiveContext(str, UNDEFINED_SERVICE_CONTEXT);
    }

    public boolean removeActiveContext(String str, AIServiceContext aIServiceContext) throws AIServiceException {
        try {
            doRequest(AIResponse.class, this.config.getContextsUrl(getSessionId(aIServiceContext), str), REQUEST_METHOD_DELETE);
            return true;
        } catch (BadResponseStatusException e) {
            if (e.response.getStatus().getCode().intValue() == 404) {
                return false;
            }
            throw new AIServiceException(e.response);
        }
    }

    public AIResponse uploadUserEntity(Entity entity) throws AIServiceException {
        return uploadUserEntity(entity, UNDEFINED_SERVICE_CONTEXT);
    }

    public AIResponse uploadUserEntity(Entity entity, AIServiceContext aIServiceContext) throws AIServiceException {
        return uploadUserEntities(Collections.singleton(entity), aIServiceContext);
    }

    public AIResponse uploadUserEntities(Collection<Entity> collection) throws AIServiceException {
        return uploadUserEntities(collection, UNDEFINED_SERVICE_CONTEXT);
    }

    public AIResponse uploadUserEntities(Collection<Entity> collection, AIServiceContext aIServiceContext) throws AIServiceException {
        if (collection == null || collection.size() == 0) {
            throw new AIServiceException("Empty entities list");
        }
        try {
            String doTextRequest = doTextRequest(this.config.getUserEntitiesEndpoint(getSessionId(aIServiceContext)), GSON.toJson((Object) collection));
            if (!StringUtils.isEmpty(doTextRequest)) {
                Logger logger = Log;
                logger.debug("Response json: " + doTextRequest);
                AIResponse aIResponse = (AIResponse) GSON.fromJson(doTextRequest, AIResponse.class);
                if (aIResponse == null) {
                    throw new AIServiceException("API.AI response parsed as null. Check debug log for details.");
                } else if (!aIResponse.isError()) {
                    aIResponse.cleanup();
                    return aIResponse;
                } else {
                    throw new AIServiceException(aIResponse);
                }
            } else {
                throw new AIServiceException("Empty response from ai service. Please check configuration and Internet connection.");
            }
        } catch (MalformedURLException e) {
            Log.error("Malformed url should not be raised", (Throwable) e);
            throw new AIServiceException("Wrong configuration. Please, connect to AI Service support", e);
        } catch (JsonSyntaxException e2) {
            throw new AIServiceException("Wrong service answer format. Please, connect to API.AI Service support", e2);
        }
    }

    /* access modifiers changed from: protected */
    public String doTextRequest(String str, AIServiceContext aIServiceContext) throws MalformedURLException, AIServiceException {
        return doTextRequest(this.config.getQuestionUrl(getSessionId(aIServiceContext)), str);
    }

    /* access modifiers changed from: protected */
    public String doTextRequest(String str) throws MalformedURLException, AIServiceException {
        return doTextRequest(str, UNDEFINED_SERVICE_CONTEXT);
    }

    /* access modifiers changed from: protected */
    public String doTextRequest(String str, String str2) throws MalformedURLException, AIServiceException {
        return doTextRequest(str, str2, (Map<String, String>) null);
    }

    /* access modifiers changed from: protected */
    public String doTextRequest(String str, String str2, Map<String, String> map) throws MalformedURLException, AIServiceException {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2 = null;
        try {
            URL url = new URL(str);
            Logger logger = Log;
            logger.debug("Request json: " + str2);
            if (this.config.getProxy() != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(this.config.getProxy());
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            HttpURLConnection httpURLConnection3 = httpURLConnection;
            httpURLConnection3.setRequestMethod("POST");
            httpURLConnection3.setDoOutput(true);
            httpURLConnection3.addRequestProperty("Authorization", "Bearer " + this.config.getApiKey());
            httpURLConnection3.addRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection3.addRequestProperty("Accept", "application/json");
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    httpURLConnection3.addRequestProperty((String) next.getKey(), (String) next.getValue());
                }
            }
            httpURLConnection3.connect();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection3.getOutputStream());
            IOUtils.writeAll(str2, bufferedOutputStream);
            bufferedOutputStream.close();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection3.getInputStream());
            String readAll = IOUtils.readAll((InputStream) bufferedInputStream);
            bufferedInputStream.close();
            if (httpURLConnection3 != null) {
                httpURLConnection3.disconnect();
            }
            return readAll;
        } catch (IOException e) {
            if (httpURLConnection2 != null) {
                InputStream errorStream = httpURLConnection2.getErrorStream();
                if (errorStream != null) {
                    String readAll2 = IOUtils.readAll(errorStream);
                    Log.debug(readAll2);
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    return readAll2;
                }
                throw new AIServiceException("Can't connect to the api.ai service.", e);
            }
        } catch (IOException e2) {
            Log.warn("Can't read error response", (Throwable) e2);
        } catch (Throwable th) {
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            throw th;
        }
        Log.error("Can't make request to the API.AI service. Please, check connection settings and API access token.", (Throwable) e);
        throw new AIServiceException("Can't make request to the API.AI service. Please, check connection settings and API access token.", e);
    }

    /* access modifiers changed from: protected */
    public String doSoundRequest(InputStream inputStream, String str) throws MalformedURLException, AIServiceException {
        return doSoundRequest(inputStream, str, (Map<String, String>) null, UNDEFINED_SERVICE_CONTEXT);
    }

    /* access modifiers changed from: protected */
    public String doSoundRequest(InputStream inputStream, String str, Map<String, String> map) throws MalformedURLException, AIServiceException {
        return doSoundRequest(inputStream, str, map, UNDEFINED_SERVICE_CONTEXT);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: ai.api.http.HttpClient} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c5 A[SYNTHETIC, Splitter:B:27:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x010f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String doSoundRequest(java.io.InputStream r8, java.lang.String r9, java.util.Map<java.lang.String, java.lang.String> r10, p000ai.api.AIServiceContext r11) throws java.net.MalformedURLException, p000ai.api.AIServiceException {
        /*
            r7 = this;
            java.lang.String r0 = "Can't make request to the API.AI service. Please, check connection settings and API.AI keys."
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            ai.api.AIConfiguration r3 = r7.config     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.lang.String r11 = r7.getSessionId(r11)     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.lang.String r11 = r3.getQuestionUrl(r11)     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            r2.<init>(r11)     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            org.apache.logging.log4j.Logger r11 = Log     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.lang.String r3 = "Connecting to {}"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            r6 = 0
            r5[r6] = r2     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            r11.debug((java.lang.String) r3, (java.lang.Object[]) r5)     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            ai.api.AIConfiguration r11 = r7.config     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.net.Proxy r11 = r11.getProxy()     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            if (r11 == 0) goto L_0x0034
            ai.api.AIConfiguration r11 = r7.config     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.net.Proxy r11 = r11.getProxy()     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.net.URLConnection r11 = r2.openConnection(r11)     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            goto L_0x003a
        L_0x0034:
            java.net.URLConnection r11 = r2.openConnection()     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ IOException -> 0x00c1, all -> 0x00bf }
        L_0x003a:
            java.lang.String r2 = "Authorization"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00bd }
            r3.<init>()     // Catch:{ IOException -> 0x00bd }
            java.lang.String r5 = "Bearer "
            r3.append(r5)     // Catch:{ IOException -> 0x00bd }
            ai.api.AIConfiguration r5 = r7.config     // Catch:{ IOException -> 0x00bd }
            java.lang.String r5 = r5.getApiKey()     // Catch:{ IOException -> 0x00bd }
            r3.append(r5)     // Catch:{ IOException -> 0x00bd }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x00bd }
            r11.addRequestProperty(r2, r3)     // Catch:{ IOException -> 0x00bd }
            java.lang.String r2 = "Accept"
            java.lang.String r3 = "application/json"
            r11.addRequestProperty(r2, r3)     // Catch:{ IOException -> 0x00bd }
            if (r10 == 0) goto L_0x0083
            java.util.Set r10 = r10.entrySet()     // Catch:{ IOException -> 0x00bd }
            java.util.Iterator r10 = r10.iterator()     // Catch:{ IOException -> 0x00bd }
        L_0x0067:
            boolean r2 = r10.hasNext()     // Catch:{ IOException -> 0x00bd }
            if (r2 == 0) goto L_0x0083
            java.lang.Object r2 = r10.next()     // Catch:{ IOException -> 0x00bd }
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch:{ IOException -> 0x00bd }
            java.lang.Object r3 = r2.getKey()     // Catch:{ IOException -> 0x00bd }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ IOException -> 0x00bd }
            java.lang.Object r2 = r2.getValue()     // Catch:{ IOException -> 0x00bd }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IOException -> 0x00bd }
            r11.addRequestProperty(r3, r2)     // Catch:{ IOException -> 0x00bd }
            goto L_0x0067
        L_0x0083:
            java.lang.String r10 = "POST"
            r11.setRequestMethod(r10)     // Catch:{ IOException -> 0x00bd }
            r11.setDoInput(r4)     // Catch:{ IOException -> 0x00bd }
            r11.setDoOutput(r4)     // Catch:{ IOException -> 0x00bd }
            ai.api.http.HttpClient r10 = new ai.api.http.HttpClient     // Catch:{ IOException -> 0x00bd }
            r10.<init>(r11)     // Catch:{ IOException -> 0x00bd }
            ai.api.AIConfiguration r1 = r7.config     // Catch:{ IOException -> 0x00ba }
            boolean r1 = r1.isWriteSoundLog()     // Catch:{ IOException -> 0x00ba }
            r10.setWriteSoundLog(r1)     // Catch:{ IOException -> 0x00ba }
            r10.connectForMultipart()     // Catch:{ IOException -> 0x00ba }
            java.lang.String r1 = "request"
            r10.addFormPart(r1, r9)     // Catch:{ IOException -> 0x00ba }
            java.lang.String r9 = "voiceData"
            java.lang.String r1 = "voice.wav"
            r10.addFilePart(r9, r1, r8)     // Catch:{ IOException -> 0x00ba }
            r10.finishMultipart()     // Catch:{ IOException -> 0x00ba }
            java.lang.String r8 = r10.getResponse()     // Catch:{ IOException -> 0x00ba }
            if (r11 == 0) goto L_0x00b9
            r11.disconnect()
        L_0x00b9:
            return r8
        L_0x00ba:
            r8 = move-exception
            r1 = r10
            goto L_0x00c3
        L_0x00bd:
            r8 = move-exception
            goto L_0x00c3
        L_0x00bf:
            r8 = move-exception
            goto L_0x010d
        L_0x00c1:
            r8 = move-exception
            r11 = r1
        L_0x00c3:
            if (r1 == 0) goto L_0x0100
            java.lang.String r9 = r1.getErrorString()     // Catch:{ all -> 0x010b }
            org.apache.logging.log4j.Logger r10 = Log     // Catch:{ all -> 0x010b }
            r10.debug((java.lang.String) r9)     // Catch:{ all -> 0x010b }
            boolean r10 = p000ai.api.util.StringUtils.isEmpty(r9)     // Catch:{ all -> 0x010b }
            if (r10 != 0) goto L_0x00da
            if (r11 == 0) goto L_0x00d9
            r11.disconnect()
        L_0x00d9:
            return r9
        L_0x00da:
            boolean r9 = r8 instanceof java.net.HttpRetryException     // Catch:{ all -> 0x010b }
            if (r9 == 0) goto L_0x0100
            ai.api.model.AIResponse r9 = new ai.api.model.AIResponse     // Catch:{ all -> 0x010b }
            r9.<init>()     // Catch:{ all -> 0x010b }
            r10 = r8
            java.net.HttpRetryException r10 = (java.net.HttpRetryException) r10     // Catch:{ all -> 0x010b }
            int r10 = r10.responseCode()     // Catch:{ all -> 0x010b }
            ai.api.model.Status r10 = p000ai.api.model.Status.fromResponseCode(r10)     // Catch:{ all -> 0x010b }
            java.net.HttpRetryException r8 = (java.net.HttpRetryException) r8     // Catch:{ all -> 0x010b }
            java.lang.String r8 = r8.getReason()     // Catch:{ all -> 0x010b }
            r10.setErrorDetails(r8)     // Catch:{ all -> 0x010b }
            r9.setStatus(r10)     // Catch:{ all -> 0x010b }
            ai.api.AIServiceException r8 = new ai.api.AIServiceException     // Catch:{ all -> 0x010b }
            r8.<init>((p000ai.api.model.AIResponse) r9)     // Catch:{ all -> 0x010b }
            throw r8     // Catch:{ all -> 0x010b }
        L_0x0100:
            org.apache.logging.log4j.Logger r9 = Log     // Catch:{ all -> 0x010b }
            r9.error((java.lang.String) r0, (java.lang.Throwable) r8)     // Catch:{ all -> 0x010b }
            ai.api.AIServiceException r9 = new ai.api.AIServiceException     // Catch:{ all -> 0x010b }
            r9.<init>(r0, r8)     // Catch:{ all -> 0x010b }
            throw r9     // Catch:{ all -> 0x010b }
        L_0x010b:
            r8 = move-exception
            r1 = r11
        L_0x010d:
            if (r1 == 0) goto L_0x0112
            r1.disconnect()
        L_0x0112:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.AIDataService.doSoundRequest(java.io.InputStream, java.lang.String, java.util.Map, ai.api.AIServiceContext):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public <TResponse> TResponse doRequest(Type type, String str, String str2) throws AIServiceException, BadResponseStatusException {
        return doRequest(type, str, str2, (Map<String, String>) null);
    }

    /* access modifiers changed from: protected */
    public <TRequest, TResponse> TResponse doRequest(TRequest trequest, Type type, String str, String str2) throws AIServiceException, BadResponseStatusException {
        return doRequest(trequest, type, str, str2, (Map<String, String>) null);
    }

    /* access modifiers changed from: protected */
    public <TResponse> TResponse doRequest(Type type, String str, String str2, Map<String, String> map) throws AIServiceException, BadResponseStatusException {
        return doRequest((Object) null, type, str, str2, map);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x0103 */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x010b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <TRequest, TResponse> TResponse doRequest(TRequest r7, java.lang.reflect.Type r8, java.lang.String r9, java.lang.String r10, java.util.Map<java.lang.String, java.lang.String> r11) throws p000ai.api.AIServiceException, p000ai.api.AIDataService.BadResponseStatusException {
        /*
            r6 = this;
            java.lang.String r0 = "Can't make request to the API.AI service. Please, check connection settings and API access token."
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ IOException -> 0x0111 }
            r2.<init>(r9)     // Catch:{ IOException -> 0x0111 }
            if (r7 == 0) goto L_0x0011
            com.google.gson.Gson r9 = GSON     // Catch:{ IOException -> 0x0111 }
            java.lang.String r7 = r9.toJson((java.lang.Object) r7)     // Catch:{ IOException -> 0x0111 }
            goto L_0x0012
        L_0x0011:
            r7 = r1
        L_0x0012:
            java.lang.String r9 = "POST"
            if (r10 == 0) goto L_0x0017
            goto L_0x0018
        L_0x0017:
            r10 = r9
        L_0x0018:
            org.apache.logging.log4j.Logger r3 = Log     // Catch:{ IOException -> 0x0111 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0111 }
            r4.<init>()     // Catch:{ IOException -> 0x0111 }
            java.lang.String r5 = "Request json: "
            r4.append(r5)     // Catch:{ IOException -> 0x0111 }
            r4.append(r7)     // Catch:{ IOException -> 0x0111 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0111 }
            r3.debug((java.lang.String) r4)     // Catch:{ IOException -> 0x0111 }
            ai.api.AIConfiguration r3 = r6.config     // Catch:{ IOException -> 0x0111 }
            java.net.Proxy r3 = r3.getProxy()     // Catch:{ IOException -> 0x0111 }
            if (r3 == 0) goto L_0x0043
            ai.api.AIConfiguration r3 = r6.config     // Catch:{ IOException -> 0x0111 }
            java.net.Proxy r3 = r3.getProxy()     // Catch:{ IOException -> 0x0111 }
            java.net.URLConnection r2 = r2.openConnection(r3)     // Catch:{ IOException -> 0x0111 }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ IOException -> 0x0111 }
            goto L_0x0049
        L_0x0043:
            java.net.URLConnection r2 = r2.openConnection()     // Catch:{ IOException -> 0x0111 }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ IOException -> 0x0111 }
        L_0x0049:
            r1 = r2
            if (r7 == 0) goto L_0x005b
            boolean r2 = r9.equals(r10)     // Catch:{ IOException -> 0x0111 }
            if (r2 == 0) goto L_0x0053
            goto L_0x005b
        L_0x0053:
            ai.api.AIServiceException r7 = new ai.api.AIServiceException     // Catch:{ IOException -> 0x0111 }
            java.lang.String r8 = "Non-empty request should be sent using POST method"
            r7.<init>((java.lang.String) r8)     // Catch:{ IOException -> 0x0111 }
            throw r7     // Catch:{ IOException -> 0x0111 }
        L_0x005b:
            r1.setRequestMethod(r10)     // Catch:{ IOException -> 0x0111 }
            boolean r9 = r9.equals(r10)     // Catch:{ IOException -> 0x0111 }
            if (r9 == 0) goto L_0x0068
            r9 = 1
            r1.setDoOutput(r9)     // Catch:{ IOException -> 0x0111 }
        L_0x0068:
            java.lang.String r9 = "Authorization"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0111 }
            r10.<init>()     // Catch:{ IOException -> 0x0111 }
            java.lang.String r2 = "Bearer "
            r10.append(r2)     // Catch:{ IOException -> 0x0111 }
            ai.api.AIConfiguration r2 = r6.config     // Catch:{ IOException -> 0x0111 }
            java.lang.String r2 = r2.getApiKey()     // Catch:{ IOException -> 0x0111 }
            r10.append(r2)     // Catch:{ IOException -> 0x0111 }
            java.lang.String r10 = r10.toString()     // Catch:{ IOException -> 0x0111 }
            r1.addRequestProperty(r9, r10)     // Catch:{ IOException -> 0x0111 }
            java.lang.String r9 = "Content-Type"
            java.lang.String r10 = "application/json; charset=utf-8"
            r1.addRequestProperty(r9, r10)     // Catch:{ IOException -> 0x0111 }
            java.lang.String r9 = "Accept"
            java.lang.String r10 = "application/json"
            r1.addRequestProperty(r9, r10)     // Catch:{ IOException -> 0x0111 }
            if (r11 == 0) goto L_0x00b8
            java.util.Set r9 = r11.entrySet()     // Catch:{ IOException -> 0x0111 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ IOException -> 0x0111 }
        L_0x009c:
            boolean r10 = r9.hasNext()     // Catch:{ IOException -> 0x0111 }
            if (r10 == 0) goto L_0x00b8
            java.lang.Object r10 = r9.next()     // Catch:{ IOException -> 0x0111 }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ IOException -> 0x0111 }
            java.lang.Object r11 = r10.getKey()     // Catch:{ IOException -> 0x0111 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ IOException -> 0x0111 }
            java.lang.Object r10 = r10.getValue()     // Catch:{ IOException -> 0x0111 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ IOException -> 0x0111 }
            r1.addRequestProperty(r11, r10)     // Catch:{ IOException -> 0x0111 }
            goto L_0x009c
        L_0x00b8:
            r1.connect()     // Catch:{ IOException -> 0x0111 }
            if (r7 == 0) goto L_0x00cc
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0111 }
            java.io.OutputStream r10 = r1.getOutputStream()     // Catch:{ IOException -> 0x0111 }
            r9.<init>(r10)     // Catch:{ IOException -> 0x0111 }
            p000ai.api.util.IOUtils.writeAll(r7, r9)     // Catch:{ IOException -> 0x0111 }
            r9.close()     // Catch:{ IOException -> 0x0111 }
        L_0x00cc:
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0111 }
            java.io.InputStream r9 = r1.getInputStream()     // Catch:{ IOException -> 0x0111 }
            r7.<init>(r9)     // Catch:{ IOException -> 0x0111 }
            java.lang.String r9 = p000ai.api.util.IOUtils.readAll((java.io.InputStream) r7)     // Catch:{ IOException -> 0x0111 }
            r7.close()     // Catch:{ IOException -> 0x0111 }
            com.google.gson.Gson r7 = GSON     // Catch:{ JsonParseException -> 0x0103 }
            java.lang.Class<ai.api.model.AIResponse> r10 = p000ai.api.model.AIResponse.class
            java.lang.Object r7 = r7.fromJson((java.lang.String) r9, r10)     // Catch:{ JsonParseException -> 0x0103 }
            ai.api.model.AIResponse r7 = (p000ai.api.model.AIResponse) r7     // Catch:{ JsonParseException -> 0x0103 }
            ai.api.model.Status r10 = r7.getStatus()     // Catch:{ JsonParseException -> 0x0103 }
            if (r10 == 0) goto L_0x0103
            ai.api.model.Status r10 = r7.getStatus()     // Catch:{ JsonParseException -> 0x0103 }
            java.lang.Integer r10 = r10.getCode()     // Catch:{ JsonParseException -> 0x0103 }
            int r10 = r10.intValue()     // Catch:{ JsonParseException -> 0x0103 }
            r11 = 200(0xc8, float:2.8E-43)
            if (r10 != r11) goto L_0x00fd
            goto L_0x0103
        L_0x00fd:
            ai.api.AIDataService$BadResponseStatusException r10 = new ai.api.AIDataService$BadResponseStatusException     // Catch:{ JsonParseException -> 0x0103 }
            r10.<init>(r7)     // Catch:{ JsonParseException -> 0x0103 }
            throw r10     // Catch:{ JsonParseException -> 0x0103 }
        L_0x0103:
            com.google.gson.Gson r7 = GSON     // Catch:{ IOException -> 0x0111 }
            java.lang.Object r7 = r7.fromJson((java.lang.String) r9, (java.lang.reflect.Type) r8)     // Catch:{ IOException -> 0x0111 }
            if (r1 == 0) goto L_0x010e
            r1.disconnect()
        L_0x010e:
            return r7
        L_0x010f:
            r7 = move-exception
            goto L_0x0144
        L_0x0111:
            r7 = move-exception
            if (r1 == 0) goto L_0x0139
            java.io.InputStream r8 = r1.getErrorStream()     // Catch:{ IOException -> 0x0131 }
            if (r8 == 0) goto L_0x0129
            java.lang.String r8 = p000ai.api.util.IOUtils.readAll((java.io.InputStream) r8)     // Catch:{ IOException -> 0x0131 }
            org.apache.logging.log4j.Logger r9 = Log     // Catch:{ IOException -> 0x0131 }
            r9.debug((java.lang.String) r8)     // Catch:{ IOException -> 0x0131 }
            ai.api.AIServiceException r9 = new ai.api.AIServiceException     // Catch:{ IOException -> 0x0131 }
            r9.<init>(r8, r7)     // Catch:{ IOException -> 0x0131 }
            throw r9     // Catch:{ IOException -> 0x0131 }
        L_0x0129:
            ai.api.AIServiceException r8 = new ai.api.AIServiceException     // Catch:{ IOException -> 0x0131 }
            java.lang.String r9 = "Can't connect to the api.ai service."
            r8.<init>(r9, r7)     // Catch:{ IOException -> 0x0131 }
            throw r8     // Catch:{ IOException -> 0x0131 }
        L_0x0131:
            r8 = move-exception
            org.apache.logging.log4j.Logger r9 = Log     // Catch:{ all -> 0x010f }
            java.lang.String r10 = "Can't read error response"
            r9.warn((java.lang.String) r10, (java.lang.Throwable) r8)     // Catch:{ all -> 0x010f }
        L_0x0139:
            org.apache.logging.log4j.Logger r8 = Log     // Catch:{ all -> 0x010f }
            r8.error((java.lang.String) r0, (java.lang.Throwable) r7)     // Catch:{ all -> 0x010f }
            ai.api.AIServiceException r8 = new ai.api.AIServiceException     // Catch:{ all -> 0x010f }
            r8.<init>(r0, r7)     // Catch:{ all -> 0x010f }
            throw r8     // Catch:{ all -> 0x010f }
        L_0x0144:
            if (r1 == 0) goto L_0x0149
            r1.disconnect()
        L_0x0149:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.AIDataService.doRequest(java.lang.Object, java.lang.reflect.Type, java.lang.String, java.lang.String, java.util.Map):java.lang.Object");
    }

    private void fillRequest(AIRequest aIRequest, RequestExtras requestExtras) {
        if (requestExtras.hasContexts()) {
            aIRequest.setContexts(requestExtras.getContexts());
        }
        if (requestExtras.hasEntities()) {
            aIRequest.setEntities(requestExtras.getEntities());
        }
        if (requestExtras.getLocation() != null) {
            aIRequest.setLocation(requestExtras.getLocation());
        }
    }

    private String getSessionId(AIServiceContext aIServiceContext) {
        if (aIServiceContext != null) {
            return aIServiceContext.getSessionId();
        }
        return this.defaultServiceContext.getSessionId();
    }

    private String getTimeZone(AIServiceContext aIServiceContext) {
        TimeZone timeZone;
        if (aIServiceContext != null) {
            timeZone = aIServiceContext.getTimeZone();
        } else {
            timeZone = this.defaultServiceContext.getTimeZone();
        }
        if (timeZone == null) {
            timeZone = Calendar.getInstance().getTimeZone();
        }
        return timeZone.getID();
    }

    /* renamed from: ai.api.AIDataService$ApiActiveContextNamesResponse */
    private static class ApiActiveContextNamesResponse extends AIResponse {
        private static final long serialVersionUID = 1;
        public List<String> names;

        private ApiActiveContextNamesResponse() {
        }
    }

    /* renamed from: ai.api.AIDataService$BadResponseStatusException */
    private static class BadResponseStatusException extends Exception {
        private static final long serialVersionUID = 1;
        /* access modifiers changed from: private */
        public final AIResponse response;

        public BadResponseStatusException(AIResponse aIResponse) {
            this.response = aIResponse;
        }
    }
}
