package expo.modules.structuredheaders;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class InnerList implements ListElement<List<Item<? extends Object>>>, Parametrizable<List<Item<? extends Object>>> {
    private final Parameters params;
    private final List<Item<? extends Object>> value;

    private InnerList(List<Item<? extends Object>> list, Parameters parameters) {
        this.value = (List) Objects.requireNonNull(list, "value must not be null");
        this.params = (Parameters) Objects.requireNonNull(parameters, "params must not be null");
    }

    public static InnerList valueOf(List<Item<? extends Object>> list) {
        return new InnerList(list, Parameters.EMPTY);
    }

    public InnerList withParams(Parameters parameters) {
        if (((Parameters) Objects.requireNonNull(parameters, "params must not be null")).isEmpty()) {
            return this;
        }
        return new InnerList(this.value, parameters);
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        sb.append('(');
        String str = "";
        for (Item<? extends Object> serializeTo : this.value) {
            sb.append(str);
            serializeTo.serializeTo(sb);
            str = StringUtils.SPACE;
        }
        sb.append(')');
        this.params.serializeTo(sb);
        return sb;
    }

    public Parameters getParams() {
        return this.params;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public List<Item<? extends Object>> get() {
        return this.value;
    }
}
