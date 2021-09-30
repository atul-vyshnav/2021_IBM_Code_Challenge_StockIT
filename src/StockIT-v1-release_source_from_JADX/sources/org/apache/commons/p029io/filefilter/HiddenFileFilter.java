package org.apache.commons.p029io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.HiddenFileFilter */
public class HiddenFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter HIDDEN;
    public static final IOFileFilter VISIBLE;
    private static final long serialVersionUID = 8930842316112759062L;

    static {
        HiddenFileFilter hiddenFileFilter = new HiddenFileFilter();
        HIDDEN = hiddenFileFilter;
        VISIBLE = new NotFileFilter(hiddenFileFilter);
    }

    protected HiddenFileFilter() {
    }

    public boolean accept(File file) {
        return file.isHidden();
    }
}
