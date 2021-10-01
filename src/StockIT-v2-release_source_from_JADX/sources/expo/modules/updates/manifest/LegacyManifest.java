package expo.modules.updates.manifest;

import android.net.Uri;
import android.util.Log;
import com.amplitude.api.DeviceInfo;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.UpdatesUtils;
import expo.modules.updates.loader.EmbeddedLoader;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LegacyManifest implements Manifest {
    private static String EXPO_ASSETS_URL_BASE = "https://d1wp6m56sqw74a.cloudfront.net/~assets/";
    private static String[] EXPO_DOMAINS = {"expo.io", "exp.host", "expo.test"};
    private static String TAG = Manifest.class.getSimpleName();
    private JSONArray mAssets;
    private Uri mAssetsUrlBase = null;
    private Uri mBundleUrl;
    private Date mCommitTime;
    private UUID mId;
    private JSONObject mManifestJson;
    private Uri mManifestUrl;
    private JSONObject mMetadata;
    private String mRuntimeVersion;
    private String mScopeKey;

    public JSONObject getManifestFilters() {
        return null;
    }

    public JSONObject getServerDefinedHeaders() {
        return null;
    }

    private LegacyManifest(JSONObject jSONObject, Uri uri, UUID uuid, String str, Date date, String str2, JSONObject jSONObject2, Uri uri2, JSONArray jSONArray) {
        this.mManifestJson = jSONObject;
        this.mManifestUrl = uri;
        this.mId = uuid;
        this.mScopeKey = str;
        this.mCommitTime = date;
        this.mRuntimeVersion = str2;
        this.mMetadata = jSONObject2;
        this.mBundleUrl = uri2;
        this.mAssets = jSONArray;
    }

    public static LegacyManifest fromLegacyManifestJson(JSONObject jSONObject, UpdatesConfiguration updatesConfiguration) throws JSONException {
        UUID fromString;
        Date date;
        String str;
        if (isUsingDeveloperTool(jSONObject)) {
            fromString = UUID.randomUUID();
            date = new Date();
        } else {
            fromString = UUID.fromString(jSONObject.getString("releaseId"));
            try {
                date = UpdatesUtils.parseDateString(jSONObject.getString("commitTime"));
            } catch (ParseException e) {
                Log.e(TAG, "Could not parse commitTime", e);
                date = new Date();
            }
        }
        UUID uuid = fromString;
        Date date2 = date;
        String string = jSONObject.getString(UpdatesConfiguration.UPDATES_CONFIGURATION_SDK_VERSION_KEY);
        Object opt = jSONObject.opt(UpdatesConfiguration.UPDATES_CONFIGURATION_RUNTIME_VERSION_KEY);
        if (opt != null) {
            if (opt instanceof String) {
                str = (String) opt;
                return new LegacyManifest(jSONObject, updatesConfiguration.getUpdateUrl(), uuid, updatesConfiguration.getScopeKey(), date2, str, jSONObject, Uri.parse(jSONObject.getString("bundleUrl")), jSONObject.optJSONArray("bundledAssets"));
            } else if (opt instanceof JSONObject) {
                string = ((JSONObject) opt).optString(DeviceInfo.OS_NAME, string);
            }
        }
        str = string;
        return new LegacyManifest(jSONObject, updatesConfiguration.getUpdateUrl(), uuid, updatesConfiguration.getScopeKey(), date2, str, jSONObject, Uri.parse(jSONObject.getString("bundleUrl")), jSONObject.optJSONArray("bundledAssets"));
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
        if (isDevelopmentMode()) {
            updateEntity.status = UpdateStatus.DEVELOPMENT;
        }
        return updateEntity;
    }

    public ArrayList<AssetEntity> getAssetEntityList() {
        String str;
        ArrayList<AssetEntity> arrayList = new ArrayList<>();
        AssetEntity assetEntity = new AssetEntity(this.mManifestJson.optString("bundleKey", (String) null), "js");
        assetEntity.url = this.mBundleUrl;
        assetEntity.isLaunchAsset = true;
        assetEntity.embeddedAssetFilename = EmbeddedLoader.BUNDLE_FILENAME;
        arrayList.add(assetEntity);
        JSONArray jSONArray = this.mAssets;
        if (jSONArray != null && jSONArray.length() > 0) {
            for (int i = 0; i < this.mAssets.length(); i++) {
                try {
                    String string = this.mAssets.getString(i);
                    int lastIndexOf = string.lastIndexOf(46);
                    if (lastIndexOf > 0) {
                        str = string.substring(6, lastIndexOf);
                    } else {
                        str = string.substring(6);
                    }
                    String substring = lastIndexOf > 0 ? string.substring(lastIndexOf + 1) : "";
                    AssetEntity assetEntity2 = new AssetEntity(str + "." + substring, substring);
                    assetEntity2.url = Uri.withAppendedPath(getAssetsUrlBase(), str);
                    assetEntity2.embeddedAssetFilename = string;
                    arrayList.add(assetEntity2);
                } catch (JSONException e) {
                    Log.e(TAG, "Could not read asset from manifest", e);
                }
            }
        }
        return arrayList;
    }

    private Uri getAssetsUrlBase() {
        if (this.mAssetsUrlBase == null) {
            this.mAssetsUrlBase = getAssetsUrlBase(this.mManifestUrl, getRawManifestJson());
        }
        return this.mAssetsUrlBase;
    }

    static Uri getAssetsUrlBase(Uri uri, JSONObject jSONObject) {
        String host = uri.getHost();
        if (host == null) {
            return Uri.parse(EXPO_ASSETS_URL_BASE);
        }
        String[] strArr = EXPO_DOMAINS;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            if (!host.equals(str)) {
                if (!host.endsWith("." + str)) {
                    i++;
                }
            }
            return Uri.parse(EXPO_ASSETS_URL_BASE);
        }
        try {
            return Uri.parse(new URI(uri.toString()).resolve(new URI(jSONObject.optString("assetUrlOverride", "assets"))).toString());
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse assetUrlOverride, falling back to default asset path", e);
            return uri.buildUpon().appendPath("assets").build();
        }
    }

    public boolean isDevelopmentMode() {
        return isDevelopmentMode(this.mManifestJson);
    }

    private static boolean isDevelopmentMode(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            if (!jSONObject.has("developer") || !jSONObject.has("packagerOpts") || !jSONObject.getJSONObject("packagerOpts").optBoolean("dev", false)) {
                return false;
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    private static boolean isUsingDeveloperTool(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            if (!jSONObject.has("developer") || !jSONObject.getJSONObject("developer").has("tool")) {
                return false;
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }
}
