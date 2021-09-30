package p022io.nlopez.smartlocation.activity.config;

/* renamed from: io.nlopez.smartlocation.activity.config.ActivityParams */
public class ActivityParams {
    public static final ActivityParams NORMAL = new Builder().setInterval(500).build();
    private long interval;

    ActivityParams(long j) {
        this.interval = j;
    }

    public long getInterval() {
        return this.interval;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof ActivityParams) && this.interval == ((ActivityParams) obj).interval) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.interval;
        return (int) (j ^ (j >>> 32));
    }

    /* renamed from: io.nlopez.smartlocation.activity.config.ActivityParams$Builder */
    public static class Builder {
        private long interval;

        public Builder setInterval(long j) {
            this.interval = j;
            return this;
        }

        public ActivityParams build() {
            return new ActivityParams(this.interval);
        }
    }
}
