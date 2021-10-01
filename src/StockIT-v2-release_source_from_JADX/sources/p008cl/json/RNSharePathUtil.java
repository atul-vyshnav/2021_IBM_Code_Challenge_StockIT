package p008cl.json;

import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReactContext;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.File;
import java.util.ArrayList;
import org.apache.logging.log4j.message.ParameterizedMessage;

/* renamed from: cl.json.RNSharePathUtil */
public class RNSharePathUtil {
    private static final ArrayList<String> authorities = new ArrayList<>();

    public static void compileAuthorities(ReactContext reactContext) {
        if (authorities.size() == 0) {
            Application application = (Application) reactContext.getApplicationContext();
            if (application instanceof ShareApplication) {
                authorities.add(((ShareApplication) application).getFileProviderAuthority());
            }
            ArrayList<String> arrayList = authorities;
            arrayList.add(reactContext.getPackageName() + ".rnshare.fileprovider");
        }
    }

    public static Uri compatUriFromFile(ReactContext reactContext, File file) {
        compileAuthorities(reactContext);
        String authority = Uri.fromFile(file).getAuthority();
        if (!TextUtils.isEmpty(authority) && authorities.contains(authority)) {
            return Uri.fromFile(file);
        }
        if (file.getAbsolutePath().startsWith(RNFetchBlobConst.FILE_PREFIX_CONTENT)) {
            return Uri.fromFile(file);
        }
        Uri uri = null;
        int i = 0;
        while (i < authorities.size()) {
            try {
                uri = FileProvider.getUriForFile(reactContext, authorities.get(i), file);
                if (uri != null) {
                    break;
                }
                i++;
            } catch (Exception unused) {
            }
        }
        return uri;
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Uri uri2 = null;
        if (Build.VERSION.SDK_INT < 19 || !DocumentsContract.isDocumentUri(context, uri)) {
            if (UriUtil.LOCAL_CONTENT_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return "" + getDataColumn(context, uri, (String) null, (String[]) null);
            } else if (UriUtil.LOCAL_FILE_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(ParameterizedMessage.ERROR_MSG_SEPARATOR);
            String str = split[0];
            if ("primary".equalsIgnoreCase(str) || "0".equalsIgnoreCase(str)) {
                return "" + Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if ("raw".equalsIgnoreCase(str)) {
                return "" + split[1];
            } else if (!TextUtils.isEmpty(str)) {
                return "" + "/storage/" + str + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (documentId.startsWith("raw:")) {
                return "" + documentId.replaceFirst("raw:", "");
            }
            Uri withAppendedId = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue());
            return "" + getDataColumn(context, withAppendedId, (String) null, (String[]) null);
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(ParameterizedMessage.ERROR_MSG_SEPARATOR);
            String str2 = split2[0];
            if ("image".equals(str2)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if (MimeTypes.BASE_TYPE_VIDEO.equals(str2)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if (MimeTypes.BASE_TYPE_AUDIO.equals(str2)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            } else if ("raw".equalsIgnoreCase(str2)) {
                return "" + split2[1];
            }
            String[] strArr = {split2[1]};
            return "" + getDataColumn(context, uri2, "_id=?", strArr);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getDataColumn(android.content.Context r10, android.net.Uri r11, java.lang.String r12, java.lang.String[] r13) {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r4 = new java.lang.String[]{r0}
            r8 = 0
            androidx.loader.content.CursorLoader r9 = new androidx.loader.content.CursorLoader     // Catch:{ all -> 0x0035 }
            r7 = 0
            r1 = r9
            r2 = r10
            r3 = r11
            r5 = r12
            r6 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0035 }
            android.database.Cursor r10 = r9.loadInBackground()     // Catch:{ all -> 0x0035 }
            if (r10 == 0) goto L_0x002f
            boolean r11 = r10.moveToFirst()     // Catch:{ all -> 0x002c }
            if (r11 == 0) goto L_0x002f
            int r11 = r10.getColumnIndexOrThrow(r0)     // Catch:{ all -> 0x002c }
            java.lang.String r11 = r10.getString(r11)     // Catch:{ all -> 0x002c }
            if (r10 == 0) goto L_0x002b
            r10.close()
        L_0x002b:
            return r11
        L_0x002c:
            r11 = move-exception
            r8 = r10
            goto L_0x0036
        L_0x002f:
            if (r10 == 0) goto L_0x0034
            r10.close()
        L_0x0034:
            return r8
        L_0x0035:
            r11 = move-exception
        L_0x0036:
            if (r8 == 0) goto L_0x003b
            r8.close()
        L_0x003b:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: p008cl.json.RNSharePathUtil.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
