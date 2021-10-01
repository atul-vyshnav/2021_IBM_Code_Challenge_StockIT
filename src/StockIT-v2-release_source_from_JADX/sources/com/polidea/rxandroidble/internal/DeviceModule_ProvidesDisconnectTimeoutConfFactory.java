package com.polidea.rxandroidble.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.internal.operations.TimeoutConfiguration;
import p030rx.Scheduler;

public final class DeviceModule_ProvidesDisconnectTimeoutConfFactory implements Factory<TimeoutConfiguration> {
    private final Provider<Scheduler> timeoutSchedulerProvider;

    public DeviceModule_ProvidesDisconnectTimeoutConfFactory(Provider<Scheduler> provider) {
        this.timeoutSchedulerProvider = provider;
    }

    public TimeoutConfiguration get() {
        return (TimeoutConfiguration) Preconditions.checkNotNull(DeviceModule.providesDisconnectTimeoutConf(this.timeoutSchedulerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static DeviceModule_ProvidesDisconnectTimeoutConfFactory create(Provider<Scheduler> provider) {
        return new DeviceModule_ProvidesDisconnectTimeoutConfFactory(provider);
    }

    public static TimeoutConfiguration proxyProvidesDisconnectTimeoutConf(Scheduler scheduler) {
        return (TimeoutConfiguration) Preconditions.checkNotNull(DeviceModule.providesDisconnectTimeoutConf(scheduler), "Cannot return null from a non-@Nullable @Provides method");
    }
}
