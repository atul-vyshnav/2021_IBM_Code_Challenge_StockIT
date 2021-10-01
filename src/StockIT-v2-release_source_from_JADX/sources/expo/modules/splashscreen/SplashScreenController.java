package expo.modules.splashscreen;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import expo.modules.splashscreen.exceptions.NoContentViewException;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0017\u001a\u00020\u0012H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0006H\u0002JP\u0010\u001a\u001a\u00020\u00192#\b\u0002\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00190\u001c2#\b\u0002\u0010 \u001a\u001d\u0012\u0013\u0012\u00110!¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00190\u001cJL\u0010#\u001a\u00020\u00192!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00190\u001c2!\u0010 \u001a\u001d\u0012\u0013\u0012\u00110!¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\u00190\u001cJ\b\u0010$\u001a\u00020\u0019H\u0002J\u0016\u0010%\u001a\u00020\u00192\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190&R\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00030\u00030\u0014X\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, mo40253d2 = {"Lexpo/modules/splashscreen/SplashScreenController;", "", "activity", "Landroid/app/Activity;", "rootViewClass", "Ljava/lang/Class;", "Landroid/view/ViewGroup;", "splashScreenViewProvider", "Lexpo/modules/splashscreen/SplashScreenViewProvider;", "(Landroid/app/Activity;Ljava/lang/Class;Lexpo/modules/splashscreen/SplashScreenViewProvider;)V", "autoHideEnabled", "", "contentView", "handler", "Landroid/os/Handler;", "rootView", "splashScreenShown", "splashScreenView", "Landroid/view/View;", "weakActivity", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "findRootView", "view", "handleRootView", "", "hideSplashScreen", "successCallback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "hasEffect", "failureCallback", "", "reason", "preventAutoHide", "searchForRootView", "showSplashScreen", "Lkotlin/Function0;", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenController.kt */
public final class SplashScreenController {
    /* access modifiers changed from: private */
    public boolean autoHideEnabled;
    /* access modifiers changed from: private */
    public final ViewGroup contentView;
    private final Handler handler;
    /* access modifiers changed from: private */
    public ViewGroup rootView;
    private final Class<? extends ViewGroup> rootViewClass;
    /* access modifiers changed from: private */
    public boolean splashScreenShown;
    /* access modifiers changed from: private */
    public View splashScreenView;
    private final WeakReference<Activity> weakActivity;

    public SplashScreenController(Activity activity, Class<? extends ViewGroup> cls, SplashScreenViewProvider splashScreenViewProvider) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(cls, "rootViewClass");
        Intrinsics.checkNotNullParameter(splashScreenViewProvider, "splashScreenViewProvider");
        this.rootViewClass = cls;
        this.weakActivity = new WeakReference<>(activity);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(16908290);
        if (viewGroup != null) {
            this.contentView = viewGroup;
            this.splashScreenView = splashScreenViewProvider.createSplashScreenView(activity);
            this.handler = new Handler();
            this.autoHideEnabled = true;
            return;
        }
        throw new NoContentViewException();
    }

    public static /* synthetic */ void showSplashScreen$default(SplashScreenController splashScreenController, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = SplashScreenController$showSplashScreen$1.INSTANCE;
        }
        splashScreenController.showSplashScreen(function0);
    }

    public final void showSplashScreen(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "successCallback");
        Activity activity = (Activity) this.weakActivity.get();
        if (activity != null) {
            activity.runOnUiThread(new SplashScreenController$showSplashScreen$2(this, function0));
        }
    }

    public final void preventAutoHide(Function1<? super Boolean, Unit> function1, Function1<? super String, Unit> function12) {
        Intrinsics.checkNotNullParameter(function1, "successCallback");
        Intrinsics.checkNotNullParameter(function12, "failureCallback");
        if (!this.autoHideEnabled || !this.splashScreenShown) {
            function1.invoke(false);
            return;
        }
        this.autoHideEnabled = false;
        function1.invoke(true);
    }

    public static /* synthetic */ void hideSplashScreen$default(SplashScreenController splashScreenController, Function1 function1, Function1 function12, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = SplashScreenController$hideSplashScreen$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            function12 = SplashScreenController$hideSplashScreen$2.INSTANCE;
        }
        splashScreenController.hideSplashScreen(function1, function12);
    }

    public final void hideSplashScreen(Function1<? super Boolean, Unit> function1, Function1<? super String, Unit> function12) {
        Intrinsics.checkNotNullParameter(function1, "successCallback");
        Intrinsics.checkNotNullParameter(function12, "failureCallback");
        if (!this.splashScreenShown) {
            function1.invoke(false);
            return;
        }
        Activity activity = (Activity) this.weakActivity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            function12.invoke("Cannot hide native splash screen on activity that is already destroyed (application is already closed).");
        } else {
            activity.runOnUiThread(new SplashScreenController$hideSplashScreen$3(this, function1));
        }
    }

    /* access modifiers changed from: private */
    public final void searchForRootView() {
        if (this.rootView == null) {
            ViewGroup findRootView = findRootView(this.contentView);
            if (findRootView != null) {
                handleRootView(findRootView);
            } else {
                this.handler.postDelayed(new SplashScreenController$searchForRootView$2(this), 20);
            }
        }
    }

    private final ViewGroup findRootView(View view) {
        if (this.rootViewClass.isInstance(view)) {
            if (view != null) {
                return (ViewGroup) view;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup");
        } else if (!(!Intrinsics.areEqual((Object) view, (Object) this.splashScreenView)) || !(view instanceof ViewGroup)) {
            return null;
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                Intrinsics.checkNotNullExpressionValue(childAt, "view.getChildAt(idx)");
                ViewGroup findRootView = findRootView(childAt);
                if (findRootView != null) {
                    return findRootView;
                }
            }
            return null;
        }
    }

    private final void handleRootView(ViewGroup viewGroup) {
        this.rootView = viewGroup;
        if ((viewGroup != null ? viewGroup.getChildCount() : 0) > 0 && this.autoHideEnabled) {
            hideSplashScreen$default(this, (Function1) null, (Function1) null, 3, (Object) null);
        }
        viewGroup.setOnHierarchyChangeListener(new SplashScreenController$handleRootView$1(this));
    }
}
