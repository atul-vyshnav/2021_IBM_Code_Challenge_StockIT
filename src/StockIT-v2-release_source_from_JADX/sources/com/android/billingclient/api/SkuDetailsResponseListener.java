package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public interface SkuDetailsResponseListener {
    void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list);
}
