package expo.modules.splashscreen;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, mo40253d2 = {"Lexpo/modules/splashscreen/SplashScreenView;", "Landroid/widget/RelativeLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "imageView", "Landroid/widget/ImageView;", "getImageView", "()Landroid/widget/ImageView;", "configureImageViewResizeMode", "", "resizeMode", "Lexpo/modules/splashscreen/SplashScreenImageResizeMode;", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenView.kt */
public final class SplashScreenView extends RelativeLayout {
    private final ImageView imageView;

    @Metadata(mo40251bv = {1, 0, 3}, mo40254k = 3, mo40255mv = {1, 4, 2})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SplashScreenImageResizeMode.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[SplashScreenImageResizeMode.NATIVE.ordinal()] = 1;
            $EnumSwitchMapping$0[SplashScreenImageResizeMode.CONTAIN.ordinal()] = 2;
            $EnumSwitchMapping$0[SplashScreenImageResizeMode.COVER.ordinal()] = 3;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SplashScreenView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        ImageView imageView2 = new ImageView(context);
        imageView2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        Unit unit = Unit.INSTANCE;
        this.imageView = imageView2;
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        setClickable(true);
        addView(this.imageView);
    }

    public final ImageView getImageView() {
        return this.imageView;
    }

    public final void configureImageViewResizeMode(SplashScreenImageResizeMode splashScreenImageResizeMode) {
        Intrinsics.checkNotNullParameter(splashScreenImageResizeMode, ViewProps.RESIZE_MODE);
        this.imageView.setScaleType(splashScreenImageResizeMode.getScaleType());
        if (WhenMappings.$EnumSwitchMapping$0[splashScreenImageResizeMode.ordinal()] == 2) {
            this.imageView.setAdjustViewBounds(true);
        }
    }
}
