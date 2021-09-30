package p008cl.json;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import p008cl.json.social.EmailShare;
import p008cl.json.social.FacebookPagesManagerShare;
import p008cl.json.social.FacebookShare;
import p008cl.json.social.GenericShare;
import p008cl.json.social.GooglePlusShare;
import p008cl.json.social.InstagramShare;
import p008cl.json.social.LinkedinShare;
import p008cl.json.social.MessengerShare;
import p008cl.json.social.PinterestShare;
import p008cl.json.social.SMSShare;
import p008cl.json.social.ShareIntent;
import p008cl.json.social.SnapChatShare;
import p008cl.json.social.TargetChosenReceiver;
import p008cl.json.social.TwitterShare;
import p008cl.json.social.WhatsAppShare;

/* renamed from: cl.json.RNShareModule */
public class RNShareModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static final int SHARE_REQUEST_CODE = 16845;
    private final ReactApplicationContext reactContext;

    public String getName() {
        return "RNShare";
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 16845 && i2 == 0) {
            TargetChosenReceiver.sendCallback(true, false, "CANCELED");
        }
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        onActivityResult(i, i2, intent);
    }

    /* renamed from: cl.json.RNShareModule$SHARES */
    private enum SHARES {
        facebook,
        generic,
        pagesmanager,
        twitter,
        whatsapp,
        instagram,
        googleplus,
        email,
        pinterest,
        messenger,
        snapchat,
        sms,
        linkedin;

        public static ShareIntent getShareClass(String str, ReactApplicationContext reactApplicationContext) {
            switch (C06091.$SwitchMap$cl$json$RNShareModule$SHARES[valueOf(str).ordinal()]) {
                case 1:
                    return new GenericShare(reactApplicationContext);
                case 2:
                    return new FacebookShare(reactApplicationContext);
                case 3:
                    return new FacebookPagesManagerShare(reactApplicationContext);
                case 4:
                    return new TwitterShare(reactApplicationContext);
                case 5:
                    return new WhatsAppShare(reactApplicationContext);
                case 6:
                    return new InstagramShare(reactApplicationContext);
                case 7:
                    return new GooglePlusShare(reactApplicationContext);
                case 8:
                    return new EmailShare(reactApplicationContext);
                case 9:
                    return new PinterestShare(reactApplicationContext);
                case 10:
                    return new SMSShare(reactApplicationContext);
                case 11:
                    return new SnapChatShare(reactApplicationContext);
                case 12:
                    return new MessengerShare(reactApplicationContext);
                case 13:
                    return new LinkedinShare(reactApplicationContext);
                default:
                    return null;
            }
        }
    }

    /* renamed from: cl.json.RNShareModule$1 */
    static /* synthetic */ class C06091 {
        static final /* synthetic */ int[] $SwitchMap$cl$json$RNShareModule$SHARES;

        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|(3:25|26|28)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0090 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                cl.json.RNShareModule$SHARES[] r0 = p008cl.json.RNShareModule.SHARES.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$cl$json$RNShareModule$SHARES = r0
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.generic     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x001d }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.facebook     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0028 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.pagesmanager     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0033 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.twitter     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x003e }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.whatsapp     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0049 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.instagram     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0054 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.googleplus     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0060 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.email     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x006c }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.pinterest     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0078 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.sms     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0084 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.snapchat     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x0090 }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.messenger     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r0 = $SwitchMap$cl$json$RNShareModule$SHARES     // Catch:{ NoSuchFieldError -> 0x009c }
                cl.json.RNShareModule$SHARES r1 = p008cl.json.RNShareModule.SHARES.linkedin     // Catch:{ NoSuchFieldError -> 0x009c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p008cl.json.RNShareModule.C06091.<clinit>():void");
        }
    }

    public RNShareModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(this);
        this.reactContext = reactApplicationContext;
    }

    @Nullable
    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        for (SHARES shares : SHARES.values()) {
            hashMap.put(shares.toString().toUpperCase(), shares.toString());
        }
        return hashMap;
    }

    @ReactMethod
    public void open(ReadableMap readableMap, Callback callback, Callback callback2) {
        TargetChosenReceiver.registerCallbacks(callback2, callback);
        try {
            new GenericShare(this.reactContext).open(readableMap);
        } catch (ActivityNotFoundException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
            TargetChosenReceiver.sendCallback(false, "not_available");
        } catch (Exception e2) {
            System.out.println("ERROR");
            System.out.println(e2.getMessage());
            TargetChosenReceiver.sendCallback(false, e2.getMessage());
        }
    }

    @ReactMethod
    public void shareSingle(ReadableMap readableMap, Callback callback, Callback callback2) {
        System.out.println("SHARE SINGLE METHOD");
        TargetChosenReceiver.registerCallbacks(callback2, callback);
        if (ShareIntent.hasValidKey(NotificationCompat.CATEGORY_SOCIAL, readableMap)) {
            try {
                ShareIntent shareClass = SHARES.getShareClass(readableMap.getString(NotificationCompat.CATEGORY_SOCIAL), this.reactContext);
                if (shareClass == null || !(shareClass instanceof ShareIntent)) {
                    throw new ActivityNotFoundException("Invalid share activity");
                }
                shareClass.open(readableMap);
            } catch (ActivityNotFoundException e) {
                System.out.println("ERROR");
                System.out.println(e.getMessage());
                TargetChosenReceiver.sendCallback(false, e.getMessage());
            } catch (Exception e2) {
                System.out.println("ERROR");
                System.out.println(e2.getMessage());
                TargetChosenReceiver.sendCallback(false, e2.getMessage());
            }
        } else {
            TargetChosenReceiver.sendCallback(false, "key 'social' missing in options");
        }
    }

    @ReactMethod
    public void isPackageInstalled(String str, Callback callback, Callback callback2) {
        try {
            callback2.invoke(Boolean.valueOf(ShareIntent.isPackageInstalled(str, this.reactContext)));
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("Error: " + e.getMessage());
            callback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void isBase64File(String str, Callback callback, Callback callback2) {
        try {
            String scheme = Uri.parse(str).getScheme();
            if (scheme == null || !scheme.equals("data")) {
                callback2.invoke(false);
                return;
            }
            callback2.invoke(true);
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
            callback.invoke(e.getMessage());
        }
    }
}
