package p000ai.api.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import p000ai.api.android.GsonFactory;

/* renamed from: ai.api.util.VersionConfig */
public class VersionConfig {
    private static final Pattern DOT_PATTERN = Pattern.compile(".", 16);
    private static final String TAG = VersionConfig.class.getName();
    private static final Map<String, VersionConfig> configuration;
    private boolean autoStopRecognizer = false;
    private boolean destroyRecognizer = true;

    static {
        HashMap hashMap = new HashMap();
        configuration = hashMap;
        hashMap.put("5.9.26", new VersionConfig(true, true));
        configuration.put("4.7.13", new VersionConfig(false, false));
    }

    private VersionConfig() {
    }

    private VersionConfig(boolean z, boolean z2) {
        this.destroyRecognizer = z;
        this.autoStopRecognizer = z2;
    }

    public static VersionConfig init(Context context) {
        return getConfigByVersion(context);
    }

    private static VersionConfig getConfigByVersion(Context context) {
        long numberFromBuildVersion = numberFromBuildVersion(RecognizerChecker.getGoogleRecognizerVersion(context));
        VersionConfig versionConfig = new VersionConfig();
        long j = 0;
        for (Map.Entry next : configuration.entrySet()) {
            String str = (String) next.getKey();
            if (!TextUtils.isEmpty(str)) {
                long numberFromBuildVersion2 = numberFromBuildVersion(str);
                if (numberFromBuildVersion >= numberFromBuildVersion2 && j < numberFromBuildVersion2) {
                    versionConfig.destroyRecognizer = ((VersionConfig) next.getValue()).destroyRecognizer;
                    versionConfig.autoStopRecognizer = ((VersionConfig) next.getValue()).autoStopRecognizer;
                    j = numberFromBuildVersion2;
                }
            }
        }
        return versionConfig;
    }

    public boolean isDestroyRecognizer() {
        return this.destroyRecognizer;
    }

    public boolean isAutoStopRecognizer() {
        return this.autoStopRecognizer;
    }

    private static long numberFromBuildVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String[] split = DOT_PATTERN.split(str);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(3, split.length); i++) {
            sb.append(split[i]);
        }
        try {
            return Long.parseLong(sb.toString());
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public String toString() {
        return GsonFactory.getGson().toJson((Object) this);
    }
}
