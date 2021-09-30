package com.polidea.multiplatformbleadapter.utils;

import java.util.HashMap;

public class IdGenerator {
    private static HashMap<IdGeneratorKey, Integer> idMap = new HashMap<>();
    private static int nextKey = 0;

    public static int getIdForKey(IdGeneratorKey idGeneratorKey) {
        Integer num = idMap.get(idGeneratorKey);
        if (num != null) {
            return num.intValue();
        }
        HashMap<IdGeneratorKey, Integer> hashMap = idMap;
        int i = nextKey + 1;
        nextKey = i;
        hashMap.put(idGeneratorKey, Integer.valueOf(i));
        return nextKey;
    }

    public static void clear() {
        idMap.clear();
        nextKey = 0;
    }
}
