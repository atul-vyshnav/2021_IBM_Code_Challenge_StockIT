package expo.modules.permissions;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import com.facebook.react.bridge.BaseJavaModule;
import expo.modules.permissions.requesters.BackgroundLocationRequester;
import expo.modules.permissions.requesters.ForegroundLocationRequester;
import expo.modules.permissions.requesters.LegacyLocationRequester;
import expo.modules.permissions.requesters.NotificationRequester;
import expo.modules.permissions.requesters.PermissionRequester;
import expo.modules.permissions.requesters.RemindersRequester;
import expo.modules.permissions.requesters.SimpleRequester;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.permissions.Permissions;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u000b\u001a\u00020\f2\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\t0\u000ej\b\u0012\u0004\u0012\u00020\t`\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J(\u0010\u0012\u001a\u00020\u00132\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\t0\u000ej\b\u0012\u0004\u0012\u00020\t`\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002JJ\u0010\u0014\u001a\u00020\f2\u0016\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\t0\u000ej\b\u0012\u0004\u0012\u00020\t`\u000f2 \u0010\u0016\u001a\u001c\u0012\u0004\u0012\u00020\u0013\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\t0\u0018\u0012\u0004\u0012\u00020\f0\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u00182\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u001aH\u0002¢\u0006\u0002\u0010\u001bJ(\u0010\u001c\u001a\u00020\f2\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\t0\u000ej\b\u0012\u0004\u0012\u00020\t`\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\b\u0010\u001d\u001a\u00020\tH\u0016J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\tH\u0002J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0016J*\u0010#\u001a\u00020$2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u001a2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020&0\bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X.¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX.¢\u0006\u0002\n\u0000¨\u0006'"}, mo40253d2 = {"Lexpo/modules/permissions/PermissionsModule;", "Lorg/unimodules/core/ExportedModule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mPermissions", "Lorg/unimodules/interfaces/permissions/Permissions;", "mRequesters", "", "", "Lexpo/modules/permissions/requesters/PermissionRequester;", "askAsync", "", "requestedPermissionsTypes", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "promise", "Lorg/unimodules/core/Promise;", "createPermissionsResponseListener", "Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;", "delegateToPermissionsServiceIfNeeded", "permissionTypes", "permissionsServiceDelegate", "Lkotlin/Function2;", "", "getAndroidPermissionsFromList", "", "(Ljava/util/List;)[Ljava/lang/String;", "getAsync", "getName", "getRequester", "permissionType", "onCreate", "moduleRegistry", "Lorg/unimodules/core/ModuleRegistry;", "parsePermissionsResponse", "Landroid/os/Bundle;", "permissionMap", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsModule.kt */
public final class PermissionsModule extends ExportedModule {
    private Permissions mPermissions;
    private Map<String, ? extends PermissionRequester> mRequesters;

    public String getName() {
        return "ExpoPermissions";
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PermissionsModule(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public void onCreate(ModuleRegistry moduleRegistry) throws IllegalStateException {
        SimpleRequester simpleRequester;
        boolean z;
        Intrinsics.checkNotNullParameter(moduleRegistry, "moduleRegistry");
        Permissions permissions = (Permissions) moduleRegistry.getModule(Permissions.class);
        if (permissions != null) {
            this.mPermissions = permissions;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            NotificationRequester notificationRequester = new NotificationRequester(context);
            Permissions permissions2 = this.mPermissions;
            if (permissions2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPermissions");
            }
            if (permissions2.isPermissionPresentInManifest("android.permission.WRITE_CONTACTS")) {
                simpleRequester = new SimpleRequester("android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS");
            } else {
                simpleRequester = new SimpleRequester("android.permission.READ_CONTACTS");
            }
            Pair[] pairArr = new Pair[14];
            String type = PermissionsTypes.LOCATION.getType();
            if (Build.VERSION.SDK_INT == 29) {
                Permissions permissions3 = this.mPermissions;
                if (permissions3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mPermissions");
                }
                z = permissions3.isPermissionPresentInManifest("android.permission.ACCESS_BACKGROUND_LOCATION");
            } else {
                z = false;
            }
            pairArr[0] = TuplesKt.m1160to(type, new LegacyLocationRequester(z));
            pairArr[1] = TuplesKt.m1160to(PermissionsTypes.LOCATION_FOREGROUND.getType(), new ForegroundLocationRequester());
            pairArr[2] = TuplesKt.m1160to(PermissionsTypes.LOCATION_BACKGROUND.getType(), new BackgroundLocationRequester());
            pairArr[3] = TuplesKt.m1160to(PermissionsTypes.CAMERA.getType(), new SimpleRequester("android.permission.CAMERA"));
            pairArr[4] = TuplesKt.m1160to(PermissionsTypes.CONTACTS.getType(), simpleRequester);
            pairArr[5] = TuplesKt.m1160to(PermissionsTypes.AUDIO_RECORDING.getType(), new SimpleRequester("android.permission.RECORD_AUDIO"));
            pairArr[6] = TuplesKt.m1160to(PermissionsTypes.MEDIA_LIBRARY_WRITE_ONLY.getType(), new SimpleRequester("android.permission.WRITE_EXTERNAL_STORAGE"));
            pairArr[7] = TuplesKt.m1160to(PermissionsTypes.MEDIA_LIBRARY.getType(), new SimpleRequester("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"));
            pairArr[8] = TuplesKt.m1160to(PermissionsTypes.CALENDAR.getType(), new SimpleRequester("android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"));
            pairArr[9] = TuplesKt.m1160to(PermissionsTypes.SMS.getType(), new SimpleRequester("android.permission.READ_SMS"));
            pairArr[10] = TuplesKt.m1160to(PermissionsTypes.NOTIFICATIONS.getType(), notificationRequester);
            pairArr[11] = TuplesKt.m1160to(PermissionsTypes.USER_FACING_NOTIFICATIONS.getType(), notificationRequester);
            pairArr[12] = TuplesKt.m1160to(PermissionsTypes.SYSTEM_BRIGHTNESS.getType(), new SimpleRequester("android.permission.WRITE_SETTINGS"));
            pairArr[13] = TuplesKt.m1160to(PermissionsTypes.REMINDERS.getType(), new RemindersRequester());
            this.mRequesters = MapsKt.mapOf(pairArr);
            return;
        }
        throw new IllegalStateException("Couldn't find implementation for Permissions interface.");
    }

    private final PermissionRequester getRequester(String str) throws IllegalStateException {
        Map<String, ? extends PermissionRequester> map = this.mRequesters;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mRequesters");
        }
        PermissionRequester permissionRequester = (PermissionRequester) map.get(str);
        if (permissionRequester != null) {
            return permissionRequester;
        }
        throw new IllegalStateException("Unrecognized permission type: " + str);
    }

    @ExpoMethod
    public final void getAsync(ArrayList<String> arrayList, Promise promise) {
        Intrinsics.checkNotNullParameter(arrayList, "requestedPermissionsTypes");
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        try {
            Permissions permissions = this.mPermissions;
            if (permissions == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPermissions");
            }
            delegateToPermissionsServiceIfNeeded(arrayList, new PermissionsModule$getAsync$1(permissions), promise);
        } catch (IllegalStateException e) {
            promise.reject("ERR_PERMISSIONS_UNKNOWN", "Failed to get permissions", e);
        }
    }

    @ExpoMethod
    public final void askAsync(ArrayList<String> arrayList, Promise promise) {
        Intrinsics.checkNotNullParameter(arrayList, "requestedPermissionsTypes");
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        try {
            Permissions permissions = this.mPermissions;
            if (permissions == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPermissions");
            }
            delegateToPermissionsServiceIfNeeded(arrayList, new PermissionsModule$askAsync$1(permissions), promise);
        } catch (IllegalStateException e) {
            promise.reject("ERR_PERMISSIONS_UNKNOWN", "Failed to get permissions", e);
        }
    }

    private final PermissionsResponseListener createPermissionsResponseListener(ArrayList<String> arrayList, Promise promise) {
        return new PermissionsModule$createPermissionsResponseListener$1(this, promise, arrayList);
    }

    private final void delegateToPermissionsServiceIfNeeded(ArrayList<String> arrayList, Function2<? super PermissionsResponseListener, ? super String[], Unit> function2, Promise promise) {
        List list = arrayList;
        String[] androidPermissionsFromList = getAndroidPermissionsFromList(list);
        if (androidPermissionsFromList.length == 0) {
            promise.resolve(parsePermissionsResponse(list, MapsKt.emptyMap()));
        } else {
            function2.invoke(createPermissionsResponseListener(arrayList, promise), androidPermissionsFromList);
        }
    }

    /* access modifiers changed from: private */
    public final Bundle parsePermissionsResponse(List<String> list, Map<String, PermissionsResponse> map) throws IllegalStateException {
        Bundle bundle = new Bundle();
        for (String str : list) {
            bundle.putBundle(str, getRequester(str).parseAndroidPermissions(map));
        }
        return bundle;
    }

    private final String[] getAndroidPermissionsFromList(List<String> list) throws IllegalStateException {
        Iterable<String> iterable = list;
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (String requester : iterable) {
            arrayList.add(getRequester(requester).getAndroidPermissions());
        }
        Iterator it = ((List) arrayList).iterator();
        if (it.hasNext()) {
            Object next = it.next();
            while (it.hasNext()) {
                next = CollectionsKt.plus((List) next, (List) it.next());
            }
            Object[] array = ((Collection) next).toArray(new String[0]);
            if (array != null) {
                return (String[]) array;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        throw new UnsupportedOperationException("Empty collection can't be reduced.");
    }
}
