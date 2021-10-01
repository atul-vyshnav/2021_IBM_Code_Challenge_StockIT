package expo.modules.updates.loader;

import android.content.Context;
import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.loader.FileDownloader;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.manifest.ManifestMetadata;
import expo.modules.updates.p020db.UpdatesDatabase;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class RemoteLoader {
    /* access modifiers changed from: private */
    public static String TAG = RemoteLoader.class.getSimpleName();
    private int mAssetTotal = 0;
    /* access modifiers changed from: private */
    public LoaderCallback mCallback;
    private UpdatesConfiguration mConfiguration;
    private Context mContext;
    private UpdatesDatabase mDatabase;
    private ArrayList<AssetEntity> mErroredAssetList = new ArrayList<>();
    private ArrayList<AssetEntity> mExistingAssetList = new ArrayList<>();
    private FileDownloader mFileDownloader;
    private ArrayList<AssetEntity> mFinishedAssetList = new ArrayList<>();
    /* access modifiers changed from: private */
    public Manifest mManifest;
    /* access modifiers changed from: private */
    public UpdateEntity mUpdateEntity;
    private File mUpdatesDirectory;

    public interface LoaderCallback {
        void onFailure(Exception exc);

        boolean onManifestLoaded(Manifest manifest);

        void onSuccess(UpdateEntity updateEntity);
    }

    public RemoteLoader(Context context, UpdatesConfiguration updatesConfiguration, UpdatesDatabase updatesDatabase, FileDownloader fileDownloader, File file) {
        this.mContext = context;
        this.mConfiguration = updatesConfiguration;
        this.mDatabase = updatesDatabase;
        this.mFileDownloader = fileDownloader;
        this.mUpdatesDirectory = file;
    }

    public void start(LoaderCallback loaderCallback) {
        if (this.mCallback != null) {
            loaderCallback.onFailure(new Exception("RemoteLoader has already started. Create a new instance in order to load multiple URLs in parallel."));
            return;
        }
        this.mCallback = loaderCallback;
        this.mFileDownloader.downloadManifest(this.mConfiguration, ManifestMetadata.getServerDefinedHeaders(this.mDatabase, this.mConfiguration), this.mContext, new FileDownloader.ManifestDownloadCallback() {
            public void onFailure(String str, Exception exc) {
                RemoteLoader.this.finishWithError(str, exc);
            }

            public void onSuccess(Manifest manifest) {
                Manifest unused = RemoteLoader.this.mManifest = manifest;
                if (RemoteLoader.this.mCallback.onManifestLoaded(manifest)) {
                    RemoteLoader.this.processManifest(manifest);
                    return;
                }
                UpdateEntity unused2 = RemoteLoader.this.mUpdateEntity = null;
                RemoteLoader.this.finishWithSuccess();
            }
        });
    }

    private void reset() {
        this.mUpdateEntity = null;
        this.mCallback = null;
        this.mAssetTotal = 0;
        this.mErroredAssetList = new ArrayList<>();
        this.mExistingAssetList = new ArrayList<>();
        this.mFinishedAssetList = new ArrayList<>();
    }

    /* access modifiers changed from: private */
    public void finishWithSuccess() {
        if (this.mCallback == null) {
            Log.e(TAG, "RemoteLoader tried to finish but it already finished or was never initialized.");
            return;
        }
        ManifestMetadata.saveMetadata(this.mManifest, this.mDatabase, this.mConfiguration);
        this.mCallback.onSuccess(this.mUpdateEntity);
        reset();
    }

    /* access modifiers changed from: private */
    public void finishWithError(String str, Exception exc) {
        Log.e(TAG, str, exc);
        LoaderCallback loaderCallback = this.mCallback;
        if (loaderCallback == null) {
            Log.e(TAG, "RemoteLoader tried to finish but it already finished or was never initialized.");
            return;
        }
        loaderCallback.onFailure(exc);
        reset();
    }

    /* access modifiers changed from: private */
    public void processManifest(Manifest manifest) {
        if (manifest.isDevelopmentMode()) {
            UpdateEntity updateEntity = manifest.getUpdateEntity();
            this.mDatabase.updateDao().insertUpdate(updateEntity);
            this.mDatabase.updateDao().markUpdateFinished(updateEntity);
            finishWithSuccess();
            return;
        }
        UpdateEntity updateEntity2 = manifest.getUpdateEntity();
        UpdateEntity loadUpdateWithId = this.mDatabase.updateDao().loadUpdateWithId(updateEntity2.f1253id);
        if (loadUpdateWithId != null && !loadUpdateWithId.scopeKey.equals(updateEntity2.scopeKey)) {
            this.mDatabase.updateDao().setUpdateScopeKey(loadUpdateWithId, updateEntity2.scopeKey);
            Log.e(TAG, "Loaded an update with the same ID but a different scopeKey than one we already have on disk. This is a server error. Overwriting the scopeKey and loading the existing update.");
        }
        if (loadUpdateWithId == null || loadUpdateWithId.status != UpdateStatus.READY) {
            if (loadUpdateWithId == null) {
                this.mUpdateEntity = updateEntity2;
                this.mDatabase.updateDao().insertUpdate(this.mUpdateEntity);
            } else {
                this.mUpdateEntity = loadUpdateWithId;
            }
            downloadAllAssets(manifest.getAssetEntityList());
            return;
        }
        this.mUpdateEntity = loadUpdateWithId;
        finishWithSuccess();
    }

    private void downloadAllAssets(ArrayList<AssetEntity> arrayList) {
        this.mAssetTotal = arrayList.size();
        Iterator<AssetEntity> it = arrayList.iterator();
        while (it.hasNext()) {
            AssetEntity next = it.next();
            AssetEntity loadAssetWithKey = this.mDatabase.assetDao().loadAssetWithKey(next.key);
            if (loadAssetWithKey != null) {
                this.mDatabase.assetDao().mergeAndUpdateAsset(loadAssetWithKey, next);
                next = loadAssetWithKey;
            }
            if (next.relativePath != null && new File(this.mUpdatesDirectory, next.relativePath).exists()) {
                handleAssetDownloadCompleted(next, true, false);
            } else if (next.url == null) {
                Log.e(TAG, "Failed to download asset with no URL provided");
                handleAssetDownloadCompleted(next, false, false);
            } else {
                this.mFileDownloader.downloadAsset(next, this.mUpdatesDirectory, this.mConfiguration, new FileDownloader.AssetDownloadCallback() {
                    public void onFailure(Exception exc, AssetEntity assetEntity) {
                        String access$600 = RemoteLoader.TAG;
                        Log.e(access$600, "Failed to download asset from " + assetEntity.url, exc);
                        RemoteLoader.this.handleAssetDownloadCompleted(assetEntity, false, false);
                    }

                    public void onSuccess(AssetEntity assetEntity, boolean z) {
                        RemoteLoader.this.handleAssetDownloadCompleted(assetEntity, true, z);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:15|16|17|18|19|40) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x005e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void handleAssetDownloadCompleted(expo.modules.updates.p020db.entity.AssetEntity r4, boolean r5, boolean r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r5 == 0) goto L_0x0011
            if (r6 == 0) goto L_0x000b
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mFinishedAssetList     // Catch:{ all -> 0x00b0 }
            r5.add(r4)     // Catch:{ all -> 0x00b0 }
            goto L_0x0016
        L_0x000b:
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mExistingAssetList     // Catch:{ all -> 0x00b0 }
            r5.add(r4)     // Catch:{ all -> 0x00b0 }
            goto L_0x0016
        L_0x0011:
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mErroredAssetList     // Catch:{ all -> 0x00b0 }
            r5.add(r4)     // Catch:{ all -> 0x00b0 }
        L_0x0016:
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r4 = r3.mFinishedAssetList     // Catch:{ all -> 0x00b0 }
            int r4 = r4.size()     // Catch:{ all -> 0x00b0 }
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mErroredAssetList     // Catch:{ all -> 0x00b0 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b0 }
            int r4 = r4 + r5
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mExistingAssetList     // Catch:{ all -> 0x00b0 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b0 }
            int r4 = r4 + r5
            int r5 = r3.mAssetTotal     // Catch:{ all -> 0x00b0 }
            if (r4 != r5) goto L_0x00ae
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r4 = r3.mExistingAssetList     // Catch:{ Exception -> 0x00a6 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x00a6 }
        L_0x0034:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x00a6 }
            if (r5 == 0) goto L_0x006d
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.entity.AssetEntity r5 = (expo.modules.updates.p020db.entity.AssetEntity) r5     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.UpdatesDatabase r6 = r3.mDatabase     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.dao.AssetDao r6 = r6.assetDao()     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.entity.UpdateEntity r0 = r3.mUpdateEntity     // Catch:{ Exception -> 0x00a6 }
            boolean r1 = r5.isLaunchAsset     // Catch:{ Exception -> 0x00a6 }
            boolean r6 = r6.addExistingAssetToUpdate(r0, r5, r1)     // Catch:{ Exception -> 0x00a6 }
            if (r6 != 0) goto L_0x0034
            r6 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x005e }
            java.io.File r1 = r3.mUpdatesDirectory     // Catch:{ Exception -> 0x005e }
            java.lang.String r2 = r5.relativePath     // Catch:{ Exception -> 0x005e }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x005e }
            byte[] r6 = expo.modules.updates.UpdatesUtils.sha256((java.io.File) r0)     // Catch:{ Exception -> 0x005e }
        L_0x005e:
            java.util.Date r0 = new java.util.Date     // Catch:{ Exception -> 0x00a6 }
            r0.<init>()     // Catch:{ Exception -> 0x00a6 }
            r5.downloadTime = r0     // Catch:{ Exception -> 0x00a6 }
            r5.hash = r6     // Catch:{ Exception -> 0x00a6 }
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r6 = r3.mFinishedAssetList     // Catch:{ Exception -> 0x00a6 }
            r6.add(r5)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x0034
        L_0x006d:
            expo.modules.updates.db.UpdatesDatabase r4 = r3.mDatabase     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.dao.AssetDao r4 = r4.assetDao()     // Catch:{ Exception -> 0x00a6 }
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r5 = r3.mFinishedAssetList     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.entity.UpdateEntity r6 = r3.mUpdateEntity     // Catch:{ Exception -> 0x00a6 }
            r4.insertAssets(r5, r6)     // Catch:{ Exception -> 0x00a6 }
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r4 = r3.mErroredAssetList     // Catch:{ Exception -> 0x00a6 }
            int r4 = r4.size()     // Catch:{ Exception -> 0x00a6 }
            if (r4 != 0) goto L_0x008d
            expo.modules.updates.db.UpdatesDatabase r4 = r3.mDatabase     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.dao.UpdateDao r4 = r4.updateDao()     // Catch:{ Exception -> 0x00a6 }
            expo.modules.updates.db.entity.UpdateEntity r5 = r3.mUpdateEntity     // Catch:{ Exception -> 0x00a6 }
            r4.markUpdateFinished(r5)     // Catch:{ Exception -> 0x00a6 }
        L_0x008d:
            java.util.ArrayList<expo.modules.updates.db.entity.AssetEntity> r4 = r3.mErroredAssetList     // Catch:{ all -> 0x00b0 }
            int r4 = r4.size()     // Catch:{ all -> 0x00b0 }
            if (r4 <= 0) goto L_0x00a2
            java.lang.String r4 = "Failed to load all assets"
            java.lang.Exception r5 = new java.lang.Exception     // Catch:{ all -> 0x00b0 }
            java.lang.String r6 = "Failed to load all assets"
            r5.<init>(r6)     // Catch:{ all -> 0x00b0 }
            r3.finishWithError(r4, r5)     // Catch:{ all -> 0x00b0 }
            goto L_0x00ae
        L_0x00a2:
            r3.finishWithSuccess()     // Catch:{ all -> 0x00b0 }
            goto L_0x00ae
        L_0x00a6:
            r4 = move-exception
            java.lang.String r5 = "Error while adding new update to database"
            r3.finishWithError(r5, r4)     // Catch:{ all -> 0x00b0 }
            monitor-exit(r3)
            return
        L_0x00ae:
            monitor-exit(r3)
            return
        L_0x00b0:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.RemoteLoader.handleAssetDownloadCompleted(expo.modules.updates.db.entity.AssetEntity, boolean, boolean):void");
    }
}
