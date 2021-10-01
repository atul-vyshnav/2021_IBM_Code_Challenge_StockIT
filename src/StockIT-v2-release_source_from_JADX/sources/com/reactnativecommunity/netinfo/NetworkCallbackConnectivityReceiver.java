package com.reactnativecommunity.netinfo;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import com.facebook.react.bridge.ReactApplicationContext;
import com.reactnativecommunity.netinfo.types.CellularGeneration;
import com.reactnativecommunity.netinfo.types.ConnectionType;

class NetworkCallbackConnectivityReceiver extends ConnectivityReceiver {
    Network mNetwork = null;
    private final ConnectivityNetworkCallback mNetworkCallback = new ConnectivityNetworkCallback();
    NetworkCapabilities mNetworkCapabilities = null;

    public NetworkCallbackConnectivityReceiver(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    /* access modifiers changed from: package-private */
    public void register() {
        try {
            getConnectivityManager().registerDefaultNetworkCallback(this.mNetworkCallback);
        } catch (SecurityException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void unregister() {
        try {
            getConnectivityManager().unregisterNetworkCallback(this.mNetworkCallback);
        } catch (IllegalArgumentException | SecurityException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void updateAndSend() {
        boolean z;
        ConnectionType connectionType = ConnectionType.UNKNOWN;
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        CellularGeneration cellularGeneration = null;
        boolean z2 = false;
        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(2)) {
                connectionType = ConnectionType.BLUETOOTH;
            } else if (this.mNetworkCapabilities.hasTransport(0)) {
                connectionType = ConnectionType.CELLULAR;
            } else if (this.mNetworkCapabilities.hasTransport(3)) {
                connectionType = ConnectionType.ETHERNET;
            } else if (this.mNetworkCapabilities.hasTransport(1)) {
                connectionType = ConnectionType.WIFI;
            } else if (this.mNetworkCapabilities.hasTransport(4)) {
                connectionType = ConnectionType.VPN;
            }
            NetworkInfo networkInfo = this.mNetwork != null ? getConnectivityManager().getNetworkInfo(this.mNetwork) : null;
            if (Build.VERSION.SDK_INT >= 28) {
                z = !this.mNetworkCapabilities.hasCapability(21);
            } else {
                z = (this.mNetwork == null || networkInfo == null || networkInfo.getDetailedState().equals(NetworkInfo.DetailedState.CONNECTED)) ? false : true;
            }
            if (this.mNetworkCapabilities.hasCapability(12) && this.mNetworkCapabilities.hasCapability(16) && !z) {
                z2 = true;
            }
            if (this.mNetwork != null && connectionType == ConnectionType.CELLULAR && z2) {
                cellularGeneration = CellularGeneration.fromNetworkInfo(networkInfo);
            }
        } else {
            connectionType = ConnectionType.NONE;
        }
        updateConnectivity(connectionType, cellularGeneration, z2);
    }

    private class ConnectivityNetworkCallback extends ConnectivityManager.NetworkCallback {
        private ConnectivityNetworkCallback() {
        }

        public void onAvailable(Network network) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
            networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLosing(Network network, int i) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
            networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLost(Network network) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = null;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = null;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onUnavailable() {
            NetworkCallbackConnectivityReceiver.this.mNetwork = null;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = null;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = networkCapabilities;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            if (NetworkCallbackConnectivityReceiver.this.mNetwork != null) {
                NetworkCallbackConnectivityReceiver.this.mNetwork = network;
                NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
                networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            }
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }
    }
}
