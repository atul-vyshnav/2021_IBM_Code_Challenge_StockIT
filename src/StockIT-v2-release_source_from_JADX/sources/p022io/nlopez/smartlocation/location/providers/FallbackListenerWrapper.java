package p022io.nlopez.smartlocation.location.providers;

import p022io.nlopez.smartlocation.location.LocationProvider;
import p022io.nlopez.smartlocation.location.ServiceLocationProvider;
import p022io.nlopez.smartlocation.utils.ServiceConnectionListener;

/* renamed from: io.nlopez.smartlocation.location.providers.FallbackListenerWrapper */
class FallbackListenerWrapper implements ServiceConnectionListener {
    private final ServiceLocationProvider childProvider;
    private final MultiFallbackProvider fallbackProvider;
    private final ServiceConnectionListener listener;

    public FallbackListenerWrapper(MultiFallbackProvider multiFallbackProvider, ServiceLocationProvider serviceLocationProvider) {
        this.fallbackProvider = multiFallbackProvider;
        this.childProvider = serviceLocationProvider;
        this.listener = serviceLocationProvider.getServiceListener();
    }

    public void onConnected() {
        ServiceConnectionListener serviceConnectionListener = this.listener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnected();
        }
    }

    public void onConnectionSuspended() {
        ServiceConnectionListener serviceConnectionListener = this.listener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnectionSuspended();
        }
        runFallback();
    }

    public void onConnectionFailed() {
        ServiceConnectionListener serviceConnectionListener = this.listener;
        if (serviceConnectionListener != null) {
            serviceConnectionListener.onConnectionFailed();
        }
        runFallback();
    }

    private void runFallback() {
        LocationProvider currentProvider = this.fallbackProvider.getCurrentProvider();
        if (currentProvider != null && currentProvider.equals(this.childProvider)) {
            this.fallbackProvider.fallbackProvider();
        }
    }
}
