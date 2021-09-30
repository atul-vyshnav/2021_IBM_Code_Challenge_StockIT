package org.unimodules.core.interfaces;

public interface LifecycleEventListener {
    void onHostDestroy();

    void onHostPause();

    void onHostResume();
}
