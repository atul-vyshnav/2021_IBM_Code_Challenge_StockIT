package org.unimodules.core.arguments;

import android.os.Bundle;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.unimodules.core.arguments.ReadableArguments;

public class MapArguments implements ReadableArguments {
    private Map<String, Object> mMap;

    public /* synthetic */ ReadableArguments getArguments(String str) {
        return ReadableArguments.CC.$default$getArguments(this, str);
    }

    public /* synthetic */ boolean getBoolean(String str) {
        return ReadableArguments.CC.$default$getBoolean(this, str);
    }

    public /* synthetic */ double getDouble(String str) {
        return ReadableArguments.CC.$default$getDouble(this, str);
    }

    public /* synthetic */ int getInt(String str) {
        return ReadableArguments.CC.$default$getInt(this, str);
    }

    public /* synthetic */ List getList(String str) {
        return ReadableArguments.CC.$default$getList(this, str);
    }

    public /* synthetic */ Map getMap(String str) {
        return ReadableArguments.CC.$default$getMap(this, str);
    }

    public /* synthetic */ String getString(String str) {
        return ReadableArguments.CC.$default$getString(this, str);
    }

    public /* synthetic */ Bundle toBundle() {
        return ReadableArguments.CC.$default$toBundle(this);
    }

    public MapArguments() {
        this.mMap = new HashMap();
    }

    public MapArguments(Map<String, Object> map) {
        this.mMap = map;
    }

    public Collection<String> keys() {
        return this.mMap.keySet();
    }

    public boolean containsKey(String str) {
        return this.mMap.containsKey(str);
    }

    public Object get(String str) {
        return this.mMap.get(str);
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = this.mMap.get(str);
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : z;
    }

    public double getDouble(String str, double d) {
        Object obj = this.mMap.get(str);
        return obj instanceof Number ? ((Number) obj).doubleValue() : d;
    }

    public int getInt(String str, int i) {
        Object obj = this.mMap.get(str);
        return obj instanceof Number ? ((Number) obj).intValue() : i;
    }

    public String getString(String str, String str2) {
        Object obj = this.mMap.get(str);
        return obj instanceof String ? (String) obj : str2;
    }

    public List getList(String str, List list) {
        Object obj = this.mMap.get(str);
        return obj instanceof List ? (List) obj : list;
    }

    public Map getMap(String str, Map map) {
        Object obj = this.mMap.get(str);
        return obj instanceof Map ? (Map) obj : map;
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public int size() {
        return this.mMap.size();
    }
}
