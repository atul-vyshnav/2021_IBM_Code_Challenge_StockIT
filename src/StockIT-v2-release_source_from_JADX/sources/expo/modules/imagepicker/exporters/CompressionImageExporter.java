package expo.modules.imagepicker.exporters;

import android.graphics.Bitmap;
import android.net.Uri;
import expo.modules.imagepicker.exporters.ImageExporter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.imageloader.ImageLoader;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J*\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002J \u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, mo40253d2 = {"Lexpo/modules/imagepicker/exporters/CompressionImageExporter;", "Lexpo/modules/imagepicker/exporters/ImageExporter;", "mImageLoader", "Lorg/unimodules/interfaces/imageloader/ImageLoader;", "mQuality", "", "mBase64", "", "(Lorg/unimodules/interfaces/imageloader/ImageLoader;IZ)V", "export", "", "source", "Landroid/net/Uri;", "output", "Ljava/io/File;", "exporterListener", "Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "saveBitmap", "bitmap", "Landroid/graphics/Bitmap;", "compressFormat", "Landroid/graphics/Bitmap$CompressFormat;", "out", "Ljava/io/ByteArrayOutputStream;", "writeImage", "image", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: CompressionImageExporter.kt */
public final class CompressionImageExporter implements ImageExporter {
    /* access modifiers changed from: private */
    public final boolean mBase64;
    private final ImageLoader mImageLoader;
    private final int mQuality;

    public CompressionImageExporter(ImageLoader imageLoader, int i, boolean z) {
        Intrinsics.checkNotNullParameter(imageLoader, "mImageLoader");
        this.mImageLoader = imageLoader;
        this.mQuality = i;
        this.mBase64 = z;
    }

    public void export(Uri uri, File file, ImageExporter.Listener listener) {
        Intrinsics.checkNotNullParameter(uri, "source");
        Intrinsics.checkNotNullParameter(file, "output");
        Intrinsics.checkNotNullParameter(listener, "exporterListener");
        this.mImageLoader.loadImageForManipulationFromURL(uri.toString(), new CompressionImageExporter$export$imageLoaderHandler$1(this, file, listener));
    }

    /* access modifiers changed from: private */
    public final void saveBitmap(Bitmap bitmap, Bitmap.CompressFormat compressFormat, File file, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        writeImage(bitmap, file, compressFormat);
        if (this.mBase64) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, this.mQuality, byteArrayOutputStream);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r0, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void writeImage(android.graphics.Bitmap r4, java.io.File r5, android.graphics.Bitmap.CompressFormat r6) throws java.io.IOException {
        /*
            r3 = this;
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r5)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r5 = 0
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r1 = r0
            java.io.FileOutputStream r1 = (java.io.FileOutputStream) r1     // Catch:{ all -> 0x0018 }
            int r2 = r3.mQuality     // Catch:{ all -> 0x0018 }
            java.io.OutputStream r1 = (java.io.OutputStream) r1     // Catch:{ all -> 0x0018 }
            r4.compress(r6, r2, r1)     // Catch:{ all -> 0x0018 }
            kotlin.p024io.CloseableKt.closeFinally(r0, r5)
            return
        L_0x0018:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x001a }
        L_0x001a:
            r5 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r0, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.exporters.CompressionImageExporter.writeImage(android.graphics.Bitmap, java.io.File, android.graphics.Bitmap$CompressFormat):void");
    }
}
