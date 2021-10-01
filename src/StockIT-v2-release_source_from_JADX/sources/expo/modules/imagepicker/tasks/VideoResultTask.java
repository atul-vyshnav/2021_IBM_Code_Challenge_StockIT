package expo.modules.imagepicker.tasks;

import android.content.ContentResolver;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.exoplayer2.util.MimeTypes;
import expo.modules.imagepicker.ImagePickerConstants;
import expo.modules.imagepicker.fileproviders.FileProvider;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ'\u0010\u000e\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u000f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0010\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, mo40253d2 = {"Lexpo/modules/imagepicker/tasks/VideoResultTask;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "promise", "Lorg/unimodules/core/Promise;", "uri", "Landroid/net/Uri;", "contentResolver", "Landroid/content/ContentResolver;", "fileProvider", "Lexpo/modules/imagepicker/fileproviders/FileProvider;", "mediaMetadataRetriever", "Landroid/media/MediaMetadataRetriever;", "(Lorg/unimodules/core/Promise;Landroid/net/Uri;Landroid/content/ContentResolver;Lexpo/modules/imagepicker/fileproviders/FileProvider;Landroid/media/MediaMetadataRetriever;)V", "doInBackground", "params", "", "([Ljava/lang/Void;)Ljava/lang/Void;", "saveVideo", "", "outputFile", "Ljava/io/File;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: VideoResultTask.kt */
public final class VideoResultTask extends AsyncTask<Void, Void, Void> {
    private final ContentResolver contentResolver;
    private final FileProvider fileProvider;
    private final MediaMetadataRetriever mediaMetadataRetriever;
    private final Promise promise;
    private final Uri uri;

    public VideoResultTask(Promise promise2, Uri uri2, ContentResolver contentResolver2, FileProvider fileProvider2, MediaMetadataRetriever mediaMetadataRetriever2) {
        Intrinsics.checkNotNullParameter(promise2, BaseJavaModule.METHOD_TYPE_PROMISE);
        Intrinsics.checkNotNullParameter(uri2, "uri");
        Intrinsics.checkNotNullParameter(contentResolver2, "contentResolver");
        Intrinsics.checkNotNullParameter(fileProvider2, "fileProvider");
        Intrinsics.checkNotNullParameter(mediaMetadataRetriever2, "mediaMetadataRetriever");
        this.promise = promise2;
        this.uri = uri2;
        this.contentResolver = contentResolver2;
        this.fileProvider = fileProvider2;
        this.mediaMetadataRetriever = mediaMetadataRetriever2;
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        Intrinsics.checkNotNullParameter(voidArr, "params");
        try {
            File generateFile = this.fileProvider.generateFile();
            saveVideo(generateFile);
            Bundle bundle = new Bundle();
            bundle.putString("uri", Uri.fromFile(generateFile).toString());
            bundle.putBoolean("cancelled", false);
            bundle.putString("type", MimeTypes.BASE_TYPE_VIDEO);
            String extractMetadata = this.mediaMetadataRetriever.extractMetadata(18);
            Intrinsics.checkNotNull(extractMetadata);
            Intrinsics.checkNotNullExpressionValue(extractMetadata, "mediaMetadataRetriever.e…TADATA_KEY_VIDEO_WIDTH)!!");
            bundle.putInt("width", Integer.parseInt(extractMetadata));
            String extractMetadata2 = this.mediaMetadataRetriever.extractMetadata(19);
            Intrinsics.checkNotNull(extractMetadata2);
            Intrinsics.checkNotNullExpressionValue(extractMetadata2, "mediaMetadataRetriever.e…ADATA_KEY_VIDEO_HEIGHT)!!");
            bundle.putInt("height", Integer.parseInt(extractMetadata2));
            String extractMetadata3 = this.mediaMetadataRetriever.extractMetadata(24);
            Intrinsics.checkNotNull(extractMetadata3);
            Intrinsics.checkNotNullExpressionValue(extractMetadata3, "mediaMetadataRetriever.e…ATA_KEY_VIDEO_ROTATION)!!");
            bundle.putInt(ViewProps.ROTATION, Integer.parseInt(extractMetadata3));
            String extractMetadata4 = this.mediaMetadataRetriever.extractMetadata(9);
            Intrinsics.checkNotNull(extractMetadata4);
            Intrinsics.checkNotNullExpressionValue(extractMetadata4, "mediaMetadataRetriever.e….METADATA_KEY_DURATION)!!");
            bundle.putInt("duration", Integer.parseInt(extractMetadata4));
            this.promise.resolve(bundle);
            return null;
        } catch (NullPointerException e) {
            this.promise.reject(ImagePickerConstants.ERR_CAN_NOT_EXTRACT_METADATA, ImagePickerConstants.CAN_NOT_EXTRACT_METADATA_MESSAGE, e);
            return null;
        } catch (IllegalArgumentException e2) {
            this.promise.reject(ImagePickerConstants.ERR_CAN_NOT_EXTRACT_METADATA, ImagePickerConstants.CAN_NOT_EXTRACT_METADATA_MESSAGE, e2);
            return null;
        } catch (SecurityException e3) {
            this.promise.reject(ImagePickerConstants.ERR_CAN_NOT_EXTRACT_METADATA, ImagePickerConstants.CAN_NOT_EXTRACT_METADATA_MESSAGE, e3);
            return null;
        } catch (IOException e4) {
            this.promise.reject(ImagePickerConstants.ERR_CAN_NOT_SAVE_RESULT, ImagePickerConstants.CAN_NOT_SAVE_RESULT_MESSAGE, e4);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r4, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0042, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0046, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void saveVideo(java.io.File r9) throws java.io.IOException {
        /*
            r8 = this;
            android.content.ContentResolver r0 = r8.contentResolver
            android.net.Uri r1 = r8.uri
            java.io.InputStream r0 = r0.openInputStream(r1)
            if (r0 == 0) goto L_0x0047
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            r2 = r1
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = r0
            java.io.InputStream r3 = (java.io.InputStream) r3     // Catch:{ all -> 0x0040 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ all -> 0x0040 }
            r4.<init>(r9)     // Catch:{ all -> 0x0040 }
            java.io.Closeable r4 = (java.io.Closeable) r4     // Catch:{ all -> 0x0040 }
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x0040 }
            r9 = r4
            java.io.FileOutputStream r9 = (java.io.FileOutputStream) r9     // Catch:{ all -> 0x0039 }
            r5 = 4096(0x1000, float:5.74E-42)
            byte[] r5 = new byte[r5]     // Catch:{ all -> 0x0039 }
        L_0x0023:
            int r6 = r3.read(r5)     // Catch:{ all -> 0x0039 }
            if (r6 <= 0) goto L_0x002e
            r7 = 0
            r9.write(r5, r7, r6)     // Catch:{ all -> 0x0039 }
            goto L_0x0023
        L_0x002e:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0039 }
            kotlin.p024io.CloseableKt.closeFinally(r4, r1)     // Catch:{ all -> 0x0040 }
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0040 }
            kotlin.p024io.CloseableKt.closeFinally(r0, r2)
            goto L_0x0047
        L_0x0039:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x003b }
        L_0x003b:
            r1 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r4, r9)     // Catch:{ all -> 0x0040 }
            throw r1     // Catch:{ all -> 0x0040 }
        L_0x0040:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x0042 }
        L_0x0042:
            r1 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r0, r9)
            throw r1
        L_0x0047:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.tasks.VideoResultTask.saveVideo(java.io.File):void");
    }
}
