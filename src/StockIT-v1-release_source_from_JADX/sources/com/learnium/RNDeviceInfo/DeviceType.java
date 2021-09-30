package com.learnium.RNDeviceInfo;

import androidx.core.p005os.EnvironmentCompat;

public enum DeviceType {
    HANDSET("Handset"),
    TABLET("Tablet"),
    TV("Tv"),
    UNKNOWN(EnvironmentCompat.MEDIA_UNKNOWN);
    
    private final String value;

    private DeviceType(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}
