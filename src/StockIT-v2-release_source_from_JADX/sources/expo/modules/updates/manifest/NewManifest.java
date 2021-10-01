package expo.modules.updates.manifest;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.internal.ImagesContract;
import expo.modules.structuredheaders.BooleanItem;
import expo.modules.structuredheaders.ListElement;
import expo.modules.structuredheaders.NumberItem;
import expo.modules.structuredheaders.Parser;
import expo.modules.structuredheaders.StringItem;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.loader.EmbeddedLoader;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewManifest implements Manifest {
    private static String TAG = Manifest.class.getSimpleName();
    private JSONArray mAssets;
    private Date mCommitTime;
    private UUID mId;
    private JSONObject mLaunchAsset;
    private String mManifestFilters;
    private JSONObject mManifestJson;
    private String mRuntimeVersion;
    private String mScopeKey;
    private String mServerDefinedHeaders;

    public boolean isDevelopmentMode() {
        return false;
    }

    private NewManifest(JSONObject jSONObject, UUID uuid, String str, Date date, String str2, JSONObject jSONObject2, JSONArray jSONArray, String str3, String str4) {
        this.mManifestJson = jSONObject;
        this.mId = uuid;
        this.mScopeKey = str;
        this.mCommitTime = date;
        this.mRuntimeVersion = str2;
        this.mLaunchAsset = jSONObject2;
        this.mAssets = jSONArray;
        this.mServerDefinedHeaders = str3;
        this.mManifestFilters = str4;
    }

    public static NewManifest fromManifestJson(JSONObject jSONObject, ManifestResponse manifestResponse, UpdatesConfiguration updatesConfiguration) throws JSONException {
        Date date;
        if (jSONObject.has("manifest")) {
            jSONObject = jSONObject.getJSONObject("manifest");
        }
        JSONObject jSONObject2 = jSONObject;
        UUID fromString = UUID.fromString(jSONObject2.getString("id"));
        String string = jSONObject2.getString(UpdatesConfiguration.UPDATES_CONFIGURATION_RUNTIME_VERSION_KEY);
        JSONObject jSONObject3 = jSONObject2.getJSONObject("launchAsset");
        JSONArray optJSONArray = jSONObject2.optJSONArray("assets");
        try {
            date = UpdatesUtils.parseDateString(jSONObject2.getString("createdAt"));
        } catch (ParseException e) {
            Log.e(TAG, "Could not parse manifest createdAt string; falling back to current time", e);
            date = new Date();
        }
        Date date2 = date;
        String str = null;
        String header = manifestResponse != null ? manifestResponse.header("expo-server-defined-headers") : null;
        if (manifestResponse != null) {
            str = manifestResponse.header("expo-manifest-filters");
        }
        return new NewManifest(jSONObject2, fromString, updatesConfiguration.getScopeKey(), date2, string, jSONObject3, optJSONArray, header, str);
    }

    public JSONObject getServerDefinedHeaders() {
        String str = this.mServerDefinedHeaders;
        if (str == null) {
            return null;
        }
        return headerDictionaryToJSONObject(str);
    }

    public JSONObject getManifestFilters() {
        String str = this.mManifestFilters;
        if (str == null) {
            return null;
        }
        return headerDictionaryToJSONObject(str);
    }

    public JSONObject getRawManifestJson() {
        return this.mManifestJson;
    }

    public UpdateEntity getUpdateEntity() {
        UpdateEntity updateEntity = new UpdateEntity(this.mId, this.mCommitTime, this.mRuntimeVersion, this.mScopeKey);
        updateEntity.metadata = this.mManifestJson;
        return updateEntity;
    }

    public ArrayList<AssetEntity> getAssetEntityList() {
        ArrayList<AssetEntity> arrayList = new ArrayList<>();
        try {
            AssetEntity assetEntity = new AssetEntity(this.mLaunchAsset.getString("key"), this.mLaunchAsset.getString("contentType"));
            assetEntity.url = Uri.parse(this.mLaunchAsset.getString(ImagesContract.URL));
            assetEntity.isLaunchAsset = true;
            assetEntity.embeddedAssetFilename = EmbeddedLoader.BUNDLE_FILENAME;
            arrayList.add(assetEntity);
        } catch (JSONException e) {
            Log.e(TAG, "Could not read launch asset from manifest", e);
        }
        JSONArray jSONArray = this.mAssets;
        if (jSONArray != null && jSONArray.length() > 0) {
            for (int i = 0; i < this.mAssets.length(); i++) {
                try {
                    JSONObject jSONObject = this.mAssets.getJSONObject(i);
                    AssetEntity assetEntity2 = new AssetEntity(jSONObject.getString("key"), jSONObject.getString("contentType"));
                    assetEntity2.url = Uri.parse(jSONObject.getString(ImagesContract.URL));
                    assetEntity2.embeddedAssetFilename = jSONObject.optString("embeddedAssetFilename");
                    arrayList.add(assetEntity2);
                } catch (JSONException e2) {
                    Log.e(TAG, "Could not read asset from manifest", e2);
                }
            }
        }
        return arrayList;
    }

    static JSONObject headerDictionaryToJSONObject(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            Map map = new Parser(str).parseDictionary().get();
            for (String str2 : map.keySet()) {
                ListElement listElement = (ListElement) map.get(str2);
                if ((listElement instanceof StringItem) || (listElement instanceof BooleanItem) || (listElement instanceof NumberItem)) {
                    jSONObject.put(str2, listElement.get());
                }
            }
            return jSONObject;
        } catch (expo.modules.structuredheaders.ParseException | JSONException e) {
            Log.e(TAG, "Failed to parse manifest header content", e);
            return null;
        }
    }
}
