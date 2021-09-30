package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* renamed from: ai.api.model.Location */
public class Location implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("latitude")
    private final double latitude;
    @SerializedName("longitude")
    private final double longitude;

    public Location(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
