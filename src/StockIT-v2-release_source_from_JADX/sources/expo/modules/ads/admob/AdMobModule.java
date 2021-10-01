package expo.modules.ads.admob;

import android.content.Context;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;

public class AdMobModule extends ExportedModule {
    private static String sTestDeviceID;

    public String getName() {
        return "ExpoAdsAdMob";
    }

    public AdMobModule(Context context) {
        super(context);
    }

    public static String getTestDeviceID() {
        return sTestDeviceID;
    }

    @ExpoMethod
    public void setTestDeviceIDAsync(String str, Promise promise) {
        if (str == null || "".equals(str)) {
            sTestDeviceID = null;
        } else if ("EMULATOR".equals(str)) {
            sTestDeviceID = "B3EEABB8EE11C2BE770B684D95219ECB";
        } else {
            sTestDeviceID = str;
        }
        promise.resolve((Object) null);
    }
}
