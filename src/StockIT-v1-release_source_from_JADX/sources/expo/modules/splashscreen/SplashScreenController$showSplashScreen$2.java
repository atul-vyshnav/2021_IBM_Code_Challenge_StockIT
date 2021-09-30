package expo.modules.splashscreen;

import android.view.ViewGroup;
import android.view.ViewParent;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo40253d2 = {"<anonymous>", "", "run"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenController.kt */
final class SplashScreenController$showSplashScreen$2 implements Runnable {
    final /* synthetic */ Function0 $successCallback;
    final /* synthetic */ SplashScreenController this$0;

    SplashScreenController$showSplashScreen$2(SplashScreenController splashScreenController, Function0 function0) {
        this.this$0 = splashScreenController;
        this.$successCallback = function0;
    }

    public final void run() {
        ViewParent parent = this.this$0.splashScreenView.getParent();
        if (!(parent instanceof ViewGroup)) {
            parent = null;
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        if (viewGroup != null) {
            viewGroup.removeView(this.this$0.splashScreenView);
        }
        this.this$0.contentView.addView(this.this$0.splashScreenView);
        this.this$0.splashScreenShown = true;
        this.$successCallback.invoke();
        this.this$0.searchForRootView();
    }
}
