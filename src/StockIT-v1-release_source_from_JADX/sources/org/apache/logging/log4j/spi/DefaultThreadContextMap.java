package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.util.PropertiesUtil;

public class DefaultThreadContextMap implements ThreadContextMap {
    public static final String INHERITABLE_MAP = "isThreadContextMapInheritable";
    private final ThreadLocal<Map<String, String>> localMap;
    private final boolean useMap;

    public DefaultThreadContextMap(boolean z) {
        this.useMap = z;
        this.localMap = createThreadLocalMap(z);
    }

    static ThreadLocal<Map<String, String>> createThreadLocalMap(final boolean z) {
        if (PropertiesUtil.getProperties().getBooleanProperty(INHERITABLE_MAP)) {
            return new InheritableThreadLocal<Map<String, String>>() {
                /* access modifiers changed from: protected */
                public Map<String, String> childValue(Map<String, String> map) {
                    if (map == null || !z) {
                        return null;
                    }
                    return Collections.unmodifiableMap(new HashMap(map));
                }
            };
        }
        return new ThreadLocal<>();
    }

    public void put(String str, String str2) {
        if (this.useMap) {
            Map map = this.localMap.get();
            HashMap hashMap = map == null ? new HashMap() : new HashMap(map);
            hashMap.put(str, str2);
            this.localMap.set(Collections.unmodifiableMap(hashMap));
        }
    }

    public String get(String str) {
        Map map = this.localMap.get();
        if (map == null) {
            return null;
        }
        return (String) map.get(str);
    }

    public void remove(String str) {
        Map map = this.localMap.get();
        if (map != null) {
            HashMap hashMap = new HashMap(map);
            hashMap.remove(str);
            this.localMap.set(Collections.unmodifiableMap(hashMap));
        }
    }

    public void clear() {
        this.localMap.remove();
    }

    public boolean containsKey(String str) {
        Map map = this.localMap.get();
        return map != null && map.containsKey(str);
    }

    public Map<String, String> getCopy() {
        Map map = this.localMap.get();
        return map == null ? new HashMap() : new HashMap(map);
    }

    public Map<String, String> getImmutableMapOrNull() {
        return this.localMap.get();
    }

    public boolean isEmpty() {
        Map map = this.localMap.get();
        return map == null || map.size() == 0;
    }

    public String toString() {
        Map map = this.localMap.get();
        if (map == null) {
            return "{}";
        }
        return map.toString();
    }

    public int hashCode() {
        int i;
        Map map = this.localMap.get();
        if (map == null) {
            i = 0;
        } else {
            i = map.hashCode();
        }
        return ((i + 31) * 31) + (this.useMap ? 1231 : 1237);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((obj instanceof DefaultThreadContextMap) && this.useMap != ((DefaultThreadContextMap) obj).useMap) || !(obj instanceof ThreadContextMap)) {
            return false;
        }
        Map map = this.localMap.get();
        Map<String, String> immutableMapOrNull = ((ThreadContextMap) obj).getImmutableMapOrNull();
        if (map == null) {
            if (immutableMapOrNull != null) {
                return false;
            }
        } else if (!map.equals(immutableMapOrNull)) {
            return false;
        }
        return true;
    }
}
