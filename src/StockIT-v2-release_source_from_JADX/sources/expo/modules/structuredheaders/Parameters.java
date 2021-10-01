package expo.modules.structuredheaders;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Parameters implements Map<String, Item<? extends Object>> {
    public static final Parameters EMPTY = new Parameters(Collections.emptyMap());
    private final Map<String, Item<? extends Object>> delegate;

    private Parameters(Map<String, Object> map) {
        this.delegate = Collections.unmodifiableMap(checkAndTransformMap(map));
    }

    public static Parameters valueOf(Map<String, Object> map) {
        return new Parameters(map);
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        for (Map.Entry next : this.delegate.entrySet()) {
            sb.append(';');
            sb.append((String) next.getKey());
            if (!((Item) next.getValue()).get().equals(Boolean.TRUE)) {
                sb.append('=');
                ((Item) next.getValue()).serializeTo(sb);
            }
        }
        return sb;
    }

    private static Map<String, Item<? extends Object>> checkAndTransformMap(Map<String, Object> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(((Map) Objects.requireNonNull(map, "Map must not be null")).size());
        for (Map.Entry next : map.entrySet()) {
            String checkKey = C3057Utils.checkKey((String) next.getKey());
            Item<? extends Object> asItem = asItem(checkKey, next.getValue());
            if (asItem.getParams().isEmpty()) {
                linkedHashMap.put(next.getKey(), asItem);
            } else {
                throw new IllegalArgumentException("Parameter value for '" + checkKey + "' must be bare item (no parameters)");
            }
        }
        return linkedHashMap;
    }

    private static Item<? extends Object> asItem(String str, Object obj) {
        if (obj instanceof Item) {
            return (Item) obj;
        }
        if (obj instanceof Integer) {
            return IntegerItem.valueOf(((Integer) obj).longValue());
        }
        if (obj instanceof Long) {
            return IntegerItem.valueOf(((Long) obj).longValue());
        }
        if (obj instanceof String) {
            return StringItem.valueOf((String) obj);
        }
        if (obj instanceof Boolean) {
            return BooleanItem.valueOf(((Boolean) obj).booleanValue());
        }
        if (obj instanceof byte[]) {
            return ByteSequenceItem.valueOf((byte[]) obj);
        }
        if (obj instanceof BigDecimal) {
            return DecimalItem.valueOf((BigDecimal) obj);
        }
        throw new IllegalArgumentException("Can't map value for parameter '" + str + "': " + obj.getClass());
    }

    public void clear() {
        this.delegate.clear();
    }

    public Item<? extends Object> compute(String str, BiFunction<? super String, ? super Item<? extends Object>, ? extends Item<? extends Object>> biFunction) {
        return this.delegate.compute(str, biFunction);
    }

    public Item<? extends Object> computeIfAbsent(String str, Function<? super String, ? extends Item<? extends Object>> function) {
        return this.delegate.computeIfAbsent(str, function);
    }

    public Item<? extends Object> computeIfPresent(String str, BiFunction<? super String, ? super Item<? extends Object>, ? extends Item<? extends Object>> biFunction) {
        return this.delegate.computeIfPresent(str, biFunction);
    }

    public boolean containsKey(Object obj) {
        return this.delegate.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this.delegate.containsValue(obj);
    }

    public Set<Map.Entry<String, Item<? extends Object>>> entrySet() {
        return this.delegate.entrySet();
    }

    public boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    public void forEach(BiConsumer<? super String, ? super Item<? extends Object>> biConsumer) {
        this.delegate.forEach(biConsumer);
    }

    public Item<? extends Object> get(Object obj) {
        return this.delegate.get(obj);
    }

    public Item<? extends Object> getOrDefault(Object obj, Item<? extends Object> item) {
        return this.delegate.getOrDefault(obj, item);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    public Item<? extends Object> merge(String str, Item<? extends Object> item, BiFunction<? super Item<? extends Object>, ? super Item<? extends Object>, ? extends Item<? extends Object>> biFunction) {
        return this.delegate.merge(str, item, biFunction);
    }

    public Item<? extends Object> put(String str, Item<? extends Object> item) {
        return this.delegate.put(str, item);
    }

    public void putAll(Map<? extends String, ? extends Item<? extends Object>> map) {
        this.delegate.putAll(map);
    }

    public Item<? extends Object> putIfAbsent(String str, Item<? extends Object> item) {
        return this.delegate.putIfAbsent(str, item);
    }

    public boolean remove(Object obj, Object obj2) {
        return this.delegate.remove(obj, obj2);
    }

    public Item<? extends Object> remove(Object obj) {
        return this.delegate.remove(obj);
    }

    public boolean replace(String str, Item<? extends Object> item, Item<? extends Object> item2) {
        return this.delegate.replace(str, item, item2);
    }

    public Item<? extends Object> replace(String str, Item<? extends Object> item) {
        return this.delegate.replace(str, item);
    }

    public void replaceAll(BiFunction<? super String, ? super Item<? extends Object>, ? extends Item<? extends Object>> biFunction) {
        this.delegate.replaceAll(biFunction);
    }

    public int size() {
        return this.delegate.size();
    }

    public Collection<Item<? extends Object>> values() {
        return this.delegate.values();
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }
}
