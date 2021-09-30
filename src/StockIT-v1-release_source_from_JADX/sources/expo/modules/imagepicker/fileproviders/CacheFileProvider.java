package expo.modules.imagepicker.fileproviders;

import expo.modules.imagepicker.ImagePickerConstants;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.utilities.FileUtilities;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, mo40253d2 = {"Lexpo/modules/imagepicker/fileproviders/CacheFileProvider;", "Lexpo/modules/imagepicker/fileproviders/FileProvider;", "cacheFolder", "Ljava/io/File;", "extension", "", "(Ljava/io/File;Ljava/lang/String;)V", "generateFile", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: CacheFileProvider.kt */
public final class CacheFileProvider implements FileProvider {
    private final File cacheFolder;
    private final String extension;

    public CacheFileProvider(File file, String str) {
        Intrinsics.checkNotNullParameter(file, "cacheFolder");
        Intrinsics.checkNotNullParameter(str, "extension");
        this.cacheFolder = file;
        this.extension = str;
    }

    public File generateFile() {
        return new File(FileUtilities.generateOutputPath(this.cacheFolder, ImagePickerConstants.CACHE_DIR_NAME, this.extension));
    }
}
