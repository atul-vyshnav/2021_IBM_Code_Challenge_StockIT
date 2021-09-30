package com.facebook.soloader;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.soloader.nativeloader.NativeLoader;
import com.google.android.exoplayer2.extractor.p012ts.TsExtractor;
import dalvik.system.BaseDexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class SoLoader {
    static final boolean DEBUG = false;
    public static final int SOLOADER_ALLOW_ASYNC_INIT = 2;
    public static final int SOLOADER_DISABLE_BACKUP_SOSOURCE = 8;
    public static final int SOLOADER_DONT_TREAT_AS_SYSTEMAPP = 32;
    public static final int SOLOADER_ENABLE_EXOPACKAGE = 1;
    public static final int SOLOADER_LOOK_IN_ZIP = 4;
    public static final int SOLOADER_SKIP_MERGED_JNI_ONLOAD = 16;
    private static final String SO_STORE_NAME_MAIN = "lib-main";
    private static final String SO_STORE_NAME_SPLIT = "lib-";
    static final boolean SYSTRACE_LIBRARY_LOADING;
    static final String TAG = "SoLoader";
    private static boolean isSystemApp;
    @Nullable
    private static ApplicationSoSource sApplicationSoSource;
    @Nullable
    private static UnpackingSoSource[] sBackupSoSources;
    private static int sFlags;
    private static final Set<String> sLoadedAndMergedLibraries = Collections.newSetFromMap(new ConcurrentHashMap());
    /* access modifiers changed from: private */
    public static final HashSet<String> sLoadedLibraries = new HashSet<>();
    /* access modifiers changed from: private */
    public static final Map<String, Object> sLoadingLibraries = new HashMap();
    @Nullable
    static SoFileLoader sSoFileLoader;
    /* access modifiers changed from: private */
    @Nullable
    public static SoSource[] sSoSources = null;
    /* access modifiers changed from: private */
    public static final ReentrantReadWriteLock sSoSourcesLock = new ReentrantReadWriteLock();
    private static volatile int sSoSourcesVersion = 0;
    @Nullable
    private static SystemLoadLibraryWrapper sSystemLoadLibraryWrapper = null;

    static /* synthetic */ int access$208() {
        int i = sSoSourcesVersion;
        sSoSourcesVersion = i + 1;
        return i;
    }

    static {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                z = true;
            }
        } catch (NoClassDefFoundError | UnsatisfiedLinkError unused) {
        }
        SYSTRACE_LIBRARY_LOADING = z;
    }

    public static void init(Context context, int i) throws IOException {
        init(context, i, (SoFileLoader) null);
    }

    public static void init(Context context, int i, @Nullable SoFileLoader soFileLoader) throws IOException {
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            isSystemApp = checkIfSystemApp(context, i);
            initSoLoader(soFileLoader);
            initSoSources(context, i, soFileLoader);
            if (!NativeLoader.isInitialized()) {
                NativeLoader.init(new NativeLoaderToSoLoaderDelegate());
            }
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public static void init(Context context, boolean z) {
        try {
            init(context, z ? 1 : 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initSoSources(Context context, int i, @Nullable SoFileLoader soFileLoader) throws IOException {
        int i2;
        sSoSourcesLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                Log.d(TAG, "init start");
                sFlags = i;
                ArrayList arrayList = new ArrayList();
                String str = System.getenv("LD_LIBRARY_PATH");
                if (str == null) {
                    str = SysUtil.is64Bit() ? "/vendor/lib64:/system/lib64" : "/vendor/lib:/system/lib";
                }
                for (String str2 : str.split(ParameterizedMessage.ERROR_MSG_SEPARATOR)) {
                    Log.d(TAG, "adding system library source: " + str2);
                    arrayList.add(new DirectorySoSource(new File(str2), 2));
                }
                if (context != null) {
                    if ((i & 1) != 0) {
                        sBackupSoSources = null;
                        Log.d(TAG, "adding exo package source: lib-main");
                        arrayList.add(0, new ExoSoSource(context, SO_STORE_NAME_MAIN));
                    } else {
                        if (isSystemApp) {
                            i2 = 0;
                        } else {
                            sApplicationSoSource = new ApplicationSoSource(context, Build.VERSION.SDK_INT <= 17 ? 1 : 0);
                            Log.d(TAG, "adding application source: " + sApplicationSoSource.toString());
                            arrayList.add(0, sApplicationSoSource);
                            i2 = 1;
                        }
                        if ((sFlags & 8) != 0) {
                            sBackupSoSources = null;
                        } else {
                            File file = new File(context.getApplicationInfo().sourceDir);
                            ArrayList arrayList2 = new ArrayList();
                            ApkSoSource apkSoSource = new ApkSoSource(context, file, SO_STORE_NAME_MAIN, i2);
                            arrayList2.add(apkSoSource);
                            Log.d(TAG, "adding backup source from : " + apkSoSource.toString());
                            if (Build.VERSION.SDK_INT >= 21 && context.getApplicationInfo().splitSourceDirs != null) {
                                Log.d(TAG, "adding backup sources from split apks");
                                String[] strArr = context.getApplicationInfo().splitSourceDirs;
                                int length = strArr.length;
                                int i3 = 0;
                                int i4 = 0;
                                while (i3 < length) {
                                    File file2 = new File(strArr[i3]);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(SO_STORE_NAME_SPLIT);
                                    sb.append(i4);
                                    ApkSoSource apkSoSource2 = new ApkSoSource(context, file2, sb.toString(), i2);
                                    Log.d(TAG, "adding backup source: " + apkSoSource2.toString());
                                    arrayList2.add(apkSoSource2);
                                    i3++;
                                    i4++;
                                }
                            }
                            sBackupSoSources = (UnpackingSoSource[]) arrayList2.toArray(new UnpackingSoSource[arrayList2.size()]);
                            arrayList.addAll(0, arrayList2);
                        }
                    }
                }
                SoSource[] soSourceArr = (SoSource[]) arrayList.toArray(new SoSource[arrayList.size()]);
                int makePrepareFlags = makePrepareFlags();
                int length2 = soSourceArr.length;
                while (true) {
                    int i5 = length2 - 1;
                    if (length2 <= 0) {
                        break;
                    }
                    Log.d(TAG, "Preparing SO source: " + soSourceArr[i5]);
                    soSourceArr[i5].prepare(makePrepareFlags);
                    length2 = i5;
                }
                sSoSources = soSourceArr;
                sSoSourcesVersion++;
                Log.d(TAG, "init finish: " + sSoSources.length + " SO sources prepared");
            }
        } finally {
            Log.d(TAG, "init exiting");
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static int makePrepareFlags() {
        sSoSourcesLock.writeLock().lock();
        try {
            return (sFlags & 2) != 0 ? 1 : 0;
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static synchronized void initSoLoader(@Nullable SoFileLoader soFileLoader) {
        synchronized (SoLoader.class) {
            if (soFileLoader != null) {
                sSoFileLoader = soFileLoader;
                return;
            }
            final Runtime runtime = Runtime.getRuntime();
            final Method nativeLoadRuntimeMethod = getNativeLoadRuntimeMethod();
            final boolean z = nativeLoadRuntimeMethod != null;
            final String classLoaderLdLoadLibrary = z ? Api14Utils.getClassLoaderLdLoadLibrary() : null;
            final String makeNonZipPath = makeNonZipPath(classLoaderLdLoadLibrary);
            sSoFileLoader = new SoFileLoader() {
                /* JADX WARNING: type inference failed for: r1v0 */
                /* JADX WARNING: type inference failed for: r1v10 */
                /* JADX WARNING: type inference failed for: r1v12 */
                /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
                    if (r1 == null) goto L_?;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
                    android.util.Log.e(com.facebook.soloader.SoLoader.TAG, "Error when loading lib: " + r1 + " lib hash: " + getLibHash(r9) + " search path is " + r10);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
                    return;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:39:0x009e  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void load(java.lang.String r9, int r10) {
                    /*
                        r8 = this;
                        boolean r0 = r2
                        if (r0 == 0) goto L_0x00c9
                        r0 = 4
                        r10 = r10 & r0
                        r1 = 1
                        r2 = 0
                        if (r10 != r0) goto L_0x000c
                        r10 = 1
                        goto L_0x000d
                    L_0x000c:
                        r10 = 0
                    L_0x000d:
                        if (r10 == 0) goto L_0x0012
                        java.lang.String r10 = r3
                        goto L_0x0014
                    L_0x0012:
                        java.lang.String r10 = r4
                    L_0x0014:
                        r0 = 0
                        java.lang.Runtime r3 = r5     // Catch:{ IllegalAccessException -> 0x0080, IllegalArgumentException -> 0x007e, InvocationTargetException -> 0x007c, all -> 0x0077 }
                        monitor-enter(r3)     // Catch:{ IllegalAccessException -> 0x0080, IllegalArgumentException -> 0x007e, InvocationTargetException -> 0x007c, all -> 0x0077 }
                        java.lang.reflect.Method r4 = r6     // Catch:{ all -> 0x0069 }
                        java.lang.Runtime r5 = r5     // Catch:{ all -> 0x0069 }
                        r6 = 3
                        java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0069 }
                        r6[r2] = r9     // Catch:{ all -> 0x0069 }
                        java.lang.Class<com.facebook.soloader.SoLoader> r2 = com.facebook.soloader.SoLoader.class
                        java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ all -> 0x0069 }
                        r6[r1] = r2     // Catch:{ all -> 0x0069 }
                        r1 = 2
                        r6[r1] = r10     // Catch:{ all -> 0x0069 }
                        java.lang.Object r1 = r4.invoke(r5, r6)     // Catch:{ all -> 0x0069 }
                        java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0069 }
                        if (r1 != 0) goto L_0x0063
                        monitor-exit(r3)     // Catch:{ all -> 0x0075 }
                        if (r1 == 0) goto L_0x00cc
                        java.lang.String r0 = "SoLoader"
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder
                        r2.<init>()
                        java.lang.String r3 = "Error when loading lib: "
                        r2.append(r3)
                        r2.append(r1)
                        java.lang.String r1 = " lib hash: "
                        r2.append(r1)
                        java.lang.String r9 = r8.getLibHash(r9)
                        r2.append(r9)
                        java.lang.String r9 = " search path is "
                        r2.append(r9)
                        r2.append(r10)
                        java.lang.String r9 = r2.toString()
                        android.util.Log.e(r0, r9)
                        goto L_0x00cc
                    L_0x0063:
                        java.lang.UnsatisfiedLinkError r0 = new java.lang.UnsatisfiedLinkError     // Catch:{ all -> 0x0075 }
                        r0.<init>(r1)     // Catch:{ all -> 0x0075 }
                        throw r0     // Catch:{ all -> 0x0075 }
                    L_0x0069:
                        r1 = move-exception
                        r7 = r1
                        r1 = r0
                        r0 = r7
                    L_0x006d:
                        monitor-exit(r3)     // Catch:{ all -> 0x0075 }
                        throw r0     // Catch:{ IllegalAccessException -> 0x0073, IllegalArgumentException -> 0x0071, InvocationTargetException -> 0x006f }
                    L_0x006f:
                        r0 = move-exception
                        goto L_0x0084
                    L_0x0071:
                        r0 = move-exception
                        goto L_0x0084
                    L_0x0073:
                        r0 = move-exception
                        goto L_0x0084
                    L_0x0075:
                        r0 = move-exception
                        goto L_0x006d
                    L_0x0077:
                        r1 = move-exception
                        r7 = r1
                        r1 = r0
                        r0 = r7
                        goto L_0x009c
                    L_0x007c:
                        r1 = move-exception
                        goto L_0x0081
                    L_0x007e:
                        r1 = move-exception
                        goto L_0x0081
                    L_0x0080:
                        r1 = move-exception
                    L_0x0081:
                        r7 = r1
                        r1 = r0
                        r0 = r7
                    L_0x0084:
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009b }
                        r2.<init>()     // Catch:{ all -> 0x009b }
                        java.lang.String r3 = "Error: Cannot load "
                        r2.append(r3)     // Catch:{ all -> 0x009b }
                        r2.append(r9)     // Catch:{ all -> 0x009b }
                        java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x009b }
                        java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x009b }
                        r2.<init>(r1, r0)     // Catch:{ all -> 0x009b }
                        throw r2     // Catch:{ all -> 0x009b }
                    L_0x009b:
                        r0 = move-exception
                    L_0x009c:
                        if (r1 == 0) goto L_0x00c8
                        java.lang.String r2 = "SoLoader"
                        java.lang.StringBuilder r3 = new java.lang.StringBuilder
                        r3.<init>()
                        java.lang.String r4 = "Error when loading lib: "
                        r3.append(r4)
                        r3.append(r1)
                        java.lang.String r1 = " lib hash: "
                        r3.append(r1)
                        java.lang.String r9 = r8.getLibHash(r9)
                        r3.append(r9)
                        java.lang.String r9 = " search path is "
                        r3.append(r9)
                        r3.append(r10)
                        java.lang.String r9 = r3.toString()
                        android.util.Log.e(r2, r9)
                    L_0x00c8:
                        throw r0
                    L_0x00c9:
                        java.lang.System.load(r9)
                    L_0x00cc:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.C13341.load(java.lang.String, int):void");
                }

                /* JADX WARNING: Code restructure failed: missing block: B:14:0x0039, code lost:
                    r0 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
                    r1.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
                    throw r0;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private java.lang.String getLibHash(java.lang.String r7) {
                    /*
                        r6 = this;
                        java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        r0.<init>(r7)     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        java.lang.String r7 = "MD5"
                        java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r7)     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        r1.<init>(r0)     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        r0 = 4096(0x1000, float:5.74E-42)
                        byte[] r0 = new byte[r0]     // Catch:{ all -> 0x0037 }
                    L_0x0014:
                        int r2 = r1.read(r0)     // Catch:{ all -> 0x0037 }
                        r3 = 0
                        if (r2 <= 0) goto L_0x001f
                        r7.update(r0, r3, r2)     // Catch:{ all -> 0x0037 }
                        goto L_0x0014
                    L_0x001f:
                        java.lang.String r0 = "%32x"
                        r2 = 1
                        java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ all -> 0x0037 }
                        java.math.BigInteger r5 = new java.math.BigInteger     // Catch:{ all -> 0x0037 }
                        byte[] r7 = r7.digest()     // Catch:{ all -> 0x0037 }
                        r5.<init>(r2, r7)     // Catch:{ all -> 0x0037 }
                        r4[r3] = r5     // Catch:{ all -> 0x0037 }
                        java.lang.String r7 = java.lang.String.format(r0, r4)     // Catch:{ all -> 0x0037 }
                        r1.close()     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                        goto L_0x0054
                    L_0x0037:
                        r7 = move-exception
                        throw r7     // Catch:{ all -> 0x0039 }
                    L_0x0039:
                        r0 = move-exception
                        r1.close()     // Catch:{ all -> 0x003e }
                        goto L_0x0042
                    L_0x003e:
                        r1 = move-exception
                        r7.addSuppressed(r1)     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                    L_0x0042:
                        throw r0     // Catch:{ IOException -> 0x004f, SecurityException -> 0x0049, NoSuchAlgorithmException -> 0x0043 }
                    L_0x0043:
                        r7 = move-exception
                        java.lang.String r7 = r7.toString()
                        goto L_0x0054
                    L_0x0049:
                        r7 = move-exception
                        java.lang.String r7 = r7.toString()
                        goto L_0x0054
                    L_0x004f:
                        r7 = move-exception
                        java.lang.String r7 = r7.toString()
                    L_0x0054:
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.C13341.getLibHash(java.lang.String):java.lang.String");
                }
            };
        }
    }

    @Nullable
    private static Method getNativeLoadRuntimeMethod() {
        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 27) {
            try {
                Method declaredMethod = Runtime.class.getDeclaredMethod("nativeLoad", new Class[]{String.class, ClassLoader.class, String.class});
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (NoSuchMethodException | SecurityException e) {
                Log.w(TAG, "Cannot get nativeLoad method", e);
            }
        }
        return null;
    }

    private static boolean checkIfSystemApp(Context context, int i) {
        return ((i & 32) != 0 || context == null || (context.getApplicationInfo().flags & TsExtractor.TS_STREAM_TYPE_AC3) == 0) ? false : true;
    }

    public static void setInTestMode() {
        TestOnlyUtils.setSoSources(new SoSource[]{new NoopSoSource()});
    }

    public static void deinitForTest() {
        TestOnlyUtils.setSoSources((SoSource[]) null);
    }

    static class TestOnlyUtils {
        TestOnlyUtils() {
        }

        static void setSoSources(SoSource[] soSourceArr) {
            SoLoader.sSoSourcesLock.writeLock().lock();
            try {
                SoSource[] unused = SoLoader.sSoSources = soSourceArr;
                SoLoader.access$208();
            } finally {
                SoLoader.sSoSourcesLock.writeLock().unlock();
            }
        }

        static void setSoFileLoader(SoFileLoader soFileLoader) {
            SoLoader.sSoFileLoader = soFileLoader;
        }

        static void resetStatus() {
            synchronized (SoLoader.class) {
                SoLoader.sLoadedLibraries.clear();
                SoLoader.sLoadingLibraries.clear();
                SoLoader.sSoFileLoader = null;
            }
            setSoSources((SoSource[]) null);
        }
    }

    public static void setSystemLoadLibraryWrapper(SystemLoadLibraryWrapper systemLoadLibraryWrapper) {
        sSystemLoadLibraryWrapper = systemLoadLibraryWrapper;
    }

    public static final class WrongAbiError extends UnsatisfiedLinkError {
        WrongAbiError(Throwable th, String str) {
            super("APK was built for a different platform. Supported ABIs: " + Arrays.toString(SysUtil.getSupportedAbis()) + " error: " + str);
            initCause(th);
        }
    }

    @Nullable
    public static String getLibraryPath(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            String str2 = null;
            if (sSoSources != null) {
                int i = 0;
                while (str2 == null && i < sSoSources.length) {
                    str2 = sSoSources[i].getLibraryPath(str);
                    i++;
                }
            }
            return str2;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    @Nullable
    public static String[] getLibraryDependencies(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            String[] strArr = null;
            if (sSoSources != null) {
                int i = 0;
                while (strArr == null && i < sSoSources.length) {
                    strArr = sSoSources[i].getLibraryDependencies(str);
                    i++;
                }
            }
            return strArr;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean loadLibrary(String str) {
        return loadLibrary(str, 0);
    }

    public static boolean loadLibrary(String str, int i) throws UnsatisfiedLinkError {
        SystemLoadLibraryWrapper systemLoadLibraryWrapper;
        boolean z;
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources == null) {
                if ("http://www.android.com/".equals(System.getProperty("java.vendor.url"))) {
                    assertInitialized();
                } else {
                    synchronized (SoLoader.class) {
                        z = !sLoadedLibraries.contains(str);
                        if (z) {
                            if (sSystemLoadLibraryWrapper != null) {
                                sSystemLoadLibraryWrapper.loadLibrary(str);
                            } else {
                                System.loadLibrary(str);
                            }
                        }
                    }
                    sSoSourcesLock.readLock().unlock();
                    return z;
                }
            }
            sSoSourcesLock.readLock().unlock();
            if (!isSystemApp || (systemLoadLibraryWrapper = sSystemLoadLibraryWrapper) == null) {
                String mapLibName = MergedSoMapping.mapLibName(str);
                return loadLibraryBySoName(System.mapLibraryName(mapLibName != null ? mapLibName : str), str, mapLibName, i, (StrictMode.ThreadPolicy) null);
            }
            systemLoadLibraryWrapper.loadLibrary(str);
            return true;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    static void loadLibraryBySoName(String str, int i, StrictMode.ThreadPolicy threadPolicy) {
        loadLibraryBySoNameImpl(str, (String) null, (String) null, i, threadPolicy);
    }

    private static boolean loadLibraryBySoName(String str, @Nullable String str2, @Nullable String str3, int i, @Nullable StrictMode.ThreadPolicy threadPolicy) {
        boolean z;
        boolean z2 = false;
        do {
            try {
                z2 = loadLibraryBySoNameImpl(str, str2, str3, i, threadPolicy);
                z = false;
                continue;
            } catch (UnsatisfiedLinkError e) {
                int i2 = sSoSourcesVersion;
                sSoSourcesLock.writeLock().lock();
                try {
                    z = true;
                    if (sApplicationSoSource == null || !sApplicationSoSource.checkAndMaybeUpdate()) {
                        z = false;
                    } else {
                        Log.w(TAG, "sApplicationSoSource updated during load: " + str + ", attempting load again.");
                        sSoSourcesVersion = sSoSourcesVersion + 1;
                    }
                    sSoSourcesLock.writeLock().unlock();
                    if (sSoSourcesVersion == i2) {
                        throw e;
                    }
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                } catch (Throwable th) {
                    sSoSourcesLock.writeLock().unlock();
                    throw th;
                }
            }
        } while (z);
        return z2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003d, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003e, code lost:
        if (r1 != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
        if (sLoadedLibraries.contains(r8) == false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0049, code lost:
        if (r10 != null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004b, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004e, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0050, code lost:
        if (r1 != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        android.util.Log.d(TAG, "About to load: " + r8);
        doLoadLibraryBySoName(r8, r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        android.util.Log.d(TAG, "Loaded: " + r8);
        sLoadedLibraries.add(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0087, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x008c, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x008d, code lost:
        r9 = r8.getMessage();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0091, code lost:
        if (r9 == null) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ac, code lost:
        throw new com.facebook.soloader.SoLoader.WrongAbiError(r8, r9.substring(r9.lastIndexOf("unexpected e_machine:")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ad, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b3, code lost:
        if ((r11 & 16) != 0) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00b9, code lost:
        if (android.text.TextUtils.isEmpty(r9) != false) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c1, code lost:
        if (sLoadedAndMergedLibraries.contains(r9) == false) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c3, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00c4, code lost:
        if (r10 == null) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c6, code lost:
        if (r2 != false) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00ca, code lost:
        if (SYSTRACE_LIBRARY_LOADING == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00cc, code lost:
        com.facebook.soloader.Api18TraceUtils.beginTraceSection("MergedSoMapping.invokeJniOnload[", r9, "]");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        android.util.Log.d(TAG, "About to merge: " + r9 + " / " + r8);
        com.facebook.soloader.MergedSoMapping.invokeJniOnload(r9);
        sLoadedAndMergedLibraries.add(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00fb, code lost:
        if (SYSTRACE_LIBRARY_LOADING == false) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00fd, code lost:
        com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0101, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0103, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0127, code lost:
        throw new java.lang.RuntimeException("Failed to call JNI_OnLoad from '" + r9 + "', which has been merged into '" + r8 + "'.  See comment for details.", r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x012a, code lost:
        if (SYSTRACE_LIBRARY_LOADING != false) goto L_0x012c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x012c, code lost:
        com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x012f, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0130, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0133, code lost:
        return !r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean loadLibraryBySoNameImpl(java.lang.String r8, @javax.annotation.Nullable java.lang.String r9, @javax.annotation.Nullable java.lang.String r10, int r11, @javax.annotation.Nullable android.os.StrictMode.ThreadPolicy r12) {
        /*
            java.lang.Class<com.facebook.soloader.SoLoader> r0 = com.facebook.soloader.SoLoader.class
            boolean r1 = android.text.TextUtils.isEmpty(r9)
            r2 = 0
            if (r1 != 0) goto L_0x0012
            java.util.Set<java.lang.String> r1 = sLoadedAndMergedLibraries
            boolean r1 = r1.contains(r9)
            if (r1 == 0) goto L_0x0012
            return r2
        L_0x0012:
            monitor-enter(r0)
            java.util.HashSet<java.lang.String> r1 = sLoadedLibraries     // Catch:{ all -> 0x0137 }
            boolean r1 = r1.contains(r8)     // Catch:{ all -> 0x0137 }
            r3 = 1
            if (r1 == 0) goto L_0x0022
            if (r10 != 0) goto L_0x0020
            monitor-exit(r0)     // Catch:{ all -> 0x0137 }
            return r2
        L_0x0020:
            r1 = 1
            goto L_0x0023
        L_0x0022:
            r1 = 0
        L_0x0023:
            java.util.Map<java.lang.String, java.lang.Object> r4 = sLoadingLibraries     // Catch:{ all -> 0x0137 }
            boolean r4 = r4.containsKey(r8)     // Catch:{ all -> 0x0137 }
            if (r4 == 0) goto L_0x0032
            java.util.Map<java.lang.String, java.lang.Object> r4 = sLoadingLibraries     // Catch:{ all -> 0x0137 }
            java.lang.Object r4 = r4.get(r8)     // Catch:{ all -> 0x0137 }
            goto L_0x003c
        L_0x0032:
            java.lang.Object r4 = new java.lang.Object     // Catch:{ all -> 0x0137 }
            r4.<init>()     // Catch:{ all -> 0x0137 }
            java.util.Map<java.lang.String, java.lang.Object> r5 = sLoadingLibraries     // Catch:{ all -> 0x0137 }
            r5.put(r8, r4)     // Catch:{ all -> 0x0137 }
        L_0x003c:
            monitor-exit(r0)     // Catch:{ all -> 0x0137 }
            monitor-enter(r4)
            if (r1 != 0) goto L_0x00b1
            monitor-enter(r0)     // Catch:{ all -> 0x0134 }
            java.util.HashSet<java.lang.String> r5 = sLoadedLibraries     // Catch:{ all -> 0x00ae }
            boolean r5 = r5.contains(r8)     // Catch:{ all -> 0x00ae }
            if (r5 == 0) goto L_0x004f
            if (r10 != 0) goto L_0x004e
            monitor-exit(r0)     // Catch:{ all -> 0x00ae }
            monitor-exit(r4)     // Catch:{ all -> 0x0134 }
            return r2
        L_0x004e:
            r1 = 1
        L_0x004f:
            monitor-exit(r0)     // Catch:{ all -> 0x00ae }
            if (r1 != 0) goto L_0x00b1
            java.lang.String r5 = "SoLoader"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            r6.<init>()     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            java.lang.String r7 = "About to load: "
            r6.append(r7)     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            r6.append(r8)     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            java.lang.String r6 = r6.toString()     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            android.util.Log.d(r5, r6)     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            doLoadLibraryBySoName(r8, r11, r12)     // Catch:{ UnsatisfiedLinkError -> 0x008c }
            monitor-enter(r0)     // Catch:{ all -> 0x0134 }
            java.lang.String r12 = "SoLoader"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0089 }
            r5.<init>()     // Catch:{ all -> 0x0089 }
            java.lang.String r6 = "Loaded: "
            r5.append(r6)     // Catch:{ all -> 0x0089 }
            r5.append(r8)     // Catch:{ all -> 0x0089 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0089 }
            android.util.Log.d(r12, r5)     // Catch:{ all -> 0x0089 }
            java.util.HashSet<java.lang.String> r12 = sLoadedLibraries     // Catch:{ all -> 0x0089 }
            r12.add(r8)     // Catch:{ all -> 0x0089 }
            monitor-exit(r0)     // Catch:{ all -> 0x0089 }
            goto L_0x00b1
        L_0x0089:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0089 }
            throw r8     // Catch:{ all -> 0x0134 }
        L_0x008c:
            r8 = move-exception
            java.lang.String r9 = r8.getMessage()     // Catch:{ all -> 0x0134 }
            if (r9 == 0) goto L_0x00ad
            java.lang.String r10 = "unexpected e_machine:"
            boolean r10 = r9.contains(r10)     // Catch:{ all -> 0x0134 }
            if (r10 == 0) goto L_0x00ad
            java.lang.String r10 = "unexpected e_machine:"
            int r10 = r9.lastIndexOf(r10)     // Catch:{ all -> 0x0134 }
            java.lang.String r9 = r9.substring(r10)     // Catch:{ all -> 0x0134 }
            com.facebook.soloader.SoLoader$WrongAbiError r10 = new com.facebook.soloader.SoLoader$WrongAbiError     // Catch:{ all -> 0x0134 }
            r10.<init>(r8, r9)     // Catch:{ all -> 0x0134 }
            throw r10     // Catch:{ all -> 0x0134 }
        L_0x00ad:
            throw r8     // Catch:{ all -> 0x0134 }
        L_0x00ae:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ae }
            throw r8     // Catch:{ all -> 0x0134 }
        L_0x00b1:
            r11 = r11 & 16
            if (r11 != 0) goto L_0x0130
            boolean r11 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0134 }
            if (r11 != 0) goto L_0x00c4
            java.util.Set<java.lang.String> r11 = sLoadedAndMergedLibraries     // Catch:{ all -> 0x0134 }
            boolean r11 = r11.contains(r9)     // Catch:{ all -> 0x0134 }
            if (r11 == 0) goto L_0x00c4
            r2 = 1
        L_0x00c4:
            if (r10 == 0) goto L_0x0130
            if (r2 != 0) goto L_0x0130
            boolean r10 = SYSTRACE_LIBRARY_LOADING     // Catch:{ all -> 0x0134 }
            if (r10 == 0) goto L_0x00d3
            java.lang.String r10 = "MergedSoMapping.invokeJniOnload["
            java.lang.String r11 = "]"
            com.facebook.soloader.Api18TraceUtils.beginTraceSection(r10, r9, r11)     // Catch:{ all -> 0x0134 }
        L_0x00d3:
            java.lang.String r10 = "SoLoader"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            r11.<init>()     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            java.lang.String r12 = "About to merge: "
            r11.append(r12)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            r11.append(r9)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            java.lang.String r12 = " / "
            r11.append(r12)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            r11.append(r8)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            java.lang.String r11 = r11.toString()     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            android.util.Log.d(r10, r11)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            com.facebook.soloader.MergedSoMapping.invokeJniOnload(r9)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            java.util.Set<java.lang.String> r10 = sLoadedAndMergedLibraries     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            r10.add(r9)     // Catch:{ UnsatisfiedLinkError -> 0x0103 }
            boolean r8 = SYSTRACE_LIBRARY_LOADING     // Catch:{ all -> 0x0134 }
            if (r8 == 0) goto L_0x0130
            com.facebook.soloader.Api18TraceUtils.endSection()     // Catch:{ all -> 0x0134 }
            goto L_0x0130
        L_0x0101:
            r8 = move-exception
            goto L_0x0128
        L_0x0103:
            r10 = move-exception
            java.lang.RuntimeException r11 = new java.lang.RuntimeException     // Catch:{ all -> 0x0101 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0101 }
            r12.<init>()     // Catch:{ all -> 0x0101 }
            java.lang.String r0 = "Failed to call JNI_OnLoad from '"
            r12.append(r0)     // Catch:{ all -> 0x0101 }
            r12.append(r9)     // Catch:{ all -> 0x0101 }
            java.lang.String r9 = "', which has been merged into '"
            r12.append(r9)     // Catch:{ all -> 0x0101 }
            r12.append(r8)     // Catch:{ all -> 0x0101 }
            java.lang.String r8 = "'.  See comment for details."
            r12.append(r8)     // Catch:{ all -> 0x0101 }
            java.lang.String r8 = r12.toString()     // Catch:{ all -> 0x0101 }
            r11.<init>(r8, r10)     // Catch:{ all -> 0x0101 }
            throw r11     // Catch:{ all -> 0x0101 }
        L_0x0128:
            boolean r9 = SYSTRACE_LIBRARY_LOADING     // Catch:{ all -> 0x0134 }
            if (r9 == 0) goto L_0x012f
            com.facebook.soloader.Api18TraceUtils.endSection()     // Catch:{ all -> 0x0134 }
        L_0x012f:
            throw r8     // Catch:{ all -> 0x0134 }
        L_0x0130:
            monitor-exit(r4)     // Catch:{ all -> 0x0134 }
            r8 = r1 ^ 1
            return r8
        L_0x0134:
            r8 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0134 }
            throw r8
        L_0x0137:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0137 }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.loadLibraryBySoNameImpl(java.lang.String, java.lang.String, java.lang.String, int, android.os.StrictMode$ThreadPolicy):boolean");
    }

    public static File unpackLibraryAndDependencies(String str) throws UnsatisfiedLinkError {
        assertInitialized();
        try {
            return unpackLibraryBySoName(System.mapLibraryName(str));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0139  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void doLoadLibraryBySoName(java.lang.String r12, int r13, @javax.annotation.Nullable android.os.StrictMode.ThreadPolicy r14) throws java.lang.UnsatisfiedLinkError {
        /*
            java.lang.String r0 = " result: "
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.lock()
            com.facebook.soloader.SoSource[] r1 = sSoSources     // Catch:{ all -> 0x0189 }
            java.lang.String r2 = "couldn't find DSO to load: "
            java.lang.String r3 = "SoLoader"
            if (r1 == 0) goto L_0x015b
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.unlock()
            r1 = 1
            r4 = 0
            if (r14 != 0) goto L_0x0026
            android.os.StrictMode$ThreadPolicy r14 = android.os.StrictMode.allowThreadDiskReads()
            r5 = 1
            goto L_0x0027
        L_0x0026:
            r5 = 0
        L_0x0027:
            boolean r6 = SYSTRACE_LIBRARY_LOADING
            if (r6 == 0) goto L_0x0032
            java.lang.String r6 = "SoLoader.loadLibrary["
            java.lang.String r7 = "]"
            com.facebook.soloader.Api18TraceUtils.beginTraceSection(r6, r12, r7)
        L_0x0032:
            r6 = 3
            java.util.concurrent.locks.ReentrantReadWriteLock r7 = sSoSourcesLock     // Catch:{ all -> 0x0115 }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ all -> 0x0115 }
            r7.lock()     // Catch:{ all -> 0x0115 }
            r7 = 0
            r8 = 0
        L_0x003e:
            if (r7 != 0) goto L_0x008c
            com.facebook.soloader.SoSource[] r9 = sSoSources     // Catch:{ all -> 0x0080 }
            int r9 = r9.length     // Catch:{ all -> 0x0080 }
            if (r8 >= r9) goto L_0x008c
            com.facebook.soloader.SoSource[] r9 = sSoSources     // Catch:{ all -> 0x0080 }
            r9 = r9[r8]     // Catch:{ all -> 0x0080 }
            int r7 = r9.loadLibrary(r12, r13, r14)     // Catch:{ all -> 0x0080 }
            if (r7 != r6) goto L_0x007d
            com.facebook.soloader.UnpackingSoSource[] r9 = sBackupSoSources     // Catch:{ all -> 0x0080 }
            if (r9 == 0) goto L_0x007d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0080 }
            r8.<init>()     // Catch:{ all -> 0x0080 }
            java.lang.String r9 = "Trying backup SoSource for "
            r8.append(r9)     // Catch:{ all -> 0x0080 }
            r8.append(r12)     // Catch:{ all -> 0x0080 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0080 }
            android.util.Log.d(r3, r8)     // Catch:{ all -> 0x0080 }
            com.facebook.soloader.UnpackingSoSource[] r8 = sBackupSoSources     // Catch:{ all -> 0x0080 }
            int r9 = r8.length     // Catch:{ all -> 0x0080 }
            r10 = 0
        L_0x006b:
            if (r10 >= r9) goto L_0x008c
            r11 = r8[r10]     // Catch:{ all -> 0x0080 }
            r11.prepare((java.lang.String) r12)     // Catch:{ all -> 0x0080 }
            int r11 = r11.loadLibrary(r12, r13, r14)     // Catch:{ all -> 0x0080 }
            if (r11 != r1) goto L_0x007a
            r7 = r11
            goto L_0x008c
        L_0x007a:
            int r10 = r10 + 1
            goto L_0x006b
        L_0x007d:
            int r8 = r8 + 1
            goto L_0x003e
        L_0x0080:
            r13 = move-exception
            r4 = r7
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = sSoSourcesLock     // Catch:{ all -> 0x0115 }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()     // Catch:{ all -> 0x0115 }
            r1.unlock()     // Catch:{ all -> 0x0115 }
            throw r13     // Catch:{ all -> 0x0115 }
        L_0x008c:
            java.util.concurrent.locks.ReentrantReadWriteLock r13 = sSoSourcesLock     // Catch:{ all -> 0x0112 }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r13 = r13.readLock()     // Catch:{ all -> 0x0112 }
            r13.unlock()     // Catch:{ all -> 0x0112 }
            boolean r13 = SYSTRACE_LIBRARY_LOADING
            if (r13 == 0) goto L_0x009c
            com.facebook.soloader.Api18TraceUtils.endSection()
        L_0x009c:
            if (r5 == 0) goto L_0x00a1
            android.os.StrictMode.setThreadPolicy(r14)
        L_0x00a1:
            if (r7 == 0) goto L_0x00a5
            if (r7 != r6) goto L_0x0127
        L_0x00a5:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r2)
            r13.append(r12)
            java.util.concurrent.locks.ReentrantReadWriteLock r12 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r12 = r12.readLock()
            r12.lock()
        L_0x00b9:
            com.facebook.soloader.SoSource[] r12 = sSoSources
            int r12 = r12.length
            if (r4 >= r12) goto L_0x00d9
            java.lang.String r12 = "\n\tSoSource "
            r13.append(r12)
            r13.append(r4)
            java.lang.String r12 = ": "
            r13.append(r12)
            com.facebook.soloader.SoSource[] r12 = sSoSources
            r12 = r12[r4]
            java.lang.String r12 = r12.toString()
            r13.append(r12)
            int r4 = r4 + 1
            goto L_0x00b9
        L_0x00d9:
            com.facebook.soloader.ApplicationSoSource r12 = sApplicationSoSource
            if (r12 == 0) goto L_0x00f6
            android.content.Context r12 = r12.getUpdatedContext()
            java.io.File r12 = com.facebook.soloader.ApplicationSoSource.getNativeLibDirFromContext(r12)
            java.lang.String r14 = "\n\tNative lib dir: "
            r13.append(r14)
            java.lang.String r12 = r12.getAbsolutePath()
            r13.append(r12)
            java.lang.String r12 = "\n"
            r13.append(r12)
        L_0x00f6:
            java.util.concurrent.locks.ReentrantReadWriteLock r12 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r12 = r12.readLock()
            r12.unlock()
            r13.append(r0)
            r13.append(r7)
            java.lang.String r12 = r13.toString()
            android.util.Log.e(r3, r12)
            java.lang.UnsatisfiedLinkError r13 = new java.lang.UnsatisfiedLinkError
            r13.<init>(r12)
            throw r13
        L_0x0112:
            r13 = move-exception
            r4 = r7
            goto L_0x0116
        L_0x0115:
            r13 = move-exception
        L_0x0116:
            boolean r1 = SYSTRACE_LIBRARY_LOADING
            if (r1 == 0) goto L_0x011d
            com.facebook.soloader.Api18TraceUtils.endSection()
        L_0x011d:
            if (r5 == 0) goto L_0x0122
            android.os.StrictMode.setThreadPolicy(r14)
        L_0x0122:
            if (r4 == 0) goto L_0x0128
            if (r4 != r6) goto L_0x0127
            goto L_0x0128
        L_0x0127:
            return
        L_0x0128:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r2)
            r14.append(r12)
            java.lang.String r12 = r13.getMessage()
            if (r12 != 0) goto L_0x013d
            java.lang.String r12 = r13.toString()
        L_0x013d:
            java.lang.String r1 = " caused by: "
            r14.append(r1)
            r14.append(r12)
            r13.printStackTrace()
            r14.append(r0)
            r14.append(r4)
            java.lang.String r12 = r14.toString()
            android.util.Log.e(r3, r12)
            java.lang.UnsatisfiedLinkError r13 = new java.lang.UnsatisfiedLinkError
            r13.<init>(r12)
            throw r13
        L_0x015b:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0189 }
            r13.<init>()     // Catch:{ all -> 0x0189 }
            java.lang.String r14 = "Could not load: "
            r13.append(r14)     // Catch:{ all -> 0x0189 }
            r13.append(r12)     // Catch:{ all -> 0x0189 }
            java.lang.String r14 = " because no SO source exists"
            r13.append(r14)     // Catch:{ all -> 0x0189 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0189 }
            android.util.Log.e(r3, r13)     // Catch:{ all -> 0x0189 }
            java.lang.UnsatisfiedLinkError r13 = new java.lang.UnsatisfiedLinkError     // Catch:{ all -> 0x0189 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0189 }
            r14.<init>()     // Catch:{ all -> 0x0189 }
            r14.append(r2)     // Catch:{ all -> 0x0189 }
            r14.append(r12)     // Catch:{ all -> 0x0189 }
            java.lang.String r12 = r14.toString()     // Catch:{ all -> 0x0189 }
            r13.<init>(r12)     // Catch:{ all -> 0x0189 }
            throw r13     // Catch:{ all -> 0x0189 }
        L_0x0189:
            r12 = move-exception
            java.util.concurrent.locks.ReentrantReadWriteLock r13 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r13 = r13.readLock()
            r13.unlock()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.doLoadLibraryBySoName(java.lang.String, int, android.os.StrictMode$ThreadPolicy):void");
    }

    @Nullable
    public static String makeNonZipPath(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split(ParameterizedMessage.ERROR_MSG_SEPARATOR);
        ArrayList arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            if (!str2.contains("!")) {
                arrayList.add(str2);
            }
        }
        return TextUtils.join(ParameterizedMessage.ERROR_MSG_SEPARATOR, arrayList);
    }

    static File unpackLibraryBySoName(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            for (SoSource unpackLibrary : sSoSources) {
                File unpackLibrary2 = unpackLibrary.unpackLibrary(str);
                if (unpackLibrary2 != null) {
                    return unpackLibrary2;
                }
            }
            sSoSourcesLock.readLock().unlock();
            throw new FileNotFoundException(str);
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    private static void assertInitialized() {
        if (!isInitialized()) {
            throw new RuntimeException("SoLoader.init() not yet called");
        }
    }

    public static boolean isInitialized() {
        sSoSourcesLock.readLock().lock();
        try {
            return sSoSources != null;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static int getSoSourcesVersion() {
        return sSoSourcesVersion;
    }

    public static void prependSoSource(SoSource soSource) throws IOException {
        sSoSourcesLock.writeLock().lock();
        try {
            Log.d(TAG, "Prepending to SO sources: " + soSource);
            assertInitialized();
            soSource.prepare(makePrepareFlags());
            SoSource[] soSourceArr = new SoSource[(sSoSources.length + 1)];
            soSourceArr[0] = soSource;
            System.arraycopy(sSoSources, 0, soSourceArr, 1, sSoSources.length);
            sSoSources = soSourceArr;
            sSoSourcesVersion++;
            Log.d(TAG, "Prepended to SO sources: " + soSource);
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    public static String makeLdLibraryPath() {
        sSoSourcesLock.readLock().lock();
        try {
            assertInitialized();
            Log.d(TAG, "makeLdLibraryPath");
            ArrayList arrayList = new ArrayList();
            SoSource[] soSourceArr = sSoSources;
            if (soSourceArr != null) {
                for (SoSource addToLdLibraryPath : soSourceArr) {
                    addToLdLibraryPath.addToLdLibraryPath(arrayList);
                }
            }
            String join = TextUtils.join(ParameterizedMessage.ERROR_MSG_SEPARATOR, arrayList);
            Log.d(TAG, "makeLdLibraryPath final path: " + join);
            return join;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean areSoSourcesAbisSupported() {
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                String[] supportedAbis = SysUtil.getSupportedAbis();
                for (SoSource soSourceAbis : sSoSources) {
                    String[] soSourceAbis2 = soSourceAbis.getSoSourceAbis();
                    int length = soSourceAbis2.length;
                    int i = 0;
                    while (i < length) {
                        String str = soSourceAbis2[i];
                        boolean z = false;
                        for (int i2 = 0; i2 < supportedAbis.length && !z; i2++) {
                            z = str.equals(supportedAbis[i2]);
                        }
                        if (!z) {
                            Log.e(TAG, "abi not supported: " + str);
                        } else {
                            i++;
                        }
                    }
                }
                sSoSourcesLock.readLock().unlock();
                return true;
            }
            return false;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    private static class Api14Utils {
        private Api14Utils() {
        }

        public static String getClassLoaderLdLoadLibrary() {
            ClassLoader classLoader = SoLoader.class.getClassLoader();
            if (classLoader == null || (classLoader instanceof BaseDexClassLoader)) {
                try {
                    return (String) BaseDexClassLoader.class.getMethod("getLdLibraryPath", new Class[0]).invoke((BaseDexClassLoader) classLoader, new Object[0]);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot call getLdLibraryPath", e);
                }
            } else {
                throw new IllegalStateException("ClassLoader " + classLoader.getClass().getName() + " should be of type BaseDexClassLoader");
            }
        }
    }
}
