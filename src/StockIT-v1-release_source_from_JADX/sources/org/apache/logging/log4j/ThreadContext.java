package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.apache.logging.log4j.spi.Provider;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;

public final class ThreadContext {
    private static final String DISABLE_ALL = "disableThreadContext";
    private static final String DISABLE_MAP = "disableThreadContextMap";
    private static final String DISABLE_STACK = "disableThreadContextStack";
    public static final Map<String, String> EMPTY_MAP = Collections.emptyMap();
    public static final ThreadContextStack EMPTY_STACK = new EmptyThreadContextStack();
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
    private static ThreadContextMap contextMap;
    private static ThreadContextStack contextStack;
    private static boolean disableAll;
    private static boolean useMap;
    private static boolean useStack;

    public interface ContextStack extends Serializable, Collection<String> {
        List<String> asList();

        ContextStack copy();

        int getDepth();

        ContextStack getImmutableStackOrNull();

        String peek();

        String pop();

        void push(String str);

        void trim(int i);
    }

    private static class EmptyThreadContextStack extends AbstractCollection<String> implements ThreadContextStack {
        private static final Iterator<String> EMPTY_ITERATOR = new EmptyIterator();
        private static final long serialVersionUID = 1;

        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        public ContextStack copy() {
            return this;
        }

        public int getDepth() {
            return 0;
        }

        public ContextStack getImmutableStackOrNull() {
            return this;
        }

        public int hashCode() {
            return 1;
        }

        public String peek() {
            return null;
        }

        public String pop() {
            return null;
        }

        public int size() {
            return 0;
        }

        public void trim(int i) {
        }

        private EmptyThreadContextStack() {
        }

        public void push(String str) {
            throw new UnsupportedOperationException();
        }

        public List<String> asList() {
            return Collections.emptyList();
        }

        public boolean equals(Object obj) {
            return (obj instanceof Collection) && ((Collection) obj).isEmpty();
        }

        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        public boolean add(String str) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends String> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public Iterator<String> iterator() {
            return EMPTY_ITERATOR;
        }
    }

    private static class EmptyIterator<E> implements Iterator<E> {
        public boolean hasNext() {
            return false;
        }

        public void remove() {
        }

        private EmptyIterator() {
        }

        public E next() {
            throw new NoSuchElementException("This is an empty iterator!");
        }
    }

    static {
        init();
    }

    static void init() {
        Class<? extends ThreadContextMap> loadThreadContextMap;
        contextMap = null;
        PropertiesUtil properties = PropertiesUtil.getProperties();
        disableAll = properties.getBooleanProperty(DISABLE_ALL);
        useStack = !properties.getBooleanProperty(DISABLE_STACK) && !disableAll;
        useMap = !properties.getBooleanProperty(DISABLE_MAP) && !disableAll;
        contextStack = new DefaultThreadContextStack(useStack);
        String stringProperty = properties.getStringProperty(THREAD_CONTEXT_KEY);
        ClassLoader findClassLoader = ProviderUtil.findClassLoader();
        if (stringProperty != null) {
            try {
                Class<?> loadClass = findClassLoader.loadClass(stringProperty);
                if (ThreadContextMap.class.isAssignableFrom(loadClass)) {
                    contextMap = (ThreadContextMap) loadClass.newInstance();
                }
            } catch (ClassNotFoundException unused) {
                LOGGER.error("Unable to locate configured ThreadContextMap {}", stringProperty);
            } catch (Exception e) {
                LOGGER.error("Unable to create configured ThreadContextMap {}", stringProperty, e);
            }
        }
        if (contextMap == null && ProviderUtil.hasProviders()) {
            String name = LogManager.getFactory().getClass().getName();
            for (Provider next : ProviderUtil.getProviders()) {
                if (name.equals(next.getClassName()) && (loadThreadContextMap = next.loadThreadContextMap()) != null) {
                    try {
                        contextMap = (ThreadContextMap) loadThreadContextMap.newInstance();
                        break;
                    } catch (Exception e2) {
                        LOGGER.error("Unable to locate or load configured ThreadContextMap {}", next.getThreadContextMap(), e2);
                        contextMap = new DefaultThreadContextMap(useMap);
                    }
                }
            }
        }
        if (contextMap == null) {
            contextMap = new DefaultThreadContextMap(useMap);
        }
    }

    private ThreadContext() {
    }

    public static void put(String str, String str2) {
        contextMap.put(str, str2);
    }

    public static String get(String str) {
        return contextMap.get(str);
    }

    public static void remove(String str) {
        contextMap.remove(str);
    }

    public static void clearMap() {
        contextMap.clear();
    }

    public static void clearAll() {
        clearMap();
        clearStack();
    }

    public static boolean containsKey(String str) {
        return contextMap.containsKey(str);
    }

    public static Map<String, String> getContext() {
        return contextMap.getCopy();
    }

    public static Map<String, String> getImmutableContext() {
        Map<String, String> immutableMapOrNull = contextMap.getImmutableMapOrNull();
        return immutableMapOrNull == null ? EMPTY_MAP : immutableMapOrNull;
    }

    public static boolean isEmpty() {
        return contextMap.isEmpty();
    }

    public static void clearStack() {
        contextStack.clear();
    }

    public static ContextStack cloneStack() {
        return contextStack.copy();
    }

    public static ContextStack getImmutableStack() {
        ContextStack immutableStackOrNull = contextStack.getImmutableStackOrNull();
        return immutableStackOrNull == null ? EMPTY_STACK : immutableStackOrNull;
    }

    public static void setStack(Collection<String> collection) {
        if (!collection.isEmpty() && useStack) {
            contextStack.clear();
            contextStack.addAll(collection);
        }
    }

    public static int getDepth() {
        return contextStack.getDepth();
    }

    public static String pop() {
        return contextStack.pop();
    }

    public static String peek() {
        return contextStack.peek();
    }

    public static void push(String str) {
        contextStack.push(str);
    }

    public static void push(String str, Object... objArr) {
        contextStack.push(ParameterizedMessage.format(str, objArr));
    }

    public static void removeStack() {
        contextStack.clear();
    }

    public static void trim(int i) {
        contextStack.trim(i);
    }
}
