package p022io.nlopez.smartlocation.geocoding.utils;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: io.nlopez.smartlocation.geocoding.utils.LocationAddress */
public class LocationAddress implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LocationAddress createFromParcel(Parcel parcel) {
            return new LocationAddress(parcel);
        }

        public LocationAddress[] newArray(int i) {
            return new LocationAddress[i];
        }
    };
    private Address address;
    private Location location;

    public int describeContents() {
        return 0;
    }

    public LocationAddress(Address address2) {
        this.address = address2;
        Location location2 = new Location(LocationAddress.class.getCanonicalName());
        this.location = location2;
        location2.setLatitude(address2.getLatitude());
        this.location.setLongitude(address2.getLongitude());
    }

    public LocationAddress(Parcel parcel) {
        this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
        this.address = (Address) parcel.readParcelable(Address.class.getClassLoader());
    }

    public Location getLocation() {
        return this.location;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getFormattedAddress() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= this.address.getMaxAddressLineIndex(); i++) {
            sb.append(this.address.getAddressLine(i));
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.location, i);
        parcel.writeParcelable(this.address, i);
    }
}
