package expo.modules.updates.launcher;

import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.Map;

public interface Launcher {

    public interface LauncherCallback {
        void onFailure(Exception exc);

        void onSuccess();
    }

    String getBundleAssetName();

    String getLaunchAssetFile();

    UpdateEntity getLaunchedUpdate();

    Map<AssetEntity, String> getLocalAssetFiles();

    boolean isUsingEmbeddedAssets();
}
