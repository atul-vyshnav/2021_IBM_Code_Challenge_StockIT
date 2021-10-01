package com.RNFetchBlob;

import android.content.res.AssetFileDescriptor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.RNFetchBlob.Utils.PathResolver;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

class RNFetchBlobFS {
    private static HashMap<String, RNFetchBlobFS> fileStreams = new HashMap<>();
    private DeviceEventManagerModule.RCTDeviceEventEmitter emitter;
    private String encoding = "base64";
    private ReactApplicationContext mCtx;
    private OutputStream writeStreamInstance = null;

    RNFetchBlobFS(ReactApplicationContext reactApplicationContext) {
        this.mCtx = reactApplicationContext;
        this.emitter = (DeviceEventManagerModule.RCTDeviceEventEmitter) reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c2 A[Catch:{ all -> 0x00e3, FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c7 A[Catch:{ all -> 0x00e3, FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void writeFile(java.lang.String r6, java.lang.String r7, java.lang.String r8, boolean r9, com.facebook.react.bridge.Promise r10) {
        /*
            java.lang.String r0 = "EUNSPECIFIED"
            java.lang.String r1 = "File '"
            java.lang.String r2 = "ENOENT"
            java.io.File r3 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r3.<init>(r6)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.io.File r4 = r3.getParentFile()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            boolean r5 = r3.exists()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r5 != 0) goto L_0x005b
            if (r4 == 0) goto L_0x003d
            boolean r5 = r4.exists()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r5 != 0) goto L_0x003d
            boolean r4 = r4.mkdirs()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r4 != 0) goto L_0x003d
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r7.<init>()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r8 = "Failed to create parent directory of '"
            r7.append(r8)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r7.append(r6)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r8 = "'"
            r7.append(r8)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r7 = r7.toString()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r10.reject((java.lang.String) r0, (java.lang.String) r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            return
        L_0x003d:
            boolean r4 = r3.createNewFile()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r4 != 0) goto L_0x005b
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r7.<init>()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r7.append(r1)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r7.append(r6)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r8 = "' does not exist and could not be created"
            r7.append(r8)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r7 = r7.toString()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r10.reject((java.lang.String) r2, (java.lang.String) r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            return
        L_0x005b:
            java.lang.String r4 = "uri"
            boolean r4 = r7.equalsIgnoreCase(r4)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r4 == 0) goto L_0x00cb
            java.lang.String r7 = normalizePath(r8)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.io.File r8 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.<init>(r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            boolean r4 = r8.exists()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            if (r4 != 0) goto L_0x0095
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.<init>()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r9 = "No such file '"
            r8.append(r9)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.append(r6)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r9 = "' ('"
            r8.append(r9)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.append(r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r7 = "')"
            r8.append(r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.lang.String r7 = r8.toString()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r10.reject((java.lang.String) r2, (java.lang.String) r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            return
        L_0x0095:
            r7 = 10240(0x2800, float:1.4349E-41)
            byte[] r7 = new byte[r7]     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x00be }
            r5.<init>(r8)     // Catch:{ all -> 0x00be }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x00ba }
            r8.<init>(r3, r9)     // Catch:{ all -> 0x00ba }
            r9 = 0
            r3 = 0
        L_0x00a6:
            int r4 = r5.read(r7)     // Catch:{ all -> 0x00b8 }
            if (r4 <= 0) goto L_0x00b1
            r8.write(r7, r9, r4)     // Catch:{ all -> 0x00b8 }
            int r3 = r3 + r4
            goto L_0x00a6
        L_0x00b1:
            r5.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            goto L_0x00db
        L_0x00b8:
            r7 = move-exception
            goto L_0x00bc
        L_0x00ba:
            r7 = move-exception
            r8 = r4
        L_0x00bc:
            r4 = r5
            goto L_0x00c0
        L_0x00be:
            r7 = move-exception
            r8 = r4
        L_0x00c0:
            if (r4 == 0) goto L_0x00c5
            r4.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
        L_0x00c5:
            if (r8 == 0) goto L_0x00ca
            r8.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
        L_0x00ca:
            throw r7     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
        L_0x00cb:
            byte[] r7 = stringToBytes(r8, r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.<init>(r3, r9)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r8.write(r7)     // Catch:{ all -> 0x00e3 }
            int r3 = r7.length     // Catch:{ all -> 0x00e3 }
            r8.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
        L_0x00db:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            r10.resolve(r7)     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            goto L_0x0108
        L_0x00e3:
            r7 = move-exception
            r8.close()     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
            throw r7     // Catch:{ FileNotFoundException -> 0x00f1, Exception -> 0x00e8 }
        L_0x00e8:
            r6 = move-exception
            java.lang.String r6 = r6.getLocalizedMessage()
            r10.reject((java.lang.String) r0, (java.lang.String) r6)
            goto L_0x0108
        L_0x00f1:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r1)
            r7.append(r6)
            java.lang.String r6 = "' does not exist and could not be created, or it is a directory"
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            r10.reject((java.lang.String) r2, (java.lang.String) r6)
        L_0x0108:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.writeFile(java.lang.String, java.lang.String, java.lang.String, boolean, com.facebook.react.bridge.Promise):void");
    }

    static void writeFile(String str, ReadableArray readableArray, boolean z, Promise promise) {
        FileOutputStream fileOutputStream;
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (!file.exists()) {
                if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                    promise.reject("ENOTDIR", "Failed to create parent directory of '" + str + "'");
                    return;
                } else if (!file.createNewFile()) {
                    promise.reject("ENOENT", "File '" + str + "' does not exist and could not be created");
                    return;
                }
            }
            fileOutputStream = new FileOutputStream(file, z);
            byte[] bArr = new byte[readableArray.size()];
            for (int i = 0; i < readableArray.size(); i++) {
                bArr[i] = (byte) readableArray.getInt(i);
            }
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            promise.resolve(Integer.valueOf(readableArray.size()));
        } catch (FileNotFoundException unused) {
            promise.reject("ENOENT", "File '" + str + "' does not exist and could not be created");
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        } catch (Throwable th) {
            fileOutputStream.close();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0076 A[Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0093 A[Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void readFile(java.lang.String r8, java.lang.String r9, com.facebook.react.bridge.Promise r10) {
        /*
            java.lang.String r0 = "bundle-assets://"
            java.lang.String r1 = normalizePath(r8)
            if (r1 == 0) goto L_0x0009
            r8 = r1
        L_0x0009:
            java.lang.String r2 = "EUNSPECIFIED"
            r3 = 0
            if (r1 == 0) goto L_0x003d
            boolean r4 = r1.startsWith(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            if (r4 == 0) goto L_0x003d
            java.lang.String r1 = ""
            java.lang.String r0 = r8.replace(r0, r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            com.facebook.react.bridge.ReactApplicationContext r1 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            android.content.res.AssetFileDescriptor r1 = r1.openFd(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            long r4 = r1.getLength()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r1 = (int) r4     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            byte[] r4 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            com.facebook.react.bridge.ReactApplicationContext r5 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            android.content.res.AssetManager r5 = r5.getAssets()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.io.InputStream r0 = r5.open(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r5 = r0.read(r4, r3, r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r0.close()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0074
        L_0x003d:
            if (r1 != 0) goto L_0x005b
            com.facebook.react.bridge.ReactApplicationContext r0 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            android.net.Uri r1 = android.net.Uri.parse(r8)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.io.InputStream r0 = r0.openInputStream(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r1 = r0.available()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            byte[] r4 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r5 = r0.read(r4)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r0.close()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0074
        L_0x005b:
            java.io.File r0 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r0.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            long r4 = r0.length()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r1 = (int) r4     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            byte[] r4 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r5.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r0 = r5.read(r4)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r5.close()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r5 = r0
        L_0x0074:
            if (r5 >= r1) goto L_0x0093
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.<init>()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.lang.String r0 = "Read only "
            r9.append(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.append(r5)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.lang.String r0 = " bytes of "
            r9.append(r0)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.append(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            java.lang.String r9 = r9.toString()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r10.reject((java.lang.String) r2, (java.lang.String) r9)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            return
        L_0x0093:
            java.lang.String r9 = r9.toLowerCase()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r0 = -1
            int r1 = r9.hashCode()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r5 = -1396204209(0xffffffffacc79d4f, float:-5.673385E-12)
            r6 = 2
            r7 = 1
            if (r1 == r5) goto L_0x00c3
            r5 = 3600241(0x36ef71, float:5.045012E-39)
            if (r1 == r5) goto L_0x00b8
            r5 = 93106001(0x58caf51, float:1.3229938E-35)
            if (r1 == r5) goto L_0x00ae
            goto L_0x00cc
        L_0x00ae:
            java.lang.String r1 = "ascii"
            boolean r9 = r9.equals(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            if (r9 == 0) goto L_0x00cc
            r0 = 1
            goto L_0x00cc
        L_0x00b8:
            java.lang.String r1 = "utf8"
            boolean r9 = r9.equals(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            if (r9 == 0) goto L_0x00cc
            r0 = 2
            goto L_0x00cc
        L_0x00c3:
            java.lang.String r1 = "base64"
            boolean r9 = r9.equals(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            if (r9 == 0) goto L_0x00cc
            r0 = 0
        L_0x00cc:
            if (r0 == 0) goto L_0x00f8
            if (r0 == r7) goto L_0x00e5
            if (r0 == r6) goto L_0x00dc
            java.lang.String r9 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r10.resolve(r9)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0151
        L_0x00dc:
            java.lang.String r9 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r10.resolve(r9)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0151
        L_0x00e5:
            com.facebook.react.bridge.WritableArray r9 = com.facebook.react.bridge.Arguments.createArray()     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r0 = r4.length     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
        L_0x00ea:
            if (r3 >= r0) goto L_0x00f4
            byte r1 = r4[r3]     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r9.pushInt(r1)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            int r3 = r3 + 1
            goto L_0x00ea
        L_0x00f4:
            r10.resolve(r9)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0151
        L_0x00f8:
            java.lang.String r9 = android.util.Base64.encodeToString(r4, r6)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            r10.resolve(r9)     // Catch:{ FileNotFoundException -> 0x0109, Exception -> 0x0100 }
            goto L_0x0151
        L_0x0100:
            r8 = move-exception
            java.lang.String r8 = r8.getLocalizedMessage()
            r10.reject((java.lang.String) r2, (java.lang.String) r8)
            goto L_0x0151
        L_0x0109:
            r9 = move-exception
            java.lang.String r9 = r9.getLocalizedMessage()
            java.lang.String r0 = "EISDIR"
            boolean r1 = r9.contains(r0)
            if (r1 == 0) goto L_0x0133
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Expecting a file but '"
            r1.append(r2)
            r1.append(r8)
            java.lang.String r8 = "' is a directory; "
            r1.append(r8)
            r1.append(r9)
            java.lang.String r8 = r1.toString()
            r10.reject((java.lang.String) r0, (java.lang.String) r8)
            goto L_0x0151
        L_0x0133:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No such file '"
            r0.append(r1)
            r0.append(r8)
            java.lang.String r8 = "'; "
            r0.append(r8)
            r0.append(r9)
            java.lang.String r8 = r0.toString()
            java.lang.String r9 = "ENOENT"
            r10.reject((java.lang.String) r9, (java.lang.String) r8)
        L_0x0151:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.readFile(java.lang.String, java.lang.String, com.facebook.react.bridge.Promise):void");
    }

    static Map<String, Object> getSystemfolders(ReactApplicationContext reactApplicationContext) {
        HashMap hashMap = new HashMap();
        hashMap.put("DocumentDir", reactApplicationContext.getFilesDir().getAbsolutePath());
        hashMap.put("CacheDir", reactApplicationContext.getCacheDir().getAbsolutePath());
        hashMap.put("DCIMDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
        hashMap.put("PictureDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        hashMap.put("MusicDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
        hashMap.put("DownloadDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        hashMap.put("MovieDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
        hashMap.put("RingtoneDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath());
        if (Environment.getExternalStorageState().equals("mounted")) {
            hashMap.put("SDCardDir", Environment.getExternalStorageDirectory().getAbsolutePath());
            File externalFilesDir = reactApplicationContext.getExternalFilesDir((String) null);
            if (externalFilesDir != null) {
                hashMap.put("SDCardApplicationDir", externalFilesDir.getParentFile().getAbsolutePath());
            } else {
                hashMap.put("SDCardApplicationDir", "");
            }
        }
        hashMap.put("MainBundleDir", reactApplicationContext.getApplicationInfo().dataDir);
        return hashMap;
    }

    public static void getSDCardDir(Promise promise) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            promise.resolve(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            promise.reject("RNFetchBlob.getSDCardDir", "External storage not mounted");
        }
    }

    public static void getSDCardApplicationDir(ReactApplicationContext reactApplicationContext, Promise promise) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                promise.resolve(reactApplicationContext.getExternalFilesDir((String) null).getParentFile().getAbsolutePath());
            } catch (Exception e) {
                promise.reject("RNFetchBlob.getSDCardApplicationDir", e.getLocalizedMessage());
            }
        } else {
            promise.reject("RNFetchBlob.getSDCardApplicationDir", "External storage not mounted");
        }
    }

    static String getTmpPath(String str) {
        return C0626RNFetchBlob.RCTContext.getFilesDir() + "/RNFetchBlobTmp_" + str;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0095 A[Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x010f A[Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readStream(java.lang.String r17, java.lang.String r18, int r19, int r20, java.lang.String r21) {
        /*
            r16 = this;
            r1 = r16
            r2 = r18
            r0 = r20
            r3 = r21
            java.lang.String r4 = "bundle-assets://"
            java.lang.String r5 = "base64"
            java.lang.String r6 = "error"
            java.lang.String r7 = normalizePath(r17)
            if (r7 == 0) goto L_0x0016
            r8 = r7
            goto L_0x0018
        L_0x0016:
            r8 = r17
        L_0x0018:
            boolean r9 = r2.equalsIgnoreCase(r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r9 == 0) goto L_0x0021
            r9 = 4095(0xfff, float:5.738E-42)
            goto L_0x0023
        L_0x0021:
            r9 = 4096(0x1000, float:5.74E-42)
        L_0x0023:
            if (r19 <= 0) goto L_0x0027
            r9 = r19
        L_0x0027:
            java.lang.String r10 = ""
            if (r7 == 0) goto L_0x0040
            boolean r11 = r8.startsWith(r4)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r11 == 0) goto L_0x0040
            com.facebook.react.bridge.ReactApplicationContext r7 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r4 = r8.replace(r4, r10)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.io.InputStream r4 = r7.open(r4)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            goto L_0x005b
        L_0x0040:
            if (r7 != 0) goto L_0x0051
            com.facebook.react.bridge.ReactApplicationContext r4 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.net.Uri r7 = android.net.Uri.parse(r8)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.io.InputStream r4 = r4.openInputStream(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            goto L_0x005b
        L_0x0051:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.io.File r7 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r7.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r4.<init>(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
        L_0x005b:
            byte[] r7 = new byte[r9]     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r11 = "utf8"
            boolean r11 = r2.equalsIgnoreCase(r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r12 = -1
            java.lang.String r13 = "data"
            r14 = 0
            if (r11 == 0) goto L_0x0095
            java.lang.String r5 = "UTF-8"
            java.nio.charset.Charset r5 = java.nio.charset.Charset.forName(r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.nio.charset.CharsetEncoder r5 = r5.newEncoder()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
        L_0x0074:
            int r9 = r4.read(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r9 == r12) goto L_0x00bc
            java.nio.ByteBuffer r11 = java.nio.ByteBuffer.wrap(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.nio.CharBuffer r11 = r11.asCharBuffer()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r5.encode(r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r11 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r11.<init>(r7, r14, r9)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r1.emitStreamEvent((java.lang.String) r3, (java.lang.String) r13, (java.lang.String) r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r0 <= 0) goto L_0x0093
            long r14 = (long) r0     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.os.SystemClock.sleep(r14)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
        L_0x0093:
            r14 = 0
            goto L_0x0074
        L_0x0095:
            java.lang.String r11 = "ascii"
            boolean r11 = r2.equalsIgnoreCase(r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r11 == 0) goto L_0x00be
        L_0x009d:
            int r5 = r4.read(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r5 == r12) goto L_0x00bc
            com.facebook.react.bridge.WritableArray r9 = com.facebook.react.bridge.Arguments.createArray()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r11 = 0
        L_0x00a8:
            if (r11 >= r5) goto L_0x00b2
            byte r14 = r7[r11]     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r9.pushInt(r14)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            int r11 = r11 + 1
            goto L_0x00a8
        L_0x00b2:
            r1.emitStreamEvent((java.lang.String) r3, (java.lang.String) r13, (com.facebook.react.bridge.WritableArray) r9)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r0 <= 0) goto L_0x009d
            long r14 = (long) r0     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.os.SystemClock.sleep(r14)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            goto L_0x009d
        L_0x00bc:
            r15 = 0
            goto L_0x00ef
        L_0x00be:
            boolean r5 = r2.equalsIgnoreCase(r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r5 == 0) goto L_0x00f1
        L_0x00c4:
            int r5 = r4.read(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            if (r5 == r12) goto L_0x00bc
            r11 = 2
            if (r5 >= r9) goto L_0x00db
            byte[] r14 = new byte[r5]     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r15 = 0
            java.lang.System.arraycopy(r7, r15, r14, r15, r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r5 = android.util.Base64.encodeToString(r14, r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r1.emitStreamEvent((java.lang.String) r3, (java.lang.String) r13, (java.lang.String) r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            goto L_0x00e3
        L_0x00db:
            r15 = 0
            java.lang.String r5 = android.util.Base64.encodeToString(r7, r11)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r1.emitStreamEvent((java.lang.String) r3, (java.lang.String) r13, (java.lang.String) r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
        L_0x00e3:
            if (r0 <= 0) goto L_0x00c4
            r19 = r13
            long r12 = (long) r0     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            android.os.SystemClock.sleep(r12)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r13 = r19
            r12 = -1
            goto L_0x00c4
        L_0x00ef:
            r14 = 0
            goto L_0x010d
        L_0x00f1:
            java.lang.String r0 = "EINVAL"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r5.<init>()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r7 = "Unrecognized encoding `"
            r5.append(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r5.append(r2)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r7 = "`, should be one of `base64`, `utf8`, `ascii`"
            r5.append(r7)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            java.lang.String r5 = r5.toString()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r1.emitStreamEvent(r3, r6, r0, r5)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            r14 = 1
        L_0x010d:
            if (r14 != 0) goto L_0x0114
            java.lang.String r0 = "end"
            r1.emitStreamEvent((java.lang.String) r3, (java.lang.String) r0, (java.lang.String) r10)     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
        L_0x0114:
            r4.close()     // Catch:{ FileNotFoundException -> 0x0138, Exception -> 0x0118 }
            goto L_0x0153
        L_0x0118:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to convert data to "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r2 = " encoded string. This might be because this encoding cannot be used for this data."
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            java.lang.String r4 = "EUNSPECIFIED"
            r1.emitStreamEvent(r3, r6, r4, r2)
            r0.printStackTrace()
            goto L_0x0153
        L_0x0138:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "No such file '"
            r0.append(r2)
            r0.append(r8)
            java.lang.String r2 = "'"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "ENOENT"
            r1.emitStreamEvent(r3, r6, r2, r0)
        L_0x0153:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.readStream(java.lang.String, java.lang.String, int, int, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public void writeStream(String str, String str2, boolean z, Callback callback) {
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (!file.exists()) {
                if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                    callback.invoke("ENOTDIR", "Failed to create parent directory of '" + str + "'");
                    return;
                } else if (!file.createNewFile()) {
                    callback.invoke("ENOENT", "File '" + str + "' does not exist and could not be created");
                    return;
                }
            } else if (file.isDirectory()) {
                callback.invoke("EISDIR", "Expecting a file but '" + str + "' is a directory");
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str, z);
            this.encoding = str2;
            String uuid = UUID.randomUUID().toString();
            fileStreams.put(uuid, this);
            this.writeStreamInstance = fileOutputStream;
            callback.invoke(null, null, uuid);
        } catch (Exception e) {
            callback.invoke("EUNSPECIFIED", "Failed to create write stream at path `" + str + "`; " + e.getLocalizedMessage());
        }
    }

    static void writeChunk(String str, String str2, Callback callback) {
        RNFetchBlobFS rNFetchBlobFS = fileStreams.get(str);
        try {
            rNFetchBlobFS.writeStreamInstance.write(stringToBytes(str2, rNFetchBlobFS.encoding));
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void writeArrayChunk(String str, ReadableArray readableArray, Callback callback) {
        try {
            OutputStream outputStream = fileStreams.get(str).writeStreamInstance;
            byte[] bArr = new byte[readableArray.size()];
            for (int i = 0; i < readableArray.size(); i++) {
                bArr[i] = (byte) readableArray.getInt(i);
            }
            outputStream.write(bArr);
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void closeStream(String str, Callback callback) {
        try {
            OutputStream outputStream = fileStreams.get(str).writeStreamInstance;
            fileStreams.remove(str);
            outputStream.close();
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void unlink(String str, Callback callback) {
        try {
            deleteRecursive(new File(normalizePath(str)));
            callback.invoke(null, true);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage(), false);
        }
    }

    private static void deleteRecursive(File file) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File deleteRecursive : listFiles) {
                    deleteRecursive(deleteRecursive);
                }
            } else {
                throw new NullPointerException("Received null trying to list files of directory '" + file + "'");
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete '" + file + "'");
        }
    }

    static void mkdir(String str, Promise promise) {
        File file = new File(str);
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(file.isDirectory() ? "Folder" : "File");
            sb.append(" '");
            sb.append(str);
            sb.append("' already exists");
            promise.reject("EEXIST", sb.toString());
            return;
        }
        try {
            if (!file.mkdirs()) {
                promise.reject("EUNSPECIFIED", "mkdir failed to create some or all directories in '" + str + "'");
                return;
            }
            promise.resolve(true);
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c2 A[SYNTHETIC, Splitter:B:44:0x00c2] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ca A[Catch:{ Exception -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f4 A[SYNTHETIC, Splitter:B:56:0x00f4] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00fc A[Catch:{ Exception -> 0x00f8 }] */
    /* renamed from: cp */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void m16cp(java.lang.String r6, java.lang.String r7, com.facebook.react.bridge.Callback r8) {
        /*
            java.lang.String r0 = ""
            java.lang.String r6 = normalizePath(r6)
            r1 = 0
            r2 = 1
            r3 = 0
            boolean r4 = isPathExists(r6)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            if (r4 != 0) goto L_0x002d
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.<init>()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r5 = "Source file at path`"
            r4.append(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.append(r6)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r6 = "` does not exist"
            r4.append(r6)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r7[r3] = r6     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r8.invoke(r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            return
        L_0x002d:
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.<init>(r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            boolean r4 = r4.exists()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            if (r4 != 0) goto L_0x0061
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.<init>(r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            boolean r4 = r4.createNewFile()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            if (r4 != 0) goto L_0x0061
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.<init>()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r5 = "Destination file at '"
            r4.append(r5)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r4.append(r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r7 = "' already exists"
            r4.append(r7)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.lang.String r7 = r4.toString()     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r6[r3] = r7     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r8.invoke(r6)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            return
        L_0x0061:
            java.io.InputStream r6 = inputStreamFromPath(r6)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a3, all -> 0x009e }
            r4.<init>(r7)     // Catch:{ Exception -> 0x00a3, all -> 0x009e }
            r7 = 10240(0x2800, float:1.4349E-41)
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x009c, all -> 0x009a }
        L_0x006e:
            int r1 = r6.read(r7)     // Catch:{ Exception -> 0x009c, all -> 0x009a }
            if (r1 <= 0) goto L_0x0078
            r4.write(r7, r3, r1)     // Catch:{ Exception -> 0x009c, all -> 0x009a }
            goto L_0x006e
        L_0x0078:
            if (r6 == 0) goto L_0x0080
            r6.close()     // Catch:{ Exception -> 0x007e }
            goto L_0x0080
        L_0x007e:
            r6 = move-exception
            goto L_0x0086
        L_0x0080:
            r4.close()     // Catch:{ Exception -> 0x007e }
            r6 = r0
            goto L_0x00e1
        L_0x0086:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r0)
            java.lang.String r6 = r6.getLocalizedMessage()
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            goto L_0x00e1
        L_0x009a:
            r7 = move-exception
            goto L_0x00a0
        L_0x009c:
            r7 = move-exception
            goto L_0x00a5
        L_0x009e:
            r7 = move-exception
            r4 = r1
        L_0x00a0:
            r1 = r6
            r6 = r7
            goto L_0x00f2
        L_0x00a3:
            r7 = move-exception
            r4 = r1
        L_0x00a5:
            r1 = r6
            r6 = r7
            goto L_0x00ad
        L_0x00a8:
            r6 = move-exception
            r4 = r1
            goto L_0x00f2
        L_0x00ab:
            r6 = move-exception
            r4 = r1
        L_0x00ad:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f1 }
            r7.<init>()     // Catch:{ all -> 0x00f1 }
            r7.append(r0)     // Catch:{ all -> 0x00f1 }
            java.lang.String r6 = r6.getLocalizedMessage()     // Catch:{ all -> 0x00f1 }
            r7.append(r6)     // Catch:{ all -> 0x00f1 }
            java.lang.String r6 = r7.toString()     // Catch:{ all -> 0x00f1 }
            if (r1 == 0) goto L_0x00c8
            r1.close()     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00c8
        L_0x00c6:
            r7 = move-exception
            goto L_0x00ce
        L_0x00c8:
            if (r4 == 0) goto L_0x00e1
            r4.close()     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00e1
        L_0x00ce:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r6 = r7.getLocalizedMessage()
            r1.append(r6)
            java.lang.String r6 = r1.toString()
        L_0x00e1:
            if (r6 == r0) goto L_0x00eb
            java.lang.Object[] r7 = new java.lang.Object[r2]
            r7[r3] = r6
            r8.invoke(r7)
            goto L_0x00f0
        L_0x00eb:
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8.invoke(r6)
        L_0x00f0:
            return
        L_0x00f1:
            r6 = move-exception
        L_0x00f2:
            if (r1 == 0) goto L_0x00fa
            r1.close()     // Catch:{ Exception -> 0x00f8 }
            goto L_0x00fa
        L_0x00f8:
            r7 = move-exception
            goto L_0x0100
        L_0x00fa:
            if (r4 == 0) goto L_0x0103
            r4.close()     // Catch:{ Exception -> 0x00f8 }
            goto L_0x0103
        L_0x0100:
            r7.getLocalizedMessage()
        L_0x0103:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.m16cp(java.lang.String, java.lang.String, com.facebook.react.bridge.Callback):void");
    }

    /* renamed from: mv */
    static void m19mv(String str, String str2, Callback callback) {
        File file = new File(str);
        if (!file.exists()) {
            callback.invoke("Source file at path `" + str + "` does not exist");
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    file.delete();
                    callback.invoke(new Object[0]);
                    return;
                }
            }
        } catch (FileNotFoundException unused) {
            callback.invoke("Source file not found.");
        } catch (Exception e) {
            callback.invoke(e.toString());
        }
    }

    static void exists(String str, Callback callback) {
        if (isAsset(str)) {
            try {
                C0626RNFetchBlob.RCTContext.getAssets().openFd(str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
                callback.invoke(true, false);
            } catch (IOException unused) {
                callback.invoke(false, false);
            }
        } else {
            String normalizePath = normalizePath(str);
            if (normalizePath != null) {
                callback.invoke(Boolean.valueOf(new File(normalizePath).exists()), Boolean.valueOf(new File(normalizePath).isDirectory()));
                return;
            }
            callback.invoke(false, false);
        }
    }

    /* renamed from: ls */
    static void m18ls(String str, Promise promise) {
        try {
            String normalizePath = normalizePath(str);
            File file = new File(normalizePath);
            if (!file.exists()) {
                promise.reject("ENOENT", "No such file '" + normalizePath + "'");
            } else if (!file.isDirectory()) {
                promise.reject("ENOTDIR", "Not a directory '" + normalizePath + "'");
            } else {
                String[] list = new File(normalizePath).list();
                WritableArray createArray = Arguments.createArray();
                for (String pushString : list) {
                    createArray.pushString(pushString);
                }
                promise.resolve(createArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void slice(String str, String str2, int i, int i2, String str3, Promise promise) {
        try {
            String normalizePath = normalizePath(str);
            File file = new File(normalizePath);
            if (file.isDirectory()) {
                promise.reject("EISDIR", "Expecting a file but '" + normalizePath + "' is a directory");
            } else if (!file.exists()) {
                promise.reject("ENOENT", "No such file '" + normalizePath + "'");
            } else {
                int length = (int) file.length();
                int min = Math.min(length, i2) - i;
                FileInputStream fileInputStream = new FileInputStream(new File(normalizePath));
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
                int skip = (int) fileInputStream.skip((long) i);
                if (skip != i) {
                    promise.reject("EUNSPECIFIED", "Skipped " + skip + " instead of the specified " + i + " bytes, size is " + length);
                    return;
                }
                byte[] bArr = new byte[10240];
                int i3 = 0;
                while (true) {
                    if (i3 >= min) {
                        break;
                    }
                    int read = fileInputStream.read(bArr, 0, 10240);
                    int i4 = min - i3;
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, Math.min(i4, read));
                    i3 += read;
                }
                fileInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                promise.resolve(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void lstat(String str, final Callback callback) {
        String normalizePath = normalizePath(str);
        new AsyncTask<String, Integer, Integer>() {
            /* access modifiers changed from: protected */
            public Integer doInBackground(String... strArr) {
                WritableArray createArray = Arguments.createArray();
                if (strArr[0] == null) {
                    callback.invoke("the path specified for lstat is either `null` or `undefined`.");
                    return 0;
                }
                File file = new File(strArr[0]);
                if (!file.exists()) {
                    callback.invoke("failed to lstat path `" + strArr[0] + "` because it does not exist or it is not a folder");
                    return 0;
                }
                if (file.isDirectory()) {
                    for (String str : file.list()) {
                        createArray.pushMap(RNFetchBlobFS.statFile(file.getPath() + "/" + str));
                    }
                } else {
                    createArray.pushMap(RNFetchBlobFS.statFile(file.getAbsolutePath()));
                }
                callback.invoke(null, createArray);
                return 0;
            }
        }.execute(new String[]{normalizePath});
    }

    static void stat(String str, Callback callback) {
        try {
            String normalizePath = normalizePath(str);
            WritableMap statFile = statFile(normalizePath);
            if (statFile == null) {
                callback.invoke("failed to stat path `" + normalizePath + "` because it does not exist or it is not a folder", null);
                return;
            }
            callback.invoke(null, statFile);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static WritableMap statFile(String str) {
        try {
            String normalizePath = normalizePath(str);
            WritableMap createMap = Arguments.createMap();
            if (isAsset(normalizePath)) {
                String replace = normalizePath.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, "");
                AssetFileDescriptor openFd = C0626RNFetchBlob.RCTContext.getAssets().openFd(replace);
                createMap.putString("filename", replace);
                createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, normalizePath);
                createMap.putString("type", UriUtil.LOCAL_ASSET_SCHEME);
                createMap.putString("size", String.valueOf(openFd.getLength()));
                createMap.putInt("lastModified", 0);
            } else {
                File file = new File(normalizePath);
                if (!file.exists()) {
                    return null;
                }
                createMap.putString("filename", file.getName());
                createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, file.getPath());
                createMap.putString("type", file.isDirectory() ? "directory" : UriUtil.LOCAL_FILE_SCHEME);
                createMap.putString("size", String.valueOf(file.length()));
                createMap.putString("lastModified", String.valueOf(file.lastModified()));
            }
            return createMap;
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void scanFile(String[] strArr, String[] strArr2, final Callback callback) {
        try {
            MediaScannerConnection.scanFile(this.mCtx, strArr, strArr2, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    callback.invoke(null, true);
                }
            });
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage(), null);
        }
    }

    static void hash(String str, String str2, Promise promise) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("md5", MessageDigestAlgorithms.MD5);
            hashMap.put("sha1", MessageDigestAlgorithms.SHA_1);
            hashMap.put("sha224", "SHA-224");
            hashMap.put("sha256", MessageDigestAlgorithms.SHA_256);
            hashMap.put("sha384", MessageDigestAlgorithms.SHA_384);
            hashMap.put("sha512", MessageDigestAlgorithms.SHA_512);
            if (!hashMap.containsKey(str2)) {
                promise.reject("EINVAL", "Invalid algorithm '" + str2 + "', must be one of md5, sha1, sha224, sha256, sha384, sha512");
                return;
            }
            File file = new File(str);
            if (file.isDirectory()) {
                promise.reject("EISDIR", "Expecting a file but '" + str + "' is a directory");
            } else if (!file.exists()) {
                promise.reject("ENOENT", "No such file '" + str + "'");
            } else {
                MessageDigest instance = MessageDigest.getInstance((String) hashMap.get(str2));
                FileInputStream fileInputStream = new FileInputStream(str);
                byte[] bArr = new byte[1048576];
                if (file.length() != 0) {
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        instance.update(bArr, 0, read);
                    }
                }
                StringBuilder sb = new StringBuilder();
                byte[] digest = instance.digest();
                int length = digest.length;
                for (int i = 0; i < length; i++) {
                    sb.append(String.format("%02x", new Object[]{Byte.valueOf(digest[i])}));
                }
                promise.resolve(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void createFile(String str, String str2, String str3, Promise promise) {
        try {
            File file = new File(str);
            boolean createNewFile = file.createNewFile();
            if (str3.equals("uri")) {
                File file2 = new File(str2.replace(RNFetchBlobConst.FILE_PREFIX, ""));
                if (!file2.exists()) {
                    promise.reject("ENOENT", "Source file : " + str2 + " does not exist");
                    return;
                }
                FileInputStream fileInputStream = new FileInputStream(file2);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[10240];
                for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                    fileOutputStream.write(bArr, 0, read);
                }
                fileInputStream.close();
                fileOutputStream.close();
            } else if (!createNewFile) {
                promise.reject("EEXIST", "File `" + str + "` already exists");
                return;
            } else {
                new FileOutputStream(file).write(stringToBytes(str2, str3));
            }
            promise.resolve(str);
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void createFileASCII(String str, ReadableArray readableArray, Promise promise) {
        try {
            File file = new File(str);
            if (!file.createNewFile()) {
                promise.reject("EEXIST", "File at path `" + str + "` already exists");
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[readableArray.size()];
            for (int i = 0; i < readableArray.size(); i++) {
                bArr[i] = (byte) readableArray.getInt(i);
            }
            fileOutputStream.write(bArr);
            promise.resolve(str);
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    /* renamed from: df */
    static void m17df(Callback callback) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        WritableMap createMap = Arguments.createMap();
        if (Build.VERSION.SDK_INT >= 18) {
            createMap.putString("internal_free", String.valueOf(statFs.getFreeBytes()));
            createMap.putString("internal_total", String.valueOf(statFs.getTotalBytes()));
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getPath());
            createMap.putString("external_free", String.valueOf(statFs2.getFreeBytes()));
            createMap.putString("external_total", String.valueOf(statFs2.getTotalBytes()));
        }
        callback.invoke(null, createMap);
    }

    static void removeSession(ReadableArray readableArray, final Callback callback) {
        new AsyncTask<ReadableArray, Integer, Integer>() {
            /* access modifiers changed from: protected */
            public Integer doInBackground(ReadableArray... readableArrayArr) {
                try {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < readableArrayArr[0].size(); i++) {
                        String string = readableArrayArr[0].getString(i);
                        File file = new File(string);
                        if (file.exists() && !file.delete()) {
                            arrayList.add(string);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        callback.invoke(null, true);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Failed to delete: ");
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            sb.append((String) it.next());
                            sb.append(", ");
                        }
                        callback.invoke(sb.toString());
                    }
                } catch (Exception e) {
                    callback.invoke(e.getLocalizedMessage());
                }
                return Integer.valueOf(readableArrayArr[0].size());
            }
        }.execute(new ReadableArray[]{readableArray});
    }

    private static byte[] stringToBytes(String str, String str2) {
        if (str2.equalsIgnoreCase("ascii")) {
            return str.getBytes(Charset.forName("US-ASCII"));
        }
        if (str2.toLowerCase().contains("base64")) {
            return Base64.decode(str, 2);
        }
        if (str2.equalsIgnoreCase(RNFetchBlobConst.RNFB_RESPONSE_UTF8)) {
            return str.getBytes(Charset.forName("UTF-8"));
        }
        return str.getBytes(Charset.forName("US-ASCII"));
    }

    private void emitStreamEvent(String str, String str2, String str3) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putString("detail", str3);
        this.emitter.emit(str, createMap);
    }

    private void emitStreamEvent(String str, String str2, WritableArray writableArray) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putArray("detail", writableArray);
        this.emitter.emit(str, createMap);
    }

    private void emitStreamEvent(String str, String str2, String str3, String str4) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putString("code", str3);
        createMap.putString("detail", str4);
        this.emitter.emit(str, createMap);
    }

    private static InputStream inputStreamFromPath(String str) throws IOException {
        if (str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET)) {
            return C0626RNFetchBlob.RCTContext.getAssets().open(str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
        }
        return new FileInputStream(new File(str));
    }

    private static boolean isPathExists(String str) {
        if (!str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET)) {
            return new File(str).exists();
        }
        try {
            C0626RNFetchBlob.RCTContext.getAssets().open(str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    static boolean isAsset(String str) {
        return str != null && str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET);
    }

    static String normalizePath(String str) {
        if (str == null) {
            return null;
        }
        if (!str.matches("\\w+\\:.*")) {
            return str;
        }
        if (str.startsWith("file://")) {
            return str.replace("file://", "");
        }
        Uri parse = Uri.parse(str);
        if (str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET)) {
            return str;
        }
        return PathResolver.getRealPathFromURI(C0626RNFetchBlob.RCTContext, parse);
    }
}
