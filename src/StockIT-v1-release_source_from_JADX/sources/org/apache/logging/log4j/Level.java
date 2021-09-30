package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.codec.language.p028bm.Rule;
import org.apache.logging.log4j.spi.StandardLevel;

public final class Level implements Comparable<Level>, Serializable {
    public static final Level ALL = new Level(Rule.ALL, StandardLevel.ALL.intLevel());
    public static final String CATEGORY = "Level";
    public static final Level DEBUG = new Level("DEBUG", StandardLevel.DEBUG.intLevel());
    public static final Level ERROR = new Level("ERROR", StandardLevel.ERROR.intLevel());
    public static final Level FATAL = new Level("FATAL", StandardLevel.FATAL.intLevel());
    public static final Level INFO = new Level("INFO", StandardLevel.INFO.intLevel());
    public static final Level OFF = new Level("OFF", StandardLevel.OFF.intLevel());
    public static final Level TRACE = new Level("TRACE", StandardLevel.TRACE.intLevel());
    public static final Level WARN = new Level("WARN", StandardLevel.WARN.intLevel());
    private static final ConcurrentMap<String, Level> levels = new ConcurrentHashMap();
    private static final long serialVersionUID = 1581082;
    private final int intLevel;
    private final String name;
    private final StandardLevel standardLevel;

    private Level(String str, int i) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Illegal null Level constant");
        } else if (i >= 0) {
            this.name = str;
            this.intLevel = i;
            this.standardLevel = StandardLevel.getStandardLevel(i);
            if (levels.putIfAbsent(str, this) != null) {
                throw new IllegalStateException("Level " + str + " has already been defined.");
            }
        } else {
            throw new IllegalArgumentException("Illegal Level int less than zero.");
        }
    }

    public int intLevel() {
        return this.intLevel;
    }

    public StandardLevel getStandardLevel() {
        return this.standardLevel;
    }

    public boolean isLessSpecificThan(Level level) {
        return this.intLevel >= level.intLevel;
    }

    public boolean isMoreSpecificThan(Level level) {
        return this.intLevel <= level.intLevel;
    }

    public Level clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public int compareTo(Level level) {
        int i = this.intLevel;
        int i2 = level.intLevel;
        if (i < i2) {
            return -1;
        }
        return i > i2 ? 1 : 0;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Level) && obj == this;
    }

    public Class<Level> getDeclaringClass() {
        return Level.class;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public static Level forName(String str, int i) {
        Level level = (Level) levels.get(str);
        if (level != null) {
            return level;
        }
        try {
            return new Level(str, i);
        } catch (IllegalStateException unused) {
            return (Level) levels.get(str);
        }
    }

    public static Level getLevel(String str) {
        return (Level) levels.get(str);
    }

    public static Level toLevel(String str) {
        return toLevel(str, DEBUG);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r2 = (org.apache.logging.log4j.Level) levels.get(r2.toUpperCase(java.util.Locale.ENGLISH));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.logging.log4j.Level toLevel(java.lang.String r2, org.apache.logging.log4j.Level r3) {
        /*
            if (r2 != 0) goto L_0x0003
            return r3
        L_0x0003:
            java.util.concurrent.ConcurrentMap<java.lang.String, org.apache.logging.log4j.Level> r0 = levels
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r2 = r2.toUpperCase(r1)
            java.lang.Object r2 = r0.get(r2)
            org.apache.logging.log4j.Level r2 = (org.apache.logging.log4j.Level) r2
            if (r2 != 0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r3 = r2
        L_0x0015:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.logging.log4j.Level.toLevel(java.lang.String, org.apache.logging.log4j.Level):org.apache.logging.log4j.Level");
    }

    public static Level[] values() {
        Collection values = levels.values();
        return (Level[]) values.toArray(new Level[values.size()]);
    }

    public static Level valueOf(String str) {
        if (str != null) {
            String upperCase = str.toUpperCase(Locale.ENGLISH);
            if (levels.containsKey(upperCase)) {
                return (Level) levels.get(upperCase);
            }
            throw new IllegalArgumentException("Unknown level constant [" + upperCase + "].");
        }
        throw new NullPointerException("No level name given.");
    }

    public static <T extends Enum<T>> T valueOf(Class<T> cls, String str) {
        return Enum.valueOf(cls, str);
    }

    /* access modifiers changed from: protected */
    public Object readResolve() {
        return valueOf(this.name);
    }
}
