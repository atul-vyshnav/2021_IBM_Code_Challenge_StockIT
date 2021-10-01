package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public class PriceChangeFlowParams {
    /* access modifiers changed from: private */
    public SkuDetails zza;

    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public static class Builder {
        private SkuDetails zza;

        public PriceChangeFlowParams build() {
            if (this.zza != null) {
                PriceChangeFlowParams priceChangeFlowParams = new PriceChangeFlowParams();
                SkuDetails unused = priceChangeFlowParams.zza = this.zza;
                return priceChangeFlowParams;
            }
            throw new IllegalArgumentException("SkuDetails must be set");
        }

        public Builder setSkuDetails(SkuDetails skuDetails) {
            this.zza = skuDetails;
            return this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public SkuDetails getSkuDetails() {
        return this.zza;
    }
}
