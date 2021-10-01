package p000ai.api.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import p000ai.api.util.ParametersConverter;
import p000ai.api.util.StringUtils;

/* renamed from: ai.api.model.Result */
public class Result implements Serializable {
    private static final String DATE_FORMAT_ERROR_MESSAGE = "'%s' parameter has value '%s' and can't be parsed as a Date or Time";
    private static final long serialVersionUID = 1;
    @SerializedName("action")
    private String action;
    @SerializedName("actionIncomplete")
    private boolean actionIncomplete;
    @SerializedName("contexts")
    private List<AIOutputContext> contexts;
    @SerializedName("fulfillment")
    private Fulfillment fulfillment;
    @SerializedName("metadata")
    private Metadata metadata;
    @SerializedName("parameters")
    private HashMap<String, JsonElement> parameters;
    @SerializedName("resolvedQuery")
    private String resolvedQuery;
    @SerializedName("score")
    private float score;
    @SerializedName("source")
    private String source;

    public String getAction() {
        String str = this.action;
        return str == null ? "" : str;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float f) {
        this.score = f;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata2) {
        this.metadata = metadata2;
    }

    public HashMap<String, JsonElement> getParameters() {
        return this.parameters;
    }

    public String getStringParameter(String str) {
        return getStringParameter(str, "");
    }

    public String getStringParameter(String str, String str2) {
        return this.parameters.containsKey(str) ? this.parameters.get(str).getAsString() : str2;
    }

    public Date getDateParameter(String str) throws IllegalArgumentException {
        return getDateParameter(str, (Date) null);
    }

    public Date getDateParameter(String str, Date date) throws IllegalArgumentException {
        if (!this.parameters.containsKey(str)) {
            return date;
        }
        String asString = this.parameters.get(str).getAsString();
        if (StringUtils.isEmpty(asString)) {
            return date;
        }
        try {
            return ParametersConverter.parseDate(asString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format(DATE_FORMAT_ERROR_MESSAGE, new Object[]{str, asString}), e);
        }
    }

    public Date getDateTimeParameter(String str) throws IllegalArgumentException {
        return getDateTimeParameter(str, (Date) null);
    }

    public Date getDateTimeParameter(String str, Date date) throws IllegalArgumentException {
        if (!this.parameters.containsKey(str)) {
            return date;
        }
        String asString = this.parameters.get(str).getAsString();
        if (StringUtils.isEmpty(asString)) {
            return date;
        }
        try {
            return ParametersConverter.parseDateTime(asString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format(DATE_FORMAT_ERROR_MESSAGE, new Object[]{str, asString}), e);
        }
    }

    public Date getTimeParameter(String str) throws IllegalArgumentException {
        return getTimeParameter(str, (Date) null);
    }

    public Date getTimeParameter(String str, Date date) throws IllegalArgumentException {
        if (!this.parameters.containsKey(str)) {
            return date;
        }
        String asString = this.parameters.get(str).getAsString();
        if (StringUtils.isEmpty(asString)) {
            return date;
        }
        try {
            return ParametersConverter.parseTime(asString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format(DATE_FORMAT_ERROR_MESSAGE, new Object[]{str, asString}), e);
        }
    }

    public int getIntParameter(String str) {
        return getIntParameter(str, 0);
    }

    public int getIntParameter(String str, int i) {
        if (!this.parameters.containsKey(str)) {
            return i;
        }
        String asString = this.parameters.get(str).getAsString();
        if (StringUtils.isEmpty(asString)) {
            return i;
        }
        return ParametersConverter.parseInteger(asString);
    }

    public float getFloatParameter(String str) {
        return getFloatParameter(str, 0.0f);
    }

    public float getFloatParameter(String str, float f) {
        if (!this.parameters.containsKey(str)) {
            return f;
        }
        String asString = this.parameters.get(str).getAsString();
        if (StringUtils.isEmpty(asString)) {
            return f;
        }
        return ParametersConverter.parseFloat(asString);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r2 = r1.parameters.get(r2).getAsJsonObject();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.gson.JsonObject getComplexParameter(java.lang.String r2, com.google.gson.JsonObject r3) {
        /*
            r1 = this;
            java.util.HashMap<java.lang.String, com.google.gson.JsonElement> r0 = r1.parameters
            boolean r0 = r0.containsKey(r2)
            if (r0 == 0) goto L_0x0018
            java.util.HashMap<java.lang.String, com.google.gson.JsonElement> r0 = r1.parameters
            java.lang.Object r2 = r0.get(r2)
            com.google.gson.JsonElement r2 = (com.google.gson.JsonElement) r2
            com.google.gson.JsonObject r2 = r2.getAsJsonObject()
            if (r2 != 0) goto L_0x0017
            return r3
        L_0x0017:
            return r2
        L_0x0018:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p000ai.api.model.Result.getComplexParameter(java.lang.String, com.google.gson.JsonObject):com.google.gson.JsonObject");
    }

    public JsonObject getComplexParameter(String str) {
        return getComplexParameter(str, (JsonObject) null);
    }

    public List<AIOutputContext> getContexts() {
        return this.contexts;
    }

    public AIOutputContext getContext(String str) {
        if (!StringUtils.isEmpty(str)) {
            List<AIOutputContext> list = this.contexts;
            if (list == null) {
                return null;
            }
            for (AIOutputContext next : list) {
                if (str.equalsIgnoreCase(next.getName())) {
                    return next;
                }
            }
            return null;
        }
        throw new IllegalArgumentException("name argument must be not empty");
    }

    public String getResolvedQuery() {
        return this.resolvedQuery;
    }

    public void setResolvedQuery(String str) {
        this.resolvedQuery = str;
    }

    public Fulfillment getFulfillment() {
        return this.fulfillment;
    }

    public void setFulfillment(Fulfillment fulfillment2) {
        this.fulfillment = fulfillment2;
    }

    public boolean isActionIncomplete() {
        return this.actionIncomplete;
    }

    public void setActionIncomplete(boolean z) {
        this.actionIncomplete = z;
    }

    /* access modifiers changed from: package-private */
    public void trimParameters() {
        if (this.parameters != null) {
            LinkedList<String> linkedList = new LinkedList<>();
            for (String next : this.parameters.keySet()) {
                JsonElement jsonElement = this.parameters.get(next);
                if (jsonElement != null && jsonElement.isJsonPrimitive() && ((JsonPrimitive) jsonElement).isString() && StringUtils.isEmpty(jsonElement.getAsString())) {
                    linkedList.add(next);
                }
            }
            for (String remove : linkedList) {
                this.parameters.remove(remove);
            }
        }
    }

    public String toString() {
        return String.format("Result {action='%s', resolvedQuery='%s'}", new Object[]{this.action, this.resolvedQuery});
    }
}
