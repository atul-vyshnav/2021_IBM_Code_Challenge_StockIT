package p022io.nlopez.smartlocation.geofencing.model;

import com.google.android.gms.location.Geofence;

/* renamed from: io.nlopez.smartlocation.geofencing.model.GeofenceModel */
public class GeofenceModel {
    private long expiration;
    private double latitude;
    private int loiteringDelay;
    private double longitude;
    private float radius;
    private String requestId;
    private int transition;

    private GeofenceModel(String str, double d, double d2, float f, long j, int i, int i2) {
        this.requestId = str;
        this.latitude = d;
        this.longitude = d2;
        this.radius = f;
        this.expiration = j;
        this.transition = i;
        this.loiteringDelay = i2;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public float getRadius() {
        return this.radius;
    }

    public long getExpiration() {
        return this.expiration;
    }

    public int getTransition() {
        return this.transition;
    }

    public int getLoiteringDelay() {
        return this.loiteringDelay;
    }

    public Geofence toGeofence() {
        return new Geofence.Builder().setCircularRegion(this.latitude, this.longitude, this.radius).setExpirationDuration(this.expiration).setRequestId(this.requestId).setTransitionTypes(this.transition).setLoiteringDelay(this.loiteringDelay).build();
    }

    /* renamed from: io.nlopez.smartlocation.geofencing.model.GeofenceModel$Builder */
    public static class Builder {
        private long expiration;
        private double latitude;
        private int loiteringDelay;
        private double longitude;
        private float radius;
        private String requestId;
        private int transition;

        public Builder(String str) {
            this.requestId = str;
        }

        public Builder setLatitude(double d) {
            this.latitude = d;
            return this;
        }

        public Builder setLongitude(double d) {
            this.longitude = d;
            return this;
        }

        public Builder setRadius(float f) {
            this.radius = f;
            return this;
        }

        public Builder setExpiration(long j) {
            this.expiration = j;
            return this;
        }

        public Builder setTransition(int i) {
            this.transition = i;
            return this;
        }

        public Builder setLoiteringDelay(int i) {
            this.loiteringDelay = i;
            return this;
        }

        public GeofenceModel build() {
            return new GeofenceModel(this.requestId, this.latitude, this.longitude, this.radius, this.expiration, this.transition, this.loiteringDelay);
        }
    }
}
