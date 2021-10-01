package expo.modules.updates.launcher;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.loader.EmbeddedLoader;
import expo.modules.updates.manifest.BareManifest;
import expo.modules.updates.manifest.Manifest;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.p029io.FileUtils;

public class NoDatabaseLauncher implements Launcher {
    private static final String ERROR_LOG_FILENAME = "expo-error.log";
    private static final String TAG = NoDatabaseLauncher.class.getSimpleName();
    private String mBundleAssetName;
    private Map<AssetEntity, String> mLocalAssetFiles;

    public String getLaunchAssetFile() {
        return null;
    }

    public UpdateEntity getLaunchedUpdate() {
        return null;
    }

    public NoDatabaseLauncher(Context context, UpdatesConfiguration updatesConfiguration) {
        this(context, updatesConfiguration, (Exception) null);
    }

    public NoDatabaseLauncher(Context context, UpdatesConfiguration updatesConfiguration, Exception exc) {
        Manifest readEmbeddedManifest = EmbeddedLoader.readEmbeddedManifest(context, updatesConfiguration);
        if (readEmbeddedManifest != null) {
            if (readEmbeddedManifest instanceof BareManifest) {
                this.mBundleAssetName = EmbeddedLoader.BARE_BUNDLE_FILENAME;
                this.mLocalAssetFiles = null;
            } else {
                this.mBundleAssetName = EmbeddedLoader.BUNDLE_FILENAME;
                this.mLocalAssetFiles = new HashMap();
                Iterator<AssetEntity> it = readEmbeddedManifest.getAssetEntityList().iterator();
                while (it.hasNext()) {
                    AssetEntity next = it.next();
                    Map<AssetEntity, String> map = this.mLocalAssetFiles;
                    map.put(next, "asset:///" + next.embeddedAssetFilename);
                }
            }
            if (exc != null) {
                AsyncTask.execute(new Runnable(context, exc) {
                    public final /* synthetic */ Context f$1;
                    public final /* synthetic */ Exception f$2;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                    }

                    public final void run() {
                        NoDatabaseLauncher.this.lambda$new$0$NoDatabaseLauncher(this.f$1, this.f$2);
                    }
                });
                return;
            }
            return;
        }
        throw new RuntimeException("Failed to launch with embedded update because the embedded manifest was null");
    }

    public String getBundleAssetName() {
        return this.mBundleAssetName;
    }

    public Map<AssetEntity, String> getLocalAssetFiles() {
        return this.mLocalAssetFiles;
    }

    public boolean isUsingEmbeddedAssets() {
        return this.mLocalAssetFiles == null;
    }

    /* access modifiers changed from: private */
    /* renamed from: writeErrorToLog */
    public void lambda$new$0$NoDatabaseLauncher(Context context, Exception exc) {
        try {
            FileUtils.writeStringToFile(new File(context.getFilesDir(), ERROR_LOG_FILENAME), exc.toString(), "UTF-8", true);
        } catch (Exception e) {
            Log.e(TAG, "Failed to write fatal error to log", e);
        }
    }

    public static String consumeErrorLog(Context context) {
        try {
            File file = new File(context.getFilesDir(), ERROR_LOG_FILENAME);
            if (!file.exists()) {
                return null;
            }
            String readFileToString = FileUtils.readFileToString(file, "UTF-8");
            file.delete();
            return readFileToString;
        } catch (Exception e) {
            Log.e(TAG, "Failed to read error log", e);
            return null;
        }
    }
}
