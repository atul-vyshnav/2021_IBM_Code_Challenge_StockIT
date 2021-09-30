package expo.modules.updates.loader;

import android.content.Context;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.p020db.UpdatesDatabase;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class EmbeddedLoader {
    public static final String BARE_BUNDLE_FILENAME = "index.android.bundle";
    public static final String BUNDLE_FILENAME = "app.bundle";
    public static final String MANIFEST_FILENAME = "app.manifest";
    private static final String TAG = EmbeddedLoader.class.getSimpleName();
    private static Manifest sEmbeddedManifest = null;
    private UpdatesConfiguration mConfiguration;
    private Context mContext;
    private UpdatesDatabase mDatabase;
    private ArrayList<AssetEntity> mErroredAssetList = new ArrayList<>();
    private ArrayList<AssetEntity> mExistingAssetList = new ArrayList<>();
    private ArrayList<AssetEntity> mFinishedAssetList = new ArrayList<>();
    private float mPixelDensity;
    private ArrayList<AssetEntity> mSkippedAssetList = new ArrayList<>();
    private UpdateEntity mUpdateEntity;
    private File mUpdatesDirectory;

    public EmbeddedLoader(Context context, UpdatesConfiguration updatesConfiguration, UpdatesDatabase updatesDatabase, File file) {
        this.mContext = context;
        this.mConfiguration = updatesConfiguration;
        this.mDatabase = updatesDatabase;
        this.mUpdatesDirectory = file;
        this.mPixelDensity = context.getResources().getDisplayMetrics().density;
    }

    public boolean loadEmbeddedUpdate() {
        Manifest readEmbeddedManifest = readEmbeddedManifest(this.mContext, this.mConfiguration);
        if (readEmbeddedManifest == null) {
            return false;
        }
        boolean processManifest = processManifest(readEmbeddedManifest);
        reset();
        return processManifest;
    }

    public void reset() {
        this.mUpdateEntity = null;
        this.mErroredAssetList = new ArrayList<>();
        this.mSkippedAssetList = new ArrayList<>();
        this.mExistingAssetList = new ArrayList<>();
        this.mFinishedAssetList = new ArrayList<>();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
        if (r3 != null) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0040, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static expo.modules.updates.manifest.Manifest readEmbeddedManifest(android.content.Context r3, expo.modules.updates.UpdatesConfiguration r4) {
        /*
            boolean r0 = r4.hasEmbeddedUpdate()
            if (r0 != 0) goto L_0x0008
            r3 = 0
            return r3
        L_0x0008:
            expo.modules.updates.manifest.Manifest r0 = sEmbeddedManifest
            if (r0 != 0) goto L_0x0064
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ Exception -> 0x0041 }
            java.lang.String r0 = "app.manifest"
            java.io.InputStream r3 = r3.open(r0)     // Catch:{ Exception -> 0x0041 }
            java.lang.String r0 = "UTF-8"
            java.lang.String r0 = org.apache.commons.p029io.IOUtils.toString((java.io.InputStream) r3, (java.lang.String) r0)     // Catch:{ all -> 0x0033 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0033 }
            r1.<init>(r0)     // Catch:{ all -> 0x0033 }
            java.lang.String r0 = "isVerified"
            r2 = 1
            r1.put(r0, r2)     // Catch:{ all -> 0x0033 }
            expo.modules.updates.manifest.Manifest r4 = expo.modules.updates.manifest.ManifestFactory.getEmbeddedManifest(r1, r4)     // Catch:{ all -> 0x0033 }
            sEmbeddedManifest = r4     // Catch:{ all -> 0x0033 }
            if (r3 == 0) goto L_0x0064
            r3.close()     // Catch:{ Exception -> 0x0041 }
            goto L_0x0064
        L_0x0033:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r0 = move-exception
            if (r3 == 0) goto L_0x0040
            r3.close()     // Catch:{ all -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r3 = move-exception
            r4.addSuppressed(r3)     // Catch:{ Exception -> 0x0041 }
        L_0x0040:
            throw r0     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            r3 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r0 = "Could not read embedded manifest"
            android.util.Log.e(r4, r0, r3)
            java.lang.AssertionError r4 = new java.lang.AssertionError
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "The embedded manifest is invalid or could not be read. Make sure you have configured expo-updates correctly in android/app/build.gradle. "
            r0.append(r1)
            java.lang.String r3 = r3.getMessage()
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r4.<init>(r3)
            throw r4
        L_0x0064:
            expo.modules.updates.manifest.Manifest r3 = sEmbeddedManifest
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.EmbeddedLoader.readEmbeddedManifest(android.content.Context, expo.modules.updates.UpdatesConfiguration):expo.modules.updates.manifest.Manifest");
    }

    public static byte[] copyAssetAndGetHash(AssetEntity assetEntity, File file, Context context) throws NoSuchAlgorithmException, IOException {
        if (assetEntity.embeddedAssetFilename != null) {
            return copyContextAssetAndGetHash(assetEntity, file, context);
        }
        if (assetEntity.resourcesFilename != null && assetEntity.resourcesFolder != null) {
            return copyResourceAndGetHash(assetEntity, file, context);
        }
        throw new AssertionError("Failed to copy embedded asset " + assetEntity.key + " from APK assets or resources because not enough information was provided.");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0017, code lost:
        if (r4 != null) goto L_0x0019;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0021, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] copyContextAssetAndGetHash(expo.modules.updates.p020db.entity.AssetEntity r2, java.io.File r3, android.content.Context r4) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch:{ Exception -> 0x0022 }
            java.lang.String r0 = r2.embeddedAssetFilename     // Catch:{ Exception -> 0x0022 }
            java.io.InputStream r4 = r4.open(r0)     // Catch:{ Exception -> 0x0022 }
            byte[] r3 = expo.modules.updates.UpdatesUtils.sha256AndWriteToFile(r4, r3)     // Catch:{ all -> 0x0014 }
            if (r4 == 0) goto L_0x0013
            r4.close()     // Catch:{ Exception -> 0x0022 }
        L_0x0013:
            return r3
        L_0x0014:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0016 }
        L_0x0016:
            r0 = move-exception
            if (r4 == 0) goto L_0x0021
            r4.close()     // Catch:{ all -> 0x001d }
            goto L_0x0021
        L_0x001d:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch:{ Exception -> 0x0022 }
        L_0x0021:
            throw r0     // Catch:{ Exception -> 0x0022 }
        L_0x0022:
            r3 = move-exception
            java.lang.String r4 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to copy asset "
            r0.append(r1)
            java.lang.String r2 = r2.embeddedAssetFilename
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            android.util.Log.e(r4, r2, r3)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.EmbeddedLoader.copyContextAssetAndGetHash(expo.modules.updates.db.entity.AssetEntity, java.io.File, android.content.Context):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        if (r6 != null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002f, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] copyResourceAndGetHash(expo.modules.updates.p020db.entity.AssetEntity r4, java.io.File r5, android.content.Context r6) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            android.content.res.Resources r0 = r6.getResources()
            java.lang.String r1 = r4.resourcesFilename
            java.lang.String r2 = r4.resourcesFolder
            java.lang.String r3 = r6.getPackageName()
            int r0 = r0.getIdentifier(r1, r2, r3)
            android.content.res.Resources r6 = r6.getResources()     // Catch:{ Exception -> 0x0030 }
            java.io.InputStream r6 = r6.openRawResource(r0)     // Catch:{ Exception -> 0x0030 }
            byte[] r5 = expo.modules.updates.UpdatesUtils.sha256AndWriteToFile(r6, r5)     // Catch:{ all -> 0x0022 }
            if (r6 == 0) goto L_0x0021
            r6.close()     // Catch:{ Exception -> 0x0030 }
        L_0x0021:
            return r5
        L_0x0022:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0024 }
        L_0x0024:
            r0 = move-exception
            if (r6 == 0) goto L_0x002f
            r6.close()     // Catch:{ all -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ Exception -> 0x0030 }
        L_0x002f:
            throw r0     // Catch:{ Exception -> 0x0030 }
        L_0x0030:
            r5 = move-exception
            java.lang.String r6 = TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to copy asset "
            r0.append(r1)
            java.lang.String r4 = r4.embeddedAssetFilename
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            android.util.Log.e(r6, r4, r5)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.EmbeddedLoader.copyResourceAndGetHash(expo.modules.updates.db.entity.AssetEntity, java.io.File, android.content.Context):byte[]");
    }

    private boolean processManifest(Manifest manifest) {
        UpdateEntity updateEntity = manifest.getUpdateEntity();
        UpdateEntity loadUpdateWithId = this.mDatabase.updateDao().loadUpdateWithId(updateEntity.f1253id);
        if (loadUpdateWithId == null || loadUpdateWithId.status != UpdateStatus.READY) {
            if (loadUpdateWithId == null) {
                this.mUpdateEntity = updateEntity;
                this.mDatabase.updateDao().insertUpdate(this.mUpdateEntity);
            } else {
                this.mUpdateEntity = loadUpdateWithId;
            }
            copyAllAssets(manifest.getAssetEntityList());
            return true;
        }
        this.mUpdateEntity = loadUpdateWithId;
        return true;
    }

    private void copyAllAssets(ArrayList<AssetEntity> arrayList) {
        Iterator<AssetEntity> it = arrayList.iterator();
        while (it.hasNext()) {
            AssetEntity next = it.next();
            if (shouldSkipAsset(next)) {
                this.mSkippedAssetList.add(next);
            } else {
                AssetEntity loadAssetWithKey = this.mDatabase.assetDao().loadAssetWithKey(next.key);
                if (loadAssetWithKey != null) {
                    this.mDatabase.assetDao().mergeAndUpdateAsset(loadAssetWithKey, next);
                    next = loadAssetWithKey;
                }
                if (next.relativePath == null || !new File(this.mUpdatesDirectory, next.relativePath).exists()) {
                    String createFilenameForAsset = UpdatesUtils.createFilenameForAsset(next);
                    File file = new File(this.mUpdatesDirectory, createFilenameForAsset);
                    if (file.exists()) {
                        next.relativePath = createFilenameForAsset;
                        this.mExistingAssetList.add(next);
                    } else {
                        try {
                            next.hash = copyAssetAndGetHash(next, file, this.mContext);
                            next.downloadTime = new Date();
                            next.relativePath = createFilenameForAsset;
                            this.mFinishedAssetList.add(next);
                        } catch (FileNotFoundException unused) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("APK bundle must contain the expected embedded asset ");
                            sb.append(next.embeddedAssetFilename != null ? next.embeddedAssetFilename : next.resourcesFilename);
                            throw new AssertionError(sb.toString());
                        } catch (Exception unused2) {
                            this.mErroredAssetList.add(next);
                        }
                    }
                } else {
                    this.mExistingAssetList.add(next);
                }
            }
        }
        Iterator<AssetEntity> it2 = this.mExistingAssetList.iterator();
        while (it2.hasNext()) {
            AssetEntity next2 = it2.next();
            if (!this.mDatabase.assetDao().addExistingAssetToUpdate(this.mUpdateEntity, next2, next2.isLaunchAsset)) {
                byte[] bArr = null;
                try {
                    bArr = UpdatesUtils.sha256(new File(this.mUpdatesDirectory, next2.relativePath));
                } catch (Exception unused3) {
                }
                next2.downloadTime = new Date();
                next2.hash = bArr;
                this.mFinishedAssetList.add(next2);
            }
        }
        this.mDatabase.assetDao().insertAssets(this.mFinishedAssetList, this.mUpdateEntity);
        if (this.mErroredAssetList.size() == 0) {
            this.mDatabase.updateDao().markUpdateFinished(this.mUpdateEntity, this.mSkippedAssetList.size() != 0);
        }
    }

    private boolean shouldSkipAsset(AssetEntity assetEntity) {
        if (assetEntity.scales == null || assetEntity.scale == null || pickClosestScale(assetEntity.scales) == assetEntity.scale.floatValue()) {
            return false;
        }
        return true;
    }

    private float pickClosestScale(Float[] fArr) {
        float f = 0.0f;
        float f2 = Float.MAX_VALUE;
        for (Float floatValue : fArr) {
            float floatValue2 = floatValue.floatValue();
            if (floatValue2 >= this.mPixelDensity && floatValue2 < f2) {
                f2 = floatValue2;
            }
            if (floatValue2 > f) {
                f = floatValue2;
            }
        }
        return f2 < Float.MAX_VALUE ? f2 : f;
    }
}
