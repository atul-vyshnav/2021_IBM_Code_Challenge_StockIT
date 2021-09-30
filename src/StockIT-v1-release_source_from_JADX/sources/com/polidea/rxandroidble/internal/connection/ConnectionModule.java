package com.polidea.rxandroidble.internal.connection;

import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble.ConnectionSetup;
import com.polidea.rxandroidble.Timeout;
import com.polidea.rxandroidble.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble.internal.util.CharacteristicPropertiesParser;
import p030rx.Scheduler;

@Module
public class ConnectionModule {
    public static final String OPERATION_TIMEOUT = "operation-timeout";
    final boolean autoConnect;
    private final Timeout operationTimeout;
    final boolean suppressOperationCheck;

    ConnectionModule(ConnectionSetup connectionSetup) {
        this.autoConnect = connectionSetup.autoConnect;
        this.suppressOperationCheck = connectionSetup.suppressOperationCheck;
        this.operationTimeout = connectionSetup.operationTimeout;
    }

    /* access modifiers changed from: package-private */
    @ConnectionScope
    @Provides
    @Named("autoConnect")
    public boolean provideAutoConnect() {
        return this.autoConnect;
    }

    /* access modifiers changed from: package-private */
    @Provides
    @Named("operation-timeout")
    public TimeoutConfiguration providesOperationTimeoutConf(@Named("timeout") Scheduler scheduler) {
        return new TimeoutConfiguration(this.operationTimeout.timeout, this.operationTimeout.timeUnit, scheduler);
    }

    /* access modifiers changed from: package-private */
    @Provides
    public IllegalOperationHandler provideIllegalOperationHandler(Provider<LoggingIllegalOperationHandler> provider, Provider<ThrowingIllegalOperationHandler> provider2) {
        if (this.suppressOperationCheck) {
            return provider.get();
        }
        return provider2.get();
    }

    /* access modifiers changed from: package-private */
    @Provides
    public CharacteristicPropertiesParser provideCharacteristicPropertiesParser() {
        return new CharacteristicPropertiesParser(1, 2, 4, 8, 16, 32, 64);
    }
}
