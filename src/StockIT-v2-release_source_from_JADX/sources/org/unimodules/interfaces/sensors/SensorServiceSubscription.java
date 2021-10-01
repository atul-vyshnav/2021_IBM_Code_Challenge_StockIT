package org.unimodules.interfaces.sensors;

public interface SensorServiceSubscription {
    Long getUpdateInterval();

    boolean isEnabled();

    void release();

    void setUpdateInterval(long j);

    void start();

    void stop();
}
