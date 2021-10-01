package org.apache.commons.p029io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.FalseFileFilter */
public class FalseFileFilter implements IOFileFilter, Serializable {
    public static final IOFileFilter FALSE;
    public static final IOFileFilter INSTANCE;
    private static final long serialVersionUID = 6210271677940926200L;

    public boolean accept(File file) {
        return false;
    }

    public boolean accept(File file, String str) {
        return false;
    }

    static {
        FalseFileFilter falseFileFilter = new FalseFileFilter();
        FALSE = falseFileFilter;
        INSTANCE = falseFileFilter;
    }

    protected FalseFileFilter() {
    }
}
