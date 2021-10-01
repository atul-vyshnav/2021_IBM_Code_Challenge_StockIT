package org.unimodules.interfaces.constants;

import java.util.List;
import java.util.Map;

public interface ConstantsInterface {
    String getAppId();

    String getAppOwnership();

    Map<String, Object> getConstants();

    String getDeviceName();

    int getDeviceYearClass();

    boolean getIsDevice();

    int getStatusBarHeight();

    List<String> getSystemFonts();

    String getSystemVersion();
}
