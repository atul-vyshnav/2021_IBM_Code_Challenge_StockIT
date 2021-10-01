package org.apache.logging.log4j.simple;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.util.PropertiesUtil;

public class SimpleLoggerContext implements LoggerContext {
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static final String SYSTEM_PREFIX = "org.apache.logging.log4j.simplelog.";
    private final String dateTimeFormat;
    private final Level defaultLevel;
    private final ConcurrentMap<String, ExtendedLogger> loggers = new ConcurrentHashMap();
    private final PropertiesUtil props;
    private final boolean showContextMap;
    private final boolean showDateTime;
    private final boolean showLogName;
    private final boolean showShortName;
    private final PrintStream stream;

    public Object getExternalContext() {
        return null;
    }

    public boolean hasLogger(String str) {
        return false;
    }

    public SimpleLoggerContext() {
        PrintStream printStream;
        PropertiesUtil propertiesUtil = new PropertiesUtil("log4j2.simplelog.properties");
        this.props = propertiesUtil;
        this.showContextMap = propertiesUtil.getBooleanProperty("org.apache.logging.log4j.simplelog.showContextMap", false);
        this.showLogName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showlogname", false);
        this.showShortName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showShortLogname", true);
        this.showDateTime = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showdatetime", false);
        this.defaultLevel = Level.toLevel(this.props.getStringProperty("org.apache.logging.log4j.simplelog.level"), Level.ERROR);
        this.dateTimeFormat = this.showDateTime ? this.props.getStringProperty("org.apache.logging.log4j.simplelog.dateTimeFormat", DEFAULT_DATE_TIME_FORMAT) : null;
        String stringProperty = this.props.getStringProperty("org.apache.logging.log4j.simplelog.logFile", "system.err");
        if ("system.err".equalsIgnoreCase(stringProperty)) {
            printStream = System.err;
        } else if ("system.out".equalsIgnoreCase(stringProperty)) {
            printStream = System.out;
        } else {
            try {
                printStream = new PrintStream(new FileOutputStream(stringProperty));
            } catch (FileNotFoundException unused) {
                printStream = System.err;
            }
        }
        this.stream = printStream;
    }

    public ExtendedLogger getLogger(String str) {
        return getLogger(str, (MessageFactory) null);
    }

    public ExtendedLogger getLogger(String str, MessageFactory messageFactory) {
        if (this.loggers.containsKey(str)) {
            ExtendedLogger extendedLogger = (ExtendedLogger) this.loggers.get(str);
            AbstractLogger.checkMessageFactory(extendedLogger, messageFactory);
            return extendedLogger;
        }
        this.loggers.putIfAbsent(str, new SimpleLogger(str, this.defaultLevel, this.showLogName, this.showShortName, this.showDateTime, this.showContextMap, this.dateTimeFormat, messageFactory, this.props, this.stream));
        return (ExtendedLogger) this.loggers.get(str);
    }
}
