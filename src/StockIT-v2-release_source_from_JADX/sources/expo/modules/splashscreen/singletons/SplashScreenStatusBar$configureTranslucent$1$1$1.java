package expo.modules.splashscreen.singletons;

import android.view.View;
import android.view.WindowInsets;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00012\u000e\u0010\u0003\u001a\n \u0002*\u0004\u0018\u00010\u00040\u00042\u000e\u0010\u0005\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0006"}, mo40253d2 = {"<anonymous>", "Landroid/view/WindowInsets;", "kotlin.jvm.PlatformType", "v", "Landroid/view/View;", "insets", "onApplyWindowInsets"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenStatusBar.kt */
final class SplashScreenStatusBar$configureTranslucent$1$1$1 implements View.OnApplyWindowInsetsListener {
    public static final SplashScreenStatusBar$configureTranslucent$1$1$1 INSTANCE = new SplashScreenStatusBar$configureTranslucent$1$1$1();

    SplashScreenStatusBar$configureTranslucent$1$1$1() {
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        WindowInsets onApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        Intrinsics.checkNotNullExpressionValue(onApplyWindowInsets, "defaultInsets");
        return onApplyWindowInsets.replaceSystemWindowInsets(onApplyWindowInsets.getSystemWindowInsetLeft(), 0, onApplyWindowInsets.getSystemWindowInsetRight(), onApplyWindowInsets.getSystemWindowInsetBottom());
    }
}
