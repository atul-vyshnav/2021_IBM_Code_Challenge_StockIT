package expo.modules.updates.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.launcher.DatabaseLauncher;
import expo.modules.updates.launcher.Launcher;
import expo.modules.updates.launcher.SelectionPolicy;
import expo.modules.updates.loader.LoaderTask;
import expo.modules.updates.loader.RemoteLoader;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.manifest.ManifestMetadata;
import expo.modules.updates.p020db.DatabaseHolder;
import expo.modules.updates.p020db.Reaper;
import expo.modules.updates.p020db.UpdatesDatabase;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;

public class LoaderTask {
    /* access modifiers changed from: private */
    public static final String TAG = LoaderTask.class.getSimpleName();
    /* access modifiers changed from: private */
    public LoaderTaskCallback mCallback;
    /* access modifiers changed from: private */
    public Launcher mCandidateLauncher;
    /* access modifiers changed from: private */
    public UpdatesConfiguration mConfiguration;
    /* access modifiers changed from: private */
    public DatabaseHolder mDatabaseHolder;
    /* access modifiers changed from: private */
    public File mDirectory;
    /* access modifiers changed from: private */
    public FileDownloader mFileDownloader;
    private Launcher mFinalizedLauncher;
    private HandlerThread mHandlerThread;
    /* access modifiers changed from: private */
    public boolean mHasLaunched = false;
    /* access modifiers changed from: private */
    public boolean mIsReadyToLaunch = false;
    /* access modifiers changed from: private */
    public boolean mIsUpToDate = false;
    /* access modifiers changed from: private */
    public SelectionPolicy mSelectionPolicy;
    private boolean mTimeoutFinished = false;

    public enum BackgroundUpdateStatus {
        ERROR,
        NO_UPDATE_AVAILABLE,
        UPDATE_AVAILABLE
    }

    private interface Callback {
        void onFailure(Exception exc);

        void onSuccess();
    }

    public interface LoaderTaskCallback {
        void onBackgroundUpdateFinished(BackgroundUpdateStatus backgroundUpdateStatus, UpdateEntity updateEntity, Exception exc);

        boolean onCachedUpdateLoaded(UpdateEntity updateEntity);

        void onFailure(Exception exc);

        void onRemoteManifestLoaded(Manifest manifest);

        void onSuccess(Launcher launcher, boolean z);
    }

    public LoaderTask(UpdatesConfiguration updatesConfiguration, DatabaseHolder databaseHolder, File file, FileDownloader fileDownloader, SelectionPolicy selectionPolicy, LoaderTaskCallback loaderTaskCallback) {
        this.mConfiguration = updatesConfiguration;
        this.mDatabaseHolder = databaseHolder;
        this.mDirectory = file;
        this.mFileDownloader = fileDownloader;
        this.mSelectionPolicy = selectionPolicy;
        this.mCallback = loaderTaskCallback;
        this.mHandlerThread = new HandlerThread("expo-updates-timer");
    }

    public void start(final Context context) {
        if (!this.mConfiguration.isEnabled()) {
            this.mCallback.onFailure(new Exception("LoaderTask was passed a configuration object with updates disabled. You should load updates from an embedded source rather than calling LoaderTask, or enable updates in the configuration."));
        } else if (this.mConfiguration.getUpdateUrl() == null) {
            this.mCallback.onFailure(new Exception("LoaderTask was passed a configuration object with a null URL. You must pass a nonnull URL in order to use LoaderTask to load updates."));
        } else if (this.mDirectory != null) {
            final boolean shouldCheckForUpdateOnLaunch = UpdatesUtils.shouldCheckForUpdateOnLaunch(this.mConfiguration, context);
            int launchWaitMs = this.mConfiguration.getLaunchWaitMs();
            if (launchWaitMs <= 0 || !shouldCheckForUpdateOnLaunch) {
                this.mTimeoutFinished = true;
            } else {
                this.mHandlerThread.start();
                new Handler(this.mHandlerThread.getLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        LoaderTask.this.timeout();
                    }
                }, (long) launchWaitMs);
            }
            launchFallbackUpdateFromDisk(context, new Callback() {
                private void launchRemoteUpdate() {
                    LoaderTask.this.launchRemoteUpdateInBackground(context, new Callback() {
                        public void onFailure(Exception exc) {
                            LoaderTask.this.finish(exc);
                            LoaderTask.this.runReaper();
                        }

                        public void onSuccess() {
                            synchronized (LoaderTask.this) {
                                boolean unused = LoaderTask.this.mIsReadyToLaunch = true;
                            }
                            LoaderTask.this.finish((Exception) null);
                            LoaderTask.this.runReaper();
                        }
                    });
                }

                public void onFailure(Exception exc) {
                    if (!shouldCheckForUpdateOnLaunch) {
                        LoaderTask.this.finish(exc);
                    } else {
                        launchRemoteUpdate();
                    }
                    Log.e(LoaderTask.TAG, "Failed to launch embedded or launchable update", exc);
                }

                public void onSuccess() {
                    if (LoaderTask.this.mCandidateLauncher.getLaunchedUpdate() == null || LoaderTask.this.mCallback.onCachedUpdateLoaded(LoaderTask.this.mCandidateLauncher.getLaunchedUpdate())) {
                        synchronized (LoaderTask.this) {
                            boolean unused = LoaderTask.this.mIsReadyToLaunch = true;
                            LoaderTask.this.maybeFinish();
                        }
                        if (shouldCheckForUpdateOnLaunch) {
                            launchRemoteUpdate();
                        } else {
                            LoaderTask.this.runReaper();
                        }
                    } else {
                        LoaderTask.this.stopTimer();
                        Launcher unused2 = LoaderTask.this.mCandidateLauncher = null;
                        launchRemoteUpdate();
                    }
                }
            });
        } else {
            throw new AssertionError("LoaderTask directory must be nonnull.");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void finish(java.lang.Exception r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.mHasLaunched     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r3)
            return
        L_0x0007:
            r0 = 1
            r3.mHasLaunched = r0     // Catch:{ all -> 0x0047 }
            expo.modules.updates.launcher.Launcher r0 = r3.mCandidateLauncher     // Catch:{ all -> 0x0047 }
            r3.mFinalizedLauncher = r0     // Catch:{ all -> 0x0047 }
            boolean r1 = r3.mIsReadyToLaunch     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x0025
            if (r0 == 0) goto L_0x0025
            expo.modules.updates.db.entity.UpdateEntity r0 = r0.getLaunchedUpdate()     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x001b
            goto L_0x0025
        L_0x001b:
            expo.modules.updates.loader.LoaderTask$LoaderTaskCallback r0 = r3.mCallback     // Catch:{ all -> 0x0047 }
            expo.modules.updates.launcher.Launcher r1 = r3.mFinalizedLauncher     // Catch:{ all -> 0x0047 }
            boolean r2 = r3.mIsUpToDate     // Catch:{ all -> 0x0047 }
            r0.onSuccess(r1, r2)     // Catch:{ all -> 0x0047 }
            goto L_0x0035
        L_0x0025:
            expo.modules.updates.loader.LoaderTask$LoaderTaskCallback r0 = r3.mCallback     // Catch:{ all -> 0x0047 }
            if (r4 == 0) goto L_0x002b
            r1 = r4
            goto L_0x0032
        L_0x002b:
            java.lang.Exception r1 = new java.lang.Exception     // Catch:{ all -> 0x0047 }
            java.lang.String r2 = "LoaderTask encountered an unexpected error and could not launch an update."
            r1.<init>(r2)     // Catch:{ all -> 0x0047 }
        L_0x0032:
            r0.onFailure(r1)     // Catch:{ all -> 0x0047 }
        L_0x0035:
            boolean r0 = r3.mTimeoutFinished     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x003c
            r3.stopTimer()     // Catch:{ all -> 0x0047 }
        L_0x003c:
            if (r4 == 0) goto L_0x0045
            java.lang.String r0 = TAG     // Catch:{ all -> 0x0047 }
            java.lang.String r1 = "Unexpected error encountered while loading this app"
            android.util.Log.e(r0, r1, r4)     // Catch:{ all -> 0x0047 }
        L_0x0045:
            monitor-exit(r3)
            return
        L_0x0047:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.LoaderTask.finish(java.lang.Exception):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void maybeFinish() {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.mIsReadyToLaunch     // Catch:{ all -> 0x0012 }
            if (r0 == 0) goto L_0x0010
            boolean r0 = r1.mTimeoutFinished     // Catch:{ all -> 0x0012 }
            if (r0 != 0) goto L_0x000a
            goto L_0x0010
        L_0x000a:
            r0 = 0
            r1.finish(r0)     // Catch:{ all -> 0x0012 }
            monitor-exit(r1)
            return
        L_0x0010:
            monitor-exit(r1)
            return
        L_0x0012:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.loader.LoaderTask.maybeFinish():void");
    }

    /* access modifiers changed from: private */
    public synchronized void stopTimer() {
        this.mTimeoutFinished = true;
        this.mHandlerThread.quitSafely();
    }

    /* access modifiers changed from: private */
    public synchronized void timeout() {
        if (!this.mTimeoutFinished) {
            this.mTimeoutFinished = true;
            maybeFinish();
        }
        stopTimer();
    }

    private void launchFallbackUpdateFromDisk(Context context, final Callback callback) {
        UpdatesDatabase database = this.mDatabaseHolder.getDatabase();
        DatabaseLauncher databaseLauncher = new DatabaseLauncher(this.mConfiguration, this.mDirectory, this.mFileDownloader, this.mSelectionPolicy);
        this.mCandidateLauncher = databaseLauncher;
        if (this.mConfiguration.hasEmbeddedUpdate()) {
            if (this.mSelectionPolicy.shouldLoadNewUpdate(EmbeddedLoader.readEmbeddedManifest(context, this.mConfiguration).getUpdateEntity(), databaseLauncher.getLaunchableUpdate(database, context), ManifestMetadata.getManifestFilters(database, this.mConfiguration))) {
                new EmbeddedLoader(context, this.mConfiguration, database, this.mDirectory).loadEmbeddedUpdate();
            }
        }
        databaseLauncher.launch(database, context, new Launcher.LauncherCallback() {
            public void onFailure(Exception exc) {
                LoaderTask.this.mDatabaseHolder.releaseDatabase();
                callback.onFailure(exc);
            }

            public void onSuccess() {
                LoaderTask.this.mDatabaseHolder.releaseDatabase();
                callback.onSuccess();
            }
        });
    }

    /* access modifiers changed from: private */
    public void launchRemoteUpdateInBackground(Context context, Callback callback) {
        AsyncTask.execute(new Runnable(context, callback) {
            public final /* synthetic */ Context f$1;
            public final /* synthetic */ LoaderTask.Callback f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                LoaderTask.this.lambda$launchRemoteUpdateInBackground$0$LoaderTask(this.f$1, this.f$2);
            }
        });
    }

    public /* synthetic */ void lambda$launchRemoteUpdateInBackground$0$LoaderTask(final Context context, final Callback callback) {
        final UpdatesDatabase database = this.mDatabaseHolder.getDatabase();
        new RemoteLoader(context, this.mConfiguration, database, this.mFileDownloader, this.mDirectory).start(new RemoteLoader.LoaderCallback() {
            public void onFailure(Exception exc) {
                LoaderTask.this.mDatabaseHolder.releaseDatabase();
                callback.onFailure(exc);
                LoaderTask.this.mCallback.onBackgroundUpdateFinished(BackgroundUpdateStatus.ERROR, (UpdateEntity) null, exc);
                Log.e(LoaderTask.TAG, "Failed to download remote update", exc);
            }

            public boolean onManifestLoaded(Manifest manifest) {
                if (LoaderTask.this.mSelectionPolicy.shouldLoadNewUpdate(manifest.getUpdateEntity(), LoaderTask.this.mCandidateLauncher == null ? null : LoaderTask.this.mCandidateLauncher.getLaunchedUpdate(), manifest.getManifestFilters())) {
                    boolean unused = LoaderTask.this.mIsUpToDate = false;
                    LoaderTask.this.mCallback.onRemoteManifestLoaded(manifest);
                    return true;
                }
                boolean unused2 = LoaderTask.this.mIsUpToDate = true;
                return false;
            }

            public void onSuccess(final UpdateEntity updateEntity) {
                final DatabaseLauncher databaseLauncher = new DatabaseLauncher(LoaderTask.this.mConfiguration, LoaderTask.this.mDirectory, LoaderTask.this.mFileDownloader, LoaderTask.this.mSelectionPolicy);
                databaseLauncher.launch(database, context, new Launcher.LauncherCallback() {
                    public void onFailure(Exception exc) {
                        LoaderTask.this.mDatabaseHolder.releaseDatabase();
                        callback.onFailure(exc);
                        Log.e(LoaderTask.TAG, "Loaded new update but it failed to launch", exc);
                    }

                    public void onSuccess() {
                        boolean access$1500;
                        LoaderTask.this.mDatabaseHolder.releaseDatabase();
                        synchronized (LoaderTask.this) {
                            access$1500 = LoaderTask.this.mHasLaunched;
                            if (!access$1500) {
                                Launcher unused = LoaderTask.this.mCandidateLauncher = databaseLauncher;
                                boolean unused2 = LoaderTask.this.mIsUpToDate = true;
                            }
                        }
                        callback.onSuccess();
                        if (!access$1500) {
                            return;
                        }
                        if (updateEntity == null) {
                            LoaderTask.this.mCallback.onBackgroundUpdateFinished(BackgroundUpdateStatus.NO_UPDATE_AVAILABLE, (UpdateEntity) null, (Exception) null);
                        } else {
                            LoaderTask.this.mCallback.onBackgroundUpdateFinished(BackgroundUpdateStatus.UPDATE_AVAILABLE, updateEntity, (Exception) null);
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void runReaper() {
        AsyncTask.execute(new Runnable() {
            public final void run() {
                LoaderTask.this.lambda$runReaper$1$LoaderTask();
            }
        });
    }

    public /* synthetic */ void lambda$runReaper$1$LoaderTask() {
        synchronized (this) {
            if (!(this.mFinalizedLauncher == null || this.mFinalizedLauncher.getLaunchedUpdate() == null)) {
                Reaper.reapUnusedUpdates(this.mConfiguration, this.mDatabaseHolder.getDatabase(), this.mDirectory, this.mFinalizedLauncher.getLaunchedUpdate(), this.mSelectionPolicy);
                this.mDatabaseHolder.releaseDatabase();
            }
        }
    }
}
