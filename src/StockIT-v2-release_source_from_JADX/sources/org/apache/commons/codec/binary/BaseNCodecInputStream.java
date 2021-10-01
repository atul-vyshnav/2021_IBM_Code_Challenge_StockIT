package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.apache.commons.codec.binary.BaseNCodec;

public class BaseNCodecInputStream extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final BaseNCodec.Context context = new BaseNCodec.Context();
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];

    public boolean markSupported() {
        return false;
    }

    protected BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec2, boolean z) {
        super(inputStream);
        this.doEncode = z;
        this.baseNCodec = baseNCodec2;
    }

    public int available() throws IOException {
        return this.context.eof ^ true ? 1 : 0;
    }

    public synchronized void mark(int i) {
    }

    public int read() throws IOException {
        int read = read(this.singleByte, 0, 1);
        while (read == 0) {
            read = read(this.singleByte, 0, 1);
        }
        if (read <= 0) {
            return -1;
        }
        byte b = this.singleByte[0];
        return b < 0 ? b + UByte.MIN_VALUE : b;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            throw null;
        } else if (i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i > bArr.length || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 == 0) {
            return 0;
        } else {
            int i3 = 0;
            while (i3 == 0) {
                if (!this.baseNCodec.hasData(this.context)) {
                    byte[] bArr2 = new byte[(this.doEncode ? 4096 : 8192)];
                    int read = this.in.read(bArr2);
                    if (this.doEncode) {
                        this.baseNCodec.encode(bArr2, 0, read, this.context);
                    } else {
                        this.baseNCodec.decode(bArr2, 0, read, this.context);
                    }
                }
                i3 = this.baseNCodec.readResults(bArr, i, i2, this.context);
            }
            return i3;
        }
    }

    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    public long skip(long j) throws IOException {
        int read;
        if (j >= 0) {
            byte[] bArr = new byte[512];
            long j2 = j;
            while (j2 > 0 && (read = read(bArr, 0, (int) Math.min((long) 512, j2))) != -1) {
                j2 -= (long) read;
            }
            return j - j2;
        }
        throw new IllegalArgumentException("Negative skip length: " + j);
    }
}
