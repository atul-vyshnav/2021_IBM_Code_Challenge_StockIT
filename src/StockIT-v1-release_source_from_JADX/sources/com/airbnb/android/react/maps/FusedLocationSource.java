package com.airbnb.android.react.maps;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnSuccessListener;

public class FusedLocationSource implements LocationSource {
    private final FusedLocationProviderClient fusedLocationClientProviderClient;
    private LocationCallback locationCallback;
    private final LocationRequest locationRequest;

    public FusedLocationSource(Context context) {
        this.fusedLocationClientProviderClient = LocationServices.getFusedLocationProviderClient(context);
        LocationRequest create = LocationRequest.create();
        this.locationRequest = create;
        create.setPriority(100);
        this.locationRequest.setInterval(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    public void setPriority(int i) {
        this.locationRequest.setPriority(i);
    }

    public void setInterval(int i) {
        this.locationRequest.setInterval((long) i);
    }

    public void setFastestInterval(int i) {
        this.locationRequest.setFastestInterval((long) i);
    }

    public void activate(final LocationSource.OnLocationChangedListener onLocationChangedListener) {
        this.fusedLocationClientProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            public void onSuccess(Location location) {
                if (location != null) {
                    onLocationChangedListener.onLocationChanged(location);
                }
            }
        });
        C06852 r0 = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                for (Location onLocationChanged : locationResult.getLocations()) {
                    onLocationChangedListener.onLocationChanged(onLocationChanged);
                }
            }
        };
        this.locationCallback = r0;
        this.fusedLocationClientProviderClient.requestLocationUpdates(this.locationRequest, r0, Looper.myLooper());
    }

    public void deactivate() {
        this.fusedLocationClientProviderClient.removeLocationUpdates(this.locationCallback);
    }
}
