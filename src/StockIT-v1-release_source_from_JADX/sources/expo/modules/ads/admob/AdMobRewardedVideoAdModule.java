package expo.modules.ads.admob;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import java.util.ArrayList;
import java.util.Collections;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.EventEmitter;

public class AdMobRewardedVideoAdModule extends ExportedModule {
    /* access modifiers changed from: private */
    public ActivityProvider mActivityProvider;
    /* access modifiers changed from: private */
    public String mAdUnitID;
    private EventEmitter mEventEmitter;
    /* access modifiers changed from: private */
    public Promise mRequestAdPromise;
    /* access modifiers changed from: private */
    public RewardedAd mRewardedAd;
    /* access modifiers changed from: private */
    public Promise mShowAdPromise;

    public String getName() {
        return "ExpoAdsAdMobRewardedVideoAdManager";
    }

    public enum Event {
        DID_REWARD("rewardedVideoUserDidEarnReward"),
        DID_LOAD("rewardedVideoDidLoad"),
        DID_FAIL_TO_LOAD("rewardedVideoDidFailToLoad"),
        DID_PRESENT("rewardedVideoDidPresent"),
        DID_FAIL_TO_PRESENT("rewardedVideoDidFailToPresent"),
        DID_DISMISS("rewardedVideoDidDismiss");
        
        private final String mName;

        private Event(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public AdMobRewardedVideoAdModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mEventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class);
        this.mActivityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
    }

    /* access modifiers changed from: private */
    public void sendEvent(Event event) {
        sendEvent(event, new Bundle());
    }

    /* access modifiers changed from: private */
    public void sendEvent(Event event, Bundle bundle) {
        this.mEventEmitter.emit(event.toString(), bundle);
    }

    @ExpoMethod
    public void setAdUnitID(String str, Promise promise) {
        this.mAdUnitID = str;
        promise.resolve((Object) null);
    }

    @ExpoMethod
    public void requestAd(final ReadableArguments readableArguments, final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                ArrayList arrayList = null;
                if (AdMobRewardedVideoAdModule.this.mRewardedAd != null) {
                    promise.reject("E_AD_ALREADY_LOADED", "Ad is already loaded.", (Throwable) null);
                    return;
                }
                Promise unused = AdMobRewardedVideoAdModule.this.mRequestAdPromise = promise;
                String testDeviceID = AdMobModule.getTestDeviceID();
                if (testDeviceID != null) {
                    arrayList = new ArrayList(Collections.singletonList(testDeviceID));
                }
                MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(arrayList).build());
                AdRequest build = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, readableArguments.toBundle()).build();
                AdMobRewardedVideoAdModule adMobRewardedVideoAdModule = AdMobRewardedVideoAdModule.this;
                RewardedAd unused2 = adMobRewardedVideoAdModule.mRewardedAd = new RewardedAd(adMobRewardedVideoAdModule.mActivityProvider.getCurrentActivity(), AdMobRewardedVideoAdModule.this.mAdUnitID);
                AdMobRewardedVideoAdModule.this.mRewardedAd.loadAd(build, (RewardedAdLoadCallback) new RewardedAdLoadCallback() {
                    public void onRewardedAdLoaded() {
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_LOAD);
                        AdMobRewardedVideoAdModule.this.mRequestAdPromise.resolve((Object) null);
                        Promise unused = AdMobRewardedVideoAdModule.this.mRequestAdPromise = null;
                    }

                    public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_FAIL_TO_LOAD, AdMobUtils.createEventForAdFailedToLoad((AdError) loadAdError));
                        RewardedAd unused = AdMobRewardedVideoAdModule.this.mRewardedAd = null;
                        AdMobRewardedVideoAdModule.this.mRequestAdPromise.reject("E_AD_REQUEST_FAILED", loadAdError.getMessage());
                        Promise unused2 = AdMobRewardedVideoAdModule.this.mRequestAdPromise = null;
                    }
                });
            }
        });
    }

    @ExpoMethod
    public void showAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (AdMobRewardedVideoAdModule.this.mRewardedAd == null || !AdMobRewardedVideoAdModule.this.mRewardedAd.isLoaded()) {
                    promise.reject("E_AD_NOT_READY", "Ad is not ready.", (Throwable) null);
                    return;
                }
                Promise unused = AdMobRewardedVideoAdModule.this.mShowAdPromise = promise;
                AdMobRewardedVideoAdModule.this.mRewardedAd.show(AdMobRewardedVideoAdModule.this.mActivityProvider.getCurrentActivity(), new RewardedAdCallback() {
                    public void onUserEarnedReward(RewardItem rewardItem) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("amount", rewardItem.getAmount());
                        bundle.putString("type", rewardItem.getType());
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_REWARD, bundle);
                    }

                    public void onRewardedAdOpened() {
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_PRESENT);
                        AdMobRewardedVideoAdModule.this.mShowAdPromise.resolve((Object) null);
                        Promise unused = AdMobRewardedVideoAdModule.this.mShowAdPromise = null;
                    }

                    public void onRewardedAdClosed() {
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_DISMISS);
                        RewardedAd unused = AdMobRewardedVideoAdModule.this.mRewardedAd = null;
                    }

                    public void onRewardedAdFailedToShow(AdError adError) {
                        AdMobRewardedVideoAdModule.this.sendEvent(Event.DID_FAIL_TO_LOAD, AdMobUtils.createEventForAdFailedToLoad(adError));
                        AdMobRewardedVideoAdModule.this.mShowAdPromise.reject("E_AD_SHOW_FAILED", adError.getMessage());
                        RewardedAd unused = AdMobRewardedVideoAdModule.this.mRewardedAd = null;
                        Promise unused2 = AdMobRewardedVideoAdModule.this.mShowAdPromise = null;
                    }
                });
            }
        });
    }

    @ExpoMethod
    public void getIsReady(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                promise.resolve(Boolean.valueOf(AdMobRewardedVideoAdModule.this.mRewardedAd != null && AdMobRewardedVideoAdModule.this.mRewardedAd.isLoaded()));
            }
        });
    }
}
