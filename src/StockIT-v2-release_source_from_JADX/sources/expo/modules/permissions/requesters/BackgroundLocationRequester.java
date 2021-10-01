package expo.modules.permissions.requesters;

import android.os.Build;
import android.os.Bundle;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.permissions.PermissionsResponse;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u001c\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0016J\u001c\u0010\u000b\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0003J\u001c\u0010\f\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0003J\u001c\u0010\r\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0002¨\u0006\u000e"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/BackgroundLocationRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "()V", "getAndroidPermissions", "", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "parseAndroidPermissionsForAndroidQ", "parseAndroidPermissionsForAndroidR", "parseAndroidPermissionsForLegacyAndroids", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: BackgroundLocationRequester.kt */
public final class BackgroundLocationRequester implements PermissionRequester {
    public Bundle parseAndroidPermissions(Map<String, PermissionsResponse> map) {
        Intrinsics.checkNotNullParameter(map, "permissionsResponse");
        if (Build.VERSION.SDK_INT >= 30) {
            return parseAndroidPermissionsForAndroidR(map);
        }
        if (Build.VERSION.SDK_INT == 29) {
            return parseAndroidPermissionsForAndroidQ(map);
        }
        return parseAndroidPermissionsForLegacyAndroids(map);
    }

    public List<String> getAndroidPermissions() {
        if (Build.VERSION.SDK_INT >= 30) {
            return CollectionsKt.listOf("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        if (Build.VERSION.SDK_INT == 29) {
            return CollectionsKt.listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        return CollectionsKt.listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [java.util.Map, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.os.Bundle parseAndroidPermissionsForAndroidR(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r5) {
        /*
            r4 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "android.permission.ACCESS_BACKGROUND_LOCATION"
            java.lang.Object r5 = kotlin.collections.MapsKt.getValue(r5, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r5 = (org.unimodules.interfaces.permissions.PermissionsResponse) r5
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = r5.getStatus()
            java.lang.String r2 = r1.getStatus()
            java.lang.String r3 = "status"
            r0.putString(r3, r2)
            java.lang.String r2 = "expires"
            java.lang.String r3 = "never"
            r0.putString(r2, r3)
            boolean r5 = r5.getCanAskAgain()
            java.lang.String r2 = "canAskAgain"
            r0.putBoolean(r2, r5)
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r1 != r5) goto L_0x0030
            r5 = 1
            goto L_0x0031
        L_0x0030:
            r5 = 0
        L_0x0031:
            java.lang.String r1 = "granted"
            r0.putBoolean(r1, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.BackgroundLocationRequester.parseAndroidPermissionsForAndroidR(java.util.Map):android.os.Bundle");
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [java.util.Map, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.os.Bundle parseAndroidPermissionsForAndroidQ(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r9) {
        /*
            r8 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "android.permission.ACCESS_FINE_LOCATION"
            java.lang.Object r1 = kotlin.collections.MapsKt.getValue(r9, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r1 = (org.unimodules.interfaces.permissions.PermissionsResponse) r1
            java.lang.String r2 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.Object r2 = kotlin.collections.MapsKt.getValue(r9, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r2 = (org.unimodules.interfaces.permissions.PermissionsResponse) r2
            java.lang.String r3 = "android.permission.ACCESS_BACKGROUND_LOCATION"
            java.lang.Object r9 = kotlin.collections.MapsKt.getValue(r9, r3)
            org.unimodules.interfaces.permissions.PermissionsResponse r9 = (org.unimodules.interfaces.permissions.PermissionsResponse) r9
            boolean r3 = r1.getCanAskAgain()
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0033
            boolean r3 = r2.getCanAskAgain()
            if (r3 == 0) goto L_0x0033
            boolean r3 = r9.getCanAskAgain()
            if (r3 == 0) goto L_0x0033
            r3 = 1
            goto L_0x0034
        L_0x0033:
            r3 = 0
        L_0x0034:
            r6 = 3
            org.unimodules.interfaces.permissions.PermissionsStatus[] r6 = new org.unimodules.interfaces.permissions.PermissionsStatus[r6]
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = r1.getStatus()
            r6[r4] = r1
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = r2.getStatus()
            r6[r5] = r1
            r1 = 2
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = r9.getStatus()
            r6[r1] = r9
            java.util.List r9 = kotlin.collections.CollectionsKt.listOf(r6)
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            boolean r1 = r9 instanceof java.util.Collection
            if (r1 == 0) goto L_0x005f
            r2 = r9
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x005f
        L_0x005d:
            r2 = 1
            goto L_0x0079
        L_0x005f:
            java.util.Iterator r2 = r9.iterator()
        L_0x0063:
            boolean r6 = r2.hasNext()
            if (r6 == 0) goto L_0x005d
            java.lang.Object r6 = r2.next()
            org.unimodules.interfaces.permissions.PermissionsStatus r6 = (org.unimodules.interfaces.permissions.PermissionsStatus) r6
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r6 != r7) goto L_0x0075
            r6 = 1
            goto L_0x0076
        L_0x0075:
            r6 = 0
        L_0x0076:
            if (r6 != 0) goto L_0x0063
            r2 = 0
        L_0x0079:
            if (r2 == 0) goto L_0x007e
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            goto L_0x00ac
        L_0x007e:
            if (r1 == 0) goto L_0x008b
            r1 = r9
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x008b
        L_0x0089:
            r9 = 1
            goto L_0x00a5
        L_0x008b:
            java.util.Iterator r9 = r9.iterator()
        L_0x008f:
            boolean r1 = r9.hasNext()
            if (r1 == 0) goto L_0x0089
            java.lang.Object r1 = r9.next()
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = (org.unimodules.interfaces.permissions.PermissionsStatus) r1
            org.unimodules.interfaces.permissions.PermissionsStatus r2 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            if (r1 != r2) goto L_0x00a1
            r1 = 1
            goto L_0x00a2
        L_0x00a1:
            r1 = 0
        L_0x00a2:
            if (r1 != 0) goto L_0x008f
            r9 = 0
        L_0x00a5:
            if (r9 == 0) goto L_0x00aa
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            goto L_0x00ac
        L_0x00aa:
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = org.unimodules.interfaces.permissions.PermissionsStatus.UNDETERMINED
        L_0x00ac:
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r9 != r1) goto L_0x00b1
            r4 = 1
        L_0x00b1:
            java.lang.String r9 = r9.getStatus()
            java.lang.String r1 = "status"
            r0.putString(r1, r9)
            java.lang.String r9 = "expires"
            java.lang.String r1 = "never"
            r0.putString(r9, r1)
            java.lang.String r9 = "canAskAgain"
            r0.putBoolean(r9, r3)
            java.lang.String r9 = "granted"
            r0.putBoolean(r9, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.BackgroundLocationRequester.parseAndroidPermissionsForAndroidQ(java.util.Map):android.os.Bundle");
    }

    private final Bundle parseAndroidPermissionsForLegacyAndroids(Map<String, PermissionsResponse> map) {
        return RequestersHelperKt.parseBasicLocationPermissions(map);
    }
}
