package expo.modules.splashscreen;

import android.view.View;
import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0016J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\b"}, mo40253d2 = {"expo/modules/splashscreen/SplashScreenController$handleRootView$1", "Landroid/view/ViewGroup$OnHierarchyChangeListener;", "onChildViewAdded", "", "parent", "Landroid/view/View;", "child", "onChildViewRemoved", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenController.kt */
public final class SplashScreenController$handleRootView$1 implements ViewGroup.OnHierarchyChangeListener {
    final /* synthetic */ SplashScreenController this$0;

    SplashScreenController$handleRootView$1(SplashScreenController splashScreenController) {
        this.this$0 = splashScreenController;
    }

    public void onChildViewRemoved(View view, View view2) {
        Intrinsics.checkNotNullParameter(view, "parent");
        Intrinsics.checkNotNullParameter(view2, "child");
        ViewGroup access$getRootView$p = this.this$0.rootView;
        if (access$getRootView$p != null && access$getRootView$p.getChildCount() == 0) {
            SplashScreenController.showSplashScreen$default(this.this$0, (Function0) null, 1, (Object) null);
        }
    }

    public void onChildViewAdded(View view, View view2) {
        Intrinsics.checkNotNullParameter(view, "parent");
        Intrinsics.checkNotNullParameter(view2, "child");
        ViewGroup access$getRootView$p = this.this$0.rootView;
        if (access$getRootView$p != null && access$getRootView$p.getChildCount() == 1 && this.this$0.autoHideEnabled) {
            SplashScreenController.hideSplashScreen$default(this.this$0, (Function1) null, (Function1) null, 3, (Object) null);
        }
    }
}
