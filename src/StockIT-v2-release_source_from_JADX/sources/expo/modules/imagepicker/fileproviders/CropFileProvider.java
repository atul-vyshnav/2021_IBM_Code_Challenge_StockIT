package expo.modules.imagepicker.fileproviders;

import android.net.Uri;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo40253d2 = {"Lexpo/modules/imagepicker/fileproviders/CropFileProvider;", "Lexpo/modules/imagepicker/fileproviders/FileProvider;", "croppedUri", "Landroid/net/Uri;", "(Landroid/net/Uri;)V", "generateFile", "Ljava/io/File;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: CropFileProvider.kt */
public final class CropFileProvider implements FileProvider {
    private final Uri croppedUri;

    public CropFileProvider(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "croppedUri");
        this.croppedUri = uri;
    }

    public File generateFile() {
        String path = this.croppedUri.getPath();
        Intrinsics.checkNotNull(path);
        return new File(path);
    }
}
