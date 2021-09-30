package org.apache.logging.log4j.util;

import java.util.Locale;

public final class EnglishEnums {
    private EnglishEnums() {
    }

    public static <T extends Enum<T>> T valueOf(Class<T> cls, String str) {
        return valueOf(cls, str, (Enum) null);
    }

    public static <T extends Enum<T>> T valueOf(Class<T> cls, String str, T t) {
        return str == null ? t : Enum.valueOf(cls, str.toUpperCase(Locale.ENGLISH));
    }
}
