package expo.modules.splashscreen;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, mo40253d2 = {"Lexpo/modules/splashscreen/NativeResourcesBasedSplashScreenViewProvider;", "Lexpo/modules/splashscreen/SplashScreenViewProvider;", "resizeMode", "Lexpo/modules/splashscreen/SplashScreenImageResizeMode;", "(Lexpo/modules/splashscreen/SplashScreenImageResizeMode;)V", "createSplashScreenView", "Lexpo/modules/splashscreen/SplashScreenView;", "context", "Landroid/content/Context;", "getBackgroundColor", "", "getImageResource", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: NativeResourcesBasedSplashScreenViewProvider.kt */
public final class NativeResourcesBasedSplashScreenViewProvider implements SplashScreenViewProvider {
    private final SplashScreenImageResizeMode resizeMode;

    public NativeResourcesBasedSplashScreenViewProvider(SplashScreenImageResizeMode splashScreenImageResizeMode) {
        Intrinsics.checkNotNullParameter(splashScreenImageResizeMode, ViewProps.RESIZE_MODE);
        this.resizeMode = splashScreenImageResizeMode;
    }

    public SplashScreenView createSplashScreenView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SplashScreenView splashScreenView = new SplashScreenView(context);
        splashScreenView.setBackgroundColor(getBackgroundColor(context));
        splashScreenView.getImageView().setImageResource(getImageResource());
        splashScreenView.configureImageViewResizeMode(this.resizeMode);
        return splashScreenView;
    }

    private final int getBackgroundColor(Context context) {
        return ContextCompat.getColor(context, C3047R.C3048color.splashscreen_background);
    }

    private final int getImageResource() {
        if (this.resizeMode == SplashScreenImageResizeMode.NATIVE) {
            return C3047R.C3049drawable.splashscreen;
        }
        return C3047R.C3049drawable.splashscreen_image;
    }
}
