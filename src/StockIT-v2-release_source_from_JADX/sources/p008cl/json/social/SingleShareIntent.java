package p008cl.json.social;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.exoplayer2.C1379C;
import com.google.android.gms.common.internal.ImagesContract;
import com.onesignal.OneSignalDbContract;
import p008cl.json.RNShareModule;

/* renamed from: cl.json.social.SingleShareIntent */
public abstract class SingleShareIntent extends ShareIntent {
    protected String appStoreURL = null;
    protected String playStoreURL = null;

    public SingleShareIntent(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        String str;
        System.out.println(getPackage());
        if (!(getPackage() == null && getDefaultWebLink() == null && getPlayStoreLink() == null)) {
            if (isPackageInstalled(getPackage(), this.reactContext)) {
                System.out.println("INSTALLED");
                getIntent().setPackage(getPackage());
                super.open(readableMap);
            } else {
                System.out.println("NOT INSTALLED");
                if (getDefaultWebLink() != null) {
                    str = getDefaultWebLink().replace("{url}", urlEncode(readableMap.getString(ImagesContract.URL))).replace("{message}", urlEncode(readableMap.getString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE)));
                } else {
                    str = getPlayStoreLink() != null ? getPlayStoreLink() : "";
                }
                setIntent(new Intent(new Intent("android.intent.action.VIEW", Uri.parse(str))));
            }
        }
        super.open(readableMap);
    }

    /* access modifiers changed from: protected */
    public void openIntentChooser() throws ActivityNotFoundException {
        if (!this.options.hasKey("forceDialog") || !this.options.getBoolean("forceDialog")) {
            getIntent().setFlags(C1379C.ENCODING_PCM_MU_LAW);
            this.reactContext.startActivity(getIntent());
            TargetChosenReceiver.sendCallback(true, true, getIntent().getPackage());
            return;
        }
        Activity currentActivity = this.reactContext.getCurrentActivity();
        if (currentActivity == null) {
            TargetChosenReceiver.sendCallback(false, "Something went wrong");
        } else if (TargetChosenReceiver.isSupported()) {
            Intent createChooser = Intent.createChooser(getIntent(), this.chooserTitle, TargetChosenReceiver.getSharingSenderIntent(this.reactContext));
            createChooser.setFlags(1073741824);
            currentActivity.startActivityForResult(createChooser, RNShareModule.SHARE_REQUEST_CODE);
        } else {
            Intent createChooser2 = Intent.createChooser(getIntent(), this.chooserTitle);
            createChooser2.setFlags(1073741824);
            currentActivity.startActivityForResult(createChooser2, RNShareModule.SHARE_REQUEST_CODE);
            TargetChosenReceiver.sendCallback(true, true, "OK");
        }
    }
}
