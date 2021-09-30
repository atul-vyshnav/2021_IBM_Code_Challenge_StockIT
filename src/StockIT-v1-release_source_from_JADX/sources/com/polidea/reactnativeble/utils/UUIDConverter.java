package com.polidea.reactnativeble.utils;

import com.facebook.react.bridge.ReadableArray;
import java.util.UUID;

public class UUIDConverter {
    private static String baseUUIDPrefix = "0000";
    private static String baseUUIDSuffix = "-0000-1000-8000-00805F9B34FB";

    public static UUID convert(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 4) {
            str = baseUUIDPrefix + str + baseUUIDSuffix;
        } else if (str.length() == 8) {
            str = str + baseUUIDSuffix;
        }
        try {
            return UUID.fromString(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static UUID[] convert(String... strArr) {
        UUID[] uuidArr = new UUID[strArr.length];
        int i = 0;
        while (i < strArr.length) {
            if (strArr[i] == null) {
                return null;
            }
            if (strArr[i].length() == 4) {
                strArr[i] = baseUUIDPrefix + strArr[i] + baseUUIDSuffix;
            } else if (strArr[i].length() == 8) {
                strArr[i] = strArr[i] + baseUUIDSuffix;
            }
            try {
                uuidArr[i] = UUID.fromString(strArr[i]);
                i++;
            } catch (Throwable unused) {
                return null;
            }
        }
        return uuidArr;
    }

    public static UUID[] convert(ReadableArray readableArray) {
        UUID[] uuidArr = new UUID[readableArray.size()];
        int i = 0;
        while (i < readableArray.size()) {
            try {
                String string = readableArray.getString(i);
                if (string.length() == 4) {
                    string = baseUUIDPrefix + string + baseUUIDSuffix;
                } else if (string.length() == 8) {
                    string = string + baseUUIDSuffix;
                }
                uuidArr[i] = UUID.fromString(string);
                i++;
            } catch (Throwable unused) {
                return null;
            }
        }
        return uuidArr;
    }

    public static String fromUUID(UUID uuid) {
        return uuid.toString().toLowerCase();
    }
}
