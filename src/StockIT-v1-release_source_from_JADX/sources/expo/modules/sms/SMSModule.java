package expo.modules.sms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import androidx.core.p005os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.services.UIManager;

public class SMSModule extends ExportedModule implements LifecycleEventListener {
    private static final String ERROR_TAG = "E_SMS";
    private static final String OPTIONS_ATTACHMENTS_KEY = "attachments";
    private static final String TAG = "ExpoSMS";
    private ModuleRegistry mModuleRegistry;
    private Promise mPendingPromise;
    private boolean mSMSComposerOpened = false;

    public String getName() {
        return TAG;
    }

    public void onHostDestroy() {
    }

    public void onHostPause() {
    }

    SMSModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
        if (moduleRegistry.getModule(UIManager.class) != null) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).registerLifecycleEventListener(this);
        }
    }

    public void onDestroy() {
        ModuleRegistry moduleRegistry = this.mModuleRegistry;
        if (!(moduleRegistry == null || moduleRegistry.getModule(UIManager.class) == null)) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).unregisterLifecycleEventListener(this);
        }
        this.mModuleRegistry = null;
    }

    @ExpoMethod
    public void sendSMSAsync(ArrayList<String> arrayList, String str, Map<String, Object> map, Promise promise) {
        Intent intent;
        if (this.mPendingPromise != null) {
            promise.reject("E_SMS_SENDING_IN_PROGRESS", "Different SMS sending in progress. Await the old request and then try again.");
            return;
        }
        List list = null;
        if (map != null && map.containsKey(OPTIONS_ATTACHMENTS_KEY)) {
            list = (List) map.get(OPTIONS_ATTACHMENTS_KEY);
        }
        if (list == null || list.isEmpty()) {
            intent = new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("smsto:" + constructRecipients(arrayList)));
        } else {
            intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("address", constructRecipients(arrayList));
            Map map2 = (Map) list.get(0);
            intent.putExtra("android.intent.extra.STREAM", Uri.parse((String) map2.get("uri")));
            intent.setType((String) map2.get("mimeType"));
            intent.addFlags(1);
        }
        String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(getContext());
        if (defaultSmsPackage != null) {
            intent.setPackage(defaultSmsPackage);
            intent.putExtra("exit_on_sent", true);
            intent.putExtra("compose_mode", true);
            intent.putExtra("android.intent.extra.TEXT", str);
            intent.putExtra("sms_body", str);
            this.mPendingPromise = promise;
            ((ActivityProvider) this.mModuleRegistry.getModule(ActivityProvider.class)).getCurrentActivity().startActivity(intent);
            this.mSMSComposerOpened = true;
            return;
        }
        promise.reject("E_SMS_NO_SMS_APP", "No messaging application available");
    }

    @ExpoMethod
    public void isAvailableAsync(Promise promise) {
        if (getContext().getPackageManager().hasSystemFeature("android.hardware.telephony")) {
            promise.resolve(true);
        } else {
            promise.resolve(false);
        }
    }

    public void onHostResume() {
        if (this.mSMSComposerOpened && this.mPendingPromise != null) {
            Bundle bundle = new Bundle();
            bundle.putString("result", EnvironmentCompat.MEDIA_UNKNOWN);
            this.mPendingPromise.resolve(bundle);
            this.mPendingPromise = null;
        }
        this.mSMSComposerOpened = false;
    }

    private String constructRecipients(List<String> list) {
        if (list.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(list.get(0));
        for (String append : list) {
            sb.append(';');
            sb.append(append);
        }
        return sb.toString();
    }
}
