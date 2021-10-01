package expo.modules.ads.admob;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import expo.modules.ads.admob.AdMobBannerViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;

public class AdMobBannerView extends FrameLayout {
    private Bundle mAdditionalRequestParams;
    private EventEmitter mEventEmitter;
    /* access modifiers changed from: private */
    public String mSizeString;

    public AdMobBannerView(Context context, EventEmitter eventEmitter) {
        super(context);
        this.mEventEmitter = eventEmitter;
        init();
    }

    private void init() {
        attachNewAdView();
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i != i5 || i3 != i7 || i2 != i6 || i4 != i8) {
                    AdMobBannerView adMobBannerView = AdMobBannerView.this;
                    adMobBannerView.setBannerSize(adMobBannerView.mSizeString);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void attachNewAdView() {
        AdView adView = new AdView(getContext());
        AdView adView2 = (AdView) getChildAt(0);
        removeAllViews();
        if (adView2 != null) {
            adView2.destroy();
        }
        addView(adView, new FrameLayout.LayoutParams(-1, -1));
        attachEvents();
    }

    /* access modifiers changed from: protected */
    public void attachEvents() {
        final AdView adView = (AdView) getChildAt(0);
        adView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                int widthInPixels = adView.getAdSize().getWidthInPixels(AdMobBannerView.this.getContext());
                int heightInPixels = adView.getAdSize().getHeightInPixels(AdMobBannerView.this.getContext());
                int left = adView.getLeft();
                int top = adView.getTop();
                adView.measure(widthInPixels, heightInPixels);
                adView.layout(left, top, widthInPixels + left, heightInPixels + top);
                AdMobBannerView.this.sendEvent(AdMobBannerViewManager.Events.EVENT_RECEIVE_AD);
            }

            public void onAdFailedToLoad(int i) {
                AdMobBannerView.this.sendEvent(AdMobBannerViewManager.Events.EVENT_ERROR, AdMobUtils.createEventForAdFailedToLoad(i));
            }

            public void onAdOpened() {
                AdMobBannerView.this.sendEvent(AdMobBannerViewManager.Events.EVENT_WILL_PRESENT);
            }

            public void onAdClosed() {
                AdMobBannerView.this.sendEvent(AdMobBannerViewManager.Events.EVENT_WILL_DISMISS);
            }

            public void onAdLeftApplication() {
                AdMobBannerView.this.sendEvent(AdMobBannerViewManager.Events.EVENT_WILL_LEAVE_APP);
            }
        });
    }

    public void setBannerSize(String str) {
        this.mSizeString = str;
        AdSize adSizeFromString = AdMobUtils.getAdSizeFromString(str);
        String adUnitId = ((AdView) getChildAt(0)).getAdUnitId();
        attachNewAdView();
        AdView adView = (AdView) getChildAt(0);
        adView.setAdSize(adSizeFromString);
        adView.setAdUnitId(adUnitId);
        sendEvent(AdMobBannerViewManager.Events.EVENT_SIZE_CHANGE, AdMobUtils.createEventForSizeChange(getContext(), adSizeFromString));
        loadAd(adView);
    }

    public void setAdUnitID(String str) {
        AdSize adSize = ((AdView) getChildAt(0)).getAdSize();
        attachNewAdView();
        AdView adView = (AdView) getChildAt(0);
        adView.setAdUnitId(str);
        adView.setAdSize(adSize);
        loadAd(adView);
    }

    public void setAdditionalRequestParams(Bundle bundle) {
        if (!bundle.equals(this.mAdditionalRequestParams)) {
            this.mAdditionalRequestParams = bundle;
            loadAd((AdView) getChildAt(0));
        }
    }

    private void loadAd(AdView adView) {
        if (adView.getAdSize() != null && adView.getAdUnitId() != null && this.mAdditionalRequestParams != null) {
            AdRequest.Builder addNetworkExtrasBundle = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, this.mAdditionalRequestParams);
            String testDeviceID = AdMobModule.getTestDeviceID();
            if (testDeviceID != null) {
                addNetworkExtrasBundle = addNetworkExtrasBundle.addTestDevice(testDeviceID);
            }
            adView.loadAd(addNetworkExtrasBundle.build());
        }
    }

    /* access modifiers changed from: private */
    public void sendEvent(AdMobBannerViewManager.Events events) {
        sendEvent(events, new Bundle());
    }

    /* access modifiers changed from: private */
    public void sendEvent(final AdMobBannerViewManager.Events events, final Bundle bundle) {
        this.mEventEmitter.emit(getId(), (EventEmitter.Event) new EventEmitter.BaseEvent() {
            public String getEventName() {
                return events.toString();
            }

            public Bundle getEventBody() {
                return bundle;
            }
        });
    }
}
