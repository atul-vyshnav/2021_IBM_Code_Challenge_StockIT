package com.polidea.multiplatformbleadapter.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

public interface Constants {
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final int MINIMUM_MTU = 23;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BluetoothLogLevel {
        public static final String DEBUG = "Debug";
        public static final String ERROR = "Error";
        public static final String INFO = "Info";
        public static final String NONE = "None";
        public static final String VERBOSE = "Verbose";
        public static final String WARNING = "Warning";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BluetoothState {
        public static final String POWERED_OFF = "PoweredOff";
        public static final String POWERED_ON = "PoweredOn";
        public static final String RESETTING = "Resetting";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String UNKNOWN = "Unknown";
        public static final String UNSUPPORTED = "Unsupported";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionPriority {
        public static final int BALANCED = 0;
        public static final int HIGH = 1;
        public static final int LOW_POWER = 2;
    }
}
