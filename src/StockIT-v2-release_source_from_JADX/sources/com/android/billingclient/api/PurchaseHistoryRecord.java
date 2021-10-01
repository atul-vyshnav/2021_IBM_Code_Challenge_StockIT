package com.android.billingclient.api;

import android.text.TextUtils;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public class PurchaseHistoryRecord {
    private final String zza;
    private final String zzb;
    private final JSONObject zzc = new JSONObject(this.zza);

    public PurchaseHistoryRecord(String str, String str2) throws JSONException {
        this.zza = str;
        this.zzb = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PurchaseHistoryRecord)) {
            return false;
        }
        PurchaseHistoryRecord purchaseHistoryRecord = (PurchaseHistoryRecord) obj;
        return TextUtils.equals(this.zza, purchaseHistoryRecord.getOriginalJson()) && TextUtils.equals(this.zzb, purchaseHistoryRecord.getSignature());
    }

    public String getDeveloperPayload() {
        return this.zzc.optString("developerPayload");
    }

    public String getOriginalJson() {
        return this.zza;
    }

    public long getPurchaseTime() {
        return this.zzc.optLong("purchaseTime");
    }

    public String getPurchaseToken() {
        JSONObject jSONObject = this.zzc;
        return jSONObject.optString("token", jSONObject.optString("purchaseToken"));
    }

    public int getQuantity() {
        return this.zzc.optInt("quantity", 1);
    }

    public String getSignature() {
        return this.zzb;
    }

    public ArrayList<String> getSkus() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (this.zzc.has("productIds")) {
            JSONArray optJSONArray = this.zzc.optJSONArray("productIds");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    arrayList.add(optJSONArray.optString(i));
                }
            }
        } else if (this.zzc.has("productId")) {
            arrayList.add(this.zzc.optString("productId"));
        }
        return arrayList;
    }

    public int hashCode() {
        return this.zza.hashCode();
    }

    public String toString() {
        String valueOf = String.valueOf(this.zza);
        return valueOf.length() != 0 ? "PurchaseHistoryRecord. Json: ".concat(valueOf) : new String("PurchaseHistoryRecord. Json: ");
    }
}
