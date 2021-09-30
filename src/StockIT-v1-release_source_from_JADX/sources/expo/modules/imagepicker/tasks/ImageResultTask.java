package expo.modules.imagepicker.tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import com.facebook.react.bridge.BaseJavaModule;
import expo.modules.imagepicker.ImagePickerConstants;
import expo.modules.imagepicker.exporters.ImageExporter;
import expo.modules.imagepicker.fileproviders.FileProvider;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ'\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0012\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, mo40253d2 = {"Lexpo/modules/imagepicker/tasks/ImageResultTask;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "promise", "Lorg/unimodules/core/Promise;", "uri", "Landroid/net/Uri;", "contentResolver", "Landroid/content/ContentResolver;", "fileProvider", "Lexpo/modules/imagepicker/fileproviders/FileProvider;", "withExifData", "", "imageExporter", "Lexpo/modules/imagepicker/exporters/ImageExporter;", "(Lorg/unimodules/core/Promise;Landroid/net/Uri;Landroid/content/ContentResolver;Lexpo/modules/imagepicker/fileproviders/FileProvider;ZLexpo/modules/imagepicker/exporters/ImageExporter;)V", "doInBackground", "params", "", "([Ljava/lang/Void;)Ljava/lang/Void;", "readExif", "Landroid/os/Bundle;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImageResultTask.kt */
public class ImageResultTask extends AsyncTask<Void, Void, Void> {
    private final ContentResolver contentResolver;
    private final FileProvider fileProvider;
    private final ImageExporter imageExporter;
    /* access modifiers changed from: private */
    public final Promise promise;
    private final Uri uri;
    private final boolean withExifData;

    public ImageResultTask(Promise promise2, Uri uri2, ContentResolver contentResolver2, FileProvider fileProvider2, boolean z, ImageExporter imageExporter2) {
        Intrinsics.checkNotNullParameter(promise2, BaseJavaModule.METHOD_TYPE_PROMISE);
        Intrinsics.checkNotNullParameter(uri2, "uri");
        Intrinsics.checkNotNullParameter(contentResolver2, "contentResolver");
        Intrinsics.checkNotNullParameter(fileProvider2, "fileProvider");
        Intrinsics.checkNotNullParameter(imageExporter2, "imageExporter");
        this.promise = promise2;
        this.uri = uri2;
        this.contentResolver = contentResolver2;
        this.fileProvider = fileProvider2;
        this.withExifData = z;
        this.imageExporter = imageExporter2;
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        Intrinsics.checkNotNullParameter(voidArr, "params");
        try {
            File generateFile = this.fileProvider.generateFile();
            this.imageExporter.export(this.uri, generateFile, new ImageResultTask$doInBackground$imageExporterHandler$1(this, generateFile, this.withExifData ? readExif() : null));
        } catch (IOException e) {
            this.promise.reject(ImagePickerConstants.ERR_CAN_NOT_EXTRACT_METADATA, ImagePickerConstants.CAN_NOT_EXTRACT_METADATA_MESSAGE, e);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a5, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a6, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a9, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.os.Bundle readExif() throws java.io.IOException {
        /*
            r14 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            android.content.ContentResolver r1 = r14.contentResolver
            android.net.Uri r2 = r14.uri
            java.io.InputStream r1 = r1.openInputStream(r2)
            if (r1 == 0) goto L_0x00aa
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = r1
            java.io.InputStream r3 = (java.io.InputStream) r3     // Catch:{ all -> 0x00a3 }
            androidx.exifinterface.media.ExifInterface r4 = new androidx.exifinterface.media.ExifInterface     // Catch:{ all -> 0x00a3 }
            r4.<init>((java.io.InputStream) r3)     // Catch:{ all -> 0x00a3 }
            expo.modules.imagepicker.ImagePickerConstants r3 = expo.modules.imagepicker.ImagePickerConstants.INSTANCE     // Catch:{ all -> 0x00a3 }
            java.lang.String[][] r3 = r3.getExifTags()     // Catch:{ all -> 0x00a3 }
            java.lang.Object[] r3 = (java.lang.Object[]) r3     // Catch:{ all -> 0x00a3 }
            int r5 = r3.length     // Catch:{ all -> 0x00a3 }
            r6 = 0
            r7 = 0
        L_0x0027:
            r8 = 0
            r10 = 1
            if (r7 >= r5) goto L_0x0080
            r11 = r3[r7]     // Catch:{ all -> 0x00a3 }
            java.lang.String[] r11 = (java.lang.String[]) r11     // Catch:{ all -> 0x00a3 }
            r12 = r11[r6]     // Catch:{ all -> 0x00a3 }
            r10 = r11[r10]     // Catch:{ all -> 0x00a3 }
            java.lang.String r11 = r4.getAttribute(r10)     // Catch:{ all -> 0x00a3 }
            if (r11 == 0) goto L_0x007d
            int r11 = r12.hashCode()     // Catch:{ all -> 0x00a3 }
            r13 = -1325958191(0xffffffffb0f77bd1, float:-1.8006806E-9)
            if (r11 == r13) goto L_0x006e
            r8 = -891985903(0xffffffffcad56011, float:-6991880.5)
            if (r11 == r8) goto L_0x005e
            r8 = 104431(0x197ef, float:1.46339E-40)
            if (r11 == r8) goto L_0x004e
            goto L_0x007d
        L_0x004e:
            java.lang.String r8 = "int"
            boolean r8 = r12.equals(r8)     // Catch:{ all -> 0x00a3 }
            if (r8 == 0) goto L_0x007d
            int r8 = r4.getAttributeInt(r10, r6)     // Catch:{ all -> 0x00a3 }
            r0.putInt(r10, r8)     // Catch:{ all -> 0x00a3 }
            goto L_0x007d
        L_0x005e:
            java.lang.String r8 = "string"
            boolean r8 = r12.equals(r8)     // Catch:{ all -> 0x00a3 }
            if (r8 == 0) goto L_0x007d
            java.lang.String r8 = r4.getAttribute(r10)     // Catch:{ all -> 0x00a3 }
            r0.putString(r10, r8)     // Catch:{ all -> 0x00a3 }
            goto L_0x007d
        L_0x006e:
            java.lang.String r11 = "double"
            boolean r11 = r12.equals(r11)     // Catch:{ all -> 0x00a3 }
            if (r11 == 0) goto L_0x007d
            double r8 = r4.getAttributeDouble(r10, r8)     // Catch:{ all -> 0x00a3 }
            r0.putDouble(r10, r8)     // Catch:{ all -> 0x00a3 }
        L_0x007d:
            int r7 = r7 + 1
            goto L_0x0027
        L_0x0080:
            double[] r3 = r4.getLatLong()     // Catch:{ all -> 0x00a3 }
            if (r3 == 0) goto L_0x009d
            java.lang.String r5 = "GPSLatitude"
            r6 = r3[r6]     // Catch:{ all -> 0x00a3 }
            r0.putDouble(r5, r6)     // Catch:{ all -> 0x00a3 }
            java.lang.String r5 = "GPSLongitude"
            r6 = r3[r10]     // Catch:{ all -> 0x00a3 }
            r0.putDouble(r5, r6)     // Catch:{ all -> 0x00a3 }
            java.lang.String r3 = "GPSAltitude"
            double r4 = r4.getAltitude(r8)     // Catch:{ all -> 0x00a3 }
            r0.putDouble(r3, r4)     // Catch:{ all -> 0x00a3 }
        L_0x009d:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00a3 }
            kotlin.p024io.CloseableKt.closeFinally(r1, r2)
            goto L_0x00aa
        L_0x00a3:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x00a5 }
        L_0x00a5:
            r2 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r1, r0)
            throw r2
        L_0x00aa:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.tasks.ImageResultTask.readExif():android.os.Bundle");
    }
}
