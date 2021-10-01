package expo.modules.imagepicker;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.core.content.FileProvider;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.common.util.UriUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.unimodules.core.utilities.FileUtilities;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t\u001a\u0018\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0012\u0010\u0010\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\tH\u0002\u001a\u000e\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t\u001a+\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u0016*\u0004\u0018\u0001H\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\bø\u0001\u0000¢\u0006\u0002\u0010\u0019\u0002\u0007\n\u0005\b20\u0001¨\u0006\u001a"}, mo40253d2 = {"contentUriFromFile", "Landroid/net/Uri;", "file", "Ljava/io/File;", "application", "Landroid/app/Application;", "createOutputFile", "cacheDir", "extension", "", "deduceExtension", "type", "getType", "contentResolver", "Landroid/content/ContentResolver;", "uri", "getTypeFromFileUrl", "url", "uriFromFile", "uriFromFilePath", "path", "ifNull", "T", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "expo-image-picker_release"}, mo40254k = 2, mo40255mv = {1, 4, 2})
/* compiled from: ImagePickerUtils.kt */
public final class ImagePickerUtilsKt {
    public static final <T> T ifNull(T t, Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(function0, "block");
        return t == null ? function0.invoke() : t;
    }

    private static final String getTypeFromFileUrl(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    public static final File createOutputFile(File file, String str) {
        Intrinsics.checkNotNullParameter(file, "cacheDir");
        Intrinsics.checkNotNullParameter(str, "extension");
        try {
            File file2 = new File(FileUtilities.generateOutputPath(file, ImagePickerConstants.CACHE_DIR_NAME, str));
            file2.createNewFile();
            return file2;
        } catch (IOException unused) {
            return null;
        }
    }

    public static final String getType(ContentResolver contentResolver, Uri uri) {
        Intrinsics.checkNotNullParameter(contentResolver, "contentResolver");
        Intrinsics.checkNotNullParameter(uri, "uri");
        String type = contentResolver.getType(uri);
        if (type != null) {
            return type;
        }
        String uri2 = uri.toString();
        Intrinsics.checkNotNullExpressionValue(uri2, "uri.toString()");
        return getTypeFromFileUrl(uri2);
    }

    public static final Uri contentUriFromFile(File file, Application application) {
        Intrinsics.checkNotNullParameter(file, UriUtil.LOCAL_FILE_SCHEME);
        Intrinsics.checkNotNullParameter(application, MimeTypes.BASE_TYPE_APPLICATION);
        try {
            Uri uriForFile = FileProvider.getUriForFile(application, application.getPackageName() + ".ImagePickerFileProvider", file);
            Intrinsics.checkNotNullExpressionValue(uriForFile, "FileProvider.getUriForFi…ickerFileProvider\", file)");
            return uriForFile;
        } catch (Exception unused) {
            Uri fromFile = Uri.fromFile(file);
            Intrinsics.checkNotNullExpressionValue(fromFile, "Uri.fromFile(file)");
            return fromFile;
        }
    }

    public static final Uri uriFromFile(File file) {
        Intrinsics.checkNotNullParameter(file, UriUtil.LOCAL_FILE_SCHEME);
        Uri fromFile = Uri.fromFile(file);
        Intrinsics.checkNotNullExpressionValue(fromFile, "Uri.fromFile(file)");
        return fromFile;
    }

    public static final Uri uriFromFilePath(String str) {
        Intrinsics.checkNotNullParameter(str, RNFetchBlobConst.RNFB_RESPONSE_PATH);
        return uriFromFile(new File(str));
    }

    public static final String deduceExtension(String str) {
        Intrinsics.checkNotNullParameter(str, "type");
        CharSequence charSequence = str;
        if (StringsKt.contains$default(charSequence, (CharSequence) "png", false, 2, (Object) null)) {
            return ".png";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "gif", false, 2, (Object) null)) {
            return ".gif";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "bmp", false, 2, (Object) null)) {
            return ".bmp";
        }
        if (StringsKt.contains$default(charSequence, (CharSequence) "jpeg", false, 2, (Object) null)) {
            return ".jpg";
        }
        Log.w(ImagePickerConstants.TAG, "Image type not supported. Falling back to JPEG instead.");
        return ".jpg";
    }
}
