package org.unimodules.core.interfaces.services;

import org.unimodules.core.errors.CurrentActivityNotFoundException;

public interface KeepAwakeManager {
    void activate(String str, Runnable runnable) throws CurrentActivityNotFoundException;

    void deactivate(String str, Runnable runnable) throws CurrentActivityNotFoundException;

    boolean isActivated();
}
