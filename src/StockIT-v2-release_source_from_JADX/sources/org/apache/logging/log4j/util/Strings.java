package org.apache.logging.log4j.util;

public final class Strings {
    public static final String EMPTY = "";

    private Strings() {
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trimToNull(String str) {
        String trim = str == null ? null : str.trim();
        if (isEmpty(trim)) {
            return null;
        }
        return trim;
    }
}
