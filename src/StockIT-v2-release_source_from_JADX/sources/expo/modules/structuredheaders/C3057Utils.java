package expo.modules.structuredheaders;

import java.util.Map;
import java.util.Objects;

/* renamed from: expo.modules.structuredheaders.Utils */
public class C3057Utils {
    protected static boolean isAlpha(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    protected static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    protected static boolean isLcAlpha(char c) {
        return c >= 'a' && c <= 'z';
    }

    private C3057Utils() {
    }

    protected static String checkKey(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Key can not be null or empty");
        }
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if ((i != 0 || charAt == '*' || isLcAlpha(charAt)) && (isLcAlpha(charAt) || isDigit(charAt) || charAt == '_' || charAt == '-' || charAt == '.' || charAt == '*')) {
                i++;
            } else {
                throw new IllegalArgumentException(String.format("Invalid character in key at position %d: '%c' (0x%04x)", new Object[]{Integer.valueOf(i), Character.valueOf(charAt), Integer.valueOf(charAt)}));
            }
        }
        return str;
    }

    protected static Map<String, ListElement<? extends Object>> checkKeys(Map<String, ListElement<? extends Object>> map) {
        for (String checkKey : ((Map) Objects.requireNonNull(map, "value must not be null")).keySet()) {
            checkKey(checkKey);
        }
        return map;
    }
}
