package p000ai.api.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.UUID;

/* renamed from: ai.api.android.SessionIdStorage */
public abstract class SessionIdStorage {
    private static final String PREF_NAME = "APIAI_preferences";
    private static final String SESSION_ID = "sessionId";

    public static synchronized String getSessionId(Context context) {
        synchronized (SessionIdStorage.class) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
            String string = sharedPreferences.getString(SESSION_ID, "");
            if (!TextUtils.isEmpty(string)) {
                return string;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            String uuid = UUID.randomUUID().toString();
            edit.putString(SESSION_ID, uuid);
            edit.commit();
            return uuid;
        }
    }

    public static synchronized void resetSessionId(Context context) {
        synchronized (SessionIdStorage.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME, 0).edit();
            edit.putString(SESSION_ID, "");
            edit.commit();
        }
    }
}
