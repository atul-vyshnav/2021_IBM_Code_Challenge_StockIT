package org.unimodules.core.interfaces;

public interface RuntimeEnvironmentInterface {

    public interface PlatformVersion {
        int major();

        int minor();

        int patch();

        String prerelease();
    }

    String platformName();

    PlatformVersion platformVersion();
}
