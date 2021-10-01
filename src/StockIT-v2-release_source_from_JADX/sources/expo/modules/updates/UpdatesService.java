package expo.modules.updates;

import android.content.Context;
import expo.modules.updates.launcher.Launcher;
import expo.modules.updates.launcher.SelectionPolicy;
import expo.modules.updates.loader.FileDownloader;
import expo.modules.updates.p020db.DatabaseHolder;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;

public class UpdatesService implements InternalModule, UpdatesInterface {
    private static final String TAG = UpdatesService.class.getSimpleName();
    protected Context mContext;

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public UpdatesService(Context context) {
        this.mContext = context;
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(UpdatesInterface.class);
    }

    public UpdatesConfiguration getConfiguration() {
        return UpdatesController.getInstance().getUpdatesConfiguration();
    }

    public SelectionPolicy getSelectionPolicy() {
        return UpdatesController.getInstance().getSelectionPolicy();
    }

    public File getDirectory() {
        return UpdatesController.getInstance().getUpdatesDirectory();
    }

    public FileDownloader getFileDownloader() {
        return UpdatesController.getInstance().getFileDownloader();
    }

    public DatabaseHolder getDatabaseHolder() {
        return UpdatesController.getInstance().getDatabaseHolder();
    }

    public boolean isEmergencyLaunch() {
        return UpdatesController.getInstance().isEmergencyLaunch();
    }

    public boolean isUsingEmbeddedAssets() {
        return UpdatesController.getInstance().isUsingEmbeddedAssets();
    }

    public boolean canRelaunch() {
        return getConfiguration().isEnabled();
    }

    public UpdateEntity getLaunchedUpdate() {
        return UpdatesController.getInstance().getLaunchedUpdate();
    }

    public Map<AssetEntity, String> getLocalAssetFiles() {
        return UpdatesController.getInstance().getLocalAssetFiles();
    }

    public void relaunchReactApplication(Launcher.LauncherCallback launcherCallback) {
        UpdatesController.getInstance().relaunchReactApplication(this.mContext, launcherCallback);
    }
}
