package expo.modules.updates.manifest;

import expo.modules.updates.UpdatesConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

public class ManifestFactory {
    private static final String TAG = ManifestFactory.class.getSimpleName();

    public static Manifest getManifest(JSONObject jSONObject, ManifestResponse manifestResponse, UpdatesConfiguration updatesConfiguration) throws JSONException {
        if (updatesConfiguration.usesLegacyManifest()) {
            return LegacyManifest.fromLegacyManifestJson(jSONObject, updatesConfiguration);
        }
        return NewManifest.fromManifestJson(jSONObject, manifestResponse, updatesConfiguration);
    }

    public static Manifest getEmbeddedManifest(JSONObject jSONObject, UpdatesConfiguration updatesConfiguration) throws JSONException {
        if (updatesConfiguration.usesLegacyManifest()) {
            if (jSONObject.has("releaseId")) {
                return LegacyManifest.fromLegacyManifestJson(jSONObject, updatesConfiguration);
            }
            return BareManifest.fromManifestJson(jSONObject, updatesConfiguration);
        } else if (jSONObject.has("manifest") || jSONObject.has(UpdatesConfiguration.UPDATES_CONFIGURATION_RUNTIME_VERSION_KEY)) {
            return NewManifest.fromManifestJson(jSONObject, (ManifestResponse) null, updatesConfiguration);
        } else {
            return BareManifest.fromManifestJson(jSONObject, updatesConfiguration);
        }
    }
}
