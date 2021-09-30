package org.apache.commons.p029io.output;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.NullOutputStream */
public class NullOutputStream extends OutputStream {
    public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

    public void write(int i) {
    }

    public void write(byte[] bArr) throws IOException {
    }

    public void write(byte[] bArr, int i, int i2) {
    }
}
