package expo.modules.structuredheaders;

import java.util.Objects;

public class IntegerItem implements NumberItem<Long> {
    private static final long MAX = 999999999999999L;
    private static final long MIN = -999999999999999L;
    private final Parameters params;
    private final long value;

    public int getDivisor() {
        return 1;
    }

    private IntegerItem(long j, Parameters parameters) {
        if (j < MIN || j > MAX) {
            throw new IllegalArgumentException("value must be in the range from -999999999999999 to 999999999999999");
        }
        this.value = j;
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static IntegerItem valueOf(long j) {
        return new IntegerItem(j, Parameters.EMPTY);
    }

    public IntegerItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new IntegerItem(this.value, parameters);
    }

    public Parameters getParams() {
        return this.params;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append(Long.toString(this.value));
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public Long get() {
        return Long.valueOf(this.value);
    }

    public long getAsLong() {
        return this.value;
    }
}
