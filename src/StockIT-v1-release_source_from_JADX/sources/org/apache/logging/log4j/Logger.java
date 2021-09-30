package org.apache.logging.log4j;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

public interface Logger {
    void catching(Throwable th);

    void catching(Level level, Throwable th);

    void debug(Object obj);

    void debug(Object obj, Throwable th);

    void debug(String str);

    void debug(String str, Throwable th);

    void debug(String str, Object... objArr);

    void debug(Marker marker, Object obj);

    void debug(Marker marker, Object obj, Throwable th);

    void debug(Marker marker, String str);

    void debug(Marker marker, String str, Throwable th);

    void debug(Marker marker, String str, Object... objArr);

    void debug(Marker marker, Message message);

    void debug(Marker marker, Message message, Throwable th);

    void debug(Message message);

    void debug(Message message, Throwable th);

    void entry();

    void entry(Object... objArr);

    void error(Object obj);

    void error(Object obj, Throwable th);

    void error(String str);

    void error(String str, Throwable th);

    void error(String str, Object... objArr);

    void error(Marker marker, Object obj);

    void error(Marker marker, Object obj, Throwable th);

    void error(Marker marker, String str);

    void error(Marker marker, String str, Throwable th);

    void error(Marker marker, String str, Object... objArr);

    void error(Marker marker, Message message);

    void error(Marker marker, Message message, Throwable th);

    void error(Message message);

    void error(Message message, Throwable th);

    <R> R exit(R r);

    void exit();

    void fatal(Object obj);

    void fatal(Object obj, Throwable th);

    void fatal(String str);

    void fatal(String str, Throwable th);

    void fatal(String str, Object... objArr);

    void fatal(Marker marker, Object obj);

    void fatal(Marker marker, Object obj, Throwable th);

    void fatal(Marker marker, String str);

    void fatal(Marker marker, String str, Throwable th);

    void fatal(Marker marker, String str, Object... objArr);

    void fatal(Marker marker, Message message);

    void fatal(Marker marker, Message message, Throwable th);

    void fatal(Message message);

    void fatal(Message message, Throwable th);

    Level getLevel();

    MessageFactory getMessageFactory();

    String getName();

    void info(Object obj);

    void info(Object obj, Throwable th);

    void info(String str);

    void info(String str, Throwable th);

    void info(String str, Object... objArr);

    void info(Marker marker, Object obj);

    void info(Marker marker, Object obj, Throwable th);

    void info(Marker marker, String str);

    void info(Marker marker, String str, Throwable th);

    void info(Marker marker, String str, Object... objArr);

    void info(Marker marker, Message message);

    void info(Marker marker, Message message, Throwable th);

    void info(Message message);

    void info(Message message, Throwable th);

    boolean isDebugEnabled();

    boolean isDebugEnabled(Marker marker);

    boolean isEnabled(Level level);

    boolean isEnabled(Level level, Marker marker);

    boolean isErrorEnabled();

    boolean isErrorEnabled(Marker marker);

    boolean isFatalEnabled();

    boolean isFatalEnabled(Marker marker);

    boolean isInfoEnabled();

    boolean isInfoEnabled(Marker marker);

    boolean isTraceEnabled();

    boolean isTraceEnabled(Marker marker);

    boolean isWarnEnabled();

    boolean isWarnEnabled(Marker marker);

    void log(Level level, Object obj);

    void log(Level level, Object obj, Throwable th);

    void log(Level level, String str);

    void log(Level level, String str, Throwable th);

    void log(Level level, String str, Object... objArr);

    void log(Level level, Marker marker, Object obj);

    void log(Level level, Marker marker, Object obj, Throwable th);

    void log(Level level, Marker marker, String str);

    void log(Level level, Marker marker, String str, Throwable th);

    void log(Level level, Marker marker, String str, Object... objArr);

    void log(Level level, Marker marker, Message message);

    void log(Level level, Marker marker, Message message, Throwable th);

    void log(Level level, Message message);

    void log(Level level, Message message, Throwable th);

    void printf(Level level, String str, Object... objArr);

    void printf(Level level, Marker marker, String str, Object... objArr);

    <T extends Throwable> T throwing(T t);

    <T extends Throwable> T throwing(Level level, T t);

    void trace(Object obj);

    void trace(Object obj, Throwable th);

    void trace(String str);

    void trace(String str, Throwable th);

    void trace(String str, Object... objArr);

    void trace(Marker marker, Object obj);

    void trace(Marker marker, Object obj, Throwable th);

    void trace(Marker marker, String str);

    void trace(Marker marker, String str, Throwable th);

    void trace(Marker marker, String str, Object... objArr);

    void trace(Marker marker, Message message);

    void trace(Marker marker, Message message, Throwable th);

    void trace(Message message);

    void trace(Message message, Throwable th);

    void warn(Object obj);

    void warn(Object obj, Throwable th);

    void warn(String str);

    void warn(String str, Throwable th);

    void warn(String str, Object... objArr);

    void warn(Marker marker, Object obj);

    void warn(Marker marker, Object obj, Throwable th);

    void warn(Marker marker, String str);

    void warn(Marker marker, String str, Throwable th);

    void warn(Marker marker, String str, Object... objArr);

    void warn(Marker marker, Message message);

    void warn(Marker marker, Message message, Throwable th);

    void warn(Message message);

    void warn(Message message, Throwable th);
}
