package expo.modules.updates;

import android.net.Uri;
import com.facebook.common.util.UriUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class UpdatesConfiguration {
    private static final String TAG = UpdatesConfiguration.class.getSimpleName();
    public static final String UPDATES_CONFIGURATION_CHECK_ON_LAUNCH_KEY = "checkOnLaunch";
    public static final String UPDATES_CONFIGURATION_ENABLED_KEY = "enabled";
    public static final String UPDATES_CONFIGURATION_HAS_EMBEDDED_UPDATE_KEY = "hasEmbeddedUpdate";
    private static final int UPDATES_CONFIGURATION_LAUNCH_WAIT_MS_DEFAULT_VALUE = 0;
    public static final String UPDATES_CONFIGURATION_LAUNCH_WAIT_MS_KEY = "launchWaitMs";
    private static final String UPDATES_CONFIGURATION_RELEASE_CHANNEL_DEFAULT_VALUE = "default";
    public static final String UPDATES_CONFIGURATION_RELEASE_CHANNEL_KEY = "releaseChannel";
    public static final String UPDATES_CONFIGURATION_REQUEST_HEADERS_KEY = "requestHeaders";
    public static final String UPDATES_CONFIGURATION_RUNTIME_VERSION_KEY = "runtimeVersion";
    public static final String UPDATES_CONFIGURATION_SCOPE_KEY_KEY = "scopeKey";
    public static final String UPDATES_CONFIGURATION_SDK_VERSION_KEY = "sdkVersion";
    public static final String UPDATES_CONFIGURATION_UPDATE_URL_KEY = "updateUrl";
    public static final String UPDATES_CONFIGURATION_USES_LEGACY_MANIFEST_KEY = "usesLegacyManifest";
    private CheckAutomaticallyConfiguration mCheckOnLaunch = CheckAutomaticallyConfiguration.ALWAYS;
    private boolean mExpectsSignedManifest = false;
    private boolean mHasEmbeddedUpdate = true;
    private boolean mIsEnabled;
    private int mLaunchWaitMs = 0;
    private String mReleaseChannel = UPDATES_CONFIGURATION_RELEASE_CHANNEL_DEFAULT_VALUE;
    private Map<String, String> mRequestHeaders = new HashMap();
    private String mRuntimeVersion;
    private String mScopeKey;
    private String mSdkVersion;
    private Uri mUpdateUrl;
    private boolean mUsesLegacyManifest = true;

    public enum CheckAutomaticallyConfiguration {
        NEVER,
        WIFI_ONLY,
        ALWAYS
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean expectsSignedManifest() {
        return this.mExpectsSignedManifest;
    }

    public String getScopeKey() {
        return this.mScopeKey;
    }

    public Uri getUpdateUrl() {
        return this.mUpdateUrl;
    }

    public Map<String, String> getRequestHeaders() {
        Map<String, String> map = this.mRequestHeaders;
        return map == null ? new HashMap() : map;
    }

    public String getReleaseChannel() {
        return this.mReleaseChannel;
    }

    public String getSdkVersion() {
        return this.mSdkVersion;
    }

    public String getRuntimeVersion() {
        return this.mRuntimeVersion;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.mSdkVersion;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isMissingRuntimeVersion() {
        /*
            r1 = this;
            java.lang.String r0 = r1.mRuntimeVersion
            if (r0 == 0) goto L_0x000a
            int r0 = r0.length()
            if (r0 != 0) goto L_0x0015
        L_0x000a:
            java.lang.String r0 = r1.mSdkVersion
            if (r0 == 0) goto L_0x0017
            int r0 = r0.length()
            if (r0 != 0) goto L_0x0015
            goto L_0x0017
        L_0x0015:
            r0 = 0
            goto L_0x0018
        L_0x0017:
            r0 = 1
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.UpdatesConfiguration.isMissingRuntimeVersion():boolean");
    }

    public CheckAutomaticallyConfiguration getCheckOnLaunch() {
        return this.mCheckOnLaunch;
    }

    public int getLaunchWaitMs() {
        return this.mLaunchWaitMs;
    }

    public boolean hasEmbeddedUpdate() {
        return this.mHasEmbeddedUpdate;
    }

    public boolean usesLegacyManifest() {
        return this.mUsesLegacyManifest;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0086 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public expo.modules.updates.UpdatesConfiguration loadValuesFromMetadata(android.content.Context r6) {
        /*
            r5 = this;
            android.content.pm.PackageManager r0 = r6.getPackageManager()     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ Exception -> 0x00b0 }
            r1 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r6 = r0.getApplicationInfo(r6, r1)     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r1 = "expo.modules.updates.EXPO_UPDATE_URL"
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x00b0 }
            r1 = 0
            if (r0 != 0) goto L_0x001b
            r0 = r1
            goto L_0x001f
        L_0x001b:
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch:{ Exception -> 0x00b0 }
        L_0x001f:
            r5.mUpdateUrl = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_SCOPE_KEY"
            java.lang.String r0 = r0.getString(r2)     // Catch:{ Exception -> 0x00b0 }
            r5.mScopeKey = r0     // Catch:{ Exception -> 0x00b0 }
            r5.maybeSetDefaultScopeKey()     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.ENABLED"
            r3 = 1
            boolean r0 = r0.getBoolean(r2, r3)     // Catch:{ Exception -> 0x00b0 }
            r5.mIsEnabled = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_SDK_VERSION"
            java.lang.String r0 = r0.getString(r2)     // Catch:{ Exception -> 0x00b0 }
            r5.mSdkVersion = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_RELEASE_CHANNEL"
            java.lang.String r4 = "default"
            java.lang.String r0 = r0.getString(r2, r4)     // Catch:{ Exception -> 0x00b0 }
            r5.mReleaseChannel = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_UPDATES_LAUNCH_WAIT_MS"
            r4 = 0
            int r0 = r0.getInt(r2, r4)     // Catch:{ Exception -> 0x00b0 }
            r5.mLaunchWaitMs = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_LEGACY_MANIFEST"
            boolean r0 = r0.getBoolean(r2, r3)     // Catch:{ Exception -> 0x00b0 }
            r5.mUsesLegacyManifest = r0     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r2 = "expo.modules.updates.EXPO_RUNTIME_VERSION"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Exception -> 0x00b0 }
            if (r0 != 0) goto L_0x006f
            goto L_0x0073
        L_0x006f:
            java.lang.String r1 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x00b0 }
        L_0x0073:
            r5.mRuntimeVersion = r1     // Catch:{ Exception -> 0x00b0 }
            android.os.Bundle r0 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r1 = "expo.modules.updates.EXPO_UPDATES_CHECK_ON_LAUNCH"
            java.lang.String r2 = "ALWAYS"
            java.lang.String r0 = r0.getString(r1, r2)     // Catch:{ Exception -> 0x00b0 }
            expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration r1 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.valueOf(r0)     // Catch:{ IllegalArgumentException -> 0x0086 }
            r5.mCheckOnLaunch = r1     // Catch:{ IllegalArgumentException -> 0x0086 }
            goto L_0x00a5
        L_0x0086:
            java.lang.String r1 = TAG     // Catch:{ Exception -> 0x00b0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b0 }
            r2.<init>()     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r4 = "Invalid value "
            r2.append(r4)     // Catch:{ Exception -> 0x00b0 }
            r2.append(r0)     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r0 = " for expo.modules.updates.EXPO_UPDATES_CHECK_ON_LAUNCH in AndroidManifest; defaulting to ALWAYS"
            r2.append(r0)     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r0 = r2.toString()     // Catch:{ Exception -> 0x00b0 }
            android.util.Log.e(r1, r0)     // Catch:{ Exception -> 0x00b0 }
            expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration r0 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.ALWAYS     // Catch:{ Exception -> 0x00b0 }
            r5.mCheckOnLaunch = r0     // Catch:{ Exception -> 0x00b0 }
        L_0x00a5:
            android.os.Bundle r6 = r6.metaData     // Catch:{ Exception -> 0x00b0 }
            java.lang.String r0 = "expo.modules.updates.HAS_EMBEDDED_UPDATE"
            boolean r6 = r6.getBoolean(r0, r3)     // Catch:{ Exception -> 0x00b0 }
            r5.mHasEmbeddedUpdate = r6     // Catch:{ Exception -> 0x00b0 }
            goto L_0x00b8
        L_0x00b0:
            r6 = move-exception
            java.lang.String r0 = TAG
            java.lang.String r1 = "Could not read expo-updates configuration data in AndroidManifest"
            android.util.Log.e(r0, r1, r6)
        L_0x00b8:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.UpdatesConfiguration.loadValuesFromMetadata(android.content.Context):expo.modules.updates.UpdatesConfiguration");
    }

    public UpdatesConfiguration loadValuesFromMap(Map<String, Object> map) {
        Boolean bool = (Boolean) readValueCheckingType(map, "enabled", Boolean.class);
        if (bool != null) {
            this.mIsEnabled = bool.booleanValue();
        }
        Boolean bool2 = (Boolean) readValueCheckingType(map, "expectsSignedManifest", Boolean.class);
        if (bool2 != null) {
            this.mExpectsSignedManifest = bool2.booleanValue();
        } else {
            this.mExpectsSignedManifest = false;
        }
        Uri uri = (Uri) readValueCheckingType(map, UPDATES_CONFIGURATION_UPDATE_URL_KEY, Uri.class);
        if (uri != null) {
            this.mUpdateUrl = uri;
        }
        String str = (String) readValueCheckingType(map, UPDATES_CONFIGURATION_SCOPE_KEY_KEY, String.class);
        if (str != null) {
            this.mScopeKey = str;
        }
        maybeSetDefaultScopeKey();
        Map<String, String> map2 = (Map) readValueCheckingType(map, UPDATES_CONFIGURATION_REQUEST_HEADERS_KEY, Map.class);
        if (map2 != null) {
            this.mRequestHeaders = map2;
        }
        String str2 = (String) readValueCheckingType(map, UPDATES_CONFIGURATION_RELEASE_CHANNEL_KEY, String.class);
        if (str2 != null) {
            this.mReleaseChannel = str2;
        }
        String str3 = (String) readValueCheckingType(map, UPDATES_CONFIGURATION_SDK_VERSION_KEY, String.class);
        if (str3 != null) {
            this.mSdkVersion = str3;
        }
        String str4 = (String) readValueCheckingType(map, UPDATES_CONFIGURATION_RUNTIME_VERSION_KEY, String.class);
        if (str4 != null) {
            this.mRuntimeVersion = str4;
        }
        String str5 = (String) readValueCheckingType(map, UPDATES_CONFIGURATION_CHECK_ON_LAUNCH_KEY, String.class);
        if (str5 != null) {
            try {
                this.mCheckOnLaunch = CheckAutomaticallyConfiguration.valueOf(str5);
            } catch (IllegalArgumentException unused) {
                throw new AssertionError("UpdatesConfiguration failed to initialize: invalid value " + str5 + " provided for checkOnLaunch");
            }
        }
        Integer num = (Integer) readValueCheckingType(map, UPDATES_CONFIGURATION_LAUNCH_WAIT_MS_KEY, Integer.class);
        if (num != null) {
            this.mLaunchWaitMs = num.intValue();
        }
        Boolean bool3 = (Boolean) readValueCheckingType(map, UPDATES_CONFIGURATION_HAS_EMBEDDED_UPDATE_KEY, Boolean.class);
        if (bool3 != null) {
            this.mHasEmbeddedUpdate = bool3.booleanValue();
        }
        Boolean bool4 = (Boolean) readValueCheckingType(map, UPDATES_CONFIGURATION_USES_LEGACY_MANIFEST_KEY, Boolean.class);
        if (bool4 != null) {
            this.mUsesLegacyManifest = bool4.booleanValue();
        }
        return this;
    }

    private <T> T readValueCheckingType(Map<String, Object> map, String str, Class<T> cls) {
        if (!map.containsKey(str)) {
            return null;
        }
        Object obj = map.get(str);
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        throw new AssertionError("UpdatesConfiguration failed to initialize: bad value of type " + obj.getClass().getSimpleName() + " provided for key " + str);
    }

    private void maybeSetDefaultScopeKey() {
        if (this.mScopeKey == null) {
            Uri uri = this.mUpdateUrl;
            if (uri != null) {
                this.mScopeKey = getNormalizedUrlOrigin(uri);
                return;
            }
            throw new AssertionError("expo-updates must be configured with a valid update URL or scope key.");
        }
    }

    static String getNormalizedUrlOrigin(Uri uri) {
        String scheme = uri.getScheme();
        int port = uri.getPort();
        if (port == getDefaultPortForScheme(scheme)) {
            port = -1;
        }
        if (port > -1) {
            return scheme + "://" + uri.getHost() + ParameterizedMessage.ERROR_MSG_SEPARATOR + port;
        }
        return scheme + "://" + uri.getHost();
    }

    private static int getDefaultPortForScheme(String str) {
        if (UriUtil.HTTP_SCHEME.equals(str) || "ws".equals(str)) {
            return 80;
        }
        if (UriUtil.HTTPS_SCHEME.equals(str) || "wss".equals(str)) {
            return 443;
        }
        return "ftp".equals(str) ? 21 : -1;
    }
}
