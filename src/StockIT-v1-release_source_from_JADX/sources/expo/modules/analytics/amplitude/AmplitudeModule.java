package expo.modules.analytics.amplitude;

import android.content.Context;
import com.amplitude.api.Amplitude;
import com.amplitude.api.AmplitudeClient;
import com.amplitude.api.TrackingOptions;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ExpoMethod;

public class AmplitudeModule extends ExportedModule {
    private AmplitudeClient mClient;
    private TrackingOptions mPendingTrackingOptions;

    public String getName() {
        return "ExpoAmplitude";
    }

    public AmplitudeModule(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AmplitudeClient getClient(String str) {
        return Amplitude.getInstance(str);
    }

    @ExpoMethod
    public void initializeAsync(String str, Promise promise) {
        AmplitudeClient client = getClient(str);
        this.mClient = client;
        TrackingOptions trackingOptions = this.mPendingTrackingOptions;
        if (trackingOptions != null) {
            client.setTrackingOptions(trackingOptions);
        }
        this.mClient.initialize(getContext(), str);
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void setUserIdAsync(String str, Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.setUserId(str);
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void setUserPropertiesAsync(Map<String, Object> map, Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.setUserProperties(new JSONObject(map));
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void clearUserPropertiesAsync(Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.clearUserProperties();
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void logEventAsync(String str, Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.logEvent(str);
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void logEventWithPropertiesAsync(String str, Map<String, Object> map, Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.logEvent(str, new JSONObject(map));
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void setGroupAsync(String str, List<Object> list, Promise promise) {
        if (!rejectUnlessInitialized(promise)) {
            this.mClient.setGroup(str, new JSONArray(list));
            promise.resolve((Object) null);
        }
    }

    @ExpoMethod
    public void setTrackingOptionsAsync(ReadableArguments readableArguments, Promise promise) {
        TrackingOptions trackingOptions = new TrackingOptions();
        if (readableArguments.getBoolean("disableAdid")) {
            trackingOptions.disableAdid();
        }
        if (readableArguments.getBoolean("disableCarrier")) {
            trackingOptions.disableCarrier();
        }
        if (readableArguments.getBoolean("disableCity")) {
            trackingOptions.disableCity();
        }
        if (readableArguments.getBoolean("disableCountry")) {
            trackingOptions.disableCountry();
        }
        if (readableArguments.getBoolean("disableDeviceBrand")) {
            trackingOptions.disableDeviceBrand();
        }
        if (readableArguments.getBoolean("disableDeviceModel")) {
            trackingOptions.disableDeviceModel();
        }
        if (readableArguments.getBoolean("disableDMA")) {
            trackingOptions.disableDma();
        }
        if (readableArguments.getBoolean("disableIPAddress")) {
            trackingOptions.disableIpAddress();
        }
        if (readableArguments.getBoolean("disableLanguage")) {
            trackingOptions.disableLanguage();
        }
        if (readableArguments.getBoolean("disableLatLng")) {
            trackingOptions.disableLatLng();
        }
        if (readableArguments.getBoolean("disableOSName")) {
            trackingOptions.disableOsName();
        }
        if (readableArguments.getBoolean("disableOSVersion")) {
            trackingOptions.disableOsVersion();
        }
        if (readableArguments.getBoolean("disablePlatform")) {
            trackingOptions.disablePlatform();
        }
        if (readableArguments.getBoolean("disableRegion")) {
            trackingOptions.disableRegion();
        }
        if (readableArguments.getBoolean("disableVersionName")) {
            trackingOptions.disableVersionName();
        }
        AmplitudeClient amplitudeClient = this.mClient;
        if (amplitudeClient != null) {
            amplitudeClient.setTrackingOptions(trackingOptions);
        } else {
            this.mPendingTrackingOptions = trackingOptions;
        }
        promise.resolve((Object) null);
    }

    private boolean rejectUnlessInitialized(Promise promise) {
        if (this.mClient != null) {
            return false;
        }
        promise.reject("E_NO_INIT", "Amplitude client has not been initialized, are you sure you have configured it with #init(apiKey)?");
        return true;
    }
}
