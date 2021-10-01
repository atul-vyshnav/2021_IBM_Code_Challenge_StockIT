package expo.modules.updates.manifest;

import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.loader.EmbeddedLoader;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BareManifest implements Manifest {
    private static String TAG = BareManifest.class.getSimpleName();
    private JSONArray mAssets;
    private Date mCommitTime;
    private UUID mId;
    private JSONObject mManifestJson;
    private JSONObject mMetadata;
    private String mRuntimeVersion;
    private String mScopeKey;

    public JSONObject getManifestFilters() {
        return null;
    }

    public JSONObject getServerDefinedHeaders() {
        return null;
    }

    public boolean isDevelopmentMode() {
        return false;
    }

    private BareManifest(JSONObject jSONObject, UUID uuid, String str, Date date, String str2, JSONObject jSONObject2, JSONArray jSONArray) {
        this.mManifestJson = jSONObject;
        this.mScopeKey = str;
        this.mId = uuid;
        this.mCommitTime = date;
        this.mRuntimeVersion = str2;
        this.mMetadata = jSONObject2;
        this.mAssets = jSONArray;
    }

    public static BareManifest fromManifestJson(JSONObject jSONObject, UpdatesConfiguration updatesConfiguration) throws JSONException {
        UUID fromString = UUID.fromString(jSONObject.getString("id"));
        Date date = new Date(jSONObject.getLong("commitTime"));
        String runtimeVersion = UpdatesUtils.getRuntimeVersion(updatesConfiguration);
        JSONObject optJSONObject = jSONObject.optJSONObject(TtmlNode.TAG_METADATA);
        JSONArray optJSONArray = jSONObject.optJSONArray("assets");
        if (!runtimeVersion.contains(",")) {
            return new BareManifest(jSONObject, fromString, updatesConfiguration.getScopeKey(), date, runtimeVersion, optJSONObject, optJSONArray);
        }
        throw new AssertionError("Should not be initializing a BareManifest in an environment with multiple runtime versions.");
    }

    public JSONObject getRawManifestJson() {
        return this.mManifestJson;
    }

    public UpdateEntity getUpdateEntity() {
        UpdateEntity updateEntity = new UpdateEntity(this.mId, this.mCommitTime, this.mRuntimeVersion, this.mScopeKey);
        JSONObject jSONObject = this.mMetadata;
        if (jSONObject != null) {
            updateEntity.metadata = jSONObject;
        }
        updateEntity.status = UpdateStatus.EMBEDDED;
        return updateEntity;
    }

    public ArrayList<AssetEntity> getAssetEntityList() {
        ArrayList<AssetEntity> arrayList = new ArrayList<>();
        AssetEntity assetEntity = new AssetEntity("bundle-" + this.mManifestJson.optString("id", this.mId.toString()), "js");
        assetEntity.isLaunchAsset = true;
        assetEntity.embeddedAssetFilename = EmbeddedLoader.BARE_BUNDLE_FILENAME;
        arrayList.add(assetEntity);
        JSONArray jSONArray = this.mAssets;
        if (jSONArray != null && jSONArray.length() > 0) {
            for (int i = 0; i < this.mAssets.length(); i++) {
                try {
                    JSONObject jSONObject = this.mAssets.getJSONObject(i);
                    String string = jSONObject.getString("type");
                    AssetEntity assetEntity2 = new AssetEntity(jSONObject.getString("packagerHash") + "." + string, string);
                    assetEntity2.resourcesFilename = jSONObject.optString("resourcesFilename");
                    assetEntity2.resourcesFolder = jSONObject.optString("resourcesFolder");
                    JSONArray optJSONArray = jSONObject.optJSONArray("scales");
                    if (optJSONArray != null && optJSONArray.length() > 1) {
                        assetEntity2.scale = Float.valueOf((float) jSONObject.optDouble("scale"));
                        assetEntity2.scales = new Float[optJSONArray.length()];
                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                            assetEntity2.scales[i2] = Float.valueOf((float) optJSONArray.getDouble(i2));
                        }
                    }
                    arrayList.add(assetEntity2);
                } catch (JSONException e) {
                    Log.e(TAG, "Could not read asset from manifest", e);
                }
            }
        }
        return arrayList;
    }
}
