package expo.modules.ads.admob;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import expo.modules.ads.admob.PublisherBannerViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;

public class PublisherBannerView extends FrameLayout implements AppEventListener {
    private Bundle mAdditionalRequestParams;
    private EventEmitter mEventEmitter;

    public PublisherBannerView(Context context, EventEmitter eventEmitter) {
        super(context);
        this.mEventEmitter = eventEmitter;
        attachNewAdView();
    }

    public void onAppEvent(String str, String str2) {
        Log.d("PublisherAdBanner", String.format("Received app event (%s, %s)", new Object[]{str, str2}));
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        sendEvent(PublisherBannerViewManager.Events.EVENT_ADMOB_EVENT_RECEIVED, bundle);
    }

    /* access modifiers changed from: protected */
    public void attachNewAdView() {
        PublisherAdView publisherAdView = new PublisherAdView(getContext());
        publisherAdView.setAppEventListener(this);
        PublisherAdView publisherAdView2 = (PublisherAdView) getChildAt(0);
        removeAllViews();
        if (publisherAdView2 != null) {
            publisherAdView2.destroy();
        }
        addView(publisherAdView, new FrameLayout.LayoutParams(-1, -1));
        attachEvents();
    }

    /* access modifiers changed from: protected */
    public void attachEvents() {
        final PublisherAdView publisherAdView = (PublisherAdView) getChildAt(0);
        publisherAdView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                int widthInPixels = publisherAdView.getAdSize().getWidthInPixels(PublisherBannerView.this.getContext());
                int heightInPixels = publisherAdView.getAdSize().getHeightInPixels(PublisherBannerView.this.getContext());
                int left = publisherAdView.getLeft();
                int top = publisherAdView.getTop();
                publisherAdView.measure(widthInPixels, heightInPixels);
                publisherAdView.layout(left, top, widthInPixels + left, heightInPixels + top);
                PublisherBannerView.this.sendEvent(PublisherBannerViewManager.Events.EVENT_RECEIVE_AD);
            }

            public void onAdFailedToLoad(int i) {
                PublisherBannerView.this.sendEvent(PublisherBannerViewManager.Events.EVENT_ERROR, AdMobUtils.createEventForAdFailedToLoad(i));
            }

            public void onAdOpened() {
                PublisherBannerView.this.sendEvent(PublisherBannerViewManager.Events.EVENT_WILL_PRESENT);
            }

            public void onAdClosed() {
                PublisherBannerView.this.sendEvent(PublisherBannerViewManager.Events.EVENT_WILL_DISMISS);
            }

            public void onAdLeftApplication() {
                PublisherBannerView.this.sendEvent(PublisherBannerViewManager.Events.EVENT_WILL_LEAVE_APP);
            }
        });
    }

    public void setBannerSize(String str) {
        AdSize adSizeFromString = AdMobUtils.getAdSizeFromString(str);
        String adUnitId = ((PublisherAdView) getChildAt(0)).getAdUnitId();
        attachNewAdView();
        PublisherAdView publisherAdView = (PublisherAdView) getChildAt(0);
        publisherAdView.setAdSizes(adSizeFromString);
        publisherAdView.setAdUnitId(adUnitId);
        sendEvent(PublisherBannerViewManager.Events.EVENT_SIZE_CHANGE, AdMobUtils.createEventForSizeChange(getContext(), adSizeFromString));
        loadAd(publisherAdView);
    }

    public void setAdUnitID(String str) {
        AdSize[] adSizes = ((PublisherAdView) getChildAt(0)).getAdSizes();
        attachNewAdView();
        PublisherAdView publisherAdView = (PublisherAdView) getChildAt(0);
        publisherAdView.setAdUnitId(str);
        publisherAdView.setAdSizes(adSizes);
        loadAd(publisherAdView);
    }

    public void setAdditionalRequestParams(Bundle bundle) {
        if (!bundle.equals(this.mAdditionalRequestParams)) {
            this.mAdditionalRequestParams = bundle;
            loadAd((PublisherAdView) getChildAt(0));
        }
    }

    private void loadAd(PublisherAdView publisherAdView) {
        if (publisherAdView.getAdSizes() != null && publisherAdView.getAdUnitId() != null && this.mAdditionalRequestParams != null) {
            PublisherAdRequest.Builder addNetworkExtrasBundle = new PublisherAdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, this.mAdditionalRequestParams);
            String testDeviceID = AdMobModule.getTestDeviceID();
            if (testDeviceID != null) {
                addNetworkExtrasBundle = addNetworkExtrasBundle.addTestDevice(testDeviceID);
            }
            publisherAdView.loadAd(addNetworkExtrasBundle.build());
        }
    }

    /* access modifiers changed from: private */
    public void sendEvent(PublisherBannerViewManager.Events events) {
        sendEvent(events, new Bundle());
    }

    /* access modifiers changed from: private */
    public void sendEvent(final PublisherBannerViewManager.Events events, final Bundle bundle) {
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
