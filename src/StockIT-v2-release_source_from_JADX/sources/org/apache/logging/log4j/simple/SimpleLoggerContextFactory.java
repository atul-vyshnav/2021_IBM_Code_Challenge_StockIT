package org.apache.logging.log4j.simple;

import java.net.URI;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

public class SimpleLoggerContextFactory implements LoggerContextFactory {
    private static LoggerContext context = new SimpleLoggerContext();

    public void removeContext(LoggerContext loggerContext) {
    }

    public LoggerContext getContext(String str, ClassLoader classLoader, Object obj, boolean z) {
        return context;
    }

    public LoggerContext getContext(String str, ClassLoader classLoader, Object obj, boolean z, URI uri, String str2) {
        return context;
    }
}
