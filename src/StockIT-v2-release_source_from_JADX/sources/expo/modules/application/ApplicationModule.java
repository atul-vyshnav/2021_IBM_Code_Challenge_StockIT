package expo.modules.application;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import java.util.HashMap;
import java.util.Map;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.RegistryLifecycleListener;

public class ApplicationModule extends ExportedModule implements RegistryLifecycleListener {
    private static final String NAME = "ExpoApplication";
    /* access modifiers changed from: private */
    public static final String TAG = ApplicationModule.class.getSimpleName();
    private Activity mActivity;
    private ActivityProvider mActivityProvider;
    private Context mContext;
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return NAME;
    }

    public ApplicationModule(Context context) {
        super(context);
        this.mContext = context;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
        ActivityProvider activityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
        this.mActivityProvider = activityProvider;
        this.mActivity = activityProvider.getCurrentActivity();
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        String charSequence = this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager()).toString();
        String packageName = this.mContext.getPackageName();
        hashMap.put("applicationName", charSequence);
        hashMap.put("applicationId", packageName);
        try {
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(packageName, 0);
            hashMap.put("nativeApplicationVersion", packageInfo.versionName);
            hashMap.put("nativeBuildVersion", Integer.toString((int) getLongVersionCode(packageInfo)));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception: ", e);
        }
        hashMap.put("androidId", Settings.Secure.getString(this.mContext.getContentResolver(), "android_id"));
        return hashMap;
    }

    @ExpoMethod
    public void getInstallationTimeAsync(Promise promise) {
        try {
            promise.resolve(Double.valueOf((double) this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).firstInstallTime));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception: ", e);
            promise.reject("ERR_APPLICATION_PACKAGE_NAME_NOT_FOUND", "Unable to get install time of this application. Could not get package info or package name.", e);
        }
    }

    @ExpoMethod
    public void getLastUpdateTimeAsync(Promise promise) {
        try {
            promise.resolve(Double.valueOf((double) this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).lastUpdateTime));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception: ", e);
            promise.reject("ERR_APPLICATION_PACKAGE_NAME_NOT_FOUND", "Unable to get last update time of this application. Could not get package info or package name.", e);
        }
    }

    @ExpoMethod
    public void getInstallReferrerAsync(final Promise promise) {
        final StringBuilder sb = new StringBuilder();
        final InstallReferrerClient build = InstallReferrerClient.newBuilder(this.mContext).build();
        build.startConnection(new InstallReferrerStateListener() {
            public void onInstallReferrerSetupFinished(int i) {
                if (i == 0) {
                    try {
                        sb.append(build.getInstallReferrer().getInstallReferrer());
                    } catch (RemoteException e) {
                        Log.e(ApplicationModule.TAG, "Exception: ", e);
                        promise.reject("ERR_APPLICATION_INSTALL_REFERRER_REMOTE_EXCEPTION", "RemoteException getting install referrer information. This may happen if the process hosting the remote object is no longer available.", e);
                    }
                    promise.resolve(sb.toString());
                } else if (i == 1) {
                    promise.reject("ERR_APPLICATION_INSTALL_REFERRER_CONNECTION", "Could not establish a connection to Google Play");
                } else if (i != 2) {
                    Promise promise = promise;
                    promise.reject("ERR_APPLICATION_INSTALL_REFERRER", "General error retrieving the install referrer: response code " + i);
                } else {
                    promise.reject("ERR_APPLICATION_INSTALL_REFERRER_UNAVAILABLE", "The current Play Store app doesn't provide the installation referrer API, or the Play Store may not be installed.");
                }
                build.endConnection();
            }

            public void onInstallReferrerServiceDisconnected() {
                promise.reject("ERR_APPLICATION_INSTALL_REFERRER_SERVICE_DISCONNECTED", "Connection to install referrer service was lost.");
            }
        });
    }

    private static long getLongVersionCode(PackageInfo packageInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo.getLongVersionCode();
        }
        return (long) packageInfo.versionCode;
    }
}
