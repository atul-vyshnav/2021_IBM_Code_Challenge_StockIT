package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;

public interface ExtendedLogger extends Logger {
    boolean isEnabled(Level level, Marker marker, Object obj, Throwable th);

    boolean isEnabled(Level level, Marker marker, String str);

    boolean isEnabled(Level level, Marker marker, String str, Throwable th);

    boolean isEnabled(Level level, Marker marker, String str, Object... objArr);

    boolean isEnabled(Level level, Marker marker, Message message, Throwable th);

    void logIfEnabled(String str, Level level, Marker marker, Object obj, Throwable th);

    void logIfEnabled(String str, Level level, Marker marker, String str2);

    void logIfEnabled(String str, Level level, Marker marker, String str2, Throwable th);

    void logIfEnabled(String str, Level level, Marker marker, String str2, Object... objArr);

    void logIfEnabled(String str, Level level, Marker marker, Message message, Throwable th);

    void logMessage(String str, Level level, Marker marker, Message message, Throwable th);
}
