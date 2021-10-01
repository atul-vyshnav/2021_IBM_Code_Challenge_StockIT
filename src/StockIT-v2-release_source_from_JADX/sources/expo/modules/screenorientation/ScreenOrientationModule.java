package expo.modules.screenorientation;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.react.bridge.BaseJavaModule;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.errors.InvalidArgumentException;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.services.UIManager;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0002J \u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\tH\u0002J\u0018\u0010 \u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0018\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020\u0010H\u0016J\b\u0010'\u001a\u00020\u0010H\u0016J\b\u0010(\u001a\u00020\u0010H\u0016J\b\u0010)\u001a\u00020\u0010H\u0016J\u0018\u0010*\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X.¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u0004\n\u0002\u0010\n¨\u0006+"}, mo40253d2 = {"Lexpo/modules/screenorientation/ScreenOrientationModule;", "Lorg/unimodules/core/ExportedModule;", "Lorg/unimodules/core/interfaces/LifecycleEventListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mActivityProvider", "Lorg/unimodules/core/interfaces/ActivityProvider;", "mInitialOrientation", "", "Ljava/lang/Integer;", "exportOrientationLock", "nativeOrientationLock", "getName", "", "getOrientationAsync", "", "promise", "Lorg/unimodules/core/Promise;", "getOrientationLockAsync", "getPlatformOrientationLockAsync", "getScreenOrientation", "Lexpo/modules/screenorientation/Orientation;", "activity", "Landroid/app/Activity;", "importOrientationLock", "orientationLock", "isPortraitNaturalOrientation", "", "rotation", "width", "height", "lockAsync", "lockPlatformAsync", "orientationAttr", "onCreate", "moduleRegistry", "Lorg/unimodules/core/ModuleRegistry;", "onDestroy", "onHostDestroy", "onHostPause", "onHostResume", "supportsOrientationLockAsync", "expo-screen-orientation_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ScreenOrientationModule.kt */
public final class ScreenOrientationModule extends ExportedModule implements LifecycleEventListener {
    private ActivityProvider mActivityProvider;
    private Integer mInitialOrientation;

    private final int exportOrientationLock(int i) {
        if (i == -1) {
            return 0;
        }
        if (i == 0) {
            return 7;
        }
        if (i == 1) {
            return 3;
        }
        switch (i) {
            case 6:
                return 5;
            case 7:
                return 2;
            case 8:
                return 6;
            case 9:
                return 4;
            case 10:
                return 1;
            default:
                return 8;
        }
    }

    private final boolean isPortraitNaturalOrientation(int i, int i2, int i3) {
        if ((i == 0 || i == 2) && i3 > i2) {
            return true;
        }
        return (i == 1 || i == 3) && i2 > i3;
    }

    public String getName() {
        return "ExpoScreenOrientation";
    }

    public void onHostDestroy() {
    }

    public void onHostPause() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ScreenOrientationModule(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        Intrinsics.checkNotNullParameter(moduleRegistry, "moduleRegistry");
        ActivityProvider activityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
        if (activityProvider != null) {
            this.mActivityProvider = activityProvider;
            UIManager uIManager = (UIManager) moduleRegistry.getModule(UIManager.class);
            if (uIManager != null) {
                uIManager.registerLifecycleEventListener(this);
                return;
            }
            throw new IllegalStateException("Could not find implementation for UIManager.");
        }
        throw new IllegalStateException("Could not find implementation for ActivityProvider.");
    }

    public void onHostResume() {
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null && this.mInitialOrientation == null) {
            this.mInitialOrientation = Integer.valueOf(currentActivity.getRequestedOrientation());
        }
    }

    public void onDestroy() {
        Integer num;
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null && (num = this.mInitialOrientation) != null) {
            currentActivity.setRequestedOrientation(num.intValue());
        }
    }

    @ExpoMethod
    public final void lockAsync(int i, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null) {
            try {
                currentActivity.setRequestedOrientation(importOrientationLock(i));
                promise.resolve((Object) null);
                Unit unit = Unit.INSTANCE;
            } catch (InvalidArgumentException e) {
                promise.reject("ERR_SCREEN_ORIENTATION_INVALID_ORIENTATION_LOCK", "An invalid OrientationLock was passed in: " + i, e);
                Unit unit2 = Unit.INSTANCE;
            } catch (Exception e2) {
                promise.reject("ERR_SCREEN_ORIENTATION_UNSUPPORTED_ORIENTATION_LOCK", "Could not apply the ScreenOrientation lock: " + i, e2);
                Unit unit3 = Unit.INSTANCE;
            }
        } else {
            promise.reject("ERR_SCREEN_ORIENTATION_MISSING_ACTIVITY", "Could not find activity.", (Throwable) null);
        }
    }

    @ExpoMethod
    public final void lockPlatformAsync(int i, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null) {
            try {
                currentActivity.setRequestedOrientation(i);
                promise.resolve((Object) null);
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                promise.reject("ERR_SCREEN_ORIENTATION_UNSUPPORTED_ORIENTATION_LOCK", "Could not apply the ScreenOrientation platform lock: " + i, e);
                Unit unit2 = Unit.INSTANCE;
            }
        } else {
            promise.reject("ERR_SCREEN_ORIENTATION_MISSING_ACTIVITY", "Could not find activity.", (Throwable) null);
        }
    }

    @ExpoMethod
    public final void getOrientationAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null) {
            promise.resolve(Integer.valueOf(getScreenOrientation(currentActivity).getValue()));
        } else {
            promise.reject("ERR_SCREEN_ORIENTATION_MISSING_ACTIVITY", "Could not find activity.", (Throwable) null);
        }
    }

    @ExpoMethod
    public final void getOrientationLockAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null) {
            try {
                promise.resolve(Integer.valueOf(exportOrientationLock(currentActivity.getRequestedOrientation())));
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                promise.reject("ERR_SCREEN_ORIENTATION_GET_ORIENTATION_LOCK", "Could not get the current screen orientation lock", e);
                Unit unit2 = Unit.INSTANCE;
            }
        } else {
            promise.reject("ERR_SCREEN_ORIENTATION_MISSING_ACTIVITY", "Could not find activity.", (Throwable) null);
        }
    }

    @ExpoMethod
    public final void getPlatformOrientationLockAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mActivityProvider");
        }
        Activity currentActivity = activityProvider.getCurrentActivity();
        if (currentActivity != null) {
            try {
                promise.resolve(Integer.valueOf(currentActivity.getRequestedOrientation()));
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                promise.reject("ERR_SCREEN_ORIENTATION_GET_PLATFORM_ORIENTATION_LOCK", "Could not get the current screen orientation platform lock", e);
                Unit unit2 = Unit.INSTANCE;
            }
        } else {
            promise.reject("ERR_SCREEN_ORIENTATION_MISSING_ACTIVITY", "Could not find activity.", (Throwable) null);
        }
    }

    @ExpoMethod
    public final void supportsOrientationLockAsync(int i, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        try {
            importOrientationLock(i);
            promise.resolve(true);
        } catch (Exception unused) {
            promise.resolve(false);
        }
    }

    private final Orientation getScreenOrientation(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        if (windowManager == null) {
            return Orientation.UNKNOWN;
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Intrinsics.checkNotNullExpressionValue(defaultDisplay, "windowManager.defaultDisplay");
        int rotation = defaultDisplay.getRotation();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        if (isPortraitNaturalOrientation(rotation, displayMetrics.widthPixels, displayMetrics.heightPixels)) {
            if (rotation == 0) {
                return Orientation.PORTRAIT_UP;
            }
            if (rotation == 1) {
                return Orientation.LANDSCAPE_RIGHT;
            }
            if (rotation == 2) {
                return Orientation.PORTRAIT_DOWN;
            }
            if (rotation != 3) {
                return Orientation.UNKNOWN;
            }
            return Orientation.LANDSCAPE_LEFT;
        } else if (rotation == 0) {
            return Orientation.LANDSCAPE_RIGHT;
        } else {
            if (rotation == 1) {
                return Orientation.PORTRAIT_DOWN;
            }
            if (rotation == 2) {
                return Orientation.LANDSCAPE_LEFT;
            }
            if (rotation != 3) {
                return Orientation.UNKNOWN;
            }
            return Orientation.PORTRAIT_UP;
        }
    }

    private final int importOrientationLock(int i) throws InvalidArgumentException {
        switch (i) {
            case 0:
                return -1;
            case 1:
                return 10;
            case 2:
                return 7;
            case 3:
                return 1;
            case 4:
                return 9;
            case 5:
                return 6;
            case 6:
                return 8;
            case 7:
                return 0;
            default:
                throw new InvalidArgumentException("OrientationLock " + i + " is not mappable to a native Android orientation attr");
        }
    }
}
