package com.polidea.rxandroidble.internal.cache;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble.ClientScope;
import com.polidea.rxandroidble.internal.DeviceComponent;
import com.polidea.rxandroidble.internal.cache.DeviceComponentWeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import p030rx.Observable;
import p030rx.functions.Action2;
import p030rx.functions.Func0;
import p030rx.functions.Func1;

@ClientScope
public class DeviceComponentCache implements Map<String, DeviceComponent> {
    private final HashMap<String, DeviceComponentWeakReference> cache;
    /* access modifiers changed from: private */
    public final DeviceComponentWeakReference.Provider deviceComponentReferenceProvider;

    @Inject
    public DeviceComponentCache() {
        this(new DeviceComponentWeakReference.Provider() {
            public DeviceComponentWeakReference provide(DeviceComponent deviceComponent) {
                return new DeviceComponentWeakReference(deviceComponent);
            }
        });
    }

    DeviceComponentCache(DeviceComponentWeakReference.Provider provider) {
        this.cache = new HashMap<>();
        this.deviceComponentReferenceProvider = provider;
    }

    public void clear() {
        this.cache.clear();
    }

    public boolean containsKey(Object obj) {
        return this.cache.containsKey(obj) && get(obj) != null;
    }

    public boolean containsValue(Object obj) {
        for (DeviceComponentWeakReference contains : this.cache.values()) {
            if (contains.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public Set<Map.Entry<String, DeviceComponent>> entrySet() {
        return (Set) Observable.from(this.cache.entrySet()).filter(new Func1<Map.Entry<String, DeviceComponentWeakReference>, Boolean>() {
            public Boolean call(Map.Entry<String, DeviceComponentWeakReference> entry) {
                return Boolean.valueOf(!entry.getValue().isEmpty());
            }
        }).map(new Func1<Map.Entry<String, DeviceComponentWeakReference>, CacheEntry>() {
            public CacheEntry call(Map.Entry<String, DeviceComponentWeakReference> entry) {
                return new CacheEntry(entry.getKey(), DeviceComponentCache.this.deviceComponentReferenceProvider.provide((DeviceComponent) entry.getValue().get()));
            }
        }).collect(new Func0<HashSet<Map.Entry<String, DeviceComponent>>>() {
            public HashSet<Map.Entry<String, DeviceComponent>> call() {
                return new HashSet<>();
            }
        }, new Action2<HashSet<Map.Entry<String, DeviceComponent>>, CacheEntry>() {
            public void call(HashSet<Map.Entry<String, DeviceComponent>> hashSet, CacheEntry cacheEntry) {
                hashSet.add(cacheEntry);
            }
        }).toBlocking().first();
    }

    public DeviceComponent get(Object obj) {
        DeviceComponentWeakReference deviceComponentWeakReference = this.cache.get(obj);
        if (deviceComponentWeakReference != null) {
            return (DeviceComponent) deviceComponentWeakReference.get();
        }
        return null;
    }

    public boolean isEmpty() {
        evictEmptyReferences();
        return this.cache.isEmpty();
    }

    public Set<String> keySet() {
        return this.cache.keySet();
    }

    public DeviceComponent put(String str, DeviceComponent deviceComponent) {
        this.cache.put(str, this.deviceComponentReferenceProvider.provide(deviceComponent));
        evictEmptyReferences();
        return deviceComponent;
    }

    public void putAll(Map<? extends String, ? extends DeviceComponent> map) {
        for (Map.Entry next : map.entrySet()) {
            put((String) next.getKey(), (DeviceComponent) next.getValue());
        }
    }

    public DeviceComponent remove(Object obj) {
        DeviceComponentWeakReference remove = this.cache.remove(obj);
        evictEmptyReferences();
        if (remove != null) {
            return (DeviceComponent) remove.get();
        }
        return null;
    }

    public int size() {
        evictEmptyReferences();
        return this.cache.size();
    }

    public Collection<DeviceComponent> values() {
        return Observable.from(this.cache.values()).filter(new Func1<DeviceComponentWeakReference, Boolean>() {
            public Boolean call(DeviceComponentWeakReference deviceComponentWeakReference) {
                return Boolean.valueOf(!deviceComponentWeakReference.isEmpty());
            }
        }).map(new Func1<DeviceComponentWeakReference, DeviceComponent>() {
            public DeviceComponent call(DeviceComponentWeakReference deviceComponentWeakReference) {
                return (DeviceComponent) deviceComponentWeakReference.get();
            }
        }).toList().toBlocking().first();
    }

    private void evictEmptyReferences() {
        Iterator<Map.Entry<String, DeviceComponentWeakReference>> it = this.cache.entrySet().iterator();
        while (it.hasNext()) {
            if (((DeviceComponentWeakReference) it.next().getValue()).isEmpty()) {
                it.remove();
            }
        }
    }
}
