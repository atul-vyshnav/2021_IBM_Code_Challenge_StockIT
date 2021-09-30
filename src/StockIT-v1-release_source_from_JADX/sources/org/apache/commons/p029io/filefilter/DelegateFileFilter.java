package org.apache.commons.p029io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.DelegateFileFilter */
public class DelegateFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -8723373124984771318L;
    private final FileFilter fileFilter;
    private final FilenameFilter filenameFilter;

    public DelegateFileFilter(FilenameFilter filenameFilter2) {
        if (filenameFilter2 != null) {
            this.filenameFilter = filenameFilter2;
            this.fileFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FilenameFilter must not be null");
    }

    public DelegateFileFilter(FileFilter fileFilter2) {
        if (fileFilter2 != null) {
            this.fileFilter = fileFilter2;
            this.filenameFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FileFilter must not be null");
    }

    public boolean accept(File file) {
        FileFilter fileFilter2 = this.fileFilter;
        if (fileFilter2 != null) {
            return fileFilter2.accept(file);
        }
        return super.accept(file);
    }

    public boolean accept(File file, String str) {
        FilenameFilter filenameFilter2 = this.filenameFilter;
        if (filenameFilter2 != null) {
            return filenameFilter2.accept(file, str);
        }
        return super.accept(file, str);
    }

    public String toString() {
        Object obj = this.fileFilter;
        if (obj == null) {
            obj = this.filenameFilter;
        }
        String obj2 = obj.toString();
        return super.toString() + "(" + obj2 + ")";
    }
}
