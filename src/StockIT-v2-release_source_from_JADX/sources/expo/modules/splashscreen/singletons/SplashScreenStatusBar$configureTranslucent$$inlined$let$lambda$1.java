package expo.modules.splashscreen.singletons;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, mo40253d2 = {"<anonymous>", "", "run", "expo/modules/splashscreen/singletons/SplashScreenStatusBar$configureTranslucent$1$1"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenStatusBar.kt */
final class SplashScreenStatusBar$configureTranslucent$$inlined$let$lambda$1 implements Runnable {
    final /* synthetic */ Activity $activity$inlined;
    final /* synthetic */ boolean $it;

    SplashScreenStatusBar$configureTranslucent$$inlined$let$lambda$1(boolean z, Activity activity) {
        this.$it = z;
        this.$activity$inlined = activity;
    }

    public final void run() {
        Window window = this.$activity$inlined.getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "activity.window");
        View decorView = window.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "activity.window.decorView");
        if (this.$it) {
            decorView.setOnApplyWindowInsetsListener(SplashScreenStatusBar$configureTranslucent$1$1$1.INSTANCE);
        } else {
            decorView.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
        }
        ViewCompat.requestApplyInsets(decorView);
    }
}
