package org.apache.logging.log4j.spi;

import java.io.Serializable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractLogger implements ExtendedLogger, Serializable {
    private static final String CATCHING = "catching";
    public static final Marker CATCHING_MARKER = MarkerManager.getMarker("CATCHING").setParents(EXCEPTION_MARKER);
    public static final Class<? extends MessageFactory> DEFAULT_MESSAGE_FACTORY_CLASS = ParameterizedMessageFactory.class;
    public static final Marker ENTRY_MARKER = MarkerManager.getMarker("ENTRY").setParents(FLOW_MARKER);
    public static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");
    public static final Marker EXIT_MARKER = MarkerManager.getMarker("EXIT").setParents(FLOW_MARKER);
    public static final Marker FLOW_MARKER = MarkerManager.getMarker("FLOW");
    private static final String FQCN = AbstractLogger.class.getName();
    private static final String THROWING = "throwing";
    public static final Marker THROWING_MARKER = MarkerManager.getMarker("THROWING").setParents(EXCEPTION_MARKER);
    private static final long serialVersionUID = 2;
    private final MessageFactory messageFactory;
    private final String name;

    public static void checkMessageFactory(ExtendedLogger extendedLogger, MessageFactory messageFactory2) {
        String name2 = extendedLogger.getName();
        MessageFactory messageFactory3 = extendedLogger.getMessageFactory();
        if (messageFactory2 != null && !messageFactory3.equals(messageFactory2)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", name2, messageFactory3, messageFactory2);
        } else if (messageFactory2 == null && !messageFactory3.getClass().equals(DEFAULT_MESSAGE_FACTORY_CLASS)) {
            StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", name2, messageFactory3, DEFAULT_MESSAGE_FACTORY_CLASS.getName());
        }
    }

    public AbstractLogger() {
        this.name = getClass().getName();
        this.messageFactory = createDefaultMessageFactory();
    }

    public AbstractLogger(String str) {
        this.name = str;
        this.messageFactory = createDefaultMessageFactory();
    }

    public AbstractLogger(String str, MessageFactory messageFactory2) {
        this.name = str;
        this.messageFactory = messageFactory2 == null ? createDefaultMessageFactory() : messageFactory2;
    }

    public void catching(Level level, Throwable th) {
        catching(FQCN, level, th);
    }

    /* access modifiers changed from: protected */
    public void catching(String str, Level level, Throwable th) {
        if (isEnabled(level, CATCHING_MARKER, (Object) null, (Throwable) null)) {
            logMessage(str, level, CATCHING_MARKER, catchingMsg(th), th);
        }
    }

    public void catching(Throwable th) {
        if (isEnabled(Level.ERROR, CATCHING_MARKER, (Object) null, (Throwable) null)) {
            logMessage(FQCN, Level.ERROR, CATCHING_MARKER, catchingMsg(th), th);
        }
    }

    /* access modifiers changed from: protected */
    public Message catchingMsg(Throwable th) {
        return this.messageFactory.newMessage(CATCHING);
    }

    private MessageFactory createDefaultMessageFactory() {
        try {
            return (MessageFactory) DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void debug(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.DEBUG, marker, message, (Throwable) null);
    }

    public void debug(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, marker, message, th);
    }

    public void debug(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.DEBUG, marker, obj, (Throwable) null);
    }

    public void debug(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, marker, obj, th);
    }

    public void debug(Marker marker, String str) {
        logIfEnabled(FQCN, Level.DEBUG, marker, str, (Throwable) null);
    }

    public void debug(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.DEBUG, marker, str, objArr);
    }

    public void debug(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, marker, str, th);
    }

    public void debug(Message message) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, message, (Throwable) null);
    }

    public void debug(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, message, th);
    }

    public void debug(Object obj) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, obj, (Throwable) null);
    }

    public void debug(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, obj, th);
    }

    public void debug(String str) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, str, (Throwable) null);
    }

    public void debug(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, str, objArr);
    }

    public void debug(String str, Throwable th) {
        logIfEnabled(FQCN, Level.DEBUG, (Marker) null, str, th);
    }

    public void entry() {
        entry(FQCN, new Object[0]);
    }

    public void entry(Object... objArr) {
        entry(FQCN, objArr);
    }

    /* access modifiers changed from: protected */
    public void entry(String str, Object... objArr) {
        if (isEnabled(Level.TRACE, ENTRY_MARKER, (Object) null, (Throwable) null)) {
            logIfEnabled(str, Level.TRACE, ENTRY_MARKER, entryMsg(objArr.length, objArr), (Throwable) null);
        }
    }

    /* access modifiers changed from: protected */
    public Message entryMsg(int i, Object... objArr) {
        if (i == 0) {
            return this.messageFactory.newMessage("entry");
        }
        StringBuilder sb = new StringBuilder("entry params(");
        int i2 = 0;
        for (Object obj : objArr) {
            if (obj != null) {
                sb.append(obj.toString());
            } else {
                sb.append("null");
            }
            i2++;
            if (i2 < objArr.length) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return this.messageFactory.newMessage(sb.toString());
    }

    public void error(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.ERROR, marker, message, (Throwable) null);
    }

    public void error(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, marker, message, th);
    }

    public void error(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.ERROR, marker, obj, (Throwable) null);
    }

    public void error(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, marker, obj, th);
    }

    public void error(Marker marker, String str) {
        logIfEnabled(FQCN, Level.ERROR, marker, str, (Throwable) null);
    }

    public void error(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.ERROR, marker, str, objArr);
    }

    public void error(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, marker, str, th);
    }

    public void error(Message message) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, message, (Throwable) null);
    }

    public void error(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, message, th);
    }

    public void error(Object obj) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, obj, (Throwable) null);
    }

    public void error(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, obj, th);
    }

    public void error(String str) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, str, (Throwable) null);
    }

    public void error(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, str, objArr);
    }

    public void error(String str, Throwable th) {
        logIfEnabled(FQCN, Level.ERROR, (Marker) null, str, th);
    }

    public void exit() {
        exit(FQCN, (Object) null);
    }

    public <R> R exit(R r) {
        return exit(FQCN, r);
    }

    /* access modifiers changed from: protected */
    public <R> R exit(String str, R r) {
        if (isEnabled(Level.TRACE, EXIT_MARKER, (Object) null, (Throwable) null)) {
            logIfEnabled(str, Level.TRACE, EXIT_MARKER, exitMsg(r), (Throwable) null);
        }
        return r;
    }

    /* access modifiers changed from: protected */
    public Message exitMsg(Object obj) {
        if (obj == null) {
            return this.messageFactory.newMessage("exit");
        }
        MessageFactory messageFactory2 = this.messageFactory;
        return messageFactory2.newMessage("exit with(" + obj + ')');
    }

    public void fatal(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.FATAL, marker, message, (Throwable) null);
    }

    public void fatal(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, marker, message, th);
    }

    public void fatal(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.FATAL, marker, obj, (Throwable) null);
    }

    public void fatal(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, marker, obj, th);
    }

    public void fatal(Marker marker, String str) {
        logIfEnabled(FQCN, Level.FATAL, marker, str, (Throwable) null);
    }

    public void fatal(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.FATAL, marker, str, objArr);
    }

    public void fatal(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, marker, str, th);
    }

    public void fatal(Message message) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, message, (Throwable) null);
    }

    public void fatal(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, message, th);
    }

    public void fatal(Object obj) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, obj, (Throwable) null);
    }

    public void fatal(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, obj, th);
    }

    public void fatal(String str) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, str, (Throwable) null);
    }

    public void fatal(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, str, objArr);
    }

    public void fatal(String str, Throwable th) {
        logIfEnabled(FQCN, Level.FATAL, (Marker) null, str, th);
    }

    public MessageFactory getMessageFactory() {
        return this.messageFactory;
    }

    public String getName() {
        return this.name;
    }

    public void info(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.INFO, marker, message, (Throwable) null);
    }

    public void info(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, marker, message, th);
    }

    public void info(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.INFO, marker, obj, (Throwable) null);
    }

    public void info(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, marker, obj, th);
    }

    public void info(Marker marker, String str) {
        logIfEnabled(FQCN, Level.INFO, marker, str, (Throwable) null);
    }

    public void info(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.INFO, marker, str, objArr);
    }

    public void info(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, marker, str, th);
    }

    public void info(Message message) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, message, (Throwable) null);
    }

    public void info(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, message, th);
    }

    public void info(Object obj) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, obj, (Throwable) null);
    }

    public void info(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, obj, th);
    }

    public void info(String str) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, str, (Throwable) null);
    }

    public void info(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, str, objArr);
    }

    public void info(String str, Throwable th) {
        logIfEnabled(FQCN, Level.INFO, (Marker) null, str, th);
    }

    public boolean isDebugEnabled() {
        return isEnabled(Level.DEBUG, (Marker) null, (String) null);
    }

    public boolean isDebugEnabled(Marker marker) {
        return isEnabled(Level.DEBUG, marker, (Object) null, (Throwable) null);
    }

    public boolean isEnabled(Level level) {
        return isEnabled(level, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isEnabled(Level level, Marker marker) {
        return isEnabled(level, marker, (Object) null, (Throwable) null);
    }

    public boolean isErrorEnabled() {
        return isEnabled(Level.ERROR, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isErrorEnabled(Marker marker) {
        return isEnabled(Level.ERROR, marker, (Object) null, (Throwable) null);
    }

    public boolean isFatalEnabled() {
        return isEnabled(Level.FATAL, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isFatalEnabled(Marker marker) {
        return isEnabled(Level.FATAL, marker, (Object) null, (Throwable) null);
    }

    public boolean isInfoEnabled() {
        return isEnabled(Level.INFO, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isInfoEnabled(Marker marker) {
        return isEnabled(Level.INFO, marker, (Object) null, (Throwable) null);
    }

    public boolean isTraceEnabled() {
        return isEnabled(Level.TRACE, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isTraceEnabled(Marker marker) {
        return isEnabled(Level.TRACE, marker, (Object) null, (Throwable) null);
    }

    public boolean isWarnEnabled() {
        return isEnabled(Level.WARN, (Marker) null, (Object) null, (Throwable) null);
    }

    public boolean isWarnEnabled(Marker marker) {
        return isEnabled(Level.WARN, marker, (Object) null, (Throwable) null);
    }

    public void log(Level level, Marker marker, Message message) {
        logIfEnabled(FQCN, level, marker, message, (Throwable) null);
    }

    public void log(Level level, Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, level, marker, message, th);
    }

    public void log(Level level, Marker marker, Object obj) {
        logIfEnabled(FQCN, level, marker, obj, (Throwable) null);
    }

    public void log(Level level, Marker marker, Object obj, Throwable th) {
        if (isEnabled(level, marker, obj, th)) {
            logMessage(FQCN, level, marker, obj, th);
        }
    }

    public void log(Level level, Marker marker, String str) {
        logIfEnabled(FQCN, level, marker, str, (Throwable) null);
    }

    public void log(Level level, Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, level, marker, str, objArr);
    }

    public void log(Level level, Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, level, marker, str, th);
    }

    public void log(Level level, Message message) {
        logIfEnabled(FQCN, level, (Marker) null, message, (Throwable) null);
    }

    public void log(Level level, Message message, Throwable th) {
        logIfEnabled(FQCN, level, (Marker) null, message, th);
    }

    public void log(Level level, Object obj) {
        logIfEnabled(FQCN, level, (Marker) null, obj, (Throwable) null);
    }

    public void log(Level level, Object obj, Throwable th) {
        logIfEnabled(FQCN, level, (Marker) null, obj, th);
    }

    public void log(Level level, String str) {
        logIfEnabled(FQCN, level, (Marker) null, str, (Throwable) null);
    }

    public void log(Level level, String str, Object... objArr) {
        logIfEnabled(FQCN, level, (Marker) null, str, objArr);
    }

    public void log(Level level, String str, Throwable th) {
        logIfEnabled(FQCN, level, (Marker) null, str, th);
    }

    public void logIfEnabled(String str, Level level, Marker marker, Message message, Throwable th) {
        if (isEnabled(level, marker, message, th)) {
            logMessage(str, level, marker, message, th);
        }
    }

    public void logIfEnabled(String str, Level level, Marker marker, Object obj, Throwable th) {
        if (isEnabled(level, marker, obj, th)) {
            logMessage(str, level, marker, obj, th);
        }
    }

    public void logIfEnabled(String str, Level level, Marker marker, String str2) {
        if (isEnabled(level, marker, str2)) {
            logMessage(str, level, marker, str2);
        }
    }

    public void logIfEnabled(String str, Level level, Marker marker, String str2, Object... objArr) {
        if (isEnabled(level, marker, str2, objArr)) {
            logMessage(str, level, marker, str2, objArr);
        }
    }

    public void logIfEnabled(String str, Level level, Marker marker, String str2, Throwable th) {
        if (isEnabled(level, marker, str2, th)) {
            logMessage(str, level, marker, str2, th);
        }
    }

    /* access modifiers changed from: protected */
    public void logMessage(String str, Level level, Marker marker, Object obj, Throwable th) {
        logMessage(str, level, marker, this.messageFactory.newMessage(obj), th);
    }

    /* access modifiers changed from: protected */
    public void logMessage(String str, Level level, Marker marker, String str2, Throwable th) {
        logMessage(str, level, marker, this.messageFactory.newMessage(str2), th);
    }

    /* access modifiers changed from: protected */
    public void logMessage(String str, Level level, Marker marker, String str2) {
        Message newMessage = this.messageFactory.newMessage(str2);
        logMessage(str, level, marker, newMessage, newMessage.getThrowable());
    }

    /* access modifiers changed from: protected */
    public void logMessage(String str, Level level, Marker marker, String str2, Object... objArr) {
        Message newMessage = this.messageFactory.newMessage(str2, objArr);
        logMessage(str, level, marker, newMessage, newMessage.getThrowable());
    }

    public void printf(Level level, Marker marker, String str, Object... objArr) {
        if (isEnabled(level, marker, str, objArr)) {
            StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(str, objArr);
            logMessage(FQCN, level, marker, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }

    public void printf(Level level, String str, Object... objArr) {
        if (isEnabled(level, (Marker) null, str, objArr)) {
            StringFormattedMessage stringFormattedMessage = new StringFormattedMessage(str, objArr);
            logMessage(FQCN, level, (Marker) null, stringFormattedMessage, stringFormattedMessage.getThrowable());
        }
    }

    public <T extends Throwable> T throwing(T t) {
        return throwing(FQCN, Level.ERROR, t);
    }

    public <T extends Throwable> T throwing(Level level, T t) {
        return throwing(FQCN, level, t);
    }

    /* access modifiers changed from: protected */
    public <T extends Throwable> T throwing(String str, Level level, T t) {
        if (isEnabled(level, THROWING_MARKER, (Object) null, (Throwable) null)) {
            logMessage(str, level, THROWING_MARKER, throwingMsg(t), t);
        }
        return t;
    }

    /* access modifiers changed from: protected */
    public Message throwingMsg(Throwable th) {
        return this.messageFactory.newMessage(THROWING);
    }

    public void trace(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.TRACE, marker, message, (Throwable) null);
    }

    public void trace(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, marker, message, th);
    }

    public void trace(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.TRACE, marker, obj, (Throwable) null);
    }

    public void trace(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, marker, obj, th);
    }

    public void trace(Marker marker, String str) {
        logIfEnabled(FQCN, Level.TRACE, marker, str, (Throwable) null);
    }

    public void trace(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.TRACE, marker, str, objArr);
    }

    public void trace(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, marker, str, th);
    }

    public void trace(Message message) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, message, (Throwable) null);
    }

    public void trace(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, message, th);
    }

    public void trace(Object obj) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, obj, (Throwable) null);
    }

    public void trace(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, obj, th);
    }

    public void trace(String str) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, str, (Throwable) null);
    }

    public void trace(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, str, objArr);
    }

    public void trace(String str, Throwable th) {
        logIfEnabled(FQCN, Level.TRACE, (Marker) null, str, th);
    }

    public void warn(Marker marker, Message message) {
        logIfEnabled(FQCN, Level.WARN, marker, message, (Throwable) null);
    }

    public void warn(Marker marker, Message message, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, marker, message, th);
    }

    public void warn(Marker marker, Object obj) {
        logIfEnabled(FQCN, Level.WARN, marker, obj, (Throwable) null);
    }

    public void warn(Marker marker, Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, marker, obj, th);
    }

    public void warn(Marker marker, String str) {
        logIfEnabled(FQCN, Level.WARN, marker, str, (Throwable) null);
    }

    public void warn(Marker marker, String str, Object... objArr) {
        logIfEnabled(FQCN, Level.WARN, marker, str, objArr);
    }

    public void warn(Marker marker, String str, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, marker, str, th);
    }

    public void warn(Message message) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, message, (Throwable) null);
    }

    public void warn(Message message, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, message, th);
    }

    public void warn(Object obj) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, obj, (Throwable) null);
    }

    public void warn(Object obj, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, obj, th);
    }

    public void warn(String str) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, str, (Throwable) null);
    }

    public void warn(String str, Object... objArr) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, str, objArr);
    }

    public void warn(String str, Throwable th) {
        logIfEnabled(FQCN, Level.WARN, (Marker) null, str, th);
    }
}
