package com.amplitude.api;

import android.util.Log;
import com.onesignal.shortcutbadger.impl.NewHtcHomeBadger;
import com.thunkable.live.BuildConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Diagnostics {
    public static final int DIAGNOSTIC_EVENT_API_VERSION = 1;
    public static final String DIAGNOSTIC_EVENT_ENDPOINT = "https://api.amplitude.com/diagnostic";
    public static final int DIAGNOSTIC_EVENT_MAX_COUNT = 50;
    public static final int DIAGNOSTIC_EVENT_MIN_COUNT = 5;
    protected static Diagnostics instance;
    private volatile String apiKey;
    /* access modifiers changed from: private */
    public volatile String deviceId;
    int diagnosticEventMaxCount = 50;
    WorkerThread diagnosticThread = new WorkerThread("diagnosticThread");
    volatile boolean enabled = false;
    private volatile OkHttpClient httpClient;
    List<String> unsentErrorStrings = new ArrayList(this.diagnosticEventMaxCount);
    Map<String, JSONObject> unsentErrors = new HashMap(this.diagnosticEventMaxCount);
    String url = DIAGNOSTIC_EVENT_ENDPOINT;

    static synchronized Diagnostics getLogger() {
        Diagnostics diagnostics;
        synchronized (Diagnostics.class) {
            if (instance == null) {
                instance = new Diagnostics();
            }
            diagnostics = instance;
        }
        return diagnostics;
    }

    private Diagnostics() {
        this.diagnosticThread.start();
    }

    /* access modifiers changed from: package-private */
    public Diagnostics enableLogging(OkHttpClient okHttpClient, String str, String str2) {
        this.enabled = true;
        this.apiKey = str;
        this.httpClient = okHttpClient;
        this.deviceId = str2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public Diagnostics disableLogging() {
        this.enabled = false;
        return this;
    }

    /* access modifiers changed from: package-private */
    public Diagnostics setDiagnosticEventMaxCount(final int i) {
        runOnBgThread(new Runnable() {
            public void run() {
                this.diagnosticEventMaxCount = Math.max(i, 5);
                Diagnostics diagnostics = this;
                diagnostics.diagnosticEventMaxCount = Math.min(diagnostics.diagnosticEventMaxCount, 50);
                if (this.diagnosticEventMaxCount < this.unsentErrorStrings.size()) {
                    for (int i = 0; i < Diagnostics.this.unsentErrorStrings.size() - this.diagnosticEventMaxCount; i++) {
                        Diagnostics.this.unsentErrors.remove(Diagnostics.this.unsentErrorStrings.remove(0));
                    }
                }
            }
        });
        return this;
    }

    /* access modifiers changed from: package-private */
    public Diagnostics logError(String str) {
        return logError(str, (Throwable) null);
    }

    /* access modifiers changed from: package-private */
    public Diagnostics logError(final String str, final Throwable th) {
        if (this.enabled && !C0772Utils.isEmptyString(str) && !C0772Utils.isEmptyString(this.deviceId)) {
            runOnBgThread(new Runnable() {
                public void run() {
                    JSONObject jSONObject = Diagnostics.this.unsentErrors.get(str);
                    if (jSONObject == null) {
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put("error", AmplitudeClient.truncate(str));
                            jSONObject2.put("timestamp", System.currentTimeMillis());
                            jSONObject2.put(AmplitudeClient.DEVICE_ID_KEY, Diagnostics.this.deviceId);
                            jSONObject2.put(NewHtcHomeBadger.COUNT, 1);
                            if (th != null) {
                                String stackTraceString = Log.getStackTraceString(th);
                                if (!C0772Utils.isEmptyString(stackTraceString)) {
                                    jSONObject2.put("stack_trace", AmplitudeClient.truncate(stackTraceString));
                                }
                            }
                            if (Diagnostics.this.unsentErrorStrings.size() >= Diagnostics.this.diagnosticEventMaxCount) {
                                for (int i = 0; i < 5; i++) {
                                    Diagnostics.this.unsentErrors.remove(Diagnostics.this.unsentErrorStrings.remove(0));
                                }
                            }
                            Diagnostics.this.unsentErrors.put(str, jSONObject2);
                            Diagnostics.this.unsentErrorStrings.add(str);
                        } catch (JSONException unused) {
                        }
                    } else {
                        jSONObject.put(NewHtcHomeBadger.COUNT, jSONObject.optInt(NewHtcHomeBadger.COUNT, 0) + 1);
                    }
                }
            });
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public Diagnostics flushEvents() {
        if (this.enabled && !C0772Utils.isEmptyString(this.apiKey) && this.httpClient != null && !C0772Utils.isEmptyString(this.deviceId)) {
            runOnBgThread(new Runnable() {
                public void run() {
                    if (!Diagnostics.this.unsentErrorStrings.isEmpty()) {
                        ArrayList arrayList = new ArrayList(Diagnostics.this.unsentErrorStrings.size());
                        for (String str : Diagnostics.this.unsentErrorStrings) {
                            arrayList.add(Diagnostics.this.unsentErrors.get(str));
                        }
                        String jSONArray = new JSONArray(arrayList).toString();
                        if (!C0772Utils.isEmptyString(jSONArray)) {
                            Diagnostics.this.makeEventUploadPostRequest(jSONArray);
                        }
                    }
                }
            });
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void makeEventUploadPostRequest(String str) {
        FormBody.Builder add = new FormBody.Builder().add("v", BuildConfig.VERSION_NAME).add("client", this.apiKey).add("e", str);
        try {
            if (this.httpClient.newCall(new Request.Builder().url(this.url).post(add.add("upload_time", "" + System.currentTimeMillis()).build()).build()).execute().body().string().equals("success")) {
                this.unsentErrors.clear();
                this.unsentErrorStrings.clear();
            }
        } catch (IOException | AssertionError | Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void runOnBgThread(Runnable runnable) {
        Thread currentThread = Thread.currentThread();
        WorkerThread workerThread = this.diagnosticThread;
        if (currentThread != workerThread) {
            workerThread.post(runnable);
        } else {
            runnable.run();
        }
    }
}
