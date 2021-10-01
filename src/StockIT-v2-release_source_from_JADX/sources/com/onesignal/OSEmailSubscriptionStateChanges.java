package com.onesignal;

import org.json.JSONException;
import org.json.JSONObject;

public class OSEmailSubscriptionStateChanges {
    OSEmailSubscriptionState from;

    /* renamed from: to */
    OSEmailSubscriptionState f1168to;

    public OSEmailSubscriptionState getTo() {
        return this.f1168to;
    }

    public OSEmailSubscriptionState getFrom() {
        return this.from;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", this.from.toJSONObject());
            jSONObject.put("to", this.f1168to.toJSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
