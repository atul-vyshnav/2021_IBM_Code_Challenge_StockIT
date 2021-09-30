package expo.modules.inapppurchases;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.onesignal.OneSignalDbContract;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.services.EventEmitter;

public class BillingManager implements PurchasesUpdatedListener {
    public static final String ACKNOWLEDGING_PURCHASE = "Acknowledging Item";
    public static final int BILLING_MANAGER_NOT_INITIALIZED = -1;
    public static final int ERROR = 2;
    public static final String INAPP_SUB_PERIOD = "P0D";

    /* renamed from: OK */
    public static final int f1241OK = 0;
    public static final String PURCHASES_UPDATED_EVENT = "Expo.purchasesUpdated";
    private static final String TAG = "BillingManager";
    public static final int USER_CANCELED = 1;
    protected static final HashMap<String, Promise> promises = new HashMap<>();
    /* access modifiers changed from: private */
    public final Activity mActivity;
    /* access modifiers changed from: private */
    public BillingClient mBillingClient;
    /* access modifiers changed from: private */
    public int mBillingClientResponseCode = -1;
    /* access modifiers changed from: private */
    public BillingUpdatesListener mBillingUpdatesListener;
    private EventEmitter mEventEmitter;
    /* access modifiers changed from: private */
    public boolean mIsServiceConnected;
    private final List<Purchase> mPurchases = new ArrayList();
    /* access modifiers changed from: private */
    public final HashMap<String, SkuDetails> mSkuDetailsMap = new HashMap<>();
    private Set<String> mTokensToBeConsumed;

    public interface BillingUpdatesListener {
        void onBillingClientSetupFinished();

        void onConsumeFinished(String str, BillingResult billingResult);

        void onPurchasesUpdated(List<Purchase> list);
    }

    public interface ServiceConnectedListener {
        void onServiceConnected(BillingResult billingResult);
    }

    private static int errorCodeNativeToJS(int i) {
        if (i == -3) {
            return 4;
        }
        if (i == -2) {
            return 1;
        }
        if (i == -1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i == 3) {
            return 5;
        }
        if (i == 4) {
            return 6;
        }
        if (i == 5) {
            return 7;
        }
        if (i != 7) {
            return i != 8 ? 0 : 9;
        }
        return 8;
    }

    private static int purchaseStateNativeToJS(int i) {
        if (i != 0) {
            return i != 1 ? 0 : 1;
        }
        return 2;
    }

    public BillingManager(Activity activity, EventEmitter eventEmitter) {
        this.mActivity = activity;
        this.mEventEmitter = eventEmitter;
        this.mBillingUpdatesListener = new UpdateListener(eventEmitter);
        this.mBillingClient = BillingClient.newBuilder(activity).enablePendingPurchases().setListener(this).build();
    }

    public void startConnection(final Promise promise) {
        startServiceConnection(new Runnable() {
            public void run() {
                BillingManager.this.mBillingUpdatesListener.onBillingClientSetupFinished();
                promise.resolve((Object) null);
            }
        });
    }

    public void startServiceConnection(final Runnable runnable) {
        this.mBillingClient.startConnection(new BillingClientStateListener() {
            public void onBillingSetupFinished(BillingResult billingResult) {
                int responseCode = billingResult.getResponseCode();
                if (responseCode == 0) {
                    boolean unused = BillingManager.this.mIsServiceConnected = true;
                    Runnable runnable = runnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
                int unused2 = BillingManager.this.mBillingClientResponseCode = responseCode;
            }

            public void onBillingServiceDisconnected() {
                boolean unused = BillingManager.this.mIsServiceConnected = false;
            }
        });
    }

    public void purchaseItemAsync(final String str, final String str2, final Promise promise) {
        executeServiceRequest(new Runnable() {
            public void run() {
                SkuDetails skuDetails = (SkuDetails) BillingManager.this.mSkuDetailsMap.get(str);
                if (skuDetails == null) {
                    promise.reject("E_ITEM_NOT_QUERIED", "Must query item from store before calling purchase");
                    return;
                }
                BillingFlowParams.Builder skuDetails2 = BillingFlowParams.newBuilder().setSkuDetails(skuDetails);
                if (str2 != null) {
                    skuDetails2.setSubscriptionUpdateParams(BillingFlowParams.SubscriptionUpdateParams.newBuilder().setOldSkuPurchaseToken(str2).build());
                }
                BillingManager.this.mBillingClient.launchBillingFlow(BillingManager.this.mActivity, skuDetails2.build());
            }
        });
    }

    public Context getContext() {
        return this.mActivity;
    }

    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        if (billingResult.getResponseCode() != 0 || list == null) {
            this.mEventEmitter.emit(PURCHASES_UPDATED_EVENT, formatResponse(billingResult, (ArrayList<? extends Parcelable>) null));
            return;
        }
        for (Purchase handlePurchase : list) {
            handlePurchase(handlePurchase);
        }
        this.mBillingUpdatesListener.onPurchasesUpdated(this.mPurchases);
    }

    public void acknowledgePurchaseAsync(String str, final Promise promise) {
        C30094 r0 = new AcknowledgePurchaseResponseListener() {
            public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                promise.resolve(BillingManager.formatResponse(billingResult, (ArrayList<? extends Parcelable>) null));
            }
        };
        this.mBillingClient.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(str).build(), r0);
    }

    public void consumeAsync(final String str, Promise promise) {
        Set<String> set = this.mTokensToBeConsumed;
        if (set == null) {
            this.mTokensToBeConsumed = new HashSet();
        } else if (set.contains(str)) {
            Bundle bundle = new Bundle();
            bundle.putInt("responseCode", 0);
            promise.resolve(bundle);
            return;
        }
        if (promises.get(ACKNOWLEDGING_PURCHASE) != null) {
            promise.reject("E_UNFINISHED_PROMISE", "Must wait for promise to resolve before recalling function.");
            return;
        }
        promises.put(ACKNOWLEDGING_PURCHASE, promise);
        this.mTokensToBeConsumed.add(str);
        final C30105 r4 = new ConsumeResponseListener() {
            public void onConsumeResponse(BillingResult billingResult, String str) {
                BillingManager.this.mBillingUpdatesListener.onConsumeFinished(str, billingResult);
            }
        };
        executeServiceRequest(new Runnable() {
            public void run() {
                BillingManager.this.mBillingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(str).build(), r4);
            }
        });
    }

    private void handlePurchase(Purchase purchase) {
        this.mPurchases.add(purchase);
    }

    public int getBillingClientResponseCode() {
        return this.mBillingClientResponseCode;
    }

    public void queryPurchases(final Promise promise) {
        executeServiceRequest(new Runnable() {
            public void run() {
                HashSet hashSet = new HashSet(Arrays.asList(new String[]{BillingClient.SkuType.INAPP, BillingClient.SkuType.SUBS}));
                ArrayList arrayList = new ArrayList();
                HashSet hashSet2 = new HashSet();
                HashSet hashSet3 = new HashSet();
                final ArrayList arrayList2 = arrayList;
                final HashSet hashSet4 = hashSet3;
                final HashSet hashSet5 = hashSet2;
                final HashSet hashSet6 = hashSet;
                BillingManager.this.mBillingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, new PurchasesResponseListener() {
                    public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
                        if (billingResult.getResponseCode() == 0) {
                            arrayList2.addAll(list);
                        }
                        hashSet4.add(billingResult);
                        hashSet5.add(BillingClient.SkuType.INAPP);
                        if (hashSet5.containsAll(hashSet6) || !BillingManager.this.areSubscriptionsSupported()) {
                            BillingManager.this.onQueryPurchasesFinished(BillingManager.this.aggregateBillingResults(hashSet4), arrayList2, promise);
                        }
                    }
                });
                if (BillingManager.this.areSubscriptionsSupported()) {
                    final ArrayList arrayList3 = arrayList;
                    final HashSet hashSet7 = hashSet3;
                    final HashSet hashSet8 = hashSet2;
                    final HashSet hashSet9 = hashSet;
                    BillingManager.this.mBillingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, new PurchasesResponseListener() {
                        public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
                            if (billingResult.getResponseCode() == 0) {
                                arrayList3.addAll(list);
                            }
                            hashSet7.add(billingResult);
                            hashSet8.add(BillingClient.SkuType.SUBS);
                            if (hashSet8.containsAll(hashSet9)) {
                                BillingManager.this.onQueryPurchasesFinished(BillingManager.this.aggregateBillingResults(hashSet7), arrayList3, promise);
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public BillingResult aggregateBillingResults(Set<BillingResult> set) {
        for (BillingResult next : set) {
            if (next.getResponseCode() != 0) {
                return next;
            }
        }
        return set.iterator().next();
    }

    public void queryPurchaseHistoryAsync(final Promise promise) {
        executeServiceRequest(new Runnable() {
            public void run() {
                final ArrayList arrayList = new ArrayList();
                BillingManager.this.mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, new PurchaseHistoryResponseListener() {
                    public void onPurchaseHistoryResponse(final BillingResult billingResult, List<PurchaseHistoryRecord> list) {
                        if (billingResult.getResponseCode() == 0 && list != null) {
                            for (PurchaseHistoryRecord access$800 : list) {
                                arrayList.add(BillingManager.purchaseHistoryToBundle(access$800));
                            }
                        }
                        BillingManager.this.mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, new PurchaseHistoryResponseListener() {
                            public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
                                if (billingResult.getResponseCode() == 0 && list != null) {
                                    for (PurchaseHistoryRecord access$800 : list) {
                                        arrayList.add(BillingManager.purchaseHistoryToBundle(access$800));
                                    }
                                }
                                promise.resolve(BillingManager.formatResponse(billingResult, arrayList));
                            }
                        });
                    }
                });
            }
        });
    }

    public static Bundle formatResponse(BillingResult billingResult, ArrayList<? extends Parcelable> arrayList) {
        Bundle bundle = new Bundle();
        int responseCode = billingResult.getResponseCode();
        if (responseCode == 0) {
            bundle.putInt("responseCode", 0);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            bundle.putParcelableArrayList("results", arrayList);
        } else if (responseCode == 1) {
            bundle.putInt("responseCode", 1);
        } else {
            bundle.putInt("responseCode", 2);
            bundle.putInt("errorCode", errorCodeNativeToJS(responseCode));
        }
        return bundle;
    }

    /* access modifiers changed from: private */
    public static Bundle skuToBundle(SkuDetails skuDetails) {
        Bundle bundle = new Bundle();
        String subscriptionPeriod = skuDetails.getType().equals(BillingClient.SkuType.SUBS) ? skuDetails.getSubscriptionPeriod() : INAPP_SUB_PERIOD;
        bundle.putString("description", skuDetails.getDescription());
        bundle.putString("price", skuDetails.getPrice());
        bundle.putLong("priceAmountMicros", skuDetails.getPriceAmountMicros());
        bundle.putString("priceCurrencyCode", skuDetails.getPriceCurrencyCode());
        bundle.putString("productId", skuDetails.getSku());
        bundle.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_TITLE, skuDetails.getTitle());
        bundle.putInt("type", skuDetails.getType().equals(BillingClient.SkuType.INAPP) ^ true ? 1 : 0);
        bundle.putString("subscriptionPeriod", subscriptionPeriod);
        return bundle;
    }

    public static Bundle purchaseToBundle(Purchase purchase) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("acknowledged", purchase.isAcknowledged());
        bundle.putString("orderId", purchase.getOrderId());
        bundle.putString("productId", purchase.getSkus().get(0));
        bundle.putInt("purchaseState", purchaseStateNativeToJS(purchase.getPurchaseState()));
        bundle.putLong("purchaseTime", purchase.getPurchaseTime());
        bundle.putString("packageName", purchase.getPackageName());
        bundle.putString("purchaseToken", purchase.getPurchaseToken());
        return bundle;
    }

    /* access modifiers changed from: private */
    public static Bundle purchaseHistoryToBundle(PurchaseHistoryRecord purchaseHistoryRecord) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", purchaseHistoryRecord.getSkus().get(0));
        bundle.putLong("purchaseTime", purchaseHistoryRecord.getPurchaseTime());
        bundle.putString("purchaseToken", purchaseHistoryRecord.getPurchaseToken());
        return bundle;
    }

    /* access modifiers changed from: private */
    public void onQueryPurchasesFinished(BillingResult billingResult, List<Purchase> list, Promise promise) {
        if (this.mBillingClient == null || billingResult.getResponseCode() != 0) {
            promise.reject("E_QUERY_FAILED", "Billing client was null or query was unsuccessful");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Purchase purchaseToBundle : list) {
            arrayList.add(purchaseToBundle(purchaseToBundle));
        }
        this.mPurchases.clear();
        onPurchasesUpdated(billingResult, list);
        promise.resolve(formatResponse(billingResult, arrayList));
    }

    public void querySkuDetailsAsync(final List<String> list, final SkuDetailsResponseListener skuDetailsResponseListener) {
        executeServiceRequest(new Runnable() {
            public void run() {
                SkuDetailsParams.Builder newBuilder = SkuDetailsParams.newBuilder();
                newBuilder.setSkusList(list).setType(BillingClient.SkuType.INAPP);
                BillingManager.this.mBillingClient.querySkuDetailsAsync(newBuilder.build(), new SkuDetailsResponseListener() {
                    public void onSkuDetailsResponse(BillingResult billingResult, final List<SkuDetails> list) {
                        SkuDetailsParams.Builder newBuilder = SkuDetailsParams.newBuilder();
                        newBuilder.setSkusList(list).setType(BillingClient.SkuType.SUBS);
                        BillingManager.this.mBillingClient.querySkuDetailsAsync(newBuilder.build(), new SkuDetailsResponseListener() {
                            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {
                                List list2 = list;
                                if (!(list2 == null || list == null)) {
                                    list2.addAll(list);
                                }
                                skuDetailsResponseListener.onSkuDetailsResponse(billingResult, list);
                            }
                        });
                    }
                });
            }
        });
    }

    public void queryPurchasableItems(List<String> list, final Promise promise) {
        querySkuDetailsAsync(list, new SkuDetailsResponseListener() {
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {
                ArrayList arrayList = new ArrayList();
                if (list != null) {
                    for (SkuDetails next : list) {
                        BillingManager.this.mSkuDetailsMap.put(next.getSku(), next);
                        arrayList.add(BillingManager.skuToBundle(next));
                    }
                }
                promise.resolve(BillingManager.formatResponse(billingResult, arrayList));
            }
        });
    }

    private void executeServiceRequest(Runnable runnable) {
        if (this.mIsServiceConnected) {
            runnable.run();
        } else {
            startServiceConnection(runnable);
        }
    }

    public boolean areSubscriptionsSupported() {
        return this.mBillingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS).getResponseCode() == 0;
    }

    public void destroy() {
        BillingClient billingClient = this.mBillingClient;
        if (billingClient != null && billingClient.isReady()) {
            this.mBillingClient.endConnection();
            this.mBillingClient = null;
        }
    }
}
