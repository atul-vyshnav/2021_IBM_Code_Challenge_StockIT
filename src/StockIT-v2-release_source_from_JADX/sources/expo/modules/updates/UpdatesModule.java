package expo.modules.updates;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import expo.modules.updates.launcher.Launcher;
import expo.modules.updates.loader.FileDownloader;
import expo.modules.updates.loader.RemoteLoader;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.manifest.ManifestMetadata;
import expo.modules.updates.p020db.DatabaseHolder;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;

public class UpdatesModule extends ExportedModule {
    private static final String NAME = "ExpoUpdates";
    /* access modifiers changed from: private */
    public static final String TAG = UpdatesModule.class.getSimpleName();
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return NAME;
    }

    public UpdatesModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    private UpdatesInterface getUpdatesService() {
        return (UpdatesInterface) this.mModuleRegistry.getModule(UpdatesInterface.class);
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        try {
            UpdatesInterface updatesService = getUpdatesService();
            if (updatesService != null) {
                hashMap.put("isEmergencyLaunch", Boolean.valueOf(updatesService.isEmergencyLaunch()));
                hashMap.put("isMissingRuntimeVersion", Boolean.valueOf(updatesService.getConfiguration().isMissingRuntimeVersion()));
                UpdateEntity launchedUpdate = updatesService.getLaunchedUpdate();
                if (launchedUpdate != null) {
                    hashMap.put("updateId", launchedUpdate.f1253id.toString());
                    hashMap.put("manifestString", launchedUpdate.metadata != null ? launchedUpdate.metadata.toString() : "{}");
                }
                Map<AssetEntity, String> localAssetFiles = updatesService.getLocalAssetFiles();
                if (localAssetFiles != null) {
                    HashMap hashMap2 = new HashMap();
                    for (AssetEntity next : localAssetFiles.keySet()) {
                        if (next.key != null) {
                            hashMap2.put(next.key, localAssetFiles.get(next));
                        }
                    }
                    hashMap.put("localAssets", hashMap2);
                }
                hashMap.put("isEnabled", Boolean.valueOf(updatesService.getConfiguration().isEnabled()));
                hashMap.put(UpdatesConfiguration.UPDATES_CONFIGURATION_RELEASE_CHANNEL_KEY, updatesService.getConfiguration().getReleaseChannel());
                hashMap.put("isUsingEmbeddedAssets", Boolean.valueOf(updatesService.isUsingEmbeddedAssets()));
            }
        } catch (Exception unused) {
            hashMap.put("isEnabled", false);
            hashMap.put("isMissingRuntimeVersion", Boolean.valueOf(new UpdatesConfiguration().loadValuesFromMetadata(getContext()).isMissingRuntimeVersion()));
        }
        return hashMap;
    }

    @ExpoMethod
    public void reload(final Promise promise) {
        try {
            UpdatesInterface updatesService = getUpdatesService();
            if (!updatesService.canRelaunch()) {
                promise.reject("ERR_UPDATES_DISABLED", "You cannot reload when expo-updates is not enabled.");
            } else {
                updatesService.relaunchReactApplication(new Launcher.LauncherCallback() {
                    public void onFailure(Exception exc) {
                        Log.e(UpdatesModule.TAG, "Failed to relaunch application", exc);
                        promise.reject("ERR_UPDATES_RELOAD", exc.getMessage(), exc);
                    }

                    public void onSuccess() {
                        promise.resolve((Object) null);
                    }
                });
            }
        } catch (IllegalStateException unused) {
            promise.reject("ERR_UPDATES_RELOAD", "The updates module controller has not been properly initialized. If you're using a development client, you cannot use `Updates.reloadAsync`. Otherwise, make sure you have called the native method UpdatesController.initialize().");
        }
    }

    @ExpoMethod
    public void checkForUpdateAsync(final Promise promise) {
        try {
            final UpdatesInterface updatesService = getUpdatesService();
            if (!updatesService.getConfiguration().isEnabled()) {
                promise.reject("ERR_UPDATES_DISABLED", "You cannot check for updates when expo-updates is not enabled.");
                return;
            }
            DatabaseHolder databaseHolder = updatesService.getDatabaseHolder();
            JSONObject serverDefinedHeaders = ManifestMetadata.getServerDefinedHeaders(databaseHolder.getDatabase(), updatesService.getConfiguration());
            databaseHolder.releaseDatabase();
            updatesService.getFileDownloader().downloadManifest(updatesService.getConfiguration(), serverDefinedHeaders, getContext(), new FileDownloader.ManifestDownloadCallback() {
                public void onFailure(String str, Exception exc) {
                    promise.reject("ERR_UPDATES_CHECK", str, exc);
                    Log.e(UpdatesModule.TAG, str, exc);
                }

                public void onSuccess(Manifest manifest) {
                    UpdateEntity launchedUpdate = updatesService.getLaunchedUpdate();
                    Bundle bundle = new Bundle();
                    if (launchedUpdate == null) {
                        bundle.putBoolean("isAvailable", true);
                        bundle.putString("manifestString", manifest.getRawManifestJson().toString());
                        promise.resolve(bundle);
                    } else if (updatesService.getSelectionPolicy().shouldLoadNewUpdate(manifest.getUpdateEntity(), launchedUpdate, manifest.getManifestFilters())) {
                        bundle.putBoolean("isAvailable", true);
                        bundle.putString("manifestString", manifest.getRawManifestJson().toString());
                        promise.resolve(bundle);
                    } else {
                        bundle.putBoolean("isAvailable", false);
                        promise.resolve(bundle);
                    }
                }
            });
        } catch (IllegalStateException unused) {
            promise.reject("ERR_UPDATES_CHECK", "The updates module controller has not been properly initialized. If you're using a development client, you cannot check for updates. Otherwise, make sure you have called the native method UpdatesController.initialize().");
        }
    }

    @ExpoMethod
    public void fetchUpdateAsync(Promise promise) {
        try {
            UpdatesInterface updatesService = getUpdatesService();
            if (!updatesService.getConfiguration().isEnabled()) {
                promise.reject("ERR_UPDATES_DISABLED", "You cannot fetch updates when expo-updates is not enabled.");
            } else {
                AsyncTask.execute(new Runnable(updatesService, promise) {
                    public final /* synthetic */ UpdatesInterface f$1;
                    public final /* synthetic */ Promise f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        UpdatesModule.this.lambda$fetchUpdateAsync$0$UpdatesModule(this.f$1, this.f$2);
                    }
                });
            }
        } catch (IllegalStateException unused) {
            promise.reject("ERR_UPDATES_FETCH", "The updates module controller has not been properly initialized. If you're using a development client, you cannot fetch updates. Otherwise, make sure you have called the native method UpdatesController.initialize().");
        }
    }

    public /* synthetic */ void lambda$fetchUpdateAsync$0$UpdatesModule(final UpdatesInterface updatesInterface, final Promise promise) {
        final DatabaseHolder databaseHolder = updatesInterface.getDatabaseHolder();
        new RemoteLoader(getContext(), updatesInterface.getConfiguration(), databaseHolder.getDatabase(), updatesInterface.getFileDownloader(), updatesInterface.getDirectory()).start(new RemoteLoader.LoaderCallback() {
            public void onFailure(Exception exc) {
                databaseHolder.releaseDatabase();
                promise.reject("ERR_UPDATES_FETCH", "Failed to download new update", exc);
            }

            public boolean onManifestLoaded(Manifest manifest) {
                return updatesInterface.getSelectionPolicy().shouldLoadNewUpdate(manifest.getUpdateEntity(), updatesInterface.getLaunchedUpdate(), manifest.getManifestFilters());
            }

            public void onSuccess(UpdateEntity updateEntity) {
                databaseHolder.releaseDatabase();
                Bundle bundle = new Bundle();
                if (updateEntity == null) {
                    bundle.putBoolean("isNew", false);
                } else {
                    bundle.putBoolean("isNew", true);
                    bundle.putString("manifestString", updateEntity.metadata.toString());
                }
                promise.resolve(bundle);
            }
        });
    }
}
