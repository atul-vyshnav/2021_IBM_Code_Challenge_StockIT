package expo.modules.inapppurchases;

import android.os.Bundle;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import expo.modules.inapppurchases.BillingManager;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.services.EventEmitter;

public class UpdateListener implements BillingManager.BillingUpdatesListener {
    private static final String TAG = "UpdateListener";
    private EventEmitter mEventEmitter;

    public void onBillingClientSetupFinished() {
    }

    public UpdateListener(EventEmitter eventEmitter) {
        this.mEventEmitter = eventEmitter;
    }

    public void onConsumeFinished(String str, BillingResult billingResult) {
        Bundle bundle = new Bundle();
        bundle.putInt("responseCode", billingResult.getResponseCode());
        bundle.putString("token", str);
        Promise promise = BillingManager.promises.get(BillingManager.ACKNOWLEDGING_PURCHASE);
        if (promise != null) {
            BillingManager.promises.put(BillingManager.ACKNOWLEDGING_PURCHASE, (Object) null);
            promise.resolve(bundle);
        }
    }

    public void onPurchasesUpdated(List<Purchase> list) {
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        for (Purchase purchaseToBundle : list) {
            arrayList.add(BillingManager.purchaseToBundle(purchaseToBundle));
        }
        bundle.putParcelableArrayList("results", arrayList);
        bundle.putInt("responseCode", 0);
        this.mEventEmitter.emit(BillingManager.PURCHASES_UPDATED_EVENT, bundle);
    }
}
