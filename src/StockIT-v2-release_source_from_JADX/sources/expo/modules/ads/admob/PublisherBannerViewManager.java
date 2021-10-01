package expo.modules.ads.admob;

import android.content.Context;
import expo.modules.ads.admob.AdMobBannerViewManager;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.ViewManager;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ExpoProp;
import org.unimodules.core.interfaces.services.EventEmitter;

public class PublisherBannerViewManager extends ViewManager<PublisherBannerView> {
    public static final String PROP_ADDITIONAL_REQUEST_PARAMS = "additionalRequestParams";
    public static final String PROP_AD_UNIT_ID = "adUnitID";
    public static final String PROP_BANNER_SIZE = "bannerSize";
    private EventEmitter mEventEmitter;

    public String getName() {
        return "ExpoAdsPublisherBannerView";
    }

    public enum Events {
        EVENT_SIZE_CHANGE("onSizeChange"),
        EVENT_RECEIVE_AD("onAdViewDidReceiveAd"),
        EVENT_ERROR("onDidFailToReceiveAdWithError"),
        EVENT_WILL_PRESENT("onAdViewWillPresentScreen"),
        EVENT_WILL_DISMISS("onAdViewWillDismissScreen"),
        EVENT_DID_DISMISS("onAdViewDidDismissScreen"),
        EVENT_WILL_LEAVE_APP("onAdViewWillLeaveApplication"),
        EVENT_ADMOB_EVENT_RECEIVED("onAdmobDispatchAppEvent");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mEventEmitter = (EventEmitter) moduleRegistry.getModule(EventEmitter.class);
    }

    public PublisherBannerView createViewInstance(Context context) {
        return new PublisherBannerView(context, this.mEventEmitter);
    }

    public ViewManager.ViewManagerType getViewManagerType() {
        return ViewManager.ViewManagerType.GROUP;
    }

    public List<String> getExportedEventNames() {
        ArrayList arrayList = new ArrayList(AdMobBannerViewManager.Events.values().length);
        for (AdMobBannerViewManager.Events events : AdMobBannerViewManager.Events.values()) {
            arrayList.add(events.toString());
        }
        return arrayList;
    }

    @ExpoProp(name = "bannerSize")
    public void setBannerSize(PublisherBannerView publisherBannerView, String str) {
        publisherBannerView.setBannerSize(str);
    }

    @ExpoProp(name = "adUnitID")
    public void setAdUnitID(PublisherBannerView publisherBannerView, String str) {
        publisherBannerView.setAdUnitID(str);
    }

    @ExpoProp(name = "additionalRequestParams")
    public void setPropAdditionalRequestParams(PublisherBannerView publisherBannerView, ReadableArguments readableArguments) {
        publisherBannerView.setAdditionalRequestParams(readableArguments.toBundle());
    }
}
