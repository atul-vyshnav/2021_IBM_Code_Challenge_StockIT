package p000ai.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/* renamed from: ai.api.model.EntityEntry */
public class EntityEntry implements Serializable {
    private static final long serialVersionUID = 1;
    @SerializedName("synonyms")
    private List<String> synonyms;
    @SerializedName("value")
    private String value;

    public EntityEntry() {
    }

    public EntityEntry(String str) {
        this.value = str;
    }

    public EntityEntry(String str, List<String> list) {
        this.value = str;
        this.synonyms = list;
    }

    public EntityEntry(String str, String[] strArr) {
        this.value = str;
        this.synonyms = Arrays.asList(strArr);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public List<String> getSynonyms() {
        return this.synonyms;
    }

    public void setSynonyms(List<String> list) {
        this.synonyms = list;
    }
}
