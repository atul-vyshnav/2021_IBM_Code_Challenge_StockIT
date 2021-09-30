package expo.modules.inapppurchases;

import android.app.Activity;
import android.content.Context;
import java.util.List;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.EventEmitter;

public class InAppPurchasesModule extends ExportedModule implements RegistryLifecycleListener {
    private static final String NAME = "ExpoInAppPurchases";
    private static final String TAG = InAppPurchasesModule.class.getSimpleName();
    private final String USE_GOOGLE_PLAY_CACHE_KEY = "useGooglePlayCache";
    private BillingManager mBillingManager;
    private EventEmitter mEventEmitter;
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return NAME;
    }

    public InAppPurchasesModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    @ExpoMethod
    public void connectAsync(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject("E_ACTIVITY_UNAVAILABLE", "Activity is not available");
        }
        this.mEventEmitter = (EventEmitter) this.mModuleRegistry.getModule(EventEmitter.class);
        BillingManager billingManager = new BillingManager(currentActivity, this.mEventEmitter);
        this.mBillingManager = billingManager;
        billingManager.startConnection(promise);
    }

    @ExpoMethod
    public void getProductsAsync(List<String> list, Promise promise) {
        this.mBillingManager.queryPurchasableItems(list, promise);
    }

    @ExpoMethod
    public void getPurchaseHistoryAsync(ReadableArguments readableArguments, Promise promise) {
        if (readableArguments.getBoolean("useGooglePlayCache", true)) {
            this.mBillingManager.queryPurchases(promise);
        } else {
            this.mBillingManager.queryPurchaseHistoryAsync(promise);
        }
    }

    @ExpoMethod
    public void purchaseItemAsync(String str, String str2, Promise promise) {
        this.mBillingManager.purchaseItemAsync(str, str2, promise);
    }

    @ExpoMethod
    public void getBillingResponseCodeAsync(Promise promise) {
        promise.resolve(Integer.valueOf(this.mBillingManager.getBillingClientResponseCode()));
    }

    @ExpoMethod
    public void finishTransactionAsync(String str, Boolean bool, Promise promise) {
        if (bool == null || !bool.booleanValue()) {
            this.mBillingManager.acknowledgePurchaseAsync(str, promise);
        } else {
            this.mBillingManager.consumeAsync(str, promise);
        }
    }

    @ExpoMethod
    public void disconnectAsync(Promise promise) {
        BillingManager billingManager = this.mBillingManager;
        if (billingManager != null) {
            billingManager.destroy();
            this.mBillingManager = null;
        }
        promise.resolve((Object) null);
    }

    private Activity getCurrentActivity() {
        ActivityProvider activityProvider = (ActivityProvider) this.mModuleRegistry.getModule(ActivityProvider.class);
        if (activityProvider != null) {
            return activityProvider.getCurrentActivity();
        }
        return null;
    }
}
