package p000ai.api.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import java.util.LinkedList;
import java.util.List;

/* renamed from: ai.api.util.RecognizerChecker */
public class RecognizerChecker {
    public static final String GOOGLE_RECOGNIZER_PACKAGE_NAME = "com.google.android.googlequicksearchbox";
    public static final String GOOGLE_VOICE_SEARCH_PACKAGE_NAME = "com.google.android.voicesearch";

    public static ComponentName findGoogleRecognizer(Context context) {
        return findRecognizerByPackage(context, getPackageName());
    }

    private static ComponentName findRecognizerByPackage(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> queryIntentServices = packageManager != null ? packageManager.queryIntentServices(new Intent("android.speech.RecognitionService"), 0) : new LinkedList<>();
        if (!(queryIntentServices.size() == 0 || str == null)) {
            for (ResolveInfo resolveInfo : queryIntentServices) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null && str.equals(serviceInfo.packageName)) {
                    return new ComponentName(serviceInfo.packageName, serviceInfo.name);
                }
            }
        }
        return null;
    }

    public static String getGoogleRecognizerVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ComponentName findGoogleRecognizer = findGoogleRecognizer(context);
            if (findGoogleRecognizer != null) {
                return packageManager.getPackageInfo(findGoogleRecognizer.getPackageName(), 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return "";
    }

    public static boolean isGoogleRecognizerAvailable(Context context) {
        return findGoogleRecognizer(context) != null;
    }

    private static String getPackageName() {
        return Build.VERSION.SDK_INT >= 16 ? GOOGLE_RECOGNIZER_PACKAGE_NAME : GOOGLE_VOICE_SEARCH_PACKAGE_NAME;
    }
}
