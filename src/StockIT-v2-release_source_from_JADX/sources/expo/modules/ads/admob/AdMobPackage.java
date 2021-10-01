package expo.modules.ads.admob;

import android.content.Context;
import java.util.Arrays;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;

public class AdMobPackage extends BasePackage {
    public List<ExportedModule> createExportedModules(Context context) {
        return Arrays.asList(new ExportedModule[]{new AdMobModule(context), new AdMobInterstitialAdModule(context), new AdMobRewardedVideoAdModule(context)});
    }

    public List<ViewManager> createViewManagers(Context context) {
        return Arrays.asList(new ViewManager[]{new AdMobBannerViewManager(), new PublisherBannerViewManager()});
    }
}
