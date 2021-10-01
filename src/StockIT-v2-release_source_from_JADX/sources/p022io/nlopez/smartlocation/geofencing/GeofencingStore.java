package p022io.nlopez.smartlocation.geofencing;

import android.content.Context;
import android.content.SharedPreferences;
import p022io.nlopez.smartlocation.common.Store;
import p022io.nlopez.smartlocation.geofencing.model.GeofenceModel;

/* renamed from: io.nlopez.smartlocation.geofencing.GeofencingStore */
public class GeofencingStore implements Store<GeofenceModel> {
    private static final String EXPIRATION_ID = "EXPIRATION";
    private static final String LATITUDE_ID = "LATITUDE";
    private static final String LOITERING_DELAY_ID = "LOITERING_DELAY";
    private static final String LONGITUDE_ID = "LONGITUDE";
    private static final String PREFERENCES_FILE = "GEOFENCING_STORE";
    private static final String PREFIX_ID = (GeofencingStore.class.getCanonicalName() + ".KEY");
    private static final String RADIUS_ID = "RADIUS";
    private static final String TRANSITION_ID = "TRANSITION";
    private SharedPreferences preferences;

    public GeofencingStore(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES_FILE, 0);
    }

    public void setPreferences(SharedPreferences sharedPreferences) {
        this.preferences = sharedPreferences;
    }

    public void put(String str, GeofenceModel geofenceModel) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putLong(getFieldKey(str, LATITUDE_ID), Double.doubleToLongBits(geofenceModel.getLatitude()));
        edit.putLong(getFieldKey(str, LONGITUDE_ID), Double.doubleToLongBits(geofenceModel.getLongitude()));
        edit.putFloat(getFieldKey(str, RADIUS_ID), geofenceModel.getRadius());
        edit.putInt(getFieldKey(str, TRANSITION_ID), geofenceModel.getTransition());
        edit.putLong(getFieldKey(str, EXPIRATION_ID), geofenceModel.getExpiration());
        edit.putInt(getFieldKey(str, LOITERING_DELAY_ID), geofenceModel.getLoiteringDelay());
        edit.apply();
    }

    public GeofenceModel get(String str) {
        SharedPreferences sharedPreferences = this.preferences;
        if (sharedPreferences == null || !sharedPreferences.contains(getFieldKey(str, LATITUDE_ID)) || !this.preferences.contains(getFieldKey(str, LONGITUDE_ID))) {
            return null;
        }
        GeofenceModel.Builder builder = new GeofenceModel.Builder(str);
        builder.setLatitude(Double.longBitsToDouble(this.preferences.getLong(getFieldKey(str, LATITUDE_ID), 0)));
        builder.setLongitude(Double.longBitsToDouble(this.preferences.getLong(getFieldKey(str, LONGITUDE_ID), 0)));
        builder.setRadius(this.preferences.getFloat(getFieldKey(str, RADIUS_ID), 0.0f));
        builder.setTransition(this.preferences.getInt(getFieldKey(str, TRANSITION_ID), 0));
        builder.setExpiration(this.preferences.getLong(getFieldKey(str, EXPIRATION_ID), 0));
        builder.setLoiteringDelay(this.preferences.getInt(getFieldKey(str, LOITERING_DELAY_ID), 0));
        return builder.build();
    }

    public void remove(String str) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.remove(getFieldKey(str, LATITUDE_ID));
        edit.remove(getFieldKey(str, LONGITUDE_ID));
        edit.remove(getFieldKey(str, RADIUS_ID));
        edit.remove(getFieldKey(str, TRANSITION_ID));
        edit.remove(getFieldKey(str, EXPIRATION_ID));
        edit.remove(getFieldKey(str, LOITERING_DELAY_ID));
        edit.apply();
    }

    private String getFieldKey(String str, String str2) {
        return PREFIX_ID + "_" + str + "_" + str2;
    }
}
