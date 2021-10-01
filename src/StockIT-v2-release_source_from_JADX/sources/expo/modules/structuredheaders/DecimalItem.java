package expo.modules.structuredheaders;

import java.math.BigDecimal;
import java.util.Objects;

public class DecimalItem implements NumberItem<BigDecimal> {
    private static final long MAX = 999999999999999L;
    private static final long MIN = -999999999999999L;
    private static final BigDecimal THOUSAND = new BigDecimal(1000);
    private final Parameters params;
    private final long value;

    public int getDivisor() {
        return 1000;
    }

    private DecimalItem(long j, Parameters parameters) {
        if (j < MIN || j > MAX) {
            throw new IllegalArgumentException("value must be in the range from -999999999999999 to 999999999999999");
        }
        this.value = j;
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static DecimalItem valueOf(long j) {
        return new DecimalItem(j, Parameters.EMPTY);
    }

    public static DecimalItem valueOf(BigDecimal bigDecimal) {
        return valueOf(((BigDecimal) Objects.requireNonNull(bigDecimal, "value must not be null")).multiply(THOUSAND).longValue());
    }

    public DecimalItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new DecimalItem(this.value, parameters);
    }

    public Parameters getParams() {
        return this.params;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        String str = this.value < 0 ? "-" : "";
        long abs = Math.abs(this.value);
        long j = abs / 1000;
        long j2 = abs % 1000;
        if (j2 % 10 == 0) {
            j2 /= 10;
        }
        if (j2 % 10 == 0) {
            j2 /= 10;
        }
        sb.append(str);
        sb.append(Long.toString(j));
        sb.append('.');
        sb.append(Long.toString(j2));
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder(20)).toString();
    }

    public BigDecimal get() {
        return BigDecimal.valueOf(this.value, 3);
    }

    public long getAsLong() {
        return this.value;
    }
}
