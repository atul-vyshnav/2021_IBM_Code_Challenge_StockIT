package org.apache.commons.p029io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Appendable;

/* renamed from: org.apache.commons.io.output.AppendableOutputStream */
public class AppendableOutputStream<T extends Appendable> extends OutputStream {
    private final T appendable;

    public AppendableOutputStream(T t) {
        this.appendable = t;
    }

    public void write(int i) throws IOException {
        this.appendable.append((char) i);
    }

    public T getAppendable() {
        return this.appendable;
    }
}
