package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public final class ConsumeParams {
    /* access modifiers changed from: private */
    public String zza;

    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public static final class Builder {
        private String zza;

        private Builder() {
        }

        /* synthetic */ Builder(zzal zzal) {
        }

        public ConsumeParams build() {
            if (this.zza != null) {
                ConsumeParams consumeParams = new ConsumeParams((zzal) null);
                String unused = consumeParams.zza = this.zza;
                return consumeParams;
            }
            throw new IllegalArgumentException("Purchase token must be set");
        }

        public Builder setPurchaseToken(String str) {
            this.zza = str;
            return this;
        }
    }

    private ConsumeParams() {
    }

    /* synthetic */ ConsumeParams(zzal zzal) {
    }

    public static Builder newBuilder() {
        return new Builder((zzal) null);
    }

    public String getPurchaseToken() {
        return this.zza;
    }
}
