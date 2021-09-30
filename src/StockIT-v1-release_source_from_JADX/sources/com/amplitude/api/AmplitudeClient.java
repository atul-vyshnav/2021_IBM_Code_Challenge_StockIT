package com.amplitude.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.util.Pair;
import androidx.core.p005os.EnvironmentCompat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.UByte;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

public class AmplitudeClient {
    public static final String DEVICE_ID_KEY = "device_id";
    public static final String END_SESSION_EVENT = "session_end";
    public static final String LAST_EVENT_ID_KEY = "last_event_id";
    public static final String LAST_EVENT_TIME_KEY = "last_event_time";
    public static final String LAST_IDENTIFY_ID_KEY = "last_identify_id";
    public static final String OPT_OUT_KEY = "opt_out";
    public static final String PREVIOUS_SESSION_ID_KEY = "previous_session_id";
    public static final String SEQUENCE_NUMBER_KEY = "sequence_number";
    public static final String START_SESSION_EVENT = "session_start";
    public static final String TAG = "com.amplitude.api.AmplitudeClient";
    public static final String USER_ID_KEY = "user_id";
    /* access modifiers changed from: private */
    public static final AmplitudeLog logger = AmplitudeLog.getLogger();
    protected String apiKey;
    JSONObject apiPropertiesTrackingOptions;
    /* access modifiers changed from: private */
    public boolean backoffUpload;
    /* access modifiers changed from: private */
    public int backoffUploadBatchSize;
    protected Context context;
    protected DatabaseHelper dbHelper;
    protected String deviceId;
    /* access modifiers changed from: private */
    public DeviceInfo deviceInfo;
    private int eventMaxCount;
    /* access modifiers changed from: private */
    public int eventUploadMaxBatchSize;
    private long eventUploadPeriodMillis;
    /* access modifiers changed from: private */
    public int eventUploadThreshold;
    /* access modifiers changed from: private */
    public boolean flushEventsOnClose;
    protected OkHttpClient httpClient;
    WorkerThread httpThread;
    /* access modifiers changed from: private */
    public boolean inForeground;
    protected boolean initialized;
    protected String instanceName;
    Throwable lastError;
    long lastEventId;
    long lastEventTime;
    long lastIdentifyId;
    WorkerThread logThread;
    private long minTimeBetweenSessionsMillis;
    private boolean newDeviceIdPerInstall;
    private boolean offline;
    /* access modifiers changed from: private */
    public boolean optOut;
    protected String platform;
    long previousSessionId;
    long sequenceNumber;
    long sessionId;
    private long sessionTimeoutMillis;
    TrackingOptions trackingOptions;
    /* access modifiers changed from: private */
    public boolean trackingSessionEvents;
    /* access modifiers changed from: private */
    public AtomicBoolean updateScheduled;
    AtomicBoolean uploadingCurrently;
    String url;
    private boolean useAdvertisingIdForDeviceId;
    protected String userId;
    private boolean usingForegroundTracking;

    public AmplitudeClient() {
        this((String) null);
    }

    public AmplitudeClient(String str) {
        this.newDeviceIdPerInstall = false;
        this.useAdvertisingIdForDeviceId = false;
        this.initialized = false;
        this.optOut = false;
        this.offline = false;
        this.trackingOptions = new TrackingOptions();
        this.sessionId = -1;
        this.sequenceNumber = 0;
        this.lastEventId = -1;
        this.lastIdentifyId = -1;
        this.lastEventTime = -1;
        this.previousSessionId = -1;
        this.eventUploadThreshold = 30;
        this.eventUploadMaxBatchSize = 50;
        this.eventMaxCount = 1000;
        this.eventUploadPeriodMillis = 30000;
        this.minTimeBetweenSessionsMillis = Constants.MIN_TIME_BETWEEN_SESSIONS_MILLIS;
        this.sessionTimeoutMillis = Constants.SESSION_TIMEOUT_MILLIS;
        this.backoffUpload = false;
        this.backoffUploadBatchSize = 50;
        this.usingForegroundTracking = false;
        this.trackingSessionEvents = false;
        this.inForeground = false;
        this.flushEventsOnClose = true;
        this.updateScheduled = new AtomicBoolean(false);
        this.uploadingCurrently = new AtomicBoolean(false);
        this.url = Constants.EVENT_LOG_URL;
        this.logThread = new WorkerThread("logThread");
        this.httpThread = new WorkerThread("httpThread");
        this.instanceName = C0772Utils.normalizeInstanceName(str);
        this.logThread.start();
        this.httpThread.start();
    }

    public AmplitudeClient initialize(Context context2, String str) {
        return initialize(context2, str, (String) null);
    }

    public AmplitudeClient initialize(Context context2, String str, String str2) {
        return initialize(context2, str, str2, (String) null, false);
    }

    public synchronized AmplitudeClient initialize(Context context2, String str, String str2, String str3, boolean z) {
        if (context2 == null) {
            logger.mo10606e(TAG, "Argument context cannot be null in initialize()");
            return this;
        } else if (C0772Utils.isEmptyString(str)) {
            logger.mo10606e(TAG, "Argument apiKey cannot be null or blank in initialize()");
            return this;
        } else {
            Context applicationContext = context2.getApplicationContext();
            this.context = applicationContext;
            this.apiKey = str;
            this.dbHelper = DatabaseHelper.getDatabaseHelper(applicationContext, this.instanceName);
            if (C0772Utils.isEmptyString(str3)) {
                str3 = Constants.PLATFORM;
            }
            this.platform = str3;
            final Context context3 = context2;
            final boolean z2 = z;
            final String str4 = str;
            final String str5 = str2;
            runOnLogThread(new Runnable() {
                public void run() {
                    if (!AmplitudeClient.this.initialized) {
                        try {
                            if (AmplitudeClient.this.instanceName.equals(Constants.DEFAULT_INSTANCE)) {
                                AmplitudeClient.upgradePrefs(context3);
                                AmplitudeClient.upgradeSharedPrefsToDB(context3);
                            }
                            AmplitudeClient.this.httpClient = new OkHttpClient();
                            DeviceInfo unused = AmplitudeClient.this.deviceInfo = new DeviceInfo(context3);
                            AmplitudeClient.this.deviceId = AmplitudeClient.this.initializeDeviceId();
                            if (z2) {
                                Diagnostics.getLogger().enableLogging(AmplitudeClient.this.httpClient, str4, AmplitudeClient.this.deviceId);
                            }
                            AmplitudeClient.this.deviceInfo.prefetch();
                            if (str5 != null) {
                                this.userId = str5;
                                AmplitudeClient.this.dbHelper.insertOrReplaceKeyValue(AmplitudeClient.USER_ID_KEY, str5);
                            } else {
                                this.userId = AmplitudeClient.this.dbHelper.getValue(AmplitudeClient.USER_ID_KEY);
                            }
                            Long longValue = AmplitudeClient.this.dbHelper.getLongValue(AmplitudeClient.OPT_OUT_KEY);
                            boolean unused2 = AmplitudeClient.this.optOut = longValue != null && longValue.longValue() == 1;
                            AmplitudeClient.this.previousSessionId = AmplitudeClient.this.getLongvalue(AmplitudeClient.PREVIOUS_SESSION_ID_KEY, -1);
                            if (AmplitudeClient.this.previousSessionId >= 0) {
                                AmplitudeClient.this.sessionId = AmplitudeClient.this.previousSessionId;
                            }
                            AmplitudeClient.this.sequenceNumber = AmplitudeClient.this.getLongvalue(AmplitudeClient.SEQUENCE_NUMBER_KEY, 0);
                            AmplitudeClient.this.lastEventId = AmplitudeClient.this.getLongvalue(AmplitudeClient.LAST_EVENT_ID_KEY, -1);
                            AmplitudeClient.this.lastIdentifyId = AmplitudeClient.this.getLongvalue(AmplitudeClient.LAST_IDENTIFY_ID_KEY, -1);
                            AmplitudeClient.this.lastEventTime = AmplitudeClient.this.getLongvalue(AmplitudeClient.LAST_EVENT_TIME_KEY, -1);
                            AmplitudeClient.this.dbHelper.setDatabaseResetListener(new DatabaseResetListener() {
                                public void onDatabaseReset(SQLiteDatabase sQLiteDatabase) {
                                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValueToTable(sQLiteDatabase, "store", AmplitudeClient.DEVICE_ID_KEY, this.deviceId);
                                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValueToTable(sQLiteDatabase, "store", AmplitudeClient.USER_ID_KEY, this.userId);
                                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValueToTable(sQLiteDatabase, "long_store", AmplitudeClient.OPT_OUT_KEY, Long.valueOf(this.optOut ? 1 : 0));
                                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValueToTable(sQLiteDatabase, "long_store", AmplitudeClient.PREVIOUS_SESSION_ID_KEY, Long.valueOf(this.sessionId));
                                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValueToTable(sQLiteDatabase, "long_store", AmplitudeClient.LAST_EVENT_TIME_KEY, Long.valueOf(this.lastEventTime));
                                }
                            });
                            AmplitudeClient.this.initialized = true;
                        } catch (CursorWindowAllocationException e) {
                            AmplitudeClient.logger.mo10606e(AmplitudeClient.TAG, String.format("Failed to initialize Amplitude SDK due to: %s", new Object[]{e.getMessage()}));
                            Diagnostics.getLogger().logError("Failed to initialize Amplitude SDK", e);
                            this.apiKey = null;
                        }
                    }
                }
            });
            return this;
        }
    }

    public AmplitudeClient enableForegroundTracking(Application application) {
        if (!this.usingForegroundTracking && contextAndApiKeySet("enableForegroundTracking()") && Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(new AmplitudeCallbacks(this));
        }
        return this;
    }

    public AmplitudeClient enableDiagnosticLogging() {
        if (!contextAndApiKeySet("enableDiagnosticLogging")) {
            return this;
        }
        Diagnostics.getLogger().enableLogging(this.httpClient, this.apiKey, this.deviceId);
        return this;
    }

    public AmplitudeClient disableDiagnosticLogging() {
        Diagnostics.getLogger().disableLogging();
        return this;
    }

    public AmplitudeClient setDiagnosticEventMaxCount(int i) {
        Diagnostics.getLogger().setDiagnosticEventMaxCount(i);
        return this;
    }

    public AmplitudeClient enableNewDeviceIdPerInstall(boolean z) {
        this.newDeviceIdPerInstall = z;
        return this;
    }

    public AmplitudeClient useAdvertisingIdForDeviceId() {
        this.useAdvertisingIdForDeviceId = true;
        return this;
    }

    public AmplitudeClient enableLocationListening() {
        runOnLogThread(new Runnable() {
            public void run() {
                if (AmplitudeClient.this.deviceInfo != null) {
                    AmplitudeClient.this.deviceInfo.setLocationListening(true);
                    return;
                }
                throw new IllegalStateException("Must initialize before acting on location listening.");
            }
        });
        return this;
    }

    public AmplitudeClient disableLocationListening() {
        runOnLogThread(new Runnable() {
            public void run() {
                if (AmplitudeClient.this.deviceInfo != null) {
                    AmplitudeClient.this.deviceInfo.setLocationListening(false);
                    return;
                }
                throw new IllegalStateException("Must initialize before acting on location listening.");
            }
        });
        return this;
    }

    public AmplitudeClient setEventUploadThreshold(int i) {
        this.eventUploadThreshold = i;
        return this;
    }

    public AmplitudeClient setEventUploadMaxBatchSize(int i) {
        this.eventUploadMaxBatchSize = i;
        this.backoffUploadBatchSize = i;
        return this;
    }

    public AmplitudeClient setEventMaxCount(int i) {
        this.eventMaxCount = i;
        return this;
    }

    public AmplitudeClient setEventUploadPeriodMillis(int i) {
        this.eventUploadPeriodMillis = (long) i;
        return this;
    }

    public AmplitudeClient setMinTimeBetweenSessionsMillis(long j) {
        this.minTimeBetweenSessionsMillis = j;
        return this;
    }

    public AmplitudeClient setServerUrl(String str) {
        if (!C0772Utils.isEmptyString(str)) {
            this.url = str;
        }
        return this;
    }

    public AmplitudeClient setSessionTimeoutMillis(long j) {
        this.sessionTimeoutMillis = j;
        return this;
    }

    public AmplitudeClient setTrackingOptions(TrackingOptions trackingOptions2) {
        this.trackingOptions = trackingOptions2;
        this.apiPropertiesTrackingOptions = trackingOptions2.getApiPropertiesTrackingOptions();
        return this;
    }

    public AmplitudeClient setOptOut(final boolean z) {
        if (!contextAndApiKeySet("setOptOut()")) {
            return this;
        }
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(AmplitudeClient.this.apiKey)) {
                    boolean unused = this.optOut = z;
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyLongValue(AmplitudeClient.OPT_OUT_KEY, Long.valueOf(z ? 1 : 0));
                }
            }
        });
        return this;
    }

    public boolean isOptedOut() {
        return this.optOut;
    }

    public AmplitudeClient enableLogging(boolean z) {
        logger.setEnableLogging(z);
        return this;
    }

    public AmplitudeClient setLogLevel(int i) {
        logger.setLogLevel(i);
        return this;
    }

    public AmplitudeClient setOffline(boolean z) {
        this.offline = z;
        if (!z) {
            uploadEvents();
        }
        return this;
    }

    public AmplitudeClient setFlushEventsOnClose(boolean z) {
        this.flushEventsOnClose = z;
        return this;
    }

    public AmplitudeClient trackSessionEvents(boolean z) {
        this.trackingSessionEvents = z;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void useForegroundTracking() {
        this.usingForegroundTracking = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isUsingForegroundTracking() {
        return this.usingForegroundTracking;
    }

    /* access modifiers changed from: package-private */
    public boolean isInForeground() {
        return this.inForeground;
    }

    public void logEvent(String str) {
        logEvent(str, (JSONObject) null);
    }

    public void logEvent(String str, JSONObject jSONObject) {
        logEvent(str, jSONObject, false);
    }

    public void logEvent(String str, JSONObject jSONObject, boolean z) {
        logEvent(str, jSONObject, (JSONObject) null, z);
    }

    public void logEvent(String str, JSONObject jSONObject, JSONObject jSONObject2) {
        logEvent(str, jSONObject, jSONObject2, false);
    }

    public void logEvent(String str, JSONObject jSONObject, JSONObject jSONObject2, boolean z) {
        logEvent(str, jSONObject, jSONObject2, getCurrentTimeMillis(), z);
    }

    public void logEvent(String str, JSONObject jSONObject, JSONObject jSONObject2, long j, boolean z) {
        if (validateLogEvent(str)) {
            logEventAsync(str, jSONObject, (JSONObject) null, (JSONObject) null, jSONObject2, (JSONObject) null, j, z);
        }
    }

    public void logEventSync(String str) {
        logEventSync(str, (JSONObject) null);
    }

    public void logEventSync(String str, JSONObject jSONObject) {
        logEventSync(str, jSONObject, false);
    }

    public void logEventSync(String str, JSONObject jSONObject, boolean z) {
        logEventSync(str, jSONObject, (JSONObject) null, z);
    }

    public void logEventSync(String str, JSONObject jSONObject, JSONObject jSONObject2) {
        logEventSync(str, jSONObject, jSONObject2, false);
    }

    public void logEventSync(String str, JSONObject jSONObject, JSONObject jSONObject2, boolean z) {
        logEventSync(str, jSONObject, jSONObject2, getCurrentTimeMillis(), z);
    }

    public void logEventSync(String str, JSONObject jSONObject, JSONObject jSONObject2, long j, boolean z) {
        if (validateLogEvent(str)) {
            logEvent(str, jSONObject, (JSONObject) null, (JSONObject) null, jSONObject2, (JSONObject) null, j, z);
        }
    }

    /* access modifiers changed from: protected */
    public boolean validateLogEvent(String str) {
        if (!C0772Utils.isEmptyString(str)) {
            return contextAndApiKeySet("logEvent()");
        }
        logger.mo10606e(TAG, "Argument eventType cannot be null or blank in logEvent()");
        return false;
    }

    /* access modifiers changed from: protected */
    public void logEventAsync(String str, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, JSONObject jSONObject4, JSONObject jSONObject5, long j, boolean z) {
        final JSONObject cloneJSONObject = jSONObject != null ? C0772Utils.cloneJSONObject(jSONObject) : jSONObject;
        final JSONObject cloneJSONObject2 = jSONObject2 != null ? C0772Utils.cloneJSONObject(jSONObject2) : jSONObject2;
        final JSONObject cloneJSONObject3 = jSONObject3 != null ? C0772Utils.cloneJSONObject(jSONObject3) : jSONObject3;
        final JSONObject cloneJSONObject4 = jSONObject4 != null ? C0772Utils.cloneJSONObject(jSONObject4) : jSONObject4;
        final JSONObject cloneJSONObject5 = jSONObject5 != null ? C0772Utils.cloneJSONObject(jSONObject5) : jSONObject5;
        final String str2 = str;
        final long j2 = j;
        final boolean z2 = z;
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(AmplitudeClient.this.apiKey)) {
                    AmplitudeClient.this.logEvent(str2, cloneJSONObject, cloneJSONObject2, cloneJSONObject3, cloneJSONObject4, cloneJSONObject5, j2, z2);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public long logEvent(String str, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, JSONObject jSONObject4, JSONObject jSONObject5, long j, boolean z) {
        String str2;
        long j2;
        JSONObject jSONObject6;
        JSONObject jSONObject7;
        JSONObject jSONObject8;
        Location mostRecentLocation;
        String str3 = str;
        JSONObject jSONObject9 = jSONObject;
        JSONObject jSONObject10 = jSONObject3;
        JSONObject jSONObject11 = jSONObject4;
        JSONObject jSONObject12 = jSONObject5;
        long j3 = j;
        logger.mo10604d(TAG, "Logged event to Amplitude: " + str3);
        if (this.optOut) {
            return -1;
        }
        if (!(this.trackingSessionEvents && (str3.equals(START_SESSION_EVENT) || str3.equals(END_SESSION_EVENT))) && !z) {
            if (!this.inForeground) {
                startNewSessionIfNeeded(j3);
            } else {
                refreshSessionTime(j3);
            }
        }
        JSONObject jSONObject13 = new JSONObject();
        try {
            jSONObject13.put("event_type", replaceWithJSONNull(str));
            jSONObject13.put("timestamp", j3);
            jSONObject13.put(USER_ID_KEY, replaceWithJSONNull(this.userId));
            jSONObject13.put(DEVICE_ID_KEY, replaceWithJSONNull(this.deviceId));
            if (z) {
                j2 = -1;
            } else {
                j2 = this.sessionId;
            }
            jSONObject13.put("session_id", j2);
            jSONObject13.put("uuid", UUID.randomUUID().toString());
            jSONObject13.put(SEQUENCE_NUMBER_KEY, getNextSequenceNumber());
            if (this.trackingOptions.shouldTrackVersionName()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_VERSION_NAME, replaceWithJSONNull(this.deviceInfo.getVersionName()));
            }
            if (this.trackingOptions.shouldTrackOsName()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_OS_NAME, replaceWithJSONNull(this.deviceInfo.getOsName()));
            }
            if (this.trackingOptions.shouldTrackOsVersion()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_OS_VERSION, replaceWithJSONNull(this.deviceInfo.getOsVersion()));
            }
            if (this.trackingOptions.shouldTrackDeviceBrand()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_DEVICE_BRAND, replaceWithJSONNull(this.deviceInfo.getBrand()));
            }
            if (this.trackingOptions.shouldTrackDeviceManufacturer()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_DEVICE_MANUFACTURER, replaceWithJSONNull(this.deviceInfo.getManufacturer()));
            }
            if (this.trackingOptions.shouldTrackDeviceModel()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_DEVICE_MODEL, replaceWithJSONNull(this.deviceInfo.getModel()));
            }
            if (this.trackingOptions.shouldTrackCarrier()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_CARRIER, replaceWithJSONNull(this.deviceInfo.getCarrier()));
            }
            if (this.trackingOptions.shouldTrackCountry()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_COUNTRY, replaceWithJSONNull(this.deviceInfo.getCountry()));
            }
            if (this.trackingOptions.shouldTrackLanguage()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_LANGUAGE, replaceWithJSONNull(this.deviceInfo.getLanguage()));
            }
            if (this.trackingOptions.shouldTrackPlatform()) {
                jSONObject13.put(Constants.AMP_TRACKING_OPTION_PLATFORM, this.platform);
            }
            JSONObject jSONObject14 = new JSONObject();
            jSONObject14.put("name", Constants.LIBRARY);
            jSONObject14.put("version", Constants.VERSION);
            jSONObject13.put("library", jSONObject14);
            JSONObject jSONObject15 = jSONObject2 == null ? new JSONObject() : jSONObject2;
            if (this.apiPropertiesTrackingOptions != null && this.apiPropertiesTrackingOptions.length() > 0) {
                jSONObject15.put("tracking_options", this.apiPropertiesTrackingOptions);
            }
            if (!this.trackingOptions.shouldTrackLatLng() || (mostRecentLocation = this.deviceInfo.getMostRecentLocation()) == null) {
                Object obj = TAG;
            } else {
                JSONObject jSONObject16 = new JSONObject();
                jSONObject16.put("lat", mostRecentLocation.getLatitude());
                str2 = TAG;
                try {
                    jSONObject16.put("lng", mostRecentLocation.getLongitude());
                    jSONObject15.put(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID, jSONObject16);
                } catch (JSONException e) {
                    e = e;
                    logger.mo10606e(str2, String.format("JSON Serialization of event type %s failed, skipping: %s", new Object[]{str3, e.toString()}));
                    Diagnostics.getLogger().logError(String.format("Failed to JSON serialize event type %s", new Object[]{str3}), e);
                    return -1;
                }
            }
            if (this.trackingOptions.shouldTrackAdid() && this.deviceInfo.getAdvertisingId() != null) {
                jSONObject15.put("androidADID", this.deviceInfo.getAdvertisingId());
            }
            jSONObject15.put("limit_ad_tracking", this.deviceInfo.isLimitAdTrackingEnabled());
            jSONObject15.put("gps_enabled", this.deviceInfo.isGooglePlayServicesEnabled());
            jSONObject13.put("api_properties", jSONObject15);
            if (jSONObject9 == null) {
                jSONObject6 = new JSONObject();
            } else {
                jSONObject6 = truncate(jSONObject9);
            }
            jSONObject13.put("event_properties", jSONObject6);
            if (jSONObject10 == null) {
                jSONObject7 = new JSONObject();
            } else {
                jSONObject7 = truncate(jSONObject10);
            }
            jSONObject13.put("user_properties", jSONObject7);
            jSONObject13.put("groups", jSONObject11 == null ? new JSONObject() : truncate(jSONObject11));
            if (jSONObject12 == null) {
                jSONObject8 = new JSONObject();
            } else {
                jSONObject8 = truncate(jSONObject12);
            }
            jSONObject13.put("group_properties", jSONObject8);
            return saveEvent(str3, jSONObject13);
        } catch (JSONException e2) {
            e = e2;
            str2 = TAG;
            logger.mo10606e(str2, String.format("JSON Serialization of event type %s failed, skipping: %s", new Object[]{str3, e.toString()}));
            Diagnostics.getLogger().logError(String.format("Failed to JSON serialize event type %s", new Object[]{str3}), e);
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public long saveEvent(String str, JSONObject jSONObject) {
        String jSONObject2 = jSONObject.toString();
        if (C0772Utils.isEmptyString(jSONObject2)) {
            logger.mo10606e(TAG, String.format("Detected empty event string for event type %s, skipping", new Object[]{str}));
            return -1;
        }
        if (str.equals(Constants.IDENTIFY_EVENT) || str.equals(Constants.GROUP_IDENTIFY_EVENT)) {
            long addIdentify = this.dbHelper.addIdentify(jSONObject2);
            this.lastIdentifyId = addIdentify;
            setLastIdentifyId(addIdentify);
        } else {
            long addEvent = this.dbHelper.addEvent(jSONObject2);
            this.lastEventId = addEvent;
            setLastEventId(addEvent);
        }
        int min = Math.min(Math.max(1, this.eventMaxCount / 10), 20);
        if (this.dbHelper.getEventCount() > ((long) this.eventMaxCount)) {
            DatabaseHelper databaseHelper = this.dbHelper;
            databaseHelper.removeEvents(databaseHelper.getNthEventId((long) min));
        }
        if (this.dbHelper.getIdentifyCount() > ((long) this.eventMaxCount)) {
            DatabaseHelper databaseHelper2 = this.dbHelper;
            databaseHelper2.removeIdentifys(databaseHelper2.getNthIdentifyId((long) min));
        }
        long totalEventCount = this.dbHelper.getTotalEventCount();
        int i = this.eventUploadThreshold;
        if (totalEventCount % ((long) i) != 0 || totalEventCount < ((long) i)) {
            updateServerLater(this.eventUploadPeriodMillis);
        } else {
            updateServer();
        }
        return (str.equals(Constants.IDENTIFY_EVENT) || str.equals(Constants.GROUP_IDENTIFY_EVENT)) ? this.lastIdentifyId : this.lastEventId;
    }

    /* access modifiers changed from: private */
    public long getLongvalue(String str, long j) {
        Long longValue = this.dbHelper.getLongValue(str);
        return longValue == null ? j : longValue.longValue();
    }

    /* access modifiers changed from: package-private */
    public long getNextSequenceNumber() {
        long j = this.sequenceNumber + 1;
        this.sequenceNumber = j;
        this.dbHelper.insertOrReplaceKeyLongValue(SEQUENCE_NUMBER_KEY, Long.valueOf(j));
        return this.sequenceNumber;
    }

    /* access modifiers changed from: package-private */
    public void setLastEventTime(long j) {
        this.lastEventTime = j;
        this.dbHelper.insertOrReplaceKeyLongValue(LAST_EVENT_TIME_KEY, Long.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    public void setLastEventId(long j) {
        this.lastEventId = j;
        this.dbHelper.insertOrReplaceKeyLongValue(LAST_EVENT_ID_KEY, Long.valueOf(j));
    }

    /* access modifiers changed from: package-private */
    public void setLastIdentifyId(long j) {
        this.lastIdentifyId = j;
        this.dbHelper.insertOrReplaceKeyLongValue(LAST_IDENTIFY_ID_KEY, Long.valueOf(j));
    }

    public long getSessionId() {
        return this.sessionId;
    }

    /* access modifiers changed from: package-private */
    public void setPreviousSessionId(long j) {
        this.previousSessionId = j;
        this.dbHelper.insertOrReplaceKeyLongValue(PREVIOUS_SESSION_ID_KEY, Long.valueOf(j));
    }

    public boolean startNewSessionIfNeeded(long j) {
        if (inSession()) {
            if (isWithinMinTimeBetweenSessions(j)) {
                refreshSessionTime(j);
                return false;
            }
            startNewSession(j);
            return true;
        } else if (isWithinMinTimeBetweenSessions(j)) {
            long j2 = this.previousSessionId;
            if (j2 == -1) {
                startNewSession(j);
                return true;
            }
            setSessionId(j2);
            refreshSessionTime(j);
            return false;
        } else {
            startNewSession(j);
            return true;
        }
    }

    private void startNewSession(long j) {
        if (this.trackingSessionEvents) {
            sendSessionEvent(END_SESSION_EVENT);
        }
        setSessionId(j);
        refreshSessionTime(j);
        if (this.trackingSessionEvents) {
            sendSessionEvent(START_SESSION_EVENT);
        }
    }

    private boolean inSession() {
        return this.sessionId >= 0;
    }

    private boolean isWithinMinTimeBetweenSessions(long j) {
        return j - this.lastEventTime < (this.usingForegroundTracking ? this.minTimeBetweenSessionsMillis : this.sessionTimeoutMillis);
    }

    /* access modifiers changed from: private */
    public void setSessionId(long j) {
        this.sessionId = j;
        setPreviousSessionId(j);
    }

    /* access modifiers changed from: package-private */
    public void refreshSessionTime(long j) {
        if (inSession()) {
            setLastEventTime(j);
        }
    }

    /* access modifiers changed from: private */
    public void sendSessionEvent(String str) {
        if (contextAndApiKeySet(String.format("sendSessionEvent('%s')", new Object[]{str})) && inSession()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("special", str);
                logEvent(str, (JSONObject) null, jSONObject, (JSONObject) null, (JSONObject) null, (JSONObject) null, this.lastEventTime, false);
            } catch (JSONException e) {
                Diagnostics.getLogger().logError(String.format("Failed to generate API Properties JSON for session event %s", new Object[]{str}), e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onExitForeground(final long j) {
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(AmplitudeClient.this.apiKey)) {
                    AmplitudeClient.this.refreshSessionTime(j);
                    boolean unused = AmplitudeClient.this.inForeground = false;
                    if (AmplitudeClient.this.flushEventsOnClose) {
                        AmplitudeClient.this.updateServer();
                    }
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValue(AmplitudeClient.DEVICE_ID_KEY, AmplitudeClient.this.deviceId);
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValue(AmplitudeClient.USER_ID_KEY, AmplitudeClient.this.userId);
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyLongValue(AmplitudeClient.OPT_OUT_KEY, Long.valueOf(AmplitudeClient.this.optOut ? 1 : 0));
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyLongValue(AmplitudeClient.PREVIOUS_SESSION_ID_KEY, Long.valueOf(AmplitudeClient.this.sessionId));
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyLongValue(AmplitudeClient.LAST_EVENT_TIME_KEY, Long.valueOf(AmplitudeClient.this.lastEventTime));
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onEnterForeground(final long j) {
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(AmplitudeClient.this.apiKey)) {
                    AmplitudeClient.this.startNewSessionIfNeeded(j);
                    boolean unused = AmplitudeClient.this.inForeground = true;
                }
            }
        });
    }

    public void logRevenue(double d) {
        logRevenue((String) null, 1, d);
    }

    public void logRevenue(String str, int i, double d) {
        logRevenue(str, i, d, (String) null, (String) null);
    }

    public void logRevenue(String str, int i, double d, String str2, String str3) {
        if (contextAndApiKeySet("logRevenue()")) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("special", Constants.AMP_REVENUE_EVENT);
                String str4 = str;
                jSONObject.put("productId", str);
                int i2 = i;
                jSONObject.put("quantity", i);
                jSONObject.put("price", d);
                jSONObject.put("receipt", str2);
                jSONObject.put("receiptSig", str3);
            } catch (JSONException e) {
                Diagnostics.getLogger().logError("Failed to generate API Properties JSON for revenue event", e);
            }
            logEventAsync(Constants.AMP_REVENUE_EVENT, (JSONObject) null, jSONObject, (JSONObject) null, (JSONObject) null, (JSONObject) null, getCurrentTimeMillis(), false);
        }
    }

    public void logRevenueV2(Revenue revenue) {
        if (contextAndApiKeySet("logRevenueV2()") && revenue != null && revenue.isValidRevenue()) {
            logEvent(Constants.AMP_REVENUE_EVENT, revenue.toJSONObject());
        }
    }

    public void setUserProperties(JSONObject jSONObject, boolean z) {
        setUserProperties(jSONObject);
    }

    public void setUserProperties(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0 && contextAndApiKeySet("setUserProperties")) {
            JSONObject truncate = truncate(jSONObject);
            if (truncate.length() != 0) {
                Identify identify = new Identify();
                Iterator<String> keys = truncate.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    try {
                        identify.setUserProperty(next, truncate.get(next));
                    } catch (JSONException e) {
                        logger.mo10606e(TAG, e.toString());
                        Diagnostics.getLogger().logError(String.format("Failed to set user property %s", new Object[]{next}), e);
                    }
                }
                identify(identify);
            }
        }
    }

    public void clearUserProperties() {
        identify(new Identify().clearAll());
    }

    public void identify(Identify identify) {
        identify(identify, false);
    }

    public void identify(Identify identify, boolean z) {
        if (identify != null && identify.userPropertiesOperations.length() != 0 && contextAndApiKeySet("identify()")) {
            logEventAsync(Constants.IDENTIFY_EVENT, (JSONObject) null, (JSONObject) null, identify.userPropertiesOperations, (JSONObject) null, (JSONObject) null, getCurrentTimeMillis(), z);
        }
    }

    public void setGroup(String str, Object obj) {
        JSONObject jSONObject;
        if (contextAndApiKeySet("setGroup()") && !C0772Utils.isEmptyString(str)) {
            try {
                jSONObject = new JSONObject().put(str, obj);
            } catch (JSONException e) {
                logger.mo10606e(TAG, e.toString());
                Diagnostics.getLogger().logError(String.format("Failed to generate Group JSON for groupType: %s", new Object[]{str}), e);
                jSONObject = null;
            }
            logEventAsync(Constants.IDENTIFY_EVENT, (JSONObject) null, (JSONObject) null, new Identify().setUserProperty(str, obj).userPropertiesOperations, jSONObject, (JSONObject) null, getCurrentTimeMillis(), false);
        }
    }

    public void groupIdentify(String str, Object obj, Identify identify) {
        groupIdentify(str, obj, identify, false);
    }

    public void groupIdentify(String str, Object obj, Identify identify, boolean z) {
        JSONObject jSONObject;
        String str2 = str;
        Identify identify2 = identify;
        if (identify2 == null || identify2.userPropertiesOperations.length() == 0) {
            return;
        }
        if (contextAndApiKeySet("groupIdentify()") && !C0772Utils.isEmptyString(str)) {
            try {
                Object obj2 = obj;
                jSONObject = new JSONObject().put(str, obj);
            } catch (JSONException e) {
                logger.mo10606e(TAG, e.toString());
                Diagnostics.getLogger().logError(String.format("Failed to generate Group Identify JSON Object for groupType %s", new Object[]{str2}), e);
                jSONObject = null;
            }
            logEventAsync(Constants.GROUP_IDENTIFY_EVENT, (JSONObject) null, (JSONObject) null, (JSONObject) null, jSONObject, identify2.userPropertiesOperations, getCurrentTimeMillis(), z);
        }
    }

    public JSONObject truncate(JSONObject jSONObject) {
        if (jSONObject == null) {
            return new JSONObject();
        }
        if (jSONObject.length() > 1000) {
            logger.mo10617w(TAG, "Warning: too many properties (more than 1000), ignoring");
            return new JSONObject();
        }
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                Object obj = jSONObject.get(next);
                if (!next.equals(Constants.AMP_REVENUE_RECEIPT)) {
                    if (!next.equals(Constants.AMP_REVENUE_RECEIPT_SIG)) {
                        if (obj.getClass().equals(String.class)) {
                            jSONObject.put(next, truncate((String) obj));
                        } else if (obj.getClass().equals(JSONObject.class)) {
                            jSONObject.put(next, truncate((JSONObject) obj));
                        } else if (obj.getClass().equals(JSONArray.class)) {
                            jSONObject.put(next, truncate((JSONArray) obj));
                        }
                    }
                }
                jSONObject.put(next, obj);
            } catch (JSONException e) {
                logger.mo10606e(TAG, e.toString());
            }
        }
        return jSONObject;
    }

    public JSONArray truncate(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return new JSONArray();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if (obj.getClass().equals(String.class)) {
                jSONArray.put(i, truncate((String) obj));
            } else if (obj.getClass().equals(JSONObject.class)) {
                jSONArray.put(i, truncate((JSONObject) obj));
            } else if (obj.getClass().equals(JSONArray.class)) {
                jSONArray.put(i, truncate((JSONArray) obj));
            }
        }
        return jSONArray;
    }

    public static String truncate(String str) {
        return str.length() <= 1024 ? str : str.substring(0, 1024);
    }

    public String getUserId() {
        return this.userId;
    }

    public AmplitudeClient setUserId(String str) {
        return setUserId(str, false);
    }

    public AmplitudeClient setUserId(final String str, final boolean z) {
        if (!contextAndApiKeySet("setUserId()")) {
            return this;
        }
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(this.apiKey)) {
                    if (z && AmplitudeClient.this.trackingSessionEvents) {
                        AmplitudeClient.this.sendSessionEvent(AmplitudeClient.END_SESSION_EVENT);
                    }
                    this.userId = str;
                    AmplitudeClient.this.dbHelper.insertOrReplaceKeyValue(AmplitudeClient.USER_ID_KEY, str);
                    if (z) {
                        long currentTimeMillis = AmplitudeClient.this.getCurrentTimeMillis();
                        AmplitudeClient.this.setSessionId(currentTimeMillis);
                        AmplitudeClient.this.refreshSessionTime(currentTimeMillis);
                        if (AmplitudeClient.this.trackingSessionEvents) {
                            AmplitudeClient.this.sendSessionEvent(AmplitudeClient.START_SESSION_EVENT);
                        }
                    }
                }
            }
        });
        return this;
    }

    public AmplitudeClient setDeviceId(final String str) {
        Set<String> invalidDeviceIds = getInvalidDeviceIds();
        if (contextAndApiKeySet("setDeviceId()") && !C0772Utils.isEmptyString(str) && !invalidDeviceIds.contains(str)) {
            runOnLogThread(new Runnable() {
                public void run() {
                    if (!C0772Utils.isEmptyString(this.apiKey)) {
                        this.deviceId = str;
                        AmplitudeClient.this.saveDeviceId(str);
                    }
                }
            });
        }
        return this;
    }

    public AmplitudeClient regenerateDeviceId() {
        if (!contextAndApiKeySet("regenerateDeviceId()")) {
            return this;
        }
        runOnLogThread(new Runnable() {
            public void run() {
                if (!C0772Utils.isEmptyString(this.apiKey)) {
                    AmplitudeClient.this.setDeviceId(DeviceInfo.generateUUID() + "R");
                }
            }
        });
        return this;
    }

    public void uploadEvents() {
        if (contextAndApiKeySet("uploadEvents()")) {
            this.logThread.post(new Runnable() {
                public void run() {
                    if (!C0772Utils.isEmptyString(AmplitudeClient.this.apiKey)) {
                        AmplitudeClient.this.updateServer();
                    }
                }
            });
        }
    }

    private void updateServerLater(long j) {
        if (!this.updateScheduled.getAndSet(true)) {
            this.logThread.postDelayed(new Runnable() {
                public void run() {
                    AmplitudeClient.this.updateScheduled.set(false);
                    AmplitudeClient.this.updateServer();
                }
            }, j);
        }
    }

    /* access modifiers changed from: protected */
    public void updateServer() {
        updateServer(false);
        Diagnostics.getLogger().flushEvents();
    }

    /* access modifiers changed from: protected */
    public void updateServer(boolean z) {
        if (!this.optOut && !this.offline && !this.uploadingCurrently.getAndSet(true)) {
            long min = Math.min((long) (z ? this.backoffUploadBatchSize : this.eventUploadMaxBatchSize), this.dbHelper.getTotalEventCount());
            if (min <= 0) {
                this.uploadingCurrently.set(false);
                return;
            }
            try {
                Pair<Pair<Long, Long>, JSONArray> mergeEventsAndIdentifys = mergeEventsAndIdentifys(this.dbHelper.getEvents(this.lastEventId, min), this.dbHelper.getIdentifys(this.lastIdentifyId, min), min);
                if (((JSONArray) mergeEventsAndIdentifys.second).length() == 0) {
                    this.uploadingCurrently.set(false);
                    return;
                }
                final long longValue = ((Long) ((Pair) mergeEventsAndIdentifys.first).first).longValue();
                final long longValue2 = ((Long) ((Pair) mergeEventsAndIdentifys.first).second).longValue();
                final String jSONArray = ((JSONArray) mergeEventsAndIdentifys.second).toString();
                this.httpThread.post(new Runnable() {
                    public void run() {
                        AmplitudeClient amplitudeClient = AmplitudeClient.this;
                        amplitudeClient.makeEventUploadPostRequest(amplitudeClient.httpClient, jSONArray, longValue, longValue2);
                    }
                });
            } catch (JSONException e) {
                this.uploadingCurrently.set(false);
                logger.mo10606e(TAG, e.toString());
                Diagnostics.getLogger().logError("Failed to update server", e);
            } catch (CursorWindowAllocationException e2) {
                this.uploadingCurrently.set(false);
                logger.mo10606e(TAG, String.format("Caught Cursor window exception during event upload, deferring upload: %s", new Object[]{e2.getMessage()}));
                Diagnostics.getLogger().logError("Failed to update server", e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Pair<Pair<Long, Long>, JSONArray> mergeEventsAndIdentifys(List<JSONObject> list, List<JSONObject> list2, long j) throws JSONException {
        long j2;
        long j3;
        List<JSONObject> list3 = list;
        List<JSONObject> list4 = list2;
        JSONArray jSONArray = new JSONArray();
        long j4 = -1;
        long j5 = -1;
        while (true) {
            if (((long) jSONArray.length()) >= j) {
                break;
            }
            boolean isEmpty = list.isEmpty();
            boolean isEmpty2 = list2.isEmpty();
            if (isEmpty && isEmpty2) {
                logger.mo10617w(TAG, String.format("mergeEventsAndIdentifys: number of events and identifys less than expected by %d", new Object[]{Long.valueOf(j - ((long) jSONArray.length()))}));
                break;
            }
            if (isEmpty2) {
                JSONObject remove = list.remove(0);
                j2 = remove.getLong("event_id");
                jSONArray.put(remove);
            } else {
                if (isEmpty) {
                    JSONObject remove2 = list4.remove(0);
                    j3 = remove2.getLong("event_id");
                    jSONArray.put(remove2);
                } else if (!list.get(0).has(SEQUENCE_NUMBER_KEY) || list.get(0).getLong(SEQUENCE_NUMBER_KEY) < list4.get(0).getLong(SEQUENCE_NUMBER_KEY)) {
                    JSONObject remove3 = list.remove(0);
                    j2 = remove3.getLong("event_id");
                    jSONArray.put(remove3);
                } else {
                    JSONObject remove4 = list4.remove(0);
                    j3 = remove4.getLong("event_id");
                    jSONArray.put(remove4);
                }
                j5 = j3;
            }
            j4 = j2;
        }
        return new Pair<>(new Pair(Long.valueOf(j4), Long.valueOf(j5)), jSONArray);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01cd  */
    /* JADX WARNING: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void makeEventUploadPostRequest(okhttp3.OkHttpClient r16, java.lang.String r17, long r18, long r20) {
        /*
            r15 = this;
            r7 = r15
            r1 = r17
            r3 = r18
            r5 = r20
            java.lang.String r8 = "Exception:"
            java.lang.String r9 = "Failed to post upload request"
            java.lang.String r10 = "com.amplitude.api.AmplitudeClient"
            java.lang.String r2 = "2"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r11 = ""
            r0.append(r11)
            long r12 = r15.getCurrentTimeMillis()
            r0.append(r12)
            java.lang.String r12 = r0.toString()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r0.<init>()     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r0.append(r2)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            java.lang.String r13 = r7.apiKey     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r0.append(r13)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r0.append(r1)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r0.append(r12)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            java.lang.String r0 = r0.toString()     // Catch:{ UnsupportedEncodingException -> 0x004f }
            com.amplitude.security.MD5 r13 = new com.amplitude.security.MD5     // Catch:{ UnsupportedEncodingException -> 0x004f }
            r13.<init>()     // Catch:{ UnsupportedEncodingException -> 0x004f }
            java.lang.String r14 = "UTF-8"
            byte[] r0 = r0.getBytes(r14)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            byte[] r0 = r13.digest(r0)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            java.lang.String r11 = r15.bytesToHexString(r0)     // Catch:{ UnsupportedEncodingException -> 0x004f }
            goto L_0x0062
        L_0x004f:
            r0 = move-exception
            com.amplitude.api.AmplitudeLog r13 = logger
            java.lang.String r14 = r0.toString()
            r13.mo10606e(r10, r14)
            com.amplitude.api.Diagnostics r13 = com.amplitude.api.Diagnostics.getLogger()
            java.lang.String r14 = "Failed to compute checksum for upload request"
            r13.logError(r14, r0)
        L_0x0062:
            okhttp3.FormBody$Builder r0 = new okhttp3.FormBody$Builder
            r0.<init>()
            java.lang.String r13 = "v"
            okhttp3.FormBody$Builder r0 = r0.add(r13, r2)
            java.lang.String r2 = r7.apiKey
            java.lang.String r13 = "client"
            okhttp3.FormBody$Builder r0 = r0.add(r13, r2)
            java.lang.String r2 = "e"
            okhttp3.FormBody$Builder r0 = r0.add(r2, r1)
            java.lang.String r1 = "upload_time"
            okhttp3.FormBody$Builder r0 = r0.add(r1, r12)
            java.lang.String r1 = "checksum"
            okhttp3.FormBody$Builder r0 = r0.add(r1, r11)
            okhttp3.FormBody r0 = r0.build()
            r11 = 0
            okhttp3.Request$Builder r1 = new okhttp3.Request$Builder     // Catch:{ IllegalArgumentException -> 0x01d3 }
            r1.<init>()     // Catch:{ IllegalArgumentException -> 0x01d3 }
            java.lang.String r2 = r7.url     // Catch:{ IllegalArgumentException -> 0x01d3 }
            okhttp3.Request$Builder r1 = r1.url((java.lang.String) r2)     // Catch:{ IllegalArgumentException -> 0x01d3 }
            okhttp3.Request$Builder r0 = r1.post(r0)     // Catch:{ IllegalArgumentException -> 0x01d3 }
            okhttp3.Request r0 = r0.build()     // Catch:{ IllegalArgumentException -> 0x01d3 }
            r12 = 1
            r1 = r16
            okhttp3.Call r0 = r1.newCall(r0)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            okhttp3.Response r0 = r0.execute()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            okhttp3.ResponseBody r1 = r0.body()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = r1.string()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r2 = "success"
            boolean r2 = r1.equals(r2)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r2 == 0) goto L_0x00de
            com.amplitude.api.WorkerThread r0 = r7.logThread     // Catch:{ ConnectException -> 0x00db, UnknownHostException -> 0x00d8, IOException -> 0x00d5, AssertionError -> 0x00d2, Exception -> 0x00cf }
            com.amplitude.api.AmplitudeClient$14 r13 = new com.amplitude.api.AmplitudeClient$14     // Catch:{ ConnectException -> 0x00db, UnknownHostException -> 0x00d8, IOException -> 0x00d5, AssertionError -> 0x00d2, Exception -> 0x00cf }
            r1 = r13
            r2 = r15
            r3 = r18
            r5 = r20
            r1.<init>(r3, r5)     // Catch:{ ConnectException -> 0x00db, UnknownHostException -> 0x00d8, IOException -> 0x00d5, AssertionError -> 0x00d2, Exception -> 0x00cf }
            r0.post(r13)     // Catch:{ ConnectException -> 0x00db, UnknownHostException -> 0x00d8, IOException -> 0x00d5, AssertionError -> 0x00d2, Exception -> 0x00cf }
            goto L_0x01cb
        L_0x00cf:
            r0 = move-exception
            goto L_0x017f
        L_0x00d2:
            r0 = move-exception
            goto L_0x0190
        L_0x00d5:
            r0 = move-exception
            goto L_0x01a1
        L_0x00d8:
            r0 = move-exception
            goto L_0x01b6
        L_0x00db:
            r0 = move-exception
            goto L_0x01c2
        L_0x00de:
            java.lang.String r2 = "invalid_api_key"
            boolean r2 = r1.equals(r2)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r2 == 0) goto L_0x00ef
            com.amplitude.api.AmplitudeLog r0 = logger     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = "Invalid API key, make sure your API key is correct in initialize()"
            r0.mo10606e(r10, r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            goto L_0x017b
        L_0x00ef:
            java.lang.String r2 = "bad_checksum"
            boolean r2 = r1.equals(r2)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r2 == 0) goto L_0x0100
            com.amplitude.api.AmplitudeLog r0 = logger     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = "Bad checksum, post request was mangled in transit, will attempt to reupload later"
            r0.mo10617w((java.lang.String) r10, (java.lang.String) r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            goto L_0x017b
        L_0x0100:
            java.lang.String r2 = "request_db_write_failed"
            boolean r2 = r1.equals(r2)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r2 == 0) goto L_0x0110
            com.amplitude.api.AmplitudeLog r0 = logger     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = "Couldn't write to request database on server, will attempt to reupload later"
            r0.mo10617w((java.lang.String) r10, (java.lang.String) r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            goto L_0x017b
        L_0x0110:
            int r0 = r0.code()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r2 = 413(0x19d, float:5.79E-43)
            if (r0 != r2) goto L_0x0160
            boolean r0 = r7.backoffUpload     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r0 == 0) goto L_0x0134
            int r0 = r7.backoffUploadBatchSize     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            if (r0 != r12) goto L_0x0134
            r0 = 0
            int r2 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r2 < 0) goto L_0x012b
            com.amplitude.api.DatabaseHelper r2 = r7.dbHelper     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r2.removeEvent(r3)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
        L_0x012b:
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 < 0) goto L_0x0134
            com.amplitude.api.DatabaseHelper r0 = r7.dbHelper     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r0.removeIdentify(r5)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
        L_0x0134:
            r7.backoffUpload = r12     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            com.amplitude.api.DatabaseHelper r0 = r7.dbHelper     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            long r0 = r0.getEventCount()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            int r1 = (int) r0     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            int r0 = r7.backoffUploadBatchSize     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            int r0 = java.lang.Math.min(r1, r0)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            double r0 = (double) r0     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r2 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r0 = r0 / r2
            double r0 = java.lang.Math.ceil(r0)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            int r0 = (int) r0     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r7.backoffUploadBatchSize = r0     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            com.amplitude.api.AmplitudeLog r0 = logger     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = "Request too large, will decrease size and attempt to reupload"
            r0.mo10617w((java.lang.String) r10, (java.lang.String) r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            com.amplitude.api.WorkerThread r0 = r7.logThread     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            com.amplitude.api.AmplitudeClient$15 r1 = new com.amplitude.api.AmplitudeClient$15     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r1.<init>()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r0.post(r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            goto L_0x017b
        L_0x0160:
            com.amplitude.api.AmplitudeLog r0 = logger     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r2.<init>()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r3 = "Upload failed, "
            r2.append(r3)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r2.append(r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = ", will attempt to reupload later"
            r2.append(r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            java.lang.String r1 = r2.toString()     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
            r0.mo10617w((java.lang.String) r10, (java.lang.String) r1)     // Catch:{ ConnectException -> 0x01c0, UnknownHostException -> 0x01b4, IOException -> 0x019f, AssertionError -> 0x018e, Exception -> 0x017d }
        L_0x017b:
            r12 = 0
            goto L_0x01cb
        L_0x017d:
            r0 = move-exception
            r12 = 0
        L_0x017f:
            com.amplitude.api.AmplitudeLog r1 = logger
            r1.mo10607e(r10, r8, r0)
            r7.lastError = r0
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            r1.logError(r9, r0)
            goto L_0x01cb
        L_0x018e:
            r0 = move-exception
            r12 = 0
        L_0x0190:
            com.amplitude.api.AmplitudeLog r1 = logger
            r1.mo10607e(r10, r8, r0)
            r7.lastError = r0
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            r1.logError(r9, r0)
            goto L_0x01cb
        L_0x019f:
            r0 = move-exception
            r12 = 0
        L_0x01a1:
            com.amplitude.api.AmplitudeLog r1 = logger
            java.lang.String r2 = r0.toString()
            r1.mo10606e(r10, r2)
            r7.lastError = r0
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            r1.logError(r9, r0)
            goto L_0x01cb
        L_0x01b4:
            r0 = move-exception
            r12 = 0
        L_0x01b6:
            r7.lastError = r0
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            r1.logError(r9, r0)
            goto L_0x01cb
        L_0x01c0:
            r0 = move-exception
            r12 = 0
        L_0x01c2:
            r7.lastError = r0
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            r1.logError(r9, r0)
        L_0x01cb:
            if (r12 != 0) goto L_0x01d2
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.uploadingCurrently
            r0.set(r11)
        L_0x01d2:
            return
        L_0x01d3:
            r0 = move-exception
            com.amplitude.api.AmplitudeLog r1 = logger
            java.lang.String r2 = r0.toString()
            r1.mo10606e(r10, r2)
            java.util.concurrent.atomic.AtomicBoolean r1 = r7.uploadingCurrently
            r1.set(r11)
            com.amplitude.api.Diagnostics r1 = com.amplitude.api.Diagnostics.getLogger()
            java.lang.String r2 = "Failed to build upload request"
            r1.logError(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amplitude.api.AmplitudeClient.makeEventUploadPostRequest(okhttp3.OkHttpClient, java.lang.String, long, long):void");
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    private Set<String> getInvalidDeviceIds() {
        HashSet hashSet = new HashSet();
        hashSet.add("");
        hashSet.add("9774d56d682e549c");
        hashSet.add(EnvironmentCompat.MEDIA_UNKNOWN);
        hashSet.add("000000000000000");
        hashSet.add(Constants.PLATFORM);
        hashSet.add("DEFACE");
        hashSet.add("00000000-0000-0000-0000-000000000000");
        return hashSet;
    }

    /* access modifiers changed from: private */
    public String initializeDeviceId() {
        Set<String> invalidDeviceIds = getInvalidDeviceIds();
        String value = this.dbHelper.getValue(DEVICE_ID_KEY);
        String stringFromSharedPreferences = C0772Utils.getStringFromSharedPreferences(this.context, this.instanceName, DEVICE_ID_KEY);
        if (!C0772Utils.isEmptyString(value) && !invalidDeviceIds.contains(value)) {
            if (!value.equals(stringFromSharedPreferences)) {
                saveDeviceId(value);
            }
            return value;
        } else if (C0772Utils.isEmptyString(stringFromSharedPreferences) || invalidDeviceIds.contains(stringFromSharedPreferences)) {
            if (!this.newDeviceIdPerInstall && this.useAdvertisingIdForDeviceId && !this.deviceInfo.isLimitAdTrackingEnabled()) {
                String advertisingId = this.deviceInfo.getAdvertisingId();
                if (!C0772Utils.isEmptyString(advertisingId) && !invalidDeviceIds.contains(advertisingId)) {
                    saveDeviceId(advertisingId);
                    return advertisingId;
                }
            }
            String str = DeviceInfo.generateUUID() + "R";
            saveDeviceId(str);
            return str;
        } else {
            saveDeviceId(stringFromSharedPreferences);
            return stringFromSharedPreferences;
        }
    }

    /* access modifiers changed from: private */
    public void saveDeviceId(String str) {
        this.dbHelper.insertOrReplaceKeyValue(DEVICE_ID_KEY, str);
        C0772Utils.writeStringToSharedPreferences(this.context, this.instanceName, DEVICE_ID_KEY, str);
    }

    /* access modifiers changed from: protected */
    public void runOnLogThread(Runnable runnable) {
        Thread currentThread = Thread.currentThread();
        WorkerThread workerThread = this.logThread;
        if (currentThread != workerThread) {
            workerThread.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* access modifiers changed from: protected */
    public Object replaceWithJSONNull(Object obj) {
        return obj == null ? JSONObject.NULL : obj;
    }

    /* access modifiers changed from: protected */
    public synchronized boolean contextAndApiKeySet(String str) {
        if (this.context == null) {
            AmplitudeLog amplitudeLog = logger;
            amplitudeLog.mo10606e(TAG, "context cannot be null, set context with initialize() before calling " + str);
            return false;
        } else if (!C0772Utils.isEmptyString(this.apiKey)) {
            return true;
        } else {
            AmplitudeLog amplitudeLog2 = logger;
            amplitudeLog2.mo10606e(TAG, "apiKey cannot be null or empty, set apiKey with initialize() before calling " + str);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public String bytesToHexString(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & UByte.MAX_VALUE;
            int i2 = i * 2;
            cArr2[i2] = cArr[b >>> 4];
            cArr2[i2 + 1] = cArr[b & 15];
        }
        return new String(cArr2);
    }

    static boolean upgradePrefs(Context context2) {
        return upgradePrefs(context2, (String) null, (String) null);
    }

    static boolean upgradePrefs(Context context2, String str, String str2) {
        if (str == null) {
            try {
                str = Constants.class.getPackage().getName();
            } catch (Exception unused) {
                str = "com.amplitude.api";
            }
        }
        if (str2 == null) {
            str2 = "com.amplitude.api";
        }
        try {
            if (str2.equals(str)) {
                return false;
            }
            String str3 = str + "." + context2.getPackageName();
            SharedPreferences sharedPreferences = context2.getSharedPreferences(str3, 0);
            if (sharedPreferences.getAll().size() == 0) {
                return false;
            }
            String str4 = str2 + "." + context2.getPackageName();
            SharedPreferences.Editor edit = context2.getSharedPreferences(str4, 0).edit();
            if (sharedPreferences.contains(str + ".previousSessionId")) {
                edit.putLong(Constants.PREFKEY_PREVIOUS_SESSION_ID, sharedPreferences.getLong(str + ".previousSessionId", -1));
            }
            if (sharedPreferences.contains(str + ".deviceId")) {
                edit.putString(Constants.PREFKEY_DEVICE_ID, sharedPreferences.getString(str + ".deviceId", (String) null));
            }
            if (sharedPreferences.contains(str + ".userId")) {
                edit.putString(Constants.PREFKEY_USER_ID, sharedPreferences.getString(str + ".userId", (String) null));
            }
            if (sharedPreferences.contains(str + ".optOut")) {
                edit.putBoolean(Constants.PREFKEY_OPT_OUT, sharedPreferences.getBoolean(str + ".optOut", false));
            }
            edit.apply();
            sharedPreferences.edit().clear().apply();
            logger.mo10609i(TAG, "Upgraded shared preferences from " + str3 + " to " + str4);
            return true;
        } catch (Exception e) {
            logger.mo10607e(TAG, "Error upgrading shared preferences", e);
            Diagnostics.getLogger().logError("Failed to upgrade shared prefs", e);
            return false;
        }
    }

    static boolean upgradeSharedPrefsToDB(Context context2) {
        return upgradeSharedPrefsToDB(context2, (String) null);
    }

    static boolean upgradeSharedPrefsToDB(Context context2, String str) {
        if (str == null) {
            str = "com.amplitude.api";
        }
        DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelper(context2);
        String value = databaseHelper.getValue(DEVICE_ID_KEY);
        Long longValue = databaseHelper.getLongValue(PREVIOUS_SESSION_ID_KEY);
        Long longValue2 = databaseHelper.getLongValue(LAST_EVENT_TIME_KEY);
        if (!C0772Utils.isEmptyString(value) && longValue != null && longValue2 != null) {
            return true;
        }
        SharedPreferences sharedPreferences = context2.getSharedPreferences(str + "." + context2.getPackageName(), 0);
        migrateStringValue(sharedPreferences, Constants.PREFKEY_DEVICE_ID, (String) null, databaseHelper, DEVICE_ID_KEY);
        SharedPreferences sharedPreferences2 = sharedPreferences;
        DatabaseHelper databaseHelper2 = databaseHelper;
        migrateLongValue(sharedPreferences2, Constants.PREFKEY_LAST_EVENT_TIME, -1, databaseHelper2, LAST_EVENT_TIME_KEY);
        migrateLongValue(sharedPreferences2, Constants.PREFKEY_LAST_EVENT_ID, -1, databaseHelper2, LAST_EVENT_ID_KEY);
        migrateLongValue(sharedPreferences2, Constants.PREFKEY_LAST_IDENTIFY_ID, -1, databaseHelper2, LAST_IDENTIFY_ID_KEY);
        migrateLongValue(sharedPreferences2, Constants.PREFKEY_PREVIOUS_SESSION_ID, -1, databaseHelper2, PREVIOUS_SESSION_ID_KEY);
        migrateStringValue(sharedPreferences, Constants.PREFKEY_USER_ID, (String) null, databaseHelper, USER_ID_KEY);
        migrateBooleanValue(sharedPreferences, Constants.PREFKEY_OPT_OUT, false, databaseHelper, OPT_OUT_KEY);
        return true;
    }

    private static void migrateLongValue(SharedPreferences sharedPreferences, String str, long j, DatabaseHelper databaseHelper, String str2) {
        if (databaseHelper.getLongValue(str2) == null) {
            databaseHelper.insertOrReplaceKeyLongValue(str2, Long.valueOf(sharedPreferences.getLong(str, j)));
            sharedPreferences.edit().remove(str).apply();
        }
    }

    private static void migrateStringValue(SharedPreferences sharedPreferences, String str, String str2, DatabaseHelper databaseHelper, String str3) {
        if (C0772Utils.isEmptyString(databaseHelper.getValue(str3))) {
            String string = sharedPreferences.getString(str, str2);
            if (!C0772Utils.isEmptyString(string)) {
                databaseHelper.insertOrReplaceKeyValue(str3, string);
                sharedPreferences.edit().remove(str).apply();
            }
        }
    }

    private static void migrateBooleanValue(SharedPreferences sharedPreferences, String str, boolean z, DatabaseHelper databaseHelper, String str2) {
        if (databaseHelper.getLongValue(str2) == null) {
            databaseHelper.insertOrReplaceKeyLongValue(str2, Long.valueOf(sharedPreferences.getBoolean(str, z) ? 1 : 0));
            sharedPreferences.edit().remove(str).apply();
        }
    }

    /* access modifiers changed from: protected */
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
