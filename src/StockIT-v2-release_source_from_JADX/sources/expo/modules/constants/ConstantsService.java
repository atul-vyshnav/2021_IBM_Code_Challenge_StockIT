package expo.modules.constants;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.amplitude.api.Constants;
import com.amplitude.api.DeviceInfo;
import com.facebook.device.yearclass.YearClass;
import com.google.android.exoplayer2.C1379C;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.interfaces.constants.ConstantsInterface;

public class ConstantsService implements InternalModule, ConstantsInterface {
    private static final String CONFIG_FILE_NAME = "app.config";
    private static final String TAG = ConstantsService.class.getSimpleName();
    protected Context mContext;
    private ExponentInstallationId mExponentInstallationId;
    private String mSessionId = UUID.randomUUID().toString();
    protected int mStatusBarHeight = 0;

    public String getAppOwnership() {
        return "guest";
    }

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public enum ExecutionEnvironment {
        BARE("bare"),
        STANDALONE("standalone"),
        STORE_CLIENT("storeClient");
        
        private final String mString;

        private ExecutionEnvironment(String str) {
            this.mString = str;
        }

        public String getString() {
            return this.mString;
        }
    }

    private static int convertPixelsToDp(float f, Context context) {
        return (int) (f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }

    public ConstantsService(Context context) {
        this.mContext = context;
        this.mExponentInstallationId = new ExponentInstallationId(this.mContext);
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", DeviceInfo.OS_NAME);
        if (identifier > 0) {
            this.mStatusBarHeight = convertPixelsToDp((float) context.getResources().getDimensionPixelSize(identifier), context);
        }
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(ConstantsInterface.class);
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("sessionId", this.mSessionId);
        hashMap.put("executionEnvironment", ExecutionEnvironment.BARE.getString());
        hashMap.put("statusBarHeight", Integer.valueOf(getStatusBarHeight()));
        hashMap.put("deviceYearClass", Integer.valueOf(getDeviceYearClass()));
        hashMap.put("deviceName", getDeviceName());
        hashMap.put("isDevice", Boolean.valueOf(getIsDevice()));
        hashMap.put("systemFonts", getSystemFonts());
        hashMap.put("systemVersion", getSystemVersion());
        hashMap.put("installationId", getOrCreateInstallationId());
        hashMap.put("manifest", getAppConfig());
        try {
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            hashMap.put("nativeAppVersion", packageInfo.versionName);
            hashMap.put("nativeBuildVersion", Integer.toString((int) getLongVersionCode(packageInfo)));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception: ", e);
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put(DeviceInfo.OS_NAME, new HashMap());
        hashMap.put(Constants.AMP_TRACKING_OPTION_PLATFORM, hashMap2);
        return hashMap;
    }

    public String getAppId() {
        return this.mContext.getPackageName();
    }

    public String getDeviceName() {
        return Build.MODEL;
    }

    public int getDeviceYearClass() {
        return YearClass.get(this.mContext);
    }

    public boolean getIsDevice() {
        return !isRunningOnGenymotion() && !isRunningOnStockEmulator();
    }

    public int getStatusBarHeight() {
        return this.mStatusBarHeight;
    }

    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getOrCreateInstallationId() {
        return this.mExponentInstallationId.getOrCreateUUID();
    }

    public List<String> getSystemFonts() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("normal");
        arrayList.add("notoserif");
        arrayList.add(C1379C.SANS_SERIF_NAME);
        arrayList.add("sans-serif-light");
        arrayList.add("sans-serif-thin");
        arrayList.add("sans-serif-condensed");
        arrayList.add("sans-serif-medium");
        arrayList.add(C1379C.SERIF_NAME);
        arrayList.add("Roboto");
        arrayList.add("monospace");
        return arrayList;
    }

    private static boolean isRunningOnGenymotion() {
        return Build.FINGERPRINT.contains("vbox");
    }

    private static boolean isRunningOnStockEmulator() {
        return Build.FINGERPRINT.contains("generic");
    }

    private static long getLongVersionCode(PackageInfo packageInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo.getLongVersionCode();
        }
        return (long) packageInfo.versionCode;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        if (r0 != null) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getAppConfig() {
        /*
            r3 = this;
            android.content.Context r0 = r3.mContext     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
            java.lang.String r1 = "app.config"
            java.io.InputStream r0 = r0.open(r1)     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.UTF_8     // Catch:{ all -> 0x0018 }
            java.lang.String r1 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r0, (java.nio.charset.Charset) r1)     // Catch:{ all -> 0x0018 }
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
        L_0x0017:
            return r1
        L_0x0018:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001a }
        L_0x001a:
            r2 = move-exception
            if (r0 == 0) goto L_0x0025
            r0.close()     // Catch:{ all -> 0x0021 }
            goto L_0x0025
        L_0x0021:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
        L_0x0025:
            throw r2     // Catch:{ FileNotFoundException -> 0x002e, Exception -> 0x0026 }
        L_0x0026:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r2 = "Error reading embedded app config"
            android.util.Log.e(r1, r2, r0)
        L_0x002e:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.constants.ConstantsService.getAppConfig():java.lang.String");
    }
}
