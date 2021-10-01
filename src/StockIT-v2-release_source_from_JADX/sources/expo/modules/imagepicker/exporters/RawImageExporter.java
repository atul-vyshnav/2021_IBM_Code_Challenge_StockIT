package expo.modules.imagepicker.exporters;

import android.content.ContentResolver;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J \u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, mo40253d2 = {"Lexpo/modules/imagepicker/exporters/RawImageExporter;", "Lexpo/modules/imagepicker/exporters/ImageExporter;", "contentResolver", "Landroid/content/ContentResolver;", "mBase64", "", "(Landroid/content/ContentResolver;Z)V", "copyImage", "", "originalUri", "Landroid/net/Uri;", "file", "Ljava/io/File;", "out", "Ljava/io/ByteArrayOutputStream;", "export", "source", "output", "exporterListener", "Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: RawImageExporter.kt */
public final class RawImageExporter implements ImageExporter {
    private final ContentResolver contentResolver;
    private final boolean mBase64;

    public RawImageExporter(ContentResolver contentResolver2, boolean z) {
        Intrinsics.checkNotNullParameter(contentResolver2, "contentResolver");
        this.contentResolver = contentResolver2;
        this.mBase64 = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004c, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r2, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void export(android.net.Uri r5, java.io.File r6, expo.modules.imagepicker.exporters.ImageExporter.Listener r7) {
        /*
            r4 = this;
            java.lang.String r0 = "source"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "output"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "exporterListener"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            boolean r0 = r4.mBase64
            r1 = 0
            if (r0 == 0) goto L_0x001a
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            goto L_0x001b
        L_0x001a:
            r0 = r1
        L_0x001b:
            r2 = r0
            java.io.Closeable r2 = (java.io.Closeable) r2
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r3 = r2
            java.io.ByteArrayOutputStream r3 = (java.io.ByteArrayOutputStream) r3     // Catch:{ all -> 0x0049 }
            r4.copyImage(r5, r6, r0)     // Catch:{ IOException -> 0x003d }
            android.graphics.BitmapFactory$Options r5 = new android.graphics.BitmapFactory$Options     // Catch:{ IOException -> 0x003d }
            r5.<init>()     // Catch:{ IOException -> 0x003d }
            r3 = 1
            r5.inJustDecodeBounds = r3     // Catch:{ IOException -> 0x003d }
            java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ IOException -> 0x003d }
            android.graphics.BitmapFactory.decodeFile(r6, r5)     // Catch:{ IOException -> 0x003d }
            int r6 = r5.outWidth     // Catch:{ IOException -> 0x003d }
            int r5 = r5.outHeight     // Catch:{ IOException -> 0x003d }
            r7.onResult(r0, r6, r5)     // Catch:{ IOException -> 0x003d }
            goto L_0x0043
        L_0x003d:
            r5 = move-exception
            java.lang.Throwable r5 = (java.lang.Throwable) r5     // Catch:{ all -> 0x0049 }
            r7.onFailure(r5)     // Catch:{ all -> 0x0049 }
        L_0x0043:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0049 }
            kotlin.p024io.CloseableKt.closeFinally(r2, r1)
            return
        L_0x0049:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x004b }
        L_0x004b:
            r6 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r2, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.exporters.RawImageExporter.export(android.net.Uri, java.io.File, expo.modules.imagepicker.exporters.ImageExporter$Listener):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004a, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0057, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0058, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r0, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005b, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void copyImage(android.net.Uri r6, java.io.File r7, java.io.ByteArrayOutputStream r8) throws java.io.IOException {
        /*
            r5 = this;
            android.content.ContentResolver r0 = r5.contentResolver
            java.io.InputStream r0 = r0.openInputStream(r6)
            if (r0 == 0) goto L_0x005c
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            r2 = r1
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = r0
            java.io.InputStream r3 = (java.io.InputStream) r3     // Catch:{ all -> 0x0055 }
            if (r8 == 0) goto L_0x0019
            r4 = r8
            java.io.OutputStream r4 = (java.io.OutputStream) r4     // Catch:{ all -> 0x0055 }
            org.apache.commons.p029io.IOUtils.copy((java.io.InputStream) r3, (java.io.OutputStream) r4)     // Catch:{ all -> 0x0055 }
        L_0x0019:
            android.net.Uri r4 = android.net.Uri.fromFile(r7)     // Catch:{ all -> 0x0055 }
            int r6 = r6.compareTo(r4)     // Catch:{ all -> 0x0055 }
            if (r6 == 0) goto L_0x004f
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ all -> 0x0055 }
            r6.<init>(r7)     // Catch:{ all -> 0x0055 }
            java.io.Closeable r6 = (java.io.Closeable) r6     // Catch:{ all -> 0x0055 }
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x0055 }
            r7 = r6
            java.io.FileOutputStream r7 = (java.io.FileOutputStream) r7     // Catch:{ all -> 0x0048 }
            if (r8 == 0) goto L_0x003b
            byte[] r8 = r8.toByteArray()     // Catch:{ all -> 0x0048 }
            r7.write(r8)     // Catch:{ all -> 0x0048 }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0048 }
            goto L_0x0044
        L_0x003b:
            java.io.OutputStream r7 = (java.io.OutputStream) r7     // Catch:{ all -> 0x0048 }
            int r7 = org.apache.commons.p029io.IOUtils.copy((java.io.InputStream) r3, (java.io.OutputStream) r7)     // Catch:{ all -> 0x0048 }
            java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0048 }
        L_0x0044:
            kotlin.p024io.CloseableKt.closeFinally(r6, r1)     // Catch:{ all -> 0x0055 }
            goto L_0x004f
        L_0x0048:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x004a }
        L_0x004a:
            r8 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r6, r7)     // Catch:{ all -> 0x0055 }
            throw r8     // Catch:{ all -> 0x0055 }
        L_0x004f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0055 }
            kotlin.p024io.CloseableKt.closeFinally(r0, r2)
            goto L_0x005c
        L_0x0055:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r7 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r0, r6)
            throw r7
        L_0x005c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.exporters.RawImageExporter.copyImage(android.net.Uri, java.io.File, java.io.ByteArrayOutputStream):void");
    }
}
