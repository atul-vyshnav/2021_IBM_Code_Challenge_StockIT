package expo.modules.ads.admob;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdSize;

class AdMobUtils {
    static String errorStringForAdFailedCode(int i) {
        if (i == 0) {
            return "ERROR_CODE_INTERNAL_ERROR";
        }
        if (i == 1) {
            return "ERROR_CODE_INVALID_REQUEST";
        }
        if (i == 2) {
            return "ERROR_CODE_NETWORK_ERROR";
        }
        if (i != 3) {
            return null;
        }
        return "ERROR_CODE_NO_FILL";
    }

    AdMobUtils() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.android.gms.ads.AdSize getAdSizeFromString(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -2023649721: goto L_0x004e;
                case -1735624867: goto L_0x0044;
                case -1396342996: goto L_0x003a;
                case -1294719333: goto L_0x0030;
                case -994916779: goto L_0x0026;
                case 438737894: goto L_0x001c;
                case 1251459344: goto L_0x0012;
                case 2081083770: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0058
        L_0x0008:
            java.lang.String r0 = "mediumRectangle"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 2
            goto L_0x0059
        L_0x0012:
            java.lang.String r0 = "smartBannerPortrait"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 5
            goto L_0x0059
        L_0x001c:
            java.lang.String r0 = "smartBannerLandscape"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 6
            goto L_0x0059
        L_0x0026:
            java.lang.String r0 = "smartBanner"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 7
            goto L_0x0059
        L_0x0030:
            java.lang.String r0 = "fullBanner"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 3
            goto L_0x0059
        L_0x003a:
            java.lang.String r0 = "banner"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 0
            goto L_0x0059
        L_0x0044:
            java.lang.String r0 = "leaderBoard"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 4
            goto L_0x0059
        L_0x004e:
            java.lang.String r0 = "largeBanner"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0058
            r1 = 1
            goto L_0x0059
        L_0x0058:
            r1 = -1
        L_0x0059:
            switch(r1) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0071;
                case 2: goto L_0x006e;
                case 3: goto L_0x006b;
                case 4: goto L_0x0068;
                case 5: goto L_0x0065;
                case 6: goto L_0x0062;
                case 7: goto L_0x005f;
                default: goto L_0x005c;
            }
        L_0x005c:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.BANNER
            return r1
        L_0x005f:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.SMART_BANNER
            return r1
        L_0x0062:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.SMART_BANNER
            return r1
        L_0x0065:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.SMART_BANNER
            return r1
        L_0x0068:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.LEADERBOARD
            return r1
        L_0x006b:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.FULL_BANNER
            return r1
        L_0x006e:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE
            return r1
        L_0x0071:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.LARGE_BANNER
            return r1
        L_0x0074:
            com.google.android.gms.ads.AdSize r1 = com.google.android.gms.ads.AdSize.BANNER
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.ads.admob.AdMobUtils.getAdSizeFromString(java.lang.String):com.google.android.gms.ads.AdSize");
    }

    static Bundle createEventForAdFailedToLoad(AdError adError) {
        Bundle bundle = new Bundle();
        bundle.putString("error", adError.getMessage());
        return bundle;
    }

    static Bundle createEventForAdFailedToLoad(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("error", errorStringForAdFailedCode(i));
        return bundle;
    }

    static Bundle createEventForSizeChange(Context context, AdSize adSize) {
        int i;
        int i2;
        Bundle bundle = new Bundle();
        if (adSize == AdSize.SMART_BANNER) {
            i2 = toDIPFromPixel(context, adSize.getWidthInPixels(context));
            i = toDIPFromPixel(context, adSize.getHeightInPixels(context));
        } else {
            i2 = adSize.getWidth();
            i = adSize.getHeight();
        }
        bundle.putDouble("width", (double) i2);
        bundle.putDouble("height", (double) i);
        return bundle;
    }

    static int toDIPFromPixel(Context context, int i) {
        return (int) (((float) i) / context.getResources().getDisplayMetrics().density);
    }
}
