package p022io.nlopez.smartlocation.activity;

import android.content.Context;
import com.google.android.gms.location.DetectedActivity;
import p022io.nlopez.smartlocation.OnActivityUpdatedListener;
import p022io.nlopez.smartlocation.activity.config.ActivityParams;
import p022io.nlopez.smartlocation.utils.Logger;

/* renamed from: io.nlopez.smartlocation.activity.ActivityProvider */
public interface ActivityProvider {
    DetectedActivity getLastActivity();

    void init(Context context, Logger logger);

    void start(OnActivityUpdatedListener onActivityUpdatedListener, ActivityParams activityParams);

    void stop();
}
