package com.polidea.multiplatformbleadapter;

public class ConnectionOptions {
    private boolean autoConnect;
    private int connectionPriority;
    private RefreshGattMoment refreshGattMoment;
    private int requestMTU;
    private Long timeoutInMillis;

    public ConnectionOptions(Boolean bool, int i, RefreshGattMoment refreshGattMoment2, Long l, int i2) {
        this.autoConnect = bool.booleanValue();
        this.requestMTU = i;
        this.refreshGattMoment = refreshGattMoment2;
        this.timeoutInMillis = l;
        this.connectionPriority = i2;
    }

    public Boolean getAutoConnect() {
        return Boolean.valueOf(this.autoConnect);
    }

    public int getRequestMTU() {
        return this.requestMTU;
    }

    public RefreshGattMoment getRefreshGattMoment() {
        return this.refreshGattMoment;
    }

    public Long getTimeoutInMillis() {
        return this.timeoutInMillis;
    }

    public int getConnectionPriority() {
        return this.connectionPriority;
    }
}
