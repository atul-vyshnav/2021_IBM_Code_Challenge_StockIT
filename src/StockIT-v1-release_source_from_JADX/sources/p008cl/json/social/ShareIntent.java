package p008cl.json.social;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.common.internal.ImagesContract;
import com.onesignal.OneSignalDbContract;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import p008cl.json.RNShareModule;
import p008cl.json.ShareFile;
import p008cl.json.ShareFiles;

/* renamed from: cl.json.social.ShareIntent */
public abstract class ShareIntent {
    protected String chooserTitle = "Share";
    protected ShareFile fileShare;
    protected Intent intent;
    protected ReadableMap options;
    protected final ReactApplicationContext reactContext;

    /* access modifiers changed from: protected */
    public abstract String getDefaultWebLink();

    /* access modifiers changed from: protected */
    public abstract String getPackage();

    /* access modifiers changed from: protected */
    public abstract String getPlayStoreLink();

    public ShareIntent(ReactApplicationContext reactApplicationContext) {
        this.reactContext = reactApplicationContext;
        setIntent(new Intent("android.intent.action.SEND"));
        getIntent().setType("text/plain");
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        this.options = readableMap;
        if (hasValidKey("subject", readableMap)) {
            getIntent().putExtra("android.intent.extra.SUBJECT", readableMap.getString("subject"));
        }
        if (hasValidKey(OneSignalDbContract.NotificationTable.COLUMN_NAME_TITLE, readableMap)) {
            this.chooserTitle = readableMap.getString(OneSignalDbContract.NotificationTable.COLUMN_NAME_TITLE);
        }
        String string = hasValidKey(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, readableMap) ? readableMap.getString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE) : "";
        if (hasValidKey("urls", readableMap)) {
            ShareFiles fileShares = getFileShares(readableMap);
            if (fileShares.isFile()) {
                ArrayList<Uri> uri = fileShares.getURI();
                getIntent().setAction("android.intent.action.SEND_MULTIPLE");
                getIntent().setType(fileShares.getType());
                getIntent().putParcelableArrayListExtra("android.intent.extra.STREAM", uri);
                getIntent().addFlags(1);
                if (!TextUtils.isEmpty(string)) {
                    getIntent().putExtra("android.intent.extra.TEXT", string);
                }
            } else if (!TextUtils.isEmpty(string)) {
                Intent intent2 = getIntent();
                intent2.putExtra("android.intent.extra.TEXT", string + StringUtils.SPACE + readableMap.getArray("urls").toString());
            } else {
                getIntent().putExtra("android.intent.extra.TEXT", readableMap.getArray("urls").toString());
            }
        } else if (hasValidKey(ImagesContract.URL, readableMap)) {
            ShareFile fileShare2 = getFileShare(readableMap);
            this.fileShare = fileShare2;
            if (fileShare2.isFile()) {
                Uri uri2 = this.fileShare.getURI();
                getIntent().setType(this.fileShare.getType());
                getIntent().putExtra("android.intent.extra.STREAM", uri2);
                getIntent().addFlags(1);
                if (!TextUtils.isEmpty(string)) {
                    getIntent().putExtra("android.intent.extra.TEXT", string);
                }
            } else if (!TextUtils.isEmpty(string)) {
                Intent intent3 = getIntent();
                intent3.putExtra("android.intent.extra.TEXT", string + StringUtils.SPACE + readableMap.getString(ImagesContract.URL));
            } else {
                getIntent().putExtra("android.intent.extra.TEXT", readableMap.getString(ImagesContract.URL));
            }
        } else if (!TextUtils.isEmpty(string)) {
            getIntent().putExtra("android.intent.extra.TEXT", string);
        }
    }

    /* access modifiers changed from: protected */
    public ShareFile getFileShare(ReadableMap readableMap) {
        if (hasValidKey("type", readableMap)) {
            return new ShareFile(readableMap.getString(ImagesContract.URL), readableMap.getString("type"), this.reactContext);
        }
        return new ShareFile(readableMap.getString(ImagesContract.URL), this.reactContext);
    }

    /* access modifiers changed from: protected */
    public ShareFiles getFileShares(ReadableMap readableMap) {
        if (hasValidKey("type", readableMap)) {
            return new ShareFiles(readableMap.getArray("urls"), readableMap.getString("type"), this.reactContext);
        }
        return new ShareFiles(readableMap.getArray("urls"), this.reactContext);
    }

    protected static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("URLEncoder.encode() failed for " + str);
        }
    }

    /* access modifiers changed from: protected */
    public Intent[] getIntentsToViewFile(Intent intent2, Uri uri) {
        List<ResolveInfo> queryIntentActivities = this.reactContext.getPackageManager().queryIntentActivities(intent2, 0);
        Intent[] intentArr = new Intent[queryIntentActivities.size()];
        for (int i = 0; i < queryIntentActivities.size(); i++) {
            ResolveInfo resolveInfo = queryIntentActivities.get(i);
            String str = resolveInfo.activityInfo.packageName;
            Intent intent3 = new Intent();
            intent3.setComponent(new ComponentName(str, resolveInfo.activityInfo.name));
            intent3.setAction("android.intent.action.VIEW");
            intent3.setDataAndType(uri, intent2.getType());
            intent3.addFlags(1);
            intentArr[i] = new Intent(intent3);
        }
        return intentArr;
    }

    /* access modifiers changed from: protected */
    public void openIntentChooser() throws ActivityNotFoundException {
        Intent intent2;
        Activity currentActivity = this.reactContext.getCurrentActivity();
        if (currentActivity == null) {
            TargetChosenReceiver.sendCallback(false, "Something went wrong");
            return;
        }
        IntentSender intentSender = null;
        if (TargetChosenReceiver.isSupported()) {
            intentSender = TargetChosenReceiver.getSharingSenderIntent(this.reactContext);
            intent2 = Intent.createChooser(getIntent(), this.chooserTitle, intentSender);
        } else {
            intent2 = Intent.createChooser(getIntent(), this.chooserTitle);
        }
        intent2.setFlags(1073741824);
        if (hasValidKey("showAppsToView", this.options) && hasValidKey(ImagesContract.URL, this.options)) {
            Intent intent3 = new Intent("android.intent.action.VIEW");
            intent3.setType(this.fileShare.getType());
            intent2.putExtra("android.intent.extra.INITIAL_INTENTS", getIntentsToViewFile(intent3, this.fileShare.getURI()));
        }
        currentActivity.startActivityForResult(intent2, RNShareModule.SHARE_REQUEST_CODE);
        if (intentSender == null) {
            TargetChosenReceiver.sendCallback(true, true, "OK");
        }
    }

    public static boolean isPackageInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Intent getIntent() {
        return this.intent;
    }

    /* access modifiers changed from: protected */
    public void setIntent(Intent intent2) {
        this.intent = intent2;
    }

    public static boolean hasValidKey(String str, ReadableMap readableMap) {
        return readableMap != null && readableMap.hasKey(str) && !readableMap.isNull(str);
    }
}
