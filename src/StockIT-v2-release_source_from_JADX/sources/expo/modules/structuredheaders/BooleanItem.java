package expo.modules.structuredheaders;

import java.util.Objects;

public class BooleanItem implements Item<Boolean> {
    private static final BooleanItem FALSE = new BooleanItem(false, Parameters.EMPTY);
    private static final BooleanItem TRUE = new BooleanItem(true, Parameters.EMPTY);
    private final Parameters params;
    private final boolean value;

    private BooleanItem(boolean z, Parameters parameters) {
        this.value = z;
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static BooleanItem valueOf(boolean z) {
        return z ? TRUE : FALSE;
    }

    public Parameters getParams() {
        return this.params;
    }

    public BooleanItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new BooleanItem(this.value, parameters);
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append(this.value ? "?1" : "?0");
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public Boolean get() {
        return Boolean.valueOf(this.value);
    }
}
