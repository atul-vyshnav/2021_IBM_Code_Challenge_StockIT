package expo.modules.splashscreen.singletons;

import android.app.Activity;
import android.os.Build;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\t¨\u0006\n"}, mo40253d2 = {"Lexpo/modules/splashscreen/singletons/SplashScreenStatusBar;", "", "()V", "configureTranslucent", "", "activity", "Landroid/app/Activity;", "translucent", "", "(Landroid/app/Activity;Ljava/lang/Boolean;)V", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenStatusBar.kt */
public final class SplashScreenStatusBar {
    public static final SplashScreenStatusBar INSTANCE = new SplashScreenStatusBar();

    private SplashScreenStatusBar() {
    }

    public final void configureTranslucent(Activity activity, Boolean bool) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (Build.VERSION.SDK_INT >= 21 && bool != null) {
            activity.runOnUiThread(new SplashScreenStatusBar$configureTranslucent$$inlined$let$lambda$1(bool.booleanValue(), activity));
        }
    }
}
