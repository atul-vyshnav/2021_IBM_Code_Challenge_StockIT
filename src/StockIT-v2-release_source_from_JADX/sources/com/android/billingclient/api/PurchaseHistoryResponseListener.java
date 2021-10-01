package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@4.0.0 */
public interface PurchaseHistoryResponseListener {
    void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list);
}
