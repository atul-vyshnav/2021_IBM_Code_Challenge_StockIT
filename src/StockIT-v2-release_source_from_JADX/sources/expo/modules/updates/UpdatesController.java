package expo.modules.updates;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.WritableMap;
import com.onesignal.OneSignalDbContract;
import expo.modules.updates.launcher.DatabaseLauncher;
import expo.modules.updates.launcher.Launcher;
import expo.modules.updates.launcher.NoDatabaseLauncher;
import expo.modules.updates.launcher.SelectionPolicy;
import expo.modules.updates.launcher.SelectionPolicyFilterAware;
import expo.modules.updates.loader.FileDownloader;
import expo.modules.updates.loader.LoaderTask;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.p020db.DatabaseHolder;
import expo.modules.updates.p020db.Reaper;
import expo.modules.updates.p020db.UpdatesDatabase;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;

public class UpdatesController {
    /* access modifiers changed from: private */
    public static final String TAG = UpdatesController.class.getSimpleName();
    private static final String UPDATE_AVAILABLE_EVENT = "updateAvailable";
    private static final String UPDATE_ERROR_EVENT = "error";
    private static final String UPDATE_NO_UPDATE_AVAILABLE_EVENT = "noUpdateAvailable";
    private static UpdatesController sInstance;
    private DatabaseHolder mDatabaseHolder;
    private FileDownloader mFileDownloader;
    /* access modifiers changed from: private */
    public boolean mIsEmergencyLaunch = false;
    private boolean mIsLoaderTaskFinished = false;
    /* access modifiers changed from: private */
    public Launcher mLauncher;
    /* access modifiers changed from: private */
    public WeakReference<ReactNativeHost> mReactNativeHost;
    private SelectionPolicy mSelectionPolicy;
    /* access modifiers changed from: private */
    public UpdatesConfiguration mUpdatesConfiguration;
    private File mUpdatesDirectory;
    private Exception mUpdatesDirectoryException;

    private UpdatesController(Context context, UpdatesConfiguration updatesConfiguration) {
        this.mUpdatesConfiguration = updatesConfiguration;
        this.mDatabaseHolder = new DatabaseHolder(UpdatesDatabase.getInstance(context));
        this.mSelectionPolicy = new SelectionPolicyFilterAware(UpdatesUtils.getRuntimeVersion(updatesConfiguration));
        this.mFileDownloader = new FileDownloader(context);
        if (context instanceof ReactApplication) {
            this.mReactNativeHost = new WeakReference<>(((ReactApplication) context).getReactNativeHost());
        }
        try {
            this.mUpdatesDirectory = UpdatesUtils.getOrCreateUpdatesDirectory(context);
        } catch (Exception e) {
            this.mUpdatesDirectoryException = e;
            this.mUpdatesDirectory = null;
        }
    }

    public static UpdatesController getInstance() {
        UpdatesController updatesController = sInstance;
        if (updatesController != null) {
            return updatesController;
        }
        throw new IllegalStateException("UpdatesController.getInstance() was called before the module was initialized");
    }

    public static void initialize(Context context) {
        if (sInstance == null) {
            UpdatesController updatesController = new UpdatesController(context, new UpdatesConfiguration().loadValuesFromMetadata(context));
            sInstance = updatesController;
            updatesController.start(context);
        }
    }

    public static void initialize(Context context, Map<String, Object> map) {
        if (sInstance == null) {
            UpdatesController updatesController = new UpdatesController(context, new UpdatesConfiguration().loadValuesFromMetadata(context).loadValuesFromMap(map));
            sInstance = updatesController;
            updatesController.start(context);
        }
    }

    public void setReactNativeHost(ReactNativeHost reactNativeHost) {
        this.mReactNativeHost = new WeakReference<>(reactNativeHost);
    }

    public synchronized String getLaunchAssetFile() {
        while (!this.mIsLoaderTaskFinished) {
            try {
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupted while waiting for launch asset file", e);
            }
        }
        if (this.mLauncher == null) {
            return null;
        }
        return this.mLauncher.getLaunchAssetFile();
    }

    public String getBundleAssetName() {
        Launcher launcher = this.mLauncher;
        if (launcher == null) {
            return null;
        }
        return launcher.getBundleAssetName();
    }

    public Map<AssetEntity, String> getLocalAssetFiles() {
        Launcher launcher = this.mLauncher;
        if (launcher == null) {
            return null;
        }
        return launcher.getLocalAssetFiles();
    }

    public boolean isUsingEmbeddedAssets() {
        Launcher launcher = this.mLauncher;
        if (launcher == null) {
            return true;
        }
        return launcher.isUsingEmbeddedAssets();
    }

    public DatabaseHolder getDatabaseHolder() {
        return this.mDatabaseHolder;
    }

    public UpdatesDatabase getDatabase() {
        return this.mDatabaseHolder.getDatabase();
    }

    public void releaseDatabase() {
        this.mDatabaseHolder.releaseDatabase();
    }

    public Uri getUpdateUrl() {
        return this.mUpdatesConfiguration.getUpdateUrl();
    }

    public UpdatesConfiguration getUpdatesConfiguration() {
        return this.mUpdatesConfiguration;
    }

    public File getUpdatesDirectory() {
        return this.mUpdatesDirectory;
    }

    public UpdateEntity getLaunchedUpdate() {
        return this.mLauncher.getLaunchedUpdate();
    }

    public SelectionPolicy getSelectionPolicy() {
        return this.mSelectionPolicy;
    }

    public FileDownloader getFileDownloader() {
        return this.mFileDownloader;
    }

    public boolean isEmergencyLaunch() {
        return this.mIsEmergencyLaunch;
    }

    public synchronized void start(final Context context) {
        if (!this.mUpdatesConfiguration.isEnabled()) {
            this.mLauncher = new NoDatabaseLauncher(context, this.mUpdatesConfiguration);
        }
        if (this.mUpdatesDirectory == null) {
            this.mLauncher = new NoDatabaseLauncher(context, this.mUpdatesConfiguration, this.mUpdatesDirectoryException);
            this.mIsEmergencyLaunch = true;
        }
        new LoaderTask(this.mUpdatesConfiguration, this.mDatabaseHolder, this.mUpdatesDirectory, this.mFileDownloader, this.mSelectionPolicy, new LoaderTask.LoaderTaskCallback() {
            public boolean onCachedUpdateLoaded(UpdateEntity updateEntity) {
                return true;
            }

            public void onRemoteManifestLoaded(Manifest manifest) {
            }

            public void onFailure(Exception exc) {
                Launcher unused = UpdatesController.this.mLauncher = new NoDatabaseLauncher(context, UpdatesController.this.mUpdatesConfiguration, exc);
                boolean unused2 = UpdatesController.this.mIsEmergencyLaunch = true;
                UpdatesController.this.notifyController();
            }

            public void onSuccess(Launcher launcher, boolean z) {
                Launcher unused = UpdatesController.this.mLauncher = launcher;
                UpdatesController.this.notifyController();
            }

            public void onBackgroundUpdateFinished(LoaderTask.BackgroundUpdateStatus backgroundUpdateStatus, UpdateEntity updateEntity, Exception exc) {
                if (backgroundUpdateStatus == LoaderTask.BackgroundUpdateStatus.ERROR) {
                    if (exc != null) {
                        WritableMap createMap = Arguments.createMap();
                        createMap.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, exc.getMessage());
                        UpdatesUtils.sendEventToReactNative(UpdatesController.this.mReactNativeHost, UpdatesController.UPDATE_ERROR_EVENT, createMap);
                        return;
                    }
                    throw new AssertionError("Background update with error status must have a nonnull exception object");
                } else if (backgroundUpdateStatus == LoaderTask.BackgroundUpdateStatus.UPDATE_AVAILABLE) {
                    if (updateEntity != null) {
                        WritableMap createMap2 = Arguments.createMap();
                        createMap2.putString("manifestString", updateEntity.metadata.toString());
                        UpdatesUtils.sendEventToReactNative(UpdatesController.this.mReactNativeHost, UpdatesController.UPDATE_AVAILABLE_EVENT, createMap2);
                        return;
                    }
                    throw new AssertionError("Background update with error status must have a nonnull update object");
                } else if (backgroundUpdateStatus == LoaderTask.BackgroundUpdateStatus.NO_UPDATE_AVAILABLE) {
                    UpdatesUtils.sendEventToReactNative(UpdatesController.this.mReactNativeHost, UpdatesController.UPDATE_NO_UPDATE_AVAILABLE_EVENT, (WritableMap) null);
                }
            }
        }).start(context);
    }

    /* access modifiers changed from: private */
    public synchronized void notifyController() {
        this.mIsLoaderTaskFinished = true;
        notify();
    }

    /* access modifiers changed from: private */
    public void runReaper() {
        AsyncTask.execute(new Runnable() {
            public final void run() {
                UpdatesController.this.lambda$runReaper$0$UpdatesController();
            }
        });
    }

    public /* synthetic */ void lambda$runReaper$0$UpdatesController() {
        Reaper.reapUnusedUpdates(this.mUpdatesConfiguration, getDatabase(), this.mUpdatesDirectory, getLaunchedUpdate(), this.mSelectionPolicy);
        releaseDatabase();
    }

    public void relaunchReactApplication(Context context, Launcher.LauncherCallback launcherCallback) {
        WeakReference<ReactNativeHost> weakReference = this.mReactNativeHost;
        if (weakReference == null || weakReference.get() == null) {
            launcherCallback.onFailure(new Exception("Could not reload application. Ensure you have passed the correct instance of ReactApplication into UpdatesController.initialize()."));
            return;
        }
        final ReactNativeHost reactNativeHost = (ReactNativeHost) this.mReactNativeHost.get();
        final String launchAssetFile = this.mLauncher.getLaunchAssetFile();
        UpdatesDatabase database = getDatabase();
        DatabaseLauncher databaseLauncher = new DatabaseLauncher(this.mUpdatesConfiguration, this.mUpdatesDirectory, this.mFileDownloader, this.mSelectionPolicy);
        final Launcher.LauncherCallback launcherCallback2 = launcherCallback;
        final DatabaseLauncher databaseLauncher2 = databaseLauncher;
        databaseLauncher.launch(database, context, new Launcher.LauncherCallback() {
            public void onFailure(Exception exc) {
                launcherCallback2.onFailure(exc);
            }

            public void onSuccess() {
                Launcher unused = UpdatesController.this.mLauncher = databaseLauncher2;
                UpdatesController.this.releaseDatabase();
                ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
                String launchAssetFile = UpdatesController.this.mLauncher.getLaunchAssetFile();
                if (launchAssetFile != null && !launchAssetFile.equals(launchAssetFile)) {
                    try {
                        JSBundleLoader createFileLoader = JSBundleLoader.createFileLoader(launchAssetFile);
                        Field declaredField = reactInstanceManager.getClass().getDeclaredField("mBundleLoader");
                        declaredField.setAccessible(true);
                        declaredField.set(reactInstanceManager, createFileLoader);
                    } catch (Exception e) {
                        Log.e(UpdatesController.TAG, "Could not reset JSBundleLoader in ReactInstanceManager", e);
                    }
                }
                launcherCallback2.onSuccess();
                Handler handler = new Handler(Looper.getMainLooper());
                reactInstanceManager.getClass();
                handler.post(new Runnable() {
                    public final void run() {
                        ReactInstanceManager.this.recreateReactContextInBackground();
                    }
                });
                UpdatesController.this.runReaper();
            }
        });
    }
}
