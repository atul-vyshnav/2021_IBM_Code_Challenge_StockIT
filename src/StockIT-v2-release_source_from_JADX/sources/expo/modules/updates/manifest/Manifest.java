package expo.modules.updates.manifest;

import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.ArrayList;
import org.json.JSONObject;

public interface Manifest {
    ArrayList<AssetEntity> getAssetEntityList();

    JSONObject getManifestFilters();

    JSONObject getRawManifestJson();

    JSONObject getServerDefinedHeaders();

    UpdateEntity getUpdateEntity();

    boolean isDevelopmentMode();
}
