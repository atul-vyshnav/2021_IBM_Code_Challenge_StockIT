package com.onesignal;

import com.onesignal.LocationController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

abstract class UserState {
    public static final int DEVICE_TYPE_ANDROID = 1;
    public static final int DEVICE_TYPE_EMAIL = 11;
    public static final int DEVICE_TYPE_FIREOS = 2;
    public static final int DEVICE_TYPE_HUAWEI = 13;
    private static final String[] LOCATION_FIELDS = {"lat", "long", "loc_acc", "loc_type", "loc_bg", "loc_time_stamp", "ad_id"};
    private static final Set<String> LOCATION_FIELDS_SET = new HashSet(Arrays.asList(LOCATION_FIELDS));
    private static final Object LOCK = new Object();
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_IOEXCEPTION = -11;
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_MISC_EXCEPTION = -12;
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_SERVICE_NOT_AVAILABLE = -9;
    static final int PUSH_STATUS_FIREBASE_FCM_INIT_ERROR = -8;
    public static final int PUSH_STATUS_HMS_API_EXCEPTION_OTHER = -27;
    public static final int PUSH_STATUS_HMS_ARGUMENTS_INVALID = -26;
    public static final int PUSH_STATUS_HMS_TOKEN_TIMEOUT = -25;
    static final int PUSH_STATUS_INVALID_FCM_SENDER_ID = -6;
    static final int PUSH_STATUS_MISSING_ANDROID_SUPPORT_LIBRARY = -3;
    static final int PUSH_STATUS_MISSING_FIREBASE_FCM_LIBRARY = -4;
    public static final int PUSH_STATUS_MISSING_HMS_PUSHKIT_LIBRARY = -28;
    static final int PUSH_STATUS_NO_PERMISSION = 0;
    static final int PUSH_STATUS_OUTDATED_ANDROID_SUPPORT_LIBRARY = -5;
    static final int PUSH_STATUS_OUTDATED_GOOGLE_PLAY_SERVICES_APP = -7;
    public static final int PUSH_STATUS_SUBSCRIBED = 1;
    static final int PUSH_STATUS_UNSUBSCRIBE = -2;
    public static final String TAGS = "tags";
    private JSONObject dependValues;
    private String persistKey;
    private JSONObject syncValues;

    /* access modifiers changed from: protected */
    public abstract void addDependFields();

    /* access modifiers changed from: package-private */
    public abstract boolean isSubscribed();

    /* access modifiers changed from: package-private */
    public abstract UserState newInstance(String str);

    public ImmutableJSONObject getDependValues() {
        try {
            return new ImmutableJSONObject(getDependValuesCopy());
        } catch (JSONException e) {
            e.printStackTrace();
            return new ImmutableJSONObject();
        }
    }

    /* access modifiers changed from: package-private */
    public void setDependValues(JSONObject jSONObject) {
        synchronized (LOCK) {
            this.dependValues = jSONObject;
        }
    }

    /* access modifiers changed from: package-private */
    public JSONObject getDependValuesCopy() throws JSONException {
        JSONObject jSONObject;
        synchronized (LOCK) {
            jSONObject = new JSONObject(this.dependValues.toString());
        }
        return jSONObject;
    }

    public ImmutableJSONObject getSyncValues() {
        try {
            return new ImmutableJSONObject(getSyncValuesCopy());
        } catch (JSONException e) {
            e.printStackTrace();
            return new ImmutableJSONObject();
        }
    }

    public JSONObject getSyncValuesCopy() throws JSONException {
        JSONObject jSONObject;
        synchronized (LOCK) {
            jSONObject = new JSONObject(this.syncValues.toString());
        }
        return jSONObject;
    }

    public void setSyncValues(JSONObject jSONObject) {
        synchronized (LOCK) {
            this.syncValues = jSONObject;
        }
    }

    UserState(String str, boolean z) {
        this.persistKey = str;
        if (z) {
            loadState();
            return;
        }
        this.dependValues = new JSONObject();
        this.syncValues = new JSONObject();
    }

    /* access modifiers changed from: package-private */
    public UserState deepClone(String str) {
        UserState newInstance = newInstance(str);
        try {
            newInstance.dependValues = getDependValuesCopy();
            newInstance.syncValues = getSyncValuesCopy();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newInstance;
    }

    private Set<String> getGroupChangeFields(UserState userState) {
        try {
            if (this.dependValues.optLong("loc_time_stamp") == userState.dependValues.getLong("loc_time_stamp")) {
                return null;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("loc_bg", userState.dependValues.opt("loc_bg"));
            hashMap.put("loc_time_stamp", userState.dependValues.opt("loc_time_stamp"));
            putValues(userState.syncValues, hashMap);
            return LOCATION_FIELDS_SET;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void putOnSyncValues(String str, Object obj) throws JSONException {
        synchronized (LOCK) {
            this.syncValues.put(str, obj);
        }
    }

    /* access modifiers changed from: package-private */
    public void putOnDependValues(String str, Object obj) throws JSONException {
        synchronized (LOCK) {
            this.dependValues.put(str, obj);
        }
    }

    private void putValues(JSONObject jSONObject, HashMap<String, Object> hashMap) throws JSONException {
        synchronized (LOCK) {
            for (Map.Entry next : hashMap.entrySet()) {
                jSONObject.put((String) next.getKey(), next.getValue());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFromSyncValues(String str) {
        synchronized (LOCK) {
            this.syncValues.remove(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFromSyncValues(List<String> list) {
        synchronized (LOCK) {
            for (String remove : list) {
                this.syncValues.remove(remove);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFromDependValues(String str) {
        synchronized (LOCK) {
            this.dependValues.remove(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFromDependValues(List<String> list) {
        synchronized (LOCK) {
            for (String remove : list) {
                this.dependValues.remove(remove);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setLocation(LocationController.LocationPoint locationPoint) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("lat", locationPoint.lat);
            hashMap.put("long", locationPoint.log);
            hashMap.put("loc_acc", locationPoint.accuracy);
            hashMap.put("loc_type", locationPoint.type);
            putValues(this.syncValues, hashMap);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("loc_bg", locationPoint.f1167bg);
            hashMap2.put("loc_time_stamp", locationPoint.timeStamp);
            putValues(this.dependValues, hashMap2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void clearLocation() {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("lat", (Object) null);
            hashMap.put("long", (Object) null);
            hashMap.put("loc_acc", (Object) null);
            hashMap.put("loc_type", (Object) null);
            hashMap.put("loc_bg", (Object) null);
            hashMap.put("loc_time_stamp", (Object) null);
            putValues(this.syncValues, hashMap);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("loc_bg", (Object) null);
            hashMap2.put("loc_time_stamp", (Object) null);
            putValues(this.dependValues, hashMap2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiff(UserState userState, boolean z) {
        addDependFields();
        userState.addDependFields();
        JSONObject generateJsonDiff = generateJsonDiff(this.syncValues, userState.syncValues, (JSONObject) null, getGroupChangeFields(userState));
        if (!z && generateJsonDiff.toString().equals("{}")) {
            return null;
        }
        try {
            if (!generateJsonDiff.has("app_id")) {
                generateJsonDiff.put("app_id", this.syncValues.optString("app_id"));
            }
            if (this.syncValues.has("email_auth_hash")) {
                generateJsonDiff.put("email_auth_hash", this.syncValues.optString("email_auth_hash"));
            }
            if (this.syncValues.has("external_user_id_auth_hash")) {
                generateJsonDiff.put("external_user_id_auth_hash", this.syncValues.optString("external_user_id_auth_hash"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return generateJsonDiff;
    }

    private void loadState() {
        JSONObject jSONObject;
        int i;
        boolean z;
        String str = OneSignalPrefs.PREFS_ONESIGNAL;
        String string = OneSignalPrefs.getString(str, OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_DEPENDVALYES_ + this.persistKey, (String) null);
        if (string == null) {
            setDependValues(new JSONObject());
            try {
                int i2 = 1;
                if (this.persistKey.equals("CURRENT_STATE")) {
                    i = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_SUBSCRIPTION, 1);
                } else {
                    i = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_SYNCED_SUBSCRIPTION, 1);
                }
                if (i == -2) {
                    z = false;
                } else {
                    i2 = i;
                    z = true;
                }
                HashMap hashMap = new HashMap();
                hashMap.put("subscribableStatus", Integer.valueOf(i2));
                hashMap.put("userSubscribePref", Boolean.valueOf(z));
                putValues(this.dependValues, hashMap);
            } catch (JSONException unused) {
            }
        } else {
            try {
                setDependValues(new JSONObject(string));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String str2 = OneSignalPrefs.PREFS_ONESIGNAL;
        String string2 = OneSignalPrefs.getString(str2, OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_SYNCVALYES_ + this.persistKey, (String) null);
        if (string2 == null) {
            try {
                jSONObject = new JSONObject();
                jSONObject.put("identifier", OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_REGISTRATION_ID, (String) null));
            } catch (JSONException e2) {
                e2.printStackTrace();
                return;
            }
        } else {
            jSONObject = new JSONObject(string2);
        }
        setSyncValues(jSONObject);
    }

    /* access modifiers changed from: package-private */
    public void persistState() {
        synchronized (LOCK) {
            String str = OneSignalPrefs.PREFS_ONESIGNAL;
            OneSignalPrefs.saveString(str, OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_SYNCVALYES_ + this.persistKey, this.syncValues.toString());
            String str2 = OneSignalPrefs.PREFS_ONESIGNAL;
            OneSignalPrefs.saveString(str2, OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_DEPENDVALYES_ + this.persistKey, this.dependValues.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void persistStateAfterSync(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject != null) {
            JSONObject jSONObject3 = this.dependValues;
            generateJsonDiff(jSONObject3, jSONObject, jSONObject3, (Set<String>) null);
        }
        if (jSONObject2 != null) {
            JSONObject jSONObject4 = this.syncValues;
            generateJsonDiff(jSONObject4, jSONObject2, jSONObject4, (Set<String>) null);
            mergeTags(jSONObject2, (JSONObject) null);
        }
        if (jSONObject != null || jSONObject2 != null) {
            persistState();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:6|7|8|9) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0021 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mergeTags(org.json.JSONObject r6, org.json.JSONObject r7) {
        /*
            r5 = this;
            java.lang.String r0 = "tags"
            boolean r0 = r6.has(r0)
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            org.json.JSONObject r0 = r5.getSyncValuesCopy()     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r1 = "tags"
            boolean r1 = r0.has(r1)     // Catch:{ JSONException -> 0x0085 }
            if (r1 == 0) goto L_0x0027
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0021 }
            java.lang.String r2 = "tags"
            java.lang.String r0 = r0.optString(r2)     // Catch:{ JSONException -> 0x0021 }
            r1.<init>(r0)     // Catch:{ JSONException -> 0x0021 }
            goto L_0x002c
        L_0x0021:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0085 }
            r1.<init>()     // Catch:{ JSONException -> 0x0085 }
            goto L_0x002c
        L_0x0027:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0085 }
            r1.<init>()     // Catch:{ JSONException -> 0x0085 }
        L_0x002c:
            java.lang.String r0 = "tags"
            org.json.JSONObject r6 = r6.optJSONObject(r0)     // Catch:{ JSONException -> 0x0085 }
            java.util.Iterator r0 = r6.keys()     // Catch:{ JSONException -> 0x0085 }
        L_0x0036:
            boolean r2 = r0.hasNext()     // Catch:{ JSONException -> 0x0085 }
            if (r2 == 0) goto L_0x0062
            java.lang.Object r2 = r0.next()     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r3 = ""
            java.lang.String r4 = r6.optString(r2)     // Catch:{ JSONException -> 0x0085 }
            boolean r3 = r3.equals(r4)     // Catch:{ JSONException -> 0x0085 }
            if (r3 == 0) goto L_0x0052
            r1.remove(r2)     // Catch:{ JSONException -> 0x0085 }
            goto L_0x0036
        L_0x0052:
            if (r7 == 0) goto L_0x005a
            boolean r3 = r7.has(r2)     // Catch:{ JSONException -> 0x0085 }
            if (r3 != 0) goto L_0x0036
        L_0x005a:
            java.lang.String r3 = r6.optString(r2)     // Catch:{ JSONException -> 0x0085 }
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0085 }
            goto L_0x0036
        L_0x0062:
            java.lang.Object r6 = LOCK     // Catch:{ JSONException -> 0x0085 }
            monitor-enter(r6)     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r7 = r1.toString()     // Catch:{ all -> 0x0082 }
            java.lang.String r0 = "{}"
            boolean r7 = r7.equals(r0)     // Catch:{ all -> 0x0082 }
            if (r7 == 0) goto L_0x0079
            org.json.JSONObject r7 = r5.syncValues     // Catch:{ all -> 0x0082 }
            java.lang.String r0 = "tags"
            r7.remove(r0)     // Catch:{ all -> 0x0082 }
            goto L_0x0080
        L_0x0079:
            org.json.JSONObject r7 = r5.syncValues     // Catch:{ all -> 0x0082 }
            java.lang.String r0 = "tags"
            r7.put(r0, r1)     // Catch:{ all -> 0x0082 }
        L_0x0080:
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            goto L_0x0089
        L_0x0082:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0082 }
            throw r7     // Catch:{ JSONException -> 0x0085 }
        L_0x0085:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.UserState.mergeTags(org.json.JSONObject, org.json.JSONObject):void");
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiffFromIntoSyncValued(JSONObject jSONObject, Set<String> set) {
        JSONObject generateJsonDiff;
        synchronized (LOCK) {
            generateJsonDiff = JSONUtils.generateJsonDiff(this.syncValues, jSONObject, this.syncValues, set);
        }
        return generateJsonDiff;
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiffFromSyncValued(UserState userState, Set<String> set) {
        JSONObject generateJsonDiff;
        synchronized (LOCK) {
            generateJsonDiff = JSONUtils.generateJsonDiff(this.syncValues, userState.syncValues, (JSONObject) null, set);
        }
        return generateJsonDiff;
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiffFromIntoDependValues(JSONObject jSONObject, Set<String> set) {
        JSONObject generateJsonDiff;
        synchronized (LOCK) {
            generateJsonDiff = JSONUtils.generateJsonDiff(this.dependValues, jSONObject, this.dependValues, set);
        }
        return generateJsonDiff;
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiffFromDependValues(UserState userState, Set<String> set) {
        JSONObject generateJsonDiff;
        synchronized (LOCK) {
            generateJsonDiff = JSONUtils.generateJsonDiff(this.dependValues, userState.dependValues, (JSONObject) null, set);
        }
        return generateJsonDiff;
    }

    private static JSONObject generateJsonDiff(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, Set<String> set) {
        JSONObject generateJsonDiff;
        synchronized (LOCK) {
            generateJsonDiff = JSONUtils.generateJsonDiff(jSONObject, jSONObject2, jSONObject3, set);
        }
        return generateJsonDiff;
    }
}
