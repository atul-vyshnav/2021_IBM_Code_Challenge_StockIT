package expo.modules.ads.admob;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.EventEmitter;

public class AdMobInterstitialAdModule extends ExportedModule {
    private ActivityProvider mActivityProvider;
    /* access modifiers changed from: private */
    public String mAdUnitID;
    private EventEmitter mEventEmitter;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    /* access modifiers changed from: private */
    public Promise mRequestAdPromise;
    /* access modifiers changed from: private */
    public Promise mShowAdPromise;

    public String getName() {
        return "ExpoAdsAdMobInterstitialManager";
    }

    public enum Events {
        DID_LOAD("interstitialDidLoad"),
        DID_FAIL_TO_LOAD("interstitialDidFailToLoad"),
        DID_OPEN("interstitialDidOpen"),
        DID_CLOSE("interstitialDidClose"),
        WILL_LEAVE_APPLICATION("interstitialWillLeaveApplication");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public AdMobInterstitialAdModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mEventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class);
        this.mActivityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
    }

    /* access modifiers changed from: private */
    public void sendEvent(String str, Bundle bundle) {
        this.mEventEmitter.emit(str, bundle);
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
                AdMobInterstitialAdModule adMobInterstitialAdModule = AdMobInterstitialAdModule.this;
                adMobInterstitialAdModule.recreateInterstitialAdWithAdUnitID(adMobInterstitialAdModule.mAdUnitID);
                if (AdMobInterstitialAdModule.this.mInterstitialAd.isLoaded() || AdMobInterstitialAdModule.this.mInterstitialAd.isLoading()) {
                    promise.reject("E_AD_ALREADY_LOADED", "Ad is already loaded.", (Throwable) null);
                    return;
                }
                Promise unused = AdMobInterstitialAdModule.this.mRequestAdPromise = promise;
                AdRequest.Builder addNetworkExtrasBundle = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, readableArguments.toBundle());
                String testDeviceID = AdMobModule.getTestDeviceID();
                if (testDeviceID != null) {
                    addNetworkExtrasBundle = addNetworkExtrasBundle.addTestDevice(testDeviceID);
                }
                AdMobInterstitialAdModule.this.mInterstitialAd.loadAd(addNetworkExtrasBundle.build());
            }
        });
    }

    @ExpoMethod
    public void showAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (AdMobInterstitialAdModule.this.mInterstitialAd == null || !AdMobInterstitialAdModule.this.mInterstitialAd.isLoaded()) {
                    promise.reject("E_AD_NOT_READY", "Ad is not ready", (Throwable) null);
                    return;
                }
                Promise unused = AdMobInterstitialAdModule.this.mShowAdPromise = promise;
                AdMobInterstitialAdModule.this.mInterstitialAd.show();
            }
        });
    }

    @ExpoMethod
    public void dismissAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (AdMobInterstitialAdModule.this.mInterstitialAd == null || !AdMobInterstitialAdModule.this.mInterstitialAd.isLoaded()) {
                    promise.reject("E_AD_NOT_READY", "Ad is not ready", (Throwable) null);
                    return;
                }
                Promise unused = AdMobInterstitialAdModule.this.mShowAdPromise = promise;
                AdMobInterstitialAdModule adMobInterstitialAdModule = AdMobInterstitialAdModule.this;
                adMobInterstitialAdModule.recreateInterstitialAdWithAdUnitID(adMobInterstitialAdModule.mAdUnitID);
            }
        });
    }

    @ExpoMethod
    public void getIsReady(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                promise.resolve(Boolean.valueOf(AdMobInterstitialAdModule.this.mInterstitialAd != null && AdMobInterstitialAdModule.this.mInterstitialAd.isLoaded()));
            }
        });
    }

    /* access modifiers changed from: private */
    public void recreateInterstitialAdWithAdUnitID(String str) {
        if (this.mInterstitialAd != null) {
            this.mInterstitialAd = null;
        }
        InterstitialAd interstitialAd = new InterstitialAd(this.mActivityProvider.getCurrentActivity());
        this.mInterstitialAd = interstitialAd;
        interstitialAd.setAdUnitId(str);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                AdMobInterstitialAdModule.this.mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdClosed() {
                        AdMobInterstitialAdModule.this.sendEvent(Events.DID_CLOSE.toString(), new Bundle());
                    }

                    public void onAdFailedToLoad(int i) {
                        AdMobInterstitialAdModule.this.sendEvent(Events.DID_FAIL_TO_LOAD.toString(), AdMobUtils.createEventForAdFailedToLoad(i));
                        if (AdMobInterstitialAdModule.this.mRequestAdPromise != null) {
                            AdMobInterstitialAdModule.this.mRequestAdPromise.reject("E_AD_REQUEST_FAILED", AdMobUtils.errorStringForAdFailedCode(i), (Throwable) null);
                            Promise unused = AdMobInterstitialAdModule.this.mRequestAdPromise = null;
                        }
                    }

                    public void onAdLeftApplication() {
                        AdMobInterstitialAdModule.this.sendEvent(Events.WILL_LEAVE_APPLICATION.toString(), new Bundle());
                    }

                    public void onAdLoaded() {
                        AdMobInterstitialAdModule.this.sendEvent(Events.DID_LOAD.toString(), new Bundle());
                        if (AdMobInterstitialAdModule.this.mRequestAdPromise != null) {
                            AdMobInterstitialAdModule.this.mRequestAdPromise.resolve((Object) null);
                            Promise unused = AdMobInterstitialAdModule.this.mRequestAdPromise = null;
                        }
                    }

                    public void onAdOpened() {
                        AdMobInterstitialAdModule.this.sendEvent(Events.DID_OPEN.toString(), new Bundle());
                        if (AdMobInterstitialAdModule.this.mShowAdPromise != null) {
                            AdMobInterstitialAdModule.this.mShowAdPromise.resolve((Object) null);
                            Promise unused = AdMobInterstitialAdModule.this.mShowAdPromise = null;
                        }
                    }
                });
            }
        });
    }
}
