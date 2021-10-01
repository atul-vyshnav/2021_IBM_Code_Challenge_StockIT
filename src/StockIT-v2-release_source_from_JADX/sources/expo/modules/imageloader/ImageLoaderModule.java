package expo.modules.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.List;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.interfaces.imageloader.ImageLoader;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010\b\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0018\u00010\tH\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u000b\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0001\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, mo40253d2 = {"Lexpo/modules/imageloader/ImageLoaderModule;", "Lorg/unimodules/core/interfaces/InternalModule;", "Lorg/unimodules/interfaces/imageloader/ImageLoader;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "getExportedInterfaces", "", "Ljava/lang/Class;", "loadImageForDisplayFromURL", "Ljava/util/concurrent/Future;", "Landroid/graphics/Bitmap;", "url", "", "", "resultListener", "Lorg/unimodules/interfaces/imageloader/ImageLoader$ResultListener;", "loadImageForManipulationFromURL", "normalizeAssetsUrl", "expo-image-loader_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImageLoaderModule.kt */
public final class ImageLoaderModule implements InternalModule, ImageLoader {
    private final Context context;

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public ImageLoaderModule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public final Context getContext() {
        return this.context;
    }

    public List<Class<?>> getExportedInterfaces() {
        return CollectionsKt.listOf(ImageLoader.class);
    }

    public Future<Bitmap> loadImageForDisplayFromURL(String str) {
        Intrinsics.checkNotNullParameter(str, ImagesContract.URL);
        SimpleSettableFuture simpleSettableFuture = new SimpleSettableFuture();
        loadImageForDisplayFromURL(str, new ImageLoaderModule$loadImageForDisplayFromURL$1(simpleSettableFuture));
        return simpleSettableFuture;
    }

    public void loadImageForDisplayFromURL(String str, ImageLoader.ResultListener resultListener) {
        Intrinsics.checkNotNullParameter(str, ImagesContract.URL);
        Intrinsics.checkNotNullParameter(resultListener, "resultListener");
        Fresco.getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(str), this.context).subscribe(new ImageLoaderModule$loadImageForDisplayFromURL$2(resultListener), AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public Future<Bitmap> loadImageForManipulationFromURL(String str) {
        Intrinsics.checkNotNullParameter(str, ImagesContract.URL);
        SimpleSettableFuture simpleSettableFuture = new SimpleSettableFuture();
        loadImageForManipulationFromURL(str, new ImageLoaderModule$loadImageForManipulationFromURL$1(simpleSettableFuture));
        return simpleSettableFuture;
    }

    public void loadImageForManipulationFromURL(String str, ImageLoader.ResultListener resultListener) {
        Intrinsics.checkNotNullParameter(str, ImagesContract.URL);
        Intrinsics.checkNotNullParameter(resultListener, "resultListener");
        ((RequestBuilder) ((RequestBuilder) Glide.with(this.context).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)).skipMemoryCache(true)).load(normalizeAssetsUrl(str)).into(new ImageLoaderModule$loadImageForManipulationFromURL$2(resultListener));
    }

    private final String normalizeAssetsUrl(String str) {
        if (!StringsKt.startsWith$default(str, "asset:///", false, 2, (Object) null)) {
            return str;
        }
        return "file:///android_asset/" + ((String) CollectionsKt.last(StringsKt.split$default((CharSequence) str, new String[]{"/"}, false, 0, 6, (Object) null)));
    }
}
