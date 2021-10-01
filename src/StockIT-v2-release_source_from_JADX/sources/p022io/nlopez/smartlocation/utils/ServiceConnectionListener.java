package p022io.nlopez.smartlocation.utils;

/* renamed from: io.nlopez.smartlocation.utils.ServiceConnectionListener */
public interface ServiceConnectionListener {
    void onConnected();

    void onConnectionFailed();

    void onConnectionSuspended();
}
