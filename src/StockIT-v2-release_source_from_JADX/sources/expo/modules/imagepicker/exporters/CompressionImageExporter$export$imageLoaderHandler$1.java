package expo.modules.imagepicker.exporters;

import expo.modules.imagepicker.exporters.ImageExporter;
import java.io.File;
import kotlin.Metadata;
import org.unimodules.interfaces.imageloader.ImageLoader;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, mo40253d2 = {"expo/modules/imagepicker/exporters/CompressionImageExporter$export$imageLoaderHandler$1", "Lorg/unimodules/interfaces/imageloader/ImageLoader$ResultListener;", "onFailure", "", "cause", "", "onSuccess", "bitmap", "Landroid/graphics/Bitmap;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: CompressionImageExporter.kt */
public final class CompressionImageExporter$export$imageLoaderHandler$1 implements ImageLoader.ResultListener {
    final /* synthetic */ ImageExporter.Listener $exporterListener;
    final /* synthetic */ File $output;
    final /* synthetic */ CompressionImageExporter this$0;

    CompressionImageExporter$export$imageLoaderHandler$1(CompressionImageExporter compressionImageExporter, File file, ImageExporter.Listener listener) {
        this.this$0 = compressionImageExporter;
        this.$output = file;
        this.$exporterListener = listener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0064, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0065, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r2, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0068, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onSuccess(android.graphics.Bitmap r11) {
        /*
            r10 = this;
            java.lang.String r0 = "bitmap"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            int r0 = r11.getWidth()
            int r1 = r11.getHeight()
            expo.modules.imagepicker.exporters.CompressionImageExporter r2 = r10.this$0
            boolean r2 = r2.mBase64
            r3 = 0
            if (r2 == 0) goto L_0x001c
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            goto L_0x001d
        L_0x001c:
            r2 = r3
        L_0x001d:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r4 = r3
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r5 = r2
            java.io.ByteArrayOutputStream r5 = (java.io.ByteArrayOutputStream) r5     // Catch:{ all -> 0x0062 }
            java.io.File r6 = r10.$output     // Catch:{ IOException -> 0x0054 }
            java.lang.String r6 = r6.getPath()     // Catch:{ IOException -> 0x0054 }
            java.lang.String r6 = org.apache.commons.p029io.FilenameUtils.getExtension(r6)     // Catch:{ IOException -> 0x0054 }
            java.lang.String r7 = "FilenameUtils.getExtension(output.path)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r7)     // Catch:{ IOException -> 0x0054 }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ IOException -> 0x0054 }
            java.lang.String r7 = "png"
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch:{ IOException -> 0x0054 }
            r8 = 0
            r9 = 2
            boolean r3 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6, (java.lang.CharSequence) r7, (boolean) r8, (int) r9, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0054 }
            if (r3 == 0) goto L_0x0045
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ IOException -> 0x0054 }
            goto L_0x0047
        L_0x0045:
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x0054 }
        L_0x0047:
            expo.modules.imagepicker.exporters.CompressionImageExporter r6 = r10.this$0     // Catch:{ IOException -> 0x0054 }
            java.io.File r7 = r10.$output     // Catch:{ IOException -> 0x0054 }
            r6.saveBitmap(r11, r3, r7, r5)     // Catch:{ IOException -> 0x0054 }
            expo.modules.imagepicker.exporters.ImageExporter$Listener r11 = r10.$exporterListener     // Catch:{ IOException -> 0x0054 }
            r11.onResult(r5, r0, r1)     // Catch:{ IOException -> 0x0054 }
            goto L_0x005c
        L_0x0054:
            r11 = move-exception
            expo.modules.imagepicker.exporters.ImageExporter$Listener r0 = r10.$exporterListener     // Catch:{ all -> 0x0062 }
            java.lang.Throwable r11 = (java.lang.Throwable) r11     // Catch:{ all -> 0x0062 }
            r0.onFailure(r11)     // Catch:{ all -> 0x0062 }
        L_0x005c:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0062 }
            kotlin.p024io.CloseableKt.closeFinally(r2, r4)
            return
        L_0x0062:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r0 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r2, r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.exporters.CompressionImageExporter$export$imageLoaderHandler$1.onSuccess(android.graphics.Bitmap):void");
    }

    public void onFailure(Throwable th) {
        this.$exporterListener.onFailure(th);
    }
}
