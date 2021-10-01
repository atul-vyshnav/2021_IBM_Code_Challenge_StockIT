package expo.modules.filesystem;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.facebook.common.util.UriUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.CookieHandler;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.C3401Response;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.p029io.FileUtils;
import org.apache.commons.p029io.IOUtils;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityEventListener;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.filesystem.FilePermissionModuleInterface;
import org.unimodules.interfaces.filesystem.Permission;
import org.unimodules.interfaces.permissions.PermissionsResponse;

public class FileSystemModule extends ExportedModule implements ActivityEventListener {
    private static final int DIR_PERMISSIONS_REQUEST_CODE = 5394;
    private static final String EXDownloadProgressEventName = "expo-file-system.downloadProgress";
    private static final String HEADER_KEY = "headers";
    private static final long MIN_EVENT_DT_MS = 100;
    private static final String NAME = "ExponentFileSystem";
    /* access modifiers changed from: private */
    public static final String TAG = FileSystemModule.class.getSimpleName();
    private OkHttpClient mClient;
    private Promise mDirPermissionsRequest;
    private final Map<String, DownloadResumable> mDownloadResumableMap = new HashMap();
    /* access modifiers changed from: private */
    public ModuleRegistry mModuleRegistry;

    interface ProgressListener {
        void update(long j, long j2, boolean z);
    }

    public String getName() {
        return NAME;
    }

    public void onNewIntent(Intent intent) {
    }

    private enum UploadType {
        INVALID(-1),
        BINARY_CONTENT(0),
        MULTIPART(1);
        
        private int value;

        private UploadType(int i) {
            this.value = i;
        }

        public static UploadType fromInt(int i) {
            for (UploadType uploadType : values()) {
                if (i == uploadType.value) {
                    return uploadType;
                }
            }
            return INVALID;
        }
    }

    public FileSystemModule(Context context) {
        super(context);
        try {
            ensureDirExists(getContext().getFilesDir());
            ensureDirExists(getContext().getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("documentDirectory", Uri.fromFile(getContext().getFilesDir()).toString() + "/");
        hashMap.put("cacheDirectory", Uri.fromFile(getContext().getCacheDir()).toString() + "/");
        hashMap.put("bundleDirectory", "asset:///");
        return hashMap;
    }

    /* access modifiers changed from: private */
    public File uriToFile(Uri uri) {
        return new File(uri.getPath());
    }

    private void checkIfFileExists(Uri uri) throws IOException {
        File uriToFile = uriToFile(uri);
        if (!uriToFile.exists()) {
            throw new IOException("Directory for " + uriToFile.getPath() + " doesn't exist.");
        }
    }

    private void checkIfFileDirExists(Uri uri) throws IOException {
        File uriToFile = uriToFile(uri);
        if (!uriToFile.getParentFile().exists()) {
            throw new IOException("Directory for " + uriToFile.getPath() + " doesn't exist. Please make sure directory '" + uriToFile.getParent() + "' exists before calling downloadAsync.");
        }
    }

    private EnumSet<Permission> permissionsForPath(String str) {
        return ((FilePermissionModuleInterface) this.mModuleRegistry.getModule(FilePermissionModuleInterface.class)).getPathPermissions(getContext(), str);
    }

    private EnumSet<Permission> permissionsForUri(Uri uri) {
        if (isSAFUri(uri)) {
            return permissionsForSAFUri(uri);
        }
        if (UriUtil.LOCAL_CONTENT_SCHEME.equals(uri.getScheme())) {
            return EnumSet.of(Permission.READ);
        }
        if (UriUtil.LOCAL_ASSET_SCHEME.equals(uri.getScheme())) {
            return EnumSet.of(Permission.READ);
        }
        if (UriUtil.LOCAL_FILE_SCHEME.equals(uri.getScheme())) {
            return permissionsForPath(uri.getPath());
        }
        if (uri.getScheme() == null) {
            return EnumSet.of(Permission.READ);
        }
        return EnumSet.noneOf(Permission.class);
    }

    private EnumSet<Permission> permissionsForSAFUri(Uri uri) {
        DocumentFile nearestSAFFile = getNearestSAFFile(uri);
        EnumSet<Permission> noneOf = EnumSet.noneOf(Permission.class);
        if (nearestSAFFile.canRead()) {
            noneOf.add(Permission.READ);
        }
        if (nearestSAFFile.canWrite()) {
            noneOf.add(Permission.WRITE);
        }
        return noneOf;
    }

    private void ensurePermission(Uri uri, Permission permission, String str) throws IOException {
        if (!permissionsForUri(uri).contains(permission)) {
            throw new IOException(str);
        }
    }

    private void ensurePermission(Uri uri, Permission permission) throws IOException {
        if (permission.equals(Permission.READ)) {
            ensurePermission(uri, permission, "Location '" + uri + "' isn't readable.");
        }
        if (permission.equals(Permission.WRITE)) {
            ensurePermission(uri, permission, "Location '" + uri + "' isn't writable.");
        }
        ensurePermission(uri, permission, "Location '" + uri + "' doesn't have permission '" + permission.name() + "'.");
    }

    private InputStream openAssetInputStream(Uri uri) throws IOException {
        return getContext().getAssets().open(uri.getPath().substring(1));
    }

    private InputStream openResourceInputStream(String str) throws IOException {
        int identifier = getContext().getResources().getIdentifier(str, "raw", getContext().getPackageName());
        if (identifier != 0 || (identifier = getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName())) != 0) {
            return getContext().getResources().openRawResource(identifier);
        }
        throw new FileNotFoundException("No resource found with the name " + str);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:44|45|52) */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r2.putBoolean("exists", false);
        r2.putBoolean("isDirectory", false);
        r15.resolve(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x0133 */
    @org.unimodules.core.interfaces.ExpoMethod
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getInfoAsync(java.lang.String r13, java.util.Map<java.lang.String, java.lang.Object> r14, org.unimodules.core.Promise r15) {
        /*
            r12 = this;
            java.lang.String r0 = "content"
            android.net.Uri r1 = android.net.Uri.parse(r13)     // Catch:{ Exception -> 0x013d }
            org.unimodules.interfaces.filesystem.Permission r2 = org.unimodules.interfaces.filesystem.Permission.READ     // Catch:{ Exception -> 0x013d }
            r12.ensurePermission(r1, r2)     // Catch:{ Exception -> 0x013d }
            java.lang.String r2 = "file"
            java.lang.String r3 = r1.getScheme()     // Catch:{ Exception -> 0x013d }
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x013d }
            java.lang.String r3 = "size"
            java.lang.String r4 = "uri"
            r5 = 1
            java.lang.String r6 = "isDirectory"
            java.lang.String r7 = "exists"
            r8 = 0
            java.lang.String r9 = "md5"
            if (r2 == 0) goto L_0x0089
            java.io.File r13 = r12.uriToFile(r1)     // Catch:{ Exception -> 0x013d }
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ Exception -> 0x013d }
            r0.<init>()     // Catch:{ Exception -> 0x013d }
            boolean r1 = r13.exists()     // Catch:{ Exception -> 0x013d }
            if (r1 == 0) goto L_0x007e
            r0.putBoolean(r7, r5)     // Catch:{ Exception -> 0x013d }
            boolean r1 = r13.isDirectory()     // Catch:{ Exception -> 0x013d }
            r0.putBoolean(r6, r1)     // Catch:{ Exception -> 0x013d }
            android.net.Uri r1 = android.net.Uri.fromFile(r13)     // Catch:{ Exception -> 0x013d }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x013d }
            r0.putString(r4, r1)     // Catch:{ Exception -> 0x013d }
            boolean r1 = r14.containsKey(r9)     // Catch:{ Exception -> 0x013d }
            if (r1 == 0) goto L_0x0060
            java.lang.Object r14 = r14.get(r9)     // Catch:{ Exception -> 0x013d }
            java.lang.Boolean r14 = (java.lang.Boolean) r14     // Catch:{ Exception -> 0x013d }
            boolean r14 = r14.booleanValue()     // Catch:{ Exception -> 0x013d }
            if (r14 == 0) goto L_0x0060
            java.lang.String r14 = r12.md5(r13)     // Catch:{ Exception -> 0x013d }
            r0.putString(r9, r14)     // Catch:{ Exception -> 0x013d }
        L_0x0060:
            long r1 = r12.getFileSize(r13)     // Catch:{ Exception -> 0x013d }
            double r1 = (double) r1     // Catch:{ Exception -> 0x013d }
            r0.putDouble(r3, r1)     // Catch:{ Exception -> 0x013d }
            java.lang.String r14 = "modificationTime"
            r1 = 4562254508917369340(0x3f50624dd2f1a9fc, double:0.001)
            long r3 = r13.lastModified()     // Catch:{ Exception -> 0x013d }
            double r3 = (double) r3     // Catch:{ Exception -> 0x013d }
            double r3 = r3 * r1
            r0.putDouble(r14, r3)     // Catch:{ Exception -> 0x013d }
            r15.resolve(r0)     // Catch:{ Exception -> 0x013d }
            goto L_0x014a
        L_0x007e:
            r0.putBoolean(r7, r8)     // Catch:{ Exception -> 0x013d }
            r0.putBoolean(r6, r8)     // Catch:{ Exception -> 0x013d }
            r15.resolve(r0)     // Catch:{ Exception -> 0x013d }
            goto L_0x014a
        L_0x0089:
            java.lang.String r2 = r1.getScheme()     // Catch:{ Exception -> 0x013d }
            boolean r2 = r0.equals(r2)     // Catch:{ Exception -> 0x013d }
            java.lang.String r10 = "asset"
            if (r2 != 0) goto L_0x00c2
            java.lang.String r2 = r1.getScheme()     // Catch:{ Exception -> 0x013d }
            boolean r2 = r10.equals(r2)     // Catch:{ Exception -> 0x013d }
            if (r2 != 0) goto L_0x00c2
            java.lang.String r2 = r1.getScheme()     // Catch:{ Exception -> 0x013d }
            if (r2 != 0) goto L_0x00a6
            goto L_0x00c2
        L_0x00a6:
            java.io.IOException r13 = new java.io.IOException     // Catch:{ Exception -> 0x013d }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x013d }
            r14.<init>()     // Catch:{ Exception -> 0x013d }
            java.lang.String r0 = "Unsupported scheme for location '"
            r14.append(r0)     // Catch:{ Exception -> 0x013d }
            r14.append(r1)     // Catch:{ Exception -> 0x013d }
            java.lang.String r0 = "'."
            r14.append(r0)     // Catch:{ Exception -> 0x013d }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x013d }
            r13.<init>(r14)     // Catch:{ Exception -> 0x013d }
            throw r13     // Catch:{ Exception -> 0x013d }
        L_0x00c2:
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ Exception -> 0x013d }
            r2.<init>()     // Catch:{ Exception -> 0x013d }
            java.lang.String r11 = r1.getScheme()     // Catch:{ FileNotFoundException -> 0x0133 }
            boolean r0 = r0.equals(r11)     // Catch:{ FileNotFoundException -> 0x0133 }
            if (r0 == 0) goto L_0x00de
            android.content.Context r13 = r12.getContext()     // Catch:{ FileNotFoundException -> 0x0133 }
            android.content.ContentResolver r13 = r13.getContentResolver()     // Catch:{ FileNotFoundException -> 0x0133 }
            java.io.InputStream r13 = r13.openInputStream(r1)     // Catch:{ FileNotFoundException -> 0x0133 }
            goto L_0x00f1
        L_0x00de:
            java.lang.String r0 = r1.getScheme()     // Catch:{ FileNotFoundException -> 0x0133 }
            boolean r0 = r10.equals(r0)     // Catch:{ FileNotFoundException -> 0x0133 }
            if (r0 == 0) goto L_0x00ed
            java.io.InputStream r13 = r12.openAssetInputStream(r1)     // Catch:{ FileNotFoundException -> 0x0133 }
            goto L_0x00f1
        L_0x00ed:
            java.io.InputStream r13 = r12.openResourceInputStream(r13)     // Catch:{ FileNotFoundException -> 0x0133 }
        L_0x00f1:
            if (r13 == 0) goto L_0x012d
            r2.putBoolean(r7, r5)     // Catch:{ FileNotFoundException -> 0x0133 }
            r2.putBoolean(r6, r8)     // Catch:{ FileNotFoundException -> 0x0133 }
            java.lang.String r0 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0133 }
            r2.putString(r4, r0)     // Catch:{ FileNotFoundException -> 0x0133 }
            int r0 = r13.available()     // Catch:{ FileNotFoundException -> 0x0133 }
            double r0 = (double) r0     // Catch:{ FileNotFoundException -> 0x0133 }
            r2.putDouble(r3, r0)     // Catch:{ FileNotFoundException -> 0x0133 }
            boolean r0 = r14.containsKey(r9)     // Catch:{ FileNotFoundException -> 0x0133 }
            if (r0 == 0) goto L_0x0129
            java.lang.Object r14 = r14.get(r9)     // Catch:{ FileNotFoundException -> 0x0133 }
            java.lang.Boolean r14 = (java.lang.Boolean) r14     // Catch:{ FileNotFoundException -> 0x0133 }
            boolean r14 = r14.booleanValue()     // Catch:{ FileNotFoundException -> 0x0133 }
            if (r14 == 0) goto L_0x0129
            byte[] r13 = org.apache.commons.codec.digest.DigestUtils.md5((java.io.InputStream) r13)     // Catch:{ FileNotFoundException -> 0x0133 }
            char[] r13 = org.apache.commons.codec.binary.Hex.encodeHex(r13)     // Catch:{ FileNotFoundException -> 0x0133 }
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ FileNotFoundException -> 0x0133 }
            r2.putString(r9, r13)     // Catch:{ FileNotFoundException -> 0x0133 }
        L_0x0129:
            r15.resolve(r2)     // Catch:{ FileNotFoundException -> 0x0133 }
            goto L_0x014a
        L_0x012d:
            java.io.FileNotFoundException r13 = new java.io.FileNotFoundException     // Catch:{ FileNotFoundException -> 0x0133 }
            r13.<init>()     // Catch:{ FileNotFoundException -> 0x0133 }
            throw r13     // Catch:{ FileNotFoundException -> 0x0133 }
        L_0x0133:
            r2.putBoolean(r7, r8)     // Catch:{ Exception -> 0x013d }
            r2.putBoolean(r6, r8)     // Catch:{ Exception -> 0x013d }
            r15.resolve(r2)     // Catch:{ Exception -> 0x013d }
            goto L_0x014a
        L_0x013d:
            r13 = move-exception
            java.lang.String r14 = TAG
            java.lang.String r0 = r13.getMessage()
            android.util.Log.e(r14, r0)
            r15.reject(r13)
        L_0x014a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.filesystem.FileSystemModule.getInfoAsync(java.lang.String, java.util.Map, org.unimodules.core.Promise):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0077, code lost:
        if (r7 != null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0081, code lost:
        throw r0;
     */
    @org.unimodules.core.interfaces.ExpoMethod
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readAsStringAsync(java.lang.String r7, java.util.Map<java.lang.String, java.lang.Object> r8, org.unimodules.core.Promise r9) {
        /*
            r6 = this;
            java.lang.String r0 = "position"
            java.lang.String r1 = "length"
            java.lang.String r2 = "encoding"
            android.net.Uri r3 = android.net.Uri.parse(r7)     // Catch:{ Exception -> 0x00f6 }
            org.unimodules.interfaces.filesystem.Permission r4 = org.unimodules.interfaces.filesystem.Permission.READ     // Catch:{ Exception -> 0x00f6 }
            r6.ensurePermission(r3, r4)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r4 = "utf8"
            boolean r5 = r8.containsKey(r2)     // Catch:{ Exception -> 0x00f6 }
            if (r5 == 0) goto L_0x0029
            java.lang.Object r5 = r8.get(r2)     // Catch:{ Exception -> 0x00f6 }
            boolean r5 = r5 instanceof java.lang.String     // Catch:{ Exception -> 0x00f6 }
            if (r5 == 0) goto L_0x0029
            java.lang.Object r2 = r8.get(r2)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r4 = r2.toLowerCase()     // Catch:{ Exception -> 0x00f6 }
        L_0x0029:
            java.lang.String r2 = "base64"
            boolean r2 = r4.equalsIgnoreCase(r2)     // Catch:{ Exception -> 0x00f6 }
            if (r2 == 0) goto L_0x0082
            java.io.InputStream r7 = r6.getInputStream(r3)     // Catch:{ Exception -> 0x00f6 }
            boolean r2 = r8.containsKey(r1)     // Catch:{ all -> 0x0074 }
            r3 = 2
            if (r2 == 0) goto L_0x0066
            boolean r2 = r8.containsKey(r0)     // Catch:{ all -> 0x0074 }
            if (r2 == 0) goto L_0x0066
            java.lang.Object r1 = r8.get(r1)     // Catch:{ all -> 0x0074 }
            java.lang.Number r1 = (java.lang.Number) r1     // Catch:{ all -> 0x0074 }
            int r1 = r1.intValue()     // Catch:{ all -> 0x0074 }
            java.lang.Object r8 = r8.get(r0)     // Catch:{ all -> 0x0074 }
            java.lang.Number r8 = (java.lang.Number) r8     // Catch:{ all -> 0x0074 }
            int r8 = r8.intValue()     // Catch:{ all -> 0x0074 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x0074 }
            long r4 = (long) r8     // Catch:{ all -> 0x0074 }
            r7.skip(r4)     // Catch:{ all -> 0x0074 }
            r8 = 0
            int r1 = r7.read(r0, r8, r1)     // Catch:{ all -> 0x0074 }
            java.lang.String r8 = android.util.Base64.encodeToString(r0, r8, r1, r3)     // Catch:{ all -> 0x0074 }
            goto L_0x006e
        L_0x0066:
            byte[] r8 = getInputStreamBytes(r7)     // Catch:{ all -> 0x0074 }
            java.lang.String r8 = android.util.Base64.encodeToString(r8, r3)     // Catch:{ all -> 0x0074 }
        L_0x006e:
            if (r7 == 0) goto L_0x00d6
            r7.close()     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00d6
        L_0x0074:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r0 = move-exception
            if (r7 == 0) goto L_0x0081
            r7.close()     // Catch:{ all -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r7 = move-exception
            r8.addSuppressed(r7)     // Catch:{ Exception -> 0x00f6 }
        L_0x0081:
            throw r0     // Catch:{ Exception -> 0x00f6 }
        L_0x0082:
            java.lang.String r8 = "file"
            java.lang.String r0 = r3.getScheme()     // Catch:{ Exception -> 0x00f6 }
            boolean r8 = r8.equals(r0)     // Catch:{ Exception -> 0x00f6 }
            if (r8 == 0) goto L_0x009c
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00f6 }
            java.io.File r8 = r6.uriToFile(r3)     // Catch:{ Exception -> 0x00f6 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r8 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r7)     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00d6
        L_0x009c:
            java.lang.String r8 = "asset"
            java.lang.String r0 = r3.getScheme()     // Catch:{ Exception -> 0x00f6 }
            boolean r8 = r8.equals(r0)     // Catch:{ Exception -> 0x00f6 }
            if (r8 == 0) goto L_0x00b1
            java.io.InputStream r7 = r6.openAssetInputStream(r3)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r8 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r7)     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00d6
        L_0x00b1:
            java.lang.String r8 = r3.getScheme()     // Catch:{ Exception -> 0x00f6 }
            if (r8 != 0) goto L_0x00c0
            java.io.InputStream r7 = r6.openResourceInputStream(r7)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r8 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r7)     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00d6
        L_0x00c0:
            boolean r7 = isSAFUri(r3)     // Catch:{ Exception -> 0x00f6 }
            if (r7 == 0) goto L_0x00da
            android.content.Context r7 = r6.getContext()     // Catch:{ Exception -> 0x00f6 }
            android.content.ContentResolver r7 = r7.getContentResolver()     // Catch:{ Exception -> 0x00f6 }
            java.io.InputStream r7 = r7.openInputStream(r3)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r8 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r7)     // Catch:{ Exception -> 0x00f6 }
        L_0x00d6:
            r9.resolve(r8)     // Catch:{ Exception -> 0x00f6 }
            goto L_0x0103
        L_0x00da:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ Exception -> 0x00f6 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f6 }
            r8.<init>()     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r0 = "Unsupported scheme for location '"
            r8.append(r0)     // Catch:{ Exception -> 0x00f6 }
            r8.append(r3)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r0 = "'."
            r8.append(r0)     // Catch:{ Exception -> 0x00f6 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00f6 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x00f6 }
            throw r7     // Catch:{ Exception -> 0x00f6 }
        L_0x00f6:
            r7 = move-exception
            java.lang.String r8 = TAG
            java.lang.String r0 = r7.getMessage()
            android.util.Log.e(r8, r0)
            r9.reject(r7)
        L_0x0103:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.filesystem.FileSystemModule.readAsStringAsync(java.lang.String, java.util.Map, org.unimodules.core.Promise):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0051, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r5.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005a, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005d, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x005e, code lost:
        if (r4 != null) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0068, code lost:
        throw r6;
     */
    @org.unimodules.core.interfaces.ExpoMethod
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeAsStringAsync(java.lang.String r4, java.lang.String r5, java.util.Map<java.lang.String, java.lang.Object> r6, org.unimodules.core.Promise r7) {
        /*
            r3 = this;
            java.lang.String r0 = "encoding"
            android.net.Uri r4 = android.net.Uri.parse(r4)     // Catch:{ Exception -> 0x0069 }
            org.unimodules.interfaces.filesystem.Permission r1 = org.unimodules.interfaces.filesystem.Permission.WRITE     // Catch:{ Exception -> 0x0069 }
            r3.ensurePermission(r4, r1)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r1 = "utf8"
            boolean r2 = r6.containsKey(r0)     // Catch:{ Exception -> 0x0069 }
            if (r2 == 0) goto L_0x0025
            java.lang.Object r2 = r6.get(r0)     // Catch:{ Exception -> 0x0069 }
            boolean r2 = r2 instanceof java.lang.String     // Catch:{ Exception -> 0x0069 }
            if (r2 == 0) goto L_0x0025
            java.lang.Object r6 = r6.get(r0)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0069 }
            java.lang.String r1 = r6.toLowerCase()     // Catch:{ Exception -> 0x0069 }
        L_0x0025:
            java.io.OutputStream r4 = r3.getOutputStream(r4)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r6 = "base64"
            boolean r6 = r1.equals(r6)     // Catch:{ all -> 0x005b }
            if (r6 == 0) goto L_0x003a
            r6 = 0
            byte[] r5 = android.util.Base64.decode(r5, r6)     // Catch:{ all -> 0x005b }
            r4.write(r5)     // Catch:{ all -> 0x005b }
            goto L_0x0045
        L_0x003a:
            java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x005b }
            r6.<init>(r4)     // Catch:{ all -> 0x005b }
            r6.write(r5)     // Catch:{ all -> 0x004f }
            r6.close()     // Catch:{ all -> 0x005b }
        L_0x0045:
            if (r4 == 0) goto L_0x004a
            r4.close()     // Catch:{ Exception -> 0x0069 }
        L_0x004a:
            r4 = 0
            r7.resolve(r4)     // Catch:{ Exception -> 0x0069 }
            goto L_0x0076
        L_0x004f:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0051 }
        L_0x0051:
            r0 = move-exception
            r6.close()     // Catch:{ all -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ all -> 0x005b }
        L_0x005a:
            throw r0     // Catch:{ all -> 0x005b }
        L_0x005b:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x005d }
        L_0x005d:
            r6 = move-exception
            if (r4 == 0) goto L_0x0068
            r4.close()     // Catch:{ all -> 0x0064 }
            goto L_0x0068
        L_0x0064:
            r4 = move-exception
            r5.addSuppressed(r4)     // Catch:{ Exception -> 0x0069 }
        L_0x0068:
            throw r6     // Catch:{ Exception -> 0x0069 }
        L_0x0069:
            r4 = move-exception
            java.lang.String r5 = TAG
            java.lang.String r6 = r4.getMessage()
            android.util.Log.e(r5, r6)
            r7.reject(r4)
        L_0x0076:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.filesystem.FileSystemModule.writeAsStringAsync(java.lang.String, java.lang.String, java.util.Map, org.unimodules.core.Promise):void");
    }

    @ExpoMethod
    public void deleteAsync(String str, Map<String, Object> map, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            Uri withAppendedPath = Uri.withAppendedPath(parse, "..");
            Permission permission = Permission.WRITE;
            ensurePermission(withAppendedPath, permission, "Location '" + parse + "' isn't deletable.");
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                File uriToFile = uriToFile(parse);
                if (uriToFile.exists()) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        FileUtils.forceDelete(uriToFile);
                    } else {
                        forceDelete(uriToFile);
                    }
                    promise.resolve((Object) null);
                } else if (!map.containsKey("idempotent") || !((Boolean) map.get("idempotent")).booleanValue()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_FIND_FILE", "File '" + parse + "' could not be deleted because it could not be found");
                } else {
                    promise.resolve((Object) null);
                }
            } else if (isSAFUri(parse)) {
                DocumentFile nearestSAFFile = getNearestSAFFile(parse);
                if (nearestSAFFile.exists()) {
                    nearestSAFFile.delete();
                    promise.resolve((Object) null);
                } else if (!map.containsKey("idempotent") || !((Boolean) map.get("idempotent")).booleanValue()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_FIND_FILE", "File '" + parse + "' could not be deleted because it could not be found");
                } else {
                    promise.resolve((Object) null);
                }
            } else {
                throw new IOException("Unsupported scheme for location '" + parse + "'.");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void moveAsync(Map<String, Object> map, Promise promise) {
        try {
            if (!map.containsKey("from")) {
                promise.reject("ERR_FILESYSTEM_MISSING_PARAMETER", "`FileSystem.moveAsync` needs a `from` path.");
                return;
            }
            Uri parse = Uri.parse((String) map.get("from"));
            Uri withAppendedPath = Uri.withAppendedPath(parse, "..");
            Permission permission = Permission.WRITE;
            ensurePermission(withAppendedPath, permission, "Location '" + parse + "' isn't movable.");
            if (!map.containsKey("to")) {
                promise.reject("ERR_FILESYSTEM_MISSING_PARAMETER", "`FileSystem.moveAsync` needs a `to` path.");
                return;
            }
            Uri parse2 = Uri.parse((String) map.get("to"));
            ensurePermission(parse2, Permission.WRITE);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                if (uriToFile(parse).renameTo(uriToFile(parse2))) {
                    promise.resolve((Object) null);
                    return;
                }
                promise.reject("ERR_FILESYSTEM_CANNOT_MOVE_FILE", "File '" + parse + "' could not be moved to '" + parse2 + "'");
            } else if (isSAFUri(parse)) {
                DocumentFile nearestSAFFile = getNearestSAFFile(parse);
                if (!nearestSAFFile.exists()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_MOVE_FILE", "File '" + parse + "' could not be moved to '" + parse2 + "'");
                    return;
                }
                transformFilesFromSAF(nearestSAFFile, new File(parse2.getPath()), false);
                promise.resolve((Object) null);
            } else {
                throw new IOException("Unsupported scheme for location '" + parse + "'.");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void copyAsync(Map<String, Object> map, Promise promise) {
        try {
            if (!map.containsKey("from")) {
                promise.reject("ERR_FILESYSTEM_MISSING_PARAMETER", "`FileSystem.moveAsync` needs a `from` path.");
                return;
            }
            Uri parse = Uri.parse((String) map.get("from"));
            ensurePermission(parse, Permission.READ);
            if (!map.containsKey("to")) {
                promise.reject("ERR_FILESYSTEM_MISSING_PARAMETER", "`FileSystem.moveAsync` needs a `to` path.");
                return;
            }
            Uri parse2 = Uri.parse((String) map.get("to"));
            ensurePermission(parse2, Permission.WRITE);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                File uriToFile = uriToFile(parse);
                File uriToFile2 = uriToFile(parse2);
                if (uriToFile.isDirectory()) {
                    FileUtils.copyDirectory(uriToFile, uriToFile2);
                } else {
                    FileUtils.copyFile(uriToFile, uriToFile2);
                }
                promise.resolve((Object) null);
            } else if (isSAFUri(parse)) {
                DocumentFile nearestSAFFile = getNearestSAFFile(parse);
                if (!nearestSAFFile.exists()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_FIND_FILE", "File '" + parse + "' could not be copied because it could not be found");
                    return;
                }
                transformFilesFromSAF(nearestSAFFile, new File(parse2.getPath()), true);
                promise.resolve((Object) null);
            } else if (UriUtil.LOCAL_CONTENT_SCHEME.equals(parse.getScheme())) {
                IOUtils.copy(getContext().getContentResolver().openInputStream(parse), (OutputStream) new FileOutputStream(uriToFile(parse2)));
                promise.resolve((Object) null);
            } else if (UriUtil.LOCAL_ASSET_SCHEME.equals(parse.getScheme())) {
                IOUtils.copy(openAssetInputStream(parse), (OutputStream) new FileOutputStream(uriToFile(parse2)));
                promise.resolve((Object) null);
            } else if (parse.getScheme() == null) {
                IOUtils.copy(openResourceInputStream((String) map.get("from")), (OutputStream) new FileOutputStream(uriToFile(parse2)));
                promise.resolve((Object) null);
            } else {
                throw new IOException("Unsupported scheme for location '" + parse + "'.");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0084, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0089, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r7.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x008d, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0090, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0091, code lost:
        if (r8 != null) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0097, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0098, code lost:
        r7.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x009b, code lost:
        throw r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void transformFilesFromSAF(androidx.documentfile.provider.DocumentFile r7, java.io.File r8, boolean r9) throws java.io.IOException {
        /*
            r6 = this;
            boolean r0 = r7.exists()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            boolean r0 = r8.exists()
            if (r0 != 0) goto L_0x001c
            boolean r0 = r8.mkdirs()
            if (r0 == 0) goto L_0x0014
            goto L_0x001c
        L_0x0014:
            java.io.IOException r7 = new java.io.IOException
            java.lang.String r8 = "Couldn't create folder in output dir."
            r7.<init>(r8)
            throw r7
        L_0x001c:
            boolean r0 = r7.isDirectory()
            if (r0 == 0) goto L_0x0048
            androidx.documentfile.provider.DocumentFile[] r0 = r7.listFiles()
            int r1 = r0.length
            r2 = 0
        L_0x0028:
            if (r2 >= r1) goto L_0x0042
            r3 = r0[r2]
            java.lang.String r4 = r7.getName()
            if (r4 != 0) goto L_0x0033
            goto L_0x003f
        L_0x0033:
            java.io.File r4 = new java.io.File
            java.lang.String r5 = r7.getName()
            r4.<init>(r8, r5)
            r6.transformFilesFromSAF(r3, r4, r9)
        L_0x003f:
            int r2 = r2 + 1
            goto L_0x0028
        L_0x0042:
            if (r9 != 0) goto L_0x0047
            r7.delete()
        L_0x0047:
            return
        L_0x0048:
            java.lang.String r0 = r7.getName()
            if (r0 != 0) goto L_0x004f
            return
        L_0x004f:
            java.io.File r0 = new java.io.File
            java.lang.String r8 = r8.getPath()
            java.lang.String r1 = r7.getName()
            r0.<init>(r8, r1)
            android.content.Context r8 = r6.getContext()
            android.content.ContentResolver r8 = r8.getContentResolver()
            android.net.Uri r1 = r7.getUri()
            java.io.InputStream r8 = r8.openInputStream(r1)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x008e }
            r1.<init>(r0)     // Catch:{ all -> 0x008e }
            org.apache.commons.p029io.IOUtils.copy((java.io.InputStream) r8, (java.io.OutputStream) r1)     // Catch:{ all -> 0x0082 }
            r1.close()     // Catch:{ all -> 0x008e }
            if (r8 == 0) goto L_0x007c
            r8.close()
        L_0x007c:
            if (r9 != 0) goto L_0x0081
            r7.delete()
        L_0x0081:
            return
        L_0x0082:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0084 }
        L_0x0084:
            r9 = move-exception
            r1.close()     // Catch:{ all -> 0x0089 }
            goto L_0x008d
        L_0x0089:
            r0 = move-exception
            r7.addSuppressed(r0)     // Catch:{ all -> 0x008e }
        L_0x008d:
            throw r9     // Catch:{ all -> 0x008e }
        L_0x008e:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0090 }
        L_0x0090:
            r9 = move-exception
            if (r8 == 0) goto L_0x009b
            r8.close()     // Catch:{ all -> 0x0097 }
            goto L_0x009b
        L_0x0097:
            r8 = move-exception
            r7.addSuppressed(r8)
        L_0x009b:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.filesystem.FileSystemModule.transformFilesFromSAF(androidx.documentfile.provider.DocumentFile, java.io.File, boolean):void");
    }

    @ExpoMethod
    public void makeDirectoryAsync(String str, Map<String, Object> map, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.WRITE);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                File uriToFile = uriToFile(parse);
                boolean isDirectory = uriToFile.isDirectory();
                boolean z = map.containsKey("intermediates") && ((Boolean) map.get("intermediates")).booleanValue();
                if (!(z ? uriToFile.mkdirs() : uriToFile.mkdir())) {
                    if (!z || !isDirectory) {
                        promise.reject("ERR_FILESYSTEM_CANNOT_CREATE_DIRECTORY", "Directory '" + parse + "' could not be created or already exists.");
                        return;
                    }
                }
                promise.resolve((Object) null);
                return;
            }
            throw new IOException("Unsupported scheme for location '" + parse + "'.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void readDirectoryAsync(String str, Map<String, Object> map, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.READ);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                File[] listFiles = uriToFile(parse).listFiles();
                if (listFiles != null) {
                    ArrayList arrayList = new ArrayList();
                    for (File name : listFiles) {
                        arrayList.add(name.getName());
                    }
                    promise.resolve(arrayList);
                    return;
                }
                promise.reject("ERR_FILESYSTEM_CANNOT_READ_DIRECTORY", "Directory '" + parse + "' could not be read.");
            } else if (isSAFUri(parse)) {
                promise.reject("ERR_FILESYSTEM_UNSUPPORTED_SCHEME", "Can't read Storage Access Framework directory, use StorageAccessFramework.readDirectoryAsync() instead.");
            } else {
                throw new IOException("Unsupported scheme for location '" + parse + "'.");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void uploadAsync(String str, String str2, Map<String, Object> map, final Promise promise) {
        String str3;
        try {
            Uri parse = Uri.parse(str2);
            ensurePermission(parse, Permission.READ);
            checkIfFileExists(parse);
            MediaType mediaType = null;
            if (!map.containsKey("httpMethod")) {
                promise.reject("ERR_FILESYSTEM_MISSING_HTTP_METHOD", "Missing HTTP method.", (Throwable) null);
                return;
            }
            String str4 = (String) map.get("httpMethod");
            if (!map.containsKey("uploadType")) {
                promise.reject("ERR_FILESYSTEM_MISSING_UPLOAD_TYPE", "Missing upload type.", (Throwable) null);
                return;
            }
            UploadType fromInt = UploadType.fromInt(((Double) map.get("uploadType")).intValue());
            Request.Builder url = new Request.Builder().url(str);
            if (map.containsKey(HEADER_KEY)) {
                Map map2 = (Map) map.get(HEADER_KEY);
                for (String str5 : map2.keySet()) {
                    url.addHeader(str5, map2.get(str5).toString());
                }
            }
            File uriToFile = uriToFile(parse);
            if (fromInt == UploadType.BINARY_CONTENT) {
                url.method(str4, RequestBody.create((MediaType) null, uriToFile));
            } else if (fromInt == UploadType.MULTIPART) {
                MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (map.containsKey("parameters")) {
                    Map map3 = (Map) map.get("parameters");
                    for (String str6 : map3.keySet()) {
                        type.addFormDataPart(str6, String.valueOf(map3.get(str6)));
                    }
                }
                if (map.containsKey("mimeType")) {
                    str3 = (String) map.get("mimeType");
                } else {
                    str3 = URLConnection.guessContentTypeFromName(uriToFile.getName());
                }
                String name = uriToFile.getName();
                if (map.containsKey("fieldName")) {
                    name = (String) map.get("fieldName");
                }
                String name2 = uriToFile.getName();
                if (str3 != null) {
                    mediaType = MediaType.parse(str3);
                }
                type.addFormDataPart(name, name2, RequestBody.create(mediaType, uriToFile));
                url.method(str4, type.build());
            } else {
                promise.reject("ERR_FILESYSTEM_INVALID_UPLOAD_TYPE", String.format("Invalid upload type: %s.", new Object[]{map.get("uploadType")}), (Throwable) null);
                return;
            }
            getOkHttpClient().newCall(url.build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    Log.e(FileSystemModule.TAG, String.valueOf(iOException.getMessage()));
                    promise.reject(iOException);
                }

                public void onResponse(Call call, C3401Response response) {
                    Bundle bundle = new Bundle();
                    try {
                        if (response.body() != null) {
                            bundle.putString(TtmlNode.TAG_BODY, response.body().string());
                        } else {
                            bundle.putString(TtmlNode.TAG_BODY, (String) null);
                        }
                        bundle.putInt("status", response.code());
                        bundle.putBundle(FileSystemModule.HEADER_KEY, FileSystemModule.translateHeaders(response.headers()));
                        response.close();
                        promise.resolve(bundle);
                    } catch (IOException e) {
                        promise.reject(e);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void downloadAsync(String str, String str2, final Map<String, Object> map, final Promise promise) {
        try {
            final Uri parse = Uri.parse(str2);
            ensurePermission(parse, Permission.WRITE);
            checkIfFileDirExists(parse);
            if (!str.contains(ParameterizedMessage.ERROR_MSG_SEPARATOR)) {
                Context context = getContext();
                BufferedSource buffer = Okio.buffer(Okio.source(context.getResources().openRawResource(context.getResources().getIdentifier(str, "raw", context.getPackageName()))));
                File uriToFile = uriToFile(parse);
                uriToFile.delete();
                BufferedSink buffer2 = Okio.buffer(Okio.sink(uriToFile));
                buffer2.writeAll(buffer);
                buffer2.close();
                Bundle bundle = new Bundle();
                bundle.putString("uri", Uri.fromFile(uriToFile).toString());
                if (map != null && map.containsKey("md5") && ((Boolean) map.get("md5")).booleanValue()) {
                    bundle.putString("md5", md5(uriToFile));
                }
                promise.resolve(bundle);
            } else if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                Request.Builder url = new Request.Builder().url(str);
                if (map != null && map.containsKey(HEADER_KEY)) {
                    try {
                        Map map2 = (Map) map.get(HEADER_KEY);
                        for (String str3 : map2.keySet()) {
                            url.addHeader(str3, (String) map2.get(str3));
                        }
                    } catch (ClassCastException e) {
                        promise.reject("ERR_FILESYSTEM_INVALID_HEADERS", "Invalid headers dictionary. Keys and values should be strings.", e);
                        return;
                    }
                }
                getOkHttpClient().newCall(url.build()).enqueue(new Callback() {
                    public void onFailure(Call call, IOException iOException) {
                        Log.e(FileSystemModule.TAG, String.valueOf(iOException.getMessage()));
                        promise.reject(iOException);
                    }

                    public void onResponse(Call call, C3401Response response) throws IOException {
                        File access$200 = FileSystemModule.this.uriToFile(parse);
                        access$200.delete();
                        BufferedSink buffer = Okio.buffer(Okio.sink(access$200));
                        buffer.writeAll(response.body().source());
                        buffer.close();
                        Bundle bundle = new Bundle();
                        bundle.putString("uri", Uri.fromFile(access$200).toString());
                        Map map = map;
                        if (map != null && map.containsKey("md5") && ((Boolean) map.get("md5")).booleanValue()) {
                            bundle.putString("md5", FileSystemModule.this.md5(access$200));
                        }
                        bundle.putInt("status", response.code());
                        bundle.putBundle(FileSystemModule.HEADER_KEY, FileSystemModule.translateHeaders(response.headers()));
                        response.close();
                        promise.resolve(bundle);
                    }
                });
            } else {
                throw new IOException("Unsupported scheme for location '" + parse + "'.");
            }
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage());
            promise.reject(e2);
        }
    }

    @ExpoMethod
    public void getTotalDiskCapacityAsync(Promise promise) {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            promise.resolve(Double.valueOf(Math.min(BigInteger.valueOf(statFs.getBlockCountLong()).multiply(BigInteger.valueOf(statFs.getBlockSizeLong())).doubleValue(), Math.pow(2.0d, 53.0d) - 1.0d)));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject("ERR_FILESYSTEM_CANNOT_DETERMINE_DISK_CAPACITY", "Unable to access total disk capacity", e);
        }
    }

    @ExpoMethod
    public void getFreeDiskStorageAsync(Promise promise) {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            promise.resolve(Double.valueOf(Math.min(BigInteger.valueOf(statFs.getAvailableBlocksLong()).multiply(BigInteger.valueOf(statFs.getBlockSizeLong())).doubleValue(), Math.pow(2.0d, 53.0d) - 1.0d)));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject("ERR_FILESYSTEM_CANNOT_DETERMINE_DISK_CAPACITY", "Unable to determine free disk storage capacity", e);
        }
    }

    @ExpoMethod
    public void getContentUriAsync(String str, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.WRITE);
            ensurePermission(parse, Permission.READ);
            checkIfFileDirExists(parse);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                promise.resolve(contentUriFromFile(uriToFile(parse)).toString());
                return;
            }
            promise.reject("ERR_FILESYSTEM_CANNOT_READ_DIRECTORY", "No readable files with the uri: " + str + ". Please use other uri.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    private Uri contentUriFromFile(File file) {
        try {
            Application application = ((ActivityProvider) this.mModuleRegistry.getModule(ActivityProvider.class)).getCurrentActivity().getApplication();
            return FileSystemFileProvider.getUriForFile(application, application.getPackageName() + ".FileSystemFileProvider", file);
        } catch (Exception e) {
            throw e;
        }
    }

    @ExpoMethod
    public void downloadResumableStartAsync(String str, String str2, final String str3, Map<String, Object> map, final String str4, Promise promise) {
        try {
            Uri parse = Uri.parse(str2);
            checkIfFileDirExists(parse);
            if (UriUtil.LOCAL_FILE_SCHEME.equals(parse.getScheme())) {
                final boolean z = str4 != null;
                final C29833 r3 = new ProgressListener() {
                    long mLastUpdate = -1;

                    public void update(long j, long j2, boolean z) {
                        EventEmitter eventEmitter = (EventEmitter) FileSystemModule.this.mModuleRegistry.getModule(EventEmitter.class);
                        if (eventEmitter != null) {
                            Bundle bundle = new Bundle();
                            Bundle bundle2 = new Bundle();
                            if (z) {
                                j += Long.parseLong(str4);
                            }
                            if (z) {
                                j2 += Long.parseLong(str4);
                            }
                            long currentTimeMillis = System.currentTimeMillis();
                            if (currentTimeMillis > this.mLastUpdate + FileSystemModule.MIN_EVENT_DT_MS || j == j2) {
                                this.mLastUpdate = currentTimeMillis;
                                bundle2.putDouble("totalBytesWritten", (double) j);
                                bundle2.putDouble("totalBytesExpectedToWrite", (double) j2);
                                bundle.putString("uuid", str3);
                                bundle.putBundle("data", bundle2);
                                eventEmitter.emit(FileSystemModule.EXDownloadProgressEventName, bundle);
                            }
                        }
                    }
                };
                OkHttpClient build = getOkHttpClient().newBuilder().addNetworkInterceptor(new Interceptor() {
                    public C3401Response intercept(Interceptor.Chain chain) throws IOException {
                        C3401Response proceed = chain.proceed(chain.request());
                        return proceed.newBuilder().body(new ProgressResponseBody(proceed.body(), r3)).build();
                    }
                }).build();
                Request.Builder builder = new Request.Builder();
                if (z) {
                    builder.addHeader("Range", "bytes=" + str4 + "-");
                }
                if (map != null && map.containsKey(HEADER_KEY)) {
                    Map map2 = (Map) map.get(HEADER_KEY);
                    for (String str5 : map2.keySet()) {
                        builder.addHeader(str5, map2.get(str5).toString());
                    }
                }
                Call newCall = build.newCall(builder.url(str).build());
                this.mDownloadResumableMap.put(str3, new DownloadResumable(str3, str, parse, newCall));
                new DownloadResumableTask().execute(new DownloadResumableTaskParams[]{new DownloadResumableTaskParams(map, newCall, uriToFile(parse), z, promise)});
                return;
            }
            throw new IOException("Unsupported scheme for location '" + parse + "'.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void downloadResumablePauseAsync(String str, Promise promise) {
        DownloadResumable downloadResumable = this.mDownloadResumableMap.get(str);
        if (downloadResumable != null) {
            downloadResumable.call.cancel();
            this.mDownloadResumableMap.remove(downloadResumable.uuid);
            try {
                File uriToFile = uriToFile(downloadResumable.fileUri);
                Bundle bundle = new Bundle();
                bundle.putString("resumeData", String.valueOf(uriToFile.length()));
                promise.resolve(bundle);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                promise.reject(e);
            }
        } else {
            IOException iOException = new IOException("No download object available");
            Log.e(TAG, iOException.getMessage());
            promise.reject(iOException);
        }
    }

    @ExpoMethod
    public void readSAFDirectoryAsync(String str, Map<String, Object> map, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.READ);
            if (isSAFUri(parse)) {
                DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getContext(), parse);
                if (fromTreeUri != null && fromTreeUri.exists()) {
                    if (fromTreeUri.isDirectory()) {
                        DocumentFile[] listFiles = fromTreeUri.listFiles();
                        ArrayList arrayList = new ArrayList();
                        for (DocumentFile uri : listFiles) {
                            arrayList.add(uri.getUri().toString());
                        }
                        promise.resolve(arrayList);
                        return;
                    }
                }
                promise.reject("ERR_FILESYSTEM_CANNOT_READ_DIRECTORY", "Uri '" + parse + "' doesn't exist or isn't a directory.");
                return;
            }
            throw new IOException("The URI '" + parse + "' is not a Storage Access Framework URI. Try using FileSystem.readDirectoryAsync instead.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void makeSAFDirectoryAsync(String str, String str2, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.WRITE);
            if (isSAFUri(parse)) {
                DocumentFile nearestSAFFile = getNearestSAFFile(parse);
                if (!nearestSAFFile.isDirectory()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_CREATE_DIRECTORY", "Provided uri '" + parse + "' is not pointing to a directory.");
                    return;
                }
                DocumentFile createDirectory = nearestSAFFile.createDirectory(str2);
                if (createDirectory == null) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_CREATE_DIRECTORY", "Unknown error.");
                } else {
                    promise.resolve(createDirectory.getUri().toString());
                }
            } else {
                throw new IOException("The URI '" + parse + "' is not a Storage Access Framework URI. Try using FileSystem.makeDirectoryAsync instead.");
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void createSAFFileAsync(String str, String str2, String str3, Promise promise) {
        try {
            Uri parse = Uri.parse(str);
            ensurePermission(parse, Permission.WRITE);
            if (isSAFUri(parse)) {
                DocumentFile nearestSAFFile = getNearestSAFFile(parse);
                if (!nearestSAFFile.isDirectory()) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_CREATE_FILE", "Provided uri '" + parse + "' is not pointing to a directory.");
                    return;
                }
                DocumentFile createFile = nearestSAFFile.createFile(str3, str2);
                if (createFile == null) {
                    promise.reject("ERR_FILESYSTEM_CANNOT_CREATE_FILE", "Unknown error.");
                } else {
                    promise.resolve(createFile.getUri().toString());
                }
            } else {
                throw new IOException("The URI '" + parse + "' is not a Storage Access Framework URI.");
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ExpoMethod
    public void requestDirectoryPermissionsAsync(String str, Promise promise) {
        Uri uri;
        if (this.mDirPermissionsRequest != null) {
            promise.reject("ERR_FILESYSTEM_CANNOT_ASK_FOR_PERMISSIONS", "You have an unfinished permission request.");
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            if (Build.VERSION.SDK_INT >= 26) {
                if (str == null) {
                    uri = null;
                } else {
                    uri = Uri.parse(str);
                }
                if (uri != null) {
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                }
            }
            Activity currentActivity = ((ActivityProvider) this.mModuleRegistry.getModule(ActivityProvider.class)).getCurrentActivity();
            if (currentActivity == null) {
                promise.reject("ERR_FILESYSTEM_CANNOT_ASK_FOR_PERMISSIONS", "Can't find activity.");
                return;
            }
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).registerActivityEventListener(this);
            this.mDirPermissionsRequest = promise;
            currentActivity.startActivityForResult(intent, DIR_PERMISSIONS_REQUEST_CODE);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            promise.reject("ERR_FILESYSTEM_CANNOT_ASK_FOR_PERMISSIONS", "Can't ask for permissions.", e);
        }
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (i == DIR_PERMISSIONS_REQUEST_CODE && this.mDirPermissionsRequest != null) {
            Bundle bundle = new Bundle();
            if (i2 == -1) {
                Uri data = intent.getData();
                activity.getContentResolver().takePersistableUriPermission(data, intent.getFlags() & 3);
                bundle.putBoolean(PermissionsResponse.GRANTED_KEY, true);
                bundle.putString("directoryUri", data.toString());
            } else {
                bundle.putBoolean(PermissionsResponse.GRANTED_KEY, false);
            }
            this.mDirPermissionsRequest.resolve(bundle);
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).unregisterActivityEventListener(this);
            this.mDirPermissionsRequest = null;
        }
    }

    private static boolean isSAFUri(Uri uri) {
        return UriUtil.LOCAL_CONTENT_SCHEME.equals(uri.getScheme()) && uri.getHost().startsWith("com.android.externalstorage");
    }

    private static byte[] getInputStreamBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } finally {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static class DownloadResumableTaskParams {
        Call call;
        File file;
        boolean isResume;
        Map<String, Object> options;
        Promise promise;

        DownloadResumableTaskParams(Map<String, Object> map, Call call2, File file2, boolean z, Promise promise2) {
            this.options = map;
            this.call = call2;
            this.file = file2;
            this.isResume = z;
            this.promise = promise2;
        }
    }

    private class DownloadResumableTask extends AsyncTask<DownloadResumableTaskParams, Void, Void> {
        private DownloadResumableTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(DownloadResumableTaskParams... downloadResumableTaskParamsArr) {
            FileOutputStream fileOutputStream;
            Call call = downloadResumableTaskParamsArr[0].call;
            Promise promise = downloadResumableTaskParamsArr[0].promise;
            File file = downloadResumableTaskParamsArr[0].file;
            boolean z = downloadResumableTaskParamsArr[0].isResume;
            Map<String, Object> map = downloadResumableTaskParamsArr[0].options;
            try {
                C3401Response execute = call.execute();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(execute.body().byteStream());
                if (z) {
                    fileOutputStream = new FileOutputStream(file, true);
                } else {
                    fileOutputStream = new FileOutputStream(file, false);
                }
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                Bundle bundle = new Bundle();
                bundle.putString("uri", Uri.fromFile(file).toString());
                if (map != null && map.containsKey("md5") && ((Boolean) map.get("md5")).booleanValue()) {
                    bundle.putString("md5", FileSystemModule.this.md5(file));
                }
                bundle.putInt("status", execute.code());
                bundle.putBundle(FileSystemModule.HEADER_KEY, FileSystemModule.translateHeaders(execute.headers()));
                execute.close();
                promise.resolve(bundle);
                return null;
            } catch (Exception e) {
                Log.e(FileSystemModule.TAG, e.getMessage());
                promise.reject(e);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    public static Bundle translateHeaders(Headers headers) {
        Bundle bundle = new Bundle();
        for (int i = 0; i < headers.size(); i++) {
            String name = headers.name(i);
            if (bundle.get(name) != null) {
                bundle.putString(name, bundle.getString(name) + ", " + headers.value(i));
            } else {
                bundle.putString(name, headers.value(i));
            }
        }
        return bundle;
    }

    private static class DownloadResumable {
        public final Call call;
        public final Uri fileUri;
        public final String url;
        public final String uuid;

        public DownloadResumable(String str, String str2, Uri uri, Call call2) {
            this.uuid = str;
            this.url = str2;
            this.fileUri = uri;
            this.call = call2;
        }
    }

    private static class ProgressResponseBody extends ResponseBody {
        private BufferedSource bufferedSource;
        /* access modifiers changed from: private */
        public final ProgressListener progressListener;
        /* access modifiers changed from: private */
        public final ResponseBody responseBody;

        ProgressResponseBody(ResponseBody responseBody2, ProgressListener progressListener2) {
            this.responseBody = responseBody2;
            this.progressListener = progressListener2;
        }

        public MediaType contentType() {
            return this.responseBody.contentType();
        }

        public long contentLength() {
            return this.responseBody.contentLength();
        }

        public BufferedSource source() {
            if (this.bufferedSource == null) {
                this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
            }
            return this.bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0;

                public long read(Buffer buffer, long j) throws IOException {
                    long read = super.read(buffer, j);
                    int i = (read > -1 ? 1 : (read == -1 ? 0 : -1));
                    this.totalBytesRead += i != 0 ? read : 0;
                    ProgressResponseBody.this.progressListener.update(this.totalBytesRead, ProgressResponseBody.this.responseBody.contentLength(), i == 0);
                    return read;
                }
            };
        }
    }

    private synchronized OkHttpClient getOkHttpClient() {
        if (this.mClient == null) {
            OkHttpClient.Builder writeTimeout = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
            CookieHandler cookieHandler = (CookieHandler) this.mModuleRegistry.getModule(CookieHandler.class);
            if (cookieHandler != null) {
                writeTimeout.cookieJar(new JavaNetCookieJar(cookieHandler));
            }
            this.mClient = writeTimeout.build();
        }
        return this.mClient;
    }

    /* access modifiers changed from: private */
    public String md5(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            return String.valueOf(Hex.encodeHex(DigestUtils.md5((InputStream) fileInputStream)));
        } finally {
            fileInputStream.close();
        }
    }

    private void ensureDirExists(File file) throws IOException {
        if (!file.isDirectory() && !file.mkdirs()) {
            throw new IOException("Couldn't create directory '" + file + "'");
        }
    }

    private void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                IOException e = null;
                for (File forceDelete : listFiles) {
                    try {
                        forceDelete(forceDelete);
                    } catch (IOException e2) {
                        e = e2;
                    }
                }
                if (e != null) {
                    throw e;
                } else if (!file.delete()) {
                    throw new IOException("Unable to delete directory " + file + ".");
                }
            } else {
                throw new IOException("Failed to list contents of " + file);
            }
        } else if (!file.delete()) {
            throw new IOException("Unable to delete file: " + file);
        }
    }

    private long getFileSize(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] listFiles = file.listFiles();
        long j = 0;
        if (listFiles == null) {
            return 0;
        }
        for (File fileSize : listFiles) {
            j += getFileSize(fileSize);
        }
        return j;
    }

    private InputStream getInputStream(Uri uri) throws IOException {
        if (UriUtil.LOCAL_FILE_SCHEME.equals(uri.getScheme())) {
            return new FileInputStream(uriToFile(uri));
        }
        if (UriUtil.LOCAL_ASSET_SCHEME.equals(uri.getScheme())) {
            return openAssetInputStream(uri);
        }
        if (isSAFUri(uri)) {
            return getContext().getContentResolver().openInputStream(uri);
        }
        throw new IOException("Unsupported scheme for location '" + uri + "'.");
    }

    private OutputStream getOutputStream(Uri uri) throws IOException {
        if (UriUtil.LOCAL_FILE_SCHEME.equals(uri.getScheme())) {
            return new FileOutputStream(uriToFile(uri));
        }
        if (isSAFUri(uri)) {
            return getContext().getContentResolver().openOutputStream(uri);
        }
        throw new IOException("Unsupported scheme for location '" + uri + "'.");
    }

    private DocumentFile getNearestSAFFile(Uri uri) {
        DocumentFile fromSingleUri = DocumentFile.fromSingleUri(getContext(), uri);
        if (fromSingleUri == null || !fromSingleUri.isFile()) {
            return DocumentFile.fromTreeUri(getContext(), uri);
        }
        return fromSingleUri;
    }
}
