package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class AcknowledgePurchaseParams {
    /* access modifiers changed from: private */
    public String zza;

    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public static final class Builder {
        private String zza;

        private Builder() {
        }

        /* synthetic */ Builder(zza zza2) {
        }

        public AcknowledgePurchaseParams build() {
            if (this.zza != null) {
                AcknowledgePurchaseParams acknowledgePurchaseParams = new AcknowledgePurchaseParams((zza) null);
                String unused = acknowledgePurchaseParams.zza = this.zza;
                return acknowledgePurchaseParams;
            }
            throw new IllegalArgumentException("Purchase token must be set");
        }

        public Builder setPurchaseToken(String str) {
            this.zza = str;
            return this;
        }
    }

    private AcknowledgePurchaseParams() {
    }

    /* synthetic */ AcknowledgePurchaseParams(zza zza2) {
    }

    public static Builder newBuilder() {
        return new Builder((zza) null);
    }

    public String getPurchaseToken() {
        return this.zza;
    }
}
