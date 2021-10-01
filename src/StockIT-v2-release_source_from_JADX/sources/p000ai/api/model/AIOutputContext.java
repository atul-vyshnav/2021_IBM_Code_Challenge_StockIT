package p000ai.api.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

/* renamed from: ai.api.model.AIOutputContext */
public class AIOutputContext implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("lifespan")
    private Integer lifespan;
    @SerializedName("name")
    private String name;
    @SerializedName("parameters")
    private Map<String, JsonElement> parameters;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Map<String, JsonElement> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, JsonElement> map) {
        this.parameters = map;
    }

    public Integer getLifespan() {
        return this.lifespan;
    }

    public void setLifespan(Integer num) {
        this.lifespan = num;
    }
}
