package expo.modules.structuredheaders;

import java.util.Collections;
import java.util.Map;

public class Dictionary implements Type<Map<String, ListElement<? extends Object>>> {
    private final Map<String, ListElement<? extends Object>> value;

    private Dictionary(Map<String, ListElement<? extends Object>> map) {
        this.value = Collections.unmodifiableMap(C3057Utils.checkKeys(map));
    }

    public static Dictionary valueOf(Map<String, ListElement<? extends Object>> map) {
        return new Dictionary(map);
    }

    public Map<String, ListElement<? extends Object>> get() {
        return this.value;
    }

    public StringBuilder serializeTo(StringBuilder sb) {
        String str = "";
        for (Map.Entry next : this.value.entrySet()) {
            sb.append(str);
            ListElement listElement = (ListElement) next.getValue();
            sb.append((String) next.getKey());
            if (Boolean.TRUE.equals(listElement.get())) {
                listElement.getParams().serializeTo(sb);
            } else {
                sb.append("=");
                listElement.serializeTo(sb);
            }
            str = ", ";
        }
        return sb;
    }

    public String serialize() {
        return serializeTo(new StringBuilder()).toString();
    }
}
