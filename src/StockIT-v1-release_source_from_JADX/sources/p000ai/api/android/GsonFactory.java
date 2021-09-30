package p000ai.api.android;

import com.google.gson.Gson;

/* renamed from: ai.api.android.GsonFactory */
public class GsonFactory {
    public static Gson getGson() {
        return p000ai.api.GsonFactory.getDefaultFactory().getGson();
    }
}
