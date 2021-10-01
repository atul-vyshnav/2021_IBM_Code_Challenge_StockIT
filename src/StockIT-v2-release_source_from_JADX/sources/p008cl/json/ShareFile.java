package p008cl.json;

import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReactApplicationContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: cl.json.ShareFile */
public class ShareFile {
    private final ReactApplicationContext reactContext;
    private String type;
    private Uri uri;
    private String url;

    public ShareFile(String str, String str2, ReactApplicationContext reactApplicationContext) {
        this(str, reactApplicationContext);
        this.type = str2;
    }

    public ShareFile(String str, ReactApplicationContext reactApplicationContext) {
        this.url = str;
        this.uri = Uri.parse(str);
        this.reactContext = reactApplicationContext;
    }

    private String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    public boolean isFile() {
        return isBase64File() || isLocalFile();
    }

    private boolean isBase64File() {
        if (this.uri.getScheme() == null || !this.uri.getScheme().equals("data")) {
            return false;
        }
        this.type = this.uri.getSchemeSpecificPart().substring(0, this.uri.getSchemeSpecificPart().indexOf(";"));
        return true;
    }

    private boolean isLocalFile() {
        if (this.uri.getScheme() == null || (!this.uri.getScheme().equals(UriUtil.LOCAL_CONTENT_SCHEME) && !this.uri.getScheme().equals(UriUtil.LOCAL_FILE_SCHEME))) {
            return false;
        }
        if (this.type != null) {
            return true;
        }
        String mimeType = getMimeType(this.uri.toString());
        this.type = mimeType;
        if (mimeType == null) {
            String realPathFromURI = getRealPathFromURI(this.uri);
            if (realPathFromURI == null) {
                return false;
            }
            this.type = getMimeType(realPathFromURI);
        }
        if (this.type == null) {
            this.type = "*/*";
        }
        return true;
    }

    public String getType() {
        String str = this.type;
        return str == null ? "*/*" : str;
    }

    private String getRealPathFromURI(Uri uri2) {
        return RNSharePathUtil.getRealPathFromURI(this.reactContext, uri2);
    }

    public Uri getURI() {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(getType());
        if (isBase64File()) {
            String substring = this.uri.getSchemeSpecificPart().substring(this.uri.getSchemeSpecificPart().indexOf(";base64,") + 8);
            try {
                File file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new IOException("mkdirs failed on " + file.getAbsolutePath());
                    }
                }
                File file2 = new File(file, System.nanoTime() + "." + extensionFromMimeType);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(Base64.decode(substring, 0));
                fileOutputStream.flush();
                fileOutputStream.close();
                return RNSharePathUtil.compatUriFromFile(this.reactContext, file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (isLocalFile()) {
                Uri parse = Uri.parse(this.url);
                if (parse.getPath() == null) {
                    return null;
                }
                return RNSharePathUtil.compatUriFromFile(this.reactContext, new File(parse.getPath()));
            }
            return null;
        }
    }
}
