package p022io.nlopez.smartlocation;

import android.location.Address;
import android.location.Location;
import java.util.List;

/* renamed from: io.nlopez.smartlocation.OnReverseGeocodingListener */
public interface OnReverseGeocodingListener {
    void onAddressResolved(Location location, List<Address> list);
}
