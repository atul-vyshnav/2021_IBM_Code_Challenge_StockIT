package org.apache.commons.p029io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.CanReadFileFilter */
public class CanReadFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter CANNOT_READ;
    public static final IOFileFilter CAN_READ;
    public static final IOFileFilter READ_ONLY = new AndFileFilter(CAN_READ, CanWriteFileFilter.CANNOT_WRITE);
    private static final long serialVersionUID = 3179904805251622989L;

    static {
        CanReadFileFilter canReadFileFilter = new CanReadFileFilter();
        CAN_READ = canReadFileFilter;
        CANNOT_READ = new NotFileFilter(canReadFileFilter);
    }

    protected CanReadFileFilter() {
    }

    public boolean accept(File file) {
        return file.canRead();
    }
}
