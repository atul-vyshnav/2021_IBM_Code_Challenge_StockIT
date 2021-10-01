package expo.modules.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.imageloader.ImageLoader;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00022\u0010\u0010\t\u001a\f\u0012\u0006\b\u0000\u0012\u00020\u0002\u0018\u00010\nH\u0016¨\u0006\u000b"}, mo40253d2 = {"expo/modules/imageloader/ImageLoaderModule$loadImageForManipulationFromURL$2", "Lcom/bumptech/glide/request/target/SimpleTarget;", "Landroid/graphics/Bitmap;", "onLoadFailed", "", "errorDrawable", "Landroid/graphics/drawable/Drawable;", "onResourceReady", "resource", "transition", "Lcom/bumptech/glide/request/transition/Transition;", "expo-image-loader_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImageLoaderModule.kt */
public final class ImageLoaderModule$loadImageForManipulationFromURL$2 extends SimpleTarget<Bitmap> {
    final /* synthetic */ ImageLoader.ResultListener $resultListener;

    ImageLoaderModule$loadImageForManipulationFromURL$2(ImageLoader.ResultListener resultListener) {
        this.$resultListener = resultListener;
    }

    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        Intrinsics.checkNotNullParameter(bitmap, "resource");
        this.$resultListener.onSuccess(bitmap);
    }

    public void onLoadFailed(Drawable drawable) {
        this.$resultListener.onFailure(new Exception("Loading bitmap failed"));
    }
}
