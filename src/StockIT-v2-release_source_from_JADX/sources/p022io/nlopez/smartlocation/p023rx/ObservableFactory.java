package p022io.nlopez.smartlocation.p023rx;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
import p022io.nlopez.smartlocation.OnActivityUpdatedListener;
import p022io.nlopez.smartlocation.OnGeocodingListener;
import p022io.nlopez.smartlocation.OnGeofencingTransitionListener;
import p022io.nlopez.smartlocation.OnLocationUpdatedListener;
import p022io.nlopez.smartlocation.OnReverseGeocodingListener;
import p022io.nlopez.smartlocation.SmartLocation;
import p022io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import p022io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import p030rx.Observable;
import p030rx.Subscriber;
import p030rx.functions.Action0;

/* renamed from: io.nlopez.smartlocation.rx.ObservableFactory */
public class ObservableFactory {
    private ObservableFactory() {
        throw new AssertionError("This should not be instantiated");
    }

    public static Observable<Location> from(final SmartLocation.LocationControl locationControl) {
        return Observable.create(new Observable.OnSubscribe<Location>() {
            public void call(final Subscriber<? super Location> subscriber) {
                locationControl.start(new OnLocationUpdatedListener() {
                    public void onLocationUpdated(Location location) {
                        subscriber.onNext(location);
                    }
                });
            }
        }).doOnUnsubscribe(new Action0() {
            public void call() {
                locationControl.stop();
            }
        });
    }

    public static Observable<DetectedActivity> from(final SmartLocation.ActivityRecognitionControl activityRecognitionControl) {
        return Observable.create(new Observable.OnSubscribe<DetectedActivity>() {
            public void call(final Subscriber<? super DetectedActivity> subscriber) {
                activityRecognitionControl.start(new OnActivityUpdatedListener() {
                    public void onActivityUpdated(DetectedActivity detectedActivity) {
                        subscriber.onNext(detectedActivity);
                    }
                });
            }
        }).doOnUnsubscribe(new Action0() {
            public void call() {
                activityRecognitionControl.stop();
            }
        });
    }

    public static Observable<TransitionGeofence> from(final SmartLocation.GeofencingControl geofencingControl) {
        return Observable.create(new Observable.OnSubscribe<TransitionGeofence>() {
            public void call(final Subscriber<? super TransitionGeofence> subscriber) {
                geofencingControl.start(new OnGeofencingTransitionListener() {
                    public void onGeofenceTransition(TransitionGeofence transitionGeofence) {
                        subscriber.onNext(transitionGeofence);
                    }
                });
            }
        }).doOnUnsubscribe(new Action0() {
            public void call() {
                geofencingControl.stop();
            }
        });
    }

    public static Observable<List<LocationAddress>> fromAddress(final Context context, final String str, final int i) {
        return Observable.create(new Observable.OnSubscribe<List<LocationAddress>>() {
            public void call(final Subscriber<? super List<LocationAddress>> subscriber) {
                SmartLocation.with(context).geocoding().add(str, i).start((OnGeocodingListener) new OnGeocodingListener() {
                    public void onLocationResolved(String str, List<LocationAddress> list) {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    public static Observable<List<Address>> fromLocation(final Context context, final Location location, final int i) {
        return Observable.create(new Observable.OnSubscribe<List<Address>>() {
            public void call(final Subscriber<? super List<Address>> subscriber) {
                SmartLocation.with(context).geocoding().add(location, i).start((OnReverseGeocodingListener) new OnReverseGeocodingListener() {
                    public void onAddressResolved(Location location, List<Address> list) {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
