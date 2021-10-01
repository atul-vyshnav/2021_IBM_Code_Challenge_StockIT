package com.polidea.rxandroidble.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class RxBleServicesLogger_Factory implements Factory<RxBleServicesLogger> {
    private final Provider<CharacteristicPropertiesParser> characteristicPropertiesParserProvider;

    public RxBleServicesLogger_Factory(Provider<CharacteristicPropertiesParser> provider) {
        this.characteristicPropertiesParserProvider = provider;
    }

    public RxBleServicesLogger get() {
        return new RxBleServicesLogger(this.characteristicPropertiesParserProvider.get());
    }

    public static RxBleServicesLogger_Factory create(Provider<CharacteristicPropertiesParser> provider) {
        return new RxBleServicesLogger_Factory(provider);
    }

    public static RxBleServicesLogger newRxBleServicesLogger(CharacteristicPropertiesParser characteristicPropertiesParser) {
        return new RxBleServicesLogger(characteristicPropertiesParser);
    }
}
