package p022io.nlopez.smartlocation.utils;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* renamed from: io.nlopez.smartlocation.utils.GooglePlayServicesListener */
public interface GooglePlayServicesListener {
    void onConnected(Bundle bundle);

    void onConnectionFailed(ConnectionResult connectionResult);

    void onConnectionSuspended(int i);
}
