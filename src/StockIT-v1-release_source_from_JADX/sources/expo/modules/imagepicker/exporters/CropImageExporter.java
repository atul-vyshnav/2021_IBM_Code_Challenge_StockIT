package expo.modules.imagepicker.exporters;

import android.graphics.Rect;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo40253d2 = {"Lexpo/modules/imagepicker/exporters/CropImageExporter;", "Lexpo/modules/imagepicker/exporters/ImageExporter;", "mRotation", "", "mCropRect", "Landroid/graphics/Rect;", "mBase64", "", "(ILandroid/graphics/Rect;Z)V", "export", "", "source", "Landroid/net/Uri;", "output", "Ljava/io/File;", "exporterListener", "Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: CropImageExporter.kt */
public final class CropImageExporter implements ImageExporter {
    private final boolean mBase64;
    private final Rect mCropRect;
    private final int mRotation;

    public CropImageExporter(int i, Rect rect, boolean z) {
        Intrinsics.checkNotNullParameter(rect, "mCropRect");
        this.mRotation = i;
        this.mCropRect = rect;
        this.mBase64 = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006f, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r5, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0073, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0089, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008a, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r1, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008d, code lost:
        throw r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void export(android.net.Uri r8, java.io.File r9, expo.modules.imagepicker.exporters.ImageExporter.Listener r10) {
        /*
            r7 = this;
            java.lang.String r0 = "source"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "output"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r9 = "exporterListener"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r9)
            int r9 = r7.mRotation
            int r9 = r9 % 360
            if (r9 >= 0) goto L_0x0017
            int r9 = r9 + 360
        L_0x0017:
            if (r9 == 0) goto L_0x002b
            r0 = 180(0xb4, float:2.52E-43)
            if (r9 != r0) goto L_0x001e
            goto L_0x002b
        L_0x001e:
            android.graphics.Rect r9 = r7.mCropRect
            int r9 = r9.height()
            android.graphics.Rect r0 = r7.mCropRect
            int r0 = r0.width()
            goto L_0x0037
        L_0x002b:
            android.graphics.Rect r9 = r7.mCropRect
            int r9 = r9.width()
            android.graphics.Rect r0 = r7.mCropRect
            int r0 = r0.height()
        L_0x0037:
            boolean r1 = r7.mBase64
            r2 = 0
            if (r1 == 0) goto L_0x008e
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            java.io.Closeable r1 = (java.io.Closeable) r1
            r3 = r2
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r4 = r1
            java.io.ByteArrayOutputStream r4 = (java.io.ByteArrayOutputStream) r4     // Catch:{ all -> 0x0087 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            java.lang.String r8 = r8.getPath()     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            r5.<init>(r8)     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            java.io.Closeable r5 = (java.io.Closeable) r5     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            java.lang.Throwable r2 = (java.lang.Throwable) r2     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            r8 = r5
            java.io.FileInputStream r8 = (java.io.FileInputStream) r8     // Catch:{ all -> 0x006d }
            java.io.InputStream r8 = (java.io.InputStream) r8     // Catch:{ all -> 0x006d }
            r6 = r4
            java.io.OutputStream r6 = (java.io.OutputStream) r6     // Catch:{ all -> 0x006d }
            org.apache.commons.p029io.IOUtils.copy((java.io.InputStream) r8, (java.io.OutputStream) r6)     // Catch:{ all -> 0x006d }
            r10.onResult(r4, r9, r0)     // Catch:{ all -> 0x006d }
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x006d }
            kotlin.p024io.CloseableKt.closeFinally(r5, r2)     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            goto L_0x0081
        L_0x006d:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x006f }
        L_0x006f:
            r9 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r5, r8)     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
            throw r9     // Catch:{ NullPointerException -> 0x007b, IOException -> 0x0074 }
        L_0x0074:
            r8 = move-exception
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch:{ all -> 0x0087 }
            r10.onFailure(r8)     // Catch:{ all -> 0x0087 }
            goto L_0x0081
        L_0x007b:
            r8 = move-exception
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch:{ all -> 0x0087 }
            r10.onFailure(r8)     // Catch:{ all -> 0x0087 }
        L_0x0081:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0087 }
            kotlin.p024io.CloseableKt.closeFinally(r1, r3)
            return
        L_0x0087:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x0089 }
        L_0x0089:
            r9 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r1, r8)
            throw r9
        L_0x008e:
            r10.onResult(r2, r9, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.exporters.CropImageExporter.export(android.net.Uri, java.io.File, expo.modules.imagepicker.exporters.ImageExporter$Listener):void");
    }
}
