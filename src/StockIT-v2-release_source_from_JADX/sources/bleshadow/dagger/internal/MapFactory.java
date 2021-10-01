package bleshadow.dagger.internal;

import bleshadow.javax.inject.Provider;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MapFactory<K, V> implements Factory<Map<K, V>> {
    private static final Provider<Map<Object, Object>> EMPTY = InstanceFactory.create(Collections.emptyMap());
    private final Map<K, Provider<V>> contributingMap;

    public static <K, V> Builder<K, V> builder(int i) {
        return new Builder<>(i);
    }

    public static <K, V> Provider<Map<K, V>> emptyMapProvider() {
        return EMPTY;
    }

    private MapFactory(Map<K, Provider<V>> map) {
        this.contributingMap = Collections.unmodifiableMap(map);
    }

    public Map<K, V> get() {
        LinkedHashMap newLinkedHashMapWithExpectedSize = DaggerCollections.newLinkedHashMapWithExpectedSize(this.contributingMap.size());
        for (Map.Entry next : this.contributingMap.entrySet()) {
            newLinkedHashMapWithExpectedSize.put(next.getKey(), ((Provider) next.getValue()).get());
        }
        return Collections.unmodifiableMap(newLinkedHashMapWithExpectedSize);
    }

    public static final class Builder<K, V> {
        private final LinkedHashMap<K, Provider<V>> map;

        private Builder(int i) {
            this.map = DaggerCollections.newLinkedHashMapWithExpectedSize(i);
        }

        public Builder<K, V> put(K k, Provider<V> provider) {
            this.map.put(Preconditions.checkNotNull(k, "key"), Preconditions.checkNotNull(provider, "provider"));
            return this;
        }

        public MapFactory<K, V> build() {
            return new MapFactory<>(this.map);
        }
    }
}
