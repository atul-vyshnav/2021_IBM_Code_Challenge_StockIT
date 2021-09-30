package p022io.nlopez.smartlocation.geofencing.utils;

import p022io.nlopez.smartlocation.geofencing.model.GeofenceModel;

/* renamed from: io.nlopez.smartlocation.geofencing.utils.TransitionGeofence */
public class TransitionGeofence {
    private GeofenceModel geofenceModel;
    private int transitionType;

    public TransitionGeofence(GeofenceModel geofenceModel2, int i) {
        this.geofenceModel = geofenceModel2;
        this.transitionType = i;
    }

    public GeofenceModel getGeofenceModel() {
        return this.geofenceModel;
    }

    public int getTransitionType() {
        return this.transitionType;
    }
}
