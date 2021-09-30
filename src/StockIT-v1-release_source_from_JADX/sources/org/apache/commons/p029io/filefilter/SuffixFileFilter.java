package org.apache.commons.p029io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.p029io.IOCase;

/* renamed from: org.apache.commons.io.filefilter.SuffixFileFilter */
public class SuffixFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -3389157631240246157L;
    private final IOCase caseSensitivity;
    private final String[] suffixes;

    public SuffixFileFilter(String str) {
        this(str, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String str, IOCase iOCase) {
        if (str != null) {
            this.suffixes = new String[]{str};
            this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
            return;
        }
        throw new IllegalArgumentException("The suffix must not be null");
    }

    public SuffixFileFilter(String[] strArr) {
        this(strArr, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String[] strArr, IOCase iOCase) {
        if (strArr != null) {
            String[] strArr2 = new String[strArr.length];
            this.suffixes = strArr2;
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
            return;
        }
        throw new IllegalArgumentException("The array of suffixes must not be null");
    }

    public SuffixFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(List<String> list, IOCase iOCase) {
        if (list != null) {
            this.suffixes = (String[]) list.toArray(new String[list.size()]);
            this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
            return;
        }
        throw new IllegalArgumentException("The list of suffixes must not be null");
    }

    public boolean accept(File file) {
        String name = file.getName();
        for (String checkEndsWith : this.suffixes) {
            if (this.caseSensitivity.checkEndsWith(name, checkEndsWith)) {
                return true;
            }
        }
        return false;
    }

    public boolean accept(File file, String str) {
        for (String checkEndsWith : this.suffixes) {
            if (this.caseSensitivity.checkEndsWith(str, checkEndsWith)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        if (this.suffixes != null) {
            for (int i = 0; i < this.suffixes.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.suffixes[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}