package expo.modules.updates.manifest;

import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.p020db.UpdatesDatabase;
import java.util.HashMap;
import org.json.JSONObject;

public class ManifestMetadata {
    public static final String MANIFEST_FILTERS_KEY = "manifestFilters";
    public static final String MANIFEST_SERVER_DEFINED_HEADERS_KEY = "serverDefinedHeaders";
    private static final String TAG = ManifestMetadata.class.getSimpleName();

    private static JSONObject getJSONObject(String str, UpdatesDatabase updatesDatabase, UpdatesConfiguration updatesConfiguration) {
        try {
            String loadJSONStringForKey = updatesDatabase.jsonDataDao().loadJSONStringForKey(str, updatesConfiguration.getScopeKey());
            if (loadJSONStringForKey != null) {
                return new JSONObject(loadJSONStringForKey);
            }
            return null;
        } catch (Exception e) {
            String str2 = TAG;
            Log.e(str2, "Error retrieving " + str + " from database", e);
            return null;
        }
    }

    public static JSONObject getServerDefinedHeaders(UpdatesDatabase updatesDatabase, UpdatesConfiguration updatesConfiguration) {
        return getJSONObject(MANIFEST_SERVER_DEFINED_HEADERS_KEY, updatesDatabase, updatesConfiguration);
    }

    public static JSONObject getManifestFilters(UpdatesDatabase updatesDatabase, UpdatesConfiguration updatesConfiguration) {
        return getJSONObject(MANIFEST_FILTERS_KEY, updatesDatabase, updatesConfiguration);
    }

    public static void saveMetadata(Manifest manifest, UpdatesDatabase updatesDatabase, UpdatesConfiguration updatesConfiguration) {
        HashMap hashMap = new HashMap();
        if (manifest.getServerDefinedHeaders() != null) {
            hashMap.put(MANIFEST_SERVER_DEFINED_HEADERS_KEY, manifest.getServerDefinedHeaders().toString());
        }
        if (manifest.getManifestFilters() != null) {
            hashMap.put(MANIFEST_FILTERS_KEY, manifest.getManifestFilters().toString());
        }
        if (hashMap.size() > 0) {
            updatesDatabase.jsonDataDao().setMultipleFields(hashMap, updatesConfiguration.getScopeKey());
        }
    }
}
