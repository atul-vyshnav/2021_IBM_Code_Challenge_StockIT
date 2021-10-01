package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

public class ExtendedLoggerWrapper extends AbstractLogger {
    private static final long serialVersionUID = 1;
    protected final ExtendedLogger logger;

    public ExtendedLoggerWrapper(ExtendedLogger extendedLogger, String str, MessageFactory messageFactory) {
        super(str, messageFactory);
        this.logger = extendedLogger;
    }

    public Level getLevel() {
        return this.logger.getLevel();
    }

    public boolean isEnabled(Level level, Marker marker, Message message, Throwable th) {
        return this.logger.isEnabled(level, marker, message, th);
    }

    public boolean isEnabled(Level level, Marker marker, Object obj, Throwable th) {
        return this.logger.isEnabled(level, marker, obj, th);
    }

    public boolean isEnabled(Level level, Marker marker, String str) {
        return this.logger.isEnabled(level, marker, str);
    }

    public boolean isEnabled(Level level, Marker marker, String str, Object... objArr) {
        return this.logger.isEnabled(level, marker, str, objArr);
    }

    public boolean isEnabled(Level level, Marker marker, String str, Throwable th) {
        return this.logger.isEnabled(level, marker, str, th);
    }

    public void logMessage(String str, Level level, Marker marker, Message message, Throwable th) {
        this.logger.logMessage(str, level, marker, message, th);
    }
}
