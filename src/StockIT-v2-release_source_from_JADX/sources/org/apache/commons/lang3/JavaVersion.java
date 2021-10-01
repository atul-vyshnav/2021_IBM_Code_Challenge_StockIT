package org.apache.commons.lang3;

import com.learnium.RNDeviceInfo.BuildConfig;
import org.apache.commons.lang3.math.NumberUtils;

public enum JavaVersion {
    JAVA_0_9(1.5f, "0.9"),
    JAVA_1_1(1.1f, BuildConfig.VERSION_NAME),
    JAVA_1_2(1.2f, "1.2"),
    JAVA_1_3(1.3f, "1.3"),
    JAVA_1_4(1.4f, "1.4"),
    JAVA_1_5(1.5f, "1.5"),
    JAVA_1_6(1.6f, "1.6"),
    JAVA_1_7(1.7f, "1.7"),
    JAVA_1_8(1.8f, "1.8"),
    JAVA_1_9(9.0f, "9"),
    JAVA_9(9.0f, "9"),
    JAVA_10(10.0f, "10"),
    JAVA_11(11.0f, "11"),
    JAVA_12(12.0f, "12"),
    JAVA_13(13.0f, "13"),
    JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));
    
    private final String name;
    private final float value;

    private JavaVersion(float f, String str) {
        this.value = f;
        this.name = str;
    }

    public boolean atLeast(JavaVersion javaVersion) {
        return this.value >= javaVersion.value;
    }

    public boolean atMost(JavaVersion javaVersion) {
        return this.value <= javaVersion.value;
    }

    static JavaVersion getJavaVersion(String str) {
        return get(str);
    }

    static JavaVersion get(String str) {
        if ("0.9".equals(str)) {
            return JAVA_0_9;
        }
        if (BuildConfig.VERSION_NAME.equals(str)) {
            return JAVA_1_1;
        }
        if ("1.2".equals(str)) {
            return JAVA_1_2;
        }
        if ("1.3".equals(str)) {
            return JAVA_1_3;
        }
        if ("1.4".equals(str)) {
            return JAVA_1_4;
        }
        if ("1.5".equals(str)) {
            return JAVA_1_5;
        }
        if ("1.6".equals(str)) {
            return JAVA_1_6;
        }
        if ("1.7".equals(str)) {
            return JAVA_1_7;
        }
        if ("1.8".equals(str)) {
            return JAVA_1_8;
        }
        if ("9".equals(str)) {
            return JAVA_9;
        }
        if ("10".equals(str)) {
            return JAVA_10;
        }
        if ("11".equals(str)) {
            return JAVA_11;
        }
        if ("12".equals(str)) {
            return JAVA_12;
        }
        if ("13".equals(str)) {
            return JAVA_13;
        }
        if (str == null) {
            return null;
        }
        float floatVersion = toFloatVersion(str);
        if (((double) floatVersion) - 1.0d < 1.0d) {
            int max = Math.max(str.indexOf(46), str.indexOf(44));
            if (Float.parseFloat(str.substring(max + 1, Math.max(str.length(), str.indexOf(44, max)))) > 0.9f) {
                return JAVA_RECENT;
            }
        } else if (floatVersion > 10.0f) {
            return JAVA_RECENT;
        }
        return null;
    }

    public String toString() {
        return this.name;
    }

    private static float maxVersion() {
        float floatVersion = toFloatVersion(System.getProperty("java.specification.version", "99.0"));
        if (floatVersion > 0.0f) {
            return floatVersion;
        }
        return 99.0f;
    }

    private static float toFloatVersion(String str) {
        if (!str.contains(".")) {
            return NumberUtils.toFloat(str, -1.0f);
        }
        String[] split = str.split("\\.");
        if (split.length < 2) {
            return -1.0f;
        }
        return NumberUtils.toFloat(split[0] + '.' + split[1], -1.0f);
    }
}
