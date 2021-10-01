package org.apache.logging.log4j.spi;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.LoaderUtil;

public abstract class AbstractLoggerAdapter<L> implements LoggerAdapter<L> {
    protected final Map<LoggerContext, ConcurrentMap<String, L>> registry = new WeakHashMap();

    /* access modifiers changed from: protected */
    public abstract LoggerContext getContext();

    /* access modifiers changed from: protected */
    public abstract L newLogger(String str, LoggerContext loggerContext);

    public L getLogger(String str) {
        LoggerContext context = getContext();
        ConcurrentMap loggersInContext = getLoggersInContext(context);
        if (loggersInContext.containsKey(str)) {
            return loggersInContext.get(str);
        }
        loggersInContext.putIfAbsent(str, newLogger(str, context));
        return loggersInContext.get(str);
    }

    public ConcurrentMap<String, L> getLoggersInContext(LoggerContext loggerContext) {
        ConcurrentMap<String, L> concurrentMap;
        synchronized (this.registry) {
            concurrentMap = this.registry.get(loggerContext);
            if (concurrentMap == null) {
                concurrentMap = new ConcurrentHashMap<>();
                this.registry.put(loggerContext, concurrentMap);
            }
        }
        return concurrentMap;
    }

    /* access modifiers changed from: protected */
    public LoggerContext getContext(Class<?> cls) {
        ClassLoader classLoader = cls != null ? cls.getClassLoader() : null;
        if (classLoader == null) {
            classLoader = LoaderUtil.getThreadContextClassLoader();
        }
        return LogManager.getContext(classLoader, false);
    }

    public void close() {
        this.registry.clear();
    }
}
