package org.unimodules.adapters.react.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.google.android.exoplayer2.C1379C;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.permissions.Permissions;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;
import org.unimodules.interfaces.permissions.PermissionsStatus;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001d\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000eH\u0002¢\u0006\u0002\u0010\u001cJ%\u0010\u001d\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e2\u0006\u0010\u001e\u001a\u00020\fH\u0014¢\u0006\u0002\u0010\u001fJ)\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\f2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0016¢\u0006\u0002\u0010\"J)\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020%2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0016¢\u0006\u0002\u0010&J\b\u0010'\u001a\u00020\u001aH\u0003J\u0010\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u000fH\u0002J\b\u0010*\u001a\u00020+H\u0002J%\u0010,\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e2\u0006\u0010\u001e\u001a\u00020\fH\u0004¢\u0006\u0002\u0010\u001fJ\u0010\u0010-\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u000fH\u0002J\u0016\u0010.\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u000201000/H\u0016J\u0010\u00102\u001a\u0002032\u0006\u0010)\u001a\u00020\u000fH\u0002J\u0010\u00104\u001a\u0002032\u0006\u0010)\u001a\u00020\u000fH\u0014J\u0018\u00105\u001a\u0002062\u0006\u0010)\u001a\u00020\u000f2\u0006\u00107\u001a\u000203H\u0002J)\u00108\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\f2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0016¢\u0006\u0002\u0010\"J)\u00109\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020%2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0016¢\u0006\u0002\u0010&J!\u0010:\u001a\u00020\u00182\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0016¢\u0006\u0002\u0010;J\b\u0010<\u001a\u00020\u0018H\u0002J\u0010\u0010=\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u000fH\u0002J\u0010\u0010>\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u000fH\u0016J\b\u0010?\u001a\u00020\u0018H\u0002J\u0010\u0010@\u001a\u00020\u001a2\u0006\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u00020\u001aH\u0016J\b\u0010D\u001a\u00020\u001aH\u0016J\b\u0010E\u001a\u00020\u001aH\u0016J1\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002060G2\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e2\u0006\u0010I\u001a\u00020JH\u0002¢\u0006\u0002\u0010KR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000f\u0018\u00010\u000eX\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\fX\u000e¢\u0006\u0002\n\u0000R(\u0010\u0014\u001a\u001c\u0012\u0018\u0012\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\u0012\u0004\u0012\u00020\f0\u00160\u0015X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u000e¢\u0006\u0002\n\u0000¨\u0006L"}, mo40253d2 = {"Lorg/unimodules/adapters/react/permissions/PermissionsService;", "Lorg/unimodules/core/interfaces/InternalModule;", "Lorg/unimodules/interfaces/permissions/Permissions;", "Lorg/unimodules/core/interfaces/LifecycleEventListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "mActivityProvider", "Lorg/unimodules/core/interfaces/ActivityProvider;", "mAskAsyncListener", "Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;", "mAskAsyncRequestedPermissions", "", "", "[Ljava/lang/String;", "mAskedPermissionsCache", "Landroid/content/SharedPreferences;", "mCurrentPermissionListener", "mPendingPermissionCalls", "Ljava/util/Queue;", "Lkotlin/Pair;", "mWriteSettingsPermissionBeingAsked", "", "addToAskedPermissionsCache", "", "permissions", "([Ljava/lang/String;)V", "askForManifestPermissions", "listener", "([Ljava/lang/String;Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;)V", "askForPermissions", "responseListener", "(Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;[Ljava/lang/String;)V", "askForPermissionsWithPromise", "promise", "Lorg/unimodules/core/Promise;", "(Lorg/unimodules/core/Promise;[Ljava/lang/String;)V", "askForWriteSettingsPermissionFirst", "canAskAgain", "permission", "createListenerWithPendingPermissionsRequest", "Lcom/facebook/react/modules/core/PermissionListener;", "delegateRequestToActivity", "didAsk", "getExportedInterfaces", "", "Ljava/lang/Class;", "", "getManifestPermission", "", "getManifestPermissionFromContext", "getPermissionResponseFromNativeResponse", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "result", "getPermissions", "getPermissionsWithPromise", "hasGrantedPermissions", "([Ljava/lang/String;)Z", "hasWriteSettingsPermission", "isPermissionGranted", "isPermissionPresentInManifest", "isRuntimePermissionsAvailable", "onCreate", "moduleRegistry", "Lorg/unimodules/core/ModuleRegistry;", "onHostDestroy", "onHostPause", "onHostResume", "parseNativeResult", "", "permissionsString", "grantResults", "", "([Ljava/lang/String;[I)Ljava/util/Map;", "unimodules-react-native-adapter_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsService.kt */
public class PermissionsService implements InternalModule, Permissions, LifecycleEventListener {
    private final Context context;
    /* access modifiers changed from: private */
    public ActivityProvider mActivityProvider;
    private PermissionsResponseListener mAskAsyncListener;
    private String[] mAskAsyncRequestedPermissions;
    private SharedPreferences mAskedPermissionsCache;
    /* access modifiers changed from: private */
    public PermissionsResponseListener mCurrentPermissionListener;
    /* access modifiers changed from: private */
    public final Queue<Pair<String[], PermissionsResponseListener>> mPendingPermissionCalls = new LinkedList();
    private boolean mWriteSettingsPermissionBeingAsked;

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public void onHostDestroy() {
    }

    public void onHostPause() {
    }

    public PermissionsService(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public final Context getContext() {
        return this.context;
    }

    private final boolean didAsk(String str) {
        SharedPreferences sharedPreferences = this.mAskedPermissionsCache;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAskedPermissionsCache");
        }
        return sharedPreferences.getBoolean(str, false);
    }

    private final void addToAskedPermissionsCache(String[] strArr) {
        SharedPreferences sharedPreferences = this.mAskedPermissionsCache;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAskedPermissionsCache");
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (String putBoolean : strArr) {
            edit.putBoolean(putBoolean, true);
        }
        edit.apply();
    }

    public List<Class<? extends Object>> getExportedInterfaces() {
        return CollectionsKt.listOf(Permissions.class);
    }

    public void onCreate(ModuleRegistry moduleRegistry) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(moduleRegistry, "moduleRegistry");
        ActivityProvider activityProvider = (ActivityProvider) moduleRegistry.getModule(ActivityProvider.class);
        if (activityProvider != null) {
            this.mActivityProvider = activityProvider;
            ((UIManager) moduleRegistry.getModule(UIManager.class)).registerLifecycleEventListener(this);
            SharedPreferences sharedPreferences = this.context.getApplicationContext().getSharedPreferences("expo.modules.permissions.asked", 0);
            Intrinsics.checkNotNullExpressionValue(sharedPreferences, "context.applicationConte…ME, Context.MODE_PRIVATE)");
            this.mAskedPermissionsCache = sharedPreferences;
            return;
        }
        throw new IllegalStateException("Couldn't find implementation for ActivityProvider.");
    }

    public void getPermissionsWithPromise(Promise promise, String... strArr) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        getPermissions(new PermissionsService$getPermissionsWithPromise$1(promise), (String[]) Arrays.copyOf(strArr, strArr.length));
    }

    public void askForPermissionsWithPromise(Promise promise, String... strArr) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        askForPermissions(new PermissionsService$askForPermissionsWithPromise$1(this, promise, strArr), (String[]) Arrays.copyOf(strArr, strArr.length));
    }

    public void askForPermissions(PermissionsResponseListener permissionsResponseListener, String... strArr) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(permissionsResponseListener, "responseListener");
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        if (!ArraysKt.contains((T[]) strArr, "android.permission.WRITE_SETTINGS") || !isRuntimePermissionsAvailable()) {
            askForManifestPermissions(strArr, permissionsResponseListener);
            return;
        }
        List mutableList = ArraysKt.toMutableList((T[]) strArr);
        mutableList.remove("android.permission.WRITE_SETTINGS");
        Object[] array = mutableList.toArray(new String[0]);
        if (array != null) {
            String[] strArr2 = (String[]) array;
            PermissionsResponseListener permissionsService$askForPermissions$newListener$1 = new PermissionsService$askForPermissions$newListener$1(this, permissionsResponseListener);
            if (hasWriteSettingsPermission()) {
                askForManifestPermissions(strArr2, permissionsService$askForPermissions$newListener$1);
            } else if (this.mAskAsyncListener == null) {
                this.mAskAsyncListener = permissionsService$askForPermissions$newListener$1;
                this.mAskAsyncRequestedPermissions = strArr2;
                addToAskedPermissionsCache(new String[]{"android.permission.WRITE_SETTINGS"});
                askForWriteSettingsPermissionFirst();
            } else {
                throw new IllegalStateException("Another permissions request is in progress. Await the old request and then try again.");
            }
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    public boolean isPermissionPresentInManifest(String str) {
        Intrinsics.checkNotNullParameter(str, "permission");
        try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 4096);
            if (packageInfo != null) {
                String[] strArr = packageInfo.requestedPermissions;
                Intrinsics.checkNotNullExpressionValue(strArr, "requestedPermissions");
                return ArraysKt.contains((T[]) strArr, str);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    private final boolean isPermissionGranted(String str) {
        if (str.hashCode() == -2078357533 && str.equals("android.permission.WRITE_SETTINGS")) {
            return hasWriteSettingsPermission();
        }
        return getManifestPermission(str) == 0;
    }

    private final int getManifestPermission(String str) {
        Activity currentActivity;
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null || (currentActivity = activityProvider.getCurrentActivity()) == null || !(currentActivity instanceof PermissionAwareActivity)) {
            return getManifestPermissionFromContext(str);
        }
        return ContextCompat.checkSelfPermission(currentActivity, str);
    }

    /* access modifiers changed from: protected */
    public int getManifestPermissionFromContext(String str) {
        Intrinsics.checkNotNullParameter(str, "permission");
        return ContextCompat.checkSelfPermission(this.context, str);
    }

    private final boolean canAskAgain(String str) {
        Activity currentActivity;
        ActivityProvider activityProvider = this.mActivityProvider;
        if (activityProvider == null || (currentActivity = activityProvider.getCurrentActivity()) == null) {
            return false;
        }
        return ActivityCompat.shouldShowRequestPermissionRationale(currentActivity, str);
    }

    /* access modifiers changed from: private */
    public final Map<String, PermissionsResponse> parseNativeResult(String[] strArr, int[] iArr) {
        HashMap hashMap = new HashMap();
        for (Pair pair : ArraysKt.zip(iArr, (R[]) strArr)) {
            int intValue = ((Number) pair.component1()).intValue();
            String str = (String) pair.component2();
            hashMap.put(str, getPermissionResponseFromNativeResponse(str, intValue));
        }
        return hashMap;
    }

    /* access modifiers changed from: private */
    public final PermissionsResponse getPermissionResponseFromNativeResponse(String str, int i) {
        PermissionsStatus permissionsStatus;
        if (i == 0) {
            permissionsStatus = PermissionsStatus.GRANTED;
        } else if (didAsk(str)) {
            permissionsStatus = PermissionsStatus.DENIED;
        } else {
            permissionsStatus = PermissionsStatus.UNDETERMINED;
        }
        return new PermissionsResponse(permissionsStatus, permissionsStatus == PermissionsStatus.DENIED ? canAskAgain(str) : true);
    }

    /* access modifiers changed from: protected */
    public void askForManifestPermissions(String[] strArr, PermissionsResponseListener permissionsResponseListener) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        Intrinsics.checkNotNullParameter(permissionsResponseListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        if (!isRuntimePermissionsAvailable()) {
            addToAskedPermissionsCache(strArr);
            Collection arrayList = new ArrayList(strArr.length);
            for (String manifestPermission : strArr) {
                arrayList.add(Integer.valueOf(getManifestPermission(manifestPermission)));
            }
            permissionsResponseListener.onResult(parseNativeResult(strArr, CollectionsKt.toIntArray((List) arrayList)));
            return;
        }
        delegateRequestToActivity(strArr, permissionsResponseListener);
    }

    /* access modifiers changed from: protected */
    public final void delegateRequestToActivity(String[] strArr, PermissionsResponseListener permissionsResponseListener) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        Intrinsics.checkNotNullParameter(permissionsResponseListener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        addToAskedPermissionsCache(strArr);
        ActivityProvider activityProvider = this.mActivityProvider;
        Activity currentActivity = activityProvider != null ? activityProvider.getCurrentActivity() : null;
        if (currentActivity instanceof PermissionAwareActivity) {
            synchronized (this) {
                if (this.mCurrentPermissionListener != null) {
                    Boolean.valueOf(this.mPendingPermissionCalls.add(TuplesKt.m1160to(strArr, permissionsResponseListener)));
                } else {
                    this.mCurrentPermissionListener = permissionsResponseListener;
                    ((PermissionAwareActivity) currentActivity).requestPermissions(strArr, 13, createListenerWithPendingPermissionsRequest());
                    Unit unit = Unit.INSTANCE;
                }
            }
            return;
        }
        int length = strArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = -1;
        }
        permissionsResponseListener.onResult(parseNativeResult(strArr, iArr));
    }

    /* access modifiers changed from: private */
    public final PermissionListener createListenerWithPendingPermissionsRequest() {
        return new PermissionsService$createListenerWithPendingPermissionsRequest$1(this);
    }

    private final void askForWriteSettingsPermissionFirst() {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        intent.addFlags(C1379C.ENCODING_PCM_MU_LAW);
        this.mWriteSettingsPermissionBeingAsked = true;
        this.context.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public final boolean hasWriteSettingsPermission() {
        if (isRuntimePermissionsAvailable()) {
            return Settings.System.canWrite(this.context.getApplicationContext());
        }
        return true;
    }

    private final boolean isRuntimePermissionsAvailable() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public void onHostResume() {
        if (this.mWriteSettingsPermissionBeingAsked) {
            boolean z = false;
            this.mWriteSettingsPermissionBeingAsked = false;
            PermissionsResponseListener permissionsResponseListener = this.mAskAsyncListener;
            Intrinsics.checkNotNull(permissionsResponseListener);
            String[] strArr = this.mAskAsyncRequestedPermissions;
            Intrinsics.checkNotNull(strArr);
            this.mAskAsyncListener = null;
            this.mAskAsyncRequestedPermissions = null;
            if (strArr.length == 0) {
                z = true;
            }
            if (!z) {
                askForManifestPermissions(strArr, permissionsResponseListener);
            } else {
                permissionsResponseListener.onResult(new LinkedHashMap());
            }
        }
    }

    public void getPermissions(PermissionsResponseListener permissionsResponseListener, String... strArr) {
        Intrinsics.checkNotNullParameter(permissionsResponseListener, "responseListener");
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        Collection arrayList = new ArrayList(strArr.length);
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            arrayList.add(Integer.valueOf(isPermissionGranted(strArr[i]) ? 0 : -1));
        }
        permissionsResponseListener.onResult(parseNativeResult(strArr, CollectionsKt.toIntArray((List) arrayList)));
    }

    public boolean hasGrantedPermissions(String... strArr) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        for (String isPermissionGranted : strArr) {
            if (!isPermissionGranted(isPermissionGranted)) {
                return false;
            }
        }
        return true;
    }
}
