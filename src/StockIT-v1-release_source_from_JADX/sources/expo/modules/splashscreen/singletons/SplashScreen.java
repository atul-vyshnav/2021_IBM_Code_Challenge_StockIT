package expo.modules.splashscreen.singletons;

import android.app.Activity;
import android.view.ViewGroup;
import com.facebook.react.uimanager.ViewProps;
import expo.modules.splashscreen.NativeResourcesBasedSplashScreenViewProvider;
import expo.modules.splashscreen.SplashScreenController;
import expo.modules.splashscreen.SplashScreenImageResizeMode;
import expo.modules.splashscreen.SplashScreenViewProvider;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.interfaces.SingletonModule;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0004H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007JT\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u000e2!\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000b0\u000eJ\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007JT\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u000e2!\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000b0\u000eJo\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u000f2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001f2#\b\u0002\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000b0\u000eH\u0007Je\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u001e2\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u000f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001f2#\b\u0002\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000b0\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, mo40253d2 = {"Lexpo/modules/splashscreen/singletons/SplashScreen;", "Lorg/unimodules/core/interfaces/SingletonModule;", "()V", "TAG", "", "controllers", "Ljava/util/WeakHashMap;", "Landroid/app/Activity;", "Lexpo/modules/splashscreen/SplashScreenController;", "getName", "hide", "", "activity", "successCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "hasEffect", "failureCallback", "reason", "preventAutoHide", "show", "resizeMode", "Lexpo/modules/splashscreen/SplashScreenImageResizeMode;", "rootViewClass", "Ljava/lang/Class;", "Landroid/view/ViewGroup;", "statusBarTranslucent", "splashScreenViewProvider", "Lexpo/modules/splashscreen/SplashScreenViewProvider;", "Lkotlin/Function0;", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreen.kt */
public final class SplashScreen implements SingletonModule {
    public static final SplashScreen INSTANCE = new SplashScreen();
    private static final String TAG = "SplashScreen";
    private static final WeakHashMap<Activity, SplashScreenController> controllers = new WeakHashMap<>();

    @JvmStatic
    public static final void show(Activity activity, SplashScreenImageResizeMode splashScreenImageResizeMode, Class<? extends ViewGroup> cls, boolean z) {
        show$default(activity, splashScreenImageResizeMode, cls, z, (SplashScreenViewProvider) null, (Function0) null, (Function1) null, 112, (Object) null);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenImageResizeMode splashScreenImageResizeMode, Class<? extends ViewGroup> cls, boolean z, SplashScreenViewProvider splashScreenViewProvider) {
        show$default(activity, splashScreenImageResizeMode, cls, z, splashScreenViewProvider, (Function0) null, (Function1) null, 96, (Object) null);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenImageResizeMode splashScreenImageResizeMode, Class<? extends ViewGroup> cls, boolean z, SplashScreenViewProvider splashScreenViewProvider, Function0<Unit> function0) {
        show$default(activity, splashScreenImageResizeMode, cls, z, splashScreenViewProvider, function0, (Function1) null, 64, (Object) null);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenViewProvider splashScreenViewProvider, Class<? extends ViewGroup> cls, boolean z) {
        show$default(activity, splashScreenViewProvider, cls, z, (Function0) null, (Function1) null, 48, (Object) null);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenViewProvider splashScreenViewProvider, Class<? extends ViewGroup> cls, boolean z, Function0<Unit> function0) {
        show$default(activity, splashScreenViewProvider, cls, z, function0, (Function1) null, 32, (Object) null);
    }

    public String getName() {
        return TAG;
    }

    private SplashScreen() {
    }

    public static /* synthetic */ void show$default(Activity activity, SplashScreenViewProvider splashScreenViewProvider, Class cls, boolean z, Function0 function0, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            function0 = SplashScreen$show$1.INSTANCE;
        }
        Function0 function02 = function0;
        if ((i & 32) != 0) {
            function1 = SplashScreen$show$2.INSTANCE;
        }
        show(activity, splashScreenViewProvider, (Class<? extends ViewGroup>) cls, z, (Function0<Unit>) function02, (Function1<? super String, Unit>) function1);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenViewProvider splashScreenViewProvider, Class<? extends ViewGroup> cls, boolean z, Function0<Unit> function0, Function1<? super String, Unit> function1) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(splashScreenViewProvider, "splashScreenViewProvider");
        Intrinsics.checkNotNullParameter(cls, "rootViewClass");
        Intrinsics.checkNotNullParameter(function0, "successCallback");
        Intrinsics.checkNotNullParameter(function1, "failureCallback");
        if (controllers.containsKey(activity)) {
            function1.invoke("'SplashScreen.show' has already been called for this activity.");
            return;
        }
        SplashScreenStatusBar.INSTANCE.configureTranslucent(activity, Boolean.valueOf(z));
        SplashScreenController splashScreenController = new SplashScreenController(activity, cls, splashScreenViewProvider);
        controllers.put(activity, splashScreenController);
        splashScreenController.showSplashScreen(function0);
    }

    public static /* synthetic */ void show$default(Activity activity, SplashScreenImageResizeMode splashScreenImageResizeMode, Class cls, boolean z, SplashScreenViewProvider splashScreenViewProvider, Function0 function0, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            splashScreenViewProvider = new NativeResourcesBasedSplashScreenViewProvider(splashScreenImageResizeMode);
        }
        SplashScreenViewProvider splashScreenViewProvider2 = splashScreenViewProvider;
        if ((i & 32) != 0) {
            function0 = SplashScreen$show$3.INSTANCE;
        }
        Function0 function02 = function0;
        if ((i & 64) != 0) {
            function1 = SplashScreen$show$4.INSTANCE;
        }
        show(activity, splashScreenImageResizeMode, cls, z, splashScreenViewProvider2, function02, function1);
    }

    @JvmStatic
    public static final void show(Activity activity, SplashScreenImageResizeMode splashScreenImageResizeMode, Class<? extends ViewGroup> cls, boolean z, SplashScreenViewProvider splashScreenViewProvider, Function0<Unit> function0, Function1<? super String, Unit> function1) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(splashScreenImageResizeMode, ViewProps.RESIZE_MODE);
        Intrinsics.checkNotNullParameter(cls, "rootViewClass");
        Intrinsics.checkNotNullParameter(splashScreenViewProvider, "splashScreenViewProvider");
        Intrinsics.checkNotNullParameter(function0, "successCallback");
        Intrinsics.checkNotNullParameter(function1, "failureCallback");
        show(activity, splashScreenViewProvider, cls, z, function0, function1);
    }

    public final void preventAutoHide(Activity activity, Function1<? super Boolean, Unit> function1, Function1<? super String, Unit> function12) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(function1, "successCallback");
        Intrinsics.checkNotNullParameter(function12, "failureCallback");
        if (!controllers.containsKey(activity)) {
            function12.invoke("No native splash screen registered for provided activity. Please configure your application's main Activity to call 'SplashScreen.show' (https://github.com/expo/expo/tree/master/packages/expo-splash-screen#-configure-android).");
            return;
        }
        SplashScreenController splashScreenController = controllers.get(activity);
        if (splashScreenController != null) {
            splashScreenController.preventAutoHide(function1, function12);
        }
    }

    @JvmStatic
    public static final void preventAutoHide(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        INSTANCE.preventAutoHide(activity, SplashScreen$preventAutoHide$1.INSTANCE, SplashScreen$preventAutoHide$2.INSTANCE);
    }

    public final void hide(Activity activity, Function1<? super Boolean, Unit> function1, Function1<? super String, Unit> function12) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(function1, "successCallback");
        Intrinsics.checkNotNullParameter(function12, "failureCallback");
        if (!controllers.containsKey(activity)) {
            function12.invoke("No native splash screen registered for provided activity. Please configure your application's main Activity to call 'SplashScreen.show' (https://github.com/expo/expo/tree/master/packages/expo-splash-screen#-configure-android).");
            return;
        }
        SplashScreenController splashScreenController = controllers.get(activity);
        if (splashScreenController != null) {
            splashScreenController.hideSplashScreen(function1, function12);
        }
    }

    @JvmStatic
    public static final void hide(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        INSTANCE.hide(activity, SplashScreen$hide$1.INSTANCE, SplashScreen$hide$2.INSTANCE);
    }
}
