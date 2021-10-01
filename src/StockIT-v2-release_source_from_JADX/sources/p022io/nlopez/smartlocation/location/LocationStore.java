package p022io.nlopez.smartlocation.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import p022io.nlopez.smartlocation.common.Store;

/* renamed from: io.nlopez.smartlocation.location.LocationStore */
public class LocationStore implements Store<Location> {
    private static final String ACCURACY_ID = "ACCURACY";
    private static final String ALTITUDE_ID = "ALTITUDE";
    private static final String BEARING_ID = "BEARING";
    private static final String LATITUDE_ID = "LATITUDE";
    private static final String LONGITUDE_ID = "LONGITUDE";
    private static final String PREFERENCES_FILE = "LOCATION_STORE";
    private static final String PREFIX_ID = (LocationStore.class.getCanonicalName() + ".KEY");
    public static final String PROVIDER = "LocationStore";
    private static final String PROVIDER_ID = "PROVIDER";
    private static final String SPEED_ID = "SPEED";
    private static final String TIME_ID = "TIME";
    private SharedPreferences preferences;

    public LocationStore(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES_FILE, 0);
    }

    public void setPreferences(SharedPreferences sharedPreferences) {
        this.preferences = sharedPreferences;
    }

    public void put(String str, Location location) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putString(getFieldKey(str, PROVIDER_ID), location.getProvider());
        edit.putLong(getFieldKey(str, LATITUDE_ID), Double.doubleToLongBits(location.getLatitude()));
        edit.putLong(getFieldKey(str, LONGITUDE_ID), Double.doubleToLongBits(location.getLongitude()));
        edit.putFloat(getFieldKey(str, ACCURACY_ID), location.getAccuracy());
        edit.putLong(getFieldKey(str, ALTITUDE_ID), Double.doubleToLongBits(location.getAltitude()));
        edit.putFloat(getFieldKey(str, SPEED_ID), location.getSpeed());
        edit.putLong(getFieldKey(str, TIME_ID), location.getTime());
        edit.putFloat(getFieldKey(str, BEARING_ID), location.getBearing());
        edit.apply();
    }

    public Location get(String str) {
        SharedPreferences sharedPreferences = this.preferences;
        if (sharedPreferences == null || !sharedPreferences.contains(getFieldKey(str, LATITUDE_ID)) || !this.preferences.contains(getFieldKey(str, LONGITUDE_ID))) {
            return null;
        }
        Location location = new Location(this.preferences.getString(PROVIDER_ID, PROVIDER));
        location.setLatitude(Double.longBitsToDouble(this.preferences.getLong(getFieldKey(str, LATITUDE_ID), 0)));
        location.setLongitude(Double.longBitsToDouble(this.preferences.getLong(getFieldKey(str, LONGITUDE_ID), 0)));
        location.setAccuracy(this.preferences.getFloat(getFieldKey(str, ACCURACY_ID), 0.0f));
        location.setAltitude(Double.longBitsToDouble(this.preferences.getLong(getFieldKey(str, ALTITUDE_ID), 0)));
        location.setSpeed(this.preferences.getFloat(getFieldKey(str, SPEED_ID), 0.0f));
        location.setTime(this.preferences.getLong(getFieldKey(str, TIME_ID), 0));
        location.setBearing(this.preferences.getFloat(getFieldKey(str, BEARING_ID), 0.0f));
        return location;
    }

    public void remove(String str) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.remove(getFieldKey(str, PROVIDER_ID));
        edit.remove(getFieldKey(str, LATITUDE_ID));
        edit.remove(getFieldKey(str, LONGITUDE_ID));
        edit.remove(getFieldKey(str, ACCURACY_ID));
        edit.remove(getFieldKey(str, ALTITUDE_ID));
        edit.remove(getFieldKey(str, SPEED_ID));
        edit.remove(getFieldKey(str, TIME_ID));
        edit.remove(getFieldKey(str, BEARING_ID));
        edit.apply();
    }

    private String getFieldKey(String str, String str2) {
        return PREFIX_ID + "_" + str + "_" + str2;
    }
}
