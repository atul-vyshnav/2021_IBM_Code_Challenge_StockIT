package org.apache.logging.log4j.spi;

import java.net.URI;

public interface LoggerContextFactory {
    LoggerContext getContext(String str, ClassLoader classLoader, Object obj, boolean z);

    LoggerContext getContext(String str, ClassLoader classLoader, Object obj, boolean z, URI uri, String str2);

    void removeContext(LoggerContext loggerContext);
}
