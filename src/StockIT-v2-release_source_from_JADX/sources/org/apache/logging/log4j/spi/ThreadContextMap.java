package org.apache.logging.log4j.spi;

import java.util.Map;

public interface ThreadContextMap {
    void clear();

    boolean containsKey(String str);

    String get(String str);

    Map<String, String> getCopy();

    Map<String, String> getImmutableMapOrNull();

    boolean isEmpty();

    void put(String str, String str2);

    void remove(String str);
}
