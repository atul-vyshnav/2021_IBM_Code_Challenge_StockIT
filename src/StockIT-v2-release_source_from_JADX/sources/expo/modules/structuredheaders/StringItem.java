package expo.modules.structuredheaders;

import java.util.Objects;
import kotlin.text.Typography;
import org.apache.commons.p029io.IOUtils;

public class StringItem implements Item<String> {
    private final Parameters params;
    private final String value;

    private StringItem(String str, Parameters parameters) {
        this.value = checkParam((String) Objects.requireNonNull(str, "value must not be null"));
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static StringItem valueOf(String str) {
        return new StringItem(str, Parameters.EMPTY);
    }

    public StringItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new StringItem(this.value, parameters);
    }

    public Parameters getParams() {
        return this.params;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append(Typography.quote);
        for (int i = 0; i < this.value.length(); i++) {
            char charAt = this.value.charAt(i);
            if (charAt == '\\' || charAt == '\"') {
                sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
            }
            sb.append(charAt);
        }
        sb.append(Typography.quote);
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder(this.value.length() + 2)).toString();
    }

    public String get() {
        return this.value;
    }

    private static String checkParam(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt >= 127) {
                throw new IllegalArgumentException(String.format("Invalid character in String at position %d: '%c' (0x%04x)", new Object[]{Integer.valueOf(i), Character.valueOf(charAt), Integer.valueOf(charAt)}));
            }
        }
        return str;
    }
}
