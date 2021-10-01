package org.apache.logging.log4j.spi;

import java.net.URL;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public class Provider {
    private static final Integer DEFAULT_PRIORITY = -1;
    public static final String FACTORY_PRIORITY = "FactoryPriority";
    private static final Logger LOGGER = StatusLogger.getLogger();
    public static final String LOGGER_CONTEXT_FACTORY = "LoggerContextFactory";
    public static final String THREAD_CONTEXT_MAP = "ThreadContextMap";
    private final ClassLoader classLoader;
    private final String className;
    private final Integer priority;
    private final String threadContextMap;
    private final URL url;

    public Provider(Properties properties, URL url2, ClassLoader classLoader2) {
        this.url = url2;
        this.classLoader = classLoader2;
        String property = properties.getProperty(FACTORY_PRIORITY);
        this.priority = property == null ? DEFAULT_PRIORITY : Integer.valueOf(property);
        this.className = properties.getProperty(LOGGER_CONTEXT_FACTORY);
        this.threadContextMap = properties.getProperty(THREAD_CONTEXT_MAP);
    }

    public Integer getPriority() {
        return this.priority;
    }

    public String getClassName() {
        return this.className;
    }

    public Class<? extends LoggerContextFactory> loadLoggerContextFactory() {
        String str = this.className;
        if (str == null) {
            return null;
        }
        try {
            Class<?> loadClass = this.classLoader.loadClass(str);
            if (LoggerContextFactory.class.isAssignableFrom(loadClass)) {
                return loadClass.asSubclass(LoggerContextFactory.class);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", this.className, this.url.toString(), e);
        }
        return null;
    }

    public String getThreadContextMap() {
        return this.threadContextMap;
    }

    public Class<? extends ThreadContextMap> loadThreadContextMap() {
        String str = this.threadContextMap;
        if (str == null) {
            return null;
        }
        try {
            Class<?> loadClass = this.classLoader.loadClass(str);
            if (ThreadContextMap.class.isAssignableFrom(loadClass)) {
                return loadClass.asSubclass(ThreadContextMap.class);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", this.threadContextMap, this.url.toString(), e);
        }
        return null;
    }

    public URL getUrl() {
        return this.url;
    }
}
