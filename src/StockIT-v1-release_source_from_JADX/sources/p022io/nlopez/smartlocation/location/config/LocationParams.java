package p022io.nlopez.smartlocation.location.config;

import com.google.android.exoplayer2.DefaultRenderersFactory;

/* renamed from: io.nlopez.smartlocation.location.config.LocationParams */
public class LocationParams {
    public static final LocationParams BEST_EFFORT = new Builder().setAccuracy(LocationAccuracy.MEDIUM).setDistance(150.0f).setInterval(2500).build();
    public static final LocationParams LAZY = new Builder().setAccuracy(LocationAccuracy.LOW).setDistance(500.0f).setInterval(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS).build();
    public static final LocationParams NAVIGATION = new Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(0.0f).setInterval(500).build();
    private LocationAccuracy accuracy;
    private float distance;
    private long interval;

    LocationParams(LocationAccuracy locationAccuracy, long j, float f) {
        this.interval = j;
        this.distance = f;
        this.accuracy = locationAccuracy;
    }

    public long getInterval() {
        return this.interval;
    }

    public float getDistance() {
        return this.distance;
    }

    public LocationAccuracy getAccuracy() {
        return this.accuracy;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationParams)) {
            return false;
        }
        LocationParams locationParams = (LocationParams) obj;
        if (Float.compare(locationParams.distance, this.distance) == 0 && this.interval == locationParams.interval && this.accuracy == locationParams.accuracy) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.interval;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        float f = this.distance;
        return ((i + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31) + this.accuracy.hashCode();
    }

    /* renamed from: io.nlopez.smartlocation.location.config.LocationParams$Builder */
    public static class Builder {
        private LocationAccuracy accuracy;
        private float distance;
        private long interval;

        public Builder setAccuracy(LocationAccuracy locationAccuracy) {
            this.accuracy = locationAccuracy;
            return this;
        }

        public Builder setInterval(long j) {
            this.interval = j;
            return this;
        }

        public Builder setDistance(float f) {
            this.distance = f;
            return this;
        }

        public LocationParams build() {
            return new LocationParams(this.accuracy, this.interval, this.distance);
        }
    }
}
