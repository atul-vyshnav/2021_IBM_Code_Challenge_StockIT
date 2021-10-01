package expo.modules.updates;

import expo.modules.updates.launcher.Launcher;
import expo.modules.updates.launcher.SelectionPolicy;
import expo.modules.updates.loader.FileDownloader;
import expo.modules.updates.p020db.DatabaseHolder;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;
import java.util.Map;

public interface UpdatesInterface {
    boolean canRelaunch();

    UpdatesConfiguration getConfiguration();

    DatabaseHolder getDatabaseHolder();

    File getDirectory();

    FileDownloader getFileDownloader();

    UpdateEntity getLaunchedUpdate();

    Map<AssetEntity, String> getLocalAssetFiles();

    SelectionPolicy getSelectionPolicy();

    boolean isEmergencyLaunch();

    boolean isUsingEmbeddedAssets();

    void relaunchReactApplication(Launcher.LauncherCallback launcherCallback);
}
