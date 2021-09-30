package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class BillingResult {
    /* access modifiers changed from: private */
    public int zza;
    /* access modifiers changed from: private */
    public String zzb;

    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public static class Builder {
        private int zza;
        private String zzb = "";

        private Builder() {
        }

        /* synthetic */ Builder(zzaj zzaj) {
        }

        public BillingResult build() {
            BillingResult billingResult = new BillingResult();
            int unused = billingResult.zza = this.zza;
            String unused2 = billingResult.zzb = this.zzb;
            return billingResult;
        }

        public Builder setDebugMessage(String str) {
            this.zzb = str;
            return this;
        }

        public Builder setResponseCode(int i) {
            this.zza = i;
            return this;
        }
    }

    public static Builder newBuilder() {
        return new Builder((zzaj) null);
    }

    public String getDebugMessage() {
        return this.zzb;
    }

    public int getResponseCode() {
        return this.zza;
    }
}
