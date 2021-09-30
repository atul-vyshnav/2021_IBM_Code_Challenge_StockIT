package expo.modules.structuredheaders;

import android.util.Base64;
import java.nio.ByteBuffer;
import java.util.Objects;

public class ByteSequenceItem implements Item<ByteBuffer> {
    private final Parameters params;
    private final byte[] value;

    private ByteSequenceItem(byte[] bArr, Parameters parameters) {
        this.value = (byte[]) Objects.requireNonNull(bArr, "value must not be null");
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static ByteSequenceItem valueOf(byte[] bArr) {
        return new ByteSequenceItem(bArr, Parameters.EMPTY);
    }

    public ByteSequenceItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new ByteSequenceItem(this.value, parameters);
    }

    public Parameters getParams() {
        return this.params;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append(':');
        sb.append(Base64.encodeToString(this.value, 0));
        sb.append(':');
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public ByteBuffer get() {
        return ByteBuffer.wrap(this.value);
    }
}
