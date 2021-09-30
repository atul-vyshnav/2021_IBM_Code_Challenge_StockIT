package expo.modules.structuredheaders;

import java.util.Objects;

public class TokenItem implements Item<String> {
    private final Parameters params;
    private final String value;

    private TokenItem(String str, Parameters parameters) {
        this.value = checkParam((String) Objects.requireNonNull(str, "value must not be null"));
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static TokenItem valueOf(String str) {
        return new TokenItem(str, Parameters.EMPTY);
    }

    public TokenItem withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new TokenItem(this.value, parameters);
    }

    public Parameters getParams() {
        return this.params;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append(this.value);
        this.params.serializeTo(sb);
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public String get() {
        return this.value;
    }

    private static String checkParam(String str) {
        if (str.length() != 0) {
            int i = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if ((i != 0 || charAt == '*' || C3057Utils.isAlpha(charAt)) && charAt > ' ' && charAt < 127 && "\"(),;<=>?@[\\]{}".indexOf(charAt) < 0) {
                    i++;
                } else {
                    throw new IllegalArgumentException(String.format("Invalid character in Token at position %d: '%c' (0x%04x)", new Object[]{Integer.valueOf(i), Character.valueOf(charAt), Integer.valueOf(charAt)}));
                }
            }
            return str;
        }
        throw new IllegalArgumentException("Token can not be empty");
    }
}
