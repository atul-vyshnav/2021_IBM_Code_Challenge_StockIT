package org.apache.commons.p029io;

import java.io.IOException;

@Deprecated
/* renamed from: org.apache.commons.io.IOExceptionWithCause */
public class IOExceptionWithCause extends IOException {
    private static final long serialVersionUID = 1;

    public IOExceptionWithCause(String str, Throwable th) {
        super(str, th);
    }

    public IOExceptionWithCause(Throwable th) {
        super(th);
    }
}
