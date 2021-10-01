package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: ai.api.model.AIEvent */
public class AIEvent implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("data")
    private Map<String, String> data;
    @SerializedName("name")
    private String name;

    public AIEvent() {
    }

    public AIEvent(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> map) {
        this.data = map;
    }

    public void addDataField(String str, String str2) {
        if (this.data == null) {
            setData(new HashMap());
        }
        this.data.put(str, str2);
    }

    public void addDataField(Map<String, String> map) {
        if (this.data == null) {
            setData(new HashMap());
        }
        this.data.putAll(map);
    }

    public String getDataField(String str) {
        return getDataField(str, "");
    }

    public String getDataField(String str, String str2) {
        return this.data.containsKey(str) ? this.data.get(str) : str2;
    }
}
