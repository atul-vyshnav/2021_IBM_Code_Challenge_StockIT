package com.google.android.gms.location;

import com.google.android.gms.common.api.C1527Response;

public class LocationSettingsResponse extends C1527Response<LocationSettingsResult> {
    public LocationSettingsStates getLocationSettingsStates() {
        return ((LocationSettingsResult) getResult()).getLocationSettingsStates();
    }
}
