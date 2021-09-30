package expo.modules.updates.loader;

import android.content.Context;
import android.net.Uri;
import com.amplitude.api.DeviceInfo;
import com.thunkable.live.BuildConfig;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.launcher.NoDatabaseLauncher;
import expo.modules.updates.launcher.SelectionPolicyFilterAware;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.manifest.ManifestFactory;
import expo.modules.updates.manifest.ManifestResponse;
import expo.modules.updates.p020db.entity.AssetEntity;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import okhttp3.C3401Response;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

public class FileDownloader {
    /* access modifiers changed from: private */
    public static final String TAG = FileDownloader.class.getSimpleName();
    private OkHttpClient mClient;

    public interface AssetDownloadCallback {
        void onFailure(Exception exc, AssetEntity assetEntity);

        void onSuccess(AssetEntity assetEntity, boolean z);
    }

    public interface FileDownloadCallback {
        void onFailure(Exception exc);

        void onSuccess(File file, byte[] bArr);
    }

    public interface ManifestDownloadCallback {
        void onFailure(String str, Exception exc);

        void onSuccess(Manifest manifest);
    }

    public FileDownloader(Context context) {
        this.mClient = new OkHttpClient.Builder().cache(getCache(context)).build();
    }

    private Cache getCache(Context context) {
        return new Cache(getCacheDirectory(context), (long) 52428800);
    }

    private File getCacheDirectory(Context context) {
        return new File(context.getCacheDir(), "okhttp");
    }

    public void downloadFileToPath(Request request, final File file, final FileDownloadCallback fileDownloadCallback) {
        downloadData(request, new Callback() {
            public void onFailure(Call call, IOException iOException) {
                fileDownloadCallback.onFailure(iOException);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0047, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
                if (r4 != null) goto L_0x004a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
                r4.close();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x0052, code lost:
                throw r0;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onResponse(okhttp3.Call r4, okhttp3.C3401Response r5) throws java.io.IOException {
                /*
                    r3 = this;
                    boolean r4 = r5.isSuccessful()
                    if (r4 != 0) goto L_0x002a
                    expo.modules.updates.loader.FileDownloader$FileDownloadCallback r4 = r4
                    java.lang.Exception r0 = new java.lang.Exception
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    java.lang.String r2 = "Network request failed: "
                    r1.append(r2)
                    okhttp3.ResponseBody r5 = r5.body()
                    java.lang.String r5 = r5.string()
                    r1.append(r5)
                    java.lang.String r5 = r1.toString()
                    r0.<init>(r5)
                    r4.onFailure(r0)
                    return
                L_0x002a:
                    okhttp3.ResponseBody r4 = r5.body()     // Catch:{ Exception -> 0x0053 }
                    java.io.InputStream r4 = r4.byteStream()     // Catch:{ Exception -> 0x0053 }
                    java.io.File r5 = r3     // Catch:{ all -> 0x0045 }
                    byte[] r5 = expo.modules.updates.UpdatesUtils.sha256AndWriteToFile(r4, r5)     // Catch:{ all -> 0x0045 }
                    expo.modules.updates.loader.FileDownloader$FileDownloadCallback r0 = r4     // Catch:{ all -> 0x0045 }
                    java.io.File r1 = r3     // Catch:{ all -> 0x0045 }
                    r0.onSuccess(r1, r5)     // Catch:{ all -> 0x0045 }
                    if (r4 == 0) goto L_0x0077
                    r4.close()     // Catch:{ Exception -> 0x0053 }
                    goto L_0x0077
                L_0x0045:
                    r5 = move-exception
                    throw r5     // Catch:{ all -> 0x0047 }
                L_0x0047:
                    r0 = move-exception
                    if (r4 == 0) goto L_0x0052
                    r4.close()     // Catch:{ all -> 0x004e }
                    goto L_0x0052
                L_0x004e:
                    r4 = move-exception
                    r5.addSuppressed(r4)     // Catch:{ Exception -> 0x0053 }
                L_0x0052:
                    throw r0     // Catch:{ Exception -> 0x0053 }
                L_0x0053:
                    r4 = move-exception
                    java.lang.String r5 = expo.modules.updates.loader.FileDownloader.TAG
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "Failed to download file to destination "
                    r0.append(r1)
                    java.io.File r1 = r3
                    java.lang.String r1 = r1.toString()
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    android.util.Log.e(r5, r0, r4)
                    expo.modules.updates.loader.FileDownloader$FileDownloadCallback r5 = r4
                    r5.onFailure(r4)
                L_0x0077:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.FileDownloader.C30881.onResponse(okhttp3.Call, okhttp3.Response):void");
            }
        });
    }

    public void downloadManifest(final UpdatesConfiguration updatesConfiguration, JSONObject jSONObject, Context context, final ManifestDownloadCallback manifestDownloadCallback) {
        try {
            downloadData(setHeadersForManifestUrl(updatesConfiguration, jSONObject, context), new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    ManifestDownloadCallback manifestDownloadCallback = manifestDownloadCallback;
                    manifestDownloadCallback.onFailure("Failed to download manifest from URL: " + updatesConfiguration.getUpdateUrl(), iOException);
                }

                /* JADX WARNING: Removed duplicated region for block: B:15:0x0055 A[Catch:{ Exception -> 0x0088 }] */
                /* JADX WARNING: Removed duplicated region for block: B:16:0x005a A[Catch:{ Exception -> 0x0088 }] */
                /* JADX WARNING: Removed duplicated region for block: B:18:0x0062 A[Catch:{ Exception -> 0x0088 }] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onResponse(okhttp3.Call r7, final okhttp3.C3401Response r8) throws java.io.IOException {
                    /*
                        r6 = this;
                        java.lang.String r7 = "manifestString"
                        boolean r0 = r8.isSuccessful()
                        if (r0 != 0) goto L_0x0032
                        expo.modules.updates.loader.FileDownloader$ManifestDownloadCallback r7 = r5
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        r0.<init>()
                        java.lang.String r1 = "Failed to download manifest from URL: "
                        r0.append(r1)
                        expo.modules.updates.UpdatesConfiguration r1 = r2
                        android.net.Uri r1 = r1.getUpdateUrl()
                        r0.append(r1)
                        java.lang.String r0 = r0.toString()
                        java.lang.Exception r1 = new java.lang.Exception
                        okhttp3.ResponseBody r8 = r8.body()
                        java.lang.String r8 = r8.string()
                        r1.<init>(r8)
                        r7.onFailure(r0, r1)
                        return
                    L_0x0032:
                        okhttp3.ResponseBody r0 = r8.body()     // Catch:{ Exception -> 0x0088 }
                        java.lang.String r0 = r0.string()     // Catch:{ Exception -> 0x0088 }
                        expo.modules.updates.UpdatesConfiguration r1 = r2     // Catch:{ Exception -> 0x0088 }
                        org.json.JSONObject r1 = expo.modules.updates.loader.FileDownloader.extractUpdateResponseJson(r0, r1)     // Catch:{ Exception -> 0x0088 }
                        boolean r2 = r1.has(r7)     // Catch:{ Exception -> 0x0088 }
                        java.lang.String r3 = "signature"
                        r4 = 0
                        if (r2 == 0) goto L_0x0051
                        boolean r2 = r1.has(r3)     // Catch:{ Exception -> 0x0088 }
                        if (r2 == 0) goto L_0x0051
                        r2 = 1
                        goto L_0x0052
                    L_0x0051:
                        r2 = 0
                    L_0x0052:
                        r5 = 0
                        if (r2 == 0) goto L_0x005a
                        java.lang.String r3 = r1.optString(r3, r5)     // Catch:{ Exception -> 0x0088 }
                        goto L_0x0060
                    L_0x005a:
                        java.lang.String r3 = "expo-manifest-signature"
                        java.lang.String r3 = r8.header(r3, r5)     // Catch:{ Exception -> 0x0088 }
                    L_0x0060:
                        if (r2 == 0) goto L_0x0066
                        java.lang.String r0 = r1.getString(r7)     // Catch:{ Exception -> 0x0088 }
                    L_0x0066:
                        org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x0088 }
                        r7.<init>(r0)     // Catch:{ Exception -> 0x0088 }
                        java.lang.String r1 = "UNSIGNED"
                        boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0088 }
                        if (r3 == 0) goto L_0x0080
                        if (r1 != 0) goto L_0x0080
                        expo.modules.updates.loader.FileDownloader r1 = expo.modules.updates.loader.FileDownloader.this     // Catch:{ Exception -> 0x0088 }
                        expo.modules.updates.loader.FileDownloader$2$1 r2 = new expo.modules.updates.loader.FileDownloader$2$1     // Catch:{ Exception -> 0x0088 }
                        r2.<init>(r7, r8)     // Catch:{ Exception -> 0x0088 }
                        expo.modules.updates.loader.Crypto.verifyPublicRSASignature(r0, r3, r1, r2)     // Catch:{ Exception -> 0x0088 }
                        goto L_0x0090
                    L_0x0080:
                        expo.modules.updates.UpdatesConfiguration r0 = r2     // Catch:{ Exception -> 0x0088 }
                        expo.modules.updates.loader.FileDownloader$ManifestDownloadCallback r1 = r5     // Catch:{ Exception -> 0x0088 }
                        expo.modules.updates.loader.FileDownloader.createManifest(r7, r8, r4, r0, r1)     // Catch:{ Exception -> 0x0088 }
                        goto L_0x0090
                    L_0x0088:
                        r7 = move-exception
                        expo.modules.updates.loader.FileDownloader$ManifestDownloadCallback r8 = r5
                        java.lang.String r0 = "Failed to parse manifest data"
                        r8.onFailure(r0, r7)
                    L_0x0090:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.FileDownloader.C30892.onResponse(okhttp3.Call, okhttp3.Response):void");
                }
            });
        } catch (Exception e) {
            manifestDownloadCallback.onFailure("Failed to download manifest from URL " + updatesConfiguration.getUpdateUrl().toString(), e);
        }
    }

    /* access modifiers changed from: private */
    public static void createManifest(JSONObject jSONObject, C3401Response response, boolean z, UpdatesConfiguration updatesConfiguration, ManifestDownloadCallback manifestDownloadCallback) throws JSONException {
        if (updatesConfiguration.expectsSignedManifest()) {
            jSONObject.put("isVerified", z);
        }
        Manifest manifest = ManifestFactory.getManifest(jSONObject, new ManifestResponse(response), updatesConfiguration);
        if (!SelectionPolicyFilterAware.matchesFilters(manifest.getUpdateEntity(), manifest.getManifestFilters())) {
            manifestDownloadCallback.onFailure("Downloaded manifest is invalid; provides filters that do not match its content", new Exception("Downloaded manifest is invalid; provides filters that do not match its content"));
        } else {
            manifestDownloadCallback.onSuccess(manifest);
        }
    }

    public void downloadAsset(final AssetEntity assetEntity, File file, UpdatesConfiguration updatesConfiguration, final AssetDownloadCallback assetDownloadCallback) {
        if (assetEntity.url == null) {
            assetDownloadCallback.onFailure(new Exception("Could not download asset " + assetEntity.key + " with no URL"), assetEntity);
            return;
        }
        final String createFilenameForAsset = UpdatesUtils.createFilenameForAsset(assetEntity);
        File file2 = new File(file, createFilenameForAsset);
        if (file2.exists()) {
            assetEntity.relativePath = createFilenameForAsset;
            assetDownloadCallback.onSuccess(assetEntity, false);
            return;
        }
        try {
            downloadFileToPath(setHeadersForUrl(assetEntity.url, updatesConfiguration), file2, new FileDownloadCallback() {
                public void onFailure(Exception exc) {
                    assetDownloadCallback.onFailure(exc, assetEntity);
                }

                public void onSuccess(File file, byte[] bArr) {
                    assetEntity.downloadTime = new Date();
                    assetEntity.relativePath = createFilenameForAsset;
                    assetEntity.hash = bArr;
                    assetDownloadCallback.onSuccess(assetEntity, true);
                }
            });
        } catch (Exception e) {
            assetDownloadCallback.onFailure(e, assetEntity);
        }
    }

    public void downloadData(Request request, Callback callback) {
        downloadData(request, callback, false);
    }

    /* access modifiers changed from: private */
    public void downloadData(final Request request, final Callback callback, final boolean z) {
        this.mClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException iOException) {
                if (z) {
                    callback.onFailure(call, iOException);
                } else {
                    FileDownloader.this.downloadData(request, callback, true);
                }
            }

            public void onResponse(Call call, C3401Response response) throws IOException {
                callback.onResponse(call, response);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005c, code lost:
        throw new java.io.IOException("No compatible manifest found. SDK Versions supported: " + r7.getSdkVersion() + " Provided manifestString: " + r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005d, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0074, code lost:
        throw new java.io.IOException("Manifest string is not a valid JSONObject or JSONArray: " + r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
        r0 = new org.json.JSONArray(r6);
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0010, code lost:
        if (r1 < r0.length()) goto L_0x0012;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0012, code lost:
        r2 = r0.getJSONObject(r1);
        r3 = r2.getString(expo.modules.updates.UpdatesConfiguration.UPDATES_CONFIGURATION_SDK_VERSION_KEY);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0020, code lost:
        if (r7.getSdkVersion() == null) goto L_0x0037;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0006 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject extractUpdateResponseJson(java.lang.String r6, expo.modules.updates.UpdatesConfiguration r7) throws java.io.IOException {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0006 }
            r0.<init>(r6)     // Catch:{ JSONException -> 0x0006 }
            return r0
        L_0x0006:
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ JSONException -> 0x005d }
            r0.<init>(r6)     // Catch:{ JSONException -> 0x005d }
            r1 = 0
        L_0x000c:
            int r2 = r0.length()     // Catch:{ JSONException -> 0x005d }
            if (r1 >= r2) goto L_0x003a
            org.json.JSONObject r2 = r0.getJSONObject(r1)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r3 = "sdkVersion"
            java.lang.String r3 = r2.getString(r3)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r4 = r7.getSdkVersion()     // Catch:{ JSONException -> 0x005d }
            if (r4 == 0) goto L_0x0037
            java.lang.String r4 = r7.getSdkVersion()     // Catch:{ JSONException -> 0x005d }
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ JSONException -> 0x005d }
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch:{ JSONException -> 0x005d }
            boolean r3 = r4.contains(r3)     // Catch:{ JSONException -> 0x005d }
            if (r3 == 0) goto L_0x0037
            return r2
        L_0x0037:
            int r1 = r1 + 1
            goto L_0x000c
        L_0x003a:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "No compatible manifest found. SDK Versions supported: "
            r1.append(r2)
            java.lang.String r7 = r7.getSdkVersion()
            r1.append(r7)
            java.lang.String r7 = " Provided manifestString: "
            r1.append(r7)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6)
            throw r0
        L_0x005d:
            r7 = move-exception
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Manifest string is not a valid JSONObject or JSONArray: "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6, r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.FileDownloader.extractUpdateResponseJson(java.lang.String, expo.modules.updates.UpdatesConfiguration):org.json.JSONObject");
    }

    private static Request setHeadersForUrl(Uri uri, UpdatesConfiguration updatesConfiguration) {
        Request.Builder header = new Request.Builder().url(uri.toString()).header("Expo-Platform", DeviceInfo.OS_NAME).header("Expo-API-Version", BuildConfig.VERSION_NAME).header("Expo-Updates-Environment", "BARE");
        for (Map.Entry next : updatesConfiguration.getRequestHeaders().entrySet()) {
            header.header((String) next.getKey(), (String) next.getValue());
        }
        return header.build();
    }

    static Request setHeadersForManifestUrl(UpdatesConfiguration updatesConfiguration, JSONObject jSONObject, Context context) {
        Request.Builder builder;
        Request.Builder url = new Request.Builder().url(updatesConfiguration.getUpdateUrl().toString());
        if (jSONObject != null) {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                url.header(next, jSONObject.optString(next, ""));
            }
        }
        Request.Builder header = url.header("Accept", "application/expo+json,application/json").header("Expo-Platform", DeviceInfo.OS_NAME).header("Expo-API-Version", BuildConfig.VERSION_NAME).header("Expo-Updates-Environment", "BARE").header("Expo-JSON-Error", "true").header("Expo-Accept-Signature", String.valueOf(updatesConfiguration.expectsSignedManifest()));
        if (updatesConfiguration.usesLegacyManifest()) {
            header = header.cacheControl(CacheControl.FORCE_NETWORK);
        }
        String runtimeVersion = updatesConfiguration.getRuntimeVersion();
        String sdkVersion = updatesConfiguration.getSdkVersion();
        if (runtimeVersion == null || runtimeVersion.length() <= 0) {
            builder = header.header("Expo-SDK-Version", sdkVersion);
        } else {
            builder = header.header("Expo-Runtime-Version", runtimeVersion);
        }
        Request.Builder header2 = builder.header("Expo-Release-Channel", updatesConfiguration.getReleaseChannel());
        String consumeErrorLog = NoDatabaseLauncher.consumeErrorLog(context);
        if (consumeErrorLog != null) {
            header2 = header2.header("Expo-Fatal-Error", consumeErrorLog.substring(0, Math.min(1024, consumeErrorLog.length())));
        }
        for (Map.Entry next2 : updatesConfiguration.getRequestHeaders().entrySet()) {
            header2.header((String) next2.getKey(), (String) next2.getValue());
        }
        return header2.build();
    }
}
