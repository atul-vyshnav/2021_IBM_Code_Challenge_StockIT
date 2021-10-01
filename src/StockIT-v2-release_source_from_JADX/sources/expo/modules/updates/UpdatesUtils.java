package expo.modules.updates;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import expo.modules.updates.p020db.entity.AssetEntity;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import kotlin.UByte;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.time.TimeZones;

public class UpdatesUtils {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final String TAG = UpdatesUtils.class.getSimpleName();
    private static final String UPDATES_DIRECTORY_NAME = ".expo-internal";
    private static final String UPDATES_EVENT_NAME = "Expo.nativeUpdatesEvent";

    public static File getOrCreateUpdatesDirectory(Context context) throws Exception {
        File file = new File(context.getFilesDir(), UPDATES_DIRECTORY_NAME);
        if (file.exists()) {
            if (file.isFile()) {
                throw new Exception("File already exists at the location of the Updates Directory: " + file.toString() + " ; aborting");
            }
        } else if (!file.mkdir()) {
            throw new Exception("Failed to create Updates Directory: mkdir() returned false");
        }
        return file;
    }

    public static String sha256(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            byte[] bytes = str.getBytes("UTF-8");
            instance.update(bytes, 0, bytes.length);
            return bytesToHex(instance.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Log.e(TAG, "Failed to checksum string via SHA-256", e);
            throw e;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0026, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0036, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] sha256(java.io.File r4) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ NoSuchAlgorithmException -> 0x0039, IOException -> 0x0037 }
            r0.<init>(r4)     // Catch:{ NoSuchAlgorithmException -> 0x0039, IOException -> 0x0037 }
            java.security.DigestInputStream r1 = new java.security.DigestInputStream     // Catch:{ all -> 0x002b }
            java.lang.String r2 = "SHA-256"
            java.security.MessageDigest r2 = java.security.MessageDigest.getInstance(r2)     // Catch:{ all -> 0x002b }
            r1.<init>(r0, r2)     // Catch:{ all -> 0x002b }
            java.security.MessageDigest r2 = r1.getMessageDigest()     // Catch:{ all -> 0x001f }
            byte[] r2 = r2.digest()     // Catch:{ all -> 0x001f }
            r1.close()     // Catch:{ all -> 0x002b }
            r0.close()     // Catch:{ NoSuchAlgorithmException -> 0x0039, IOException -> 0x0037 }
            return r2
        L_0x001f:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x0026 }
            goto L_0x002a
        L_0x0026:
            r1 = move-exception
            r2.addSuppressed(r1)     // Catch:{ all -> 0x002b }
        L_0x002a:
            throw r3     // Catch:{ all -> 0x002b }
        L_0x002b:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002d }
        L_0x002d:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0032 }
            goto L_0x0036
        L_0x0032:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0039, IOException -> 0x0037 }
        L_0x0036:
            throw r2     // Catch:{ NoSuchAlgorithmException -> 0x0039, IOException -> 0x0037 }
        L_0x0037:
            r0 = move-exception
            goto L_0x003a
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to checksum file via SHA-256: "
            r2.append(r3)
            java.lang.String r4 = r4.toString()
            r2.append(r4)
            java.lang.String r4 = r2.toString()
            android.util.Log.e(r1, r4, r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.UpdatesUtils.sha256(java.io.File):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0057, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        r3.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0060, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] sha256AndWriteToFile(java.io.InputStream r3, java.io.File r4) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            java.security.DigestInputStream r0 = new java.security.DigestInputStream
            java.lang.String r1 = "SHA-256"
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)
            r0.<init>(r3, r1)
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x0055 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0055 }
            r1.<init>()     // Catch:{ all -> 0x0055 }
            java.lang.String r2 = r4.getAbsolutePath()     // Catch:{ all -> 0x0055 }
            r1.append(r2)     // Catch:{ all -> 0x0055 }
            java.lang.String r2 = ".tmp"
            r1.append(r2)     // Catch:{ all -> 0x0055 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0055 }
            r3.<init>(r1)     // Catch:{ all -> 0x0055 }
            org.apache.commons.p029io.FileUtils.copyInputStreamToFile(r0, r3)     // Catch:{ all -> 0x0055 }
            boolean r3 = r3.renameTo(r4)     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x003a
            java.security.MessageDigest r3 = r0.getMessageDigest()     // Catch:{ all -> 0x0055 }
            byte[] r3 = r3.digest()     // Catch:{ all -> 0x0055 }
            r0.close()
            return r3
        L_0x003a:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x0055 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0055 }
            r1.<init>()     // Catch:{ all -> 0x0055 }
            java.lang.String r2 = "File download was successful, but failed to move from temporary to permanent location "
            r1.append(r2)     // Catch:{ all -> 0x0055 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x0055 }
            r1.append(r4)     // Catch:{ all -> 0x0055 }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x0055 }
            r3.<init>(r4)     // Catch:{ all -> 0x0055 }
            throw r3     // Catch:{ all -> 0x0055 }
        L_0x0055:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r4 = move-exception
            r0.close()     // Catch:{ all -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r0 = move-exception
            r3.addSuppressed(r0)
        L_0x0060:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.UpdatesUtils.sha256AndWriteToFile(java.io.InputStream, java.io.File):byte[]");
    }

    public static String createFilenameForAsset(AssetEntity assetEntity) {
        if (assetEntity.key != null) {
            return assetEntity.key;
        }
        return "asset-" + new Date().getTime() + "-" + new Random().nextInt();
    }

    public static void sendEventToReactNative(WeakReference<ReactNativeHost> weakReference, String str, WritableMap writableMap) {
        if (weakReference == null || weakReference.get() == null) {
            String str2 = TAG;
            Log.e(str2, "Could not emit " + str + " event; UpdatesController was not initialized with an instance of ReactApplication.");
            return;
        }
        AsyncTask.execute(new Runnable(writableMap, str) {
            public final /* synthetic */ WritableMap f$1;
            public final /* synthetic */ String f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                UpdatesUtils.lambda$sendEventToReactNative$0(ReactInstanceManager.this, this.f$1, this.f$2);
            }
        });
    }

    static /* synthetic */ void lambda$sendEventToReactNative$0(ReactInstanceManager reactInstanceManager, WritableMap writableMap, String str) {
        DeviceEventManagerModule.RCTDeviceEventEmitter rCTDeviceEventEmitter;
        ReactContext reactContext = null;
        int i = 0;
        while (true) {
            if (i >= 5) {
                break;
            }
            try {
                reactContext = reactInstanceManager.getCurrentReactContext();
                if (reactContext != null) {
                    break;
                }
                Thread.sleep(1000);
                i++;
            } catch (Exception unused) {
                String str2 = TAG;
                Log.e(str2, "Could not emit " + str + " event; no react context was found.");
                return;
            }
        }
        if (reactContext == null || (rCTDeviceEventEmitter = (DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)) == null) {
            String str3 = TAG;
            Log.e(str3, "Could not emit " + str + " event; no event emitter was found.");
            return;
        }
        if (writableMap == null) {
            writableMap = Arguments.createMap();
        }
        writableMap.putString("type", str);
        rCTDeviceEventEmitter.emit(UPDATES_EVENT_NAME, writableMap);
    }

    public static boolean shouldCheckForUpdateOnLaunch(UpdatesConfiguration updatesConfiguration, Context context) {
        if (updatesConfiguration.getUpdateUrl() == null) {
            return false;
        }
        int i = C30681.f1250xda9ff90c[updatesConfiguration.getCheckOnLaunch().ordinal()];
        if (i == 1) {
            return false;
        }
        if (i != 2) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            return !connectivityManager.isActiveNetworkMetered();
        }
        Log.e(TAG, "Could not determine active network connection is metered; not checking for updates");
        return false;
    }

    /* renamed from: expo.modules.updates.UpdatesUtils$1 */
    static /* synthetic */ class C30681 {

        /* renamed from: $SwitchMap$expo$modules$updates$UpdatesConfiguration$CheckAutomaticallyConfiguration */
        static final /* synthetic */ int[] f1250xda9ff90c;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration[] r0 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1250xda9ff90c = r0
                expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration r1 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.NEVER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f1250xda9ff90c     // Catch:{ NoSuchFieldError -> 0x001d }
                expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration r1 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.WIFI_ONLY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f1250xda9ff90c     // Catch:{ NoSuchFieldError -> 0x0028 }
                expo.modules.updates.UpdatesConfiguration$CheckAutomaticallyConfiguration r1 = expo.modules.updates.UpdatesConfiguration.CheckAutomaticallyConfiguration.ALWAYS     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.UpdatesUtils.C30681.<clinit>():void");
        }
    }

    public static String getRuntimeVersion(UpdatesConfiguration updatesConfiguration) {
        String runtimeVersion = updatesConfiguration.getRuntimeVersion();
        String sdkVersion = updatesConfiguration.getSdkVersion();
        if (runtimeVersion == null || runtimeVersion.length() <= 0) {
            return (sdkVersion == null || sdkVersion.length() <= 0) ? "1" : sdkVersion;
        }
        return runtimeVersion;
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & UByte.MAX_VALUE;
            int i2 = i * 2;
            char[] cArr2 = HEX_ARRAY;
            cArr[i2] = cArr2[b >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public static Date parseDateString(String str) throws ParseException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US).parse(str);
        } catch (IllegalArgumentException | ParseException e) {
            String str2 = TAG;
            Log.e(str2, "Failed to parse date string on first try: " + str, e);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
            return simpleDateFormat.parse(str);
        }
    }
}
