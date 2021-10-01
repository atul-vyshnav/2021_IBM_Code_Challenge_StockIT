package com.android.billingclient.api;

import android.app.Activity;
import android.content.Context;
import com.android.billingclient.api.Purchase;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public abstract class BillingClient {

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public @interface BillingResponseCode {
        public static final int BILLING_UNAVAILABLE = 3;
        public static final int DEVELOPER_ERROR = 5;
        public static final int ERROR = 6;
        public static final int FEATURE_NOT_SUPPORTED = -2;
        public static final int ITEM_ALREADY_OWNED = 7;
        public static final int ITEM_NOT_OWNED = 8;
        public static final int ITEM_UNAVAILABLE = 4;

        /* renamed from: OK */
        public static final int f53OK = 0;
        public static final int SERVICE_DISCONNECTED = -1;
        public static final int SERVICE_TIMEOUT = -3;
        public static final int SERVICE_UNAVAILABLE = 2;
        public static final int USER_CANCELED = 1;
    }

    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public static final class Builder {
        private volatile String zza;
        private volatile boolean zzb;
        private final Context zzc;
        private volatile PurchasesUpdatedListener zzd;

        /* synthetic */ Builder(Context context, zzi zzi) {
            this.zzc = context;
        }

        public BillingClient build() {
            if (this.zzc == null) {
                throw new IllegalArgumentException("Please provide a valid Context.");
            } else if (this.zzd == null) {
                throw new IllegalArgumentException("Please provide a valid listener for purchases updates.");
            } else if (this.zzb) {
                return new BillingClientImpl((String) null, this.zzb, this.zzc, this.zzd);
            } else {
                throw new IllegalArgumentException("Support for pending purchases must be enabled. Enable this by calling 'enablePendingPurchases()' on BillingClientBuilder.");
            }
        }

        public Builder enablePendingPurchases() {
            this.zzb = true;
            return this;
        }

        public Builder setListener(PurchasesUpdatedListener purchasesUpdatedListener) {
            this.zzd = purchasesUpdatedListener;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public @interface ConnectionState {
        public static final int CLOSED = 3;
        public static final int CONNECTED = 2;
        public static final int CONNECTING = 1;
        public static final int DISCONNECTED = 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public @interface FeatureType {
        public static final String IN_APP_ITEMS_ON_VR = "inAppItemsOnVr";
        public static final String PRICE_CHANGE_CONFIRMATION = "priceChangeConfirmation";
        public static final String SUBSCRIPTIONS = "subscriptions";
        public static final String SUBSCRIPTIONS_ON_VR = "subscriptionsOnVr";
        public static final String SUBSCRIPTIONS_UPDATE = "subscriptionsUpdate";
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.android.billingclient:billing@@4.0.0 */
    public @interface SkuType {
        public static final String INAPP = "inapp";
        public static final String SUBS = "subs";
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context, (zzi) null);
    }

    public abstract void acknowledgePurchase(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener);

    public abstract void consumeAsync(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener);

    public abstract void endConnection();

    public abstract int getConnectionState();

    public abstract BillingResult isFeatureSupported(String str);

    public abstract boolean isReady();

    public abstract BillingResult launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams);

    public abstract void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, PriceChangeConfirmationListener priceChangeConfirmationListener);

    public abstract void queryPurchaseHistoryAsync(String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener);

    @Deprecated
    public abstract Purchase.PurchasesResult queryPurchases(String str);

    public abstract void queryPurchasesAsync(String str, PurchasesResponseListener purchasesResponseListener);

    public abstract void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, SkuDetailsResponseListener skuDetailsResponseListener);

    public abstract void startConnection(BillingClientStateListener billingClientStateListener);
}
