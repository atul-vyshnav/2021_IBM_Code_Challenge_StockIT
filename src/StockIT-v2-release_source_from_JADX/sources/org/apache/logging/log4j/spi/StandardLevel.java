package org.apache.logging.log4j.spi;

import com.facebook.common.statfs.StatFsHelper;
import java.util.EnumSet;
import java.util.Iterator;

public enum StandardLevel {
    OFF(0),
    FATAL(100),
    ERROR(200),
    WARN(300),
    INFO(StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB),
    DEBUG(500),
    TRACE(600),
    ALL(Integer.MAX_VALUE);
    
    private static final EnumSet<StandardLevel> levelSet = null;
    private final int intLevel;

    static {
        levelSet = EnumSet.allOf(StandardLevel.class);
    }

    private StandardLevel(int i) {
        this.intLevel = i;
    }

    public int intLevel() {
        return this.intLevel;
    }

    public static StandardLevel getStandardLevel(int i) {
        StandardLevel standardLevel = OFF;
        Iterator it = levelSet.iterator();
        while (it.hasNext()) {
            StandardLevel standardLevel2 = (StandardLevel) it.next();
            if (standardLevel2.intLevel() > i) {
                break;
            }
            standardLevel = standardLevel2;
        }
        return standardLevel;
    }
}
