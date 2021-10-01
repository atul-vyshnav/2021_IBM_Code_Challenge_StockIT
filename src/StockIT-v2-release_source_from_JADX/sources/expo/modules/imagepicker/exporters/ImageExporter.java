package expo.modules.imagepicker.exporters;

import android.net.Uri;
import java.io.ByteArrayOutputStream;
import java.io.File;
import kotlin.Metadata;

@FunctionalInterface
@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\nJ \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\u000b"}, mo40253d2 = {"Lexpo/modules/imagepicker/exporters/ImageExporter;", "", "export", "", "source", "Landroid/net/Uri;", "output", "Ljava/io/File;", "exporterListener", "Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "Listener", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImageExporter.kt */
public interface ImageExporter {

    @Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\"\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH&¨\u0006\f"}, mo40253d2 = {"Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "", "onFailure", "", "cause", "", "onResult", "out", "Ljava/io/ByteArrayOutputStream;", "width", "", "height", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
    /* compiled from: ImageExporter.kt */
    public interface Listener {
        void onFailure(Throwable th);

        void onResult(ByteArrayOutputStream byteArrayOutputStream, int i, int i2);
    }

    void export(Uri uri, File file, Listener listener);
}
