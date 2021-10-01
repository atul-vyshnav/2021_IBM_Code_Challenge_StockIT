package com.amplitude.api;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.amplitude.api.Utils */
public class C0772Utils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String TAG = "com.amplitude.api.Utils";
    private static AmplitudeLog logger = AmplitudeLog.getLogger();

    static JSONObject cloneJSONObject(JSONObject jSONObject) {
        JSONArray jSONArray;
        if (jSONObject == null) {
            return null;
        }
        if (jSONObject.length() == 0) {
            return new JSONObject();
        }
        try {
            jSONArray = jSONObject.names();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.mo10606e(TAG, e.toString());
            jSONArray = null;
        }
        int length = jSONArray != null ? jSONArray.length() : 0;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.optString(i);
        }
        try {
            return new JSONObject(jSONObject, strArr);
        } catch (JSONException e2) {
            logger.mo10606e(TAG, e2.toString());
            return null;
        }
    }

    static boolean compareJSONObjects(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == jSONObject2) {
            return true;
        }
        if ((jSONObject != null && jSONObject2 == null) || (jSONObject == null && jSONObject2 != null)) {
            return false;
        }
        try {
            if (jSONObject.length() != jSONObject2.length()) {
                return false;
            }
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (!jSONObject2.has(next)) {
                    return false;
                }
                Object obj = jSONObject.get(next);
                Object obj2 = jSONObject2.get(next);
                if (!obj.getClass().equals(obj2.getClass())) {
                    return false;
                }
                if (obj.getClass() == JSONObject.class) {
                    if (!compareJSONObjects((JSONObject) obj, (JSONObject) obj2)) {
                        return false;
                    }
                } else if (!obj.equals(obj2)) {
                    return false;
                }
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    static String normalizeInstanceName(String str) {
        if (isEmptyString(str)) {
            str = Constants.DEFAULT_INSTANCE;
        }
        return str.toLowerCase();
    }

    static SharedPreferences getAmplitudeSharedPreferences(Context context, String str) {
        return context.getSharedPreferences("com.amplitude.api." + str + "." + context.getPackageName(), 4);
    }

    static void writeStringToSharedPreferences(Context context, String str, String str2, String str3) {
        SharedPreferences.Editor edit = getAmplitudeSharedPreferences(context, str).edit();
        edit.putString(str2, str3);
        edit.apply();
    }

    static String getStringFromSharedPreferences(Context context, String str, String str2) {
        return getAmplitudeSharedPreferences(context, str).getString(str2, (String) null);
    }
}
