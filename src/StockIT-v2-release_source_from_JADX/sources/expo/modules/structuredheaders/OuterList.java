package expo.modules.structuredheaders;

import java.util.List;
import java.util.Objects;

public class OuterList implements Type<List<ListElement<? extends Object>>> {
    private final List<ListElement<? extends Object>> value;

    private OuterList(List<ListElement<? extends Object>> list) {
        this.value = (List) Objects.requireNonNull(list, "value must not be null");
    }

    public static OuterList valueOf(List<ListElement<? extends Object>> list) {
        return new OuterList(list);
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        String str = "";
        for (ListElement<? extends Object> serializeTo : this.value) {
            sb.append(str);
            serializeTo.serializeTo(sb);
            str = ", ";
        }
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }

    public List<ListElement<? extends Object>> get() {
        return this.value;
    }
}
