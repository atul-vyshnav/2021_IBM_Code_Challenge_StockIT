package org.apache.logging.log4j.status;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class StatusLogger extends AbstractLogger {
    private static final String DEFAULT_STATUS_LEVEL = PROPS.getStringProperty("log4j2.StatusLogger.level");
    private static final int MAX_ENTRIES;
    public static final String MAX_STATUS_ENTRIES = "log4j2.status.entries";
    private static final String NOT_AVAIL = "?";
    private static final PropertiesUtil PROPS;
    private static final StatusLogger STATUS_LOGGER = new StatusLogger();
    private static final long serialVersionUID = 2;
    private final Collection<StatusListener> listeners = new CopyOnWriteArrayList();
    private int listenersLevel = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
    private final ReadWriteLock listenersLock = new ReentrantReadWriteLock();
    private final SimpleLogger logger = new SimpleLogger("StatusLogger", Level.ERROR, false, true, false, false, "", (MessageFactory) null, PROPS, System.err);
    /* access modifiers changed from: private */
    public final Queue<StatusData> messages = new BoundedQueue(MAX_ENTRIES);
    private final Lock msgLock = new ReentrantLock();

    static {
        PropertiesUtil propertiesUtil = new PropertiesUtil("log4j2.StatusLogger.properties");
        PROPS = propertiesUtil;
        MAX_ENTRIES = propertiesUtil.getIntegerProperty(MAX_STATUS_ENTRIES, 200);
    }

    private StatusLogger() {
    }

    public static StatusLogger getLogger() {
        return STATUS_LOGGER;
    }

    public void setLevel(Level level) {
        this.logger.setLevel(level);
    }

    public void registerListener(StatusListener statusListener) {
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.add(statusListener);
            Level statusLevel = statusListener.getStatusLevel();
            if (this.listenersLevel < statusLevel.intLevel()) {
                this.listenersLevel = statusLevel.intLevel();
            }
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    public void removeListener(StatusListener statusListener) {
        closeSilently(statusListener);
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.remove(statusListener);
            int intLevel = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
            for (StatusListener statusLevel : this.listeners) {
                int intLevel2 = statusLevel.getStatusLevel().intLevel();
                if (intLevel < intLevel2) {
                    intLevel = intLevel2;
                }
            }
            this.listenersLevel = intLevel;
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    public Iterable<StatusListener> getListeners() {
        return this.listeners;
    }

    public void reset() {
        this.listenersLock.writeLock().lock();
        try {
            for (StatusListener closeSilently : this.listeners) {
                closeSilently(closeSilently);
            }
        } finally {
            this.listeners.clear();
            this.listenersLock.writeLock().unlock();
            clear();
        }
    }

    private static void closeSilently(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public List<StatusData> getStatusData() {
        this.msgLock.lock();
        try {
            return new ArrayList(this.messages);
        } finally {
            this.msgLock.unlock();
        }
    }

    public void clear() {
        this.msgLock.lock();
        try {
            this.messages.clear();
        } finally {
            this.msgLock.unlock();
        }
    }

    public Level getLevel() {
        return this.logger.getLevel();
    }

    /* JADX INFO: finally extract failed */
    public void logMessage(String str, Level level, Marker marker, Message message, Throwable th) {
        StatusData statusData = new StatusData(str != null ? getStackTraceElement(str, Thread.currentThread().getStackTrace()) : null, level, message, th);
        this.msgLock.lock();
        try {
            this.messages.add(statusData);
            this.msgLock.unlock();
            if (this.listeners.size() > 0) {
                for (StatusListener next : this.listeners) {
                    if (statusData.getLevel().isMoreSpecificThan(next.getStatusLevel())) {
                        next.log(statusData);
                    }
                }
                return;
            }
            this.logger.logMessage(str, level, marker, message, th);
        } catch (Throwable th2) {
            this.msgLock.unlock();
            throw th2;
        }
    }

    private StackTraceElement getStackTraceElement(String str, StackTraceElement[] stackTraceElementArr) {
        if (str == null) {
            return null;
        }
        boolean z = false;
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            String className = stackTraceElement.getClassName();
            if (z && !str.equals(className)) {
                return stackTraceElement;
            }
            if (str.equals(className)) {
                z = true;
            } else if (NOT_AVAIL.equals(className)) {
                break;
            }
        }
        return null;
    }

    public boolean isEnabled(Level level, Marker marker, String str, Throwable th) {
        return isEnabled(level, marker);
    }

    public boolean isEnabled(Level level, Marker marker, String str) {
        return isEnabled(level, marker);
    }

    public boolean isEnabled(Level level, Marker marker, String str, Object... objArr) {
        return isEnabled(level, marker);
    }

    public boolean isEnabled(Level level, Marker marker, Object obj, Throwable th) {
        return isEnabled(level, marker);
    }

    public boolean isEnabled(Level level, Marker marker, Message message, Throwable th) {
        return isEnabled(level, marker);
    }

    public boolean isEnabled(Level level, Marker marker) {
        if (this.listeners.size() > 0) {
            return this.listenersLevel >= level.intLevel();
        }
        return this.logger.isEnabled(level, marker);
    }

    private class BoundedQueue<E> extends ConcurrentLinkedQueue<E> {
        private static final long serialVersionUID = -3945953719763255337L;
        private final int size;

        public BoundedQueue(int i) {
            this.size = i;
        }

        public boolean add(E e) {
            while (StatusLogger.this.messages.size() > this.size) {
                StatusLogger.this.messages.poll();
            }
            return super.add(e);
        }
    }
}
