package org.apache.logging.log4j;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.apache.logging.log4j.simple.SimpleLoggerContextFactory;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.spi.Provider;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;
import org.apache.logging.log4j.util.ReflectionUtil;

public class LogManager {
    public static final String FACTORY_PROPERTY_NAME = "log4j2.loggerContextFactory";
    private static final String FQCN = LogManager.class.getName();
    private static final Logger LOGGER = StatusLogger.getLogger();
    public static final String ROOT_LOGGER_NAME = "";
    private static volatile LoggerContextFactory factory;

    static {
        String stringProperty = PropertiesUtil.getProperties().getStringProperty(FACTORY_PROPERTY_NAME);
        if (stringProperty != null) {
            try {
                Class<?> loadClass = LoaderUtil.loadClass(stringProperty);
                if (LoggerContextFactory.class.isAssignableFrom(loadClass)) {
                    factory = (LoggerContextFactory) loadClass.asSubclass(LoggerContextFactory.class).newInstance();
                }
            } catch (ClassNotFoundException unused) {
                LOGGER.error("Unable to locate configured LoggerContextFactory {}", stringProperty);
            } catch (Exception e) {
                LOGGER.error("Unable to create configured LoggerContextFactory {}", stringProperty, e);
            }
        }
        if (factory == null) {
            TreeMap treeMap = new TreeMap();
            if (ProviderUtil.hasProviders()) {
                for (Provider next : ProviderUtil.getProviders()) {
                    Class<? extends LoggerContextFactory> loadLoggerContextFactory = next.loadLoggerContextFactory();
                    if (loadLoggerContextFactory != null) {
                        try {
                            treeMap.put(next.getPriority(), loadLoggerContextFactory.newInstance());
                        } catch (Exception e2) {
                            LOGGER.error("Unable to create class {} specified in {}", loadLoggerContextFactory.getName(), next.getUrl().toString(), e2);
                        }
                    }
                }
                if (treeMap.isEmpty()) {
                    LOGGER.error("Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...");
                    factory = new SimpleLoggerContextFactory();
                    return;
                }
                StringBuilder sb = new StringBuilder("Multiple logging implementations found: \n");
                for (Map.Entry entry : treeMap.entrySet()) {
                    sb.append("Factory: ");
                    sb.append(((LoggerContextFactory) entry.getValue()).getClass().getName());
                    sb.append(", Weighting: ");
                    sb.append(entry.getKey());
                    sb.append(10);
                }
                factory = (LoggerContextFactory) treeMap.get(treeMap.lastKey());
                sb.append("Using factory: ");
                sb.append(factory.getClass().getName());
                LOGGER.warn(sb.toString());
                return;
            }
            LOGGER.error("Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...");
            factory = new SimpleLoggerContextFactory();
        }
    }

    public static boolean exists(String str) {
        return getContext().hasLogger(str);
    }

    public static LoggerContext getContext() {
        return factory.getContext(FQCN, (ClassLoader) null, (Object) null, true);
    }

    public static LoggerContext getContext(boolean z) {
        return factory.getContext(FQCN, (ClassLoader) null, (Object) null, z, (URI) null, (String) null);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean z) {
        return factory.getContext(FQCN, classLoader, (Object) null, z);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean z, Object obj) {
        return factory.getContext(FQCN, classLoader, obj, z);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean z, URI uri) {
        return factory.getContext(FQCN, classLoader, (Object) null, z, uri, (String) null);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean z, Object obj, URI uri) {
        return factory.getContext(FQCN, classLoader, obj, z, uri, (String) null);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean z, Object obj, URI uri, String str) {
        return factory.getContext(FQCN, classLoader, obj, z, uri, str);
    }

    protected static LoggerContext getContext(String str, boolean z) {
        return factory.getContext(str, (ClassLoader) null, (Object) null, z);
    }

    protected static LoggerContext getContext(String str, ClassLoader classLoader, boolean z) {
        return factory.getContext(str, classLoader, (Object) null, z);
    }

    public static LoggerContextFactory getFactory() {
        return factory;
    }

    public static void setFactory(LoggerContextFactory loggerContextFactory) {
        factory = loggerContextFactory;
    }

    public static Logger getFormatterLogger(Class<?> cls) {
        if (cls == null) {
            cls = ReflectionUtil.getCallerClass(2);
        }
        return getLogger(cls, (MessageFactory) StringFormatterMessageFactory.INSTANCE);
    }

    public static Logger getFormatterLogger(Object obj) {
        return getLogger(obj != null ? obj.getClass() : ReflectionUtil.getCallerClass(2), (MessageFactory) StringFormatterMessageFactory.INSTANCE);
    }

    public static Logger getFormatterLogger(String str) {
        return str == null ? getFormatterLogger(ReflectionUtil.getCallerClass(2)) : getLogger(str, (MessageFactory) StringFormatterMessageFactory.INSTANCE);
    }

    public static Logger getLogger() {
        return getLogger(ReflectionUtil.getCallerClass(2));
    }

    public static Logger getLogger(Class<?> cls) {
        if (cls != null) {
            return getContext(cls.getClassLoader(), false).getLogger(cls.getName());
        }
        Class<?> callerClass = ReflectionUtil.getCallerClass(2);
        if (callerClass != null) {
            return getLogger(callerClass);
        }
        throw new UnsupportedOperationException("No class provided, and an appropriate one cannot be found.");
    }

    public static Logger getLogger(Class<?> cls, MessageFactory messageFactory) {
        if (cls != null) {
            return getContext(cls.getClassLoader(), false).getLogger(cls.getName(), messageFactory);
        }
        Class<?> callerClass = ReflectionUtil.getCallerClass(2);
        if (callerClass != null) {
            return getLogger(callerClass, messageFactory);
        }
        throw new UnsupportedOperationException("No class provided, and an appropriate one cannot be found.");
    }

    public static Logger getLogger(MessageFactory messageFactory) {
        return getLogger(ReflectionUtil.getCallerClass(2), messageFactory);
    }

    public static Logger getLogger(Object obj) {
        return getLogger(obj != null ? obj.getClass() : ReflectionUtil.getCallerClass(2));
    }

    public static Logger getLogger(Object obj, MessageFactory messageFactory) {
        return getLogger(obj != null ? obj.getClass() : ReflectionUtil.getCallerClass(2), messageFactory);
    }

    public static Logger getLogger(String str) {
        return str != null ? getContext(false).getLogger(str) : getLogger(ReflectionUtil.getCallerClass(2));
    }

    public static Logger getLogger(String str, MessageFactory messageFactory) {
        return str != null ? getContext(false).getLogger(str, messageFactory) : getLogger(ReflectionUtil.getCallerClass(2), messageFactory);
    }

    protected static Logger getLogger(String str, String str2) {
        return factory.getContext(str, (ClassLoader) null, (Object) null, false).getLogger(str2);
    }

    public static Logger getRootLogger() {
        return getLogger("");
    }

    protected LogManager() {
    }
}
